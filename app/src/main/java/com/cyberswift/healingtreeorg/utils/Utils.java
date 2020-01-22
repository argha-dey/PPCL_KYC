package com.cyberswift.healingtreeorg.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.*;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.ContactsContract;
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
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.cyberswift.healingtreeorg.R;
import com.cyberswift.healingtreeorg.interfaces.AlertDialogWithCancelAndRetryListener;
import com.cyberswift.healingtreeorg.interfaces.CustomAlertDialogListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    /**
     * launch a new activity
     */
    public static void launchActivity(Context context, Class<?> destinationClass) {
        Intent i = new Intent(context, destinationClass);
      //   i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(i);
    }


    /**
     * launch a new activity and finish current activity
     */
    public static void launchActivityWithFinish(Context context, Class<?> destinationClass) {
        Intent i = new Intent(context, destinationClass);
          i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         context.startActivity(i);
        ((Activity) context).finish();
    }


    /**
     * Show log
     */
    public static void showLog(String tag, String value) {
        if (Constants.IS_DEVELOPMENT_MODE)
            Log.d("@@@ ", tag + "==> " + value);
    }


    /**
     * Show toasts
     */
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
     */

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
     */
    public static String getCurrentDateNTime(String input_format) {
        Date d = new Date();
        CharSequence curr_datetime = android.text.format.DateFormat.format(input_format, d.getTime());
        return curr_datetime.toString();
    }


    /**
     * Change date and time format
     */
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


    public static void showSoftKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }


    public static void hideSoftKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
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
     */
/*    public static boolean isValidPassword(String newPassword) {
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
    }*/
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
     */
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
     */
    @SuppressLint("ResourceType")
    public static void showAlertDialogWithOkButton(Context context, String title, String msg) {
        Utils.showCustomAlertDialog(context, true, title, true, msg, true, "Ok",
                false, "", true, new CustomAlertDialogListener() {
                    @Override
                    public void positiveButtonWork() {
                    }

                    @Override
                    public void negativeButtonWork() {
                    }
                });
    }

    /**
     * Checking permission request
     */
    public static boolean checkAndRequestAllPermissions(final Context context) {
        int locationPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE);
