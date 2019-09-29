package com.example.projetc2application.handlers;


import com.example.projetc2application.beans.DoctorsBean;
import com.example.projetc2application.beans.PetsBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DoctorsHandler {

    public static ArrayList<DoctorsBean> parseDoctors(String response) {
        ArrayList<DoctorsBean> petsBeans = new ArrayList<>();
        if (response != null) {
            try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                         String _id = "";
                         String diplomas = "";
                         String firstName = "";
                         String fullName = "";
                         String lastName = "";
                         String speciality = "";
                        DoctorsBean petsBean = new DoctorsBean();
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        if (jsonObject.has("_id")) {
                            _id = jsonObject.getString("_id");
                        }
                        if (jsonObject.has("diplomas")) {
                            diplomas = jsonObject.getString("diplomas");
                        }
                        if (jsonObject.has("firstName")) {
                            firstName = jsonObject.getString("firstName");
                        }
                        if (jsonObject.has("lastName")) {
                            lastName = jsonObject.getString("lastName");
                        }
                        if (jsonObject.has("fullName")) {
                            fullName = jsonObject.getString("fullName");
                        }
                        if (jsonObject.has("speciality")) {
                            speciality = jsonObject.getString("speciality");
                        }


                        petsBean.set_id(_id);
                        petsBean.setDiplomas(diplomas);
                        petsBean.setFirstName(firstName);
                        petsBean.setLastName(lastName);
                        petsBean.setFullName(fullName);
                        petsBean.setSpeciality(speciality);
                        petsBeans.add(petsBean);
                    }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return petsBeans;
    }
}
