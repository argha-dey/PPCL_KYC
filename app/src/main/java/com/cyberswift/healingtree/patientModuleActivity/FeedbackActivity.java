package com.cyberswift.healingtree.patientModuleActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cyberswift.healingtree.R;
import com.cyberswift.healingtree.model.FeedBackResponseModel;
import com.cyberswift.healingtree.retrofit.ApiClient;
import com.cyberswift.healingtree.retrofit.ApiInterface;
import com.cyberswift.healingtree.utils.Constants;
import com.cyberswift.healingtree.utils.LocalModel;
import com.cyberswift.healingtree.utils.Prefs;
import com.cyberswift.healingtree.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackActivity extends BaseActivity {
    private Activity activity;
    private Context mContext;
    private EditText details_feedback;
    private  EditText subject_feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.feed_back_activity);
        initViewsFeedBack();
        BaseActivity.checkedMenuItem = BaseActivity.navView.getMenu().findItem(Constants.FEEDBACK_NAV_ID);
        BaseActivity.checkedMenuItem.setChecked(true);
    }

    private void initViewsFeedBack() {
        mContext = FeedbackActivity.this;
        details_feedback = findViewById(R.id.details_feedback);
        subject_feedback = findViewById(R.id.subject_feedback);
    }



/*    public  void onBookingDone(View view){
        LocalModel.getInstance().showProgressDialog(activity, activity.getResources().getString(R.string.please_wait_msg), false);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                LocalModel.getInstance().cancelProgressDialog();
                Intent intentHome = new Intent(activity, HomeActivity.class);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentHome);
            }
        }, 500);

    }*/


    public void onFeedbackSubmit(View view){
        if(subject_feedback.getText().toString().isEmpty()||subject_feedback.getText().toString().equals("")){
            Toast.makeText(mContext,"Subject field is blank!",Toast.LENGTH_SHORT).show();
        }
        else if(details_feedback.getText().toString().isEmpty()||details_feedback.getText().toString().equals("")){
            Toast.makeText(mContext,"Feedback Details field is blank!",Toast.LENGTH_SHORT).show();
        }
        else {
            if(Utils.isOnline(mContext)) {
                callFeedBackService();
            }
            else {
                Toast.makeText(mContext,"Internet Connection not available!",Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void callFeedBackService() {
        LocalModel.getInstance().showProgressDialog(this, this.getResources().getString(R.string.please_wait_msg), false);
        Map<String, String> requestBody = new HashMap<>();

        requestBody.put("user_id", new Prefs(this).getUserID());
        requestBody.put("subject",subject_feedback.getText().toString());
        requestBody.put("message", details_feedback.getText().toString());
        ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<FeedBackResponseModel> call = apiService.postUserFeedBack(requestBody);
        call.enqueue(new Callback<FeedBackResponseModel>() {
            @Override
            public void onResponse(Call<FeedBackResponseModel> call, Response<FeedBackResponseModel> response) {

                    if (response.body() != null) {
                        FeedBackResponseModel feedBackRespond = response.body();
                        if (feedBackRespond.isStatus()) {
                            Toast.makeText(mContext,"Feedback data submitted successfully",Toast.LENGTH_LONG).show();
                            LocalModel.getInstance().cancelProgressDialog();
                            finish();
                        }
                        else {
                            LocalModel.getInstance().cancelProgressDialog();
                            Toast.makeText(mContext,"Feedback data not submitted",Toast.LENGTH_LONG).show();
                        }
                    } else {
                        LocalModel.getInstance().cancelProgressDialog();
                        Toast.makeText(mContext,"Some error occur in feedback data submission time!",Toast.LENGTH_LONG).show();
                    }

                }



            @Override
            public void onFailure(Call<FeedBackResponseModel> call, Throwable t) {
                LocalModel.getInstance().cancelProgressDialog();
                Toast.makeText(mContext,"Some error occur in feedback data submission time!",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onFeedbackBackButtonClick(View view){
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
