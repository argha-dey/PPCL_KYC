package com.cyberswift.healingtree.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cyberswift.healingtree.R;
import com.cyberswift.healingtree.dropdown.DropDownViewForXML;
import com.cyberswift.healingtree.model.HelloHealthPackageCost;
import com.cyberswift.healingtree.model.HelloHealthPackageCostResponseModel;
import com.cyberswift.healingtree.model.HelloHealthSubPackageTypeResponseModel;
import com.cyberswift.healingtree.model.HelloSubPackageModel;
import com.cyberswift.healingtree.retrofit.ApiClient;
import com.cyberswift.healingtree.retrofit.ApiInterface;
import com.cyberswift.healingtree.utils.LocalModel;
import com.cyberswift.healingtree.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.*;

public class HelloHealthPackageBookingActivity extends AppCompatActivity {

    public static String[] PackageType = {"Day","Overnight"};

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
    private ArrayList<HelloSubPackageModel> helloSubPackageList;
    private ArrayList<HelloHealthPackageCost> helloHealthPackageCosts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_health_package_booking);
        mContext = HelloHealthPackageBookingActivity.this;
        getValueFromBundle();
        initHelloHealthBooking();
        populateStaticPackageType();
    }

    private void getValueFromBundle() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            helloHealthPackageName = extras.getString("HelloHealthPackageName");
            helloHealthPackageId = extras.getString("HelloHealthPackageId");
        }
    }

    private void populateStaticPackageType() {
        if (PackageType.length > 0) {
            dropDown_hello_health_packages_type.setEnabled(true);
            dropDown_hello_health_packages_type.setHint("Select");
            dropDown_hello_health_packages_type.setItems(PackageType);
        }
    }


    private void helloHealthPackageSubTypeApiCall(String _packageDurationType) {
        if(Utils.isOnline(mContext)){
            LocalModel.getInstance().showProgressDialog(this, this.getResources().getString(R.string.please_wait_msg), false);
            Map<String, String> request = new HashMap<>();
            request.put("HLO_ID","HLO0000000001");
            request.put("HHS_PACKAGE_TYPE",_packageDurationType);
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
                                        tv_hello_health_package_amount.setText("₹"+helloSubPackageList.get(0).getHHS_PACKAGE_COST());
                                    }
                                    else {
                                        populateHelloHealthSubPackageList(helloSubPackageList);
                                    }

                                      LocalModel.getInstance().cancelProgressDialog();
                                }
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
        dropDown_hello_health_packages_type = findViewById(R.id.dropDown_hello_health_packages_type);
        dropDown_hello_health_packages_sub_type = findViewById(R.id.dropDown_hello_health_packages_sub_type);
        tv_hello_health_package_name = findViewById(R.id.tv_hello_health_package_name);
        tv_hello_health_package_amount = findViewById(R.id.tv_hello_health_package_amount);
        ll_sub_package = findViewById(R.id.ll_sub_package);

        tv_hello_health_package_name.setText(helloHealthPackageName);

        dropDown_hello_health_packages_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                packageDurationType = PackageType[position];
                if(packageDurationType.equals("Overnight")) {
                    ll_sub_package.setVisibility(View.VISIBLE);
                    tv_hello_health_package_amount.setText("");
                    helloHealthPackageSubTypeApiCall(packageDurationType);
                }else if(packageDurationType.equals("Day")){
                    ll_sub_package.setVisibility(View.GONE);
                    tv_hello_health_package_amount.setText("");
                    helloHealthPackageSubTypeApiCall(packageDurationType);
                }

            }
        });

        dropDown_hello_health_packages_sub_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
          //      subPackageId = helloSubPackageList.get(position).g;
                subPackageName = helloSubPackageList.get(position).getHHS_PACKAGE_CATEGORY();//    DistrictName = districtArrayList.get(position).getDistrictName();
                packageCostApiCall(subPackageName);

            }
        });

    }

    private void packageCostApiCall(String _subPackageName) {
        if(Utils.isOnline(mContext)){
            LocalModel.getInstance().showProgressDialog(this, this.getResources().getString(R.string.please_wait_msg), false);
            Map<String, String> request = new HashMap<>();
            request.put("HLO_ID","HLO0000000001");
            request.put("HHS_PACKAGE_TYPE",packageDurationType);
            request.put("HHS_PACKAGE_CATEGORY",_subPackageName);
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
                                helloHealthPackageCosts = packageCostResponse.getPackageCost();
                                tv_hello_health_package_amount.setText("₹"+helloHealthPackageCosts.get(0).getHHS_PACKAGE_COST());
                                LocalModel.getInstance().cancelProgressDialog();
                            }
                        }
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
                corridorArray[i] = arrayList.get(i).getHHS_PACKAGE_CATEGORY();
            }
            dropDown_hello_health_packages_sub_type.setItems(corridorArray);
        }
    }
}