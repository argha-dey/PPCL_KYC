package com.cyberswift.healingtreeorg.patientModuleActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.cyberswift.healingtreeorg.R;
import com.cyberswift.healingtreeorg.dropdown.DropDownViewForXML;
import com.cyberswift.healingtreeorg.model.HomeCarePackageBooking;
import com.cyberswift.healingtreeorg.model.MemberShipClubCostResponseModel;
import com.cyberswift.healingtreeorg.model.MemberShipCostModel;
import com.cyberswift.healingtreeorg.retrofit.ApiClient;
import com.cyberswift.healingtreeorg.retrofit.ApiInterface;
import com.cyberswift.healingtreeorg.utils.LocalModel;
import com.cyberswift.healingtreeorg.utils.Prefs;
import com.cyberswift.healingtreeorg.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemberShipFacilityBookingActivity extends AppCompatActivity {
    public static String[] noOfFamilyMemberArray = {"Single","Family(2+2)","Couple Packages(1+1)"};
    public static String[] memberShipDurationArray = {"Annual","Lifetime"};
    private DropDownViewForXML dropDown_select_number_of_family_members;
    private DropDownViewForXML dropDown_duration_of_member_ship;
    private TextView tv_member_ship_amount;
    private TextView tv_membership_package_name;
    private Context mContext;
    private  String noOfFamilyMember = "";
    private  String clubMemberShipDuration = "";
    private String memberShipClubName = "";
    private  String memberShipClubId = "";
    private String clubMemberShipCost = "";
    private Prefs mPrefs;
    private ArrayList<MemberShipCostModel> memberShipCostList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_ship_facility_booking);
        mContext = MemberShipFacilityBookingActivity.this;
        getValueFromBundle();
        initViewMemberShip();
        populateStaticNumberOfFamilyMember();

    }

    private void getValueFromBundle() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
                memberShipClubName = extras.getString("MemberShipClubName");
             //   memberShipClubId = extras.getString("HelloHealthPackageId");
        }
    }

    private void initViewMemberShip() {
        mPrefs = new Prefs(mContext);
        dropDown_select_number_of_family_members = findViewById(R.id.dropDown_select_number_of_family_members);
        dropDown_duration_of_member_ship = findViewById(R.id.dropDown_duration_of_member_ship);
        tv_member_ship_amount = findViewById(R.id.tv_member_ship_amount);
        tv_membership_package_name = findViewById(R.id.tv_membership_package_name);
        // set MemberShip Club Package name
        tv_membership_package_name.setText(memberShipClubName);
        // select No of  Member
        dropDown_select_number_of_family_members.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                noOfFamilyMember = noOfFamilyMemberArray[position];
                populateStaticMemberShipDuration();
                tv_member_ship_amount.setText("");
            }
        });

        // Select duration of Member ship club
        dropDown_duration_of_member_ship.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clubMemberShipDuration = memberShipDurationArray[position];
                membershipClubPackageCostApiCall();

            }
        });




    }

    private void membershipClubPackageCostApiCall() {
        if(Utils.isOnline(mContext)){
            LocalModel.getInstance().showProgressDialog(this, this.getResources().getString(R.string.please_wait_msg), false);
            Map<String, String> request = new HashMap<>();
            request.put("package_name",memberShipClubName);
            request.put("facility_for",noOfFamilyMember);
            request.put("span",clubMemberShipDuration);

            ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
            Call<MemberShipClubCostResponseModel> call = apiService.getMemberShipCost(request);
            call.enqueue(new Callback<MemberShipClubCostResponseModel>() {
                @Override
                public void onResponse(Call<MemberShipClubCostResponseModel> call, Response<MemberShipClubCostResponseModel> response) {

                    if (response.body() != null) {
                        MemberShipClubCostResponseModel memberShipCostResponse = response.body();
                        if (memberShipCostResponse.isStatus()) {
                            if (memberShipCostResponse.getMemberShipClubCostList() != null) {
                                LocalModel.getInstance().cancelProgressDialog();
                                memberShipCostList = memberShipCostResponse.getMemberShipClubCostList();
                                clubMemberShipCost = memberShipCostList.get(0).getMember_subscription_amount();
                                tv_member_ship_amount.setText("₹"+memberShipCostList.get(0).getMember_subscription_amount());
                                LocalModel.getInstance().cancelProgressDialog();
                            }
                        }
                        else
                            LocalModel.getInstance().cancelProgressDialog();
                    } else
                        LocalModel.getInstance().cancelProgressDialog();

                }


                @Override
                public void onFailure(Call<MemberShipClubCostResponseModel> call, Throwable t) {
                    LocalModel.getInstance().cancelProgressDialog();
                }
            });
        }
    }

    private void populateStaticNumberOfFamilyMember() {
        if (noOfFamilyMemberArray.length > 0) {
            dropDown_select_number_of_family_members.setEnabled(true);
            dropDown_select_number_of_family_members.setHint("Select");
            dropDown_select_number_of_family_members.setItems(noOfFamilyMemberArray);
        }
    }

    private void populateStaticMemberShipDuration() {
        if (memberShipDurationArray.length > 0) {
            dropDown_duration_of_member_ship.setEnabled(true);
            dropDown_duration_of_member_ship.setHint("Select");
            dropDown_duration_of_member_ship.setItems(memberShipDurationArray);
        }
    }

    public  void onClubMemberShipBooking(View view){
        if(mPrefs.getUserEmailId().equals("")||mPrefs.getUserEmailId().isEmpty()){
            Utils.showAlertDialogWithOkButton(mContext, "Update User Profile!", "Please update your profile before booking a Club Member Ship");
        }
        else if(mPrefs.getUserPhoneNumber().equals("")||mPrefs.getUserPhoneNumber().isEmpty()){
            Utils.showAlertDialogWithOkButton(mContext, "Update User Profile!", "Please update your profile before booking a Club Member Ship ");
        }
        else if(Utils.isOnline(mContext)){
            LocalModel.getInstance().showProgressDialog(this, this.getResources().getString(R.string.please_wait_msg), false);
            Map<String, String> request = new HashMap<>();
            request.put("USR_USER_ID",mPrefs.getUserID());
            request.put("club_membership_name",memberShipClubName);
            request.put("MMS_FACILITY",noOfFamilyMember);
            request.put("MMS_SPAN",clubMemberShipDuration);
            request.put("MMS_AMOUNT",clubMemberShipCost);


            ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
            Call<HomeCarePackageBooking> call = apiService.getHelloHealthClubMemberShipBooking(request);
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

    public  void onClickBackClubMemberShipPackage(View view){
        finish();
    }


    public  void onTalkToUsForBookMembershipPackage(View view){
        if (Utils.checkAndRequestAllPermissions(mContext)) {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:02692228666"));
            mContext.startActivity(callIntent);
        }
    }

}
