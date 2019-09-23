package com.example.projetc2application.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.projetc2application.R;
import com.example.projetc2application.asyncs.UserLoginAsync;
import com.example.projetc2application.asyncs.ValidateCodeAsync;
import com.example.projetc2application.beans.UserBean;
import com.example.projetc2application.utils.GlobalVars;
import com.example.projetc2application.utils.Prefs;

public class VerifyCodeActivity extends AppCompatActivity {

    Activity activity;
    EditText etVerifyCode;
    RelativeLayout rlProgressBar;
    TextView tvVerify;

    ValidateCodeAsync.OnFinishListener onFinishListener;
    ValidateCodeAsync validateCodeAsync;

    String code ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);

        setupViews();

        implementListeners();
    }

    public void setupViews() {
        activity = this;
        etVerifyCode = findViewById(R.id.etFullName);
        tvVerify = findViewById(R.id.tvVerifyCode);
        rlProgressBar = findViewById(R.id.rlProgressBar);

        onFinishListener = new ValidateCodeAsync.OnFinishListener() {
            @Override
            public void onSuccess(Object var1) {
                rlProgressBar.setVisibility(View.GONE);
                Intent intent = new Intent(activity,SignUpActivity.class);
                intent.putExtra(GlobalVars.CODE_BUNDLE,code);
                activity.startActivity(intent);
            }

            @Override
            public void onError(Object var1) {
                rlProgressBar.setVisibility(View.GONE);

            }
        };
    }

    public void implementListeners() {
        tvVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code = etVerifyCode.getText().toString().trim();

                if (!code.isEmpty()) {
                    validateUser(code);
                }
            }
        });

    }

    public void validateUser(String code) {
        validateCodeAsync = new ValidateCodeAsync(activity, code, rlProgressBar, false, onFinishListener);
        validateCodeAsync.execute();
    }

}
