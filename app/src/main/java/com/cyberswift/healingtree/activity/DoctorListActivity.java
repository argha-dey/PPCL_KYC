package com.cyberswift.healingtree.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import com.cyberswift.healingtree.R;
import com.cyberswift.healingtree.adapters.AllDoctorListAdapter;
import com.cyberswift.healingtree.adapters.DoctorListAdapter;
import com.cyberswift.healingtree.fragments.*;
import com.cyberswift.healingtree.model.AllDoctorListResponceModel;
import com.cyberswift.healingtree.model.DoctorListResponseModel;
import com.cyberswift.healingtree.retrofit.ApiClient;
import com.cyberswift.healingtree.retrofit.ApiInterface;
import com.cyberswift.healingtree.utils.LocalModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.SimpleDateFormat;
import java.util.*;

public class DoctorListActivity extends AppCompatActivity {

    private Activity activity;
    private EditText et_search_doctors;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentMonday fragmentMonday;
    private FragmentTuesday fragmentTuesday;
    private FragmentWednesday fragmentWednesday;
    private FragmentThursday fragmentThursday;
    private FragmentFriday fragmentFriday;
    private FragmentSaturday fragmentSaturday;
    private  AllDoctorListFragment fragmentAllDoctorList;
    public static DoctorListAdapter mDoctorListAdapter;
    public  static AllDoctorListAdapter mAllDoctorListAdapter;
    private String departmentId ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);
        getBundle();
        initializeViews();
        fetchDoctorListAll();
     //   fetchDoctorListDay();
        setupViewPager(viewPager);

        et_search_doctors.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //mDoctorListAdapter.filter(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Log.d("@@@ Filter text", s.toString());

                if(tabLayout.getSelectedTabPosition() == 0) {
                    mAllDoctorListAdapter = fragmentAllDoctorList.getAdapterCurrentInstance();
                    if (mAllDoctorListAdapter != null)
                        mAllDoctorListAdapter.filter(s.toString());
                }
                else {
                    if (tabLayout.getSelectedTabPosition() == 1)
                        mDoctorListAdapter = fragmentMonday.getAdapterCurrentInstance();
                    if (mDoctorListAdapter != null)
                        mDoctorListAdapter.filter(s.toString());
                    else
                        Log.d("@@@ ", "mDoctorListAdapter == null");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //mDoctorListAdapter.filter(s.toString());
            }
        });


    }

    private void fetchDoctorListAll() {
        LocalModel.getInstance().showProgressDialog(this, this.getResources().getString(R.string.please_wait_msg), false);
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("dept_id",departmentId);
        ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<AllDoctorListResponceModel> call = apiService.allDoctorList(requestBody);
        call.enqueue(new Callback<AllDoctorListResponceModel>() {
            @Override
            public void onResponse(Call<AllDoctorListResponceModel> call, Response<AllDoctorListResponceModel> response) {
                if (response.body() != null) {
                    AllDoctorListResponceModel allDoctorListResponceModel = response.body();
                    if (allDoctorListResponceModel.isStatus()) {
                        if (allDoctorListResponceModel.getDoctorListModel() != null && allDoctorListResponceModel.getDoctorListModel().size() > 0) {
                            fragmentAllDoctorList.updateDrList(allDoctorListResponceModel.getDoctorListModel());
                            LocalModel.getInstance().cancelProgressDialog();
                        }
                    }
                    LocalModel.getInstance().cancelProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<AllDoctorListResponceModel> call, Throwable t) {
                LocalModel.getInstance().cancelProgressDialog();
            }
        });
    }


    // initializeViews
    private void initializeViews() {
        activity = DoctorListActivity.this;
        et_search_doctors = findViewById(R.id.et_search_doctors);
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabLayout);
    }
    private void getBundle() {
        departmentId = getIntent().getStringExtra("DEPARTMENT_ID");
    }

    public void onBackButtonClick(View view) {
        finish();
    }


    public void onSearchClick(View view) {
        Toast.makeText(activity, "Coming soon...", Toast.LENGTH_SHORT).show();
    }


    public void onCalendarClick(View view) {
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


    public void onFilterClick(View view) {
        Toast.makeText(activity, "Coming soon...", Toast.LENGTH_SHORT).show();
    }


    // setup view pager
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());  // /*getFragmentManager*/getChildFragmentManager()
        fragmentAllDoctorList = new AllDoctorListFragment();
     //   fragmentMonday = new FragmentMonday();
     //   fragmentTuesday = new FragmentTuesday();
      //  fragmentWednesday = new FragmentWednesday();
     //   fragmentThursday = new FragmentThursday();
     //   fragmentFriday = new FragmentFriday();
      //  fragmentSaturday = new FragmentSaturday();
       adapter.addFrag(fragmentAllDoctorList, "All Doctor List");

      //  adapter.addFrag(fragmentMonday, "MON");
      //  adapter.addFrag(fragmentTuesday, "TUE");
      //  adapter.addFrag(fragmentWednesday, "WED");
     //   adapter.addFrag(fragmentThursday, "THU");
      //  adapter.addFrag(fragmentFriday, "FRI");
      //  adapter.addFrag(fragmentSaturday, "SAT");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        //tabLayout.setTag(this.viewPager);
        //tabLayout.getTabAt(1).select();


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // called when tab selected
                Log.d("@@@ Tab ", "selected postion==>" + String.valueOf(tab.getPosition()));
                if (tab.isSelected()) {
                    switch (tab.getPosition()) {
                        case 0:
                            mAllDoctorListAdapter = fragmentAllDoctorList.getAdapterCurrentInstance();
                            break;
                   /*     case 1:
                            mDoctorListAdapter = fragmentMonday.getAdapterCurrentInstance();
                            break;
                        case 2:
                            mDoctorListAdapter = fragmentTuesday.getAdapterCurrentInstance();
                            break;
                        case 3:
                            mDoctorListAdapter = fragmentWednesday.getAdapterCurrentInstance();
                            break;
                        case 4:
                            mDoctorListAdapter = fragmentThursday.getAdapterCurrentInstance();
                            break;
                        case 5:
                            mDoctorListAdapter = fragmentFriday.getAdapterCurrentInstance();
                            break;
                        case 6:
                            mDoctorListAdapter = fragmentSaturday.getAdapterCurrentInstance();
                            break;
                        default:
                            mDoctorListAdapter = fragmentMonday.getAdapterCurrentInstance();
                            break;*/
                    }
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // called when tab unselected
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // called when a tab is reselected
            }
        });
    }


    // view pager adapter
    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        public void addFragWithOutHeader(Fragment fragment) {
            mFragmentList.add(fragment);

        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    public void fetchDoctorListDay() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("dept_id",departmentId);
        ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<DoctorListResponseModel> call = apiService.doctorList(requestBody);
        call.enqueue(new Callback<DoctorListResponseModel>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onResponse(@NonNull Call<DoctorListResponseModel> call, @NonNull Response<DoctorListResponseModel> response) {
                try {
                    if (response.body() != null) {
                        DoctorListResponseModel doctorListResponseModel = response.body();
                        if (doctorListResponseModel.isStatus()){
                            if (doctorListResponseModel.getDoctorListModel().getMondayDrList() != null && doctorListResponseModel.getDoctorListModel().getMondayDrList().size() > 0)
                                fragmentMonday.updateDrList(doctorListResponseModel.getDoctorListModel().getMondayDrList());
                            if (doctorListResponseModel.getDoctorListModel().getTuesdayDrList() != null && doctorListResponseModel.getDoctorListModel().getTuesdayDrList().size() > 0)
                                fragmentTuesday.updateDrList(doctorListResponseModel.getDoctorListModel().getTuesdayDrList());
                            if (doctorListResponseModel.getDoctorListModel().getWednesdayDrList() != null && doctorListResponseModel.getDoctorListModel().getWednesdayDrList().size() > 0)
                                fragmentWednesday.updateDrList(doctorListResponseModel.getDoctorListModel().getWednesdayDrList());
                            if (doctorListResponseModel.getDoctorListModel().getThursdayDrList() != null && doctorListResponseModel.getDoctorListModel().getThursdayDrList().size() > 0)
                                fragmentThursday.updateDrList(doctorListResponseModel.getDoctorListModel().getThursdayDrList());
                            if (doctorListResponseModel.getDoctorListModel().getFridayDrList() != null && doctorListResponseModel.getDoctorListModel().getFridayDrList().size() > 0)
                                fragmentFriday.updateDrList(doctorListResponseModel.getDoctorListModel().getFridayDrList());
                            if (doctorListResponseModel.getDoctorListModel().getSaturdayDrList() != null && doctorListResponseModel.getDoctorListModel().getSaturdayDrList().size() > 0)
                                fragmentSaturday.updateDrList(doctorListResponseModel.getDoctorListModel().getSaturdayDrList());

                            if (mDoctorListAdapter != null)
                                mDoctorListAdapter = fragmentMonday.getAdapterCurrentInstance();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DoctorListResponseModel>call, Throwable t) {
               /* Utils.showCustomAlertDialog(activity, true, getResources().getString(R.string.server_error), true,
                        getResources().getString(R.string.server_error_description), true, getResources().getString(R.string.ok),
                        false, "", true, null);*/
            }
        });

    }


}
