package com.cyberswift.healingtree.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MedicineOrderDetailsResponseModel {
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private boolean status;

    public ArrayList<MedicineOrderDetails> getMedicineOrderDetails() {
        return medicineOrderDetails;
    }

    public void setMedicineOrderDetails(ArrayList<MedicineOrderDetails> medicineOrderDetails) {
        this.medicineOrderDetails = medicineOrderDetails;
    }

    @SerializedName("data")
    private ArrayList<MedicineOrderDetails> medicineOrderDetails;

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
