package com.cyberswift.healingtree.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.cyberswift.healingtree.R;

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
        Intent intentHome = new Intent(activity, HomeActivity.class);
        intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intentHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intentHome);
    }



    @Override
    public void onBackPressed() {

    }
}