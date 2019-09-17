package com.cyberswift.healingtree.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class HealthCareResponseModel {
    public ArrayList<THCA_Model> getTrainedHomeCareAttandanceModel() {
        return trainedHomeCareAttandanceModel;
    }

    public void setTrainedHomeCareAttandanceModel(ArrayList<THCA_Model> trainedHomeCareAttandanceModel) {
        this.trainedHomeCareAttandanceModel = trainedHomeCareAttandanceModel;
    }

    public ArrayList<HCAC_Model> getHomeCareAttandantChargesModel() {
        return homeCareAttandantChargesModel;
    }

    public void setHomeCareAttandantChargesModel(ArrayList<HCAC_Model> homeCareAttandantChargesModel) {
        this.homeCareAttandantChargesModel = homeCareAttandantChargesModel;
    }

    public ArrayList<SO_Model> getHomeCareAttandanceSpacialOffer() {
        return homeCareAttandanceSpacialOffer;
    }

    public void setHomeCareAttandanceSpacialOffer(ArrayList<SO_Model> homeCareAttandanceSpacialOffer) {
        this.homeCareAttandanceSpacialOffer = homeCareAttandanceSpacialOffer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @SerializedName("THCA")
    private ArrayList<THCA_Model> trainedHomeCareAttandanceModel = null;
    @SerializedName("HCAC")
    private ArrayList<HCAC_Model> homeCareAttandantChargesModel = null;
    @SerializedName("SO")
    private ArrayList<SO_Model> homeCareAttandanceSpacialOffer = null;
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private Boolean status;
}
