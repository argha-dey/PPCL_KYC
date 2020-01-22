package com.cyberswift.healingtreeorg.retrofit;


import com.cyberswift.healingtreeorg.model.AllDoctorListResponceModel;
import com.cyberswift.healingtreeorg.model.AppointmentListResponseModel;
import com.cyberswift.healingtreeorg.model.AwarnessArticleResponseDo;
import com.cyberswift.healingtreeorg.model.ClubMemberShipRecordResponseModel;
import com.cyberswift.healingtreeorg.model.DoctorDepartmentModel;
import com.cyberswift.healingtreeorg.model.DoctorListResponseModel;
import com.cyberswift.healingtreeorg.model.DoctorTimeslotResponceModel;
import com.cyberswift.healingtreeorg.model.FeedBackResponseModel;
import com.cyberswift.healingtreeorg.model.HealthCareResponseModel;
import com.cyberswift.healingtreeorg.model.HealthLibraryResponseDo;
import com.cyberswift.healingtreeorg.model.HelloHealthPackageCostResponseModel;
import com.cyberswift.healingtreeorg.model.HelloHealthPackageRecordResponseModel;
import com.cyberswift.healingtreeorg.model.HelloHealthPackageResponseModel;
import com.cyberswift.healingtreeorg.model.HelloHealthSubPackageTypeResponseModel;
import com.cyberswift.healingtreeorg.model.HomeCareAttendantDataPostResponseModel;
import com.cyberswift.healingtreeorg.model.HomeCarePackageBooking;
import com.cyberswift.healingtreeorg.model.HomeCareRecordResponceModel;
import com.cyberswift.healingtreeorg.model.LogOutResponseModel;
import com.cyberswift.healingtreeorg.model.MedicineDeliveryResponseModel;
import com.cyberswift.healingtreeorg.model.MedicineDeliveryTaskListResponseModel;
import com.cyberswift.healingtreeorg.model.MedicineOrderDetailsResponseModel;
import com.cyberswift.healingtreeorg.model.MemberShipClubCostResponseModel;
import com.cyberswift.healingtreeorg.model.NotificationRecordResponseModel;
import com.cyberswift.healingtreeorg.model.OrderMedicineHistoryResponceModel;
import com.cyberswift.healingtreeorg.model.TaskDetailsResponseModel;
import com.cyberswift.healingtreeorg.model.TaskListResponseModel;
import com.cyberswift.healingtreeorg.model.TaskStartEndResponseModel;
import com.cyberswift.healingtreeorg.model.UserAppointmentResponceModel;
import com.cyberswift.healingtreeorg.model.UserProfileResponseModel;
import com.cyberswift.healingtreeorg.utils.Urls;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;


public interface ApiInterface {

    @GET
    Call<DoctorDepartmentModel> doctorDepartment(@Url String url);
    @POST(Urls.DOCTOR_TIME_SLOT)
    Call<DoctorTimeslotResponceModel> doctorTimeSlot(@Body Map<String, Object>body);
    @POST(Urls.DOCTOR_LIST)
    Call<DoctorListResponseModel> doctorList(@Body Map<String, String> body);

    @POST(Urls.DOCTOR_APPOINTMENT)
    Call<UserAppointmentResponceModel> appointement(@Body Map<String, String> body);

    @POST(Urls.DOCTOR_LIST_ALL)
    Call<AllDoctorListResponceModel> allDoctorList(@Body Map<String, String> body);

    @POST(Urls.HOME_CARE_ATTENDANCE_LIST)
    Call<HealthCareResponseModel> getHomeCareAttandanceData(@Body Map<String, String> body);

    @POST(Urls.HOME_CARE_ATTENDANCE_DATA_POST)
    Call<HomeCareAttendantDataPostResponseModel> getHomeCareAttandanceDataPost(@Body Map<String, String> body);

    @POST(Urls.HEALTH_RECORD)
    Call<AppointmentListResponseModel> getAppointmentList(@Body Map<String, String> body);

    @POST(Urls.NOTIFICATION_RECORD)
    Call<NotificationRecordResponseModel> getNotificationList(@Body Map<String, String> body);

    @POST(Urls.FEED_BACK_SERVICE_URL)
    Call<FeedBackResponseModel> postUserFeedBack(@Body Map<String, String> body);

    @POST(Urls.TASK_LIST)
    Call<TaskListResponseModel> getTaskList(@Body Map<String, String> body);

    @POST(Urls.MEDICINE_DELIVERY_TASK_LIST)
    Call<MedicineDeliveryTaskListResponseModel> getMedicineDeliveryTaskList(@Body Map<String, String> body);

