package com.cyberswift.healingtree.model;

import com.google.gson.annotations.SerializedName;

public class DoctorTimeslotResponceModel {
    public DoctorTimeSlotDayList getDoctorTimeSlotDayList() {
        return doctorTimeSlotDayList;
    }

    public void setDoctorTimeSlotDayList(DoctorTimeSlotDayList doctorTimeSlotDayList) {
        this.doctorTimeSlotDayList = doctorTimeSlotDayList;
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

    @SerializedName("data")
    private DoctorTimeSlotDayList doctorTimeSlotDayList;
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private Boolean status;
}
