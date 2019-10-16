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
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.cyberswift.healingtree.R;
import com.cyberswift.healingtree.adapters.HomeCareAttandanceSpacialOfferAdapter;
import com.cyberswift.healingtree.adapters.HomeCareAttendanceChargesAdapter;
import com.cyberswift.healingtree.adapters.MultiSelectionAdapter;
import com.cyberswift.healingtree.interfaces.AlertDialogWithCancelAndRetryListener;
import com.cyberswift.healingtree.interfaces.OnChargesDataChangeListener;
import com.cyberswift.healingtree.interfaces.OnSpacialOfferDataChange;
import com.cyberswift.healingtree.model.*;
import com.cyberswift.healingtree.retrofit.ApiClient;
import com.cyberswift.healingtree.retrofit.ApiInterface;
import com.cyberswift.healingtree.utils.Constants;
import com.cyberswift.healingtree.utils.LocalModel;
import com.cyberswift.healingtree.utils.Prefs;
import com.cyberswift.healingtree.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.cyberswift.healingtree.adapters.HomeCareAttandanceSpacialOfferAdapter.lastSelectedPositionSOA;
import static com.cyberswift.healingtree.adapters.HomeCareAttendanceChargesAdapter.lastSelectedPositionOfACA;

public class HomeCareAttendantServicesActivity extends AppCompatActivity implements OnChargesDataChangeListener, OnSpacialOfferDataChange {
    private Context mContext;
    private RecyclerView home_care_attendants_charges_RecyclerView;
    private RecyclerView home_care_attendants_special_offers_RecyclerView;
    private RecyclerView home_care_attendants_help_RecyclerView;

    private ArrayList<THCA_Model> trainedHomeCareAttendanceList;
    private ArrayList<HCAC_Model> homeCareAttendantChargeslist;
    private ArrayList<SO_Model> homeCareAttendanceSpacialOfferList;

    private TextView tv_total_payable_amount;
   private int chargeAmount = 0;
   private int spacialOffer = 0;
   private Prefs mPrefs;

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
        mPrefs = new Prefs(mContext);
        home_care_attendants_charges_RecyclerView = findViewById(R.id.home_care_attendants_charges_RecyclerView);
        home_care_attendants_special_offers_RecyclerView = findViewById(R.id.home_care_attendants_special_offers_RecyclerView);
        home_care_attendants_help_RecyclerView = findViewById(R.id.rcv_home_care_attendants_help);
        tv_total_payable_amount = findViewById(R.id.tv_total_payable_amount);

