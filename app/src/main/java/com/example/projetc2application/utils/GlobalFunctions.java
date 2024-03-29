package com.example.projetc2application.utils;

import android.app.Activity;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.example.projetc2application.BuildConfig;
import com.example.projetc2application.activities.MainActivity;
import com.example.projetc2application.activities.ViewImageActivity;
import com.example.projetc2application.beans.HttpResponseBean;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class GlobalFunctions {

    private static final int MAX_LOG_SIZE = 65516;
    private static final int TIME_OUT = 45000;
    private static String HASH = "TBxk8zxu3uCfSKcg8c7K2MxSfsXdeDdc";


    public static boolean CheckNetwork(Context context) {
        try {
            ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Network[] networks = connec.getAllNetworks();
                boolean isConnected = false;
                for (Network network : networks) {
                    NetworkInfo netInfo = connec.getNetworkInfo(network);
                    if ((netInfo.getType() == ConnectivityManager.TYPE_WIFI || netInfo.getType() == ConnectivityManager.TYPE_MOBILE) && netInfo.isConnected()) {
                        return true;
                    }
                }
            } else {
                NetworkInfo wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                NetworkInfo mobile = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                if ((wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected()))
                    return true;
            }
            return false;
        } catch (Exception e) {
            GlobalFunctions.printException(e);

            return false;
        }
    }
    public static void printException(Exception e) {
        if (BuildConfig.DEBUG || BuildConfig.FLAVOR.equals("dev")) {
            if (e != null) {
                Log.d(GlobalVars.LOG_TAG, Log.getStackTraceString(e));
            }
        }
    }
    public static String PrintStream(InputStream in) throws Exception {
        InputStreamReader isr = new InputStreamReader(in, "UTF-8");
        BufferedReader buffer = new BufferedReader(isr);
        String str = "";
        StringBuilder strbuild = new StringBuilder();
        while ((str = buffer.readLine()) != null) {
            strbuild.append(str);
        }
        return strbuild.toString();
    }

    public static String encryptData(String text) {
        try {
            byte[] ivBytes = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                    0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
            byte[] keyBytes = HASH
                    .getBytes("UTF-8");
            byte[] textBytes = text.getBytes("UTF-8");
            byte[] encryptedData = AES256Cipher.AES_Encode(ivBytes, keyBytes,
                    textBytes);
            encryptedData = Base64.encode(encryptedData, 0);
            return new String(encryptedData, "UTF-8");
        } catch (Exception e) {
            GlobalFunctions.printException(e);

            return "";
        }
    }

    public static String decryptData(String text) {
        try {
            byte[] ivBytes = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                    0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
            byte[] encryptedData = Base64.decode(text, 0);
            return new String(AES256Cipher.AES_Decode(ivBytes,
                    HASH.getBytes("UTF-8"),
                    encryptedData), "UTF-8");
        } catch (Exception e) {
            GlobalFunctions.printException(e);

            return "";
        }
    }

    public static void showToast(Activity activity, String Message) {
        if (activity != null) {
            Toast.makeText(activity, Message, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showToast(Activity activity, String Message, int length) {
        if (activity != null) {
            Toast.makeText(activity, Message, length).show();
        }
    }
    public static HttpResponseBean Get_StreamHttp(String urlStr, HashMap<String, String> headers, boolean isGet) throws Exception {

        HttpResponseBean httpResponseBean = new HttpResponseBean();

        URL url = new URL(urlStr);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(TIME_OUT /* milliseconds */);
        connection.setConnectTimeout(TIME_OUT /* milliseconds */);
        connection.setRequestMethod(isGet ? "GET" : "DELETE");
        connection.setDoInput(true);

        if (!headers.isEmpty()) {
            for (String key : headers.keySet()) {
                if (!headers.get(key).isEmpty()) {
                    connection.setRequestProperty(key, headers.get(key));
                }
            }
        }

        connection.connect();

        int status = 0;
        try {
            status = connection.getResponseCode();
        } catch (Exception e) {
            if (GlobalVars.IS_DEBUG) {
                GlobalFunctions.printException(e);
            }
            if (e.getMessage().equalsIgnoreCase("No authentication challenges found")) {
                status = HttpURLConnection.HTTP_UNAUTHORIZED;
            }
        }
        if (GlobalVars.IS_DEBUG) {
            largeLog("HttpStatus", String.valueOf(status));
        }

        httpResponseBean.setStatus(status);

        if (status >= HttpURLConnection.HTTP_OK && status < 400) {
            InputStream inputStream = connection.getInputStream();
            if (inputStream != null) {
                httpResponseBean.setResponse(PrintStream(inputStream));
            } else {
                httpResponseBean.setResponse("");
            }
        } else {
            httpResponseBean.setResponse(PrintStream(connection.getErrorStream()));
        }

        return httpResponseBean;
    }

    public static HttpResponseBean Post_StreamHttp(JSONObject jsonObject, HashMap<String, String> headers, String urlStr, String method) throws Exception {

        HttpResponseBean httpResponseBean = new HttpResponseBean();

        URL url = new URL(urlStr);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(TIME_OUT /* milliseconds */);
        connection.setConnectTimeout(TIME_OUT /* milliseconds */);
        connection.setRequestMethod(method);

        if (!headers.isEmpty()) {
            for (String key : headers.keySet()) {
                if (!headers.get(key).isEmpty()) {
                    connection.setRequestProperty(key, headers.get(key));
                }
            }
        }

        connection.addRequestProperty("Connection", "Keep-Alive");
        connection.addRequestProperty("Accept", "application/json");
        connection.addRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.setUseCaches(false);
        connection.setDoOutput(true);
        connection.setDoInput(true);

        OutputStream os = connection.getOutputStream();
        os.write(jsonObject.toString().getBytes("UTF-8"));
        os.close();

//        OutputStream out = new BufferedOutputStream(connection.getOutputStream());
//        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
//        writer.write(URLEncoder.encode(jsonObject.toString(), "UTF-8"));
//        writer.flush();
//        writer.close();
//        out.close();

        connection.connect();

        int status = 0;
        try {
            status = connection.getResponseCode();
        } catch (Exception e) {
            if (GlobalVars.IS_DEBUG) {
                GlobalFunctions.printException(e);
            }
            if (e.getMessage().equalsIgnoreCase("No authentication challenges found")) {
                status = HttpURLConnection.HTTP_UNAUTHORIZED;
            }
        }
        if (GlobalVars.IS_DEBUG) {
            largeLog("HttpStatus", String.valueOf(status));
        }

        httpResponseBean.setStatus(status);

        if (status >= HttpURLConnection.HTTP_OK && status < 400) {
            InputStream inputStream = connection.getInputStream();
            if (inputStream != null) {
                httpResponseBean.setResponse(PrintStream(inputStream));
            } else {
                httpResponseBean.setResponse("");
            }
        } else {
            httpResponseBean.setResponse(PrintStream(connection.getErrorStream()));
        }

        return httpResponseBean;
    }

    public static HttpResponseBean Post_StreamX_WWW_Http(HashMap<String, String> params, String auth_token, String urlStr) throws Exception {
        HttpResponseBean httpResponseBean = new HttpResponseBean();
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(TIME_OUT);
        connection.setConnectTimeout(TIME_OUT);
        connection.setRequestMethod("POST");
        if (!auth_token.isEmpty()) {
            connection.setRequestProperty("Authorization", auth_token);
        }

        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
        connection.setUseCaches(false);
        connection.setDoOutput(true);
        connection.setDoInput(true);
        if (params != null) {
            OutputStream os = connection.getOutputStream();
            DataOutputStream wr = new DataOutputStream(os);
            wr.writeBytes(createQueryStringForParameters(params));
            wr.close();
            os.flush();
            os.close();
        }

        connection.connect();
        int status = 0;

        try {
            status = connection.getResponseCode();
        } catch (Exception var8) {
            if ("".equals("dev")) {
                printException(var8);
            }

            if (var8.getMessage().equalsIgnoreCase("No authentication challenges found")) {
                status = 401;
            }
        }

        if ("".equals("dev")) {
            largeLog("HttpStatus", String.valueOf(status));
        }

        httpResponseBean.setStatus(status);

        if (status >= HttpURLConnection.HTTP_OK && status < 400) {
            InputStream inputStream = connection.getInputStream();
            if (inputStream != null) {
                httpResponseBean.setResponse(PrintStream(inputStream));
            } else {
                httpResponseBean.setResponse("");
            }
        } else {
            httpResponseBean.setResponse(PrintStream(connection.getErrorStream()));
        }
        return httpResponseBean;
    }
    public static void largeLog(String tag, String content) {
        if (content.length() > 4000) {
            final int chunkSize = 2048;
            final int maxSize = Math.min(content.length(), MAX_LOG_SIZE);
            for (int i = 0; i < maxSize; i += chunkSize) {
                Log.d(tag, content.substring(i, Math.min(maxSize, i + chunkSize)));
            }
        } else {
            Log.d(tag, content);
        }
    }

    public static void largeLog2(String tag, String content) {
        if (content.length() > 4000) {
            Log.d(tag, content.substring(0, 4000));
            largeLog2(tag, content.substring(4000));
        } else {
            Log.d(tag, content);
        }
    }
    public static String createQueryStringForParameters(Map<String, String> parameters) throws UnsupportedEncodingException {
        StringBuilder parametersAsQueryString = new StringBuilder();
        if (parameters != null) {
            boolean firstParameter = true;

            for(Iterator var3 = parameters.entrySet().iterator(); var3.hasNext(); firstParameter = false) {
                Map.Entry<String, String> stringStringEntry = (Map.Entry)var3.next();
                if (!firstParameter) {
                    parametersAsQueryString.append('&');
                }

                parametersAsQueryString.append((String)stringStringEntry.getKey()).append('=').append(URLEncoder.encode((String)stringStringEntry.getValue(), "UTF-8"));
            }
        }

        return parametersAsQueryString.toString();
    }


    public static void handlingOnErrorResponse(Activity activity,String error,String message){
        if(error.equals("401")){
            //session expiered
            GlobalFunctions.showToast(activity,"Your session is expiered, please login to continue");
            Intent intent =  new Intent(activity, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            activity.startActivity(intent);
        }else{
            GlobalFunctions.showToast(activity,message);
        }

    }

    public static Uri takePhoto(Activity activity, int result, String name) {
        {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri picUri;
            File file;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                file = getOutputMediaFile(1, name);
                picUri = Uri.fromFile(file); // create
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
            } else {
                file = new File(getOutputMediaFile(1, name).getPath());
                picUri = FileProvider.getUriForFile(activity.getApplicationContext(), activity.getApplicationContext().getPackageName() + ".provider", file);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
            }
            cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            activity.startActivityForResult(cameraIntent, result);
            return Uri.fromFile(file);
        }
    }

    public static Uri takePhoto(Activity activity, int result, String name, String fileProvider) {
        {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri picUri;
            File file;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                file = getOutputMediaFile(1, name);
                picUri = Uri.fromFile(file); // create
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
            } else {
                file = new File(getOutputMediaFile(1, name).getPath());
                picUri = FileProvider.getUriForFile(activity.getApplicationContext(), fileProvider, file);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
            }
            cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            activity.startActivityForResult(cameraIntent, result);
            return Uri.fromFile(file);
        }
    }
    public static File getOutputMediaFile(int type, String name) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), name);

        /**Create the storage directory if it does not exist*/
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        /**Create a media file name*/
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == 1) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".png");
        } else {
            return null;
        }

        return mediaFile;
    }
    public static void showDialog(final Activity activity, String message) {
        try {
            AlertDialogFragment diag = new AlertDialogFragment();
            diag.setMessage(message);
            diag.setCancelable(false);
            diag.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            if (!activity.isFinishing())
                diag.show(activity.getFragmentManager(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static String getFileName(Activity activity, Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(activity, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }
    public static void goToViewImage(Activity activity, String imageUrl) {
        try {
            Intent intent = new Intent(activity, ViewImageActivity.class);
            intent.putExtra("imageUrl", imageUrl);
            activity.startActivity(intent);
        } catch (Exception e) {
        }
    }

    public static String convertDateToTimeZone(String date, String inputF, String outputF, Locale locale, TimeZone inputTimezone, TimeZone
            outputTimezone) {
        DateFormat inputFormat = new SimpleDateFormat(inputF, locale);
        inputFormat.setTimeZone(inputTimezone);

        DateFormat outputFormat = new SimpleDateFormat(outputF, locale);
        outputFormat.setTimeZone(outputTimezone);

        Date parsed = new Date();
        try {
            parsed = inputFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputFormat.format(parsed);
    }
}
