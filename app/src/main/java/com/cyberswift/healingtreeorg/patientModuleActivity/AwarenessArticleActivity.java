package com.cyberswift.healingtreeorg.patientModuleActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.cyberswift.healingtreeorg.R;
import com.cyberswift.healingtreeorg.model.AwarnessArticleResponseDo;
import com.cyberswift.healingtreeorg.model.AwarnessLinkDo;
import com.cyberswift.healingtreeorg.retrofit.ApiClient;
import com.cyberswift.healingtreeorg.retrofit.ApiInterface;
import com.cyberswift.healingtreeorg.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AwarenessArticleActivity extends BaseActivity {

    private Intent intent;
    private WebView webView;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.awarness_article);
        intent =getIntent();
        id = intent.getStringExtra(Constants.ARTICLE_ID_CONSTANTS);
        initViews();
        sendRequestToGetArticle();

    }

    private void initViews() {
        webView = (WebView) findViewById(R.id.webview);
    }

    private void sendRequestToGetArticle() {
     //   LocalModel.getInstance().showProgressDialog(this, getResources().getString(R.string.please_wait_msg), false);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put(Constants.ARTICLE_ID_CONSTANTS, id);
        ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<AwarnessArticleResponseDo> call = apiService.getAwarnessArticle(requestBody);
        call.enqueue(new Callback<AwarnessArticleResponseDo>() {
            @Override
            public void onResponse(Call<AwarnessArticleResponseDo> call, Response<AwarnessArticleResponseDo> response) {
                if (response.body().isStatus()) {
                    if (response.body().getAwarness_link() != null) {
                        ArrayList<AwarnessLinkDo> awarnessLinkDos = response.body().getAwarness_link();
                        setLinkToWebView(awarnessLinkDos);
                    } else {
                   //     LocalModel.getInstance().cancelProgressDialog();
                    }
                } else {
                //    LocalModel.getInstance().cancelProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<AwarnessArticleResponseDo> call, Throwable t) {
              //  LocalModel.getInstance().cancelProgressDialog();
            }
        });
    }

    private void setLinkToWebView(ArrayList<AwarnessLinkDo> awarenessLinkDos) {
        webView.loadUrl(awarenessLinkDos.get(0).getLink());
    }
}
