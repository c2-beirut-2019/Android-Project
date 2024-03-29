package com.example.projetc2application.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.projetc2application.R;
import com.example.projetc2application.activities.AddAppointmentActivity;
import com.example.projetc2application.adapters.AppointmentUserAdapter;
import com.example.projetc2application.adapters.DoctorsAdapter;
import com.example.projetc2application.asyncs.GetAppointmentsAsync;
import com.example.projetc2application.asyncs.GetDoctorsListAsync;
import com.example.projetc2application.beans.AppointmentUserBean;
import com.example.projetc2application.beans.DoctorsBean;
import com.example.projetc2application.utils.GlobalFunctions;
import com.example.projetc2application.utils.GlobalVars;

import java.util.ArrayList;

public class AppointmentFragment extends Fragment {

    View layout;
    RecyclerView rvNews;
    RelativeLayout rlProgressBar;
    Activity activity;
    AppointmentUserAdapter newsAdapter;
    GetAppointmentsAsync getDoctorsListAsync;
    GetAppointmentsAsync.OnFinishListener onFinishListener;
    int page = 1;
    ImageView ivAppointment;

    public AppointmentFragment() {
        // Required empty public constructor
    }

    public static AppointmentFragment newInstance(String param1, String param2) {
        AppointmentFragment fragment = new AppointmentFragment();
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
        layout = inflater.inflate(R.layout.fragment_appointments, container, false);

        setupView();

        return layout;
    }

    public void setupView(){
        activity = getActivity();
        rvNews = layout.findViewById(R.id.rvProducts);
        ivAppointment = layout.findViewById(R.id.ivAddAppointment);

        rlProgressBar = layout.findViewById(R.id.rlProgressBar);
        onFinishListener = new GetAppointmentsAsync.OnFinishListener() {
            @Override
            public void onSuccess(Object var1) {
                ArrayList<AppointmentUserBean> newsBeans = (ArrayList<AppointmentUserBean>)var1;
                newsAdapter = new AppointmentUserAdapter(activity,newsBeans);
                rvNews.setAdapter(newsAdapter);
            }

            @Override
            public void onError(Object var1,Object var2) {
                GlobalFunctions.handlingOnErrorResponse(activity,(String)var1,(String) var2);

            }
        };

        getProducts(page,false);

        if(GlobalVars.IS_USER){
            ivAppointment.setVisibility(View.VISIBLE);
        }else{
            ivAppointment.setVisibility(View.GONE);
        }

        ivAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, AddAppointmentActivity.class);
                activity.startActivityForResult(intent,100);
            }
        });
    }

    public void getProducts(int page , boolean isRefresh){
        getDoctorsListAsync = new GetAppointmentsAsync(activity,rlProgressBar,false,onFinishListener);
        getDoctorsListAsync.execute();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
