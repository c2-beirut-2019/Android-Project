package com.example.projetc2application.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.projetc2application.R;


public class ViewImageActivity extends AppCompatActivity {


    ImageView ivProfile;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        String imageUrl = getIntent().getStringExtra("imageUrl");

        ivProfile = findViewById(R.id.ivProfile);



    }
}
