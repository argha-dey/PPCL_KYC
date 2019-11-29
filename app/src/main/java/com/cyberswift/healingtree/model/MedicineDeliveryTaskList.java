package com.cyberswift.healingtree.model;

import com.google.gson.annotations.SerializedName;

public class MedicineDeliveryTaskList {


    @SerializedName("PP_ID")
    private String PP_ID;

    @SerializedName("patient_name")
    private String patient_name;

    @SerializedName("patient_mobile_number")
    private String patient_mobile_number;

    @SerializedName("booking_date")
    private String booking_date;

    @SerializedName("assigned_date")
    private String assigned_date;

    @SerializedName("assigned_remarks")
    private String assigned_remarks;

    @SerializedName("patient_text_document")
    private String patient_text_document;



    public String getPP_ID() {
        return PP_ID;
    }

    public void setPP_ID(String PP_ID) {
        this.PP_ID = PP_ID;
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

    public String getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(String booking_date) {
        this.booking_date = booking_date;
    }

    public String getAssigned_date() {
        return assigned_date;
    }

    public void setAssigned_date(String assigned_date) {
        this.assigned_date = assigned_date;
    }

    public String getAssigned_remarks() {
        return assigned_remarks;
    }

    public void setAssigned_remarks(String assigned_remarks) {
        this.assigned_remarks = assigned_remarks;
    }

    public String getPatient_text_document() {
        return patient_text_document;
    }

    public void setPatient_text_document(String patient_text_document) {
        this.patient_text_document = patient_text_document;
    }


}
