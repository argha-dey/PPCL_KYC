package com.cyberswift.healingtree.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.cyberswift.healingtree.R;
import com.cyberswift.healingtree.adapters.AllDoctorListAdapter;
import com.cyberswift.healingtree.model.DoctorListModel;

import java.util.ArrayList;

public class AllDoctorListFragment extends Fragment {

    private TextView tv_no_data_found;
    private RecyclerView allDoctorRecyclerView;
    private ArrayList<DoctorListModel> bookingAppointmentList = new ArrayList<>();
    public AllDoctorListAdapter mDoctorListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_all_doctor_list, container, false);

        tv_no_data_found = (TextView)rootView.findViewById(R.id.tv_no_data_found);
        allDoctorRecyclerView = (RecyclerView)rootView.findViewById(R.id.allDoctorRecyclerView);
        allDoctorRecyclerView.setHasFixedSize(false);
        allDoctorRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        allDoctorRecyclerView.setItemAnimator(new DefaultItemAnimator());

        setAdapter();

        return rootView;
    }


    private void setAdapter() {
        mDoctorListAdapter = new AllDoctorListAdapter(getActivity(), bookingAppointmentList);
        allDoctorRecyclerView.setAdapter(mDoctorListAdapter);
        if (bookingAppointmentList.size() > 0)
            tv_no_data_found.setVisibility(View.GONE);
        else
            tv_no_data_found.setVisibility(View.VISIBLE);
    }


    public void updateDrList(ArrayList<DoctorListModel> arrList) {
        bookingAppointmentList.clear();
        bookingAppointmentList.addAll(arrList);
        if (mDoctorListAdapter != null) {
            mDoctorListAdapter.notifyDataSetChanged();
            mDoctorListAdapter.updateArrListCopy(bookingAppointmentList);
        }
        if (tv_no_data_found != null) {
            if (bookingAppointmentList.size() > 0)
                tv_no_data_found.setVisibility(View.GONE);
            else
                tv_no_data_found.setVisibility(View.VISIBLE);
        }
    }


    public AllDoctorListAdapter getAdapterCurrentInstance() {
        return mDoctorListAdapter;
    }

}

