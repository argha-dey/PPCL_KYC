package com.cyberswift.healingtree.activity;

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
import android.view.*;
import android.widget.*;
import com.cyberswift.healingtree.R;
import com.cyberswift.healingtree.interfaces.CustomAlertDialogListener;
import com.cyberswift.healingtree.utils.Constants;
import com.cyberswift.healingtree.utils.Prefs;
import com.cyberswift.healingtree.utils.Utils;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Activity activity;
   // private SessionManager mSessionManager;
    private DrawerLayout drawerLayout;
    private FrameLayout container;
    public ImageView emergency_icon;
    public NavigationView navView;
    public MenuItem checkedMenuItem; // To control last item that was checked.
    public TextView tv_loading_msg;
    private RelativeLayout progressbar;
    public Toolbar toolbar;
    private Prefs prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

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
        activity = BaseActivity.this;
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
        container.addView(LayoutInflater.from(BaseActivity.this).inflate(resId, container, false));
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
        MenuItem home = menu.add(Menu.NONE, Constants.HOME_NAV_ID, Menu.NONE, Constants.HOME_NAV_NAME);
        home.setCheckable(true);
        home.setIcon(R.drawable.ic_house_blue);

        MenuItem userProfile = menu.add(Menu.NONE, Constants.PROFILE_NAV_ID, Menu.NONE, Constants.PROFILE_NAV_NAME);
        userProfile.setCheckable(true);
        userProfile.setIcon(R.drawable.ic_user_profile_blue);
        userProfile.setVisible(true);

        MenuItem notifications = menu.add(Menu.NONE, Constants.NOTIFICATION_NAV_ID, Menu.NONE, Constants.NOTIFICATION_NAV_NAME);
        notifications.setCheckable(true);
        notifications.setIcon(R.drawable.ic_notification_blue);
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
            case Constants.HOME_NAV_NAME:
                Utils.launchActivityWithFinish(activity, HomeActivity.class);
                drawerLayout.closeDrawer(Gravity.START);  // Close drawer when item is tapped
                break;

            case Constants.PROFILE_NAV_NAME:
                Utils.launchActivityWithFinish(activity, UserProfileActivity.class);
                drawerLayout.closeDrawer(Gravity.START);  // Close drawer when item is tapped
                break;

            case Constants.NOTIFICATION_NAV_NAME:
                Utils.launchActivityWithFinish(activity, NotificationActivity.class);
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
        prefs.clearPrefdata();
        gotoLoginPage();
    }

    private void gotoLoginPage() {
        Intent intentHome = new  Intent(activity, LoginActivity.class);
        intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intentHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intentHome);
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



    public void onEmargencyClick(View view) {
        if (Utils.checkAndRequestAllPermissions(activity)) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:02692228100"));
             startActivity(callIntent);
        }
    }

}
