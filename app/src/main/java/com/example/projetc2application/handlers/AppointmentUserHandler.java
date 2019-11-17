package com.example.projetc2application.handlers;


import com.example.projetc2application.beans.AppointmentUserBean;
import com.example.projetc2application.beans.DoctorsBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AppointmentUserHandler {

    public static ArrayList<AppointmentUserBean> parseAppointmentUser(String response) {
        ArrayList<AppointmentUserBean> petsBeans = new ArrayList<>();
        if (response != null) {
            try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                         String _id = "";
                         String doctor_firstName = "";
                         String doctor_lastName = "";
                         String doctor_speciality = "";
                         String pet_name = "";
                         String appointmentType_name = "";
                         String startDate = "";
                         String pet_image = "";
                        AppointmentUserBean petsBean = new AppointmentUserBean();
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        if (jsonObject.has("_id")) {
                            _id = jsonObject.getString("_id");
                        }
                        if (jsonObject.has("doctor_firstName")) {
                            doctor_firstName = jsonObject.getString("doctor_firstName");
                        }
                        if (jsonObject.has("doctor_lastName")) {
                            doctor_lastName = jsonObject.getString("doctor_lastName");
                        }
                        if (jsonObject.has("doctor_speciality")) {
                            doctor_speciality = jsonObject.getString("doctor_speciality");
                        }
                        if (jsonObject.has("pet_name")) {
                            pet_name = jsonObject.getString("pet_name");
                        }
                        if (jsonObject.has("appointmentType_name")) {
                            appointmentType_name = jsonObject.getString("appointmentType_name");
                        }
                        if (jsonObject.has("startDate")) {
                            startDate = jsonObject.getString("startDate");
                        }
                        if (jsonObject.has("pet_image")) {
                            pet_image = jsonObject.getString("pet_image");
                        }


                        petsBean.set_id(_id);
                        petsBean.setDoctor_firstName(doctor_firstName);
                        petsBean.setDoctor_lastName(doctor_lastName);
                        petsBean.setDoctor_speciality(doctor_speciality);
                        petsBean.setPet_name(pet_name);
                        petsBean.setAppointmentType_name(appointmentType_name);
                        petsBean.setStartDate(startDate);
                        petsBean.setPet_image(pet_image);
                        petsBeans.add(petsBean);
                    }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return petsBeans;
    }
}
