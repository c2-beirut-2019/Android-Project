package com.example.projetc2application.asyncs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.projetc2application.R;
import com.example.projetc2application.beans.DecodedBitmapBean;
import com.example.projetc2application.beans.ErrorResponseBean;
import com.example.projetc2application.beans.HttpResponseBean;
import com.example.projetc2application.beans.UserBean;
import com.example.projetc2application.handlers.UserHandler;
import com.example.projetc2application.utils.GlobalFunctions;
import com.example.projetc2application.utils.GlobalVars;
import com.example.projetc2application.utils.Prefs;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by User on 1/22/2018.
 */

public class UpdateProfileAsync extends AsyncTask<Void, Void, String> {

    Activity activity;
    OnFinishListener mListener;
    UserBean userBean;
    LayoutInflater mLayoutInflater;
    HttpResponseBean bean;
    ErrorResponseBean errorResponseBean;
    RelativeLayout rlProgressBar;
    //    ProgressBar pgloadmore;
    boolean isRefresh;
    public boolean isRunning = false;
    public boolean isAll = false;
    boolean didFail = false;
    Uri imgUri;
    String firstName = "", lastName = "",phoneNumber="",name="",emergencyPerson="",emergencyNumber="";
    public static final int MAX_IMAGE_SIZE = 300000;
    public static final int MAX_IMAGE_RESOLUTION = 800;

    public UpdateProfileAsync(Activity activity, String firstName, String lastName, String phoneNumber, String name,String emergencyPerson,String emergencyNumber,Uri imgUri, RelativeLayout rlProgressBar, boolean isRefresh, OnFinishListener listener) {
        this.activity = activity;
        this.mListener = listener;
        this.isRefresh = isRefresh;
        this.emergencyPerson = emergencyPerson;
        this.emergencyNumber = emergencyNumber;
        this.rlProgressBar = rlProgressBar;
        this.lastName = lastName;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.imgUri = imgUri;
        this.name = name;
        mLayoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        isRunning = true;
        isAll = false;
        didFail = false;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        try {
            if (GlobalFunctions.CheckNetwork(activity)) {
                if (!isRefresh) {
                    rlProgressBar.setVisibility(View.VISIBLE);
                }
            } else {
                didFail = true;
//                swipeRefresh.setRefreshing(false);
//                Toast.makeText(activity, activity.getString(R.string.message_error_connection), Toast.LENGTH_SHORT).show();
                mListener.onError(activity.getString(R.string.message_error_connection),"");
            }
        } catch (Exception e) {
            e.printStackTrace();
            didFail = true;
        }
    }

