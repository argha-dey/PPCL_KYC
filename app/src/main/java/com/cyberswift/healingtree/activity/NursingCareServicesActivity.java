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
import com.cyberswift.healingtree.adapters.HomeCareAttendanceChargesAdapter;
import com.cyberswift.healingtree.adapters.MultiSelectionNursingCareAdapter;
import com.cyberswift.healingtree.interfaces.AlertDialogWithCancelAndRetryListener;
import com.cyberswift.healingtree.interfaces.OnChargesDataChangeListener;
import com.cyberswift.healingtree.model.HCAC_Model;
import com.cyberswift.healingtree.model.HealthCareResponseModel;
import com.cyberswift.healingtree.model.HomeCareAttendantDataPostResponseModel;
import com.cyberswift.healingtree.model.THCA_Model;
import com.cyberswift.healingtree.retrofit.ApiClient;
import com.cyberswift.healingtree.retrofit.ApiInterface;
import com.cyberswift.healingtree.utils.Constants;
import com.cyberswift.healingtree.utils.LocalModel;
import com.cyberswift.healingtree.utils.Prefs;
import com.cyberswift.healingtree.utils.Utils;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.cyberswift.healingtree.adapters.HomeCareAttendanceChargesAdapter.lastSelectedPositionOfACA;

public class NursingCareServicesActivity extends AppCompatActivity implements OnChargesDataChangeListener {
private Context mContext;
private RecyclerView rcv_nursing_care_charges;
private  RecyclerView rcv_nursing_offer;
    private ArrayList<THCA_Model> nursingCareTypeList;
private ArrayList<HCAC_Model> nursingCareChargesList;
private Prefs mPrefs;
private TextView tv_total_payable_amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nursing_care_services);
        initViews();
        apiServicesCall();
    }

    private void initViews() {
        mContext = NursingCareServicesActivity.this;
        mPrefs = new Prefs(mContext);
        nursingCareChargesList = new ArrayList<>();
        nursingCareTypeList = new ArrayList<>();
        rcv_nursing_care_charges = findViewById(R.id.rcv_nursing_care_charges);
        rcv_nursing_offer = findViewById(R.id.rcv_nursing_offer);
        tv_total_payable_amount = findViewById(R.id.tv_total_payable_amount);
    }

    public void onNursingCareBackButtonClick(View view) {
        resetData();
        finish();
    }

    @Override
    public void onBackPressed() {
        resetData();
        finish();
    }

    public  void onCallForNursingCareServices(View view){
        if (Utils.checkAndRequestAllPermissions(mContext)) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:02692228408"));
            mContext.startActivity(callIntent);
        }
    }

    public  void  onBookForNursingCareServicesCallBack(View view){
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
        LocalModel.getInstance().showProgressDialog(this, this.getResources().getString(R.string.please_wait_msg), false);
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
            public void onFailure(Call<HealthCareResponseModel>call, Throwable t) {

            }
        });

    }

    private void setNursingCareTypeInAdapter(ArrayList<THCA_Model> nursingCareTypeList) {
        rcv_nursing_offer.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        rcv_nursing_offer.setItemAnimator(new DefaultItemAnimator());
        MultiSelectionNursingCareAdapter multiNursingCareSelectionAdapter = new MultiSelectionNursingCareAdapter(mContext,nursingCareTypeList,Constants.NURSING_CARE);
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


    public void onNursingCareServicesProcess(View view) {
        String service_id_list = "";
        ArrayList<String> selectedTargetItemId = new ArrayList<>();
        for (int i = 0; i < MultiSelectionNursingCareAdapter.taineHomeCareAttendanceList.size(); i++) {
            if (MultiSelectionNursingCareAdapter.taineHomeCareAttendanceList.get(i).getChecked()) {
                Toast.makeText(mContext, ": " + MultiSelectionNursingCareAdapter.taineHomeCareAttendanceList.get(i).getHomeCareServiceId(), Toast.LENGTH_SHORT).show();
                selectedTargetItemId.add(MultiSelectionNursingCareAdapter.taineHomeCareAttendanceList.get(i).getHomeCareServiceId());
            }
        }
        service_id_list =   TextUtils.join(",", selectedTargetItemId);



        String chargesAmountId = "";
        if(lastSelectedPositionOfACA >= 0) {
            chargesAmountId =   HomeCareAttendanceChargesAdapter.chargesList.get(lastSelectedPositionOfACA).getHomeCareAttandanceChargeId();

        }



        if (selectedTargetItemId.size()>0){
            if(!chargesAmountId.equals("")) {
                final Map<String, String> requestBody = new HashMap<>();
                requestBody.put("user_id", mPrefs.getUserID());
                requestBody.put("service_type_id",Constants.NURSING_CARE);
                requestBody.put("service_id", service_id_list);
                requestBody.put("charges", chargesAmountId);
                requestBody.put("offer", "");
                requestBody.put("service_date", Utils.currentDate());
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
                        System.out.println("Failure  : ");
                    }
                });
            }
            else
                Utils.showAlertDialogWithOkButton(mContext,"Alert!","Please Select Any Charges");
        }else
            Utils.showAlertDialogWithOkButton(mContext,"Alert!","Please Select Any Offer that We are Providing.");
    }

    private  void resetData(){
        MultiSelectionNursingCareAdapter.taineHomeCareAttendanceList.clear();
        HomeCareAttendanceChargesAdapter.chargesList.clear();
        lastSelectedPositionOfACA = -1;
    }

    @Override
    public void onChargesDataChanged(String amount) {
        tv_total_payable_amount.setText("₹"+amount);
    }
}
