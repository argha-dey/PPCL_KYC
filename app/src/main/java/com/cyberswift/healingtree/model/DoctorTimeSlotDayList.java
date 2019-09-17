package com.cyberswift.healingtree.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DoctorTimeSlotDayList {


    @SerializedName("MON")
    private ArrayList<DoctorListModel> MON;
    @SerializedName("TUE")
    private ArrayList<DoctorListModel> TUE;
    @SerializedName("WED")
    private ArrayList<DoctorListModel> WED;
    @SerializedName("THU")
    private ArrayList<DoctorListModel> THU;
    @SerializedName("FRI")
    private ArrayList<DoctorListModel> FRI;
    @SerializedName("SAT")
    private ArrayList<DoctorListModel> SAT;

    public ArrayList<TabDateModel> getTabDateList() {
        return tabDateList;
    }

    public void setTabDateList(ArrayList<TabDateModel> tabDateList) {
        this.tabDateList = tabDateList;
    }

    @SerializedName("date")
    private ArrayList<TabDateModel> tabDateList;


    public ArrayList<DoctorListModel> getMON() {
        return MON;
    }

    public void setMON(ArrayList<DoctorListModel> MON) {
        this.MON = MON;
    }

    public ArrayList<DoctorListModel> getTUE() {
        return TUE;
    }

    public void setTUE(ArrayList<DoctorListModel> TUE) {
        this.TUE = TUE;
    }

    public ArrayList<DoctorListModel> getWED() {
        return WED;
    }

    public void setWED(ArrayList<DoctorListModel> WED) {
        this.WED = WED;
    }

    public ArrayList<DoctorListModel> getTHU() {
        return THU;
    }

    public void setTHU(ArrayList<DoctorListModel> THU) {
        this.THU = THU;
    }

    public ArrayList<DoctorListModel> getFRI() {
        return FRI;
    }

    public void setFRI(ArrayList<DoctorListModel> FRI) {
        this.FRI = FRI;
    }

    public ArrayList<DoctorListModel> getSAT() {
        return SAT;
    }

    public void setSAT(ArrayList<DoctorListModel> SAT) {
        this.SAT = SAT;
    }


}
