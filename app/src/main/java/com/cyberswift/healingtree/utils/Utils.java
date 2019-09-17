package com.cyberswift.healingtree.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.cyberswift.healingtree.R;
import com.cyberswift.healingtree.interfaces.AlertDialogWithCancelAndRetryListener;
import com.cyberswift.healingtree.interfaces.CustomAlertDialogListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Utils {

    /**
     * launch a new activity
     * */
    public static void launchActivity(Context context, Class<?> destinationClass) {
        Intent i = new Intent(context, destinationClass);
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(i);
    }


    /**
     * launch a new activity and finish current activity
     * */
    public static void launchActivityWithFinish(Context context, Class<?> destinationClass) {
        Intent i = new Intent(context, destinationClass);
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(i);
        ((Activity)context).finish();
    }


    /**
     * Show log
     * */
    public static void showLog(String tag, String value) {
        if (Constants.IS_DEVELOPMENT_MODE)
            Log.d("@@@ ", tag + "==> " + value);
    }


    /**
     * Show toasts
     * */
    public static void showToast(Context context, String string, String length) {
        switch (length) {
            case Constants.SHORT:
                Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
                break;
            case Constants.LONG:
                Toast.makeText(context, string, Toast.LENGTH_LONG).show();
                break;
            case Constants.MIDDLE_SHORT:
                Toast tMiddleShort = Toast.makeText(context, string, Toast.LENGTH_SHORT);
                tMiddleShort.setGravity(Gravity.CENTER, 0, 0);
                tMiddleShort.show();
                break;
            case Constants.MIDDLE_LONG:
                Toast tMiddleLong = Toast.makeText(context, string, Toast.LENGTH_LONG);
                tMiddleLong.setGravity(Gravity.CENTER, 0, 0);
                tMiddleLong.show();
                break;
            case Constants.TOP_SHORT:
                Toast tTopShort = Toast.makeText(context, string, Toast.LENGTH_SHORT);
                tTopShort.setGravity(Gravity.TOP, 0, 0);
                tTopShort.show();
                break;
            case Constants.TOP_LONG:
                Toast tTopLong = Toast.makeText(context, string, Toast.LENGTH_LONG);
                tTopLong.setGravity(Gravity.TOP, 0, 0);
                tTopLong.show();
                break;
        }
    }


    /**
     * Check internet connected or not
     * */

    public static boolean checkConnectivity(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkGpsStatus(Context mContext) {
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        return locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }


    public static boolean checkGpsHighAccuracyMode(Context mContext) {
        int location = 0;
        boolean GpsAccuracy = false;
        try {
            location = Settings.Secure.getInt(mContext.getContentResolver(), Settings.Secure.LOCATION_MODE);
            if (location == Settings.Secure.LOCATION_MODE_HIGH_ACCURACY) {
                GpsAccuracy = true;
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        return GpsAccuracy;
    }


    // Works for OS version < 18
    public static boolean isMockLocationOn(Context context) {
        // Returns true if mock location enabled, false if not enabled.
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION).equals("0");
    }


    public static ProgressDialog showProgressDialog(final Context context, final String msg) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        return progressDialog;
    }


    /**
     * Get current date and time
     * */
    public static String getCurrentDateNTime(String input_format) {
        Date d = new Date();
        CharSequence curr_datetime  = android.text.format.DateFormat.format(input_format, d.getTime());
        return curr_datetime.toString();
    }


    /**
     * Change date and time format
     * */
    public static String changeDateNTimeFormat(String inputDateTime, String inFormat, String outFormat) {
        String output_datetime = "";
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat(inFormat, Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat(outFormat, Locale.getDefault());
            output_datetime = outputFormat.format(inputFormat.parse(inputDateTime));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output_datetime;
    }


    public static void showSoftKeyboard(Context context, View view){
        InputMethodManager imm =(InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);
    }


    public static void hideSoftKeyboard(Context context, View view){
        InputMethodManager imm =(InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public static String makeTextViewUnderline(String text) {
        return String.valueOf(Html.fromHtml("<u>" + text + "</u>"));
    }


    public static void makeTextviewDifferentColor(String firstLetter, String restText, TextView textView) {
        String text = "<font color=#212121>" + firstLetter + "</font> <font color=#757575>" + restText + "</font>";
        textView.setText(Html.fromHtml(text));
    }


    /**
     * Check upper case, lower case, number & special character available or not
     * */
    public static boolean isValidPassword(String newPassword) {
        int upper = 0, lower = 0, number = 0, special = 0;
        for(int i = 0; i < newPassword.length(); i++) {
            char ch = newPassword.charAt(i);
            if (ch >= 'A' && ch <= 'Z')
                upper++;
            else if (ch >= 'a' && ch <= 'z')
                lower++;
            else if (ch >= '0' && ch <= '9')
                number++;
            else
                special++;
        }
        return (upper > 0 && lower > 0 && number > 0 && special > 0);
    }


    public static String getAppVersion(Context context) {
        String version = "0.0";
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return version;
    }







    /**
     * Show custom alert dialog
     * */
    public static void showCustomAlertDialog(final Context mContext, final Boolean isTitileAllowed, final String title,
                                             final Boolean isMessageAllowed, final String message, final Boolean isPosBtnAllowed, final String posBtnName,
                                             final Boolean isNegBtnAllowed, final String negBtnName, final Boolean isDialogCancellable,
                                             final CustomAlertDialogListener dialogCallBack) {
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @SuppressLint("ResourceType")
            public void run() {
                final AlertDialog.Builder adBuilder = new AlertDialog.Builder(mContext);

                // if title allowed then change title color & set
                if (isTitileAllowed) {
                    SpannableStringBuilder builder = new SpannableStringBuilder();
                    SpannableString spannable = new SpannableString(title);
                    spannable.setSpan(new ForegroundColorSpan(Color.parseColor(mContext.getString(R.color.colorPrimary))), 0, title.length(), 0);
                    builder.append(spannable);
                    adBuilder.setTitle(builder);
                }

                // if message allowed then change message color & set
                if (isMessageAllowed) {
                    ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor(mContext.getString(R.color.text_color)));
                    SpannableStringBuilder sSBuilder = new SpannableStringBuilder(message);
                    sSBuilder.setSpan(foregroundColorSpan, 0, message.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    adBuilder.setMessage(sSBuilder);
                }

                // set dialog cancellable or not
                adBuilder.setCancelable(isDialogCancellable);

                // if position button allowed - set button name & click listener
                if (isPosBtnAllowed) {
                    adBuilder.setPositiveButton(posBtnName, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialogCallBack.positiveButtonWork();
                        }
                    });
                }

                // if negative button allowed - set button name & click listener
                if (isNegBtnAllowed) {
                    adBuilder.setNegativeButton(negBtnName, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialogCallBack.negativeButtonWork();
                        }
                    });
                }

                // Create the alert dialog
                AlertDialog aDialog = adBuilder.create();
                aDialog.show();

                // Get the alert dialog buttons reference
                Button positiveButton = aDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button negativeButton = aDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

                // Change the alert dialog buttons text and background color
                positiveButton.setTextColor(Color.parseColor(mContext.getString(R.color.colorPrimaryDark)));
                positiveButton.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                positiveButton.setTextSize(18);
                positiveButton.setAllCaps(false);
                // positiveButton.setBackgroundColor(Color.parseColor("#FFE1FCEA"));
                negativeButton.setTextColor(Color.parseColor(mContext.getString(R.color.colorPrimaryDark)/*Color.parseColor("#47B734")*/));
                negativeButton.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                negativeButton.setTextSize(18);
                negativeButton.setAllCaps(false);
                // negativeButton.setBackgroundColor(Color.parseColor("#FFFCB9B7"));

                // Add or create your own background drawable or color for AlertDialog window
                //Window view = ((AlertDialog)aDialog).getWindow();
                //view.setBackgroundDrawableResource(backgroundColor);
                //view.setBackgroundDrawableResource(R.drawable.bg_roundcorner_withborder_grey);  // set drawable to background

            }
        });
    }


    /**
     * Show custom alert dialog with ok button only. It does not perform any action on click
     * */
    @SuppressLint("ResourceType")
    public static void showAlertDialogWithOkButton(Context context, String title, String msg) {
        Utils.showCustomAlertDialog(context, true, title, true, msg, true, "Ok",
                false, "", true, new CustomAlertDialogListener() {
                    @Override
                    public void positiveButtonWork() {}

                    @Override
                    public void negativeButtonWork() {}
                });
    }

    /**
     * Checking permission request
     */
    public static boolean checkAndRequestAllPermissions(final Context context) {
        int locationPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE);
