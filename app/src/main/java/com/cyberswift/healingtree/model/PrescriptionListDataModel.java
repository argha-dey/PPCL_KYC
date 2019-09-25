package com.cyberswift.healingtree.model;

import com.google.gson.annotations.SerializedName;

public class PrescriptionListDataModel {

    @SerializedName("PP_ID")
    private String PP_ID;
    @SerializedName("PP_UPLOADED_DOC")
    private String PP_UPLOADED_DOC;
    @SerializedName("PP_CRT_ID")
    private String PP_CRT_ID;

    @SerializedName("PP_CRT_TS")
    private String PP_CRT_TS;
    @SerializedName("PP_UPD_ID")
    private String PP_UPD_ID;
    @SerializedName("PP_UPD_TS")
    private String PP_UPD_TS;
    @SerializedName("PP_FL_ARCHIVE")
    private String PP_FL_ARCHIVE;


    public String getPP_ID() {
        return PP_ID;
    }

    public void setPP_ID(String PP_ID) {
        this.PP_ID = PP_ID;
    }

    public String getPP_UPLOADED_DOC() {
        return PP_UPLOADED_DOC;
    }

    public void setPP_UPLOADED_DOC(String PP_UPLOADED_DOC) {
        this.PP_UPLOADED_DOC = PP_UPLOADED_DOC;
    }

    public String getPP_CRT_ID() {
        return PP_CRT_ID;
    }

    public void setPP_CRT_ID(String PP_CRT_ID) {
        this.PP_CRT_ID = PP_CRT_ID;
    }

    public String getPP_CRT_TS() {
        return PP_CRT_TS;
    }

    public void setPP_CRT_TS(String PP_CRT_TS) {
        this.PP_CRT_TS = PP_CRT_TS;
    }

    public String getPP_UPD_ID() {
        return PP_UPD_ID;
    }

    public void setPP_UPD_ID(String PP_UPD_ID) {
        this.PP_UPD_ID = PP_UPD_ID;
    }

    public String getPP_UPD_TS() {
        return PP_UPD_TS;
    }

    public void setPP_UPD_TS(String PP_UPD_TS) {
        this.PP_UPD_TS = PP_UPD_TS;
    }

    public String getPP_FL_ARCHIVE() {
        return PP_FL_ARCHIVE;
    }

    public void setPP_FL_ARCHIVE(String PP_FL_ARCHIVE) {
        this.PP_FL_ARCHIVE = PP_FL_ARCHIVE;
    }




}
