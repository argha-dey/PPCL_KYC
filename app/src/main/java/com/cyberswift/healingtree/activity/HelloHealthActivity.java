package com.cyberswift.healingtree.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import com.cyberswift.healingtree.R;
import com.cyberswift.healingtree.document_download_manager.PdfDownloadManagerForHelloHealth;
import com.cyberswift.healingtree.dropdown.DropDownViewForXML;
import com.cyberswift.healingtree.model.HelloHealthPackageResponseModel;
import com.cyberswift.healingtree.model.PackageModel;
import com.cyberswift.healingtree.retrofit.ApiClient;
import com.cyberswift.healingtree.retrofit.ApiInterface;
import com.cyberswift.healingtree.utils.LocalModel;
import com.cyberswift.healingtree.utils.Prefs;
import com.cyberswift.healingtree.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.*;

public class HelloHealthActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView mCheckUpListRecyclerView;
    private Button mHelloHeathCheckButton;
    private Button mTalkToUsButton;
    private String helloHealthPackagename;
    private String helloHealthPackageId;
    private  ArrayList<PackageModel> packageList;
    private DropDownViewForXML dropDown_hello_health_packages;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_book_health_check);
        mContext = HelloHealthActivity.this;
        initViews();
        registerListeners();
        getHelloHealthPackageListApiCall();
    }

    private void getHelloHealthPackageListApiCall() {
        if(Utils.isOnline(mContext)){
            LocalModel.getInstance().showProgressDialog(this, this.getResources().getString(R.string.please_wait_msg), false);
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("USER_ID", new Prefs(this).getUserID());
          //  requestBody.put("USER_ID", "4");
            ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
            Call<HelloHealthPackageResponseModel> call = apiService.getHelloHealthPackage(requestBody);
            call.enqueue(new Callback<HelloHealthPackageResponseModel>() {
                @Override
                public void onResponse(Call<HelloHealthPackageResponseModel> call, Response<HelloHealthPackageResponseModel> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            HelloHealthPackageResponseModel appointListResponse = response.body();
                            if (appointListResponse.isStatus()) {
                                if (appointListResponse.getData() != null) {
                                    LocalModel.getInstance().cancelProgressDialog();
                                    packageList = appointListResponse.getData();
                                    populateHelloHealthPackageList(packageList);

                                }
                            }
                        } else {
                            LocalModel.getInstance().cancelProgressDialog();
                        }

                    } else {
//                    Toast.makeText(this, "No", LENGTH_LONG).show();
                        LocalModel.getInstance().cancelProgressDialog();
                    }
                }


                @Override
                public void onFailure(Call<HelloHealthPackageResponseModel> call, Throwable t) {
                    LocalModel.getInstance().cancelProgressDialog();
                }
            });
        }
    }


    private void registerListeners() {
        mHelloHeathCheckButton.setOnClickListener(this);
        mTalkToUsButton.setOnClickListener(this);
    }

    private void initViews() {
        packageList = new ArrayList<>();
        mHelloHeathCheckButton = (Button) findViewById(R.id.hello_health_package_button);
        mTalkToUsButton = (Button) findViewById(R.id.talk_to_us_button);
        dropDown_hello_health_packages = findViewById(R.id.dropDown_hello_health_packages);

        dropDown_hello_health_packages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                helloHealthPackageId = packageList.get(position).getHLO_ID();
                helloHealthPackagename = packageList.get(position).getHLO_PACKAGE_NAME();//    DistrictName = districtArrayList.get(position).getDistrictName();
            //    subDistrictDropDownApiCall(districtId);

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.hello_health_package_button:
                onClickOnHelloHealthCheckButton();
                break;
            case R.id.talk_to_us_button:
                onClickOnTalkToUsButton();
                break;
        }
    }

    private void onClickOnTalkToUsButton() {

    }

    private void onClickOnHelloHealthCheckButton() {
        Intent intentHome = new Intent(mContext, HelloHealthPackageBookingActivity.class);
        intentHome.putExtra("HelloHealthPackageName",helloHealthPackagename);
        intentHome.putExtra("HelloHealthPackageId",helloHealthPackageId);
        startActivity(intentHome);
    }


    public void populateHelloHealthPackageList( ArrayList<PackageModel> arrayList) {
        /**ArrayList of Object Shorting   By Name **/
        Collections.sort(arrayList, new Comparator<PackageModel>() {
            @Override
            public int compare(PackageModel regionObject1, PackageModel regionObject2) {

                return regionObject1.getHLO_PACKAGE_NAME().compareTo(regionObject2.getHLO_PACKAGE_NAME());
            }
        });

        if (arrayList.size() > 0) {
            dropDown_hello_health_packages.setEnabled(true);
            dropDown_hello_health_packages.setHint("Select");
            String[] corridorArray = new String[arrayList.size()];
            for (int i = 0; i < arrayList.size(); i++) {
                corridorArray[i] = arrayList.get(i).getHLO_PACKAGE_NAME();
            }
            dropDown_hello_health_packages.setItems(corridorArray);
        }
    }
    public void onOpenPdfHelloHealthPackage(View view) {
        checkFileReadFromExternalPermission();
    }


    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 999;

    private void checkFileReadFromExternalPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("File Write Permission is needed")
                        .setMessage("This app needs the File permission, please accept to use this functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(HelloHealthActivity.this,
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        } else {
            String pdfDownloadUrl = "http://182.74.36.11:8080/uat/healingtree/assets/files/hello_health_package/health_checkup_brochure.pdf";
            PdfDownloadManagerForHelloHealth pdfDownloader = new PdfDownloadManagerForHelloHealth();
            pdfDownloader.showPDFUrl(this, pdfDownloadUrl);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        String pdfDownloadUrl = "http://182.74.36.11:8080/uat/healingtree/assets/files/hello_health_package/health_checkup_brochure.pdf";
        PdfDownloadManagerForHelloHealth pdfDownloader = new PdfDownloadManagerForHelloHealth();
        pdfDownloader.showPDFUrl(this, pdfDownloadUrl);
    }
}
