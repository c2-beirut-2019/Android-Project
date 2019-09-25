package com.example.projetc2application.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.projetc2application.R;
import com.example.projetc2application.utils.GlobalVars;

public class MainMenuActivity extends AppCompatActivity {

    Activity activity;
    CardView cvDoctor,cvUser;
    boolean toSignIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        activity=this;
        toSignIn = getIntent().getBooleanExtra(GlobalVars.CODE_BUNDLE,false);

        cvDoctor = findViewById(R.id.cvDoctor);
        cvUser = findViewById(R.id.cvUser);

        cvDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVars.IS_USER = false;
                if(toSignIn){
                    Intent intent1 = new Intent(activity,SignInActivity.class);
                    intent1.putExtra(GlobalVars.IS_DOCTOR_BUNDLE,true);
                    activity.startActivity(intent1);
                }else{
                    Intent intent1 = new Intent(activity,VerifyCodeActivity.class);
                    intent1.putExtra(GlobalVars.IS_DOCTOR_BUNDLE,true);
                    activity.startActivity(intent1);
                }

            }
        });

        cvUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVars.IS_USER = true;
                if(toSignIn){
                    Intent intent1 = new Intent(activity,SignInActivity.class);
                    intent1.putExtra(GlobalVars.IS_DOCTOR_BUNDLE,false);
                    activity.startActivity(intent1);
                }else{
                    Intent intent1 = new Intent(activity,VerifyCodeActivity.class);
                    intent1.putExtra(GlobalVars.IS_DOCTOR_BUNDLE,false);
                    activity.startActivity(intent1);
                }
            }
        });
    }
}
