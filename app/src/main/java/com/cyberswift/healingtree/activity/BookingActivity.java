package com.cyberswift.healingtree.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.cyberswift.healingtree.R;
import com.cyberswift.healingtree.model.DoctorListModel;
import com.cyberswift.healingtree.model.UserAppointmentResponceModel;
import com.cyberswift.healingtree.retrofit.ApiClient;
import com.cyberswift.healingtree.retrofit.ApiInterface;
import com.cyberswift.healingtree.utils.Constants;
import com.cyberswift.healingtree.utils.Prefs;
import com.cyberswift.healingtree.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.HashMap;
import java.util.Map;

public class BookingActivity extends Activity {
    private Activity activity;
    private TextView user_name,mobile_number,user_email,tv_time_date;
    private TextView doctor_name,doctor_spl,doctor_exp,doctor_spl_depart;
    private Prefs prefs;
    private DoctorListModel doctorDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        getDataFromBundle();
        initViewsBooking();
        setUserData();

    }

    private void getDataFromBundle() {
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            doctorDetails = extras.getParcelable("DOCTOR_DETAILS");
        }


    }

    private void setUserData() {
                 user_name.setText(prefs.getUserFirstname() +" "+prefs.getUserLastname());
                 mobile_number.setText(prefs.getUserPhoneNumber());
                 user_email.setText(prefs.getUserEmailId());
                 tv_time_date.setText(prefs.getDoctorBookSelectTime()+ "  "+Utils.changeDateNTimeFormat(prefs.getDoctorBookSelectDate(), Constants.DATE_TIME_FORMAT_1, Constants.DATE_TIME_FORMAT_9));

        doctor_name.setText(doctorDetails.getDocName());
        doctor_spl.setText(doctorDetails.getQualification());
        doctor_exp.setText(doctorDetails.getExperience());
        doctor_spl_depart.setText(doctorDetails.getDeptName());

    }

    private void initViewsBooking() {
        activity = BookingActivity.this;
        prefs = new Prefs(activity);
        user_name = findViewById(R.id.user_name);
        mobile_number = findViewById(R.id.mobile_number);
        user_email = findViewById(R.id.user_email);
        tv_time_date = findViewById(R.id.tv_time_date);

        doctor_name = findViewById(R.id.doctor_name);
        doctor_spl = findViewById(R.id.doctor_spl);
        doctor_exp = findViewById(R.id.doctor_exp);
        doctor_spl_depart = findViewById(R.id.doctor_spl_depart);
    }
    public  void onConfirmBooking(View view){
        appointmentApiCall();
    }

    private void appointmentApiCall() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("user_id",prefs.getUserID());
        requestBody.put("doc_id",doctorDetails.getDocId());
        requestBody.put("day", Utils.changeDateNTimeFormat(prefs.getDoctorBookSelectDate(), Constants.DATE_TIME_FORMAT_1, Constants.DATE_TIME_FORMAT_9));
        requestBody.put("scheduled_time",prefs.getDoctorBookSelectTime());
        ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<UserAppointmentResponceModel> call = apiService.appointement(requestBody);
        call.enqueue(new Callback<UserAppointmentResponceModel>() {
            @Override
            public void onResponse(Call<UserAppointmentResponceModel> call, Response<UserAppointmentResponceModel> response) {
                if (response.body() != null) {
                    UserAppointmentResponceModel doctorListResponseModel = response.body();
                    if (doctorListResponseModel.isStatus()){
                        goToConfirmationPage();
                        Toast.makeText(activity, "Booking Successfully Done", Toast.LENGTH_SHORT).show();
                    }
                    else
                       Utils.showAlertDialogWithOkButton(activity,"Booking Info!",""+doctorListResponseModel.getMessage()+" Please select another time slot.");

                }
                else {
                    Toast.makeText(activity, "Booking Failed ! please try Again.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UserAppointmentResponceModel> call, Throwable t) {

            }
        });
    }

    private void goToConfirmationPage() {
        prefs.setDoctorBookSelectTime("");
        Intent intentHome = new Intent(activity, ConfirmationDoneActivity.class);
        startActivity(intentHome);
    }

    public void onBackConfirmClick(View view) {
        prefs.setDoctorBookSelectTime("");
        finish();
    }

    @Override
    public void onBackPressed() {

    }
}
