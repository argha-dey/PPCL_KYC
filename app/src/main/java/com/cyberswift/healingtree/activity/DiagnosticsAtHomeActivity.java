package com.cyberswift.healingtree.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.cyberswift.healingtree.R;
import com.cyberswift.healingtree.adapters.DiagnosticsAtHomeAdapter;
import com.cyberswift.healingtree.interfaces.AlertDialogWithCancelAndRetryListener;
import com.cyberswift.healingtree.interfaces.OnChargesDataChangeListener;
import com.cyberswift.healingtree.model.HCAC_Model;
import com.cyberswift.healingtree.model.HealthCareResponseModel;
import com.cyberswift.healingtree.model.HomeCareAttendantDataPostResponseModel;
import com.cyberswift.healingtree.retrofit.ApiClient;
import com.cyberswift.healingtree.retrofit.ApiInterface;
import com.cyberswift.healingtree.utils.Constants;
import com.cyberswift.healingtree.utils.Utils;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.cyberswift.healingtree.adapters.DiagnosticsAtHomeAdapter.lastSelectedPositionDAH;

public class DiagnosticsAtHomeActivity extends AppCompatActivity  implements OnChargesDataChangeListener {
private Context mContext;
    private ArrayList<HCAC_Model> homeCareAttendantChargeslist;
    private RecyclerView diagnostics_charges_RecyclerView;
    private TextView tv_total_payable_amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_sample_collection);
        initViews();
        apiServicesCall();
    }

    private void initViews() {
        mContext = DiagnosticsAtHomeActivity.this;
        diagnostics_charges_RecyclerView = findViewById(R.id.diagnostics_charges_RecyclerView);
        tv_total_payable_amount = findViewById(R.id.tv_total_payable_amount);
    }


    public void onDiagnosticsAtHomeBackButtonClick(View view) {
        resetData();
        finish();
    }

    @Override
    public void onBackPressed() {
        resetData();
        finish();
    }

    public void onCallForSampleCollectionService(View view){
        if (Utils.checkAndRequestAllPermissions(mContext)) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:02692228408"));
            mContext.startActivity(callIntent);
        }

    }
    public void onBookViaAppSampleCollectionService(View view){
        Toast.makeText(mContext,"Available Shortly ",Toast.LENGTH_SHORT).show();
    }

    private void apiServicesCall() {
        if(Utils.isOnline(mContext)){
            fetchDiagosticsAtHomeData();
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

    public void fetchDiagosticsAtHomeData() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("HHC_ID", Constants.DIAGNOSTICS_AT_HOME);
        ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<HealthCareResponseModel> call = apiService.getHomeCareAttandanceData(requestBody);
        call.enqueue(new Callback<HealthCareResponseModel>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onResponse(@NonNull Call<HealthCareResponseModel> call, @NonNull Response<HealthCareResponseModel> response) {
                try {
                    if (response.body() != null) {
                        HealthCareResponseModel healthCareAttendanceResponse = response.body();
                        homeCareAttendantChargeslist = healthCareAttendanceResponse.getHomeCareAttandantChargesModel();
                        setDiagnosticsChargesInAdapter(homeCareAttendantChargeslist);


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<HealthCareResponseModel>call, Throwable t) {

            }
        });

    }
    //Set Home Care Attendance Charges Adapter
    private void setDiagnosticsChargesInAdapter(ArrayList<HCAC_Model> homeCareAttendantChargesList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set  VERTICAL Orientation
        diagnostics_charges_RecyclerView.setLayoutManager(linearLayoutManager);
        diagnostics_charges_RecyclerView.setItemAnimator(new DefaultItemAnimator());
        DiagnosticsAtHomeAdapter homeCareAdapter = new DiagnosticsAtHomeAdapter(mContext, homeCareAttendantChargesList);
        diagnostics_charges_RecyclerView.setAdapter(homeCareAdapter);

    }

    public void onDiagnosticsProcess(View view) {
        String chargesAmountId = "";
        if(lastSelectedPositionDAH >= 0) {
            chargesAmountId = DiagnosticsAtHomeAdapter.chargesList.get(lastSelectedPositionDAH).getHomeCareAttandanceChargeId();
        }

            if(!chargesAmountId.equals("")) {
                final Map<String, String> requestBody = new HashMap<>();
                requestBody.put("user_id", "4");
                requestBody.put("service_type_id",Constants.DIAGNOSTICS_AT_HOME);
                requestBody.put("service_id", "");
                requestBody.put("charges", chargesAmountId);
                requestBody.put("offer", "");
                requestBody.put("service_date", "20-09-2019");
                requestBody.put("marchant_name", "Admin");
                requestBody.put("card_no", "41414141414141");
                requestBody.put("date_of_expiry", "12-08-2025");
                requestBody.put("cvv", "569");
                System.out.println("response : "+new JSONObject(requestBody));
                ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
                Call<HomeCareAttendantDataPostResponseModel> call = apiService.getHomeCareAttandanceDataPost(requestBody);
                call.enqueue(new Callback<HomeCareAttendantDataPostResponseModel>() {
                    @Override
                    public void onResponse(Call<HomeCareAttendantDataPostResponseModel> call, Response<HomeCareAttendantDataPostResponseModel> response) {
                        Toast.makeText(mContext, "Booking Successfully Done.", Toast.LENGTH_SHORT).show();
                        resetData();
                        Intent intentHome = new Intent(mContext, ConfirmationDoneActivity.class);
                        startActivity(intentHome);
                    }

                    @Override
                    public void onFailure(Call<HomeCareAttendantDataPostResponseModel> call, Throwable t) {
                        Toast.makeText(mContext, "Booking Failure.", Toast.LENGTH_SHORT).show();

                    }
                });
            }
            else
                Utils.showAlertDialogWithOkButton(mContext,"Alert!","Please Select Any Charges");

    }
    protected void resetData(){
        DiagnosticsAtHomeAdapter.chargesList.clear();
        lastSelectedPositionDAH = -1;
    }

    @Override
    public void onChargesDataChanged(String amount) {
        tv_total_payable_amount.setText("₹"+amount);
    }
}
