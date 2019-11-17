package com.example.projetc2application.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.example.projetc2application.R;
import com.example.projetc2application.adapters.AppointmentTypesAdapter;
import com.example.projetc2application.adapters.DoctorsAdapter;
import com.example.projetc2application.asyncs.GetAppointmentTypesAsync;
import com.example.projetc2application.beans.AppointmentBean;
import com.example.projetc2application.beans.DoctorsBean;
import com.example.projetc2application.utils.GlobalFunctions;

import java.util.ArrayList;

public class AppointmentTypesActivity extends AppCompatActivity {

    Activity activity;
    RelativeLayout rlProgressBar;
    RecyclerView recyclerView;
    AppointmentTypesAdapter appointmentTypesAdapter;
    GetAppointmentTypesAsync getAppointmentTypesAsync;
    GetAppointmentTypesAsync.OnFinishListener onFinishListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_types);

        activity = this;
        rlProgressBar = findViewById(R.id.rlProgressBar);
        recyclerView = findViewById(R.id.rvAppointmentTypes);
        onFinishListener = new GetAppointmentTypesAsync.OnFinishListener() {
            @Override
            public void onSuccess(Object var1) {
                ArrayList<AppointmentBean> newsBeans = (ArrayList<AppointmentBean>)var1;
                appointmentTypesAdapter = new AppointmentTypesAdapter(activity,newsBeans);
                recyclerView.setAdapter(appointmentTypesAdapter);
            }

            @Override
            public void onError(Object var1,Object var2) {
                GlobalFunctions.handlingOnErrorResponse(activity,(String)var1,(String)var2);

            }
        };

        getAppointmentTypesAsync = new GetAppointmentTypesAsync(activity, rlProgressBar, false, onFinishListener);
        getAppointmentTypesAsync.execute();


    }
}
