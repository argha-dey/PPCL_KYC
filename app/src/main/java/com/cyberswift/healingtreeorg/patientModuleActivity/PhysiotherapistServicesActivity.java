package com.cyberswift.healingtreeorg.patientModuleActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cyberswift.healingtreeorg.R;
import com.cyberswift.healingtreeorg.adapters.HomeCareAttendanceChargesAdapter;
import com.cyberswift.healingtreeorg.adapters.MultiSelectionAdapter;
import com.cyberswift.healingtreeorg.interfaces.AlertDialogWithCancelAndRetryListener;
import com.cyberswift.healingtreeorg.interfaces.OnChargesDataChangeListener;
import com.cyberswift.healingtreeorg.model.HCAC_Model;
import com.cyberswift.healingtreeorg.model.HealthCareResponseModel;
import com.cyberswift.healingtreeorg.model.HomeCareAttendantDataPostResponseModel;
import com.cyberswift.healingtreeorg.model.THCA_Model;
import com.cyberswift.healingtreeorg.retrofit.ApiClient;
import com.cyberswift.healingtreeorg.retrofit.ApiInterface;
import com.cyberswift.healingtreeorg.utils.Constants;
import com.cyberswift.healingtreeorg.utils.LocalModel;
import com.cyberswift.healingtreeorg.utils.Prefs;
import com.cyberswift.healingtreeorg.utils.Utils;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cyberswift.healingtreeorg.adapters.HomeCareAttendanceChargesAdapter.lastSelectedPositionOfACA;

public class PhysiotherapistServicesActivity extends AppCompatActivity implements OnChargesDataChangeListener {
    private Context mContext;
    private RecyclerView rcv_type_of_injuries_disabilities;
    private RecyclerView rcv_physiotherapists_charges;

