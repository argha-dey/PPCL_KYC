package com.cyberswift.healingtree.homeCareAttendanceModuleActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cyberswift.healingtree.R;
import com.cyberswift.healingtree.interfaces.AlertDialogWithCancelAndRetryListener;
import com.cyberswift.healingtree.model.MedicineDeliveryResponseModel;
import com.cyberswift.healingtree.model.MedicineOrderDetails;
import com.cyberswift.healingtree.model.MedicineOrderDetailsResponseModel;
import com.cyberswift.healingtree.retrofit.ApiClient;
import com.cyberswift.healingtree.retrofit.ApiInterface;
import com.cyberswift.healingtree.utils.AlertDialogCallBack;
import com.cyberswift.healingtree.utils.LocalModel;
import com.cyberswift.healingtree.utils.Prefs;
import com.cyberswift.healingtree.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MedicineDeliveryActivity extends BaseAttendanceActivity {
    private Context mContext;
    private String medicine_order_id = "";
    private TextView tv_order_id;
    private TextView tv_holder_name;
    private TextView tv_service_type;
    private TextView tv_order_date;
    private TextView tv_user_contact;
    private TextView tv_user_address;
    private TextView tv_medicine_details;
    private String uploded_url_doc = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oder_medicine_details);
        mContext = MedicineDeliveryActivity.this;
        getDataFromBundle();
        initViews();

        if (Utils.isOnline(mContext)) {
            callDeliveryCall();
        } else {
            Utils.showAlertDialogWithCancelAndRetry(mContext, "Internet connection not available! ", new AlertDialogWithCancelAndRetryListener() {
                @Override
                public void onCancelClick() {
                }

                @Override
                public void onRetryClick() {
                    callDeliveryCall();
                }
            });
        }
    }

    private void getDataFromBundle() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            medicine_order_id = extras.getString("ORDER_ID");
        }
    }

    private void initViews() {
        tv_order_id = findViewById(R.id.tv_order_id);
        tv_holder_name = findViewById(R.id.tv_holder_name);
        tv_service_type = findViewById(R.id.tv_service_type);
        tv_order_date = findViewById(R.id.tv_order_date);
        tv_user_contact = findViewById(R.id.tv_user_contact);
        tv_user_address = findViewById(R.id.tv_user_address);
        tv_medicine_details = findViewById(R.id.tv_medicine_details);
    }

    private void callDeliveryCall() {
        LocalModel.getInstance().showProgressDialog(this, this.getResources().getString(R.string.please_wait_msg), false);
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("user_id", new Prefs(this).getUserID());
        requestBody.put("PP_ID", medicine_order_id);
        ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<MedicineOrderDetailsResponseModel> call = apiService.getMedicineOrderDetails(requestBody);
        call.enqueue(new Callback<MedicineOrderDetailsResponseModel>() {
            @Override
            public void onResponse(Call<MedicineOrderDetailsResponseModel> call, Response<MedicineOrderDetailsResponseModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        MedicineOrderDetailsResponseModel orderDetailsResponseModel = response.body();
                        if (orderDetailsResponseModel.isStatus()) {
                            if (orderDetailsResponseModel.getMedicineOrderDetails() != null) {
                                ArrayList<MedicineOrderDetails> medicineOrderDetails = orderDetailsResponseModel.getMedicineOrderDetails();
                                medicineOrderDetailsDataSet(medicineOrderDetails);
                                LocalModel.getInstance().cancelProgressDialog();
                            }
                        } else {
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
            public void onFailure(Call<MedicineOrderDetailsResponseModel> call, Throwable t) {
                LocalModel.getInstance().cancelProgressDialog();
            }
        });

    }

    private void medicineOrderDetailsDataSet(ArrayList<MedicineOrderDetails> medicineOrderDetails) {
        tv_order_id.setText(medicine_order_id);
        tv_holder_name.setText(medicineOrderDetails.get(0).getPatient_name());
        tv_service_type.setText("Medicine Delivery");
        tv_order_date.setText(medicineOrderDetails.get(0).getBooking_date());
        tv_user_contact.setText(medicineOrderDetails.get(0).getPatient_mobile_number());
        tv_user_address.setText(medicineOrderDetails.get(0).getPatient_address());
        tv_medicine_details.setText(medicineOrderDetails.get(0).getPatient_text_document());
        uploded_url_doc = medicineOrderDetails.get(0).getPatient_uploaded_doc();
    }

    public void onMedicineOderDetailsBackButtonClick(View view) {
        finish();
    }

    public void onDeliveryClick(View view) {
        Utils.showCallBackMessageWithOkCancelCustomButton(mContext, "Are you sure to delivery medicine?", "Yes", "cancel", new AlertDialogCallBack() {
            @Override
            public void onSubmit() {
                deliveryServiceCall();
            }

            @Override
            public void onCancel() {
            }
        });

    }

    private void deliveryServiceCall() {
        LocalModel.getInstance().showProgressDialog(this, this.getResources().getString(R.string.please_wait_msg), false);
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("user_id", new Prefs(this).getUserID());
        requestBody.put("PP_ID", medicine_order_id);
        requestBody.put("delivery_datetime", Utils.getCurrentDateNTime("yyyy-MM-dd HH:mm:ss"));
        ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<MedicineDeliveryResponseModel> call = apiService.getMedicineDeliveryDetails(requestBody);
        call.enqueue(new Callback<MedicineDeliveryResponseModel>() {
            @Override
            public void onResponse(Call<MedicineDeliveryResponseModel> call, Response<MedicineDeliveryResponseModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        MedicineDeliveryResponseModel taskDetailsResponse = response.body();
                        if (taskDetailsResponse.isStatus()) {
                            LocalModel.getInstance().cancelProgressDialog();
                            Toast.makeText(MedicineDeliveryActivity.this, "Medicine Delivery Successfully Done.", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
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
            public void onFailure(Call<MedicineDeliveryResponseModel> call, Throwable t) {
                LocalModel.getInstance().cancelProgressDialog();
            }
        });
    }

    public void onViewDocumentClick(View view) {
        if(!uploded_url_doc.equals("")) {
            Utils.filePreviewDialog(mContext, uploded_url_doc);
        }
        else {
            Toast.makeText(MedicineDeliveryActivity.this, "Document Not Available! .", Toast.LENGTH_SHORT).show();
        }

    }
}