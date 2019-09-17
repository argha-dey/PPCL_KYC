package com.cyberswift.healingtree.utils;

public interface Urls {
  //  String BASE_URL = "http://192.168.1.45/healingtree/app/";
//  String BASE_URL = "http://192.168.1.88/healingtree/app/";
   String BASE_URL = "http://182.74.36.11:8080/uat/healingtree/app/";
    String DEPARTMENT_LIST = "department/lists";
    String  LOGIN_URL ="login/logins";
    String REGISTRATION_URL = "login/registration";
    String DOCTOR_TIME_SLOT ="doctor/info";
    String DOCTOR_APPOINTMENT ="appointment/add";

    /**
     * Api's methods
     * */
    String DOCTOR_LIST = "doctor/lists";
  String DOCTOR_LIST_ALL = "doctor/all";
  /**
   * Api's Home care
   * */
  String HOME_CARE_ATTENDANCE = "health_care/lists";

}
