package com.example.projetc2application.beans;

import java.io.Serializable;

public class UserBean implements Serializable {

    private String firstName = "";
    private String lastName = "";
    private String username = "";
    private String _id = "";
    private String access_token = "";
    private String expires_in = "";
    private String refresh_token = "";
    private String refresh_token_header = "";


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getRefresh_token_header() {
        return refresh_token_header;
    }

    public void setRefresh_token_header(String refresh_token_header) {
        this.refresh_token_header = refresh_token_header;
    }
}
