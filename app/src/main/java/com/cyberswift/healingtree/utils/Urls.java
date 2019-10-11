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

    String FILE_UPLOAD_URL = "prescription/upload_prepcription/post";

    /**
     * Api's methods
     * */
    String DOCTOR_LIST = "doctor/lists";
  String DOCTOR_LIST_ALL = "doctor/all";
  /**
   * Api's Home care
   * */
  String HOME_CARE_ATTENDANCE_LIST = "health_care/lists";
  String  HOME_CARE_ATTENDANCE_DATA_POST = "services/request_service/post";

    String HOME_CARE_ATTENDANCE = "health_care/lists";
    String HEALTH_RECORD="appointment/lists";
    String MEDICINE_RECORD_LIST="prescription/prepcription_list";
    String HOME_CARE_SERVICE_RECORD_LIST ="services/lists";
    String HELLO_HEALTH_PACKAGE_RECORD_LIST ="hello_health/hello_health_service_lists";

    String HELLO_HEALTH_PACKAGE ="hello_health/package_master";
    String HELLO_HEALTH_PACKAGE_DETAILS ="hello_health/package_details";

    String HELLO_HEALTH_SUB_PACKAGE_TYPE ="hello_health/package_type";
    String HELLO_HEALTH_PACKAGE_COST ="hello_health/package_categorywise";

    String HEALTH_LIBRARY = "awarnesslist/awarness_list";
    String AWARNESS_ARTICLE = "awarnesslist/awarness_link";

    String HEALING_TREE_MEMBER_SHIP_CLUB_COST ="membership/membership_get_amount";

    String HELLO_HEALTH_PACKAGE_BOOKING = "hello_health/hello_health_service_add";

    String  HELLO_HEALTH_CLUB_MEMBER_SHIP_BOOKING = "membership/membership_service_add";
    String UPLOAD_PREPCRIPTION = "prescription/upload_prepcription";
}
