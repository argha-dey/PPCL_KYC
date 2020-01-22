package com.cyberswift.healingtreeorg.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class DoctorDidDName implements Parcelable {

    @SerializedName("DEPT_ID")
    private String DEPT_ID;
    @SerializedName("DEPT_NAME")
    private String DEPT_NAME;

    public String getDEPT_ID() {
        return DEPT_ID;
    }

    public void setDEPT_ID(String DEPT_ID) {
        this.DEPT_ID = DEPT_ID;
    }

    public String getDEPT_NAME() {
        return DEPT_NAME;
    }

    public void setDEPT_NAME(String DEPT_NAME) {
        this.DEPT_NAME = DEPT_NAME;
    }





    protected DoctorDidDName(Parcel in) {
    }

    public static final Creator<DoctorDidDName> CREATOR = new Creator<DoctorDidDName>() {
        @Override
        public DoctorDidDName createFromParcel(Parcel in) {
            return new DoctorDidDName(in);
        }

        @Override
        public DoctorDidDName[] newArray(int size) {
            return new DoctorDidDName[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
