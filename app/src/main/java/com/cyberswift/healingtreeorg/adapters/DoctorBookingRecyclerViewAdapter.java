package com.cyberswift.healingtreeorg.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.cyberswift.healingtreeorg.R;
import com.cyberswift.healingtreeorg.model.AppointmentDataDo;
import com.cyberswift.healingtreeorg.utils.Constants;
import com.cyberswift.healingtreeorg.utils.Prefs;
import com.cyberswift.healingtreeorg.utils.Utils;

import java.util.ArrayList;

public class DoctorBookingRecyclerViewAdapter extends RecyclerView.Adapter<DoctorBookingRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<AppointmentDataDo> appointmentDataDos;

    public DoctorBookingRecyclerViewAdapter(Context context, ArrayList<AppointmentDataDo> appointmentDataDos) {
        this.appointmentDataDos = appointmentDataDos;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.doctors_booking_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if (appointmentDataDos != null && appointmentDataDos.size() > 0) {
            viewHolder.setDataToViews(appointmentDataDos, position);
        }

    }

    @Override
    public int getItemCount() {
        if (appointmentDataDos != null && !appointmentDataDos.isEmpty()) {
            return appointmentDataDos.size();
        } else {
            return 0;

        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mDateTextView;
        private TextView mPatientName;
        private TextView mDoctorsname;
        private TextView mTreatementType;
        private TextView mRequestType;
        private TextView mTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initViews();
        }

        private void initViews() {
            mDateTextView = (TextView) itemView.findViewById(R.id.date_textview);
            mPatientName = (TextView) itemView.findViewById(R.id.patient_name);
            mDoctorsname = (TextView) itemView.findViewById(R.id.doctor_name);
            mTreatementType = (TextView) itemView.findViewById(R.id.type_value);
            mRequestType = (TextView) itemView.findViewById(R.id.request_type_value);
            mTime = (TextView) itemView.findViewById(R.id.time_value);
        }

        public void setDataToViews(ArrayList<AppointmentDataDo> appointmentDataDos, int position) {


              String date = Utils.changeDateNTimeFormat(appointmentDataDos.get(position).getAPLC_SCHEDULE_DATE(), Constants.DATE_TIME_FORMAT_1,Constants.DATE_TIME_FORMAT_2);
              mDateTextView.setText(date);
        //    mPatientName.setText(new Prefs(context).getUserFirstname() + " " + new Prefs(context).getUserLastname());
             mPatientName.setText(appointmentDataDos.get(position).getDOC_NAME());
             mDoctorsname.setText(appointmentDataDos.get(position).getAPLC_STATUS().toUpperCase());
             mTreatementType.setText(appointmentDataDos.get(position).getDOC_SPECIALIZATION());
             mRequestType.setText(new Prefs(context).getUserFirstname() + " " + new Prefs(context).getUserLastname().toUpperCase());


        }
    }
}
