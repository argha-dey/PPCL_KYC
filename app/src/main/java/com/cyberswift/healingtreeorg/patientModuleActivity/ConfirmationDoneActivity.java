package com.cyberswift.healingtreeorg.patientModuleActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import com.cyberswift.healingtreeorg.R;
import com.cyberswift.healingtreeorg.utils.LocalModel;

public class ConfirmationDoneActivity  extends Activity {
    private Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_booking);
        initViewsConfirmBooking();
    }


    private void initViewsConfirmBooking() {
        activity = ConfirmationDoneActivity.this;
    }
    public  void onBookingDone(View view){
        LocalModel.getInstance().showProgressDialog(activity, activity.getResources().getString(R.string.please_wait_msg), false);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                LocalModel.getInstance().cancelProgressDialog();
                Intent intentHome = new Intent(activity, HomeActivity.class);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentHome);
            }
        }, 500);

    }



    @Override
    public void onBackPressed() {

    }
}