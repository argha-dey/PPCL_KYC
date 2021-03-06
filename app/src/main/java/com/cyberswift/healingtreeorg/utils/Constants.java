package com.cyberswift.healingtreeorg.utils;

public interface Constants {


    boolean IS_DEVELOPMENT_MODE = true;
/**
* SERVICES LIST CONSTANT */

    String HOME_CARE_ATTENDANT = "HHC0000000002";
    String NURSING_CARE = "HHC0000000003";
    String PHYSIOTHERAPIST_SERVICES = "HHC0000000004";
    String MEDICALE_EQUIPMENT_SERVICE = "HHC0000000005";
    String DIAGNOSTICS_AT_HOME = "HHC0000000006";



    int HOME_NAV_ID = 1;
    String HOME_NAV_NAME = "Home";

    int PROFILE_NAV_ID = 2;
    String PROFILE_NAV_NAME = "Profile";

    int FEEDBACK_NAV_ID = 4;
    String FEEDBACK_NAV_NAME = "Feedback";

    int NOTIFICATION_NAV_ID = 3;
    String NOTIFICATION_NAV_NAME = "Notification";

    int HISTORY_NAV_ID = 5;
    String HISTORY_NAV_NAME = "History";

    int LOGOUT_NAV_ID = 7;
    String LOGOUT_NAV_NAME = "Logout";


    /**
     * Attendance  Task  related constants
     * */
    int ASSIGNED_TASK_LIST_ID = 9;
    String ASSIGNED_TASK_LIST_NAME = "Task List";

    /**
     * Session User manager related constants
     * */
    String LOGIN = "logIn";
    String USER_ID = "userId";
    String USER_FIRST_NAME = "userFirstName";
    String USER_LAST_NAME = "userLastName";
    String USER_EMAIL_ID = "userEmailId";
    String USER_ZIP_CODE = "userZipCode";
    String USER_ADDRESS = "userAddress";
    String USER_ROLE_NAME = "userRoleName";
    String USER_PHONE_NUMBER = "userPhoneNumber";
    String DOCTOR_BOOKING_TIME = "doctorBookTime";
    String DOCTOR_BOOKING_DATE = "userBookDate";
    String LOGIN_STATUS = "LOGIN_STATUS";


    /**
     * Toasts
     * */
    String LONG = "long";
    String SHORT = "short";
    String MIDDLE_SHORT = "middleShort";
    String MIDDLE_LONG = "middleLong";
    String TOP_SHORT = "topShort";
    String TOP_LONG = "topLong";

    /**
     * Date time format
     * */
    String DATE_TIME_FORMAT_1 = "yyyy-MM-dd";
    String DATE_TIME_FORMAT_2 = "dd MMM yyyy";  // 27 Nov 2018
    String DATE_TIME_FORMAT_3 = "yyyy-MM-dd HH:mm:ss";  // 2018-09-10 10:30:12
    String DATE_TIME_FORMAT_4 = "yyyy-MM-dd KK:mm a"; // 2018-09-10 10:30 AM
    String DATE_TIME_FORMAT_5 = "EEEE"; // Sunday
    String DATE_TIME_FORMAT_6 = "dd MMM yyyy KK:mm a"; // 27 Nov 2018 10:30 AM
    String DATE_TIME_FORMAT_7 = "yyyyMMdd_HHmmss"; // 27 Nov 2018 10:30 AM
    String DATE_TIME_FORMAT_9 = "dd-MM-yyyy";
    String DATE_TIME_FORMAT_10 = "dd-MMM";
    String DATE_TIME_FORMAT_11 = "yyyy-MM-dd HH:mm";
    String DATE_TIME_FORMAT_12 = "KK:mm a";

    String DATE_TIME_FORMAT_8 = "MM/dd/yy";
    public static final String PERMISSION_STATUS = "PERMISSION_STATUS";
    public static final String[] imageDocFileTypes = {"pdf", "doc", "docx", "xls", "xlsx", "txt", "csv", "jpg", "png", "gif","jpeg"};
    public static final String[] imageDocMimeTypes = {"application/vnd.openxmlformats-officedocument.wordprocessingml.document", "application/pdf", "image/jpeg", "image/png", "text/plain", "application/msword", "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"};



    String ARTICLE_ID_CONSTANTS = "id";

    // Role Type
    String ROLE_PATIENT = "Patient";
    String ROLE_PHYSIOTHERAPIST = "Physiotherapist";
    String ROLE_NURSE = "Nurse";
    String ROLE_ATTENDANT = "Attendant";
    String ROLE_MEDICINE_DELIVERY = "Medicine Delivery Person";



}
