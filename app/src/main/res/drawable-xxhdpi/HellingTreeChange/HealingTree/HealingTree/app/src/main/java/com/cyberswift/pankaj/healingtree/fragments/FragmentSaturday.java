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


public class FragmentSaturday extends Fragment {

    private RecyclerView saturdayRecyclerView;
    private ArrayList<BookingAppointmentModel> bookingAppointmentList = new ArrayList<>();
    private BookingAppointmentAdapter mBookingAppointmentAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_saturday, container, false);

        saturdayRecyclerView = (RecyclerView)rootView.findViewById(R.id.saturdayRecyclerView);
        saturdayRecyclerView.setHasFixedSize(false);
        saturdayRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        saturdayRecyclerView.setItemAnimator(new DefaultItemAnimator());

        prepareData();
        setAdapter();

        return rootView;
    }


    private void prepareData() {
        bookingAppointmentList.clear();
        BookingAppointmentModel bookingAppointmentModel2 = new BookingAppointmentModel("Dr Alpa Leuva", "(MD)", "Medicine",
                "18 Yrs", "Thyroid Disorder", "", "25/07/19", "");
        bookingAppointmentList.add(bookingAppointmentModel2);
        BookingAppointmentModel bookingAppointmentModel3 = new BookingAppointmentModel("Dr Himanshu Pandya", "(MD)", "Medicine",
                "32 Yrs", "Dean Professor", "", "01/08/19", "");
        bookingAppointmentList.add(bookingAppointmentModel3);
    }


    private void setAdapter() {
        mBookingAppointmentAdapter = new BookingAppointmentAdapter(getActivity(), bookingAppointmentList);
        saturdayRecyclerView.setAdapter(mBookingAppointmentAdapter);
    }


}