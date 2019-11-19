package com.example.projetc2application.beans;

import org.json.JSONObject;

import java.io.Serializable;

public class ProfileBean implements Serializable {

    private String _id = "";
    private String firstName = "";
    private String lastName = "";
    private String phoneNumber = "";
    private String emergencyPerson = "";
    private String emergencyNumber = "";
    private String registrationDate = "";
    private String username = "";
    private String image = "";


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmergencyPerson() {
        return emergencyPerson;
    }

    public void setEmergencyPerson(String emergencyPerson) {
        this.emergencyPerson = emergencyPerson;
    }

    public String getEmergencyNumber() {
        return emergencyNumber;
    }

    public void setEmergencyNumber(String emergencyNumber) {
        this.emergencyNumber = emergencyNumber;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static ProfileBean parseUser(String response) {
        ProfileBean userBean = new ProfileBean();
        if (response != null) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                 String _id = "";
                 String firstName = "";
                 String lastName = "";
                 String phoneNumber = "";
                 String emergencyPerson = "";
                 String emergencyNumber = "";
                 String registrationDate = "";
                 String username = "";
                 String image = "";
                if (jsonObject.has("_id")) {
                    _id = jsonObject.getString("_id");
                }
                if (jsonObject.has("profilePic")) {
                    image = jsonObject.getString("profilePic");
                }
                if (jsonObject.has("firstName")) {
                    firstName = jsonObject.getString("firstName");
                }
                if (jsonObject.has("lastName")) {
                    lastName = jsonObject.getString("lastName");
                }
                if (jsonObject.has("phoneNumber")) {
                    phoneNumber = jsonObject.getString("phoneNumber");
                }
                if (jsonObject.has("emergencyPerson")) {
                    emergencyPerson = jsonObject.getString("emergencyPerson");
                }
                if (jsonObject.has("emergencyNumber")) {
                    emergencyNumber = jsonObject.getString("emergencyNumber");
                }
                if (jsonObject.has("registrationDate")) {
                    registrationDate = jsonObject.getString("registrationDate");
                }

                userBean.set_id(_id);
                userBean.setFirstName(firstName);
                userBean.setUsername(username);
                userBean.setLastName(lastName);
                userBean.setPhoneNumber(phoneNumber);
                userBean.setEmergencyNumber(emergencyNumber);
                userBean.setEmergencyPerson(emergencyPerson);
                userBean.setRegistrationDate(registrationDate);
                userBean.setImage(image);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return userBean;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
