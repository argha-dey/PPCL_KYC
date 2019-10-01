package com.cyberswift.healingtree.model;

import com.google.gson.annotations.SerializedName;

public class MemberShipCostModel {

    public String getMember_subscription_amount() {
        return member_subscription_amount;
    }

    public void setMember_subscription_amount(String member_subscription_amount) {
        this.member_subscription_amount = member_subscription_amount;
    }

    @SerializedName("subscription_amount")
    private String member_subscription_amount;

}
