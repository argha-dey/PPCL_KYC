package com.cyberswift.healingtree.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.cyberswift.healingtree.R;
import com.cyberswift.healingtree.adapters.DoctorBookingRecyclerViewAdapter;
import com.cyberswift.healingtree.adapters.HelloHealthRecordViewAdapter;
import com.cyberswift.healingtree.adapters.HomeCareServiceRecordViewAdapter;
import com.cyberswift.healingtree.adapters.OrderMedicineRecordRecyclerViewAdapter;
import com.cyberswift.healingtree.model.*;
import com.cyberswift.healingtree.retrofit.ApiClient;
import com.cyberswift.healingtree.retrofit.ApiInterface;
import com.cyberswift.healingtree.utils.LocalModel;
import com.cyberswift.healingtree.utils.Prefs;
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
    private  TextView hello_health_package_tv;
    private RecyclerView mDoctorBookingRv;
    private RecyclerView mHomeCareRv;
    private RecyclerView mMedicineRv;
    private  RecyclerView hello_health_package_recyclerview;
    private DoctorBookingRecyclerViewAdapter doctorBookingRecyclerViewAdapter;
    private OrderMedicineRecordRecyclerViewAdapter orderMedicineRecyclerViewAdapter;
    private HomeCareServiceRecordViewAdapter homeCareServiceRecordViewAdapter;
    private HelloHealthRecordViewAdapter helloHealthRecordViewAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.health_record_activity);
        initViews();
        registerListenersForViews();
        setVisibilityToViews();
        sendRequestToGetDoctorsBookingList();
        sentRequestToGetMedicineBookingHistory();
        sentHomeCareServiceBookingHistory();
        setHelloHealthPackageHistory();
    }

    private void setHelloHealthPackageHistory() {
        //  LocalModel.getInstance().showProgressDialog(this, this.getResources().getString(R.string.please_wait_msg), false);
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("user_id", new Prefs(this).getUserID());
        ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<HelloHealthPackageRecordResponceModel> call = apiService.getHelloHealthRecordList(requestBody);
        call.enqueue(new Callback<HelloHealthPackageRecordResponceModel>() {
            @Override
            public void onResponse(Call<HelloHealthPackageRecordResponceModel> call, Response<HelloHealthPackageRecordResponceModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        HelloHealthPackageRecordResponceModel helloHealthResponseModel = response.body();
                        if (helloHealthResponseModel.isStatus()) {
                            if (helloHealthResponseModel.getHelloHealthPackageRecords() != null) {
                                LocalModel.getInstance().cancelProgressDialog();
                                ArrayList<HelloHealthPackageRecord> helloHealthPackageDataDos = helloHealthResponseModel.getHelloHealthPackageRecords();
                                setAdapterHelloHealthPackage(helloHealthPackageDataDos);

                            }
                        }
                    } else {
                        //     LocalModel.getInstance().cancelProgressDialog();
                    }

                } else {
//                    Toast.makeText(this, "No", LENGTH_LONG).show();
                    //  LocalModel.getInstance().cancelProgressDialog();
                }
            }


            @Override
            public void onFailure(Call<HelloHealthPackageRecordResponceModel> call, Throwable t) {
                //   LocalModel.getInstance().cancelProgressDialog();
            }
        });
    }

    private void sentHomeCareServiceBookingHistory() {
        //  LocalModel.getInstance().showProgressDialog(this, this.getResources().getString(R.string.please_wait_msg), false);
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("user_id", new Prefs(this).getUserID());
        ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<HomeCareRecordResponceModel> call = apiService.getHomeCareSeviceRecordList(requestBody);
        call.enqueue(new Callback<HomeCareRecordResponceModel>() {
            @Override
            public void onResponse(Call<HomeCareRecordResponceModel> call, Response<HomeCareRecordResponceModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        HomeCareRecordResponceModel historyResponseModel = response.body();
                        if (historyResponseModel.isStatus()) {
                            if (historyResponseModel.getHomeCareServiceRecordLists() != null) {
                                LocalModel.getInstance().cancelProgressDialog();
                                ArrayList<HomeCareServiceRecordList> historyDataDos = historyResponseModel.getHomeCareServiceRecordLists();
                                setAdapterToHomeCare(historyDataDos);

                            }
                        }
                    } else {
                        //     LocalModel.getInstance().cancelProgressDialog();
                    }

                } else {
//                    Toast.makeText(this, "No", LENGTH_LONG).show();
                    //  LocalModel.getInstance().cancelProgressDialog();
                }
            }


            @Override
            public void onFailure(Call<HomeCareRecordResponceModel> call, Throwable t) {
                //   LocalModel.getInstance().cancelProgressDialog();
            }
        });

    }

    private void sentRequestToGetMedicineBookingHistory() {
      //  LocalModel.getInstance().showProgressDialog(this, this.getResources().getString(R.string.please_wait_msg), false);
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("user_id", new Prefs(this).getUserID());
        ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<OrderMedicineHistoryResponceModel> call = apiService.getMedicineHistoryList(requestBody);
        call.enqueue(new Callback<OrderMedicineHistoryResponceModel>() {
            @Override
            public void onResponse(Call<OrderMedicineHistoryResponceModel> call, Response<OrderMedicineHistoryResponceModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        OrderMedicineHistoryResponceModel historyResponseModel = response.body();
                        if (historyResponseModel.isStatus()) {
                            if (historyResponseModel.getOrderMedicineHistoryListModels() != null) {
                                LocalModel.getInstance().cancelProgressDialog();
                                ArrayList<OrderMedicineHistoryListModel> historyDataDos = historyResponseModel.getOrderMedicineHistoryListModels();
                                setAdapterToMedicine(historyDataDos);

                            }
                        }
                    } else {
                   //     LocalModel.getInstance().cancelProgressDialog();
                    }

                } else {
//                    Toast.makeText(this, "No", LENGTH_LONG).show();
                  //  LocalModel.getInstance().cancelProgressDialog();
                }
            }


            @Override
            public void onFailure(Call<OrderMedicineHistoryResponceModel> call, Throwable t) {
             //   LocalModel.getInstance().cancelProgressDialog();
            }
        });

    }

    private void sendRequestToGetDoctorsBookingList() {
        LocalModel.getInstance().showProgressDialog(this, this.getResources().getString(R.string.please_wait_msg), false);
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("user_id", new Prefs(this).getUserID());
      //  requestBody.put("user_id", "4");
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
        //    setAdapterToHomeCare(appointmentDataDos);
            onClickOnDoctorsBookingTextview();


        }
    }

    private void setAdapterToHomeCare(ArrayList<HomeCareServiceRecordList> hcDataDos) {
        homeCareServiceRecordViewAdapter = new HomeCareServiceRecordViewAdapter(this, hcDataDos);
        layoutManager = new LinearLayoutManager(this);
        mHomeCareRv.setLayoutManager(layoutManager);
        mHomeCareRv.setAdapter(homeCareServiceRecordViewAdapter);
    }

    private void setAdapterHelloHealthPackage(ArrayList<HelloHealthPackageRecord> _helloHealthPackageDataDos) {
        helloHealthRecordViewAdapter = new HelloHealthRecordViewAdapter(this, _helloHealthPackageDataDos);
        layoutManager = new LinearLayoutManager(this);
        hello_health_package_recyclerview.setLayoutManager(layoutManager);
        hello_health_package_recyclerview.setAdapter(helloHealthRecordViewAdapter);
    }


    private void setAdapterToMedicine(ArrayList<OrderMedicineHistoryListModel> medicineRecordListDataDos) {
        orderMedicineRecyclerViewAdapter = new OrderMedicineRecordRecyclerViewAdapter(this, medicineRecordListDataDos);
        layoutManager = new LinearLayoutManager(this);
        mMedicineRv.setLayoutManager(layoutManager);
        mMedicineRv.setAdapter(orderMedicineRecyclerViewAdapter);

    }

    private void setVisibilityToViews() {
        mDoctorBookingRv.setVisibility(View.GONE);
        mHomeCareRv.setVisibility(View.GONE);
        mMedicineRv.setVisibility(View.GONE);
        hello_health_package_recyclerview.setVisibility(View.GONE);
    }

    private void registerListenersForViews() {
        mDoctorBookingTv.setOnClickListener(this);
        mHomeCareTv.setOnClickListener(this);
        mMedicineTv.setOnClickListener(this);
        hello_health_package_tv.setOnClickListener(this);
    }

    private void initViews() {
        mDoctorBookingTv = (TextView) findViewById(R.id.doctors_booking_textview);
        mHomeCareTv = (TextView) findViewById(R.id.home_care_textview);
        mMedicineTv = (TextView) findViewById(R.id.medicine_textview);
        hello_health_package_tv = findViewById(R.id.hello_health_package_textview);

        mDoctorBookingRv = (RecyclerView) findViewById(R.id.doctors_booking_recyclerview);
        mHomeCareRv = (RecyclerView) findViewById(R.id.home_care_recyclerview);
        mMedicineRv = (RecyclerView) findViewById(R.id.medicine_recyclerview);
        hello_health_package_recyclerview = findViewById(R.id.hello_health_package_recyclerview);
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
            case R.id.hello_health_package_textview:
                onClickOnHealthPackageTextview();
                break;
        }
    }

    private void onClickOnHealthPackageTextview() {
        mDoctorBookingRv.setVisibility(View.GONE);
        mHomeCareRv.setVisibility(View.GONE);
        mMedicineRv.setVisibility(View.GONE);
        hello_health_package_recyclerview.setVisibility(View.VISIBLE);

    }

    private void onClickOnMedicineTextview() {

        mDoctorBookingRv.setVisibility(View.GONE);
        mHomeCareRv.setVisibility(View.GONE);
        hello_health_package_recyclerview.setVisibility(View.GONE);
        mMedicineRv.setVisibility(View.VISIBLE);

    }

    private void onClickOnHomeCareTextview() {

        mDoctorBookingRv.setVisibility(View.GONE);
        mHomeCareRv.setVisibility(View.VISIBLE);
        mMedicineRv.setVisibility(View.GONE);
        hello_health_package_recyclerview.setVisibility(View.GONE);
    }

    private void onClickOnDoctorsBookingTextview() {
        mDoctorBookingRv.setVisibility(View.VISIBLE);
        mHomeCareRv.setVisibility(View.GONE);
        mMedicineRv.setVisibility(View.GONE);
        hello_health_package_recyclerview.setVisibility(View.GONE);
    }
}
