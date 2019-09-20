package com.cyberswift.healingtree.activity;

import android.annotation.SuppressLint;
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
import android.view.View;
import android.widget.Toast;
import com.cyberswift.healingtree.R;
import com.cyberswift.healingtree.adapters.HomeCareAttendanceChargesAdapter;
import com.cyberswift.healingtree.adapters.MultiSelectionNursingCareAdapter;
import com.cyberswift.healingtree.interfaces.AlertDialogWithCancelAndRetryListener;
import com.cyberswift.healingtree.model.HCAC_Model;
import com.cyberswift.healingtree.model.HealthCareResponseModel;
import com.cyberswift.healingtree.model.THCA_Model;
import com.cyberswift.healingtree.retrofit.ApiClient;
import com.cyberswift.healingtree.retrofit.ApiInterface;
import com.cyberswift.healingtree.utils.Constants;
import com.cyberswift.healingtree.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NursingCareServicesActivity extends AppCompatActivity {
private Context mContext;
private RecyclerView rcv_nursing_care_charges;
private  RecyclerView rcv_nursing_offer;
    private ArrayList<THCA_Model> nursingCareTypeList;
private ArrayList<HCAC_Model> nursingCareChargesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nursing_care_services);
        initViews();
        apiServicesCall();
    }

    private void initViews() {
        mContext = NursingCareServicesActivity.this;
        nursingCareChargesList = new ArrayList<>();
        nursingCareTypeList = new ArrayList<>();
        rcv_nursing_care_charges = findViewById(R.id.rcv_nursing_care_charges);
        rcv_nursing_offer = findViewById(R.id.rcv_nursing_offer);
    }

    public void onNursingCareBackButtonClick(View view) {
        finish();
    }

    public  void onCallForNursingCareServices(View view){
        if (Utils.checkAndRequestAllPermissions(mContext)) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:02692228408"));
            mContext.startActivity(callIntent);
        }
    }

    public  void  onBookViaAppForNursingCareServices(View view){
        Toast.makeText(mContext,"Available Shortly",Toast.LENGTH_SHORT).show();
    }

    private void apiServicesCall() {
        if(Utils.isOnline(mContext)){
            fetchNursingCareData();
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

    public void fetchNursingCareData() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("HHC_ID", Constants.NURSING_CARE);
        ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<HealthCareResponseModel> call = apiService.getHomeCareAttandanceData(requestBody);
        call.enqueue(new Callback<HealthCareResponseModel>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onResponse(@NonNull Call<HealthCareResponseModel> call, @NonNull Response<HealthCareResponseModel> response) {
                try {
                    if (response.body() != null) {
                        HealthCareResponseModel healthCareAttendanceResponse = response.body();
                        nursingCareTypeList = healthCareAttendanceResponse.getTrainedHomeCareAttandanceModel();
                        nursingCareChargesList = healthCareAttendanceResponse.getHomeCareAttandantChargesModel();

                        setNursingCareTypeInAdapter(nursingCareTypeList);
                        setNursingCareChargesInAdapter(nursingCareChargesList);


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

    private void setNursingCareTypeInAdapter(ArrayList<THCA_Model> nursingCareTypeList) {
        rcv_nursing_offer.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        rcv_nursing_offer.setItemAnimator(new DefaultItemAnimator());
        MultiSelectionNursingCareAdapter multiNursingCareSelectionAdapter = new MultiSelectionNursingCareAdapter(mContext);
        multiNursingCareSelectionAdapter.setTrainedHomeCareAttendanceListData(nursingCareTypeList);
        rcv_nursing_offer.setAdapter(multiNursingCareSelectionAdapter);

    }

    private void setNursingCareChargesInAdapter(ArrayList<HCAC_Model> nursingCareChargeslist) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set  VERTICAL Orientation
        rcv_nursing_care_charges.setLayoutManager(linearLayoutManager);
        rcv_nursing_care_charges.setItemAnimator(new DefaultItemAnimator());
        HomeCareAttendanceChargesAdapter homeCareAdapter = new HomeCareAttendanceChargesAdapter(mContext, nursingCareChargeslist);
        rcv_nursing_care_charges.setAdapter(homeCareAdapter);

    }
}
