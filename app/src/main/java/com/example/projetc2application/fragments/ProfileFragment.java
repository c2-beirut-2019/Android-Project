package com.example.projetc2application.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.projetc2application.R;
import com.example.projetc2application.activities.MainActivity;
import com.example.projetc2application.activities.MenuTabActivity;
import com.example.projetc2application.activities.ProfileActivity;
import com.example.projetc2application.adapters.NewsAdapter;
import com.example.projetc2application.asyncs.GetProfileAsync;
import com.example.projetc2application.beans.ProfileBean;
import com.example.projetc2application.utils.CircleImageView;
import com.example.projetc2application.utils.Prefs;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {

    Activity activity;
    View layout;
    TextView tvUsername,tvEditProfile,tvLogOut;
    GetProfileAsync getProfileAsync;
    ProfileBean profileBean;
    RelativeLayout rlProgressBar;
    CircleImageView ivProfile;

    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_profile, container, false);

        setupViews();

        implementListeners();

        getProfileInfo();

        return layout;
    }

    public  void setupViews(){
        activity = getActivity();
        ivProfile = layout.findViewById(R.id.ivProfile);
        rlProgressBar = layout.findViewById(R.id.rlProgressBar);
        tvUsername = layout.findViewById(R.id.tvUsername);
        tvEditProfile = layout.findViewById(R.id.tvEditProfile);
        tvLogOut = layout.findViewById(R.id.tvLogout);
    }

    public void implementListeners(){

        tvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Prefs.getInstance(activity).clearPrefVar();
                Intent intent = new Intent(activity, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(intent);
            }
        });

        tvEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ProfileActivity.class);
                activity.startActivityForResult(intent,104);
            }
        });
    }

    public void getProfileInfo() {
        getProfileAsync = new GetProfileAsync(activity, rlProgressBar, false, new GetProfileAsync.OnFinishListener() {
            @Override
            public void onSuccess(Object var1) {
                profileBean = (ProfileBean) var1;
                tvUsername.setText(profileBean.getFirstName()+" "+profileBean.getLastName());
                com.squareup.picasso.Transformation transformation = new RoundedTransformationBuilder()
                        .scaleType(ImageView.ScaleType.CENTER_CROP)
                        .oval(false)
                        .build();

                try {
                    Picasso.get()
                            .load(profileBean.getImage())
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
                                    ivProfile.setImageDrawable(activity.getResources().getDrawable(R.drawable.default_pic));

                                }
                            });
                } catch (Exception e) {
                    ivProfile.setImageDrawable(activity.getResources().getDrawable(R.drawable.default_pic));
                }

            }

            @Override
            public void onError(Object var1, Object var2) {

            }
        });
        getProfileAsync.execute();
    }
}
