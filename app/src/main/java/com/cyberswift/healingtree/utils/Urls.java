package com.cyberswift.healingtree.utils;

public interface Urls {
 //  String BASE_URL = "http://192.168.1.45/healingtree/app/";
//  String BASE_URL = "http://192.168.1.88/healingtree/app/";
 String BASE_URL = "http://182.74.36.11:8080/uat/healingtree/app/";

 //  String BASE_URL = "http://202.129.240.238/healingtree/app/";

 String DEPARTMENT_LIST = "department/lists";
 String  LOGIN_URL ="login/logins";
 String REGISTRATION_URL = "login/registration";
 String FORGOT_PASSWORD_URL = "login/forgot_password";
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
 String NOTIFICATION_RECORD ="notification/notification_list";
 String FEED_BACK_SERVICE_URL ="login/feedback";
 String TASK_LIST = "services/tasklists";
 String MEDICINE_DELIVERY_TASK_LIST ="prescription/medicine_service_tasklist";
 String MEDICINE_ORDER_DETAILS = "prescription/medicine_service_taskview";
 String MEDICINE_ORDER_DELIVIRY =  "prescription/medicine_service_delivered";
 String MEDICINE_DELIVERY_HISTORY_LIST ="prescription/medicine_service_task_history";
 String TASK_DETAILS_FOR_ATTENDANCE = "services/task_details";

 String MEDICINE_RECORD_LIST="prescription/prepcription_list";
 String HOME_CARE_SERVICE_RECORD_LIST ="services/lists";
 String HELLO_HEALTH_PACKAGE_RECORD_LIST ="hello_health/hello_health_service_lists";
 String CLUB_MEMBER_SHIP_RECORD_LIST ="membership/membership_service_lists";

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

 String TASK_START = "services/task_duration_start";
 String TASK_END = "services/task_duration_end";
 String LOGOUT_URL_POST = "login/logout";
 String USER_PROFILE_VIEW = "login/user_profile_view";
 String USER_PROFILE_EDIT = "login/user_profile_edit";

}
