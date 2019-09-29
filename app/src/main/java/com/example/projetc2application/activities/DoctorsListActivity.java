package com.example.projetc2application.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.example.projetc2application.R;
import com.example.projetc2application.adapters.DoctorsAdapter;
import com.example.projetc2application.asyncs.GetDoctorsListAsync;
import com.example.projetc2application.beans.DoctorsBean;

import java.util.ArrayList;

public class DoctorsListActivity extends AppCompatActivity {

    RecyclerView rvNews;
    RelativeLayout rlProgressBar;
    Activity activity;
    DoctorsAdapter newsAdapter;
    GetDoctorsListAsync getDoctorsListAsync;
    GetDoctorsListAsync.OnFinishListener onFinishListener;
    int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_list);

        setupView();
    }

    public void setupView() {
        activity = this;
        rvNews = findViewById(R.id.rvProducts);

        rlProgressBar = findViewById(R.id.rlProgressBar);
        onFinishListener = new GetDoctorsListAsync.OnFinishListener() {
            @Override
            public void onSuccess(Object var1) {
                ArrayList<DoctorsBean> newsBeans = (ArrayList<DoctorsBean>) var1;
                newsAdapter = new DoctorsAdapter(activity, newsBeans);
                rvNews.setAdapter(newsAdapter);
            }

            @Override
            public void onError(Object var1) {

            }
        };

        getProducts(page, false);
    }

    public void getProducts(int page, boolean isRefresh) {
        getDoctorsListAsync = new GetDoctorsListAsync(activity, rlProgressBar, false, onFinishListener);
        getDoctorsListAsync.execute();
    }
}
