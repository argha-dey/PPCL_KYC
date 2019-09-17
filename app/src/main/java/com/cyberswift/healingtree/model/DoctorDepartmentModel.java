package com.cyberswift.healingtree.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DoctorDepartmentModel implements Parcelable {

    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private ArrayList<DoctorDidDName> DoctorDidDNameList;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<DoctorDidDName> getDoctorDidDNameList() {
        return DoctorDidDNameList;
    }

    public void setDoctorDidDNameList(ArrayList<DoctorDidDName> doctorDidDNameList) {
        DoctorDidDNameList = doctorDidDNameList;
    }


    protected DoctorDepartmentModel(Parcel in) {
    }

    public static final Creator<DoctorDepartmentModel> CREATOR = new Creator<DoctorDepartmentModel>() {
        @Override
        public DoctorDepartmentModel createFromParcel(Parcel in) {
            return new DoctorDepartmentModel(in);
        }

        @Override
        public DoctorDepartmentModel[] newArray(int size) {
            return new DoctorDepartmentModel[size];
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
