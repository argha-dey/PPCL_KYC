package com.cyberswift.healingtree.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.cyberswift.healingtree.R;
import com.cyberswift.healingtree.utils.Prefs;

public class UserProfileActivity extends BaseActivity{
    private EditText et_user_address;
    private EditText et_user_number;
    private EditText et_user_email;
    private EditText et_user_name;
    private Prefs prefs;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_user_profile);
        initViews();
        setProfileData();
    }

    private void setProfileData() {
       et_user_address.setText(prefs.getUserAddress());
       et_user_number.setText(prefs.getUserPhoneNumber());
       et_user_email.setText(prefs.getUserEmailId());
       et_user_name.setText(prefs.getUserFirstname() +" "+prefs.getUserLastname());
    }

    private void initViews() {
        mContext = UserProfileActivity.this;
        prefs = new Prefs(mContext);
        et_user_address = findViewById(R.id.et_user_address);
        et_user_number = findViewById(R.id.et_user_number);
        et_user_email = findViewById(R.id.et_user_email);
        et_user_name = findViewById(R.id.et_user_name);

    }
    public  void onProfileBackButtonClick(View view){
        finish();

    }
}
