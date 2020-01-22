package com.cyberswift.healingtreeorg.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.cyberswift.healingtreeorg.R;
import com.cyberswift.healingtreeorg.model.HelloHealthPackageRecord;
import com.cyberswift.healingtreeorg.utils.Constants;
import com.cyberswift.healingtreeorg.utils.Prefs;
import com.cyberswift.healingtreeorg.utils.Utils;

import java.util.ArrayList;

public class HelloHealthRecordViewAdapter extends RecyclerView.Adapter<HelloHealthRecordViewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<HelloHealthPackageRecord> helloHealthPackageRecord;
    private Prefs mPrefs;

    public HelloHealthRecordViewAdapter(Context context, ArrayList<HelloHealthPackageRecord> _helloHealthPackageRecord) {
        this.helloHealthPackageRecord = _helloHealthPackageRecord;
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
        if (helloHealthPackageRecord != null && helloHealthPackageRecord.size() > 0) {
            viewHolder.setDataToViews(helloHealthPackageRecord, position);
        }

    }

    @Override
    public int getItemCount() {
        if (helloHealthPackageRecord != null && !helloHealthPackageRecord.isEmpty()) {
            return helloHealthPackageRecord.size();
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

        public void setDataToViews(final ArrayList<HelloHealthPackageRecord> _helloHealthPackageRecord, int position) {
            String date = Utils.changeDateNTimeFormat(_helloHealthPackageRecord.get(position).getHHS_CRT_TS(), Constants.DATE_TIME_FORMAT_11,Constants.DATE_TIME_FORMAT_2);
            mDateTextView.setText(date);
            mPatientName.setText(mPrefs.getUserFirstname().toUpperCase());
            service_type.setText(_helloHealthPackageRecord.get(position).getHLO_PACKAGE_NAME());
            String time = Utils.changeDateNTimeFormat(_helloHealthPackageRecord.get(position).getHHS_CRT_TS(), Constants.DATE_TIME_FORMAT_11,Constants.DATE_TIME_FORMAT_12);
            mTime.setText(context.getResources().getString(R.string.time_label)+time);
            list_of_service.setText(_helloHealthPackageRecord.get(position).getHHS_PACKAGE_TYPE());
            service_cost.setText("Amount:  ₹"+_helloHealthPackageRecord.get(position).getHHS_COST());
            tv_status.setText(_helloHealthPackageRecord.get(position).getHHS_STATUS().toUpperCase());


        }
    }
}
