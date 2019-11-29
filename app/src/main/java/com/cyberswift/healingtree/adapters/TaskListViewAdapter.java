package com.cyberswift.healingtree.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.cyberswift.healingtree.homeCareAttendanceModuleActivity.TaskDetailsPageActivity;
import com.cyberswift.healingtree.R;
import com.cyberswift.healingtree.model.TaskList;
import com.cyberswift.healingtree.utils.Constants;
import com.cyberswift.healingtree.utils.Prefs;
import com.cyberswift.healingtree.utils.Utils;

import java.util.ArrayList;

public class TaskListViewAdapter extends RecyclerView.Adapter<TaskListViewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<TaskList> taskLists;
    private Prefs mPrefs;

    public TaskListViewAdapter(Context context, ArrayList<TaskList> taskLists) {
        this.taskLists = taskLists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.assign_task_list_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if (taskLists != null && taskLists.size() > 0) {
            viewHolder.setDataToViews(taskLists, position);
        }

    }

    @Override
    public int getItemCount() {
        if (taskLists != null && !taskLists.isEmpty()) {
            return taskLists.size();
        } else {
            return 0;

        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mDateTextView;
        private TextView mPatientName;
        private TextView service_type,service_cost;
        private TextView tv_status;
        private TextView paicent_contact;
        private TextView mTime;
        private RelativeLayout rl_task_details;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initViews();

        }

        private void initViews() {
            mPrefs = new Prefs(context);
            mDateTextView = (TextView) itemView.findViewById(R.id.date_textview);
            mPatientName = (TextView) itemView.findViewById(R.id.patient_name);
            service_type = (TextView) itemView.findViewById(R.id.service_type);
            paicent_contact = (TextView) itemView.findViewById(R.id.list_of_service);
            service_cost = (TextView) itemView.findViewById(R.id.service_cost);
            mTime = (TextView) itemView.findViewById(R.id.time_value);
            tv_status = itemView.findViewById(R.id.tv_status);
            rl_task_details = itemView.findViewById(R.id.rl_task_details);

        }

        public void setDataToViews(final ArrayList<TaskList> taskList, final int position) {
            String date = Utils.changeDateNTimeFormat(taskList.get(position).getService_assigned_date(), Constants.DATE_TIME_FORMAT_11,Constants.DATE_TIME_FORMAT_2);
            mDateTextView.setText(date);
            mPatientName.setText(" "+taskList.get(position).getPatient_name().toUpperCase());
            service_type.setText(" "+taskList.get(position).getServices());
            paicent_contact.setText(" Contact : "+taskList.get(position).getPatient_mobile_number());

            rl_task_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                      Intent intentTask = new Intent(context, TaskDetailsPageActivity.class);
                      intentTask.putExtra("SERVICE_ID",taskList.get(position).getRSV_ID());
                      context.startActivity(intentTask);
                }
            });
        }
    }
}
