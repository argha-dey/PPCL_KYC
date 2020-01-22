package com.cyberswift.healingtreeorg.homeCareAttendanceModuleActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cyberswift.healingtreeorg.R;
import com.cyberswift.healingtreeorg.interfaces.CustomAlertDialogListener;
import com.cyberswift.healingtreeorg.model.LogOutResponseModel;
import com.cyberswift.healingtreeorg.patientModuleActivity.LoginActivity;
import com.cyberswift.healingtreeorg.retrofit.ApiClient;
import com.cyberswift.healingtreeorg.retrofit.ApiInterface;
import com.cyberswift.healingtreeorg.utils.Constants;
import com.cyberswift.healingtreeorg.utils.LocalModel;
import com.cyberswift.healingtreeorg.utils.Prefs;
import com.cyberswift.healingtreeorg.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseAttendanceActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Activity activity;
   // private SessionManager mSessionManager;
    private DrawerLayout drawerLayout;
    private FrameLayout container;
    public ImageView emergency_icon;

    public static NavigationView navView;
    public static MenuItem checkedMenuItemForAttandance; // To control last item that was checked.
    public TextView tv_loading_msg;
    private RelativeLayout progressbar;
    public Toolbar toolbar;
    private Prefs prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list_base_activity);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.menu_nav_drawer);

        initializeViews();
        setNavHeaderData();
        addMenuItemsInNavDrawer();
        navView.setNavigationItemSelectedListener(this);

    }


    private void initializeViews() {
        activity = BaseAttendanceActivity.this;
        prefs = new Prefs(activity);
        emergency_icon = findViewById(R.id.emergency_icon);
      //  mSessionManager = new SessionManager(activity);
        drawerLayout = findViewById(R.id.drawer_layout);
        container = findViewById(R.id.container_frame);
        navView = findViewById(R.id.nav_view);
       // tv_loading_msg = findViewById(R.id.tv_loading_msg);
       //  progressbar = findViewById(R.id.progressbar);
    }


    /***
     * Set the view of other activities into this frame container
     * @param resId
     */
    public void setContentLayout(int resId) {
        container.removeAllViews();
        container.addView(LayoutInflater.from(BaseAttendanceActivity.this).inflate(resId, container, false));
    }


    private void setNavHeaderData() {
       TextView tv_user_name = navView.getHeaderView(0).findViewById(R.id.tv_user_name_nav);
        tv_user_name.setText(prefs.getUserFirstname());

   /*     TextView tv_user_email_nav = navView.getHeaderView(0).findViewById(R.id.tv_user_email_nav);
        tv_user_email_nav.setText(prefs.getUserEmailId());*/

        String currAppVersion = "Version " + Utils.getAppVersion(activity);
        TextView tv_app_version = navView.findViewById(R.id.tv_app_version);
        tv_app_version.setText(currAppVersion);
    }


    private void addMenuItemsInNavDrawer() {
        Menu menu = navView.getMenu();
        MenuItem home = menu.add(Menu.NONE, Constants.ASSIGNED_TASK_LIST_ID, Menu.NONE, Constants.ASSIGNED_TASK_LIST_NAME);
        home.setCheckable(true);
        home.setIcon(R.drawable.ic_home_black_24dp);

        MenuItem userProfile = menu.add(Menu.NONE, Constants.PROFILE_NAV_ID, Menu.NONE, Constants.PROFILE_NAV_NAME);
        userProfile.setCheckable(true);
        userProfile.setIcon(R.drawable.ic_user_profile_blue);
        userProfile.setVisible(true);

        MenuItem history = menu.add(Menu.NONE, Constants.HISTORY_NAV_ID, Menu.NONE, Constants.HISTORY_NAV_NAME);
        history.setCheckable(true);
        history.setIcon(R.drawable.history_48);
        history.setVisible(true);

        MenuItem notifications = menu.add(Menu.NONE, Constants.NOTIFICATION_NAV_ID, Menu.NONE, Constants.NOTIFICATION_NAV_NAME);
        notifications.setCheckable(true);
        notifications.setIcon(R.drawable.ic_notifications_black_24dp);
        notifications.setVisible(true);


        MenuItem logout = menu.add(1, Constants.LOGOUT_NAV_ID, Menu.NONE, Constants.LOGOUT_NAV_NAME);
        logout.setCheckable(true);
        logout.setIcon(R.drawable.logout);
        logout.setVisible(true);
        navView.invalidate();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getTitle().toString()) {
            case Constants.ASSIGNED_TASK_LIST_NAME:
                Utils.launchActivityWithFinish(activity, AssignedTaskActivity.class);
                drawerLayout.closeDrawer(Gravity.START);  // Close drawer when item is tapped
                break;

           case Constants.PROFILE_NAV_NAME:
                Utils.launchActivity(activity, AttendanceUserProfileActivity.class);
                drawerLayout.closeDrawer(Gravity.START);  // Close drawer when item is tapped
                break;
            case Constants.NOTIFICATION_NAV_NAME:
                Utils.launchActivity(activity, AttenderNotificationActivity.class);
                drawerLayout.closeDrawer(Gravity.START);  // Close drawer when item is tapped
                break;
            case Constants.HISTORY_NAV_NAME:
                Utils.launchActivity(activity, HistoryMedicineDeliveryActivity.class);
                drawerLayout.closeDrawer(Gravity.START);  // Close drawer when item is tapped
                break;

            case Constants.LOGOUT_NAV_NAME:
                Utils.showCustomAlertDialog(activity, true, "Logout", true, "Are you sure you want to logout?",
                        true, "Logout", true, "Cancel", true,
                        new CustomAlertDialogListener() {
                            @Override
                            public void positiveButtonWork() {
                                drawerLayout.closeDrawer(Gravity.START);
                                callLogoutService();
                            }

                            @Override
                            public void negativeButtonWork() { }
                        });
                break;

        }
        return false;
    }

    public void callLogoutService() {
        gotoLoginPage();
    }

    private void gotoLoginPage() {
        LocalModel.getInstance().showProgressDialog(activity, activity.getResources().getString(R.string.please_wait_msg), false);
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("user_id", new Prefs(activity).getUserID());
        requestBody.put("deviceId",Utils.getMobileDeviceID(activity));
        ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<LogOutResponseModel> call = apiService.postLogOut(requestBody);
        call.enqueue(new Callback<LogOutResponseModel>() {
            @Override
            public void onResponse(Call<LogOutResponseModel> call, Response<LogOutResponseModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        LogOutResponseModel logoutResponse = response.body();
                        if (logoutResponse.isStatus()) {
                            prefs.clearPrefdata();
                            LocalModel.getInstance().cancelProgressDialog();
                            Toast.makeText(activity, logoutResponse.getMessage(), Toast.LENGTH_LONG).show();
                            Intent intentHome = new  Intent(activity, LoginActivity.class);
                            intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intentHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intentHome);
                        }
                        else{
                            LocalModel.getInstance().cancelProgressDialog();
                            Toast.makeText(activity, logoutResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        LocalModel.getInstance().cancelProgressDialog();
                        Toast.makeText(activity, "Error may occurs!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    LocalModel.getInstance().cancelProgressDialog();
                }
            }


            @Override
            public void onFailure(Call<LogOutResponseModel> call, Throwable t) {
                LocalModel.getInstance().cancelProgressDialog();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //TODO: now its disable
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }



    public void onEmergencyClickForAttender(View view) {
        if (Utils.checkAndRequestAllPermissions(activity)) {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:02692228100"));
             startActivity(callIntent);
        }
    }

}
