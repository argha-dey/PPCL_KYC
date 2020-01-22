package com.cyberswift.healingtreeorg.volley;


import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cyberswift.healingtreeorg.AppController;
import com.cyberswift.healingtreeorg.R;
import com.cyberswift.healingtreeorg.interfaces.MultipartPostCallback;
import com.cyberswift.healingtreeorg.model.FileDetails;
import com.cyberswift.healingtreeorg.utils.MultipartPostRequest;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VolleyTaskManager {
    private Context mContext;
    private ProgressDialog mProgressDialog;
    private String TAG = "";
    private String tag_json_obj = "jobj_req";
    private boolean isToShowDialog = true, isToHideDialog = true;

    public VolleyTaskManager(Context context) {
        mContext = context;

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Loading...");

        TAG = mContext.getClass().getSimpleName();
        Log.d("tag", TAG);
    }

    public void showProgressDialog() {
        if (!mProgressDialog.isShowing())
            mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    /**
     * Making json object request
     */
    private void makeJsonObjReqWithCallBack(int method, String url, final Map<String, String> paramsMap,
                                            final ServerResponseCallback serverResponseCallback) {
        if (isToShowDialog) {
            showProgressDialog();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(method, url, new JSONObject(paramsMap),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "On Response");
                        // msgResponse.setText(response.toString());
                        if (isToHideDialog) {
                            hideProgressDialog();
                        }
                        if (serverResponseCallback != null)
                            serverResponseCallback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressDialog();
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(mContext, mContext.getString(R.string.response_timeout),
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(mContext, mContext.getString(R.string.auth_failure),
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(mContext, mContext.getString(R.string.server_error),
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(mContext, mContext.getString(R.string.network_error),
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(mContext, mContext.getString(R.string.parse_error),
                            Toast.LENGTH_LONG).show();
                }
                serverResponseCallback.onError();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=utf-8");
                //   params.put("appid","b1b15e88fa797225412429c1c50c122a1");
                // params.put("DEVICE_TYPE", Consts.DEVICE_TYPE);
                //  params.put("APP_VERSION", Consts.APP_VERSION);
                return params;
            }
        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(60000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }



    /**
     *  Image/doc Service call
     */
    public void doSaveImageDoc(Context _activity, Map<String, String> map, String _url, ArrayList<FileDetails> fileList, MultipartPostCallback multipartPostCallback) {
        MultipartPostRequest request = new MultipartPostRequest(_activity, _url,
                map, fileList, "document");
        request.mListener = multipartPostCallback;
        request.execute();
    }



}
