package com.cyberswift.healingtreeorg.model;

import com.google.gson.annotations.SerializedName;

public class HelloHealthPackageRecord {
    @SerializedName("HHS_ID")
    private String HHS_ID;

    public String getHHS_ID() {
        return HHS_ID;
    }

    public void setHHS_ID(String HHS_ID) {
        this.HHS_ID = HHS_ID;
    }

    public String getHHS_CRT_TS() {
        return HHS_CRT_TS;
    }

    public void setHHS_CRT_TS(String HHS_CRT_TS) {
        this.HHS_CRT_TS = HHS_CRT_TS;
    }

    public String getHHS_PACKAGE_TYPE() {
        return HHS_PACKAGE_TYPE;
    }

    public void setHHS_PACKAGE_TYPE(String HHS_PACKAGE_TYPE) {
        this.HHS_PACKAGE_TYPE = HHS_PACKAGE_TYPE;
    }

    public String getHHS_COST() {
        return HHS_COST;
    }

    public void setHHS_COST(String HHS_COST) {
        this.HHS_COST = HHS_COST;
    }

    public String getHHS_STATUS() {
        return HHS_STATUS;
    }

    public void setHHS_STATUS(String HHS_STATUS) {
        this.HHS_STATUS = HHS_STATUS;
    }

    public String getHHS_SUB_PACKAGE_TYPE() {
        return HHS_SUB_PACKAGE_TYPE;
    }

    public void setHHS_SUB_PACKAGE_TYPE(String HHS_SUB_PACKAGE_TYPE) {
        this.HHS_SUB_PACKAGE_TYPE = HHS_SUB_PACKAGE_TYPE;
    }

    public String getHLO_PACKAGE_NAME() {
        return HLO_PACKAGE_NAME;
    }

    public void setHLO_PACKAGE_NAME(String HLO_PACKAGE_NAME) {
        this.HLO_PACKAGE_NAME = HLO_PACKAGE_NAME;
    }

    @SerializedName("HHS_CRT_TS")
    private String HHS_CRT_TS;

    @SerializedName("HHS_PACKAGE_TYPE")
    private String HHS_PACKAGE_TYPE;

    @SerializedName("HHS_COST")
    private String HHS_COST;

    @SerializedName("HHS_STATUS")
    private String HHS_STATUS;

    @SerializedName("HHS_SUB_PACKAGE_TYPE")
    private String HHS_SUB_PACKAGE_TYPE;

    @SerializedName("HLO_PACKAGE_NAME")
    private String HLO_PACKAGE_NAME;
}
