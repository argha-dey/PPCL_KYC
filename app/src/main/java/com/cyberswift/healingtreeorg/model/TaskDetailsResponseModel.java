package com.cyberswift.healingtreeorg.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TaskDetailsResponseModel {
    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private boolean status;

    @SerializedName("data")
    private ArrayList<TaskDetailsData> taskDetailsData;

    public ArrayList<TaskDetailsData> getTaskDetailsData() {
        return taskDetailsData;
    }

    public void setTaskDetailsData(ArrayList<TaskDetailsData> taskDetailsData) {
        this.taskDetailsData = taskDetailsData;
    }


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





}
