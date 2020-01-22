package com.cyberswift.healingtreeorg.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class HomeCareRecordResponceModel {
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private boolean status;
    @SerializedName("data")
    private ArrayList<HomeCareServiceRecordList> homeCareServiceRecordLists;

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

    public ArrayList<HomeCareServiceRecordList> getHomeCareServiceRecordLists() {
        return homeCareServiceRecordLists;
    }

    public void setHomeCareServiceRecordLists(ArrayList<HomeCareServiceRecordList> homeCareServiceRecordLists) {
        this.homeCareServiceRecordLists = homeCareServiceRecordLists;
    }



}
