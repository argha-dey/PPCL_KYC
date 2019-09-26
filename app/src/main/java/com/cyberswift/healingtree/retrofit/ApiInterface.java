package com.cyberswift.healingtree.retrofit;


import com.cyberswift.healingtree.model.*;
import com.cyberswift.healingtree.utils.Urls;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;


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

    @POST(Urls.HELLO_HEALTH_PACKAGE)
    Call<HelloHealthPackageResponseModel> getHelloHealthPackage(@Body Map<String, String> body);


 /*   @POST("prescription/upload_prepcription/post")
    Call<PrescriptionUploadResponceModel> uploadMultiFile(@Body RequestBody file);*/

    @Multipart
    @POST("prescription/upload_prepcription/post")
    Call<ResponseBody> upload(
            @Part("user_id") RequestBody user_id,
            @Part("reamrks") RequestBody reamrks,
            @Part MultipartBody.Part file
    );

 /*   @Multipart
    @POST("prescription/upload_prepcription/post")
    Call<PrescriptionUploadResponceModel> updateProfile(@Part("user_id") RequestBody id,
                                                              @Part MultipartBody.Part image,
                                                              @Part("reamrks") RequestBody other);*/
}
