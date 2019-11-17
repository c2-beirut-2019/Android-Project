package com.example.projetc2application.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.projetc2application.R;
import com.example.projetc2application.adapters.NewsAdapter;
import com.example.projetc2application.adapters.PetsAdapter;
import com.example.projetc2application.asyncs.GetNewsAsync;
import com.example.projetc2application.asyncs.GetPetsToAdoptAsync;
import com.example.projetc2application.beans.NewsBean;
import com.example.projetc2application.beans.PetsBean;
import com.example.projetc2application.utils.GlobalFunctions;

import java.util.ArrayList;

public class PetsFragment extends Fragment {

    View layout;
    RecyclerView rvNews;
    RelativeLayout rlProgressBar;
    Activity activity;
    PetsAdapter newsAdapter;
    GetPetsToAdoptAsync getNewsAsync;
    GetPetsToAdoptAsync.OnFinishListener onFinishListener;
    int page = 1;

    public PetsFragment() {
        // Required empty public constructor
    }

    public static PetsFragment newInstance(String param1, String param2) {
        PetsFragment fragment = new PetsFragment();
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
        onFinishListener = new GetPetsToAdoptAsync.OnFinishListener() {
            @Override
            public void onSuccess(Object var1) {
                ArrayList<PetsBean> newsBeans = (ArrayList<PetsBean>)var1;
                newsAdapter = new PetsAdapter(activity,newsBeans);
                rvNews.setAdapter(newsAdapter);
            }

            @Override
            public void onError(Object var1,Object var2) {
                GlobalFunctions.handlingOnErrorResponse(activity,(String)var1,(String) var2);
            }
        };

        getNews(page,false);
    }

    public void getNews(int page , boolean isRefresh){
        getNewsAsync = new GetPetsToAdoptAsync(activity,rlProgressBar,false,onFinishListener);
        getNewsAsync.execute();
    }

}
