package com.cyberswift.healingtree.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.cyberswift.healingtree.R;
import com.cyberswift.healingtree.adapters.DoctorBookingRecyclerViewAdapter;
import com.cyberswift.healingtree.model.AppointmentDataDo;
import com.cyberswift.healingtree.model.AppointmentListResponseModel;
import com.cyberswift.healingtree.retrofit.ApiClient;
import com.cyberswift.healingtree.retrofit.ApiInterface;
import com.cyberswift.healingtree.utils.LocalModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HealthRecordActivity extends BaseActivity implements View.OnClickListener {
    private TextView mDoctorBookingTv;
    private TextView mHomeCareTv;
    private TextView mMedicineTv;
    private RecyclerView mDoctorBookingRv;
    private RecyclerView mHomeCareRv;
    private RecyclerView mMedicineRv;
    private DoctorBookingRecyclerViewAdapter doctorBookingRecyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.health_record_activity);
        initViews();
        registerListenersForViews();
        setVisibilityToViews();
        sendRequestToGetDoctorsBookingList();
    }

    private void sendRequestToGetDoctorsBookingList() {
        LocalModel.getInstance().showProgressDialog(this, this.getResources().getString(R.string.please_wait_msg), false);
        Map<String, String> requestBody = new HashMap<>();
//        requestBody.put("user_id", new Prefs(this).getUserID());
        requestBody.put("user_id", "4");
        ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<AppointmentListResponseModel> call = apiService.getAppointmentList(requestBody);
        call.enqueue(new Callback<AppointmentListResponseModel>() {
            @Override
            public void onResponse(Call<AppointmentListResponseModel> call, Response<AppointmentListResponseModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        AppointmentListResponseModel appointListResponse = response.body();
                        if (appointListResponse.isStatus()) {
                            if (appointListResponse.getData() != null) {
                                LocalModel.getInstance().cancelProgressDialog();
                                ArrayList<AppointmentDataDo> appointmentDataDos = appointListResponse.getData();
                                setAdapterToDoctorBookingRecyclerView(appointmentDataDos);

                            }
                        }
                    } else {
                        LocalModel.getInstance().cancelProgressDialog();
                    }

                } else {
//                    Toast.makeText(this, "No", LENGTH_LONG).show();
                    LocalModel.getInstance().cancelProgressDialog();
                }
            }


            @Override
            public void onFailure(Call<AppointmentListResponseModel> call, Throwable t) {
                LocalModel.getInstance().cancelProgressDialog();
            }
        });
    }

    private void setAdapterToDoctorBookingRecyclerView(ArrayList<AppointmentDataDo> appointmentDataDos) {
        if (appointmentDataDos != null) {
            doctorBookingRecyclerViewAdapter = new DoctorBookingRecyclerViewAdapter(this, appointmentDataDos);
            layoutManager = new LinearLayoutManager(this);
            mDoctorBookingRv.setLayoutManager(layoutManager);
            mDoctorBookingRv.setAdapter(doctorBookingRecyclerViewAdapter);
            setAdapterToHomeCare(appointmentDataDos);
            onClickOnDoctorsBookingTextview();


        }
    }

    private void setAdapterToHomeCare(ArrayList<AppointmentDataDo> appointmentDataDos) {
        doctorBookingRecyclerViewAdapter = new DoctorBookingRecyclerViewAdapter(this, appointmentDataDos);
        layoutManager = new LinearLayoutManager(this);
        mHomeCareRv.setLayoutManager(layoutManager);
        mHomeCareRv.setAdapter(doctorBookingRecyclerViewAdapter);
        setAdapterToMedicine(appointmentDataDos);
    }

    private void setAdapterToMedicine(ArrayList<AppointmentDataDo> appointmentDataDos) {
        doctorBookingRecyclerViewAdapter = new DoctorBookingRecyclerViewAdapter(this, appointmentDataDos);
        layoutManager = new LinearLayoutManager(this);
        mMedicineRv.setLayoutManager(layoutManager);
        mMedicineRv.setAdapter(doctorBookingRecyclerViewAdapter);
    }

    private void setVisibilityToViews() {
        mDoctorBookingRv.setVisibility(View.GONE);
        mHomeCareRv.setVisibility(View.GONE);
        mMedicineRv.setVisibility(View.GONE);
    }

    private void registerListenersForViews() {
        mDoctorBookingTv.setOnClickListener(this);
        mHomeCareTv.setOnClickListener(this);
        mMedicineTv.setOnClickListener(this);
    }

    private void initViews() {
        mDoctorBookingTv = (TextView) findViewById(R.id.doctors_booking_textview);
        mHomeCareTv = (TextView) findViewById(R.id.home_care_textview);
        mMedicineTv = (TextView) findViewById(R.id.medicine_textview);

        mDoctorBookingRv = (RecyclerView) findViewById(R.id.doctors_booking_recyclerview);
        mHomeCareRv = (RecyclerView) findViewById(R.id.home_care_recyclerview);
        mMedicineRv = (RecyclerView) findViewById(R.id.medicine_recyclerview);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.doctors_booking_textview:
                onClickOnDoctorsBookingTextview();
                break;
            case R.id.home_care_textview:
                onClickOnHomeCareTextview();
                break;
            case R.id.medicine_textview:
                onClickOnMedicineTextview();
                break;
        }
    }

    private void onClickOnMedicineTextview() {

        mDoctorBookingRv.setVisibility(View.GONE);
        mHomeCareRv.setVisibility(View.GONE);
        mMedicineRv.setVisibility(View.VISIBLE);
    }

    private void onClickOnHomeCareTextview() {

        mDoctorBookingRv.setVisibility(View.GONE);
        mHomeCareRv.setVisibility(View.VISIBLE);
        mMedicineRv.setVisibility(View.GONE);
    }

    private void onClickOnDoctorsBookingTextview() {
        mDoctorBookingRv.setVisibility(View.VISIBLE);
        mHomeCareRv.setVisibility(View.GONE);
        mMedicineRv.setVisibility(View.GONE);
    }
}
