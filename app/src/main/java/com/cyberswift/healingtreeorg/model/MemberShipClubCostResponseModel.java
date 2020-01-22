package com.cyberswift.healingtreeorg.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MemberShipClubCostResponseModel {
    @SerializedName("message")
    private String message;

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

    public ArrayList<MemberShipCostModel> getMemberShipClubCostList() {
        return memberShipClubCostList;
    }

    public void setMemberShipClubCostList(ArrayList<MemberShipCostModel> memberShipClubCostList) {
        this.memberShipClubCostList = memberShipClubCostList;
    }

    @SerializedName("status")
    private boolean status;
    @SerializedName("membership_data")
    private ArrayList<MemberShipCostModel> memberShipClubCostList;
}
