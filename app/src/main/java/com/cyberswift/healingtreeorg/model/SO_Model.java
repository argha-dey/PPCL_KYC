package com.cyberswift.healingtreeorg.model;

import com.google.gson.annotations.SerializedName;

public class SO_Model {
    @SerializedName("SOF_ID")
    private String homeCareServiceSpacialId;
    @SerializedName("SOF_DURATION")
    private String homeCareServiceSpacialDuration;
    @SerializedName("SOF_CHARGES")
    private String homeCareServiceSpacialCharges;


    public String getHomeCareServiceSpacialId() {
        return homeCareServiceSpacialId;
    }

    public void setHomeCareServiceSpacialId(String homeCareServiceSpacialId) {
        this.homeCareServiceSpacialId = homeCareServiceSpacialId;
    }

    public String getHomeCareServiceSpacialDuration() {
        return homeCareServiceSpacialDuration;
    }

    public void setHomeCareServiceSpacialDuration(String homeCareServiceSpacialDuration) {
        this.homeCareServiceSpacialDuration = homeCareServiceSpacialDuration;
    }

    public String getHomeCareServiceSpacialCharges() {
        return homeCareServiceSpacialCharges;
    }

    public void setHomeCareServiceSpacialCharges(String homeCareServiceSpacialCharges) {
        this.homeCareServiceSpacialCharges = homeCareServiceSpacialCharges;
    }

}
