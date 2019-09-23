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
import com.example.projetc2application.asyncs.SetUsernamePasswordAsync;
import com.example.projetc2application.asyncs.UserLoginAsync;
import com.example.projetc2application.asyncs.ValidateCodeAsync;
import com.example.projetc2application.beans.UserBean;
import com.example.projetc2application.utils.GlobalVars;
import com.example.projetc2application.utils.Prefs;

public class SignUpActivity extends AppCompatActivity {

    Activity activity;
    EditText etUsername,etPassword,etConfPassword;
    TextView tvSignUp;
    RelativeLayout rlProgressBar;
    SetUsernamePasswordAsync.OnFinishListener onFinishListener;
    SetUsernamePasswordAsync setUsernamePasswordAsync;
    UserLoginAsync userLoginAsync;
    UserLoginAsync.OnFinishListener onFinishListener2;
    String code ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setupViews();

        implementListeners();

    }

    public void setupViews() {
        activity = this;
        code = getIntent().getStringExtra(GlobalVars.CODE_BUNDLE);
        etUsername = findViewById(R.id.etFullName);
        etPassword = findViewById(R.id.etPassword);
        etConfPassword = findViewById(R.id.etConfPass);
        tvSignUp = findViewById(R.id.tvSignUp);
        rlProgressBar = findViewById(R.id.rlProgressBar);

        onFinishListener2= new UserLoginAsync.OnFinishListener() {
            @Override
            public void onSuccess(Object var1) {
                Prefs prefs = Prefs.getInstance(activity);
                UserBean userBean = (UserBean) var1;
                if (userBean != null) {
                    prefs.setIsLoggedIn(true);
                    prefs.setUser(userBean.getUsername());
                    prefs.setAccessToken(userBean.getAccess_token());
                    prefs.setFullName(userBean.getFirstName() + " " + userBean.getLastName());
                    prefs.setUserId(userBean.get_id());
                }
                Intent intent = new Intent(activity,MenuTabActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(intent);
            }

            @Override
            public void onError(Object var1) {

            }
        };

        onFinishListener = new SetUsernamePasswordAsync.OnFinishListener() {
            @Override
            public void onSuccess(Object var1) {
                rlProgressBar.setVisibility(View.GONE);
               loginUser(Prefs.getInstance(activity).getUsername(),Prefs.getInstance(activity).getPassword());
            }

            @Override
            public void onError(Object var1) {
                rlProgressBar.setVisibility(View.GONE);

            }
        };
    }

    public void implementListeners() {
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String pass = etPassword.getText().toString().trim();
                String confPass = etConfPassword.getText().toString().trim();

                if (!username.isEmpty()&& !pass.isEmpty()&& !confPass.isEmpty()) {
                    if(pass.equals(confPass)) {
                        Prefs.getInstance(activity).setUsername(username);
                        Prefs.getInstance(activity).setPassword(pass);
                        validateUser(username, pass);
                    }
                }
            }
        });

    }

    public void validateUser(String username,String password) {
        setUsernamePasswordAsync = new SetUsernamePasswordAsync(activity, code ,username,password, rlProgressBar, false, onFinishListener);
        setUsernamePasswordAsync.execute();
    }
    public void loginUser(String username, String password) {
        userLoginAsync = new UserLoginAsync(activity, username, password, rlProgressBar, false, onFinishListener2);
        userLoginAsync.execute();
    }
}
