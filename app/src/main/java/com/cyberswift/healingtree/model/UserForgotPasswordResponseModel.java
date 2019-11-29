package com.cyberswift.healingtree.model;

import com.google.gson.annotations.SerializedName;

public class UserForgotPasswordResponseModel {
    @SerializedName("status")
    private Boolean status;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    @SerializedName("message")
    private String message;
}