    @POST(Urls.MEDICINE_DELIVERY_HISTORY_LIST)
    Call<MedicineDeliveryTaskListResponseModel> getMedicineDeliveryHistoryList(@Body Map<String, String> body);

    @POST(Urls.TASK_START)
    Call<TaskStartEndResponseModel> getStartTask(@Body Map<String, String> body);

    @POST(Urls.TASK_END)
    Call<TaskStartEndResponseModel> getEndTask(@Body Map<String, String> body);

    @POST(Urls.LOGOUT_URL_POST)
    Call<LogOutResponseModel> postLogOut(@Body Map<String, String> body);


    @POST(Urls.USER_PROFILE_VIEW)
    Call<UserProfileResponseModel> getUserProfileData(@Body Map<String, String> body);

    @POST(Urls.USER_PROFILE_EDIT)
    Call<UserProfileResponseModel> updateUserProfileData(@Body Map<String, String> body);

    @POST(Urls.TASK_DETAILS_FOR_ATTENDANCE)
    Call<TaskDetailsResponseModel> getTaskDetails(@Body Map<String, String> body);

    @POST(Urls.MEDICINE_ORDER_DETAILS)
    Call<MedicineOrderDetailsResponseModel> getMedicineOrderDetails(@Body Map<String, String> body);

    @POST(Urls.MEDICINE_ORDER_DELIVIRY)
    Call<MedicineDeliveryResponseModel> getMedicineDeliveryDetails(@Body Map<String, String> body);

    @POST(Urls.MEDICINE_RECORD_LIST)
    Call<OrderMedicineHistoryResponceModel> getMedicineHistoryList(@Body Map<String, String> body);

    @POST(Urls.HOME_CARE_SERVICE_RECORD_LIST)
    Call<HomeCareRecordResponceModel> getHomeCareSeviceRecordList(@Body Map<String, String> body);

    @POST(Urls.HELLO_HEALTH_PACKAGE_RECORD_LIST)
    Call<HelloHealthPackageRecordResponseModel> getHelloHealthRecordList(@Body Map<String, String> body);

    @POST(Urls.CLUB_MEMBER_SHIP_RECORD_LIST)
    Call<ClubMemberShipRecordResponseModel> getClubMemberShipRecordList(@Body Map<String, String> body);


    @POST(Urls.HELLO_HEALTH_PACKAGE)
    Call<HelloHealthPackageResponseModel> getHelloHealthPackage(@Body Map<String, String> body);

    @POST(Urls.HELLO_HEALTH_SUB_PACKAGE_TYPE)
    Call<HelloHealthSubPackageTypeResponseModel> getHelloHealthSubPackage(@Body Map<String, String> body);

    @POST(Urls.HELLO_HEALTH_PACKAGE_COST)
    Call<HelloHealthPackageCostResponseModel> getHelloHealthPackageCost(@Body Map<String, String> body);


    @POST(Urls.HEALING_TREE_MEMBER_SHIP_CLUB_COST)
    Call<MemberShipClubCostResponseModel> getMemberShipCost(@Body Map<String, String> body);

    @GET(Urls.HEALTH_LIBRARY)
    Call<HealthLibraryResponseDo> getHealthLibraryResponse();

    @POST(Urls.AWARNESS_ARTICLE)
    Call<AwarnessArticleResponseDo> getAwarnessArticle(@Body Map<String, String> requestBody);

    @POST(Urls.HELLO_HEALTH_PACKAGE_BOOKING)
    Call<HomeCarePackageBooking> getHelloHealthPackageBooking(@Body Map<String, String> body);

    @POST(Urls.HELLO_HEALTH_CLUB_MEMBER_SHIP_BOOKING)
    Call<HomeCarePackageBooking> getHelloHealthClubMemberShipBooking(@Body Map<String, String> body);

 /*   @POST("prescription/upload_prepcription/post")
    Call<PrescriptionUploadResponceModel> uploadMultiFile(@Body RequestBody file);*/

    @Multipart
    @POST("prescription/upload_prepcription")
    Call<ResponseBody> upload(
            @Part("user_id") RequestBody user_id,
            @Part("reamrks") RequestBody reamrks,
            @Part MultipartBody.Part file
    );

    @Multipart
    @POST("prescription/upload_prepcription")
    Call<ResponseBody> uploadImage(@Part MultipartBody.Part file,@Part("user_id") RequestBody user_id,@Part("reamrks") RequestBody description);


 /*   @Multipart
    @POST("prescription/upload_prepcription/post")
    Call<PrescriptionUploadResponceModel> updateProfile(@Part("user_id") RequestBody id,
                                                              @Part MultipartBody.Part image,
                                                              @Part("reamrks") RequestBody other);*/
}
