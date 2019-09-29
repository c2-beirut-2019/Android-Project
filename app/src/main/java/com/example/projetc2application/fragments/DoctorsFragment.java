package com.example.projetc2application.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.projetc2application.R;
import com.example.projetc2application.adapters.DoctorsAdapter;
import com.example.projetc2application.adapters.ProductsAdapter;
import com.example.projetc2application.asyncs.GetDoctorsListAsync;
import com.example.projetc2application.asyncs.GetProductsAsync;
import com.example.projetc2application.beans.DoctorsBean;
import com.example.projetc2application.beans.ProductsBean;

import java.util.ArrayList;

public class DoctorsFragment extends Fragment {

    View layout;
    RecyclerView rvNews;
    RelativeLayout rlProgressBar;
    Activity activity;
    DoctorsAdapter newsAdapter;
    GetDoctorsListAsync getDoctorsListAsync;
    GetDoctorsListAsync.OnFinishListener onFinishListener;
    int page = 1;

    public DoctorsFragment() {
        // Required empty public constructor
    }

    public static DoctorsFragment newInstance(String param1, String param2) {
        DoctorsFragment fragment = new DoctorsFragment();
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
        layout = inflater.inflate(R.layout.fragment_products, container, false);

        setupView();

        return layout;
    }

    public void setupView(){
        activity = getActivity();
        rvNews = layout.findViewById(R.id.rvProducts);

        rlProgressBar = layout.findViewById(R.id.rlProgressBar);
        onFinishListener = new GetDoctorsListAsync.OnFinishListener() {
            @Override
            public void onSuccess(Object var1) {
                ArrayList<DoctorsBean> newsBeans = (ArrayList<DoctorsBean>)var1;
                newsAdapter = new DoctorsAdapter(activity,newsBeans);
                rvNews.setAdapter(newsAdapter);
            }

            @Override
            public void onError(Object var1) {

            }
        };

        getProducts(page,false);
    }

    public void getProducts(int page , boolean isRefresh){
        getDoctorsListAsync = new GetDoctorsListAsync(activity,rlProgressBar,false,onFinishListener);
        getDoctorsListAsync.execute();
    }

}
