package com.cyberswift.healingtreeorg.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.cyberswift.healingtreeorg.R;
import com.cyberswift.healingtreeorg.model.HomeCareServiceRecordList;
import com.cyberswift.healingtreeorg.utils.Constants;
import com.cyberswift.healingtreeorg.utils.Prefs;
import com.cyberswift.healingtreeorg.utils.Utils;

import java.util.ArrayList;

public class HomeCareServiceRecordViewAdapter extends RecyclerView.Adapter<HomeCareServiceRecordViewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<HomeCareServiceRecordList> homeCareServiceRecordLists;
    private Prefs mPrefs;

    public HomeCareServiceRecordViewAdapter(Context context, ArrayList<HomeCareServiceRecordList> _homeCareServiceRecordLists) {
        this.homeCareServiceRecordLists = _homeCareServiceRecordLists;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_care_record_list, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if (homeCareServiceRecordLists != null && homeCareServiceRecordLists.size() > 0) {
            viewHolder.setDataToViews(homeCareServiceRecordLists, position);
        }

    }

    @Override
    public int getItemCount() {
        if (homeCareServiceRecordLists != null && !homeCareServiceRecordLists.isEmpty()) {
            return homeCareServiceRecordLists.size();
        } else {
            return 0;

        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mDateTextView;
        private TextView mPatientName;
        private TextView service_type,service_cost;
        private TextView tv_status;
        private TextView list_of_service;
        private TextView mTime;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initViews();

        }

        private void initViews() {
            mPrefs = new Prefs(context);
            mDateTextView = (TextView) itemView.findViewById(R.id.date_textview);
            mPatientName = (TextView) itemView.findViewById(R.id.patient_name);
            service_type = (TextView) itemView.findViewById(R.id.service_type);
            list_of_service = (TextView) itemView.findViewById(R.id.list_of_service);
            service_cost = (TextView) itemView.findViewById(R.id.service_cost);
            mTime = (TextView) itemView.findViewById(R.id.time_value);
            tv_status = itemView.findViewById(R.id.tv_status);



        }

        public void setDataToViews(final ArrayList<HomeCareServiceRecordList> _homeCareServiceRecordLists, int position) {
            String date = Utils.changeDateNTimeFormat(_homeCareServiceRecordLists.get(position).getRSV_CRT_TS(), Constants.DATE_TIME_FORMAT_11,Constants.DATE_TIME_FORMAT_2);
            mDateTextView.setText(date);
            mPatientName.setText(mPrefs.getUserFirstname().toUpperCase());
            service_type.setText(_homeCareServiceRecordLists.get(position).getHHC_NAME());
            String time = Utils.changeDateNTimeFormat(_homeCareServiceRecordLists.get(position).getRSV_CRT_TS(), Constants.DATE_TIME_FORMAT_11,Constants.DATE_TIME_FORMAT_12);
            mTime.setText(context.getResources().getString(R.string.time_label)+time);
            list_of_service.setText(_homeCareServiceRecordLists.get(position).getServices());
            service_cost.setText("Amount:  ₹"+_homeCareServiceRecordLists.get(position).getRSV_TOTAL_AMOUNT());
            tv_status.setText(_homeCareServiceRecordLists.get(position).getRSV_STATUS().toUpperCase());


        }
    }
}
