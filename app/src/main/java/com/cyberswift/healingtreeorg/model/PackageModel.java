package com.cyberswift.healingtreeorg.model;

import com.google.gson.annotations.SerializedName;

public class PackageModel {
    public String getHLO_ID() {
        return HLO_ID;
    }

    public void setHLO_ID(String HLO_ID) {
        this.HLO_ID = HLO_ID;
    }

    public String getHLO_PACKAGE_NAME() {
        return HLO_PACKAGE_NAME;
    }

    public void setHLO_PACKAGE_NAME(String HLO_PACKAGE_NAME) {
        this.HLO_PACKAGE_NAME = HLO_PACKAGE_NAME;
    }

    @SerializedName("HLO_ID")
    private String HLO_ID;
    @SerializedName("HLO_PACKAGE_NAME")
    private String HLO_PACKAGE_NAME;
}
