package com.example.projetc2application.utils;

public class GlobalVars {

    //apis --START--
    public static String BASE_URL = "http://34.247.71.103:4444";

    public static String LOGIN_URL = "/client/authenticate";
    public static String LOGIN_DOCTOR_URL = "/doctor/authenticate";
    public static String VERIFY_CODE_URL = "/client/code";
    public static String VERIFY_CODE__DOCTOR_URL = "/doctor/code";
    public static String SET_USERNAME_PASS_URL = "/client/username";
    public static String SET_USERNAME_PASS_DOCTOR_URL = "/doctor/username";
    public static String GET_APPOINTMENT_TYPES_URL = "/appointmentType";
    public static String GET_PETS_USER_URL = "/pet/client";
    public static String GET_DOCTORS_URL = "/doctor/list";
    public static String GET_APPOINTMENTS_USER_URL = "/appointment/userAppointment?";
    public static String GET_APPOINTMENTS_DOCTOR_URL = "/appointment/doctorAppointment?user=5d52f85cb55127289caf493e&appointmentType=5d530577bab06518408f2f1d&pet=5d53066814868b116c591746";
    public static String GET_NEWS_URL = "/news?page=1&limit=10";
    public static String GET_PRODUCTS_URL = "/product?page=1&limit=10&sortBy=price&sortOrder=desc";
    public static String GET_USER_APPOINTMENT_URL = "/appointment/userAppointment?";
    public static String GET_DOCTOR_APPOINTMENT_URL = "/appointment/doctorAppointment?user=5d52f85cb55127289caf493e&appointmentType=5d530577bab06518408f2f1d&pet=5d53066814868b116c591746";
    public static String GET_PETS_TO_ADOPT_URL = "/pet/toAdopt?page=1&limit=10&sortBy=name&sortOrder=desc";

    //apis --END--

    //BUNDLE --START--
    public static String CODE_BUNDLE = "CODE";
    public static String IS_DOCTOR_BUNDLE = "IS_DOCTOR_BUNDLE";
    public static String NEWS_BEAN_BUNDLE = "NEWS_BEAN";
    public static String PRODUCTS_BEAN_BUNDLE = "PRODUCTS_BEAN";
    public static String PETS_BEAN_BUNDLE = "PETS_BEAN";

    //BUNDLE --END--

    //REQUEST CODES --START--

    //REQUEST CODES --END--

    //Others
    public static final String LOG_TAG = "ProjetC2Log";


    public static boolean IS_DEBUG;
    public static boolean IS_USER = true;
}
