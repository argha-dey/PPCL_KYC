package com.cyberswift.healingtree.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.cyberswift.healingtree.R;
import com.cyberswift.healingtree.model.TaskDateListAttendance;
import com.cyberswift.healingtree.model.TaskStartEndResponseModel;
import com.cyberswift.healingtree.retrofit.ApiClient;
import com.cyberswift.healingtree.retrofit.ApiInterface;
import com.cyberswift.healingtree.utils.Constants;
import com.cyberswift.healingtree.utils.LocalModel;
import com.cyberswift.healingtree.utils.Prefs;
import com.cyberswift.healingtree.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TaskAttendanceDetailsAdapter extends RecyclerView.Adapter<TaskAttendanceDetailsAdapter.ViewHolder> {
    private Context context;
    private ArrayList<TaskDateListAttendance> taskDateListAttendance;
    private Prefs mPrefs;

    public TaskAttendanceDetailsAdapter(Context context, ArrayList<TaskDateListAttendance> _taskDateListAttendance) {
        this.taskDateListAttendance = _taskDateListAttendance;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.attendance_time_layout_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if (taskDateListAttendance != null && taskDateListAttendance.size() > 0) {
            viewHolder.setDataToViews(taskDateListAttendance, position);
        }

    }

    @Override
    public int getItemCount() {
        if (taskDateListAttendance != null && !taskDateListAttendance.isEmpty()) {
            return taskDateListAttendance.size();
        } else {
            return 0;

        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mDate;
        private TextView mDay;
        private Button check_out;
        private Button check_in;
        private RelativeLayout rl_task_single_row_layout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initViews();

        }

        private void initViews() {
            mPrefs = new Prefs(context);
            mDate =  itemView.findViewById(R.id.tv_task_date);
            mDay  =  itemView.findViewById(R.id.tv_number_of_day);
            check_out =itemView.findViewById(R.id.btn_check_out);
            check_in =  itemView.findViewById(R.id.btn_check_in);
            rl_task_single_row_layout = itemView.findViewById(R.id.rl_task_single_row_layout);

        }

        public void setDataToViews(final ArrayList<TaskDateListAttendance> taskDateListAttendances,final int position) {
            String date = Utils.changeDateNTimeFormat(taskDateListAttendances.get(position).getSRD_SERVICE_DATE(), Constants.DATE_TIME_FORMAT_1, Constants.DATE_TIME_FORMAT_2);
            mDate.setText(date);
            int pos = position+1;
            mDay.setText("Day "+pos);
            String currentDate = Utils.currentDateAsYYYYMMDD();

            if(currentDate.equals(taskDateListAttendances.get(position).getSRD_SERVICE_DATE())){
                rl_task_single_row_layout.setBackgroundColor(Color.WHITE);
                rl_task_single_row_layout.setEnabled(true);
                check_in.setEnabled(true);
                check_out.setEnabled(true);
                check_in.setBackgroundColor(Color.parseColor("#58aa47"));
                check_out.setBackgroundColor(Color.parseColor("#F60E0E"));
            }
            else {
                rl_task_single_row_layout.setBackgroundColor(Color.parseColor("#CCF4F4F4"));
                rl_task_single_row_layout.setEnabled(false);
                check_in.setEnabled(false);
                check_out.setEnabled(false);
                check_in.setBackgroundColor(Color.parseColor("#FFAFE4A6"));
                check_out.setBackgroundColor(Color.parseColor("#FFDC7575"));
            }



// Check In
            check_in.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LocalModel.getInstance().showProgressDialog(context, context.getResources().getString(R.string.please_wait_msg), false);
                    Map<String, String> requestBody = new HashMap<>();
                    requestBody.put("user_id", new Prefs(context).getUserID());
                    requestBody.put("task_id",taskDateListAttendances.get(position).getSRD_RSV_ID());
                    requestBody.put("date_wise_id",taskDateListAttendances.get(position).getSRD_ID());
                    requestBody.put("task_date", taskDateListAttendances.get(position).getSRD_SERVICE_DATE());
                    requestBody.put("start_time",Utils.currentTime());
                    ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
                    Call<TaskStartEndResponseModel> call = apiService.getStartTask(requestBody);
                    call.enqueue(new Callback<TaskStartEndResponseModel>() {
                        @Override
                        public void onResponse(Call<TaskStartEndResponseModel> call, Response<TaskStartEndResponseModel> response) {
                            if (response.isSuccessful()) {
                                if (response.body() != null) {
                                    TaskStartEndResponseModel taskListResponse = response.body();
                                    if (taskListResponse.isStatus()) {
                                        LocalModel.getInstance().cancelProgressDialog();
                                        check_in.setEnabled(false);
                                        check_in.setBackgroundColor(Color.parseColor("#FFAFE4A6"));
                                        Utils.showAlertDialogWithOkButton(context,"Attendance Service",taskListResponse.getMessage());
                                    }
                                    else{
                                        LocalModel.getInstance().cancelProgressDialog();
                                    }
                                } else {
                                    LocalModel.getInstance().cancelProgressDialog();
                                }

                            } else {
                                LocalModel.getInstance().cancelProgressDialog();
                            }
                        }


                        @Override
                        public void onFailure(Call<TaskStartEndResponseModel> call, Throwable t) {
                            LocalModel.getInstance().cancelProgressDialog();
                        }
                    });

                }
            });

// Check Out
            check_out.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    LocalModel.getInstance().showProgressDialog(context, context.getResources().getString(R.string.please_wait_msg), false);

                    // check for finish task
                    String taskFinish = "";
                    if(position == taskDateListAttendances.size()-1){
                        taskFinish = "Y";
                    }

                    Map<String, String> requestBody = new HashMap<>();
                    requestBody.put("user_id", new Prefs(context).getUserID());
                    requestBody.put("task_id",taskDateListAttendances.get(position).getSRD_RSV_ID());
                    requestBody.put("date_wise_id",taskDateListAttendances.get(position).getSRD_ID());
                    requestBody.put("task_date", taskDateListAttendances.get(position).getSRD_SERVICE_DATE());
                    requestBody.put("end_time",Utils.currentTime());
                    requestBody.put("is_task_end",taskFinish);
                    ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
                    Call<TaskStartEndResponseModel> call = apiService.getEndTask(requestBody);
                    call.enqueue(new Callback<TaskStartEndResponseModel>() {
                        @Override
                        public void onResponse(Call<TaskStartEndResponseModel> call, Response<TaskStartEndResponseModel> response) {
                            if (response.isSuccessful()) {
                                if (response.body() != null) {
                                    TaskStartEndResponseModel taskListResponse = response.body();
                                    if (taskListResponse.isStatus()) {
                                        LocalModel.getInstance().cancelProgressDialog();
                                        check_out.setEnabled(false);
                                        check_out.setBackgroundColor(Color.parseColor("#FFDC7575"));
                                        Utils.showAlertDialogWithOkButton(context,"Attendance Service",taskListResponse.getMessage());
                                    }
                                    else{
                                        LocalModel.getInstance().cancelProgressDialog();
                                    }
                                } else {
                                    LocalModel.getInstance().cancelProgressDialog();
                                }

                            } else {
                                LocalModel.getInstance().cancelProgressDialog();
                            }
                        }


                        @Override
                        public void onFailure(Call<TaskStartEndResponseModel> call, Throwable t) {
                            LocalModel.getInstance().cancelProgressDialog();
                        }
                    });

                }

            });

        }
    }
}
