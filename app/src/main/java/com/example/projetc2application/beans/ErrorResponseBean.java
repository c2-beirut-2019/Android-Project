package com.example.projetc2application.beans;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by User on 1/29/2018.
 */

public class ErrorResponseBean implements Serializable {

    private String message="";
    private int status=0;

    public String getResponse() {
        return message;
    }

    public void setResponse(String response) {
        this.message = response;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static ErrorResponseBean parseError(String response){
        ErrorResponseBean errorResponseBean = new ErrorResponseBean();
        try {
            JSONObject jsonObject = new JSONObject(response);
            if(jsonObject.has("status")){
                errorResponseBean.setStatus(jsonObject.getInt("status"));
            }
            if(jsonObject.has("message")){
                errorResponseBean.setResponse(jsonObject.getString("message"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return errorResponseBean;
    }
}
