package com.cyberswift.healingtreeorg.model;

import java.util.ArrayList;

public class AppointmentListResponseModel {
    private boolean status;
    private String message;
    private ArrayList<AppointmentDataDo> data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<AppointmentDataDo> getData() {
        return data;
    }

    public void setData(ArrayList<AppointmentDataDo> data) {
        this.data = data;
    }
}
