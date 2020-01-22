package com.cyberswift.healingtreeorg.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class HelloHealthPackageResponseModel {


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

    public ArrayList<PackageModel> getData() {
        return data;
    }

    public void setData(ArrayList<PackageModel> data) {
        this.data = data;
    }

    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private boolean status;
    @SerializedName("data")
    private ArrayList<PackageModel> data;

}