        trainedHomeCareAttendanceList = new ArrayList<>();
        homeCareAttendantChargeslist = new ArrayList<>();
        homeCareAttendanceSpacialOfferList = new ArrayList<>();
    }


    public void onHomeCareAttendanceBackButtonClick(View view) {
        resetData();
        finish();
    }

    @Override
    public void onBackPressed() {
        resetData();
        finish();
    }

    public void onCallForHomeCareAttendantSeries(View view) {

        if (Utils.checkAndRequestAllPermissions(mContext)) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:02692228408"));
            mContext.startActivity(callIntent);
        }
    }

    public void onBookViaHomeCareAttendantSeriesCallBackRequest(View view) {
        Toast.makeText(mContext, "Available on Shortly ", Toast.LENGTH_SHORT).show();
    }

    public void fetchHomeCareData() {
        LocalModel.getInstance().showProgressDialog(this, this.getResources().getString(R.string.please_wait_msg), false);
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
                        LocalModel.getInstance().cancelProgressDialog();
                    }
                    else{
                        LocalModel.getInstance().cancelProgressDialog();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<HealthCareResponseModel> call, Throwable t) {
                LocalModel.getInstance().cancelProgressDialog();
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
        String service_id_list = "";
                ArrayList<String> selectedTargetItemId = new ArrayList<>();
        for (int i = 0; i < MultiSelectionAdapter.taineHomeCareAttendanceList.size(); i++) {
            if (MultiSelectionAdapter.taineHomeCareAttendanceList.get(i).getChecked()) {
                Toast.makeText(mContext, ": " + MultiSelectionAdapter.taineHomeCareAttendanceList.get(i).getHomeCareServiceId(), Toast.LENGTH_SHORT).show();
                selectedTargetItemId.add(MultiSelectionAdapter.taineHomeCareAttendanceList.get(i).getHomeCareServiceId());
            }
        }
         service_id_list =   TextUtils.join(",", selectedTargetItemId);


        String spacialOfferId = "";
        if(lastSelectedPositionSOA >= 0) {
            spacialOfferId = HomeCareAttandanceSpacialOfferAdapter.spacialOffersList.get(lastSelectedPositionSOA).getHomeCareServiceSpacialId();
        }

        String chargesAmountId = "";
        if(lastSelectedPositionOfACA >= 0) {
            chargesAmountId =   HomeCareAttendanceChargesAdapter.chargesList.get(lastSelectedPositionOfACA).getHomeCareAttandanceChargeId();
        }



        if (selectedTargetItemId.size()>0){
            if(!chargesAmountId.equals("")) {
                if(Utils.isOnline(mContext)) {
                    homeCareAttendanceBookingApiCall(service_id_list, chargesAmountId, spacialOfferId);
                }
                else
                    Utils.showAlertDialogWithOkButton(mContext,"Alert!","Please Check Internet Connection");
            }
            else
                Utils.showAlertDialogWithOkButton(mContext,"Alert!","Please Select Any  Home Care Attendance Charges");
        }else
            Utils.showAlertDialogWithOkButton(mContext,"Alert!","Please Select Any Trained Home Care Attendance Service.");
    }

    private  void resetData(){
        MultiSelectionAdapter.taineHomeCareAttendanceList.clear();
        HomeCareAttandanceSpacialOfferAdapter.spacialOffersList.clear();
        lastSelectedPositionSOA = -1;
        HomeCareAttendanceChargesAdapter.chargesList.clear();
        lastSelectedPositionOfACA = -1;
    }
    @Override
    public void onChargesDataChanged(String amount) {
        if(spacialOffer<=0) {
            tv_total_payable_amount.setText("₹" + amount);
            chargeAmount = Integer.parseInt(amount);
        }
        else {
            chargeAmount = Integer.parseInt(amount);
            int chargeAmountWithSpacialOffer = chargeAmount - (chargeAmount*spacialOffer)/100;
            tv_total_payable_amount.setText("₹"+chargeAmountWithSpacialOffer);
        }

    }

    @Override
    public void onSpacialOfferDataChange(String amount) {
       if(chargeAmount>0) {
           spacialOffer = Integer.parseInt(amount);
          int chargeAmountWithSpacialOffer = chargeAmount - (chargeAmount*spacialOffer)/100;
           tv_total_payable_amount.setText("₹"+chargeAmountWithSpacialOffer);
       }
       else {
           Toast.makeText(mContext,"Please select any attendance charge!",Toast.LENGTH_LONG).show();
       }
    }
    private  void homeCareAttendanceBookingApiCall(String _service_id_list,String _chargesAmountId,String _spacialOfferId){
        LocalModel.getInstance().showProgressDialog(this, this.getResources().getString(R.string.please_wait_msg), false);
        final Map<String, String> requestBody = new HashMap<>();
        requestBody.put("user_id", mPrefs.getUserID());
        requestBody.put("service_type_id",Constants.HOME_CARE_ATTENDANT);
        requestBody.put("service_id", _service_id_list);
        requestBody.put("charges", _chargesAmountId);
        requestBody.put("offer", _spacialOfferId);
        requestBody.put("service_date", Utils.currentDate());
        requestBody.put("marchant_name", "Admin");
        requestBody.put("card_no", "41414141414141");
        requestBody.put("date_of_expiry", "12-08-2025");
        requestBody.put("cvv", "569");
        ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<HomeCareAttendantDataPostResponseModel> call = apiService.getHomeCareAttandanceDataPost(requestBody);
        call.enqueue(new Callback<HomeCareAttendantDataPostResponseModel>() {
            @Override
            public void onResponse(Call<HomeCareAttendantDataPostResponseModel> call, Response<HomeCareAttendantDataPostResponseModel> response) {
                if(response.body().isStatus()) {
                    resetData();
                    LocalModel.getInstance().cancelProgressDialog();
                    Intent intentHome = new Intent(mContext, ConfirmationDoneActivity.class);
                    startActivity(intentHome);
                }
                else {
                    LocalModel.getInstance().cancelProgressDialog();
                    Utils.showAlertDialogWithOkButton(mContext,"Error Occur!","Please try again.");
                }
            }
            @Override
            public void onFailure(Call<HomeCareAttendantDataPostResponseModel> call, Throwable t) {
                LocalModel.getInstance().cancelProgressDialog();
                Utils.showAlertDialogWithOkButton(mContext,"Error Occur!","Please try again.");
            }
        });
    }

}
