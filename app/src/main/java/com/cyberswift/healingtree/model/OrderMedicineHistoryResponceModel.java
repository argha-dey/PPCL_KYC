package com.cyberswift.healingtree.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OrderMedicineHistoryResponceModel {
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private boolean status;
    @SerializedName("data")
    private ArrayList<OrderMedicineHistoryListModel> orderMedicineHistoryListModels;

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

    public ArrayList<OrderMedicineHistoryListModel> getOrderMedicineHistoryListModels() {
        return orderMedicineHistoryListModels;
    }

    public void setOrderMedicineHistoryListModels(ArrayList<OrderMedicineHistoryListModel> orderMedicineHistoryListModels) {
        this.orderMedicineHistoryListModels = orderMedicineHistoryListModels;
    }



}
