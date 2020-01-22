package com.cyberswift.healingtreeorg.model;

import com.google.gson.annotations.SerializedName;

public class TaskList {

    @SerializedName("RSV_ID")
    private String RSV_ID;

    @SerializedName("RSV_SERVICE_DATE")
    private String RSV_SERVICE_DATE;

    @SerializedName("RSV_ASSIGN_REMARKS")
    private String RSV_ASSIGN_REMARKS;

    @SerializedName("HHC_NAME")
    private String HHC_NAME;

    @SerializedName("Service_booking_date")
    private String Service_booking_date;

    @SerializedName("Service_assigned_date")
    private String Service_assigned_date;

    @SerializedName("patient_name")
    private String patient_name;

    @SerializedName("patient_mobile_number")
    private String patient_mobile_number;

    @SerializedName("services")
    private String services;


    public String getRSV_ID() {
        return RSV_ID;
    }

    public void setRSV_ID(String RSV_ID) {
        this.RSV_ID = RSV_ID;
    }

    public String getRSV_SERVICE_DATE() {
        return RSV_SERVICE_DATE;
    }

    public void setRSV_SERVICE_DATE(String RSV_SERVICE_DATE) {
        this.RSV_SERVICE_DATE = RSV_SERVICE_DATE;
    }

    public String getRSV_ASSIGN_REMARKS() {
        return RSV_ASSIGN_REMARKS;
    }

    public void setRSV_ASSIGN_REMARKS(String RSV_ASSIGN_REMARKS) {
        this.RSV_ASSIGN_REMARKS = RSV_ASSIGN_REMARKS;
    }

    public String getHHC_NAME() {
        return HHC_NAME;
    }

    public void setHHC_NAME(String HHC_NAME) {
        this.HHC_NAME = HHC_NAME;
    }

    public String getService_booking_date() {
        return Service_booking_date;
    }

    public void setService_booking_date(String service_booking_date) {
        Service_booking_date = service_booking_date;
    }

    public String getService_assigned_date() {
        return Service_assigned_date;
    }

    public void setService_assigned_date(String service_assigned_date) {
        Service_assigned_date = service_assigned_date;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getPatient_mobile_number() {
        return patient_mobile_number;
    }

    public void setPatient_mobile_number(String patient_mobile_number) {
        this.patient_mobile_number = patient_mobile_number;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }




}
