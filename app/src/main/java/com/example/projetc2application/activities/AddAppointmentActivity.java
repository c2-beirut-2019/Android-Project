package com.example.projetc2application.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.projetc2application.R;
import com.example.projetc2application.asyncs.AddAppointmentAsync;
import com.example.projetc2application.beans.AppointmentBean;
import com.example.projetc2application.beans.DoctorsBean;
import com.example.projetc2application.beans.PetsBean;
import com.example.projetc2application.utils.GlobalFunctions;
import com.example.projetc2application.utils.GlobalVars;

import java.util.Calendar;

public class AddAppointmentActivity extends AppCompatActivity {
    Activity activity;
    RelativeLayout rlProgressBar;
    TextView tvAdd, tvPet, tvAppType, tvDoctor, tvDateTime, tvTime;
    int mYear, mMonth, mDay, mHour, mMinute;
    String doctor_id = "", pet_id = "", appointment_type_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        activity = this;
        rlProgressBar = findViewById(R.id.rlProgressBar);
        tvAdd = findViewById(R.id.tvAdd);
        tvPet = findViewById(R.id.tvPet);
        tvAppType = findViewById(R.id.tvAppType);
        tvDoctor = findViewById(R.id.tvDoctor);
        tvDateTime = findViewById(R.id.tvDateTime);
        tvTime = findViewById(R.id.tvTime);

        tvAppType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, AppointmentTypesActivity.class);
                activity.startActivityForResult(intent, 101);
            }
        });
        tvPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, PetsAdoptedActivity.class);
                activity.startActivityForResult(intent, 102);
            }
        });
        tvDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, DoctorsListActivity.class);
                activity.startActivityForResult(intent, 103);
            }
        });

        tvDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(activity,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                tvDateTime.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(activity,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                tvTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time = tvTime.getText().toString().trim();
                String date = tvDateTime.getText().toString().trim();
                if (doctor_id != null && !doctor_id.isEmpty() && !appointment_type_id.isEmpty() && !pet_id.isEmpty() && !time.isEmpty() && !date.isEmpty()) {
                    new AddAppointmentAsync(activity, pet_id, appointment_type_id, doctor_id, date + " " + time, rlProgressBar, false, new AddAppointmentAsync.OnFinishListener() {
                        @Override
                        public void onSuccess(Object var1) {

                            activity.setResult(RESULT_OK);
                            activity.finish();
                        }

                        @Override
                        public void onError(Object var1) {

                        }
                    }).execute();
                } else {
                    GlobalFunctions.showToast(activity, "Please fill all fields");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            switch (requestCode) {
                case 101:
                    if (resultCode == RESULT_OK) {
                        AppointmentBean doctorsBean = (AppointmentBean) data.getSerializableExtra(GlobalVars.CODE_BUNDLE);
                        tvAppType.setText(doctorsBean.getName());
                        appointment_type_id = doctorsBean.get_id();
                    }
                    break;
                case 102:
                    if (resultCode == RESULT_OK) {
                        PetsBean doctorsBean = (PetsBean) data.getSerializableExtra(GlobalVars.CODE_BUNDLE);
                        tvPet.setText(doctorsBean.getName());
                        pet_id = doctorsBean.get_id();
                    }
                    break;
                case 103:
                    if (resultCode == RESULT_OK) {
                        DoctorsBean doctorsBean = (DoctorsBean) data.getSerializableExtra(GlobalVars.CODE_BUNDLE);
                        tvDoctor.setText(doctorsBean.getFullName());
                        doctor_id = doctorsBean.get_id();
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
