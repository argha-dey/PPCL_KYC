package com.cyberswift.healingtree.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class TaskDetailsData {

    @SerializedName("RSV_ID")
    private String RSV_ID;
    @SerializedName("RSV_SERVICE_DATE")
    private String RSV_SERVICE_DATE;
    @SerializedName("RSV_ASSIGN_REMARKS")
    private String RSV_ASSIGN_REMARKS;
    @SerializedName("HHC_NAME")
    private String HHC_NAME;
    @SerializedName("service_start_date")
    private String service_start_date;
    @SerializedName("service_end_date")
    private String service_end_date;
    @SerializedName("Service_booking_date")
    private String Service_booking_date;
    @SerializedName("Service_assigned_date")
    private String Service_assigned_date;
    @SerializedName("booked_user_name")
    private String booked_user_name;
    @SerializedName("booked_user_address")
    private String booked_user_address;
    @SerializedName("services")
    private String services;
    @SerializedName("task_date_list")
    private ArrayList<TaskDateListAttendance> taskDateListForAttendance;

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

    public String getService_start_date() {
        return service_start_date;
    }

    public void setService_start_date(String service_start_date) {
        this.service_start_date = service_start_date;
    }

    public String getService_end_date() {
        return service_end_date;
    }

    public void setService_end_date(String service_end_date) {
        this.service_end_date = service_end_date;
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

    public String getBooked_user_name() {
        return booked_user_name;
    }

    public void setBooked_user_name(String booked_user_name) {
        this.booked_user_name = booked_user_name;
    }

    public String getBooked_user_address() {
        return booked_user_address;
    }

    public void setBooked_user_address(String booked_user_address) {
        this.booked_user_address = booked_user_address;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public ArrayList<TaskDateListAttendance> getTaskDateListForAttendance() {
        return taskDateListForAttendance;
    }

    public void setTaskDateListForAttendance(ArrayList<TaskDateListAttendance> taskDateListForAttendance) {
        this.taskDateListForAttendance = taskDateListForAttendance;
    }


}
