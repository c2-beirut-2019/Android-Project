package com.example.projetc2application.beans;

import java.io.Serializable;

public class AppointmentUserBean implements Serializable {

    private String _id = "";
    private String pet_image = "";
    private String doctor_firstName = "";
    private String doctor_lastName = "";
    private String doctor_speciality = "";
    private String pet_name = "";
    private String appointmentType_name = "";
    private String startDate = "";


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDoctor_firstName() {
        return doctor_firstName;
    }

    public void setDoctor_firstName(String doctor_firstName) {
        this.doctor_firstName = doctor_firstName;
    }

    public String getDoctor_lastName() {
        return doctor_lastName;
    }

    public void setDoctor_lastName(String doctor_lastName) {
        this.doctor_lastName = doctor_lastName;
    }

    public String getDoctor_speciality() {
        return doctor_speciality;
    }

    public void setDoctor_speciality(String doctor_speciality) {
        this.doctor_speciality = doctor_speciality;
    }

    public String getPet_name() {
        return pet_name;
    }

    public void setPet_name(String pet_name) {
        this.pet_name = pet_name;
    }

    public String getAppointmentType_name() {
        return appointmentType_name;
    }

    public void setAppointmentType_name(String appointmentType_name) {
        this.appointmentType_name = appointmentType_name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getPet_image() {
        return pet_image;
    }

    public void setPet_image(String pet_image) {
        this.pet_image = pet_image;
    }
}
