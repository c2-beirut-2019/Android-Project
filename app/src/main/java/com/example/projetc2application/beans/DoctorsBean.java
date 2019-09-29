package com.example.projetc2application.beans;

import java.io.Serializable;
import java.util.ArrayList;

public class DoctorsBean implements Serializable {

    private String _id = "";
    private String diplomas = "";
    private String firstName = "";
    private String fullName = "";
    private String lastName = "";
    private String speciality = "";


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDiplomas() {
        return diplomas;
    }

    public void setDiplomas(String diplomas) {
        this.diplomas = diplomas;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
}
