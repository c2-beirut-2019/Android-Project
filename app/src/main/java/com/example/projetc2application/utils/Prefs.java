package com.example.projetc2application.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {

    private static SharedPreferences prefs;
    private static Prefs mPrefs;
    Activity activity;
    private static final String PREF_KEY = "MyPreferences";
    static final String PREF_NAME = "MyPreferences";
    static final String PREF_ISLOGGEDIN = "IsLoggedIn";
    static final String PREF_ACCESSTOKEN = "user_accesstoken";
    static final String PREF_USERNAME = "user_username";
    static final String PREF_FULLNAME = "user_fullname";
    static final String PREF_EMAIL = "user_email";
    static final String PREF_EXPIRES = "user_expires_in";
    static final String PREF_REFRESH_TOKEN = "user_refresh_token";
    static final String PREF_REFRESH_TOKEN_HEADER = "user_refresh_token_header";
    static final String PREF_IMAGE = "user_image";
    static final String IS_PROFILE_CHECKED = "is_profile";
    static final String PREF_USER = "user_user";
    static final String PREF_PASSWORD = "user_password";
    static final String PREF_SOCKET_TOKEN = "user_socket_token";
    static final String PREF_USER_ID = "user_id";

    public static void clearPrefVar() {
        mPrefs = null;
    }

    public static Prefs getInstance(Activity activity) {
        if (mPrefs == null)
            synchronized (Prefs.class) {
                if (mPrefs == null)
                    mPrefs = new Prefs(activity);
            }
        return mPrefs;

    }

    public static Prefs getInstance(Context context) {
        if (mPrefs == null)
            synchronized (Prefs.class) {
                if (mPrefs == null)
                    mPrefs = new Prefs(context);
            }
        return mPrefs;

    }

    public Prefs(Activity activity) {
        this.activity = activity;
        prefs = activity.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
    }

    public Prefs(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
    }


      public void setIsProfileChecked(boolean isProfileChecked){
        prefs.edit().putBoolean(IS_PROFILE_CHECKED, isProfileChecked).apply();
    }

    public boolean isProfileChecked(){
        return prefs.getBoolean(IS_PROFILE_CHECKED,false);
    }


    public void setIsLoggedIn(boolean isLoggedIn) {
        prefs.edit().putBoolean(PREF_ISLOGGEDIN, isLoggedIn).apply();
    }


    public boolean getIsLoggedIn() {
        return prefs.getBoolean(PREF_ISLOGGEDIN, false);
    }


    public void removeAllLoginCreds() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(PREF_ISLOGGEDIN);
        editor.apply();
    }

    public String getCustomPref(String key) {
        return prefs.getString(key, "");
    }

    public void setCustomPref(String key, String value) {
        prefs.edit().putString(key, value).apply();
    }



    public String getAccessToken() {
        return prefs.getString(PREF_ACCESSTOKEN, "");
    }

    public void setAccessToken(String st) {
        prefs.edit().putString(PREF_ACCESSTOKEN, st).apply();
    }

    public String getSocketToken() {
        return prefs.getString(PREF_SOCKET_TOKEN, "");
    }

    public void setSocketToken(String st) {
        prefs.edit().putString(PREF_SOCKET_TOKEN, st).apply();
    }
    public String getUserId() {
        return prefs.getString(PREF_USER_ID, "");
    }

    public void setUserId(String st) {
        prefs.edit().putString(PREF_USER_ID, st).apply();
    }

    public String getExpires() {
        return prefs.getString(PREF_EXPIRES, "");
    }

    public void setExpires(String st) {
        prefs.edit().putString(PREF_EXPIRES, st).apply();
    }

    public String getRefreshToken() {
        return prefs.getString(PREF_REFRESH_TOKEN, "");
    }

    public void setRefreshToken(String st) {
        prefs.edit().putString(PREF_REFRESH_TOKEN, st).apply();
    }

    public String getRefreshTokenHeader() {
        return prefs.getString(PREF_REFRESH_TOKEN_HEADER, "");
    }

    public void setRefreshTokenHeader(String st) {
        prefs.edit().putString(PREF_REFRESH_TOKEN_HEADER, st).apply();
    }

    public String getUsername() {
        return prefs.getString(PREF_USERNAME, "");
    }

    public void setUsername(String st) {
        prefs.edit().putString(PREF_USERNAME, st).apply();
    }

    public String getUser() {
        return prefs.getString(PREF_USER, "");
    }

    public void setUser(String st) {
        prefs.edit().putString(PREF_USER, st).apply();
    }

    public String getPassword() {
        return prefs.getString(PREF_PASSWORD, "");
    }

    public void setPassword(String st) {
        prefs.edit().putString(PREF_PASSWORD, st).apply();
    }

    public String getEmail() {
        return prefs.getString(PREF_EMAIL, "");
    }

    public void setEmail(String st) {
        prefs.edit().putString(PREF_EMAIL, st).apply();
    }

    public String getImage() {
        return prefs.getString(PREF_IMAGE, "");
    }

    public void setImage(String st) {
        prefs.edit().putString(PREF_IMAGE, st).apply();
    }

    public String getFullName() {
        return prefs.getString(PREF_FULLNAME, "");
    }

    public void setFullName(String st) {
        prefs.edit().putString(PREF_FULLNAME, st).apply();
    }


}