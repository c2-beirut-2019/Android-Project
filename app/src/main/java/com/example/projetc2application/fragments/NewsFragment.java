package com.example.projetc2application.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.projetc2application.R;
import com.example.projetc2application.adapters.NewsAdapter;
import com.example.projetc2application.asyncs.GetNewsAsync;
import com.example.projetc2application.beans.NewsBean;

import java.util.ArrayList;

public class NewsFragment extends Fragment {

    View layout;
    RecyclerView rvNews;
    RelativeLayout rlProgressBar;
    Activity activity;
    NewsAdapter newsAdapter;
    GetNewsAsync getNewsAsync;
    GetNewsAsync.OnFinishListener onFinishListener;
    int page = 1;

    public NewsFragment() {
        // Required empty public constructor
    }

    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_news, container, false);

        setupView();

        return layout;
    }

    public void setupView(){
        activity = getActivity();
        rvNews = layout.findViewById(R.id.rvNews);
        rlProgressBar = layout.findViewById(R.id.rlProgressBar);
        onFinishListener = new GetNewsAsync.OnFinishListener() {
            @Override
            public void onSuccess(Object var1) {
                ArrayList<NewsBean> newsBeans = (ArrayList<NewsBean>)var1;
                newsAdapter = new NewsAdapter(activity,newsBeans);
                rvNews.setAdapter(newsAdapter);
            }

            @Override
            public void onError(Object var1) {

            }
        };

        getNews(page,false);
    }

    public void getNews(int page , boolean isRefresh){
        getNewsAsync = new GetNewsAsync(activity,rlProgressBar,false,onFinishListener);
        getNewsAsync.execute();
    }

}
