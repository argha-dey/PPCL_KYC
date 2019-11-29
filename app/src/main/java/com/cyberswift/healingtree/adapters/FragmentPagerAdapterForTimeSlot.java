package com.cyberswift.healingtree.adapters;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.cyberswift.healingtree.fragments.FragmentTimeSlot;

import java.util.ArrayList;
import java.util.List;

public class FragmentPagerAdapterForTimeSlot extends FragmentPagerAdapter {
    private final List<FragmentTimeSlot> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public FragmentPagerAdapterForTimeSlot(FragmentManager manager) {
        super(manager);
    }

    @Override
    public FragmentTimeSlot getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(FragmentTimeSlot fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}