//        int storagePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        int cameraPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        int contactSavePermissionWrite = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_CONTACTS);

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

        if (contactSavePermissionWrite != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_CONTACTS);
        }

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

    public static String getDefaultFilePathForImg() {
        File prntFile = new File(Environment.getExternalStorageDirectory()+ "/HT_ImageFolder");
        prntFile.mkdirs();
        String getDestinationFileFortakePic = prntFile.getAbsolutePath() + "/HealingTree_" + System.currentTimeMillis() + ".jpg";
        return getDestinationFileFortakePic;
    }


    //check if choose file is image or video
    @SuppressLint("DefaultLocale")
    public static int checkFileExtension(Context context, Uri selectedFile) {
        int type = -1;
        try {
            ContentResolver cR = context.getContentResolver();
            String extention = cR.getType(selectedFile);


            if (extention != null) {
                //checking for  extention
                for (String extensionval : Constants.imageDocFileTypes) {
                    if (extention.toLowerCase().contains(extensionval)) {
                        type = 1;
                        break;
                    }
                }
            } else {
                String extention1 = selectedFile.getPath();
                //checking for image extention
                for (String extensionval : Constants.imageDocFileTypes) {
                    if (extention1.toLowerCase().contains(extensionval)) {
                        type = 1;
                        break;
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return type;
    }

    public static boolean isExist(String[] array, String val) {

        for (String s : array) {
            if (s.equalsIgnoreCase(val)) {
                return true;
            }

        }
        return false;

    }

    public static void showCallBackMessageWithOkCancelCustomButton(final Context mContext, final String message,
                                                                   final String positiveButton, final String negativeButton, final AlertDialogCallBack callBack) {
        ((Activity) mContext).runOnUiThread(new Runnable() {

            public void run() {
                final AlertDialog.Builder alert = new AlertDialog.Builder(
                        mContext);
                alert.setTitle(R.string.app_name);
                alert.setCancelable(false);
                alert.setMessage(message);
                alert.setPositiveButton(positiveButton,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                callBack.onSubmit();
                            }
                        });
                alert.setNegativeButton(negativeButton,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                callBack.onCancel();
                            }
                        });
                alert.show();
            }
        });
    }

    public static String changeDateTimeWithMonthInStringFormat(String date) {
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");

        try {
            Date scheduleDte = inputFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDateStr = outputFormat.format(date);

        return outputDateStr;
    }

    public static String currentDate() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String formattedDate = df.format(date);
        return formattedDate;
    }
    public static String currentDateAsYYYYMMDD() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(date);
        return formattedDate;
    }
    public static String currentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String time = sdf.format(new Date());
        return time;
    }


    public static boolean isValidPassword(String password) {
        Matcher matcher = Pattern.compile("((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{6,20})").matcher(password);
        return matcher.matches();
    }

    public static boolean dateCompar(String myDate) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String getCurrentDateTime = sdf.format(c.getTime());
        if (getCurrentDateTime.compareTo(myDate) < 0)
            return true;
        else
            return false;
    }
    public static void filePreviewDialog(final Context _context, String fileUrl) {
        final Dialog fileDitailsDialog = new Dialog(_context, android.R.style.Theme_NoTitleBar_Fullscreen);
        fileDitailsDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        fileDitailsDialog.setContentView(R.layout.file_preview_dialog_layout);
        fileDitailsDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        fileDitailsDialog.getWindow().setGravity(Gravity.CENTER);
        ImageView iv_close = (ImageView) fileDitailsDialog.findViewById(R.id.iv_close);
        ImageView iv_image_viewer = (ImageView) fileDitailsDialog.findViewById(R.id.iv_image_viewer);
        WebView webview_document = (WebView) fileDitailsDialog.findViewById(R.id.webview_document);
        String fileType = fileUrl.substring(fileUrl.lastIndexOf('.') + 1);
        if (fileType.equalsIgnoreCase("jpg") || fileType.equalsIgnoreCase("png")) {

            webview_document.setVisibility(View.GONE);
            iv_image_viewer.setVisibility(View.VISIBLE);
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.setDefaultLoadingListener(new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {
                    LocalModel.getInstance().showProgressDialog(_context, _context.getResources().getString(R.string.please_wait_msg), false);
                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {
                    LocalModel.getInstance().cancelProgressDialog();
                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    LocalModel.getInstance().cancelProgressDialog();
                }

                @Override
                public void onLoadingCancelled(String s, View view) {
                    LocalModel.getInstance().cancelProgressDialog();
                }
            });
            imageLoader.displayImage(fileUrl, iv_image_viewer);

        } else {
            webview_document.setVisibility(View.VISIBLE);
            iv_image_viewer.setVisibility(View.GONE);
            webview_document.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    LocalModel.getInstance().showProgressDialog(_context, _context.getResources().getString(R.string.please_wait_msg), false);
                }


                @Override
                public void onPageFinished(WebView view, String url) {
                    LocalModel.getInstance().cancelProgressDialog();

                }
            });
            webview_document.getSettings().setJavaScriptEnabled(true);
            webview_document.loadUrl("https://docs.google.com/viewer?url=" + fileUrl);

        }


        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalModel.getInstance().cancelProgressDialog();
                fileDitailsDialog.dismiss();
            }
        });


        fileDitailsDialog.show();


    }

  public static  void addContact(Context ctx, String displayname, String homenumber) {
        String DisplayName = displayname;
        String MobileNumber = homenumber;

        ArrayList<ContentProviderOperation> contentProviderOperation = new
                ArrayList<ContentProviderOperation>();

        contentProviderOperation.add(ContentProviderOperation
                .newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());

        // ------------------------------------------------------ Names
        if (DisplayName != null) {
            contentProviderOperation.add(ContentProviderOperation
                    .newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(
                            ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(
                            ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(
                            ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                            DisplayName).build());
        }

        // ------------------------------------------------------ Mobile Number
        if (MobileNumber != null) {
            contentProviderOperation.add(ContentProviderOperation
                    .newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(
                            ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(
                            ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,
                            MobileNumber)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                    .build());
        }


        // Asking the Contact provider to create a new contact
        try {
            ctx.getContentResolver()
                    .applyBatch(ContactsContract.AUTHORITY, contentProviderOperation);
        } catch (Exception e) {
            e.printStackTrace();
            //show exception in toast
            Toast.makeText(ctx, "Exception: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public static void showCallBackMessageWithOk(final Context mContext,
                                                 final String message, final AlertDialogCallBack callBack) {
        ((Activity) mContext).runOnUiThread(new Runnable() {

            public void run() {
                final AlertDialog.Builder alert = new AlertDialog.Builder(
                        mContext);
                alert.setTitle(R.string.app_name);
                alert.setCancelable(false);
                alert.setMessage(message);
                alert.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                callBack.onSubmit();
                            }
                        });
                alert.setCancelable(false);
                alert.show();
            }
        });
    }

    public static String getCountOfDays(String createdDateString, String expireDateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        Date createdConvertedDate = null, expireCovertedDate = null, todayWithZeroTime = null;
        try {
            createdConvertedDate = dateFormat.parse(createdDateString);
            expireCovertedDate = dateFormat.parse(expireDateString);

            Date today = new Date();

            todayWithZeroTime = dateFormat.parse(dateFormat.format(today));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int cYear = 0, cMonth = 0, cDay = 0;

        if (createdConvertedDate.after(todayWithZeroTime)) {
            Calendar cCal = Calendar.getInstance();
            cCal.setTime(createdConvertedDate);
            cYear = cCal.get(Calendar.YEAR);
            cMonth = cCal.get(Calendar.MONTH);
            cDay = cCal.get(Calendar.DAY_OF_MONTH);

        } else {
            Calendar cCal = Calendar.getInstance();
            cCal.setTime(todayWithZeroTime);
            cYear = cCal.get(Calendar.YEAR);
            cMonth = cCal.get(Calendar.MONTH);
            cDay = cCal.get(Calendar.DAY_OF_MONTH);
        }


    /*Calendar todayCal = Calendar.getInstance();
    int todayYear = todayCal.get(Calendar.YEAR);
    int today = todayCal.get(Calendar.MONTH);
    int todayDay = todayCal.get(Calendar.DAY_OF_MONTH);
    */

        Calendar eCal = Calendar.getInstance();
        eCal.setTime(expireCovertedDate);

        int eYear = eCal.get(Calendar.YEAR);
        int eMonth = eCal.get(Calendar.MONTH);
        int eDay = eCal.get(Calendar.DAY_OF_MONTH);

        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();

        date1.clear();
        date1.set(cYear, cMonth, cDay);
        date2.clear();
        date2.set(eYear, eMonth, eDay);

        long diff = date2.getTimeInMillis() - date1.getTimeInMillis();

        float dayCount = (float) diff / (24 * 60 * 60 * 1000);
        dayCount = dayCount+1;
        return ("" + (int) dayCount);
    }

    public static String getMobileDeviceID(final Context context) {
        String device_ID = "";
        try {
            device_ID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            Toast.makeText(context, "Device ID not available.", Toast.LENGTH_LONG).show();
        }
        return device_ID;
    }

    @TargetApi(Build.VERSION_CODES.O)

    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retrieving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = Base64.getEncoder().encodeToString(md.digest());
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        }
        catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }

}
