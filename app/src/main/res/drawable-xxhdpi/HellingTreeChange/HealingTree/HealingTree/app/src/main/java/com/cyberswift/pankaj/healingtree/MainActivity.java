package com.cyberswift.pankaj.healingtree;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cyberswift.pankaj.healingtree.fragments.FragmentFriday;
import com.cyberswift.pankaj.healingtree.fragments.FragmentMonday;
import com.cyberswift.pankaj.healingtree.fragments.FragmentSaturday;
import com.cyberswift.pankaj.healingtree.fragments.FragmentThursday;
import com.cyberswift.pankaj.healingtree.fragments.FragmentTuesday;
import com.cyberswift.pankaj.healingtree.fragments.FragmentWednesday;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Activity activity;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupViewPager(viewPager);

    }


    // initializeViews
    private void initializeViews() {
        activity = MainActivity.this;
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabLayout);
    }


    public void onSearchClick(View view) {
        Toast.makeText(activity, "Working...", Toast.LENGTH_SHORT).show();
    }


    public void onCalendarClick(View view) {
        Toast.makeText(activity, "Working...", Toast.LENGTH_SHORT).show();
    }


    public void onFilterClick(View view) {
        Toast.makeText(activity, "Working...", Toast.LENGTH_SHORT).show();
    }


    // setup view pager
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());  // /*getFragmentManager*/getChildFragmentManager()
        adapter.addFrag(new FragmentMonday(), "MON");
        adapter.addFrag(new FragmentTuesday(), "TUE");
        adapter.addFrag(new FragmentWednesday(), "WED");
        adapter.addFrag(new FragmentThursday(), "THU");
        adapter.addFrag(new FragmentFriday(), "FRI");
        adapter.addFrag(new FragmentSaturday(), "SAT");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
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

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
