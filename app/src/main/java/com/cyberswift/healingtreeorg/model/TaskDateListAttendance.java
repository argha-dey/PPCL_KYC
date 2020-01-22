package com.cyberswift.healingtreeorg.model;

import com.google.gson.annotations.SerializedName;

public class TaskDateListAttendance {

    @SerializedName("SRD_ID")
    private String SRD_ID;

    public String getSRD_ID() {
        return SRD_ID;
    }

    public void setSRD_ID(String SRD_ID) {
        this.SRD_ID = SRD_ID;
    }

    public String getSRD_RSV_ID() {
        return SRD_RSV_ID;
    }

    public void setSRD_RSV_ID(String SRD_RSV_ID) {
        this.SRD_RSV_ID = SRD_RSV_ID;
    }

    public String getSRD_SERVICE_DATE() {
        return SRD_SERVICE_DATE;
    }

    public void setSRD_SERVICE_DATE(String SRD_SERVICE_DATE) {
        this.SRD_SERVICE_DATE = SRD_SERVICE_DATE;
    }

    public String getSRD_SERVICE_STATUS() {
        return SRD_SERVICE_STATUS;
    }

    public void setSRD_SERVICE_STATUS(String SRD_SERVICE_STATUS) {
        this.SRD_SERVICE_STATUS = SRD_SERVICE_STATUS;
    }

    @SerializedName("SRD_RSV_ID")
    private String SRD_RSV_ID;
    @SerializedName("SRD_SERVICE_DATE")
    private String SRD_SERVICE_DATE;
    @SerializedName("SRD_SERVICE_STATUS")
    private String SRD_SERVICE_STATUS;

    @SerializedName("task_start_active")
    private boolean task_start_active;

    public boolean isTask_start_active() {
        return task_start_active;
    }

    public void setTask_start_active(boolean task_start_active) {
        this.task_start_active = task_start_active;
    }

    public boolean isTask_end_active() {
        return task_end_active;
    }

    public void setTask_end_active(boolean task_end_active) {
        this.task_end_active = task_end_active;
    }

    public boolean isTask_date_active() {
        return task_date_active;
    }

    public void setTask_date_active(boolean task_date_active) {
        this.task_date_active = task_date_active;
    }

    @SerializedName("task_end_active")
    private boolean task_end_active;
    @SerializedName("task_date_active")
    private boolean task_date_active;



}
