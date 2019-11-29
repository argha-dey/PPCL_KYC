package com.cyberswift.healingtree.homeCareAttendanceModuleActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cyberswift.healingtree.R;
import com.cyberswift.healingtree.patientModuleActivity.UserProfileActivity;
import com.cyberswift.healingtree.adapters.TaskListViewAdapter;
import com.cyberswift.healingtree.adapters.TaskMedicineOrderDeliveryListAdapter;
import com.cyberswift.healingtree.interfaces.AlertDialogWithCancelAndRetryListener;
import com.cyberswift.healingtree.model.MedicineDeliveryTaskList;
import com.cyberswift.healingtree.model.MedicineDeliveryTaskListResponseModel;
import com.cyberswift.healingtree.model.TaskList;
import com.cyberswift.healingtree.model.TaskListResponseModel;
import com.cyberswift.healingtree.retrofit.ApiClient;
import com.cyberswift.healingtree.retrofit.ApiInterface;
import com.cyberswift.healingtree.utils.Constants;
import com.cyberswift.healingtree.utils.LocalModel;
import com.cyberswift.healingtree.utils.Prefs;
import com.cyberswift.healingtree.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignedTaskActivity extends  BaseAttendanceActivity{
    private Activity activity;
    private RecyclerView rv_assign_list;
    private RecyclerView.LayoutManager layoutManager;
    private TaskListViewAdapter taskListViewAdapter;
    private Prefs mPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.today_task_list_layout);
        initialViews();
        getSupportActionBar().setTitle("");
        checkedMenuItemForAttandance = navView.getMenu().findItem(Constants.ASSIGNED_TASK_LIST_ID);
        checkedMenuItemForAttandance.setChecked(true);


        if(Utils.isOnline(activity)) {
            if(mPrefs.getUserRoleName().equals("Medicine Delivery Person")) {getMedicineOderList();}
            else {sendRequestForTaskListApi();}
        }else {
            Utils.showAlertDialogWithCancelAndRetry(activity, "Internet connection not available! ", new AlertDialogWithCancelAndRetryListener() {
                @Override
                public void onCancelClick() {

                }

                @Override
                public void onRetryClick() {
                    if(mPrefs.getUserRoleName().equals("Medicine Delivery Person")) {getMedicineOderList();}
                    else {sendRequestForTaskListApi();}
                }
            });
        }
    }

    private void getMedicineOderList() {
        LocalModel.getInstance().showProgressDialog(this, this.getResources().getString(R.string.please_wait_msg), false);
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("user_id", mPrefs.getUserID());
        ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<MedicineDeliveryTaskListResponseModel> call = apiService.getMedicineDeliveryTaskList(requestBody);
        call.enqueue(new Callback<MedicineDeliveryTaskListResponseModel>() {
            @Override
            public void onResponse(Call<MedicineDeliveryTaskListResponseModel> call, Response<MedicineDeliveryTaskListResponseModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        MedicineDeliveryTaskListResponseModel taskListResponse = response.body();
                        if (taskListResponse.isStatus()) {
                            if (taskListResponse.getTaskList() != null) {
                                LocalModel.getInstance().cancelProgressDialog();
                                ArrayList<MedicineDeliveryTaskList> OrderList = taskListResponse.getTaskList();
                                setAdapterMedicineOrderList(OrderList);
                                LocalModel.getInstance().cancelProgressDialog();
                            }
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
            public void onFailure(Call<MedicineDeliveryTaskListResponseModel> call, Throwable t) {
                LocalModel.getInstance().cancelProgressDialog();
            }
        });
    }

    private void initialViews() {
        activity = AssignedTaskActivity.this;
        mPrefs = new Prefs(activity);
        rv_assign_list = findViewById(R.id.rv_assign_list);

    }

    private void sendRequestForTaskListApi() {
        LocalModel.getInstance().showProgressDialog(this, this.getResources().getString(R.string.please_wait_msg), false);
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("user_id", mPrefs.getUserID());
        ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);

        Call<TaskListResponseModel> call = apiService.getTaskList(requestBody);
        call.enqueue(new Callback<TaskListResponseModel>() {
            @Override
            public void onResponse(Call<TaskListResponseModel> call, Response<TaskListResponseModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        TaskListResponseModel taskListResponse = response.body();
                        if (taskListResponse.isStatus()) {
                            if (taskListResponse.getTaskList() != null) {
                                LocalModel.getInstance().cancelProgressDialog();
                                ArrayList<TaskList> taskList = taskListResponse.getTaskList();
                                setAdapterTaskList(taskList);
                                LocalModel.getInstance().cancelProgressDialog();
                            }
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
            public void onFailure(Call<TaskListResponseModel> call, Throwable t) {
                LocalModel.getInstance().cancelProgressDialog();
            }
        });
    }

    private void setAdapterTaskList(ArrayList<TaskList> taskList) {
        taskListViewAdapter = new TaskListViewAdapter(this, taskList);
        layoutManager = new LinearLayoutManager(this);
        rv_assign_list.setLayoutManager(layoutManager);
        rv_assign_list.setAdapter(taskListViewAdapter);
    }
    private void setAdapterMedicineOrderList(ArrayList<MedicineDeliveryTaskList> taskList) {
        TaskMedicineOrderDeliveryListAdapter taskListViewAdapter = new TaskMedicineOrderDeliveryListAdapter(this, taskList);
        layoutManager = new LinearLayoutManager(this);
        rv_assign_list.setLayoutManager(layoutManager);
        rv_assign_list.setAdapter(taskListViewAdapter);
    }
/*    public  void bookDoctorAppointment(View view){
        Intent intentHome = new Intent(activity, SpecialitiesActivity.class);
        startActivity(intentHome);
    }*/

    public void goProfilePage(View view){
        Intent intent = new Intent(activity,UserProfileActivity.class);
        startActivity(intent);

    }

}


