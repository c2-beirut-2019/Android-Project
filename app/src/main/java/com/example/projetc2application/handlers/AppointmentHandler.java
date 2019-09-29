package com.example.projetc2application.handlers;


import com.example.projetc2application.beans.AppointmentBean;
import com.example.projetc2application.beans.DoctorsBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AppointmentHandler {

    public static ArrayList<AppointmentBean> parseDoctors(String response) {
        ArrayList<AppointmentBean> petsBeans = new ArrayList<>();
        if (response != null) {
            try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                         int procedureTime=0;
                         String name="";
                         String _id = "";
                        AppointmentBean petsBean = new AppointmentBean();
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        if (jsonObject.has("_id")) {
                            _id = jsonObject.getString("_id");
                        }
                        if (jsonObject.has("name")) {
                            name = jsonObject.getString("name");
                        }
                        if (jsonObject.has("procedureTime")) {
                            procedureTime = jsonObject.getInt("procedureTime");
                        }


                        petsBean.set_id(_id);
                        petsBean.setName(name);
                        petsBean.setProcedureTime(procedureTime);
                        petsBeans.add(petsBean);
                    }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return petsBeans;
    }
}
