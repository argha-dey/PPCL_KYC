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
import com.cyberswift.healingtree.adapters.HomeCareAttandanceSpacialOfferAdapter;
import com.cyberswift.healingtree.adapters.HomeCareAttendanceChargesAdapter;
import com.cyberswift.healingtree.adapters.MultiSelectionAdapter;
import com.cyberswift.healingtree.interfaces.AlertDialogWithCancelAndRetryListener;
import com.cyberswift.healingtree.model.*;
import com.cyberswift.healingtree.retrofit.ApiClient;
import com.cyberswift.healingtree.retrofit.ApiInterface;
import com.cyberswift.healingtree.utils.Constants;
import com.cyberswift.healingtree.utils.Utils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.cyberswift.healingtree.adapters.HomeCareAttandanceSpacialOfferAdapter.lastSelectedPosition;

public class HomeCareAttendantServicesActivity extends AppCompatActivity {
    private Context mContext;
    private RecyclerView home_care_attendants_charges_RecyclerView;
    private RecyclerView home_care_attendants_special_offers_RecyclerView;
    private RecyclerView home_care_attendants_help_RecyclerView;

    private ArrayList<THCA_Model> trainedHomeCareAttendanceList;
    private ArrayList<HCAC_Model> homeCareAttendantChargeslist;
    private ArrayList<SO_Model> homeCareAttendanceSpacialOfferList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_care_attendant_services);
        initViews();
        apiServicesCall();
    }

    private void apiServicesCall() {
        if (Utils.isOnline(mContext)) {
            fetchHomeCareData();
        } else {
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

    private void initViews() {
        mContext = HomeCareAttendantServicesActivity.this;
        home_care_attendants_charges_RecyclerView = findViewById(R.id.home_care_attendants_charges_RecyclerView);
        home_care_attendants_special_offers_RecyclerView = findViewById(R.id.home_care_attendants_special_offers_RecyclerView);
        home_care_attendants_help_RecyclerView = findViewById(R.id.rcv_home_care_attendants_help);


        trainedHomeCareAttendanceList = new ArrayList<>();
        homeCareAttendantChargeslist = new ArrayList<>();
        homeCareAttendanceSpacialOfferList = new ArrayList<>();
    }


    public void onHomeCareAttendanceBackButtonClick(View view) {
        finish();
    }

    public void onCallForHomeCareAttendantSeries(View view) {

        if (Utils.checkAndRequestAllPermissions(mContext)) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:02692228408"));
            mContext.startActivity(callIntent);
        }
    }

    public void onBookViaAppForHomeCareAttendantSeries(View view) {
        Toast.makeText(mContext, "Available Shortly ", Toast.LENGTH_SHORT).show();
    }

    public void fetchHomeCareData() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("HHC_ID", Constants.HOME_CARE_ATTENDANT);
        ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<HealthCareResponseModel> call = apiService.getHomeCareAttandanceData(requestBody);
        call.enqueue(new Callback<HealthCareResponseModel>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onResponse(@NonNull Call<HealthCareResponseModel> call, @NonNull Response<HealthCareResponseModel> response) {
                try {
                    if (response.body() != null) {
                        HealthCareResponseModel healthCareAttendanceResponse = response.body();
                        trainedHomeCareAttendanceList = healthCareAttendanceResponse.getTrainedHomeCareAttandanceModel();
                        homeCareAttendantChargeslist = healthCareAttendanceResponse.getHomeCareAttandantChargesModel();
                        homeCareAttendanceSpacialOfferList = healthCareAttendanceResponse.getHomeCareAttandanceSpacialOffer();
                        setHomeCareAttendanceChargesInAdapter(homeCareAttendantChargeslist);
                        setHomeCareAttendanceChargesOfferInAdapter(homeCareAttendanceSpacialOfferList);
                        setHomeCareAttendanceTrainHelpInAdapter(trainedHomeCareAttendanceList);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<HealthCareResponseModel> call, Throwable t) {

            }
        });

    }


    // Set Home Care Attendance Trained Help Adapter
    private void setHomeCareAttendanceTrainHelpInAdapter(ArrayList<THCA_Model> trainedHomeCareAttendanceList) {
        home_care_attendants_help_RecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        home_care_attendants_help_RecyclerView.setItemAnimator(new DefaultItemAnimator());
        MultiSelectionAdapter multiSelectionAdapter = new MultiSelectionAdapter(mContext, trainedHomeCareAttendanceList, Constants.HOME_CARE_ATTENDANT);
        home_care_attendants_help_RecyclerView.setAdapter(multiSelectionAdapter);
    }


    // Set Home Care Attendance Charges Offer Adapter
    private void setHomeCareAttendanceChargesOfferInAdapter(ArrayList<SO_Model> homeCareAttendanceSpacialOfferList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set  VERTICAL Orientation
        home_care_attendants_special_offers_RecyclerView.setLayoutManager(linearLayoutManager);
        home_care_attendants_special_offers_RecyclerView.setItemAnimator(new DefaultItemAnimator());
        HomeCareAttandanceSpacialOfferAdapter spacialOfferCareAdapter = new HomeCareAttandanceSpacialOfferAdapter(mContext, homeCareAttendanceSpacialOfferList);
        home_care_attendants_special_offers_RecyclerView.setAdapter(spacialOfferCareAdapter);

    }


    //Set Home Care Attendance Charges Adapter
    private void setHomeCareAttendanceChargesInAdapter(ArrayList<HCAC_Model> homeCareAttendantChargesList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set  VERTICAL Orientation
        home_care_attendants_charges_RecyclerView.setLayoutManager(linearLayoutManager);
        home_care_attendants_charges_RecyclerView.setItemAnimator(new DefaultItemAnimator());
        HomeCareAttendanceChargesAdapter homeCareAdapter = new HomeCareAttendanceChargesAdapter(mContext, homeCareAttendantChargesList);
        home_care_attendants_charges_RecyclerView.setAdapter(homeCareAdapter);

    }

    public void onHomeCareAttendantServicesProcessToPay(View view) {
        ArrayList<String> selectedTargetItemId = new ArrayList<>();
        for (int i = 0; i < MultiSelectionAdapter.taineHomeCareAttendanceList.size(); i++) {
            if (MultiSelectionAdapter.taineHomeCareAttendanceList.get(i).getChecked()) {
                Toast.makeText(mContext,": "+ MultiSelectionAdapter.taineHomeCareAttendanceList.get(i).getHomeCareServiceId(),Toast.LENGTH_SHORT).show();
                selectedTargetItemId.add(MultiSelectionAdapter.taineHomeCareAttendanceList.get(i).getHomeCareServiceId());
            }
        }

        String SpacialofferId = HomeCareAttandanceSpacialOfferAdapter.spacialOffersList.get(lastSelectedPosition).getHomeCareServiceSpacialId();
        Toast.makeText(mContext," "+ SpacialofferId,Toast.LENGTH_SHORT).show();

        String chargesAmountId = HomeCareAttendanceChargesAdapter.chargesList.get(lastSelectedPosition).getHomeCareAttandanceChargeId();
        Toast.makeText(mContext,": "+ chargesAmountId,Toast.LENGTH_SHORT).show();


        JSONArray array = new JSONArray();
        for(int i=0;i<selectedTargetItemId.size();i++){
            JSONObject obj=new JSONObject();
            try {
                obj.put(""+i,selectedTargetItemId.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            array.put(obj);
        }

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("user_id", "4");
        requestBody.put("service_type_id", selectedTargetItemId.toString());
        requestBody.put("charges", chargesAmountId);
        requestBody.put("offer",SpacialofferId);
        requestBody.put("service_date", "20-09-2019");
        requestBody.put("marchant_name", "Admin");
        requestBody.put("card_no", "41414141414141");
        requestBody.put("date_of_expiry", "12-08-2025");
        requestBody.put("cvv", "569");
        System.out.println("JSON RESPONSE: "+new JSONObject(requestBody));
        ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<HomeCareAttendantDataPostResponseModel> call = apiService.getHomeCareAttandanceDataPost(requestBody);
        call.enqueue(new Callback<HomeCareAttendantDataPostResponseModel>() {
            @Override
            public void onResponse(Call<HomeCareAttendantDataPostResponseModel> call, Response<HomeCareAttendantDataPostResponseModel> response) {

            }

            @Override
            public void onFailure(Call<HomeCareAttendantDataPostResponseModel> call, Throwable t) {

            }
        });


    }
}
