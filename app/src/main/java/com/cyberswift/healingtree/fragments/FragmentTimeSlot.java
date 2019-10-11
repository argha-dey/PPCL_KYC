package com.cyberswift.healingtree.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cyberswift.healingtree.R;
import com.cyberswift.healingtree.adapters.MorningTimeSlotAdapter;
import com.cyberswift.healingtree.model.DoctorListModel;
import com.cyberswift.healingtree.model.TimeSlotModel;
import com.cyberswift.healingtree.utils.Prefs;

import java.util.ArrayList;

public class FragmentTimeSlot extends Fragment {
    private Activity activity;
    private ArrayList<DoctorListModel> doctorListModels;
    private LayoutInflater layoutInflater;
    private LinearLayout ll_time_slot_container;
    private TextView tv_no_data_found;
    private Prefs prefs;
    private ArrayList<TimeSlotModel> timeSlotModelArrayList;
    private RecyclerView rcv_morningTimeSlot;



    public static FragmentTimeSlot newInstance(ArrayList<DoctorListModel> _doctorListModels) {
        FragmentTimeSlot f = new FragmentTimeSlot();
        Bundle b = new Bundle();
        b.putParcelableArrayList("GET_TIME_SLOT_DATA_LIST", _doctorListModels);
        f.setArguments(b);
        return f;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_time_slot, container, false);
        this.doctorListModels = getArguments().getParcelableArrayList("GET_TIME_SLOT_DATA_LIST");
        initComponent(myView);
        return myView;
    }

    private void initComponent(View v) {
        activity = getActivity();
        prefs = new Prefs(activity);
        rcv_morningTimeSlot =v.findViewById(R.id.rcv_morningTimeSlot);
        tv_no_data_found = v.findViewById(R.id.tv_no_data_found);
        //this.ll_time_slot_container = v.findViewById(R.id.ll_time_slot_container);


// Static Data Set End

        if (doctorListModels != null) {
            prefs.setDoctorBookSelectDate(doctorListModels.get(0).getDate());
            timeSlotModelArrayList = doctorListModels.get(0).getTime_slot();
            setTimeSlotModelArrayList(timeSlotModelArrayList);
        }
    }




    // Set Home Care Attendance Trained Help Adapter
    private void setTimeSlotModelArrayList(ArrayList<TimeSlotModel> _morningTimeSlot) {
        /*     for(int pos =0 ; pos<_morningTimeSlot.size();pos++) {
         *//*   layoutInflater = (LayoutInflater) activity.getSystemService(LAYOUT_INFLATER_SERVICE);
            View viewlayout = layoutInflater.inflate(R.layout.time_slot_row, null);
            TextView time_slot_button = viewlayout.findViewById(R.id.time_slot_button);
            time_slot_button.setText(""+_morningTimeSlot.get(pos).getTime());
            ll_time_slot_container.addView(viewlayout);*//*
        }*/
        if(_morningTimeSlot.size()>0) {
            tv_no_data_found.setVisibility(View.GONE);
            rcv_morningTimeSlot.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            rcv_morningTimeSlot.setItemAnimator(new DefaultItemAnimator());
            MorningTimeSlotAdapter morningTimeSlotAdapter = new MorningTimeSlotAdapter(getActivity(), _morningTimeSlot);
            rcv_morningTimeSlot.setAdapter(morningTimeSlotAdapter);
        }
        else {
            tv_no_data_found.setVisibility(View.VISIBLE);
        }

    }
}



/*

   if (doctorListModels != null) {
           for (int pos = 0; pos < doctorListModels.size(); pos++) {
        if (doctorListModels.get(pos).getTime_slot() != null) {
        layoutInflater = (LayoutInflater) activity.getSystemService(LAYOUT_INFLATER_SERVICE);
        View viewlayout = this.layoutInflater.inflate(R.layout.time_slot_row, null);
final TextView btn_time = (TextView) viewlayout.findViewById(R.id.time_slot_button);
        //   btn_time.setText(doctorListModels.get(pos).getTime());
        btn_time.setTag(pos);
        btn_time.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        Utils.showCustomAlertDialog(activity, true, "Appointment Booking Info!", true, "Are you sure to book appointment on this time slot?",
        true, "Yes", true, "No", true,
        new CustomAlertDialogListener() {
@Override
public void positiveButtonWork() {
        int pos =Integer.parseInt(btn_time.getTag().toString());
        prefs.setDoctorBookSelectDate(doctorListModels.get(pos).getDate());
        //   prefs.setDoctorBookSelectTime(doctorListModels.get(pos).getTime());
        }

@Override
public void negativeButtonWork() { }
        });

        }
        });
        ll_time_slot_list.addView(viewlayout);
        }
        }

        }*/
