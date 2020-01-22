package com.cyberswift.healingtreeorg.patientModuleActivity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.cyberswift.healingtreeorg.R;

public class HealingHomeCareActivity extends AppCompatActivity {

    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healing_home_care);

        activity = HealingHomeCareActivity.this;

    }


    public void onHomeCareBackButtonClick(View view) {
        finish();
    }


    public void onHomeCareAttendantServicesClick(View view) {
        Intent i = new Intent(activity, HomeCareAttendantServicesActivity.class);
        startActivity(i);
    }


    public void onNursingCareServicesClick(View view) {
        Intent i = new Intent(activity, NursingCareServicesActivity.class);
        startActivity(i);
    }


    public void onPhysiotherapistServicesClick(View view) {
        Intent i = new Intent(activity, PhysiotherapistServicesActivity.class);
        startActivity(i);
    }


    public void onMedicalEquipmentForHomeCareClick(View view) {
        Intent i = new Intent(activity, MedicalEquipmentForHomeCareActivity.class);
        startActivity(i);
    }


    public void onHomeSampleCollectionClick(View view) {
        Intent i = new Intent(activity, DiagnosticsAtHomeActivity.class);
        startActivity(i);
    }


}
