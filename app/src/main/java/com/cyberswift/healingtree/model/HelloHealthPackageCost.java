package com.cyberswift.healingtree.model;

import com.google.gson.annotations.SerializedName;

public class HelloHealthPackageCost {


    @SerializedName("HHS_ID")
    private String HHS_ID;

    public String getHHS_ID() {
        return HHS_ID;
    }

    public void setHHS_ID(String HHS_ID) {
        this.HHS_ID = HHS_ID;
    }

    public String getHHS_PACKAGE_TYPE() {
        return HHS_PACKAGE_TYPE;
    }

    public void setHHS_PACKAGE_TYPE(String HHS_PACKAGE_TYPE) {
        this.HHS_PACKAGE_TYPE = HHS_PACKAGE_TYPE;
    }

    public String getHHS_PACKAGE_CATEGORY() {
        return HHS_PACKAGE_CATEGORY;
    }

    public void setHHS_PACKAGE_CATEGORY(String HHS_PACKAGE_CATEGORY) {
        this.HHS_PACKAGE_CATEGORY = HHS_PACKAGE_CATEGORY;
    }

    public String getHHS_PACKAGE_COST() {
        return HHS_PACKAGE_COST;
    }

    public void setHHS_PACKAGE_COST(String HHS_PACKAGE_COST) {
        this.HHS_PACKAGE_COST = HHS_PACKAGE_COST;
    }

    public String getHHS_PACKAGE_FOR() {
        return HHS_PACKAGE_FOR;
    }

    public void setHHS_PACKAGE_FOR(String HHS_PACKAGE_FOR) {
        this.HHS_PACKAGE_FOR = HHS_PACKAGE_FOR;
    }

    @SerializedName("HHS_PACKAGE_TYPE")
    private String HHS_PACKAGE_TYPE;

    @SerializedName("HHS_PACKAGE_CATEGORY")
    private String HHS_PACKAGE_CATEGORY;

    @SerializedName("HHS_PACKAGE_COST")
    private String HHS_PACKAGE_COST;

    @SerializedName("HHS_PACKAGE_FOR")
    private String HHS_PACKAGE_FOR;

}
