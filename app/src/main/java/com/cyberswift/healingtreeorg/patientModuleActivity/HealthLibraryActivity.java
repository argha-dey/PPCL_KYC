package com.cyberswift.healingtreeorg.patientModuleActivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.cyberswift.healingtreeorg.R;
import com.cyberswift.healingtreeorg.adapters.HealthLibraryAdapter;
import com.cyberswift.healingtreeorg.model.AwarnessData;
import com.cyberswift.healingtreeorg.model.HealthLibraryResponseDo;
import com.cyberswift.healingtreeorg.retrofit.ApiClient;
import com.cyberswift.healingtreeorg.retrofit.ApiInterface;
import com.cyberswift.healingtreeorg.utils.LocalModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class HealthLibraryActivity extends BaseActivity {
    private RecyclerView mHealthLibraryRecyclerView;
    private HealthLibraryAdapter healthLibraryAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_health_library);
        initViews();
        sendRequestToGetLibraryList();
    }

    private void sendRequestToGetLibraryList() {
        LocalModel.getInstance().showProgressDialog(this, getResources().getString(R.string.please_wait_msg), false);
        ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<HealthLibraryResponseDo> call = apiService.getHealthLibraryResponse();
        call.enqueue(new Callback<HealthLibraryResponseDo>() {
            @Override
            public void onResponse(Call<HealthLibraryResponseDo> call, Response<HealthLibraryResponseDo> response) {

                if (response.body().isStatus()) {
                    HealthLibraryResponseDo model = response.body();
                    ArrayList<AwarnessData> awarnessDataArrayList = model.getAwarnessdata();
                    if (awarnessDataArrayList != null) {
                        setAdapterToRecyclerView(awarnessDataArrayList);
                        LocalModel.getInstance().cancelProgressDialog();
                    }
                } else {
                    LocalModel.getInstance().cancelProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<HealthLibraryResponseDo> call, Throwable t) {
                LocalModel.getInstance().cancelProgressDialog();
            }
        });
    }

    private void setAdapterToRecyclerView(ArrayList<AwarnessData> awarnessDataArrayList) {
        healthLibraryAdapter = new HealthLibraryAdapter(this, awarnessDataArrayList);
        layoutManager = new LinearLayoutManager(this);
        mHealthLibraryRecyclerView.setLayoutManager(layoutManager);
        mHealthLibraryRecyclerView.setAdapter(healthLibraryAdapter);
    }

    private void initViews() {
        mHealthLibraryRecyclerView = (RecyclerView) findViewById(R.id.health_library_recyclerview);
    }
}