    @Override
    protected String doInBackground(Void... params) {
        String resp = "";
        if (!didFail) {
            try {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", Prefs.getInstance(activity).getAccessToken());
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("firstName",firstName);
                jsonObject.put("lastName",lastName);
                jsonObject.put("phoneNumber",phoneNumber);
                if (GlobalVars.IS_USER) {
                    if(emergencyPerson!=null && !emergencyPerson.isEmpty())
                    jsonObject.put("emergencyPerson", emergencyPerson);
                    if(emergencyNumber!=null && !emergencyNumber.isEmpty())
                        jsonObject.put("emergencyNumber", emergencyNumber);
                }
                if(imgUri!=null) {
                    JSONObject jsonObject1 = new JSONObject();
                    long time = System.currentTimeMillis();
                    jsonObject1.put("name", "image_" + time);
                    jsonObject1.put("extension", "png");
                    DecodedBitmapBean decodedBitmapBean = decodeFileBitmap(activity, imgUri);
                    Bitmap bitmap = decodedBitmapBean.getBitmap();
                    System.out.println("size<>>>>>>>>>>bITMAPP>>>" + bitmap.getByteCount());
                    String encodedData = decodedBitmapBean.getBase64Image();
                    jsonObject1.put("data", encodedData.trim().replaceAll("\n", "").replaceAll("\r", ""));
                    jsonObject.put("profilePic", jsonObject1);
                }
                if (GlobalVars.IS_USER)
                    bean = GlobalFunctions.Post_StreamHttp( jsonObject,headers,GlobalVars.BASE_URL + GlobalVars.GET_PROFILE_USER_URL,"POST");
                else
                    bean = GlobalFunctions.Post_StreamHttp( jsonObject,headers,GlobalVars.BASE_URL + GlobalVars.GET_DOCTOR_USER_URL,"POST");

                System.out.println("UpdateProfileAsync>>>>>>>>>>>>>>>>>>>>>>>" + bean.getResponse());

                if (bean.getStatus() >= 200 && bean.getStatus() < 400) {
                    resp = bean.getResponse();

                    if (!isCancelled()) {
                        userBean = UserHandler.parseUser(resp);
                    }
                }else{
                    if (!isCancelled()) {
                        resp = bean.getResponse();
                        errorResponseBean = ErrorResponseBean.parseError(resp);
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
                didFail = true;
            }
        }
        return resp;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            rlProgressBar.setVisibility(View.GONE);
            isRunning = false;
            if (mListener != null) {
                if (userBean != null) {
                    mListener.onSuccess(userBean);
                } else {
                    mListener.onError(bean.getStatus() + "",errorResponseBean.getResponse());
                }

            } else {
                mListener.onError("An error has occured, try again later","");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void clearReferences() {
        activity = null;
    }

    public interface OnFinishListener {
        void onSuccess(Object var1);

        void onError(Object var1, Object var2);
    }

    //to upload a base64 photo
    public static DecodedBitmapBean decodeFileBitmap(Activity activity,Uri imgPath) {
        // deal with the file
        ByteArrayOutputStream bmpStream = new ByteArrayOutputStream();

//        ExifInterface exif = null;
//        try {
//            exif = new ExifInterface(imgPath);
//        } catch (IOException e) {
//            printException(e);
//        }
//        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
//		         3: 180, 6: 90, 8: 270
        Matrix matrix = new Matrix();


//        switch (orientation) {
//            case 3:
//                matrix.postRotate(180);
//                break;
//            case 6:
//                matrix.postRotate(90);
//                break;
//            case 8:
//                matrix.postRotate(270);
//                break;
//
//            default:
//                break;
//        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inDither = true;
        InputStream imageStream = null;
        try {
            imageStream = activity.getContentResolver().openInputStream(imgPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
//        Bitmap bitmap = BitmapFactory.decodeFile(imgPath);

        Bitmap newb = resizeImageForImageView(bitmap, MAX_IMAGE_RESOLUTION);

        Bitmap rotatedBitmap = Bitmap.createBitmap(newb, 0, 0, newb.getWidth(), newb.getHeight(), matrix, true);


        int streamLength = bitmap.getByteCount();
        int compressQuality = 100;
        while (streamLength >= MAX_IMAGE_SIZE && compressQuality > 5) {
            try {
                bmpStream.flush();//to avoid out of memory error
                bmpStream.reset();
            } catch (IOException e) {
                e.printStackTrace();
            }
            compressQuality -= 5;
            rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream);
            byte[] bmpPicByteArray = bmpStream.toByteArray();
            streamLength = bmpPicByteArray.length;
        }
        if(streamLength<MAX_IMAGE_SIZE){
            rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream);
        }
        byte[] bmpPicByteArray = bmpStream.toByteArray();
        DecodedBitmapBean decodedBitmapBean = new DecodedBitmapBean();
        decodedBitmapBean.setBitmap(BitmapFactory.decodeStream(new ByteArrayInputStream(bmpPicByteArray)));
        decodedBitmapBean.setBase64Image(Base64.encodeToString(bmpPicByteArray, Base64.DEFAULT));

        return decodedBitmapBean;
    }
    public static Bitmap resizeImageForImageView(Bitmap bitmap, int maxSize) {
        Bitmap resizedBitmap = null;
        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int newWidth = -1;
        int newHeight = -1;
        float multFactor = -1.0F;
        if (originalHeight > originalWidth && originalHeight > maxSize) {
            newHeight = maxSize;
            multFactor = (float) originalWidth / (float) originalHeight;
            newWidth = (int) (newHeight * multFactor);
        } else if (originalWidth > originalHeight && originalWidth > maxSize) {
            newWidth = maxSize;
            multFactor = (float) originalHeight / (float) originalWidth;
            newHeight = (int) (newWidth * multFactor);
        } else if (originalHeight == originalWidth && originalHeight > maxSize) {
            newHeight = maxSize;
            newWidth = maxSize;
        } else {
            return bitmap;
        }
        resizedBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, false);

        return resizedBitmap;
    }
}
