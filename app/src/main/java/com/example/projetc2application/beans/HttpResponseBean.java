package com.example.projetc2application.beans;

import java.io.Serializable;

/**
 * Created by User on 1/29/2018.
 */

public class HttpResponseBean implements Serializable {

    private String response;
    private int status;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
