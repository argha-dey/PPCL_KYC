package com.cyberswift.healingtree.patientModuleActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import com.cyberswift.healingtree.R;
import com.cyberswift.healingtree.adapters.FragmentPagerAdapterForTimeSlot;
import com.cyberswift.healingtree.fragments.FragmentTimeSlot;
import com.cyberswift.healingtree.interfaces.CustomAlertDialogListener;
import com.cyberswift.healingtree.model.DoctorListModel;
import com.cyberswift.healingtree.model.DoctorTimeSlotDayList;
import com.cyberswift.healingtree.model.DoctorTimeslotResponceModel;
import com.cyberswift.healingtree.model.TabDateModel;
import com.cyberswift.healingtree.retrofit.ApiClient;
import com.cyberswift.healingtree.retrofit.ApiInterface;
import com.cyberswift.healingtree.utils.*;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DoctorTimeSlot extends AppCompatActivity {
    private Activity activity;
    private TabLayout tabs;
    private CustomViewPager customViewPager;
    private FragmentPagerAdapterForTimeSlot adapter;
    private String tab_header;
    //   private ArrayList<DoctorTimeSlotDayList> doctorTimeslotMainArraylist;
    private DoctorListModel doctorDetails;
    private TextView doctorExp, doctordepart, doctorName, doctorSpl;
    private ArrayList<DoctorTimeSlotDayList> weeklyTimeSlot;
    private HashMap<String,ArrayList<DoctorListModel>> weeklyTimeSlotHashMap;
    private ArrayList<TabDateModel> tabDateArrayList;
    private Prefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_time_slot);
        getDataFromBundle();
        initViewsDoctorTimeSlot();
        setData();
        doctorTimeSlotApiCall();
        // setupViewPager();
    }

    private void setData() {

        doctorExp.setText("Exp: "+doctorDetails.getExperience());
        doctordepart.setText(doctorDetails.getDeptName());
        doctorName.setText(doctorDetails.getDocName());
        doctorSpl.setText(doctorDetails.getQualification());
    }

    public void  onSelectDateFromCal(View view){
        final Calendar sCalendar = Calendar.getInstance();
        sCalendar.add(Calendar.DATE, +7);
        DatePickerDialog sDatePickerDialog = new DatePickerDialog(activity,
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        sCalendar.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat df =  new SimpleDateFormat("yyyy-MM-dd");
                        String surveyDate = df.format(sCalendar.getTime());
                    }
                }, sCalendar.get(Calendar.YEAR), sCalendar.get(Calendar.MONTH), sCalendar.get(Calendar.DAY_OF_MONTH));

        sDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        sDatePickerDialog.getDatePicker().setMaxDate(sCalendar.getTimeInMillis());
        sDatePickerDialog.show();
    }

    private void doctorTimeSlotApiCall() {
        LocalModel.getInstance().showProgressDialog(this, this.getResources().getString(R.string.please_wait_msg), false);
        weeklyTimeSlot = new ArrayList<>();
        weeklyTimeSlotHashMap = new HashMap<>();
        tabDateArrayList = new ArrayList<>();
        final Map<String, Object> requestBody = new HashMap<>();
           requestBody.put("doc_id", doctorDetails.getDocId());
           if(Utils.dateCompar(doctorDetails.getDate())){
               requestBody.put("date", doctorDetails.getDate());
           }
           else {
               Calendar c = Calendar.getInstance();
               SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
               String getCurrentDateTime = sdf.format(c.getTime());
               requestBody.put("date",getCurrentDateTime);
           }
     //  requestBody.put("doc_id","79");
     //  requestBody.put("date","2019-09-30");

        ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
        System.out.println("request : "+new JSONObject(requestBody));
        Call<DoctorTimeslotResponceModel> call = apiService.doctorTimeSlot(requestBody);
        call.enqueue(new Callback<DoctorTimeslotResponceModel>() {
            @Override
            public void onResponse(Call<DoctorTimeslotResponceModel> call, Response<DoctorTimeslotResponceModel> response) {
                if (response.body() != null) {
                    DoctorTimeslotResponceModel doctorTimeslotResponceModel = response.body();
                    if (doctorTimeslotResponceModel.getStatus()) {
                        tabDateArrayList = doctorTimeslotResponceModel.getDoctorTimeSlotDayList().getTabDateList();
                        for(int tabPosition = 0 ;tabPosition< tabDateArrayList.size();tabPosition++)
                        {
                            switch (tabDateArrayList.get(tabPosition).getT_Day()){
                                case "MON":
                                    weeklyTimeSlotHashMap.put("MON",doctorTimeslotResponceModel.getDoctorTimeSlotDayList().getMON());
                                    break;
                                case "TUE":
                                    weeklyTimeSlotHashMap.put("TUE",doctorTimeslotResponceModel.getDoctorTimeSlotDayList().getTUE());
                                    break;
                                case "WED":
                                    weeklyTimeSlotHashMap.put("WED",doctorTimeslotResponceModel.getDoctorTimeSlotDayList().getWED());
                                    break;
                                case "THU":
                                    weeklyTimeSlotHashMap.put("THU",doctorTimeslotResponceModel.getDoctorTimeSlotDayList().getTHU());
                                    break;
                                case "FRI":
                                    weeklyTimeSlotHashMap.put("FRI",doctorTimeslotResponceModel.getDoctorTimeSlotDayList().getFRI());
                                    break;
                                case "SAT":
                                    weeklyTimeSlotHashMap.put("SAT",doctorTimeslotResponceModel.getDoctorTimeSlotDayList().getSAT());
                                    break;

                            }

                        }
                      //  setupViewPager(tabDateArrayList,weeklyTimeSlotHashMap);
                        setupViewPager(tabDateArrayList,weeklyTimeSlotHashMap);
                        LocalModel.getInstance().cancelProgressDialog();
                    }
                    else
                        LocalModel.getInstance().cancelProgressDialog();
                }
                else
                    LocalModel.getInstance().cancelProgressDialog();
            }

            @Override
            public void onFailure(Call<DoctorTimeslotResponceModel> call, Throwable t) {
                System.out.println("Response Fail");
            }
        });
    }


    // TODO: Time slot Set in Fragment Adapter ...
    private void setupViewPager( final  ArrayList<TabDateModel> tabDateArrayList,final HashMap<String, ArrayList<DoctorListModel>> dataTimeSlot) {
        adapter = new FragmentPagerAdapterForTimeSlot(getSupportFragmentManager());
        for (int i = 0; i < tabDateArrayList.size(); i++) {
            String date = Utils.changeDateNTimeFormat(tabDateArrayList.get(i).getT_Date(), Constants.DATE_TIME_FORMAT_1,Constants.DATE_TIME_FORMAT_10);
            tab_header = tabDateArrayList.get(i).getT_Day()+"\n"+date;
            FragmentTimeSlot fragment;
            if(i==0) { fragment = FragmentTimeSlot.newInstance(dataTimeSlot.get(tabDateArrayList.get(i).getT_Day())); }
            else {fragment = FragmentTimeSlot.newInstance(new ArrayList<DoctorListModel>()); }
            adapter.addFragment(fragment, tab_header);
        }
        customViewPager.setAdapter(adapter);
        tabs.setupWithViewPager(customViewPager);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(final TabLayout.Tab tab) {
                LocalModel.getInstance().showProgressDialog(activity, activity.getResources().getString(R.string.please_wait_msg), false);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        prefs.setDoctorBookSelectTime("");
                        prefs.setDoctorBookSelectDate(tabDateArrayList.get(tab.getPosition()).getT_Date());
                        adapter.getItem(tab.getPosition()).setTimeSlotModelArrayList(dataTimeSlot.get(tabDateArrayList.get(tab.getPosition()).getT_Day()).get(0).getTime_slot());
                        LocalModel.getInstance().cancelProgressDialog();
                    }
                }, 400);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }



    private void initViewsDoctorTimeSlot() {
        activity = DoctorTimeSlot.this;
        prefs = new Prefs(activity);
        prefs.setDoctorBookSelectTime("");
        customViewPager = findViewById(R.id.viewpager_time_slot);
        tabs = findViewById(R.id.tab_date);

        doctorExp = findViewById(R.id.doctorExp);
        doctordepart = findViewById(R.id.doctordepart);
        doctorName = findViewById(R.id.doctorName);
        doctorSpl = findViewById(R.id.doctorSpl);
       // Scroll Disable
        customViewPager.disableScroll(true);

    }


    public void onBookingClick(View view) {
        if(prefs.getDoctorBookSelectTime().isEmpty() || prefs.getDoctorBookSelectTime()==null) {
            Utils.showCustomAlertDialog(activity, true, "Booking Info!", true, "Please choose a time slot before confirm your booking.",
                    true, "OK", false, "", true,
                    new CustomAlertDialogListener() {
                        @Override
                        public void positiveButtonWork() {

                        }

                        @Override
                        public void negativeButtonWork() {
                        }
                    });

        }else {
            LocalModel.getInstance().showProgressDialog(activity, activity.getResources().getString(R.string.please_wait_msg), false);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    LocalModel.getInstance().cancelProgressDialog();
                    Intent intentBooking = new Intent(activity, BookingActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("DOCTOR_DETAILS", doctorDetails);
                    intentBooking.putExtras(bundle);
                    startActivity(intentBooking);
                }
            }, 500);

        }
    }

    public void onTimeSlotBackButton(View view) {
        finish();
    }


    private void getDataFromBundle() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            doctorDetails = extras.getParcelable("DOCTOR_DETAILS");
        }

    }

}




