package com.cyberswift.healingtreeorg.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.cyberswift.healingtreeorg.R;
import com.cyberswift.healingtreeorg.utils.Prefs;

import java.util.ArrayList;

public class NotificationRecordViewAdapter extends RecyclerView.Adapter<NotificationRecordViewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> notificationRecord;
    private Prefs mPrefs;

    public NotificationRecordViewAdapter(Context context, ArrayList<String> _notificationRecord) {
        this.notificationRecord = _notificationRecord;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_list_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if (notificationRecord != null && notificationRecord.size() > 0) {
            viewHolder.setDataToViews(notificationRecord, position);
        }

    }

    @Override
    public int getItemCount() {
        if (notificationRecord != null && !notificationRecord.isEmpty()) {
            return notificationRecord.size();
        } else {
            return 0;

        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mDateTextView;
        private TextView mPatientName;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initViews();

        }

        private void initViews() {
            mPrefs = new Prefs(context);
            mPatientName = (TextView) itemView.findViewById(R.id.patient_name);


        }

        public void setDataToViews(final ArrayList<String> notificationRecords, int position) {

            mPatientName.setText(""+notificationRecords.get(position));

        }
    }
}
