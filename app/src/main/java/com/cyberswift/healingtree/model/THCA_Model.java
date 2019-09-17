package com.cyberswift.healingtree.model;

import com.google.gson.annotations.SerializedName;

public class THCA_Model {

    @SerializedName("HCS_ID")
    private String homeCareServiceId;
    @SerializedName("HCS_NAME")
    private String homeCareServiceName;
    @SerializedName("HCS_DESCRIPTION")
    private String homeCareServiceDescribtion;

    private boolean isChecked = false;


    public String getHomeCareServiceId() {
        return homeCareServiceId;
    }

    public void setHomeCareServiceId(String homeCareServiceId) {
        this.homeCareServiceId = homeCareServiceId;
    }

    public String getHomeCareServiceName() {
        return homeCareServiceName;
    }

    public void setHomeCareServiceName(String homeCareServiceName) {
        this.homeCareServiceName = homeCareServiceName;
    }

    public String getHomeCareServiceDescribtion() {
        return homeCareServiceDescribtion;
    }

    public void setHomeCareServiceDescribtion(String homeCareServiceDescribtion) {
        this.homeCareServiceDescribtion = homeCareServiceDescribtion;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }


}
