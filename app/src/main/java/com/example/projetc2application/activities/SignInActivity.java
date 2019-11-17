package com.example.projetc2application.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.projetc2application.R;
import com.example.projetc2application.asyncs.UserLoginAsync;
import com.example.projetc2application.beans.UserBean;
import com.example.projetc2application.utils.GlobalFunctions;
import com.example.projetc2application.utils.Prefs;

public class SignInActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    TextView tvLogin;
    RelativeLayout rlProgressBar;
    Activity activity;
    UserLoginAsync userLoginAsync;
    UserLoginAsync.OnFinishListener onFinishListener;
    String password = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        setupViews();

        implementListeners();
    }

    public void setupViews() {
        activity = this;
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        tvLogin = findViewById(R.id.tvSignIn);
        rlProgressBar = findViewById(R.id.rlProgressBar);

        onFinishListener = new UserLoginAsync.OnFinishListener() {
            @Override
            public void onSuccess(Object var1) {
                rlProgressBar.setVisibility(View.GONE);
                Prefs prefs = Prefs.getInstance(activity);
                UserBean userBean = (UserBean) var1;
                if (userBean != null) {
                    prefs.setIsLoggedIn(true);
                    prefs.setUser(userBean.getUsername());
                    prefs.setPassword(password);
                    prefs.setAccessToken(userBean.getAccess_token());
                    prefs.setFullName(userBean.getFirstName() + " " + userBean.getLastName());
                    prefs.setUserId(userBean.get_id());
                }
                Intent intent = new Intent(activity, MenuTabActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(intent);
            }

            @Override
            public void onError(Object var1,Object var2) {
                rlProgressBar.setVisibility(View.GONE);
                GlobalFunctions.handlingOnErrorResponse(activity,(String)var1,(String) var2);

            }
        };
    }

    public void implementListeners() {
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                password = etPassword.getText().toString().trim();

                if (!username.isEmpty() && !password.isEmpty()) {

                    loginUser(username, password);
                }
            }
        });

    }

    public void loginUser(String username, String password) {
        userLoginAsync = new UserLoginAsync(activity, username, password, rlProgressBar, false, onFinishListener);
        userLoginAsync.execute();
    }
}
