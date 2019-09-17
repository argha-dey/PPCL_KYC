package com.cyberswift.healingtree.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserRegisterResponseModel {
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


    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private boolean status;
    @SerializedName("data")
    private ArrayList<UserRegisterData> data = null;


    public ArrayList<UserRegisterData> getData() {
        return data;
    }

    public void setData(ArrayList<UserRegisterData> data) {
        this.data = data;
    }


}
