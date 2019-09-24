package com.example.projetc2application.activities;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.projetc2application.R;
import com.example.projetc2application.fragments.NewsFragment;
import com.example.projetc2application.fragments.PetsFragment;
import com.example.projetc2application.fragments.ProductsFragment;
import com.example.projetc2application.fragments.ProfileFragment;
import com.example.projetc2application.utils.GlobalFunctions;
import com.example.projetc2application.utils.Prefs;

public class MenuTabActivity extends AppCompatActivity implements View.OnClickListener {

    Activity activity;
    Fragment fragment;
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
        fragment = new NewsFragment();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frContainer, fragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rlProducts:
                fragment = new ProductsFragment();
                replaceFragments(fragment);
                break;
            case R.id.rlNews:
                fragment = new NewsFragment();
                replaceFragments(fragment);
                break;
            case R.id.rlPets:
                fragment = new PetsFragment();
                replaceFragments(fragment);
                break;
            case R.id.rlDoctors:
                if(Prefs.getInstance(activity).getIsLoggedIn()) {

                }else{
                    GlobalFunctions.showToast(activity,"Please login to access doctors section.");
                }
                break;
            case R.id.rlAppointments:
                if(Prefs.getInstance(activity).getIsLoggedIn()) {

                }else{
                    GlobalFunctions.showToast(activity,"Please login to access appointment section.");
                }
                break;
            case R.id.rlProfile:
                if(Prefs.getInstance(activity).getIsLoggedIn()) {
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
}
