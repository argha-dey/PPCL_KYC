package com.cyberswift.healingtree.homeCareAttendanceModuleActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.cyberswift.healingtree.R;
import com.cyberswift.healingtree.model.UserDetailsModel;
import com.cyberswift.healingtree.model.UserProfileResponseModel;
import com.cyberswift.healingtree.retrofit.ApiClient;
import com.cyberswift.healingtree.retrofit.ApiInterface;
import com.cyberswift.healingtree.utils.Constants;
import com.cyberswift.healingtree.utils.LocalModel;
import com.cyberswift.healingtree.utils.Prefs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendanceUserProfileActivity extends BaseAttendanceActivity {
    String[] stateNameArray = {"Select State","Gujarat"};

    String[] cityNameArray = {"Select City","Anand","Vadodara",
            "Bhavnagar",
            "Bharuch",
            "Gandhidham",
            "Jamnagar",
            "Junagadh",
            "Rajkot",
            "Morvi",
            "Navsari",
            "Nadiad",
            "Surendranagar",
            "Porbandar"
    };
    private EditText et_user_address;
    private EditText et_user_number;
    private EditText et_user_email;
    private EditText et_user_name;
    private EditText et_user_role;
    private EditText et_hospital_id;
    private EditText et_age;
    private EditText et_gender;
    private EditText et_user_last_name;
    private EditText et_user_zip_code;
    private LinearLayout ll_cancel_save,ll_gender_id;
    private RelativeLayout rl_update_button;
    private Prefs prefs;
    private Context mContext;
    private String  gender = "Male";
    private RadioButton rb_male;
    private RadioButton rb_female;
    private RadioGroup rg_gender;
    private Spinner state;
    private Spinner city;
    private String stateName="Select State",cityName="Select City";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_attender_user_profile);
        initViews();
        getUserProfileDataApiCall();
        BaseAttendanceActivity.checkedMenuItemForAttandance = BaseAttendanceActivity.navView.getMenu().findItem(Constants.PROFILE_NAV_ID);
        BaseAttendanceActivity.checkedMenuItemForAttandance.setChecked(true);
    }

    private void getUserProfileDataApiCall() {
        LocalModel.getInstance().showProgressDialog(mContext, mContext.getResources().getString(R.string.please_wait_msg), false);
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("user_id", new Prefs(mContext).getUserID());
        ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<UserProfileResponseModel> call = apiService.getUserProfileData(requestBody);
        call.enqueue(new Callback<UserProfileResponseModel>() {
            @Override
            public void onResponse(Call<UserProfileResponseModel> call, Response<UserProfileResponseModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        UserProfileResponseModel userProfileResponse = response.body();
                        if (userProfileResponse.isStatus()) {
                            if (userProfileResponse.getUserDetails() != null) {
                                ArrayList<UserDetailsModel> userDetails = userProfileResponse.getUserDetails();
                                LocalModel.getInstance().cancelProgressDialog();
                                setProfileData(userDetails);
                                disableEditField();
                            }
                        }
                        else{
                            LocalModel.getInstance().cancelProgressDialog();
                            Toast.makeText(mContext, userProfileResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        LocalModel.getInstance().cancelProgressDialog();
                        Toast.makeText(mContext, "Error may occurs!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    LocalModel.getInstance().cancelProgressDialog();
                    Toast.makeText(mContext, "Error may occurs!", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<UserProfileResponseModel> call, Throwable t) {
                LocalModel.getInstance().cancelProgressDialog();
            }
        });
    }

    private void setProfileData(ArrayList<UserDetailsModel> userDetails) {

        if(userDetails.get(0).getFirst_name()==null)
        { et_user_name.setText(prefs.getUserFirstname()); }
        else { et_user_name.setText(userDetails.get(0).getFirst_name()); }

        if(userDetails.get(0).getLast_name()==null)
        { et_user_last_name.setText(prefs.getUserLastname()); }
        else { et_user_last_name.setText(userDetails.get(0).getLast_name()); }

        if(userDetails.get(0).getEmail()==null){ et_user_email.setText(prefs.getUserEmailId()); }
        else { et_user_email.setText(userDetails.get(0).getEmail()); }

        if(userDetails.get(0).getPhone1()==null){ et_user_number.setText(prefs.getUserPhoneNumber()); }
        else { et_user_number.setText(userDetails.get(0).getPhone1()); }



        stateName = userDetails.get(0).getState();
        for(int stateItemPosition = 0; stateItemPosition<stateNameArray.length; stateItemPosition++){
            if(stateNameArray[stateItemPosition].equals(stateName)) {
                state.setSelection(stateItemPosition);
            }
        }

        cityName =  userDetails.get(0).getCity();
        for(int cityItemPosition = 0; cityItemPosition<cityNameArray.length; cityItemPosition++){
            if(cityNameArray[cityItemPosition].equals(cityName)) {
                city.setSelection(cityItemPosition);
            }
        }

        et_user_zip_code.setText(userDetails.get(0).getZip_code());
        et_user_address.setText(userDetails.get(0).getAddress());
        et_user_role.setText(userDetails.get(0).getRole_name());
        et_hospital_id.setText(userDetails.get(0).getHospital_id());
        et_age.setText(userDetails.get(0).getAge());

        gender = userDetails.get(0).getGender();
        if(gender!=null) {
            if (gender.equals("Male")) rb_male.setChecked(true);
            else rb_female.setChecked(true);
        }
        else {
            rb_male.setChecked(true);
        }
    }

    private void initViews() {
        mContext = AttendanceUserProfileActivity.this;
        prefs = new Prefs(mContext);
        et_user_address = findViewById(R.id.et_user_address);
        et_user_number = findViewById(R.id.et_user_number);
        et_user_email = findViewById(R.id.et_user_email);
        et_user_name = findViewById(R.id.et_user_name);
        et_user_role = findViewById(R.id.et_user_role);
        et_hospital_id = findViewById(R.id.et_hospital_id);
        et_age = findViewById(R.id.et_age);
        et_user_zip_code = findViewById(R.id.et_user_zip_code);
        et_user_last_name = findViewById(R.id.et_user_last_name);
        ll_cancel_save = findViewById(R.id.ll_cancel_save);
        ll_gender_id = findViewById(R.id.ll_gender_id);
        rl_update_button = findViewById(R.id.rl_update_button);
        rb_male = findViewById(R.id.rb_male);
        rb_female = findViewById(R.id.rb_female);
        rg_gender = findViewById(R.id.rg_gender);
        state = (Spinner) findViewById(R.id.tv_state);
        city = (Spinner) findViewById(R.id.tv_city);
        setDataInSpinner();
        getDataFromSpinner();
    }

    private void getDataFromSpinner() {
        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stateName = String.valueOf(stateNameArray[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cityName = String.valueOf(cityNameArray[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void setDataInSpinner() {
        // State list Data Set
        ArrayAdapter<String> stateArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, stateNameArray);
        stateArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        state.setAdapter(stateArrayAdapter);

        // City list Data Set
        ArrayAdapter<String> cityArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, cityNameArray);
        cityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        city.setAdapter(cityArrayAdapter);
    }

    public  void onProfileBackButtonClickAttender(View view){
        finish();

    }
    public  void  onUserProfileEditAttender(View view){
        et_user_address.setEnabled(true);
        et_user_number.setEnabled(true);
        et_user_email.setEnabled(true);
        et_user_name.setEnabled(true);
        et_user_last_name.setEnabled(true);
        et_user_role.setEnabled(false);
        et_hospital_id.setEnabled(true);
        et_age.setEnabled(true);
        et_user_zip_code.setEnabled(true);
        rb_female.setEnabled(true);
        rb_male.setEnabled(true);
        ll_gender_id.setEnabled(true);
        ll_cancel_save.setVisibility(View.VISIBLE);
    }
    private  void disableEditField(){
        et_user_address.setEnabled(false);
        et_user_number.setEnabled(false);
        et_user_email.setEnabled(false);
        et_user_name.setEnabled(false);
        et_user_last_name.setEnabled(false);
        et_user_role.setEnabled(false);
        et_hospital_id.setEnabled(false);
        et_age.setEnabled(false);
        rb_female.setEnabled(false);
        ll_gender_id.setEnabled(false);
        rb_male.setEnabled(false);
        et_user_zip_code.setEnabled(false);
        ll_cancel_save.setVisibility(View.GONE);
    }

    public void onUpdateUserProfileClickAttender(View view){
        LocalModel.getInstance().showProgressDialog(mContext, mContext.getResources().getString(R.string.please_wait_msg), false);
        Map<String, String> requestBody = new HashMap<>();

        requestBody.put("user_id", new Prefs(mContext).getUserID());
        requestBody.put("first_name", et_user_name.getText().toString());
        requestBody.put("last_name",et_user_last_name.getText().toString());
        requestBody.put("email",et_user_email.getText().toString());
        requestBody.put("age", et_age.getText().toString());
        requestBody.put("zip_code",et_user_zip_code.getText().toString());
        requestBody.put("phone1", et_user_number.getText().toString());

        if(stateName.equals("Select State")) requestBody.put("state","");
        else requestBody.put("state",stateName);
        if(cityName.equals("Select City")) requestBody.put("city","");
        else requestBody.put("city",cityName);

        requestBody.put("address", et_user_address.getText().toString());
        requestBody.put("gender",gender);
        requestBody.put("hospital_id", et_hospital_id.getText().toString());
        requestBody.put("role","");
        requestBody.put("role_name","Patient");

        ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<UserProfileResponseModel> call = apiService.updateUserProfileData(requestBody);
        call.enqueue(new Callback<UserProfileResponseModel>() {
            @Override
            public void onResponse(Call<UserProfileResponseModel> call, Response<UserProfileResponseModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        UserProfileResponseModel userProfileResponse = response.body();
                        if (userProfileResponse.isStatus()) {
                            LocalModel.getInstance().cancelProgressDialog();
                            disableEditField();
                            Toast.makeText(mContext, "  Successfully Updated  ", Toast.LENGTH_SHORT).show();
                            userDetailsDataSetInPrefs(userProfileResponse);
                        }
                        else{
                            LocalModel.getInstance().cancelProgressDialog();
                            Toast.makeText(mContext, userProfileResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        LocalModel.getInstance().cancelProgressDialog();
                        Toast.makeText(mContext, "Error may occurs!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    LocalModel.getInstance().cancelProgressDialog();
                    Toast.makeText(mContext, "Error may occurs!", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<UserProfileResponseModel> call, Throwable t) {
                LocalModel.getInstance().cancelProgressDialog();
            }
        });
    }

    private void userDetailsDataSetInPrefs(UserProfileResponseModel _userProfileResponse) {
        UserDetailsModel userDetail = new UserDetailsModel();
        ArrayList<UserDetailsModel> userDetailsArray = _userProfileResponse.getUserDetails();
        prefs.setUserID(userDetailsArray.get(0).getUser_id());
        prefs.setUserEmailId(userDetailsArray.get(0).getEmail());
        prefs.setUserPhoneNumber(userDetailsArray.get(0).getPhone1());
    }
    public void onGenderSelect(View view){
        switch(view.getId()){
            case R.id.rb_male:
                gender = "Male";
                break;
            case R.id.rb_female:
                gender = "Female";
                break;
        }
    }

    public void onCancelUserProfileClickAttender(View view){
        finish();
    }


    private void Disable_Or_Enable_RG_Button(RadioGroup radioGroup,boolean enable_or_disable){
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            ((RadioButton) radioGroup.getChildAt(i)).setEnabled(enable_or_disable);
        }
    }
}
