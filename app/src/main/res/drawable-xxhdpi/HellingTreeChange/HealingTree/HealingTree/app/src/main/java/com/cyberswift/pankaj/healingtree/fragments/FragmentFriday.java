package com.cyberswift.pankaj.healingtree.fragments;

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

import com.cyberswift.pankaj.healingtree.R;
import com.cyberswift.pankaj.healingtree.adapters.BookingAppointmentAdapter;
import com.cyberswift.pankaj.healingtree.models.BookingAppointmentModel;

import java.util.ArrayList;


public class FragmentFriday extends Fragment {

    private RecyclerView fridayRecyclerView;
    private ArrayList<BookingAppointmentModel> bookingAppointmentList = new ArrayList<>();
    private BookingAppointmentAdapter mBookingAppointmentAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_friday, container, false);

        fridayRecyclerView = (RecyclerView)rootView.findViewById(R.id.fridayRecyclerView);
        fridayRecyclerView.setHasFixedSize(false);
        fridayRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        fridayRecyclerView.setItemAnimator(new DefaultItemAnimator());

        prepareData();
        setAdapter();

        return rootView;
    }

    private void prepareData() {
        bookingAppointmentList.clear();
        BookingAppointmentModel bookingAppointmentModel1 = new BookingAppointmentModel("Dr Bhalandu Vaisanav", "(MD)", "Medicine",
                "29 Yrs", "Internal Medicine", "", "25/07/19", "");
        bookingAppointmentList.add(bookingAppointmentModel1);
    }


    private void setAdapter() {
        mBookingAppointmentAdapter = new BookingAppointmentAdapter(getActivity(), bookingAppointmentList);
        fridayRecyclerView.setAdapter(mBookingAppointmentAdapter);
    }


}
