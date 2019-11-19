package com.example.projetc2application.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.projetc2application.R;
import com.example.projetc2application.fragments.AppointmentFragment;
import com.example.projetc2application.fragments.DoctorsFragment;
import com.example.projetc2application.fragments.NewsFragment;
import com.example.projetc2application.fragments.PetsFragment;
import com.example.projetc2application.fragments.ProductsFragment;
import com.example.projetc2application.fragments.ProfileFragment;
import com.example.projetc2application.utils.GlobalFunctions;
import com.example.projetc2application.utils.Prefs;

public class MenuTabActivity extends AppCompatActivity implements View.OnClickListener {

    Activity activity;
    Fragment fragment;
    TextView tvTitle;
    private FragmentManager fragmentManager;
    RelativeLayout rlProducts,rlNews,rlDoctors,rlAppointments,rlPets,rlProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        setupViews();
    }

    public void setupViews(){
        activity = this;
        tvTitle = findViewById(R.id.tvTitle);
        rlProducts = findViewById(R.id.rlProducts);
        rlNews = findViewById(R.id.rlNews);
        rlDoctors = findViewById(R.id.rlDoctors);
        rlAppointments = findViewById(R.id.rlAppointments);
        rlPets = findViewById(R.id.rlPets);
        rlProfile = findViewById(R.id.rlProfile);

        rlProducts.setOnClickListener(this);
        rlNews.setOnClickListener(this);
        rlDoctors.setOnClickListener(this);
        rlAppointments.setOnClickListener(this);
        rlPets.setOnClickListener(this);
        rlProfile.setOnClickListener(this);

        fragmentManager = getSupportFragmentManager();
        tvTitle.setText("News");
        fragment = new NewsFragment();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frContainer, fragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rlProducts:
                tvTitle.setText("Products");
                fragment = new ProductsFragment();
                replaceFragments(fragment);
                break;
            case R.id.rlNews:
                tvTitle.setText("News");
                fragment = new NewsFragment();
                replaceFragments(fragment);
                break;
            case R.id.rlPets:
                tvTitle.setText("Pets");
                fragment = new PetsFragment();
                replaceFragments(fragment);
                break;
            case R.id.rlDoctors:
                if(Prefs.getInstance(activity).getIsLoggedIn()) {
                    tvTitle.setText("Doctors");
                    fragment = new DoctorsFragment();
                    replaceFragments(fragment);
                }else{
                    GlobalFunctions.showToast(activity,"Please login to access doctors section.");
                }
                break;
            case R.id.rlAppointments:
                if(Prefs.getInstance(activity).getIsLoggedIn()) {
                    tvTitle.setText("My Appointments");
                    fragment = new AppointmentFragment();
                    replaceFragments(fragment);
                }else{
                    GlobalFunctions.showToast(activity,"Please login to access appointment section.");
                }
                break;
            case R.id.rlProfile:
                if(Prefs.getInstance(activity).getIsLoggedIn()) {
                    tvTitle.setText("My Profile");
                    fragment = new ProfileFragment();
                    replaceFragments(fragment);
                }else{
                    GlobalFunctions.showToast(activity,"Please login to access profile section.");
                }
                break;
        }
    }

    public void replaceFragments(Fragment fragment){
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frContainer, fragment).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100){
            if(fragment instanceof AppointmentFragment){
                ((AppointmentFragment)fragment).getProducts(1,false);
            }
        }
        if(requestCode == 104 && resultCode ==RESULT_OK){
            if(fragment instanceof ProfileFragment)
                ((ProfileFragment)fragment).getProfileInfo();
            }
        }

}
