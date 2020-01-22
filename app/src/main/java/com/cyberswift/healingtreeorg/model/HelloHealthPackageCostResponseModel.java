package com.cyberswift.healingtreeorg.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class HelloHealthPackageCostResponseModel {
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

    public ArrayList<HelloHealthPackageCost> getPackageCost() {
        return packageCost;
    }

    public void setPackageCost(ArrayList<HelloHealthPackageCost> packageCost) {
        this.packageCost = packageCost;
    }

    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private boolean status;
    @SerializedName("hello_health_categorywise_cost")
    private ArrayList<HelloHealthPackageCost> packageCost;
}
