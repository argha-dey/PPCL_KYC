package com.cyberswift.healingtree.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DoctorListDataModel {

    @SerializedName("MON")
    private ArrayList<DoctorListModel> mondayDrList;
    @SerializedName("TUE")
    private ArrayList<DoctorListModel> tuesdayDrList;
    @SerializedName("WED")
    private ArrayList<DoctorListModel> wednesdayDrList;
    @SerializedName("THU")
    private ArrayList<DoctorListModel> thursdayDrList;
    @SerializedName("FRI")
    private ArrayList<DoctorListModel> fridayDrList;
    @SerializedName("SAT")
    private ArrayList<DoctorListModel> saturdayDrList;

    public ArrayList<DoctorListModel> getMondayDrList() {
        return mondayDrList;
    }

    public void setMondayDrList(ArrayList<DoctorListModel> mondayDrList) {
        this.mondayDrList = mondayDrList;
    }

    public ArrayList<DoctorListModel> getTuesdayDrList() {
        return tuesdayDrList;
    }

    public void setTuesdayDrList(ArrayList<DoctorListModel> tuesdayDrList) {
        this.tuesdayDrList = tuesdayDrList;
    }

    public ArrayList<DoctorListModel> getWednesdayDrList() {
        return wednesdayDrList;
    }

    public void setWednesdayDrList(ArrayList<DoctorListModel> wednesdayDrList) {
        this.wednesdayDrList = wednesdayDrList;
    }

    public ArrayList<DoctorListModel> getThursdayDrList() {
        return thursdayDrList;
    }

    public void setThursdayDrList(ArrayList<DoctorListModel> thursdayDrList) {
        this.thursdayDrList = thursdayDrList;
    }

    public ArrayList<DoctorListModel> getFridayDrList() {
        return fridayDrList;
    }

    public void setFridayDrList(ArrayList<DoctorListModel> fridayDrList) {
        this.fridayDrList = fridayDrList;
    }

    public ArrayList<DoctorListModel> getSaturdayDrList() {
        return saturdayDrList;
    }

    public void setSaturdayDrList(ArrayList<DoctorListModel> saturdayDrList) {
        this.saturdayDrList = saturdayDrList;
    }
}
