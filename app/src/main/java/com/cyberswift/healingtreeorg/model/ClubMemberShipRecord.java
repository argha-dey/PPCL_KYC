package com.cyberswift.healingtreeorg.model;

import com.google.gson.annotations.SerializedName;

public class ClubMemberShipRecord {

    @SerializedName("MMS_MEMBERSHIP_ID")
    private String MMS_MEMBERSHIP_ID;

    public String getMMS_MEMBERSHIP_ID() {
        return MMS_MEMBERSHIP_ID;
    }

    public void setMMS_MEMBERSHIP_ID(String MMS_MEMBERSHIP_ID) {
        this.MMS_MEMBERSHIP_ID = MMS_MEMBERSHIP_ID;
    }

    public String getMMS_CRT_TS() {
        return MMS_CRT_TS;
    }

    public void setMMS_CRT_TS(String MMS_CRT_TS) {
        this.MMS_CRT_TS = MMS_CRT_TS;
    }

    public String getMMS_STATUS() {
        return MMS_STATUS;
    }

    public void setMMS_STATUS(String MMS_STATUS) {
        this.MMS_STATUS = MMS_STATUS;
    }

    public String getMMC_FACILITY() {
        return MMC_FACILITY;
    }

    public void setMMC_FACILITY(String MMC_FACILITY) {
        this.MMC_FACILITY = MMC_FACILITY;
    }

    public String getMMC_SPAN() {
        return MMC_SPAN;
    }

    public void setMMC_SPAN(String MMC_SPAN) {
        this.MMC_SPAN = MMC_SPAN;
    }

    public String getMMC_AMOUNT() {
        return MMC_AMOUNT;
    }

    public void setMMC_AMOUNT(String MMC_AMOUNT) {
        this.MMC_AMOUNT = MMC_AMOUNT;
    }

    public String getMMC_PACKAGE() {
        return MMC_PACKAGE;
    }

    public void setMMC_PACKAGE(String MMC_PACKAGE) {
        this.MMC_PACKAGE = MMC_PACKAGE;
    }

    @SerializedName("MMS_CRT_TS")
    private String MMS_CRT_TS;

    @SerializedName("MMS_STATUS")
    private String MMS_STATUS;

    @SerializedName("MMC_FACILITY")
    private String MMC_FACILITY;

    @SerializedName("MMC_SPAN")
    private String MMC_SPAN;

    @SerializedName("MMC_AMOUNT")
    private String MMC_AMOUNT;

    @SerializedName("MMC_PACKAGE")
    private String MMC_PACKAGE;




}
