package com.cyberswift.healingtree.retrofit;


import com.cyberswift.healingtree.model.*;
import com.cyberswift.healingtree.utils.Urls;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

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


}
