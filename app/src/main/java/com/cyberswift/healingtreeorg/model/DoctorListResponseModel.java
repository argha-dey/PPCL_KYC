package com.cyberswift.healingtreeorg.model;

import com.google.gson.annotations.SerializedName;

public class DoctorListResponseModel {

    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private boolean status;
    @SerializedName("data")
    private DoctorListDataModel doctorListModel;

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

    public DoctorListDataModel getDoctorListModel() {
        return doctorListModel;
    }

    public void setDoctorListModel(DoctorListDataModel doctorListModel) {
        this.doctorListModel = doctorListModel;
    }

}
