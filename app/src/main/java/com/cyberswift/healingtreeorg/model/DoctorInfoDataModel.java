package com.cyberswift.healingtreeorg.model;

import com.google.gson.annotations.SerializedName;

public class DoctorInfoDataModel {

    @SerializedName("MON")
    private DoctorListModel drInfoMonday;
    @SerializedName("TUE")
    private DoctorListModel drInfoTuesday;
    @SerializedName("WED")
    private DoctorListModel drInfoWednesday;
    @SerializedName("THU")
    private DoctorListModel drInfoThursday;
    @SerializedName("FRI")
    private DoctorListModel drInfoFriday;
    @SerializedName("SAT")
    private DoctorListModel drInfoSaturday;

    public DoctorListModel getDrInfoMonday() {
        return drInfoMonday;
    }

    public void setDrInfoMonday(DoctorListModel drInfoMonday) {
        this.drInfoMonday = drInfoMonday;
    }

    public DoctorListModel getDrInfoTuesday() {
        return drInfoTuesday;
    }

    public void setDrInfoTuesday(DoctorListModel drInfoTuesday) {
        this.drInfoTuesday = drInfoTuesday;
    }

    public DoctorListModel getDrInfoWednesday() {
        return drInfoWednesday;
    }

    public void setDrInfoWednesday(DoctorListModel drInfoWednesday) {
        this.drInfoWednesday = drInfoWednesday;
    }

    public DoctorListModel getDrInfoThursday() {
        return drInfoThursday;
    }

    public void setDrInfoThursday(DoctorListModel drInfoThursday) {
        this.drInfoThursday = drInfoThursday;
    }

    public DoctorListModel getDrInfoFriday() {
        return drInfoFriday;
    }

    public void setDrInfoFriday(DoctorListModel drInfoFriday) {
        this.drInfoFriday = drInfoFriday;
    }

    public DoctorListModel getDrInfoSaturday() {
        return drInfoSaturday;
    }

    public void setDrInfoSaturday(DoctorListModel drInfoSaturday) {
        this.drInfoSaturday = drInfoSaturday;
    }
}
