package com.cyberswift.healingtree.model;

import com.google.gson.annotations.SerializedName;

public class HCAC_Model {


    @SerializedName("CGS_ID")
    private String homeCareAttandanceChargeId;
    @SerializedName("CGS_DURATION")
    private String homeCareAttandanceDuration;
    @SerializedName("CGS_CHARGES")
    private String homeCareAttandanceCharges;


    public String getHomeCareAttandanceChargeId() {
        return homeCareAttandanceChargeId;
    }

    public void setHomeCareAttandanceChargeId(String homeCareAttandanceChargeId) {
        this.homeCareAttandanceChargeId = homeCareAttandanceChargeId;
    }

    public String getHomeCareAttandanceDuration() {
        return homeCareAttandanceDuration;
    }

    public void setHomeCareAttandanceDuration(String homeCareAttandanceDuration) {
        this.homeCareAttandanceDuration = homeCareAttandanceDuration;
    }

    public String getHomeCareAttandanceCharges() {
        return homeCareAttandanceCharges;
    }

    public void setHomeCareAttandanceCharges(String homeCareAttandanceCharges) {
        this.homeCareAttandanceCharges = homeCareAttandanceCharges;
    }


}
