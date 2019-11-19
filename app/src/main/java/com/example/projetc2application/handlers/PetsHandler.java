package com.example.projetc2application.handlers;


import com.example.projetc2application.beans.PetsBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PetsHandler {

    public static ArrayList<PetsBean> parsePets(String response) {
        ArrayList<PetsBean> petsBeans = new ArrayList<>();
        if (response != null) {
            try {
                JSONObject jsonObj = new JSONObject(response);
                if (jsonObj.has("data")) {
                    JSONArray jsonArray = jsonObj.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String _id = "";
                        String name = "";
                        String image = "";
                        String color = "";
                        String dateOfBirth = "";
                        String registrationDate = "";
                        String category_name = "";
                        String category_id = "";
                        String specie_name = "";
                        String specie = "";
                        PetsBean petsBean = new PetsBean();
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        if (jsonObject.has("_id")) {
                            _id = jsonObject.getString("_id");
                        }
                        if (jsonObject.has("name")) {
                            name = jsonObject.getString("name");
                        }
                        if (jsonObject.has("image")) {
                            image = jsonObject.getString("image");
                        }
                        if (jsonObject.has("color")) {
                            color = jsonObject.getString("color");
                        }
                        if (jsonObject.has("dateOfBirth")) {
                            dateOfBirth = jsonObject.getString("dateOfBirth");
                        }
                        if (jsonObject.has("registrationDate")) {
                            registrationDate = jsonObject.getString("registrationDate");
                        }
                        if (jsonObject.has("category_name")) {
                            category_name = jsonObject.getString("category_name");
                        }
                        if (jsonObject.has("category_id")) {
                            category_id = jsonObject.getString("category_id");
                        }
                        if (jsonObject.has("specie_name")) {
                            specie_name = jsonObject.getString("specie_name");
                        }
                        if (jsonObject.has("specie")) {
                            specie = jsonObject.getString("specie");
                        }


                        petsBean.set_id(_id);
                        petsBean.setName(name);
                        petsBean.setCategory_id(category_id);
                        petsBean.setCategory_name(category_name);
                        petsBean.setImage(image);
                        petsBean.setColor(color);
                        petsBean.setDateOfBirth(dateOfBirth);
                        petsBean.setSpecie_name(specie_name);
                        petsBean.setSpecie(specie);
                        petsBean.setRegistrationDate(registrationDate);
                        petsBeans.add(petsBean);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return petsBeans;
    }

    public static ArrayList<PetsBean> parseUserPets(String response) {
        ArrayList<PetsBean> petsBeans = new ArrayList<>();
        if (response != null) {
            try {

                    JSONArray jsonArray = new JSONArray(response) ;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String _id = "";
                        String name = "";
                        String image = "";
                        String color = "";
                        String dateOfBirth = "";
                        String registrationDate = "";
                        String category_name = "";
                        String category_id = "";
                        String specie_name = "";
                        String specie = "";
                        PetsBean petsBean = new PetsBean();
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        if (jsonObject.has("_id")) {
                            _id = jsonObject.getString("_id");
                        }
                        if (jsonObject.has("name")) {
                            name = jsonObject.getString("name");
                        }
                        if (jsonObject.has("image")) {
                            image = jsonObject.getString("image");
                        }
                        if (jsonObject.has("color")) {
                            color = jsonObject.getString("color");
                        }
                        if (jsonObject.has("dateOfBirth")) {
                            dateOfBirth = jsonObject.getString("dateOfBirth");
                        }
                        if (jsonObject.has("registrationDate")) {
                            registrationDate = jsonObject.getString("registrationDate");
                        }
                        if (jsonObject.has("category_name")) {
                            category_name = jsonObject.getString("category_name");
                        }
                        if (jsonObject.has("category_id")) {
                            category_id = jsonObject.getString("category_id");
                        }
                        if (jsonObject.has("specie_name")) {
                            specie_name = jsonObject.getString("specie_name");
                        }
                        if (jsonObject.has("specie")) {
                            specie = jsonObject.getString("specie");
                        }


                        petsBean.set_id(_id);
                        petsBean.setName(name);
                        petsBean.setCategory_id(category_id);
                        petsBean.setCategory_name(category_name);
                        petsBean.setImage(image);
                        petsBean.setColor(color);
                        petsBean.setDateOfBirth(dateOfBirth);
                        petsBean.setSpecie_name(specie_name);
                        petsBean.setSpecie(specie);
                        petsBean.setRegistrationDate(registrationDate);
                        petsBeans.add(petsBean);
                    }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return petsBeans;
    }
}
