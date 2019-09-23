package com.example.projetc2application.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.projetc2application.R;
import com.example.projetc2application.adapters.NewsAdapter;
import com.example.projetc2application.adapters.ProductsAdapter;
import com.example.projetc2application.asyncs.GetProductsAsync;
import com.example.projetc2application.beans.ProductsBean;

import java.util.ArrayList;

public class ProductsFragment extends Fragment {

    View layout;
    RecyclerView rvNews;
    RelativeLayout rlProgressBar;
    Activity activity;
    ProductsAdapter newsAdapter;
    GetProductsAsync getProductsAsync;
    GetProductsAsync.OnFinishListener onFinishListener;
    int page = 1;

    public ProductsFragment() {
        // Required empty public constructor
    }

    public static ProductsFragment newInstance(String param1, String param2) {
        ProductsFragment fragment = new ProductsFragment();
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
        rvNews.setLayoutManager(new GridLayoutManager(activity, 2));

        rlProgressBar = layout.findViewById(R.id.rlProgressBar);
        onFinishListener = new GetProductsAsync.OnFinishListener() {
            @Override
            public void onSuccess(Object var1) {
                ArrayList<ProductsBean> newsBeans = (ArrayList<ProductsBean>)var1;
                newsAdapter = new ProductsAdapter(activity,newsBeans);
                rvNews.setAdapter(newsAdapter);
            }

            @Override
            public void onError(Object var1) {

            }
        };

        getProducts(page,false);
    }

    public void getProducts(int page , boolean isRefresh){
        getProductsAsync = new GetProductsAsync(activity,rlProgressBar,false,onFinishListener);
        getProductsAsync.execute();
    }

}
