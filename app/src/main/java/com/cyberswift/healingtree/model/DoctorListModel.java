package com.cyberswift.healingtree.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DoctorListModel implements Parcelable {

    @SerializedName("doc_id")
    private String docId;
    @SerializedName("doc_name")
    private String docName;
    @SerializedName("qualification")
    private String qualification;
    @SerializedName("specialization")
    private String specialization;
    @SerializedName("experience")
    private String experience;
    @SerializedName("dept")
    private String deptName;
    @SerializedName("dept_id")
    private String deptId;
    @SerializedName("SAT")
    private String docSchedule;
    @SerializedName("day")
    private String day;
    @SerializedName("date")
    private String date;
    @SerializedName("time_slot")
    private ArrayList<TimeSlotModel> time_slot;

    protected DoctorListModel(Parcel in) {
        docId = in.readString();
        docName = in.readString();
        qualification = in.readString();
        specialization = in.readString();
        experience = in.readString();
        deptName = in.readString();
        deptId = in.readString();
        docSchedule = in.readString();
        day = in.readString();
        date = in.readString();
    }

    public static final Creator<DoctorListModel> CREATOR = new Creator<DoctorListModel>() {
        @Override
        public DoctorListModel createFromParcel(Parcel in) {
            return new DoctorListModel(in);
        }

        @Override
        public DoctorListModel[] newArray(int size) {
            return new DoctorListModel[size];
        }
    };

    public ArrayList<TimeSlotModel> getTime_slot() {
        return time_slot;
    }

    public void setTime_slot(ArrayList<TimeSlotModel> time_slot) {
        this.time_slot = time_slot;
    }

    public DoctorListModel() {
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDocSchedule() {
        return docSchedule;
    }

    public void setDocSchedule(String docSchedule) {
        this.docSchedule = docSchedule;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(docId);
        dest.writeString(docName);
        dest.writeString(qualification);
        dest.writeString(specialization);
        dest.writeString(experience);
        dest.writeString(deptName);
        dest.writeString(deptId);
        dest.writeString(docSchedule);
        dest.writeString(day);
        dest.writeString(date);
    }

}
