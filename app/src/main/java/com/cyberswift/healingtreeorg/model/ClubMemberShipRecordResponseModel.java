package com.cyberswift.healingtreeorg.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ClubMemberShipRecordResponseModel {
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }



    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private boolean status;

    public ArrayList<ClubMemberShipRecord> getClubMemberShipRecord() {
        return clubMemberShipRecord;
    }

    public void setClubMemberShipRecord(ArrayList<ClubMemberShipRecord> clubMemberShipRecord) {
        this.clubMemberShipRecord = clubMemberShipRecord;
    }

    @SerializedName("data")
    private ArrayList<ClubMemberShipRecord> clubMemberShipRecord;
}
