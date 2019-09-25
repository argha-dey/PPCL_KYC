package com.cyberswift.healingtree.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;


public class Prefs {

    private Context context = null;
    private String cluster_name;

    public Prefs(Context context) {
        this.context = context;
    }

    private String getString(String key, String def) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String s = prefs.getString(key, def);
        return s;
    }

    private int getInt(String key, int def) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int i = Integer.parseInt(prefs.getString(key, Integer.toString(def)));
        return i;
    }

    private float getFloat(String key, float def) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        float f = Float.parseFloat(prefs.getString(key, Float.toString(def)));
        return f;
    }

    private long getLong(String key, long def) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        long l = Long.parseLong(prefs.getString(key, Long.toString(def)));
        return l;
    }

    private void setString(String key, String val) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Editor e = prefs.edit();
        e.putString(key, val);
        e.commit();
    }

    private void setBoolean(String key, boolean val) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Editor e = prefs.edit();
        e.putBoolean(key, val);
        e.commit();
    }

    private void setInt(String key, int val) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Editor e = prefs.edit();
        e.putString(key, Integer.toString(val));
        e.commit();
    }

    private void setLong(String key, long val) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Editor e = prefs.edit();
        e.putString(key, Long.toString(val));
        e.commit();
    }

    private boolean getBoolean(String key, boolean def) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        boolean b = prefs.getBoolean(key, def);
        return b;
    }

    private String getStringValue(String key, String val) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String str = prefs.getString(key, val);
        return str;
    }


    public boolean getLoginStatus(){
        return getBoolean(Constants.LOGIN_STATUS,false);
    }

    public void setLoginStatus(boolean val) {
        setBoolean(Constants.LOGIN_STATUS, val);
    }

    public String getUserAddress() {
        return getString(Constants.USER_ADDRESS, "");
    }

    public void setUserAddress(String val) {
        setString(Constants.USER_ADDRESS, val);
    }

    public String getDoctorBookSelectDate() {
        return getString(Constants.DOCTOR_BOOKING_DATE, "");
    }

    public void setDoctorBookSelectDate(String val) {
        setString(Constants.DOCTOR_BOOKING_DATE, val);
    }

    public String getDoctorBookSelectTime() {
        return getString(Constants.DOCTOR_BOOKING_TIME, "");
    }

    public void setDoctorBookSelectTime(String val) {
        setString(Constants.DOCTOR_BOOKING_TIME, val);
    }

    public String getUserID() {
        return getString(Constants.USER_ID, "");
    }

    public void setUserID(String val) {
        setString(Constants.USER_ID, val);
    }


    public String getUserFirstname() {
        return getString(Constants.USER_FIRST_NAME, "");
    }

    public void setUserFirstname(String val) {
        setString(Constants.USER_FIRST_NAME, val);
    }

    public String getUserLastname() {
        return getString(Constants.USER_LAST_NAME, "");
    }

    public void setUserLastname(String val) {
        setString(Constants.USER_LAST_NAME, val);
    }


    public String getUserEmailId() {
        return getString(Constants.USER_EMAIL_ID, "");
    }

    public void setUserEmailId(String val) {
        setString(Constants.USER_EMAIL_ID, val);
    }

    public String getUserPhoneNumber() {
        return getString(Constants.USER_PHONE_NUMBER, "");
    }

    public void setUserPhoneNumber(String val) {
        setString(Constants.USER_PHONE_NUMBER, val);

        }


    public boolean getPermissionStatus() {
        return getBoolean(Constants.PERMISSION_STATUS, false);
    }

    public void setPermissionStatus(boolean val) {
        setBoolean(Constants.PERMISSION_STATUS, false);
    }


        public void clearPrefdata () {
            setUserID("");
            setLoginStatus(false);
            setUserEmailId("");
            setDoctorBookSelectTime("");
            setUserFirstname("");
            setUserLastname("");
            setUserPhoneNumber("");
            setDoctorBookSelectDate("");
            setUserAddress("");
        }
    }

