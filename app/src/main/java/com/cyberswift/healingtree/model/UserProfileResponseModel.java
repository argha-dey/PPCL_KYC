package com.cyberswift.healingtree.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserProfileResponseModel {
    @SerializedName("message")
    private String message;

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



    @SerializedName("status")
    private boolean status;

    public ArrayList<UserDetailsModel> getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(ArrayList<UserDetailsModel> userDetails) {
        this.userDetails = userDetails;
    }

    @SerializedName("data")
    private ArrayList<UserDetailsModel> userDetails;


}
