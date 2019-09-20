package com.cyberswift.healingtree.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import com.cyberswift.healingtree.R;
import com.cyberswift.healingtree.adapters.HomeCareAttendanceChargesAdapter;
import com.cyberswift.healingtree.adapters.MultiSelectionAdapter;
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

public class MedicalEquipmentForHomeCareActivity extends AppCompatActivity {
private Context mContext;
private  RecyclerView rcv_we_offer;
private RecyclerView rcv_medical_equipment_charges;

    private ArrayList<THCA_Model> medicalWeOfferList;
    private ArrayList<HCAC_Model> medicalEquipmentChargeslist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_equipment_for_home_care);
        initViews();
        apiServicesCall();
    }

    private void initViews() {
        mContext = MedicalEquipmentForHomeCareActivity.this;
        medicalWeOfferList = new ArrayList<>();
        medicalEquipmentChargeslist = new ArrayList<>();
        rcv_we_offer = findViewById(R.id.rcv_we_offer);
        rcv_medical_equipment_charges = findViewById(R.id.rcv_medical_equipment_charges);
    }


    public void onMedicalEquipmentBackButtonClick(View view) {
        finish();
    }

    public void onCallForMedicalEquipment(View view){
        if (Utils.checkAndRequestAllPermissions(mContext)) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:02692228408"));
            mContext.startActivity(callIntent);
        }
    }

    public void onBookViaForMedicalEquipment(View view){
        Toast.makeText(mContext,"Available Shortly ",Toast.LENGTH_SHORT).show();
    }


    private void apiServicesCall() {
        if(Utils.isOnline(mContext)){
            fetchMedicalEquipmentData();
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


    public void fetchMedicalEquipmentData() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("HHC_ID", Constants.MEDICALE_EQUIPMENT_SERVICE);
        ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<HealthCareResponseModel> call = apiService.getHomeCareAttandanceData(requestBody);
        call.enqueue(new Callback<HealthCareResponseModel>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onResponse(@NonNull Call<HealthCareResponseModel> call, @NonNull Response<HealthCareResponseModel> response) {
                try {
                    if (response.body() != null) {
                        HealthCareResponseModel healthCareAttendanceResponse = response.body();
                        medicalWeOfferList = healthCareAttendanceResponse.getTrainedHomeCareAttandanceModel();
                        medicalEquipmentChargeslist = healthCareAttendanceResponse.getHomeCareAttandantChargesModel();

                        setWeOfferInAdapter(medicalWeOfferList);
                        setMedicalEquipmentChargesInAdapter(medicalEquipmentChargeslist);

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

    private void setMedicalEquipmentChargesInAdapter(ArrayList<HCAC_Model> medicalEquipmentChargeslist) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set  VERTICAL Orientation
        rcv_medical_equipment_charges.setLayoutManager(linearLayoutManager);
        rcv_medical_equipment_charges.setItemAnimator(new DefaultItemAnimator());
        HomeCareAttendanceChargesAdapter homeCareAdapter = new HomeCareAttendanceChargesAdapter(mContext, medicalEquipmentChargeslist);
        rcv_medical_equipment_charges.setAdapter(homeCareAdapter);

    }

    private void setWeOfferInAdapter(ArrayList<THCA_Model> medicalWeOfferList) {
        rcv_we_offer.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        rcv_we_offer.setItemAnimator(new DefaultItemAnimator());
        MultiSelectionAdapter multiSelectionAdapter = new MultiSelectionAdapter(mContext,medicalWeOfferList,Constants.MEDICALE_EQUIPMENT_SERVICE);
        rcv_we_offer.setAdapter(multiSelectionAdapter);


    }

}
