package com.example.projetc2application.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.projetc2application.R;
import com.example.projetc2application.utils.Prefs;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    TextView tvSignIn,tvSignUp,tvSkipAll;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViews();
        implementListeners();

    }

    public void setupViews(){
        activity = this;
        tvSignIn = findViewById(R.id.tvSignIn);
        tvSignUp = findViewById(R.id.tvSignUp);
        tvSkipAll = findViewById(R.id.tvSkip);
    }

    public void implementListeners(){
        tvSignIn.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
        tvSkipAll.setOnClickListener(this);

        if(Prefs.getInstance(activity).getIsLoggedIn()){
            Intent intent = new Intent(activity, MenuTabActivity.class);
            activity.startActivity(intent);
            activity.finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvSignIn:
                Intent intent1 = new Intent(activity,SignInActivity.class);
                activity.startActivity(intent1);
                break;
            case R.id.tvSignUp:
                Intent intent2 = new Intent(activity,VerifyCodeActivity.class);
                activity.startActivity(intent2);
                break;
            case R.id.tvSkip:
                Intent intent = new Intent(activity, MenuTabActivity.class);
                activity.startActivity(intent);
                activity.finish();
                break;
        }
    }
}
