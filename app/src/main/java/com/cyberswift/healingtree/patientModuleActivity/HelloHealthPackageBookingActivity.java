package com.cyberswift.healingtree.patientModuleActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.cyberswift.healingtree.R;
import com.cyberswift.healingtree.dropdown.DropDownViewForXML;
import com.cyberswift.healingtree.model.*;
import com.cyberswift.healingtree.retrofit.ApiClient;
import com.cyberswift.healingtree.retrofit.ApiInterface;
import com.cyberswift.healingtree.utils.LocalModel;
import com.cyberswift.healingtree.utils.Prefs;
import com.cyberswift.healingtree.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.*;

public class HelloHealthPackageBookingActivity extends AppCompatActivity {

    public static String[] PackageTypeForNormal = {"Day","Overnight"};
    public static String[] PackageTypeForCancer = {"Day (Male)","Day (Female)","Overnight"};


    private DropDownViewForXML dropDown_hello_health_packages_type;
    private DropDownViewForXML dropDown_hello_health_packages_sub_type;
    private TextView tv_hello_health_package_name;
    private TextView tv_hello_health_package_amount;
    private LinearLayout ll_sub_package;
    private String helloHealthPackageName="";
    private String helloHealthPackageId = "";
    private Context mContext;
    private String packageDurationType ="";
    private String subPackageId ="";
    private String subPackageName ="";
    private String gendertype = "";
    private String packageCost = "";
    private ArrayList<HelloSubPackageModel> helloSubPackageList;
    private ArrayList<HelloHealthPackageCost> helloHealthPackageCostsArrayList;
    private Prefs mPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_health_package_booking);
        mContext = HelloHealthPackageBookingActivity.this;
        getValueFromBundle();
        initHelloHealthBooking();
        populateStaticPackageType();
        if (Utils.checkAndRequestAllPermissions(mContext)) {
            Utils.addContact(mContext, "Homecare Services", "02692228666");
        }
    }

    private void getValueFromBundle() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            helloHealthPackageName = extras.getString("HelloHealthPackageName");
            helloHealthPackageId = extras.getString("HelloHealthPackageId");
        }
    }

    private void populateStaticPackageType() {
        if (PackageTypeForNormal.length > 0) {
            if (helloHealthPackageId.equals("HLO0000000007")) {
                dropDown_hello_health_packages_type.setEnabled(true);
                dropDown_hello_health_packages_type.setHint("Select");
                dropDown_hello_health_packages_type.setItems(PackageTypeForCancer);
            } else {
                dropDown_hello_health_packages_type.setEnabled(true);
                dropDown_hello_health_packages_type.setHint("Select");
                dropDown_hello_health_packages_type.setItems(PackageTypeForNormal);
            }
        }
    }


    private void helloHealthPackageSubTypeApiCall(String _packageDurationType,String gender) {
        if(Utils.isOnline(mContext)){
            LocalModel.getInstance().showProgressDialog(this, this.getResources().getString(R.string.please_wait_msg), false);
            Map<String, String> request = new HashMap<>();
            request.put("HLO_ID",helloHealthPackageId);
            request.put("HHS_PACKAGE_TYPE",_packageDurationType);
            request.put("GENDER_TYPE",gender);
            ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
            Call<HelloHealthSubPackageTypeResponseModel> call = apiService.getHelloHealthSubPackage(request);
            call.enqueue(new Callback<HelloHealthSubPackageTypeResponseModel>() {
                @Override
                public void onResponse(Call<HelloHealthSubPackageTypeResponseModel> call, Response<HelloHealthSubPackageTypeResponseModel> response) {

                    if (response.body() != null) {
                        HelloHealthSubPackageTypeResponseModel subPackageListResponse = response.body();
                        if (subPackageListResponse.isStatus()) {
                            if (subPackageListResponse.getSubPackageDetails() != null) {
                                LocalModel.getInstance().cancelProgressDialog();
                                helloSubPackageList = subPackageListResponse.getSubPackageDetails();

                                if(helloSubPackageList.get(0).getHHS_PACKAGE_TYPE().equals("Day")){
                                    packageCost = helloSubPackageList.get(0).getHHS_PACKAGE_COST();
                                    tv_hello_health_package_amount.setText("₹"+helloSubPackageList.get(0).getHHS_PACKAGE_COST());
                                }
                                else {
                                    populateHelloHealthSubPackageList(helloSubPackageList);
                                }

                                LocalModel.getInstance().cancelProgressDialog();
                            }
                        }
                        else {
                            LocalModel.getInstance().cancelProgressDialog();
                        }
                    } else {
                        LocalModel.getInstance().cancelProgressDialog();
                    }
                }


                @Override
                public void onFailure(Call<HelloHealthSubPackageTypeResponseModel> call, Throwable t) {
                    LocalModel.getInstance().cancelProgressDialog();
                }
            });
        }
    }

    private void initHelloHealthBooking() {
        mPrefs = new Prefs(mContext);
        dropDown_hello_health_packages_type = findViewById(R.id.dropDown_hello_health_packages_type);
        dropDown_hello_health_packages_sub_type = findViewById(R.id.dropDown_hello_health_packages_sub_type);
        tv_hello_health_package_name = findViewById(R.id.tv_hello_health_package_name);
        tv_hello_health_package_amount = findViewById(R.id.tv_hello_health_package_amount);
        ll_sub_package = findViewById(R.id.ll_sub_package);

        tv_hello_health_package_name.setText(helloHealthPackageName);

        dropDown_hello_health_packages_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (helloHealthPackageId.equals("HLO0000000007")) { packageDurationType = PackageTypeForCancer[position]; }
                else { packageDurationType = PackageTypeForNormal[position]; }

                if(packageDurationType.equals("Overnight")) {
                    ll_sub_package.setVisibility(View.VISIBLE);
                    tv_hello_health_package_amount.setText("");
                    helloHealthPackageSubTypeApiCall(packageDurationType,"");
                }else if(packageDurationType.equals("Day")){
                    ll_sub_package.setVisibility(View.GONE);
                    tv_hello_health_package_amount.setText("");
                    helloHealthPackageSubTypeApiCall(packageDurationType,"");
                }
                else if(packageDurationType.equals("Day (Male)")){
                    ll_sub_package.setVisibility(View.GONE);
                    tv_hello_health_package_amount.setText("");
                    helloHealthPackageSubTypeApiCall("Day","Male");
                }
                else if(packageDurationType.equals("Day (Female)")){
                    ll_sub_package.setVisibility(View.GONE);
                    tv_hello_health_package_amount.setText("");
                    helloHealthPackageSubTypeApiCall("Day","Female");
                }

            }
        });

        dropDown_hello_health_packages_sub_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                subPackageId = helloSubPackageList.get(position).getHHS_ID();
                subPackageName = helloSubPackageList.get(position).getHHS_PACKAGE_CATEGORY();//
                gendertype = helloSubPackageList.get(position).getHHS_PACKAGE_FOR();
                //  DistrictName = districtArrayList.get(position).getDistrictName();
                packageCostApiCall(subPackageName,gendertype);
            }
        });
    }

    private void packageCostApiCall(String _subPackageName,String _genderType) {
        if(Utils.isOnline(mContext)){
            LocalModel.getInstance().showProgressDialog(this, this.getResources().getString(R.string.please_wait_msg), false);
            Map<String, String> request = new HashMap<>();
            request.put("HLO_ID",helloHealthPackageId);
            request.put("HHS_PACKAGE_TYPE",packageDurationType);
            request.put("HHS_PACKAGE_CATEGORY",_subPackageName);
            request.put("GENDER_TYPE",_genderType);
            ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
            Call<HelloHealthPackageCostResponseModel> call = apiService.getHelloHealthPackageCost(request);
            call.enqueue(new Callback<HelloHealthPackageCostResponseModel>() {
                @Override
                public void onResponse(Call<HelloHealthPackageCostResponseModel> call, Response<HelloHealthPackageCostResponseModel> response) {

                    if (response.body() != null) {
                        HelloHealthPackageCostResponseModel packageCostResponse = response.body();
                        if (packageCostResponse.isStatus()) {
                            if (packageCostResponse.getPackageCost() != null) {
                                LocalModel.getInstance().cancelProgressDialog();
                                helloHealthPackageCostsArrayList = packageCostResponse.getPackageCost();
                                packageCost = helloHealthPackageCostsArrayList.get(0).getHHS_PACKAGE_COST();
                                tv_hello_health_package_amount.setText("₹"+ helloHealthPackageCostsArrayList.get(0).getHHS_PACKAGE_COST());
                                LocalModel.getInstance().cancelProgressDialog();
                            }
                        }
                        else
                            LocalModel.getInstance().cancelProgressDialog();
                    } else {
                        LocalModel.getInstance().cancelProgressDialog();
                    }
                }


                @Override
                public void onFailure(Call<HelloHealthPackageCostResponseModel> call, Throwable t) {
                    LocalModel.getInstance().cancelProgressDialog();
                }
            });
        }
    }


    public void populateHelloHealthSubPackageList( ArrayList<HelloSubPackageModel> arrayList) {
        /**ArrayList of Object Shorting   By Name **/
        Collections.sort(arrayList, new Comparator<HelloSubPackageModel>() {
            @Override
            public int compare(HelloSubPackageModel regionObject1, HelloSubPackageModel regionObject2) {

                return regionObject1.getHHS_PACKAGE_CATEGORY().compareTo(regionObject2.getHHS_PACKAGE_CATEGORY());
            }
        });


        if (arrayList.size() > 0) {
            dropDown_hello_health_packages_sub_type.setEnabled(true);
            dropDown_hello_health_packages_sub_type.setHint("Select");
            String[] corridorArray = new String[arrayList.size()];
            for (int i = 0; i < arrayList.size(); i++) {
                if(arrayList.get(i).getHHS_PACKAGE_FOR().equals("")) {
                    corridorArray[i] = arrayList.get(i).getHHS_PACKAGE_CATEGORY();
                }
                else {
                    corridorArray[i] = arrayList.get(i).getHHS_PACKAGE_CATEGORY() + " ( " + arrayList.get(i).getHHS_PACKAGE_FOR()+" )";
                }
            }
            dropDown_hello_health_packages_sub_type.setItems(corridorArray);
        }
    }

    public  void onHelloHealthPackageBooking(View view){
        if(mPrefs.getUserEmailId().equals("")||mPrefs.getUserEmailId().isEmpty()){
            Utils.showAlertDialogWithOkButton(mContext, "Update User Profile!", "Please update your profile before booking an Hello health package");
        }
        else if(mPrefs.getUserPhoneNumber().equals("")||mPrefs.getUserPhoneNumber().isEmpty()){
            Utils.showAlertDialogWithOkButton(mContext, "Update User Profile!", "Please update your profile before booking an Hello health package");
        }
       else if(Utils.isOnline(mContext)){
            LocalModel.getInstance().showProgressDialog(this, this.getResources().getString(R.string.please_wait_msg), false);
            Map<String, String> request = new HashMap<>();
            request.put("USR_USER_ID",mPrefs.getUserID());
            request.put("HHS_PACKAGE_ID",helloHealthPackageId);
            request.put("HHS_PACKAGE_TYPE",packageDurationType);
            request.put("HHS_SUB_PACKAGE_TYPE",subPackageName);
            request.put("HHS_SUB_PACKAGE_ID",subPackageId);
            request.put("HHS_COST",packageCost);

            ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
            Call<HomeCarePackageBooking> call = apiService.getHelloHealthPackageBooking(request);
            call.enqueue(new Callback<HomeCarePackageBooking>() {
                @Override
                public void onResponse(Call<HomeCarePackageBooking> call, Response<HomeCarePackageBooking> response) {

                    if (response.body() != null) {
                        HomeCarePackageBooking bookingResponse = response.body();
                        if (bookingResponse.isStatus()) {
                            Intent intentHome = new Intent(mContext, ConfirmationDoneActivity.class);
                            startActivity(intentHome);
                        }
                        else
                            LocalModel.getInstance().cancelProgressDialog();
                    } else {
                        LocalModel.getInstance().cancelProgressDialog();
                    }
                }


                @Override
                public void onFailure(Call<HomeCarePackageBooking> call, Throwable t) {
                    LocalModel.getInstance().cancelProgressDialog();
                }
            });

        }
        else
            Toast.makeText(mContext,"Please Wait for Internet connection!",Toast.LENGTH_LONG).show();
    }

    public void onTalkToUsForBookPackage(View view) {

        if (Utils.checkAndRequestAllPermissions(mContext)) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:02692228666"));
            mContext.startActivity(callIntent);
        }
    }

    public void onClickBackButton(View view){
        finish();
    }
}
