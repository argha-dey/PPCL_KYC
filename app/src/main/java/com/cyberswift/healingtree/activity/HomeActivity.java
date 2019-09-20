package com.cyberswift.healingtree.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.cyberswift.healingtree.R;
import com.cyberswift.healingtree.utils.Constants;

public class HomeActivity extends BaseActivity {
    private Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_home);

        initialViews();

        getSupportActionBar().setTitle("");
        checkedMenuItem = navView.getMenu().findItem(Constants.HOME_NAV_ID);
        checkedMenuItem.setChecked(true);
    }

    private void initialViews() {
        activity = HomeActivity.this;
    }


    public  void bookDoctorAppointment(View view){
        Intent intentHome = new Intent(activity, SpecialitiesActivity.class);
        startActivity(intentHome);
    }

    public void onBookHealthCheck (View view) {
        goToUrl ( "http://thehealingtree.org/hello_health");
    }

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    public void onHealingHomeCareClick(View view) {
        Intent i = new Intent(activity, HealingHomeCareActivity.class);
        startActivity(i);
    }


    public void OneHealingTreeMembershipClick(View view) {
    //    goToUrl ( "http://thehealingtree.org/club");

        Intent i = new Intent(activity, MembershipFacilityActivity.class);
        startActivity(i);
    }


    public void onOrderMedicines(View view) {
        Intent i = new Intent(activity, OrderMedicineActivity.class);
        startActivity(i);
    }

    public void onHealthLibrary(View view) {
        Toast.makeText(activity,"Available Shortly",Toast.LENGTH_LONG).show();
    }

    public void onHealthRecordHealingTree(View view) {
        Toast.makeText(activity,"Available Shortly",Toast.LENGTH_LONG).show();
    }


    public void onLocationHealingTree(View view) {
     /*   double latitude = 22.5387816;
        double longitude = 72.8947539;
        String label = "I'm Here!";
        String uriBegin = "geo:" + latitude + "," + longitude;
        String query = latitude + "," + longitude + "(" + label + ")";
        String encodedQuery = Uri.encode(query);
        String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
        Uri uri = Uri.parse(uriString);
        Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, uri);
        startActivity(mapIntent);*/

    /*    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr=22.5389689,72.8947186"));
        startActivity(intent);*/

        Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode("Shree Krishna Hospital, Anand - Sojitra Rd, Gokul Nagar, Karamsad, Anand, Gujarat 388325"));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);

    }

    public void goToProfilePage(View view){
        Intent intent = new Intent(activity,UserProfileActivity.class);
        startActivity(intent);

    }

}
