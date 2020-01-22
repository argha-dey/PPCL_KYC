package com.cyberswift.healingtreeorg.model;

import com.google.gson.annotations.SerializedName;

public class DoctorinfoResponseModel {

    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private boolean status;
    @SerializedName("data")
    private DoctorInfoDataModel doctorInfoDataModel;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public DoctorInfoDataModel getDoctorInfoDataModel() {
        return doctorInfoDataModel;
    }

    public void setDoctorInfoDataModel(DoctorInfoDataModel doctorInfoDataModel) {
        this.doctorInfoDataModel = doctorInfoDataModel;
    }
}
