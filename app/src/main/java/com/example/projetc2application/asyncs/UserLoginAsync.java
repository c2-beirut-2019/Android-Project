package com.example.projetc2application.asyncs;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.projetc2application.R;
import com.example.projetc2application.beans.ErrorResponseBean;
import com.example.projetc2application.beans.HttpResponseBean;
import com.example.projetc2application.beans.NewsBean;
import com.example.projetc2application.beans.UserBean;
import com.example.projetc2application.handlers.NewsHandler;
import com.example.projetc2application.handlers.UserHandler;
import com.example.projetc2application.utils.GlobalFunctions;
import com.example.projetc2application.utils.GlobalVars;
import com.example.projetc2application.utils.Prefs;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by User on 1/22/2018.
 */

public class UserLoginAsync extends AsyncTask<Void, Void, String> {

    Activity activity;
    OnFinishListener mListener;
    UserBean userBean;
    LayoutInflater mLayoutInflater;
    HttpResponseBean bean;
    RelativeLayout rlProgressBar;
    ErrorResponseBean errorResponseBean;
    //    ProgressBar pgloadmore;
    boolean isRefresh;
    public boolean isRunning = false;
    public boolean isAll = false;
    boolean didFail = false;

    String username = "", password = "";

    public UserLoginAsync(Activity activity, String username, String password, RelativeLayout rlProgressBar, boolean isRefresh, OnFinishListener listener) {
        this.activity = activity;
        this.mListener = listener;
        this.isRefresh = isRefresh;
        this.rlProgressBar = rlProgressBar;
        this.username = username;
        this.password = password;
        mLayoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        isRunning = true;
        isAll = false;
        didFail = false;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        try {
            if (GlobalFunctions.CheckNetwork(activity)) {
                if (!isRefresh) {
                    rlProgressBar.setVisibility(View.VISIBLE);
                }
            } else {
                didFail = true;
//                swipeRefresh.setRefreshing(false);
                Toast.makeText(activity, activity.getString(R.string.message_error_connection), Toast.LENGTH_SHORT).show();
                mListener.onError(activity.getString(R.string.message_error_connection),activity.getString(R.string.message_error_connection));
            }
        } catch (Exception e) {
            e.printStackTrace();
            didFail = true;
        }
    }

    @Override
    protected String doInBackground(Void... params) {
        String resp = "";
        if (!didFail) {
            try {
                HashMap<String, String> headers = new HashMap<>();
                JSONObject jsonObject = new JSONObject();
                headers.put("grant_type","password");
                headers.put("username",username);
                headers.put("password",password);
                if (GlobalVars.IS_USER)
                    bean = GlobalFunctions.Post_StreamX_WWW_Http( headers, Prefs.getInstance(activity).getAccessToken(),GlobalVars.BASE_URL + GlobalVars.LOGIN_URL);
                else
                    bean = GlobalFunctions.Post_StreamX_WWW_Http( headers, Prefs.getInstance(activity).getAccessToken(),GlobalVars.BASE_URL + GlobalVars.LOGIN_DOCTOR_URL);

                System.out.println("UserLoginAsync>>>>>>>>>>>>>>>>>>>>>>>" + bean.getResponse());

                if (bean.getStatus() >= 200 && bean.getStatus() < 400) {
                    resp = bean.getResponse();

                    if (!isCancelled()) {
                        userBean = UserHandler.parseUser(resp);
                    }
                }else{
                    if (!isCancelled()) {
                        resp = bean.getResponse();
                        errorResponseBean = ErrorResponseBean.parseError(resp);
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
                didFail = true;
            }
        }
        return resp;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            rlProgressBar.setVisibility(View.GONE);
            isRunning = false;
            if (mListener != null) {
                if (userBean != null) {
                    mListener.onSuccess(userBean);
                } else {
                    mListener.onError(bean.getStatus() + "",errorResponseBean.getResponse());
                }

            } else {
                mListener.onError("An error has occured, try again later","");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void clearReferences() {
        activity = null;
    }

    public interface OnFinishListener {
        void onSuccess(Object var1);

        void onError(Object var1,Object var2);
    }
}
