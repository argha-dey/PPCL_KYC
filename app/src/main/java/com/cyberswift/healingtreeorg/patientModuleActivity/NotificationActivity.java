package com.cyberswift.healingtreeorg.patientModuleActivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.cyberswift.healingtreeorg.R;
import com.cyberswift.healingtreeorg.adapters.NotificationRecordViewAdapter;
import com.cyberswift.healingtreeorg.model.NotificationRecordResponseModel;
import com.cyberswift.healingtreeorg.retrofit.ApiClient;
import com.cyberswift.healingtreeorg.retrofit.ApiInterface;
import com.cyberswift.healingtreeorg.utils.Constants;
import com.cyberswift.healingtreeorg.utils.LocalModel;
import com.cyberswift.healingtreeorg.utils.Prefs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends BaseActivity{

    private Prefs prefs;
    private Context mContext;
    private RecyclerView rcv_notification_list;
    private NotificationRecordViewAdapter notificationRecordViewAdapter;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.notification_list);
        initViews();
        sendRequestForNotificationListApi();


       // getSupportActionBar().setTitle(Constants.SURVEY_LIST_NAV_NAME);
        checkedMenuItem = navView.getMenu().findItem(Constants.NOTIFICATION_NAV_ID);
        checkedMenuItem.setChecked(true);

    }

    private void initViews() {
        mContext = NotificationActivity.this;
        prefs = new Prefs(mContext);
        rcv_notification_list = findViewById(R.id.rv_notification_list);
    }
    private void sendRequestForNotificationListApi() {
        LocalModel.getInstance().showProgressDialog(this, this.getResources().getString(R.string.please_wait_msg), false);
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("user_id", new Prefs(this).getUserID());
        ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<NotificationRecordResponseModel> call = apiService.getNotificationList(requestBody);
        call.enqueue(new Callback<NotificationRecordResponseModel>() {
            @Override
            public void onResponse(Call<NotificationRecordResponseModel> call, Response<NotificationRecordResponseModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        NotificationRecordResponseModel notificationListResponse = response.body();
                        if (notificationListResponse.isStatus()) {
                            if (notificationListResponse.getNotificationList() != null) {
                                LocalModel.getInstance().cancelProgressDialog();
                                ArrayList<String> notificationList = notificationListResponse.getNotificationList();
                                setAdapterNotification(notificationList);
                            }
                        }
                        else {
                            LocalModel.getInstance().cancelProgressDialog();
                            Toast.makeText(mContext,"Notification list not available",Toast.LENGTH_LONG).show();
                        }
                    } else {
                        LocalModel.getInstance().cancelProgressDialog();
                        Toast.makeText(mContext,"Notification list not available",Toast.LENGTH_LONG).show();
                    }

                } else {
                    LocalModel.getInstance().cancelProgressDialog();
                    Toast.makeText(mContext,"Notification list not available",Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onFailure(Call<NotificationRecordResponseModel> call, Throwable t) {
                LocalModel.getInstance().cancelProgressDialog();
                Toast.makeText(mContext,"Some error occur in notification list!",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setAdapterNotification(ArrayList<String> _notificationList) {
        notificationRecordViewAdapter = new NotificationRecordViewAdapter(this, _notificationList);
        layoutManager = new LinearLayoutManager(this);
        rcv_notification_list.setLayoutManager(layoutManager);
        rcv_notification_list.setAdapter(notificationRecordViewAdapter);
    }
}
