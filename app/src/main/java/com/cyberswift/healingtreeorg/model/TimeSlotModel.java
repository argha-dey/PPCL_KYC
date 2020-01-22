package com.cyberswift.healingtreeorg.model;

import com.google.gson.annotations.SerializedName;

public class TimeSlotModel {

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @SerializedName("time")
    private String time = null;

    @SerializedName("type")
    private String type = null;
}
