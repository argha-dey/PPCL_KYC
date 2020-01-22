package com.cyberswift.healingtreeorg.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserLoginResponseModel {
    @SerializedName("status")
    private Boolean status;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ArrayList<UserLoginModel> getData() {
        return data;
    }

    public void setData(ArrayList<UserLoginModel> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @SerializedName("data")
    private ArrayList<UserLoginModel> data = null;
    @SerializedName("message")
    private String message;
}
