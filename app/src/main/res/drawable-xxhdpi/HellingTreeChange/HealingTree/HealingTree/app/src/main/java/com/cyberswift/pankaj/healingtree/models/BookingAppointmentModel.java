package com.cyberswift.pankaj.healingtree.models;

public class BookingAppointmentModel {

    private String drName;
    private String drQualification;
    private String drDepartment;
    private String drExperience;
    private String drSpecializedIn;
    private String day;
    private String date;
    private String drImage;

    public BookingAppointmentModel() {
    }

    public BookingAppointmentModel(String drName, String drQualification, String drDepartment, String drExperience, String drSpecializedIn, String day, String date, String drImage) {
        this.drName = drName;
        this.drQualification = drQualification;
        this.drDepartment = drDepartment;
        this.drExperience = drExperience;
        this.drSpecializedIn = drSpecializedIn;
        this.day = day;
        this.date = date;
        this.drImage = drImage;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getDrQualification() {
        return drQualification;
    }

    public void setDrQualification(String drQualification) {
        this.drQualification = drQualification;
    }

    public String getDrDepartment() {
        return drDepartment;
    }

    public void setDrDepartment(String drDepartment) {
        this.drDepartment = drDepartment;
    }

    public String getDrExperience() {
        return drExperience;
    }

    public void setDrExperience(String drExperience) {
        this.drExperience = drExperience;
    }

    public String getDrSpecializedIn() {
        return drSpecializedIn;
    }

    public void setDrSpecializedIn(String drSpecializedIn) {
        this.drSpecializedIn = drSpecializedIn;
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

    public String getDrImage() {
        return drImage;
    }

    public void setDrImage(String drImage) {
        this.drImage = drImage;
    }

}
