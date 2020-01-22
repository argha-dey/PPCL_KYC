package com.cyberswift.healingtreeorg.model;

import com.google.gson.annotations.SerializedName;

public class HomeCareServiceRecordList {

    @SerializedName("RSV_ID")
    private String RSV_ID;

    @SerializedName("RSV_CRT_TS")
    private String RSV_CRT_TS;

    @SerializedName("RSV_CARE_OFFER")
    private String RSV_CARE_OFFER;


    public String getRSV_ID() {
        return RSV_ID;
    }

    public void setRSV_ID(String RSV_ID) {
        this.RSV_ID = RSV_ID;
    }

    public String getRSV_CRT_TS() {
        return RSV_CRT_TS;
    }

    public void setRSV_CRT_TS(String RSV_CRT_TS) {
        this.RSV_CRT_TS = RSV_CRT_TS;
    }

    public String getRSV_CARE_OFFER() {
        return RSV_CARE_OFFER;
    }

    public void setRSV_CARE_OFFER(String RSV_CARE_OFFER) {
        this.RSV_CARE_OFFER = RSV_CARE_OFFER;
    }

    public String getRSV_STATUS() {
        return RSV_STATUS;
    }

    public void setRSV_STATUS(String RSV_STATUS) {
        this.RSV_STATUS = RSV_STATUS;
    }

    public String getRSV_TOTAL_AMOUNT() {
        return RSV_TOTAL_AMOUNT;
    }

    public void setRSV_TOTAL_AMOUNT(String RSV_TOTAL_AMOUNT) {
        this.RSV_TOTAL_AMOUNT = RSV_TOTAL_AMOUNT;
    }

    public String getRSV_PAYMENT_STATUS() {
        return RSV_PAYMENT_STATUS;
    }

    public void setRSV_PAYMENT_STATUS(String RSV_PAYMENT_STATUS) {
        this.RSV_PAYMENT_STATUS = RSV_PAYMENT_STATUS;
    }

    public String getHHC_NAME() {
        return HHC_NAME;
    }

    public void setHHC_NAME(String HHC_NAME) {
        this.HHC_NAME = HHC_NAME;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    @SerializedName("RSV_STATUS")
    private String RSV_STATUS;

    @SerializedName("RSV_TOTAL_AMOUNT")
    private String RSV_TOTAL_AMOUNT;

    @SerializedName("RSV_PAYMENT_STATUS")
    private String RSV_PAYMENT_STATUS;

    @SerializedName("HHC_NAME")
    private String HHC_NAME;

    @SerializedName("services")
    private String services;


}
