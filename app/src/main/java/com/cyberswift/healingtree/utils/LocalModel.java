package com.cyberswift.healingtree.utils;

import android.app.Activity;

import android.content.Context;

public class LocalModel {
    /**
     * Instance of this class.
     */
    private static LocalModel localModel;

    /**
     * Hold current activity.
     */
    private Activity currentActivity;

    /**
     * Hold current context.
     */
    private Context context;

    private ProgressDialog progressDialog;


    public Activity getCurrentActivity() {
        return currentActivity;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
        if (context instanceof Activity) {
            currentActivity = (Activity) context;
        } else {
            currentActivity = null;
        }
    }


    /**
     * Returns the singleton instance of this class.
     *
     * @return
     */
    public static LocalModel getInstance() {
        if (localModel == null) {
            localModel = new LocalModel();
        }
        return localModel;
    }


    public void showProgressDialog(Context context, String msg, boolean cancelable) {

        progressDialog = new ProgressDialog(context, msg);
        progressDialog.setCancelable(cancelable);
        progressDialog.show();
    }

    public void cancelProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

}
