package com.cyberswift.healingtree.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cyberswift.healingtree.R;
import com.cyberswift.healingtree.interfaces.CustomAlertDialogListener;
import com.cyberswift.healingtree.model.DoctorListModel;
import com.cyberswift.healingtree.utils.Prefs;
import com.cyberswift.healingtree.utils.Utils;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class FragmentTimeSlot extends Fragment {
    private Activity activity;
    private ArrayList<DoctorListModel> doctorListModels;
    private LayoutInflater layoutInflater;
    private LinearLayout ll_time_slot_list;
    private Prefs prefs;

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
        this.ll_time_slot_list = (LinearLayout) v.findViewById(R.id.ll_time_slot_container);

        // Static Data Implement Here
        doctorListModels = new ArrayList<>();
        DoctorListModel doctorListModel1 = new DoctorListModel();
        doctorListModel1.setTime("10:30 AM");
        doctorListModel1.setDate("2019-08-09");
        DoctorListModel doctorListModel2 = new DoctorListModel();
        doctorListModel2.setTime("11:30 AM");
        doctorListModel2.setDate("2019-08-10");
        DoctorListModel doctorListModel3 = new DoctorListModel();
        doctorListModel3.setTime("01:30 PM");
        doctorListModel3.setDate("2019-08-11");

        doctorListModels.add(doctorListModel1);
        doctorListModels.add(doctorListModel2);
        doctorListModels.add(doctorListModel3);

// Static Data Set End

        if (doctorListModels != null) {
            for (int pos = 0; pos < doctorListModels.size(); pos++) {
                if (doctorListModels.get(pos).getTime() != null) {
                    layoutInflater = (LayoutInflater) activity.getSystemService(LAYOUT_INFLATER_SERVICE);
                    View viewlayout = this.layoutInflater.inflate(R.layout.time_slot_row, null);
                    final TextView btn_time = (TextView) viewlayout.findViewById(R.id.time_slot_button);
                    btn_time.setText(doctorListModels.get(pos).getTime());
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
                                            prefs.setDoctorBookSelectTime(doctorListModels.get(pos).getTime());
                                        }

                                        @Override
                                        public void negativeButtonWork() { }
                                    });

                        }
                    });
                    ll_time_slot_list.addView(viewlayout);
                }
            }

        }
    }
}
