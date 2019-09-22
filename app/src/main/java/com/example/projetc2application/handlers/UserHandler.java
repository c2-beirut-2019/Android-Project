package com.example.projetc2application.handlers;

import com.example.projetc2application.beans.NewsBean;
import com.example.projetc2application.beans.UserBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class UserHandler implements Serializable {

    public static UserBean parseUser(String response) {
        UserBean userBean = new UserBean();
        if (response != null) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String firstName = "";
                String lastName = "";
                String username = "";
                String _id = "";
                String access_token = "";
                String expires_in = "";
                String refresh_token = "";
                String refresh_token_header = "";
                if (jsonObject.has("_id")) {
                    _id = jsonObject.getString("_id");
                }
                if (jsonObject.has("firstName")) {
                    firstName = jsonObject.getString("firstName");
                }
                if (jsonObject.has("lastName")) {
                    lastName = jsonObject.getString("lastName");
                }
                if (jsonObject.has("access_token")) {
                    access_token = jsonObject.getString("access_token");
                }
                if (jsonObject.has("expires_in")) {
                    expires_in = jsonObject.getString("expires_in");
                }
                if (jsonObject.has("refresh_token")) {
                    refresh_token = jsonObject.getString("refresh_token");
                }
                if (jsonObject.has("refresh_token_header")) {
                    refresh_token_header = jsonObject.getString("refresh_token_header");
                }

                userBean.set_id(_id);
                userBean.setFirstName(firstName);
                userBean.setUsername(username);
                userBean.setLastName(lastName);
                userBean.setAccess_token(access_token);
                userBean.setRefresh_token_header(refresh_token_header);
                userBean.setRefresh_token(refresh_token);
                userBean.setExpires_in(expires_in);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return userBean;
    }

}
