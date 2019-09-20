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

public class PhysiotherapistServicesActivity extends AppCompatActivity {
private Context mContext;
private RecyclerView rcv_type_of_injuries_disabilities;
private RecyclerView rcv_physiotherapists_charges;

    private ArrayList<THCA_Model> physiotherapistInjuriesTypeList;
    private ArrayList<HCAC_Model> physiotherapistInjuriesChargeslist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physiotherapist_services);
        initViews();
        apiServicesCall();
    }

    private void initViews() {
        mContext = PhysiotherapistServicesActivity.this;
        physiotherapistInjuriesTypeList = new ArrayList<>();
        physiotherapistInjuriesChargeslist = new ArrayList<>();
        rcv_type_of_injuries_disabilities = findViewById(R.id.rcv_type_of_injuries_disabilities);
        rcv_physiotherapists_charges = findViewById(R.id.rcv_physiotherapists_charges);
    }

    public void onPhysiotherapistBackButtonClick(View view) {
        finish();
    }

    public void onCallForPhysiotherapistService(View view){
        if (Utils.checkAndRequestAllPermissions(mContext)) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:02692228408"));
            mContext.startActivity(callIntent);
        }
    }
    public  void  onBookViaAppForPhysiotherapistService(View view){
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

}