    private ArrayList<THCA_Model> physiotherapistInjuriesTypeList;
    private ArrayList<HCAC_Model> physiotherapistInjuriesChargeslist;
    private TextView tv_end_date,tv_start_date;
    private TextView tv_total_payable_amount;
    private    Prefs mPrefs;
    private LinearLayout ll_service_duration;
    private  TextView tv_total_number_of_days;
    private int chargeAmount = 0;
    int totalNumber_of_days = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physiotherapist_services);
        initViews();
        apiServicesCall();
    }

    private void initViews() {
        mContext = PhysiotherapistServicesActivity.this;
        mPrefs = new Prefs(mContext);
        physiotherapistInjuriesTypeList = new ArrayList<>();
        physiotherapistInjuriesChargeslist = new ArrayList<>();
        rcv_type_of_injuries_disabilities = findViewById(R.id.rcv_type_of_injuries_disabilities);
        rcv_physiotherapists_charges = findViewById(R.id.rcv_physiotherapists_charges);
        tv_total_payable_amount = findViewById(R.id.tv_total_payable_amount);
        tv_end_date = findViewById(R.id.tv_end_date);
        tv_start_date = findViewById(R.id.tv_start_date);
        tv_total_number_of_days =  findViewById(R.id.tv_total_number_of_days);
        ll_service_duration = findViewById(R.id.ll_service_duration);
        ll_service_duration.setVisibility(View.GONE);
    }

    public void onPhysiotherapistBackButtonClick(View view) {
        resetData();
        finish();
    }

    @Override
    public void onBackPressed() {
        resetData();
        finish();
    }

    public void onCallForPhysiotherapistService(View view){
        if (Utils.checkAndRequestAllPermissions(mContext)) {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:02692228408"));
            mContext.startActivity(callIntent);
        }
    }
    public  void  onBookPhysiotherapistServiceCallRequest(View view){
        Toast.makeText(mContext,"Available Shortly",Toast.LENGTH_SHORT).show();
    }


    private void apiServicesCall() {
        if(Utils.isOnline(mContext)){
            fetchPhysiotherapistData();
        }
        else {
            Utils.showAlertDialogWithCancelAndRetry(mContext, "Please Check your internet connection!", new AlertDialogWithCancelAndRetryListener() {
                @Override
                public void onCancelClick() {

                }

                @Override
                public void onRetryClick() {
                    apiServicesCall();
                }
            });
        }

    }
    public void fetchPhysiotherapistData() {
        LocalModel.getInstance().showProgressDialog(this, this.getResources().getString(R.string.please_wait_msg), false);
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("HHC_ID", Constants.PHYSIOTHERAPIST_SERVICES);
        ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<HealthCareResponseModel> call = apiService.getHomeCareAttandanceData(requestBody);
        call.enqueue(new Callback<HealthCareResponseModel>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onResponse(@NonNull Call<HealthCareResponseModel> call, @NonNull Response<HealthCareResponseModel> response) {
                try {
                    if (response.body() != null) {
                        HealthCareResponseModel healthCareAttendanceResponse = response.body();
                        physiotherapistInjuriesTypeList = healthCareAttendanceResponse.getTrainedHomeCareAttandanceModel();
                        physiotherapistInjuriesChargeslist = healthCareAttendanceResponse.getHomeCareAttandantChargesModel();

                        setTypeInAdapter(physiotherapistInjuriesTypeList);
                        setChargesInAdapter(physiotherapistInjuriesChargeslist);
                        LocalModel.getInstance().cancelProgressDialog();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<HealthCareResponseModel>call, Throwable t) {
                LocalModel.getInstance().cancelProgressDialog();
            }
        });

    }

    private void setTypeInAdapter(ArrayList<THCA_Model> physiotherapistInjuriesTypeList) {

        rcv_type_of_injuries_disabilities.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        rcv_type_of_injuries_disabilities.setItemAnimator(new DefaultItemAnimator());
        MultiSelectionAdapter multiSelectionAdapter = new MultiSelectionAdapter(mContext,physiotherapistInjuriesTypeList, Constants.PHYSIOTHERAPIST_SERVICES);
        rcv_type_of_injuries_disabilities.setAdapter(multiSelectionAdapter);

    }

    private void setChargesInAdapter(ArrayList<HCAC_Model> physiotherapistInjuriesChargeslist) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set  VERTICAL Orientation
        rcv_physiotherapists_charges.setLayoutManager(linearLayoutManager);
        rcv_physiotherapists_charges.setItemAnimator(new DefaultItemAnimator());
        HomeCareAttendanceChargesAdapter homeCareAdapter = new HomeCareAttendanceChargesAdapter(mContext, physiotherapistInjuriesChargeslist);
        rcv_physiotherapists_charges.setAdapter(homeCareAdapter);

    }
    public void onPhysiotherapistProcess(View view) {
        if(mPrefs.getUserEmailId().equals("")||mPrefs.getUserEmailId().isEmpty()){
            Utils.showAlertDialogWithOkButton(mContext, "Update User Profile!", "Please update your profile before booking");
        }
        else if(mPrefs.getUserPhoneNumber().equals("")||mPrefs.getUserPhoneNumber().isEmpty()){
            Utils.showAlertDialogWithOkButton(mContext, "Update User Profile!", "Please update your profile before booking");
        }
        else if (tv_start_date.getText().toString().equals("")){
            Utils.showAlertDialogWithOkButton(mContext, "Select Start Date Alert!", "Please choose any date before booking");
        }
        else if(tv_end_date.getText().toString().equals("")){
            Utils.showAlertDialogWithOkButton(mContext, "Select End Date Alert!", "Please choose any date before booking");
        }
        else {

            String service_id_list = "";
            ArrayList<String> selectedTargetItemId = new ArrayList<>();
            for (int i = 0; i < MultiSelectionAdapter.taineHomeCareAttendanceList.size(); i++) {
                if (MultiSelectionAdapter.taineHomeCareAttendanceList.get(i).getChecked()) {
                    Toast.makeText(mContext, ": " + MultiSelectionAdapter.taineHomeCareAttendanceList.get(i).getHomeCareServiceId(), Toast.LENGTH_SHORT).show();
                    selectedTargetItemId.add(MultiSelectionAdapter.taineHomeCareAttendanceList.get(i).getHomeCareServiceId());
                }
            }
            service_id_list = TextUtils.join(",", selectedTargetItemId);

            String chargesAmountId = "";
            if (lastSelectedPositionOfACA >= 0) {
                chargesAmountId = HomeCareAttendanceChargesAdapter.chargesList.get(lastSelectedPositionOfACA).getHomeCareAttandanceChargeId();
            }
            if (selectedTargetItemId.size() > 0) {
                if (!chargesAmountId.equals("")) {
                    LocalModel.getInstance().showProgressDialog(this, this.getResources().getString(R.string.please_wait_msg), false);
                    final Map<String, String> requestBody = new HashMap<>();
                    requestBody.put("user_id", mPrefs.getUserID());
                    requestBody.put("service_type_id", Constants.PHYSIOTHERAPIST_SERVICES);
                    requestBody.put("service_id", service_id_list);
                    requestBody.put("charges", chargesAmountId);
                    requestBody.put("offer", "");
                    requestBody.put("service_date", Utils.currentDate());
                    requestBody.put("start_date", tv_start_date.getText().toString());
                    requestBody.put("end_date", tv_end_date.getText().toString());
                    requestBody.put("no_of_days", String.valueOf(totalNumber_of_days));
                    requestBody.put("marchant_name", "Admin");
                    requestBody.put("card_no", "41414141414141");
                    requestBody.put("date_of_expiry", "12-08-2025");
                    requestBody.put("cvv", "569");
                    requestBody.put("total_payable_amount",tv_total_payable_amount.getText().toString().replaceAll("[₹]",""));
                    System.out.println("response : " + new JSONObject(requestBody));
                    ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
                    Call<HomeCareAttendantDataPostResponseModel> call = apiService.getHomeCareAttandanceDataPost(requestBody);
                    call.enqueue(new Callback<HomeCareAttendantDataPostResponseModel>() {
                        @Override
                        public void onResponse(Call<HomeCareAttendantDataPostResponseModel> call, Response<HomeCareAttendantDataPostResponseModel> response) {

                            resetData();
                            LocalModel.getInstance().cancelProgressDialog();
                            Toast.makeText(mContext, "Booking Successfully Done.", Toast.LENGTH_SHORT).show();
                            Intent intentHome = new Intent(mContext, ConfirmationDoneActivity.class);
                            startActivity(intentHome);
                        }

                        @Override
                        public void onFailure(Call<HomeCareAttendantDataPostResponseModel> call, Throwable t) {
                            LocalModel.getInstance().cancelProgressDialog();
                            Toast.makeText(mContext, "Some Problem may Occurs!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else
                    Utils.showAlertDialogWithOkButton(mContext, "Alert!", "Please Select Any Charges");
            } else {
                Utils.showAlertDialogWithOkButton(mContext, "Alert!", "Please Select Any Patient Recover from Injuries and Disability.");
            }
        }
    }
    private void resetData(){
        MultiSelectionAdapter.taineHomeCareAttendanceList.clear();
        HomeCareAttendanceChargesAdapter.chargesList.clear();
        lastSelectedPositionOfACA = -1;
    }

    @Override
    public void onChargesDataChanged(String amount) {
        chargeAmount = Integer.parseInt(amount);
        tv_total_payable_amount.setText("₹"+amount);
        tv_start_date.setText("");
        tv_end_date.setText("");
        ll_service_duration.setVisibility(View.GONE);
    }







    public void  OnStartDatePickerForPHY(View view){
        final Calendar sCalendar = Calendar.getInstance();
        DatePickerDialog sDatePickerDialog = new DatePickerDialog(mContext,
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        sCalendar.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat df =  new SimpleDateFormat("dd-MM-yyyy");
                        String surveyDate = df.format(sCalendar.getTime());
                        tv_start_date.setText(surveyDate);
                        tv_end_date.setText("");
                        ll_service_duration.setVisibility(View.GONE);
                    }
                }, sCalendar.get(Calendar.YEAR), sCalendar.get(Calendar.MONTH), sCalendar.get(Calendar.DAY_OF_MONTH));
        sDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        sDatePickerDialog.show();
    }

    public void  OnEndDatePickerForPHY(View view){
        final Calendar sCalendar = Calendar.getInstance();
        //    sCalendar.add(Calendar.DATE, +7);
        DatePickerDialog sDatePickerDialog = new DatePickerDialog(mContext,
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        sCalendar.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat df =  new SimpleDateFormat("dd-MM-yyyy");
                        String surveyDate = df.format(sCalendar.getTime());
                        tv_end_date.setText(surveyDate);
                        calculateTotalNumberOfDays(tv_start_date.getText().toString(),tv_end_date.getText().toString());
                    }
                }, sCalendar.get(Calendar.YEAR), sCalendar.get(Calendar.MONTH), sCalendar.get(Calendar.DAY_OF_MONTH));
        sDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        //    sDatePickerDialog.getDatePicker().setMaxDate(sCalendar.getTimeInMillis());
        sDatePickerDialog.show();
    }

    private void calculateTotalNumberOfDays(String start_date, String end_date) {
        totalNumber_of_days  = Integer.parseInt(Utils.getCountOfDays(start_date,end_date));
        if(totalNumber_of_days>0) {
            ll_service_duration.setVisibility(View.VISIBLE);
            tv_total_number_of_days.setText("" + Utils.getCountOfDays(start_date, end_date) + " Days");
            int amount = Integer.parseInt(Utils.getCountOfDays(start_date, end_date)) * chargeAmount;
            tv_total_payable_amount.setText("₹" + amount);
        }
        else {
            Utils.showAlertDialogWithOkButton(mContext,"Healing Tree Alert!","End date always bigger then Start date");
            tv_end_date.setText("");
        }
    }
}
