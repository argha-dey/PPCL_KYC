package com.cyberswift.healingtree.model;

import java.util.ArrayList;

public class AwarnessArticleResponseDo {
    private boolean status;
    private String message;
    private ArrayList<AwarnessLinkDo> awarness_link;

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

    public ArrayList<AwarnessLinkDo> getAwarness_link() {
        return awarness_link;
    }

    public void setAwarness_link(ArrayList<AwarnessLinkDo> awarness_link) {
        this.awarness_link = awarness_link;
    }
}
