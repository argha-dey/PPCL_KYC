package com.cyberswift.healingtreeorg.model;

import com.google.gson.annotations.SerializedName;

public class FeedBackResponseModel {
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @SerializedName("status")
    private boolean status;

    @SerializedName("data")
    private String  data;


}
