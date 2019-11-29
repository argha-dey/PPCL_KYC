package com.cyberswift.healingtree.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MedicineDeliveryTaskListResponseModel {
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private boolean status;

    public ArrayList<MedicineDeliveryTaskList> getTaskList() {
        return taskList;
    }

    public void setTaskList(ArrayList<MedicineDeliveryTaskList> taskList) {
        this.taskList = taskList;
    }

    @SerializedName("data")
    private ArrayList<MedicineDeliveryTaskList> taskList;

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
