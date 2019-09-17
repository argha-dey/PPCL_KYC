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
import android.widget.Toast;
import com.cyberswift.healingtree.R;
import com.cyberswift.healingtree.adapters.DiagnosticsAtHomeAdapter;
import com.cyberswift.healingtree.interfaces.AlertDialogWithCancelAndRetryListener;
import com.cyberswift.healingtree.model.HealthCareResponseModel;
import com.cyberswift.healingtree.model.HCAC_Model;
import com.cyberswift.healingtree.retrofit.ApiClient;
import com.cyberswift.healingtree.retrofit.ApiInterface;
import com.cyberswift.healingtree.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DiagnosticsAtHomeActivity extends AppCompatActivity {
private Context mContext;
    private ArrayList<HCAC_Model> homeCareAttendantChargeslist;
    private RecyclerView diagnostics_charges_RecyclerView;
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
    }


    public void onDiagnosticsAtHomeBackButtonClick(View view) {
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
        requestBody.put("HHC_ID","22");
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
}
