package com.example.projetc2application.asyncs;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.projetc2application.R;
import com.example.projetc2application.beans.HttpResponseBean;
import com.example.projetc2application.beans.ProductsBean;
import com.example.projetc2application.handlers.ProductsHandler;
import com.example.projetc2application.utils.GlobalFunctions;
import com.example.projetc2application.utils.GlobalVars;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by User on 1/22/2018.
 */

public class GetAppointmentTypesAsync extends AsyncTask<Void, Void, String> {

    Activity activity;
    OnFinishListener mListener;
    ArrayList<ProductsBean> newsBeans;
    LayoutInflater mLayoutInflater;
    HttpResponseBean bean;
    RelativeLayout rlProgressBar;
//    ProgressBar pgloadmore;
    boolean isRefresh;
    public boolean isRunning = false;
    public boolean isAll = false;
    boolean didFail = false;

    public GetAppointmentTypesAsync(Activity activity, RelativeLayout rlProgressBar, boolean isRefresh, OnFinishListener listener) {
        this.activity = activity;
        this.mListener = listener;
        this.isRefresh = isRefresh;
        this.rlProgressBar=rlProgressBar;
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
                mListener.onError("");
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
                bean = GlobalFunctions.Get_StreamHttp(GlobalVars.BASE_URL + GlobalVars.GET_APPOINTMENT_TYPES_URL, headers, true);

                System.out.println("GetNewsAsync>>>>>>>>>>>>>>>>>>>>>>>"+bean.getResponse());

                if (bean.getStatus() >= 200 && bean.getStatus() < 400) {
                    resp = bean.getResponse();

                    if (!isCancelled()) {
                        newsBeans = ProductsHandler.parseNews(resp);
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
                if (newsBeans != null) {
                    mListener.onSuccess(newsBeans);
                } else {
                    mListener.onError(bean.getStatus()+"");
                }

            } else {
                mListener.onError("An error has occured, try again later");
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

        void onError(Object var1);
    }
}
