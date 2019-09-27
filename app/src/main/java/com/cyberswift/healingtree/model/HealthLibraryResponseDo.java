package com.cyberswift.healingtree.model;

import java.util.ArrayList;

public class HealthLibraryResponseDo {
    private boolean status;
    private String message;
    private ArrayList<AwarnessData> awarnessdata;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<AwarnessData> getAwarnessdata() {
        return awarnessdata;
    }

    public void setAwarnessdata(ArrayList<AwarnessData> awarnessdata) {
        this.awarnessdata = awarnessdata;
    }
}
