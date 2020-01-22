package com.cyberswift.healingtreeorg.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PrescriptionUploadResponceModel {

    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private boolean status;
    @SerializedName("data")
    private ArrayList<PrescriptionListDataModel> PrescriptionListData;


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

    public ArrayList<PrescriptionListDataModel> getPrescriptionListData() {
        return PrescriptionListData;
    }

    public void setPrescriptionListData(ArrayList<PrescriptionListDataModel> prescriptionListData) {
        PrescriptionListData = prescriptionListData;
    }


}
