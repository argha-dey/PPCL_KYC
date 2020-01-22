package com.cyberswift.healingtreeorg.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class HelloHealthPackageRecordResponseModel {
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

    public ArrayList<HelloHealthPackageRecord> getHelloHealthPackageRecords() {
        return helloHealthPackageRecords;
    }

    public void setHelloHealthPackageRecords(ArrayList<HelloHealthPackageRecord> helloHealthPackageRecords) {
        this.helloHealthPackageRecords = helloHealthPackageRecords;
    }

    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private boolean status;
    @SerializedName("data")
    private ArrayList<HelloHealthPackageRecord> helloHealthPackageRecords;
}
