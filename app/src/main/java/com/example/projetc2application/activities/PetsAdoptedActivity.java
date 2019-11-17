package com.example.projetc2application.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.example.projetc2application.R;
import com.example.projetc2application.adapters.PetsAdapter;
import com.example.projetc2application.asyncs.GetPetsToAdoptAsync;
import com.example.projetc2application.asyncs.GetPetsUserAsync;
import com.example.projetc2application.beans.PetsBean;
import com.example.projetc2application.utils.GlobalFunctions;

import java.util.ArrayList;

public class PetsAdoptedActivity extends AppCompatActivity {

    RecyclerView rvNews;
    RelativeLayout rlProgressBar;
    Activity activity;
    PetsAdapter newsAdapter;
//    GetPetsToAdoptAsync getNewsAsync;
        GetPetsUserAsync getNewsAsync;
    GetPetsUserAsync.OnFinishListener onFinishListener;
//    GetPetsToAdoptAsync.OnFinishListener onFinishListener;
    int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets_adopted);

        setupView();
    }

    public void setupView() {
        activity = this;
        rvNews = findViewById(R.id.rvNews);
        rlProgressBar = findViewById(R.id.rlProgressBar);
        onFinishListener = new GetPetsUserAsync.OnFinishListener() {
            @Override
            public void onSuccess(Object var1) {
                ArrayList<PetsBean> newsBeans = (ArrayList<PetsBean>) var1;
                newsAdapter = new PetsAdapter(activity, newsBeans);
                rvNews.setAdapter(newsAdapter);
            }

            @Override
            public void onError(Object var1,Object var2) {
                GlobalFunctions.handlingOnErrorResponse(activity,(String)var1,(String) var2);

            }
        };

        getNews(page, false);
    }

    public void getNews(int page, boolean isRefresh) {
        getNewsAsync = new GetPetsUserAsync(activity, rlProgressBar, false, onFinishListener);
        getNewsAsync.execute();
    }

}
