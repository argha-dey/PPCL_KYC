package com.cyberswift.healingtreeorg.patientModuleActivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cyberswift.healingtreeorg.R;
import com.cyberswift.healingtreeorg.adapters.ClubMemberShipRecordViewAdapter;
import com.cyberswift.healingtreeorg.adapters.DoctorBookingRecyclerViewAdapter;
import com.cyberswift.healingtreeorg.adapters.HelloHealthRecordViewAdapter;
import com.cyberswift.healingtreeorg.adapters.HomeCareServiceRecordViewAdapter;
import com.cyberswift.healingtreeorg.adapters.OrderMedicineRecordRecyclerViewAdapter;
import com.cyberswift.healingtreeorg.model.AppointmentDataDo;
import com.cyberswift.healingtreeorg.model.AppointmentListResponseModel;
import com.cyberswift.healingtreeorg.model.ClubMemberShipRecord;
import com.cyberswift.healingtreeorg.model.ClubMemberShipRecordResponseModel;
import com.cyberswift.healingtreeorg.model.HelloHealthPackageRecord;
import com.cyberswift.healingtreeorg.model.HelloHealthPackageRecordResponseModel;
import com.cyberswift.healingtreeorg.model.HomeCareRecordResponceModel;
import com.cyberswift.healingtreeorg.model.HomeCareServiceRecordList;
import com.cyberswift.healingtreeorg.model.OrderMedicineHistoryListModel;
import com.cyberswift.healingtreeorg.model.OrderMedicineHistoryResponceModel;
import com.cyberswift.healingtreeorg.retrofit.ApiClient;
import com.cyberswift.healingtreeorg.retrofit.ApiInterface;
import com.cyberswift.healingtreeorg.utils.LocalModel;
import com.cyberswift.healingtreeorg.utils.Prefs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HealthRecordActivity extends BaseActivity implements View.OnClickListener {
    private TextView mDoctorBookingTv;
    private TextView mHomeCareTv;
    private TextView mMedicineTv;
    private  TextView hello_health_package_tv;
    private  TextView  member_ship_club_text_view;
    private RecyclerView mDoctorBookingRv;
    private RecyclerView mHomeCareRv;
    private RecyclerView mMedicineRv;
    private LinearLayout ll_doctor_booking,ll_home_care,ll_medicine_booking,ll_hello_health_booking,ll_club_member_booking;
    private  RecyclerView hello_health_package_recyclerview;
    private  RecyclerView member_ship_club_recycler_view;
    private DoctorBookingRecyclerViewAdapter doctorBookingRecyclerViewAdapter;
    private OrderMedicineRecordRecyclerViewAdapter orderMedicineRecyclerViewAdapter;
    private HomeCareServiceRecordViewAdapter homeCareServiceRecordViewAdapter;
    private HelloHealthRecordViewAdapter helloHealthRecordViewAdapter;
    private ClubMemberShipRecordViewAdapter clubMemberShipRecordViewAdapter;
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
        setClubMemberShipHistory();
    }

    private void setClubMemberShipHistory() {
      //    LocalModel.getInstance().showProgressDialog(this, this.getResources().getString(R.string.please_wait_msg), false);
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("user_id", new Prefs(this).getUserID());
        ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ClubMemberShipRecordResponseModel> call = apiService.getClubMemberShipRecordList(requestBody);
        call.enqueue(new Callback<ClubMemberShipRecordResponseModel>() {
            @Override
            public void onResponse(Call<ClubMemberShipRecordResponseModel> call, Response<ClubMemberShipRecordResponseModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        ClubMemberShipRecordResponseModel clubMemberShipRecordResponse = response.body();
                        if (clubMemberShipRecordResponse.isStatus()) {
                            if (clubMemberShipRecordResponse.getClubMemberShipRecord() != null) {
                                ArrayList<ClubMemberShipRecord> clubMemberShipRecord = clubMemberShipRecordResponse.getClubMemberShipRecord();
                                setAdapterClubMemberShipPackage(clubMemberShipRecord);
                         //       LocalModel.getInstance().cancelProgressDialog();
                            }
                        }
                    } else {
                        //     LocalModel.getInstance().cancelProgressDialog();
                    }

                } else {
//                    Toast.makeText(this, "No", LENGTH_LONG).show();
                     // LocalModel.getInstance().cancelProgressDialog();
                }
            }


            @Override
            public void onFailure(Call<ClubMemberShipRecordResponseModel> call, Throwable t) {
                 //  LocalModel.getInstance().cancelProgressDialog();
            }
        });
    }

    private void setHelloHealthPackageHistory() {
     //     LocalModel.getInstance().showProgressDialog(this, this.getResources().getString(R.string.please_wait_msg), false);
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("user_id", new Prefs(this).getUserID());
        ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<HelloHealthPackageRecordResponseModel> call = apiService.getHelloHealthRecordList(requestBody);
        call.enqueue(new Callback<HelloHealthPackageRecordResponseModel>() {
            @Override
            public void onResponse(Call<HelloHealthPackageRecordResponseModel> call, Response<HelloHealthPackageRecordResponseModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        HelloHealthPackageRecordResponseModel helloHealthResponseModel = response.body();
                        if (helloHealthResponseModel.isStatus()) {
                            if (helloHealthResponseModel.getHelloHealthPackageRecords() != null) {
                              //  LocalModel.getInstance().cancelProgressDialog();
                                ArrayList<HelloHealthPackageRecord> helloHealthPackageDataDos = helloHealthResponseModel.getHelloHealthPackageRecords();
                                setAdapterHelloHealthPackage(helloHealthPackageDataDos);
                           //     LocalModel.getInstance().cancelProgressDialog();
                            }
                        }
                    } else {
                         //    LocalModel.getInstance().cancelProgressDialog();
                    }

                } else {
//                    Toast.makeText(this, "No", LENGTH_LONG).show();
                 //     LocalModel.getInstance().cancelProgressDialog();
                }
            }


            @Override
            public void onFailure(Call<HelloHealthPackageRecordResponseModel> call, Throwable t) {
                //   LocalModel.getInstance().cancelProgressDialog();
            }
        });
    }

    private void sentHomeCareServiceBookingHistory() {
       //   LocalModel.getInstance().showProgressDialog(this, this.getResources().getString(R.string.please_wait_msg), false);
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
                               // LocalModel.getInstance().cancelProgressDialog();
                                ArrayList<HomeCareServiceRecordList> historyDataDos = historyResponseModel.getHomeCareServiceRecordLists();
                                setAdapterToHomeCare(historyDataDos);
                             //   LocalModel.getInstance().cancelProgressDialog();
                            }
                        }
                    } else {
                         //    LocalModel.getInstance().cancelProgressDialog();
                    }

                } else {
//                    Toast.makeText(this, "No", LENGTH_LONG).show();
                    //  LocalModel.getInstance().cancelProgressDialog();
                }
            }


            @Override
            public void onFailure(Call<HomeCareRecordResponceModel> call, Throwable t) {
                  // LocalModel.getInstance().cancelProgressDialog();
            }
        });

    }

    private void sentRequestToGetMedicineBookingHistory() {
     //   LocalModel.getInstance().showProgressDialog(this, this.getResources().getString(R.string.please_wait_msg), false);
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
                              //  LocalModel.getInstance().cancelProgressDialog();
                                ArrayList<OrderMedicineHistoryListModel> historyDataDos = historyResponseModel.getOrderMedicineHistoryListModels();
                                setAdapterToMedicine(historyDataDos);
                            //    LocalModel.getInstance().cancelProgressDialog();
                            }
                        }
                    } else {
                       // LocalModel.getInstance().cancelProgressDialog();
                    }

                } else {
//                    Toast.makeText(this, "No", LENGTH_LONG).show();
                  //  LocalModel.getInstance().cancelProgressDialog();
                }
            }


            @Override
            public void onFailure(Call<OrderMedicineHistoryResponceModel> call, Throwable t) {
              //  LocalModel.getInstance().cancelProgressDialog();
            }
        });

    }

    private void sendRequestToGetDoctorsBookingList() {
        LocalModel.getInstance().showProgressDialog(this, this.getResources().getString(R.string.please_wait_msg), false);
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("user_id", new Prefs(this).getUserID());
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
                                ArrayList<AppointmentDataDo> appointmentDataDos = appointListResponse.getData();
                                setAdapterToDoctorBookingRecyclerView(appointmentDataDos);
                                LocalModel.getInstance().cancelProgressDialog();
                            }
                            else {
                                LocalModel.getInstance().cancelProgressDialog();
                            }
                        }
                        else {
                            LocalModel.getInstance().cancelProgressDialog();
                        }

                    } else {
                        LocalModel.getInstance().cancelProgressDialog();
                    }

                } else {
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
            onClickOnDoctorsBookingTextView();
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

    private void setAdapterClubMemberShipPackage(ArrayList<ClubMemberShipRecord> _clubMemberShipRecord) {
        clubMemberShipRecordViewAdapter = new ClubMemberShipRecordViewAdapter(this, _clubMemberShipRecord);
        layoutManager = new LinearLayoutManager(this);
        member_ship_club_recycler_view.setLayoutManager(layoutManager);
        member_ship_club_recycler_view.setAdapter(clubMemberShipRecordViewAdapter);
    }


    private void setVisibilityToViews() {
        mDoctorBookingRv.setVisibility(View.GONE);
        mHomeCareRv.setVisibility(View.GONE);
        mMedicineRv.setVisibility(View.GONE);
        hello_health_package_recyclerview.setVisibility(View.GONE);
        member_ship_club_recycler_view.setVisibility(View.GONE);
    }

    private void registerListenersForViews() {
        mDoctorBookingTv.setOnClickListener(this);
        mHomeCareTv.setOnClickListener(this);
        mMedicineTv.setOnClickListener(this);
        hello_health_package_tv.setOnClickListener(this);
        member_ship_club_text_view.setOnClickListener(this);
    }

    private void initViews() {
        ll_doctor_booking = findViewById(R.id.ll_doctor_booking);
        ll_home_care = findViewById(R.id.ll_home_care);
        ll_medicine_booking = findViewById(R.id.ll_medicine_booking);
        ll_hello_health_booking = findViewById(R.id.ll_hello_health_booking);
        ll_club_member_booking = findViewById(R.id.ll_club_member_booking);

        ll_doctor_booking.setOnClickListener(this);
        ll_home_care.setOnClickListener(this);
        ll_medicine_booking.setOnClickListener(this);
        ll_hello_health_booking.setOnClickListener(this);
        ll_club_member_booking.setOnClickListener(this);


        mDoctorBookingTv = (TextView) findViewById(R.id.doctors_booking_textview);
        mHomeCareTv = (TextView) findViewById(R.id.home_care_textview);
        mMedicineTv = (TextView) findViewById(R.id.medicine_textview);
        hello_health_package_tv = findViewById(R.id.hello_health_package_textview);
        member_ship_club_text_view = findViewById(R.id.member_ship_club_text_view);

        mDoctorBookingRv = (RecyclerView) findViewById(R.id.doctors_booking_recyclerview);
        mHomeCareRv = (RecyclerView) findViewById(R.id.home_care_recyclerview);
        mMedicineRv = (RecyclerView) findViewById(R.id.medicine_recyclerview);
        hello_health_package_recyclerview = findViewById(R.id.hello_health_package_recyclerview);
        member_ship_club_recycler_view =  findViewById(R.id.member_ship_club_recycler_view);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_doctor_booking:
                onClickOnDoctorsBookingTextView();
                break;
            case R.id.ll_home_care:
                onClickOnHomeCareTextView();
                break;
            case R.id.ll_medicine_booking:
                onClickOnMedicineTextview();
                break;
            case R.id.ll_hello_health_booking:
                onClickOnHealthPackageTextView();
                break;
            case R.id.ll_club_member_booking:
                onClickOnClubMemberShipTextView();
                break;
        }
    }

    private void onClickOnClubMemberShipTextView() {
        mDoctorBookingRv.setVisibility(View.GONE);
        mHomeCareRv.setVisibility(View.GONE);
        mMedicineRv.setVisibility(View.GONE);
        hello_health_package_recyclerview.setVisibility(View.GONE);
        member_ship_club_recycler_view.setVisibility(View.VISIBLE);

    }

    private void onClickOnHealthPackageTextView() {
        mDoctorBookingRv.setVisibility(View.GONE);
        mHomeCareRv.setVisibility(View.GONE);
        mMedicineRv.setVisibility(View.GONE);
        hello_health_package_recyclerview.setVisibility(View.VISIBLE);
        member_ship_club_recycler_view.setVisibility(View.GONE);
    }

    private void onClickOnMedicineTextview() {

        mDoctorBookingRv.setVisibility(View.GONE);
        mHomeCareRv.setVisibility(View.GONE);
        hello_health_package_recyclerview.setVisibility(View.GONE);
        mMedicineRv.setVisibility(View.VISIBLE);
        member_ship_club_recycler_view.setVisibility(View.GONE);

    }

    private void onClickOnHomeCareTextView() {

        mDoctorBookingRv.setVisibility(View.GONE);
        mHomeCareRv.setVisibility(View.VISIBLE);
        mMedicineRv.setVisibility(View.GONE);
        hello_health_package_recyclerview.setVisibility(View.GONE);
        member_ship_club_recycler_view.setVisibility(View.GONE);
    }

    private void onClickOnDoctorsBookingTextView() {
        mDoctorBookingRv.setVisibility(View.VISIBLE);
        mHomeCareRv.setVisibility(View.GONE);
        mMedicineRv.setVisibility(View.GONE);
        hello_health_package_recyclerview.setVisibility(View.GONE);
        member_ship_club_recycler_view.setVisibility(View.GONE);
    }
}
