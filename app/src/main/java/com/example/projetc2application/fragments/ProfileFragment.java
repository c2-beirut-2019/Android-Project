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
import android.widget.TextView;

import com.example.projetc2application.R;
import com.example.projetc2application.activities.MainActivity;
import com.example.projetc2application.activities.MenuTabActivity;
import com.example.projetc2application.activities.ProfileActivity;
import com.example.projetc2application.utils.Prefs;

public class ProfileFragment extends Fragment {

    Activity activity;
    View layout;
    TextView tvUsername,tvEditProfile,tvLogOut;

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

        return layout;
    }

    public  void setupViews(){
        activity = getActivity();
        tvUsername = layout.findViewById(R.id.tvUsername);
        tvEditProfile = layout.findViewById(R.id.tvEditProfile);
        tvLogOut = layout.findViewById(R.id.tvLogout);
    }

    public void implementListeners(){
        tvUsername.setText(Prefs.getInstance(activity).getFullName());
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
                activity.startActivity(intent);
            }
        });
    }
}
