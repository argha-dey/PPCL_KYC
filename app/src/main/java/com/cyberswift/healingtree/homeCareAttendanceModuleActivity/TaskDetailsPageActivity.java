package com.cyberswift.healingtree.homeCareAttendanceModuleActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cyberswift.healingtree.R;
import com.cyberswift.healingtree.adapters.TaskAttendanceDetailsAdapter;
import com.cyberswift.healingtree.interfaces.AlertDialogWithCancelAndRetryListener;
import com.cyberswift.healingtree.model.TaskDateListAttendance;
import com.cyberswift.healingtree.model.TaskDetailsData;
import com.cyberswift.healingtree.model.TaskDetailsResponseModel;
import com.cyberswift.healingtree.retrofit.ApiClient;
import com.cyberswift.healingtree.retrofit.ApiInterface;
import com.cyberswift.healingtree.utils.LocalModel;
import com.cyberswift.healingtree.utils.Prefs;
import com.cyberswift.healingtree.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskDetailsPageActivity extends BaseAttendanceActivity {
    private Context mContext;
    private String taskId = "";
    private TextView tv_holder_name,tv_service_type,tv_service_details,tv_user_address;
    private RecyclerView rcv_attendant_day_list;
    private ArrayList<TaskDateListAttendance> taskDateListAttendances;
    private TaskAttendanceDetailsAdapter taskAttendanceDetailsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_details_activity);
        mContext = TaskDetailsPageActivity.this;
        getDataFromBundle();
        initViews();

        if(Utils.isOnline(mContext)) {
            callTaskDetailsServiceCall();
        }else {
            Utils.showAlertDialogWithCancelAndRetry(mContext, "Internet connection not available! ", new AlertDialogWithCancelAndRetryListener() {
                @Override
                public void onCancelClick() { }
                @Override
                public void onRetryClick() { callTaskDetailsServiceCall();
                }
            });
        }
    }

    private void initViews() {
        tv_holder_name = findViewById(R.id.tv_holder_name);
        tv_service_type = findViewById(R.id.tv_service_type);
        tv_service_details = findViewById(R.id.tv_service_details);
        tv_user_address  = findViewById(R.id.tv_user_address);
        rcv_attendant_day_list = findViewById(R.id.rcv_attendant_day_list);
    }

    private void getDataFromBundle() {
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            taskId = extras.getString("SERVICE_ID");
        }
    }

    private void callTaskDetailsServiceCall() {
        LocalModel.getInstance().showProgressDialog(this, this.getResources().getString(R.string.please_wait_msg), false);
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("user_id", new Prefs(this).getUserID());
        requestBody.put("task_id", taskId);
        ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<TaskDetailsResponseModel> call = apiService.getTaskDetails(requestBody);
        call.enqueue(new Callback<TaskDetailsResponseModel>() {
            @Override
            public void onResponse(Call<TaskDetailsResponseModel> call, Response<TaskDetailsResponseModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        TaskDetailsResponseModel taskDetailsResponse = response.body();
                        if (taskDetailsResponse.isStatus()) {
                            if (taskDetailsResponse.getTaskDetailsData() != null) {
                                ArrayList<TaskDetailsData> taskDetails = taskDetailsResponse.getTaskDetailsData();
                                taskDetailsDataSet(taskDetails);
                                LocalModel.getInstance().cancelProgressDialog();
                            }
                        }
                        else {
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
            public void onFailure(Call<TaskDetailsResponseModel> call, Throwable t) {
                LocalModel.getInstance().cancelProgressDialog();
            }
        });
    }

    private void taskDetailsDataSet(ArrayList<TaskDetailsData> taskDetails) {
        tv_holder_name.setText(taskDetails.get(0).getBooked_user_name());
        tv_service_type.setText(taskDetails.get(0).getHHC_NAME());
        tv_service_details.setText(taskDetails.get(0).getServices());
        tv_user_address.setText(taskDetails.get(0).getBooked_user_address());
        setTaskAttendanceDetails(taskDetails.get(0).getTaskDateListForAttendance());
    }

        private void setTaskAttendanceDetails(ArrayList<TaskDateListAttendance> _taskDateListAttendance) {
            taskAttendanceDetailsAdapter = new TaskAttendanceDetailsAdapter(this, _taskDateListAttendance);
            layoutManager = new LinearLayoutManager(this);
            rcv_attendant_day_list.setLayoutManager(layoutManager);
            rcv_attendant_day_list.setAdapter(taskAttendanceDetailsAdapter);
        }



    public void onTaskDetailsBackButtonClick(View view){
        Intent intentTaskList = new Intent(mContext,AssignedTaskActivity.class);
        intentTaskList.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intentTaskList.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentTaskList);
    }
}
