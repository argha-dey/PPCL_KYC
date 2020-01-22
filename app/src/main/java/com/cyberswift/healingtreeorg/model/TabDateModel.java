package com.cyberswift.healingtreeorg.model;

import com.google.gson.annotations.SerializedName;

public class TabDateModel {
    @SerializedName("day")
    private String t_Day;

    public String getT_Day() {
        return t_Day;
    }

    public void setT_Day(String t_Day) {
        this.t_Day = t_Day;
    }

    public String getT_Date() {
        return t_Date;
    }

    public void setT_Date(String t_Date) {
        this.t_Date = t_Date;
    }

    @SerializedName("date")
    private String t_Date;
}
