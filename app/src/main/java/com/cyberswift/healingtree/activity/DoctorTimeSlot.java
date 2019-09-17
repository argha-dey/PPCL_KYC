package com.cyberswift.healingtree.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
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
import com.cyberswift.healingtree.utils.Constants;
import com.cyberswift.healingtree.utils.Prefs;
import com.cyberswift.healingtree.utils.Utils;
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
    private ViewPager viewPager;
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

        doctorExp.setText(doctorDetails.getExperience());
        doctordepart.setText(doctorDetails.getDeptName());
        doctorName.setText(doctorDetails.getDocName());
        doctorSpl.setText(doctorDetails.getQualification());
    }

    public void  onSelectDateFromCal(View view){
        final Calendar sCalendar = Calendar.getInstance();
        DatePickerDialog sDatePickerDialog = new DatePickerDialog(activity,R.style.DatePickerDialogTheme,
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        sCalendar.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat df =  new SimpleDateFormat("yyyy-MM-dd");
                        String surveyDate = df.format(sCalendar.getTime());
                       // tv_date.setText(surveyDate);
                    }
                }, sCalendar.get(Calendar.YEAR), sCalendar.get(Calendar.MONTH), sCalendar.get(Calendar.DAY_OF_MONTH));
        sDatePickerDialog.show();
    }

    private void doctorTimeSlotApiCall() {
        weeklyTimeSlot = new ArrayList<>();
        weeklyTimeSlotHashMap = new HashMap<>();
        tabDateArrayList = new ArrayList<>();
        final Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("doc_id", doctorDetails.getDocId());
        requestBody.put("date", doctorDetails.getDate());
        ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
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
                                    weeklyTimeSlotHashMap.put("WEN",doctorTimeslotResponceModel.getDoctorTimeSlotDayList().getWED());
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
                        setupViewPager(tabDateArrayList,weeklyTimeSlotHashMap);
                    }
                }
            }

            @Override
            public void onFailure(Call<DoctorTimeslotResponceModel> call, Throwable t) {

            }
        });
    }

    private void setupViewPager(ArrayList<TabDateModel> tabDateArrayList, HashMap<String, ArrayList<DoctorListModel>> dataTimeSlot) {
        this.adapter = new FragmentPagerAdapterForTimeSlot(getSupportFragmentManager());
        for (int i = 0; i < tabDateArrayList.size(); i++) {
            String date = Utils.changeDateNTimeFormat(tabDateArrayList.get(i).getT_Date(), Constants.DATE_TIME_FORMAT_1,Constants.DATE_TIME_FORMAT_10);
            this.tab_header = tabDateArrayList.get(i).getT_Day()+"\n"+date;
            this.adapter.addFragment(FragmentTimeSlot.newInstance(dataTimeSlot.get(tabDateArrayList.get(i).getT_Day())), this.tab_header);
        }
        viewPager.setAdapter(this.adapter);
        tabs.setupWithViewPager(this.viewPager);
        tabs.setTag(this.viewPager);
        tabs.getTabAt(0).select();


    }

    private void initViewsDoctorTimeSlot() {
        activity = DoctorTimeSlot.this;
        prefs = new Prefs(activity);
        viewPager = findViewById(R.id.viewpager);
        tabs = findViewById(R.id.tab_date);

        doctorExp = findViewById(R.id.doctorExp);
        doctordepart = findViewById(R.id.doctordepart);
        doctorName = findViewById(R.id.doctorName);
        doctorSpl = findViewById(R.id.doctorSpl);

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


    Intent intentHome = new Intent(activity, BookingActivity.class);
    Bundle bundle = new Bundle();
    bundle.putParcelable("DOCTOR_DETAILS", doctorDetails);
    intentHome.putExtras(bundle);
    startActivity(intentHome);
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




