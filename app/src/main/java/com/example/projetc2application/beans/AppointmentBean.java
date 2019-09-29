package com.example.projetc2application.beans;

import java.io.Serializable;

public class AppointmentBean implements Serializable {

    private boolean isSelected = false;
    private int procedureTime=0;
    private String name="";
    private String _id = "";


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getProcedureTime() {
        return procedureTime;
    }

    public void setProcedureTime(int procedureTime) {
        this.procedureTime = procedureTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
