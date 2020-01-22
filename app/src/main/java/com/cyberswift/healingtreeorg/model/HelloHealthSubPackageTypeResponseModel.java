package com.cyberswift.healingtreeorg.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class HelloHealthSubPackageTypeResponseModel {

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

    public ArrayList<HelloSubPackageModel> getSubPackageDetails() {
        return subPackageDetails;
    }

    public void setSubPackageDetails(ArrayList<HelloSubPackageModel> subPackageDetails) {
        this.subPackageDetails = subPackageDetails;
    }

    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private boolean status;
    @SerializedName("hello_health_package_type")
    private ArrayList<HelloSubPackageModel> subPackageDetails;
}
