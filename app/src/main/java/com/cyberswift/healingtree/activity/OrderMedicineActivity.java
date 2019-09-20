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

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.cyberswift.healingtree.R;
import com.cyberswift.healingtree.document_download_manager.PdfDownloadManager;
import com.cyberswift.healingtree.document_download_manager.PdfViewerActivity;

public class OrderMedicineActivity extends BaseActivity {


private TextView tv_no_file_chosen;
private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_order_medicine);

        initViews();
    }

    private void registerEvents() {
     //   mGoldMemberShipIv.setOnClickListener(this);
     //   mPlatinumMemberShipIv.setOnClickListener(this);


    }

    private void initViews() {
        mContext = OrderMedicineActivity.this;
       tv_no_file_chosen =  findViewById(R.id.tv_no_file_chosen);
      //  mPlatinumMemberShipIv = (ImageView) findViewById(R.id.platinum_mem_iv);

    }

    public void onChooseFile(View view){
        Toast.makeText(mContext,"Available Shortly",Toast.LENGTH_LONG).show();
    }


    public void onUploadOderMedicineFile(View view){
        Toast.makeText(mContext,"Available Shortly",Toast.LENGTH_LONG).show();
    }
 /*   @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gold_mem_iv:
                //   openPdfActivity();
                onClickOnGoldMembership();
                break;
            case R.id.platinum_mem_iv:
                //   openPdfActivity();
                onClickOnPlatinumMembership();
                break;
            case R.id.diamond_mem_iv:
                //  openPdfActivity();
                onClickOnDiamondMemberShip();
                break;
            case R.id.privilage_club_package_button:
                // openPdfActivity();
                onClickOnClubPackageButton();
                break;
        }
    }*/

    private void openPdfActivity(){

        Intent i = new Intent(mContext, PdfViewerActivity.class);
        startActivity(i);
    }
    private void onClickOnClubPackageButton() {
        openMembeShipDetailPdf();
    }

    private void onClickOnDiamondMemberShip() {
        openMembeShipDetailPdf();
    }

    private void onClickOnPlatinumMembership() {
        openMembeShipDetailPdf();

    }

    private void onClickOnGoldMembership() {
        openMembeShipDetailPdf();

    }

    private void openMembeShipDetailPdf() {
        checkLocationPermission();
    }


    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 999;

    private void checkLocationPermission() {
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
                                ActivityCompat.requestPermissions(OrderMedicineActivity.this,
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
            String pdfDownloadUrl = "http://www.thehealingtree.org/assets/pdf/health_checkup_brochure.pdf";
            PdfDownloadManager pdfDownloader = new PdfDownloadManager();
            pdfDownloader.showPDFUrl(this, pdfDownloadUrl);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        String pdfDownloadUrl = "http://www.thehealingtree.org/assets/pdf/health_checkup_brochure.pdf";
        PdfDownloadManager pdfDownloader = new PdfDownloadManager();
        pdfDownloader.showPDFUrl(this, pdfDownloadUrl);
    }
}

