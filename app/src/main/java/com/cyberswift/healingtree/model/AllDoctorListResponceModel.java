package com.cyberswift.healingtree.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AllDoctorListResponceModel {
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private boolean status;
    @SerializedName("data")
    private ArrayList<DoctorListModel> doctorListModel;

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

    public ArrayList<DoctorListModel> getDoctorListModel() {
        return doctorListModel;
    }

    public void setDoctorListModel(ArrayList<DoctorListModel> doctorListModel) {
        this.doctorListModel = doctorListModel;
    }



}
