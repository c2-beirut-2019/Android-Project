package com.example.projetc2application.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetc2application.R;
import com.example.projetc2application.adapters.DoctorsAdapter;
import com.example.projetc2application.asyncs.GetProfileAsync;
import com.example.projetc2application.fragments.BottomSheetChangeProfileDialogFragment;
import com.example.projetc2application.utils.CircleImageView;
import com.example.projetc2application.utils.GlobalFunctions;
import com.example.projetc2application.utils.Prefs;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    CircleImageView ivProfile;
    EditText etFirstName,etLastName;
    RelativeLayout rlProgressBar;
    Uri imgUri;
    Activity activity;
    GetProfileAsync getProfileAsync;
    boolean isChanged = false;
    boolean isDeleted = false;
    public static final int HANDLE_READ_STORAGE_PERM = 400;
    public static final int HANDLE_CAMERA_PERM = 401;
    public static final int CHOOSE_PICTURE_REQUEST = 402;
    public static final int HANDLE_WRITE_STORAGE_PERM = 403;
    public static final int TAKE_PICTURE_REQUEST = 404;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        activity = this;
        rlProgressBar = findViewById(R.id.rlProgressBar);
        ivProfile = findViewById(R.id.ivCeleb);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);

        etFirstName.setText(Prefs.getInstance(activity).getFullName());
        etLastName.setText(Prefs.getInstance(activity).getFullName());

        getProfileInfo();
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                BottomSheetChangeProfileDialogFragment editListDialog1 =
//                        BottomSheetChangeProfileDialogFragment.newInstance(profileBean);
//                editListDialog1.show(((AppCompatActivity) activity).getSupportFragmentManager(), null);
            }
        });
    }

    public void takePicture() {
        if (checkCameraPermissions(activity)) {
            if (checkWriteStoragePermissions(activity)) {
                imgUri = GlobalFunctions.takePhoto(activity, TAKE_PICTURE_REQUEST, "Projetc2");
            } else {
                requestWriteStoragePermission();
            }

        } else {
            requestCameraPermission();
        }
    }

    public void getProfileInfo(){
        getProfileAsync = new GetProfileAsync(activity, rlProgressBar, false, new GetProfileAsync.OnFinishListener() {
            @Override
            public void onSuccess(Object var1) {

            }

            @Override
            public void onError(Object var1, Object var2) {

            }
        });
        getProfileAsync.execute();
    }
    public void choosePicture() {
        if (checkReadStoragePermissions(activity)) {
            try {
                Intent localIntent = new Intent(Intent.ACTION_PICK);
                localIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                localIntent.setType("image/*");
                activity.startActivityForResult(localIntent, CHOOSE_PICTURE_REQUEST);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            requestReadStoragePermission();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            switch (requestCode) {
                case TAKE_PICTURE_REQUEST:

                    if (resultCode == Activity.RESULT_OK) {

                        if (imgUri != null) {
                            isChanged = true;
                            if (resultCode == Activity.RESULT_OK) {
                                onImageReceived(imgUri);
                            } else {
                                activity.getContentResolver().delete(imgUri, null, null);
                            }
                        } else {
//                            GlobalFunctions.showToast(activity, getString(R.string.could_not_upload_image));
                        }
                    }
                    break;


                case CHOOSE_PICTURE_REQUEST:

                    if (resultCode == Activity.RESULT_OK) {
                        isChanged = true;
                        if (data != null && data.getData() != null) {
                            String imageFile = GlobalFunctions.getFileName(this, data.getData());

                            onImageReceived(imageFile);
                        } else {
//                            GlobalFunctions.showToast(activity, getString(R.string.could_not_upload_image));
                        }
                    }
                    break;
                case UCrop.REQUEST_CROP:
                    if (resultCode == RESULT_OK) {
                        isChanged = true;
                        Uri resultUri = UCrop.getOutput(data);
                        try {
                            String imagePath = resultUri.getPath();
                            ivProfile.setImageURI(resultUri);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public static boolean checkWriteStoragePermissions(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean checkReadStoragePermissions(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean checkCameraPermissions(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case HANDLE_READ_STORAGE_PERM:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        Intent localIntent = new Intent(Intent.ACTION_PICK);
                        localIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                        localIntent.setType("image/*");
                        activity.startActivityForResult(localIntent, CHOOSE_PICTURE_REQUEST);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    GlobalFunctions.showDialog(activity,"Please allow write external storage");
                }
                break;
            case HANDLE_WRITE_STORAGE_PERM:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    imgUri = GlobalFunctions.takePhoto(activity, TAKE_PICTURE_REQUEST, "Projetc2");
                } else {
                    GlobalFunctions.showDialog(activity, "Please allow write external storage");
                }
                break;
            case HANDLE_CAMERA_PERM:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (checkWriteStoragePermissions(activity)) {
                        imgUri = GlobalFunctions.takePhoto(activity, TAKE_PICTURE_REQUEST, "Projetc2");
                    } else {
                        requestWriteStoragePermission();
                    }
                }
        }
    }

    private void requestReadStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            final String[] permissions;
            permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissions,
                        HANDLE_READ_STORAGE_PERM);
            }
        }
    }

    private void requestCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            final String[] permissions;
            permissions = new String[]{Manifest.permission.CAMERA};
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissions,
                        HANDLE_CAMERA_PERM);
            }
        }
    }

    private void requestWriteStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            final String[] permissions;
            permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissions,
                        HANDLE_WRITE_STORAGE_PERM);
            }
        }
    }

    public void onImageReceived(Uri imageFile) {
        UCrop.Options options = new UCrop.Options();
        options.withAspectRatio(1, 1);
        options.withMaxResultSize(750, 750);
        options.setCircleDimmedLayer(true);


        UCrop.of(imageFile, imageFile)
                .withOptions(options)
                .start(this);
    }

    public void onImageReceived(String imageFile) {
        UCrop.Options options = new UCrop.Options();
        options.withAspectRatio(1, 1);
        options.withMaxResultSize(750, 750);
        options.setCircleDimmedLayer(true);

        imgUri = Uri.fromFile(new File(imageFile));
        UCrop.of(Uri.fromFile(new File(imageFile)), Uri.fromFile(new File(imageFile)))
                .withOptions(options)
                .start(this);
    }


    
    public void deleteImage() {
        try {
            isChanged = true;
            isDeleted = true;
            com.squareup.picasso.Transformation transformation = new RoundedTransformationBuilder()
                    .scaleType(ImageView.ScaleType.CENTER_CROP)
                    .oval(false)
                    .build();

            try {
                Picasso.get()
                        .load("")
                        .fit()
                        .placeholder(R.drawable.default_pic)
                        .centerCrop()
                        .transform(transformation)
                        .into(ivProfile, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {
                                ivProfile.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_profile));

                            }
                        });
            } catch (Exception e) {
                ivProfile.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_profile));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