//        int storagePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        int cameraPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.CALL_PHONE);
        }
        /*if (storagePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.CAMERA);
        }*/

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions((Activity) context, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 121);
            return false;
        }

        return true;
    }

    // Alert dialog with Cancel & Retry option [callback feature]
    public static void showAlertDialogWithCancelAndRetry(final Context context, final String message,
                                                         final AlertDialogWithCancelAndRetryListener callBack) {
        ((Activity) context).runOnUiThread(new Runnable() {
            public void run() {
                final AlertDialog.Builder alert = new AlertDialog.Builder(context);
                SpannableStringBuilder builder = new SpannableStringBuilder();
                String first = "Network Error!";
                SpannableString spannable = new SpannableString(first);
                spannable.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorRed)), 0, first.length(), 0);
                builder.append(spannable);
                alert.setTitle(builder);
                alert.setCancelable(false);
                alert.setMessage(message);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        callBack.onCancelClick();
                    }
                });
                alert.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        callBack.onRetryClick();
                    }
                });
                alert.show();
            }
        });
    }


    public static boolean isSelectedDateBeforeCurrentDate(String selectedDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_FORMAT_1, Locale.getDefault());
        Date dateSelected = null, dateCurrent = null;
        try {
            dateSelected = sdf.parse(selectedDate);
            dateCurrent = sdf.parse(Utils.getCurrentDateNTime(Constants.DATE_TIME_FORMAT_1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateSelected.before(dateCurrent);
    }
    // check internet connection
    public static boolean isOnline(Context mContext) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
        /*if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }*/
    }
}
