package com.cyberswift.healingtreeorg.patientModuleActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cyberswift.healingtreeorg.AppController;
import com.cyberswift.healingtreeorg.BuildConfig;
import com.cyberswift.healingtreeorg.R;
import com.cyberswift.healingtreeorg.document_download_manager.FilePath;
import com.cyberswift.healingtreeorg.document_download_manager.FileUtils;
import com.cyberswift.healingtreeorg.interfaces.MultipartPostCallback;
import com.cyberswift.healingtreeorg.model.FileDetails;
import com.cyberswift.healingtreeorg.retrofit.ApiClient;
import com.cyberswift.healingtreeorg.retrofit.ApiInterface;
import com.cyberswift.healingtreeorg.utils.AlertDialogCallBack;
import com.cyberswift.healingtreeorg.utils.Constants;
import com.cyberswift.healingtreeorg.utils.LocalModel;
import com.cyberswift.healingtreeorg.utils.Prefs;
import com.cyberswift.healingtreeorg.utils.Urls;
import com.cyberswift.healingtreeorg.utils.Utils;
import com.cyberswift.healingtreeorg.volley.VolleyTaskManager;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OrderMedicineActivity extends BaseActivity {
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 999;
    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    private static final int galleryRequestCode = 1001, cameraRequestCode = 1002;
    String[] permissionsRequired = new String[]{android.Manifest.permission.CAMERA,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE};
    private boolean sentToSettings = false;
    private String cameraImagePath = "";
    private String imageFileName = "";
    private Uri mImageUri = null;
    private Uri fileUri;
    private TextView tv_no_file_chosen, tv_file_preview, tv_description;
    private ImageView delete_btn;
    private Context mContext;
    private ArrayList<FileDetails> imageDocFileList;
    private Prefs mPrefs;
    public AppController appController;
    VolleyTaskManager mVollyTaskManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_order_medicine);
        mContext = OrderMedicineActivity.this;
        initViews();

    }


    private void initViews() {
        mContext = OrderMedicineActivity.this;
        mPrefs = new Prefs(mContext);
        mVollyTaskManager = new VolleyTaskManager(mContext);
        appController = (AppController) getApplication();
        imageDocFileList = new ArrayList<FileDetails>();
        tv_no_file_chosen = findViewById(R.id.tv_no_file_chosen);
        tv_file_preview = findViewById(R.id.tv_file_preview);
        tv_description = findViewById(R.id.tv_description);
        delete_btn = findViewById(R.id.iv_delete);
        askForPermission();
    }


    private void askForPermission() {
        if (ContextCompat.checkSelfPermission(this, permissionsRequired[0]) !=
                PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                permissionsRequired[2]) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                permissionsRequired[3]) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[0]) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[2]) || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[3])) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(OrderMedicineActivity.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Camera and Location permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        sentToSettings = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                ActivityCompat.requestPermissions(OrderMedicineActivity.this, permissionsRequired,
                        PERMISSION_CALLBACK_CONSTANT);
            }
            mPrefs.setPermissionStatus(true);
        } else {
            //You already have the permission, just go ahead.
            proceedAfterPermission();
        }
    }

    private void proceedAfterPermission() {
        Toast.makeText(getBaseContext(), "We got All Permissions", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull
            int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CALLBACK_CONSTANT) {
            //check if all permissions are granted
            boolean allGranted = false;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    allGranted = true;
                } else {
                    allGranted = false;
                    break;
                }
            }

            if (allGranted) {
                proceedAfterPermission();
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(OrderMedicineActivity.this, permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(OrderMedicineActivity.this, permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[2]) || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[3])) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OrderMedicineActivity.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Camera and Location permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(OrderMedicineActivity.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                Toast.makeText(getBaseContext(), "Unable to get Permission", Toast.LENGTH_LONG).show();
            }
        }
    }


    public void onChooseFile(View view) {
        if (imageDocFileList.size() < 1) {
            attachFile();
        } else
            Toast.makeText(mContext, "Maximum number of files has already been selected!", Toast.LENGTH_SHORT).show();
    }

    private void attachFile() {
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.attach_file_dialog);
        dialog.getWindow().setLayout(DrawerLayout.LayoutParams.MATCH_PARENT, DrawerLayout.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 0.7f;
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        // set the custom dialog components - text, image and button
        Button btn_takePicture = (Button) dialog.findViewById(R.id.btn_takePicture);
        Button btn_attach_file = (Button) dialog.findViewById(R.id.btn_attach_file);
        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);

        btn_attach_file.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //System.out.println(Build.VERSION.SDK_INT);
                Intent galleryintent = new Intent();
                if (Build.VERSION.SDK_INT < 19) {
                    galleryintent.setAction(Intent.ACTION_GET_CONTENT);
                } else {
                    galleryintent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    galleryintent.addCategory(Intent.CATEGORY_OPENABLE);
                }
                galleryintent.setType("*/*");
                startActivityForResult(galleryintent, galleryRequestCode);
            }
        });

        btn_takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                cameraImagePath = Utils.getDefaultFilePathForImg();
                System.out.println("Image Path " +cameraImagePath);
                Intent cameraIntent;
                cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    //    fileUri = Uri.fromFile(file);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(cameraImagePath)));
                } else {
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID + ".provider", new File(cameraImagePath)));
                }
                startActivityForResult(cameraIntent, cameraRequestCode);

          /*      imageFileName = "HT_" + Utils.getCurrentDateNTime(Constants.DATE_TIME_FORMAT_7) + System.currentTimeMillis() + ".jpg";
                mImageUri = storeImageFileAndGetUri(imageFileName);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                startActivityForResult(intent, cameraRequestCode);*/


            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private Uri storeImageFileAndGetUri(String fileName) {
        Uri photoFileUri = null;
        File outputDir = new File(Environment.getExternalStorageDirectory() + "/HT");
        Utils.showLog("file path", outputDir.toString());

        if (!outputDir.exists())  // have the object build the directory structure, if needed.
            outputDir.mkdirs();
        File photoFile = new File(outputDir, fileName);
        photoFileUri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID + ".provider", photoFile);
        return photoFileUri;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case galleryRequestCode:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri selectedFileUri = data.getData();
                    String mimeType = getContentResolver().getType(selectedFileUri);
                    String selectedFilePath = null;

                    if (mimeType.equals("image/jpeg") || mimeType.equals("image/png")) {
                        selectedFilePath = FilePath.getPath(mContext, selectedFileUri);
                    } else {
                        selectedFilePath = FileUtils.getPath(mContext, selectedFileUri);
                    }
                    System.out.println("File Path --" + selectedFilePath);

                    Uri uri = data.getData();
                    int actionType = Utils.checkFileExtension(mContext, uri);

                    //Log.e(" ", "uri" + uri);
                    String uriString = uri.toString();
                    File myFile = new File(uriString);

                    //Log.e(" ", "uri" + uri);
                    String displayName = null;
                    FileDetails file = null;
                    // int sizeIndex = 0;
                    try {
                        InputStream input = getContentResolver().openInputStream(uri);
                        if (uriString.startsWith("content://")) {
                            Cursor cursor = null;
                            try {
                                cursor = mContext.getContentResolver().query(uri, null, null, null, null);
                                if (cursor != null && cursor.moveToFirst()) {
                                    displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                }
                            } finally {
                                cursor.close();
                            }
                        } else if (uriString.startsWith("file://")) {
                            displayName = myFile.getName();
                        }
                        String fileType = displayName.substring(displayName.lastIndexOf(".") + 1);

                        if (Utils.isExist(Constants.imageDocFileTypes, fileType) || (mimeType != null && Utils.isExist(Constants.imageDocMimeTypes, mimeType))) {
                            file = new FileDetails();
                            file.setFileName(displayName);
                            file.setFilePath(uriString);
                            file.setSelectedFilePath(selectedFilePath);
                            // file.setFileSize(sizeIndex);
                            file.setInputStream(input);
                            file.setActionType(actionType);
                            file.setMimeType(mimeType);
                            file.setFromWhere("FromFile");
                            imageDocFileList.add(file);
                            generateFileList(imageDocFileList);
                        } else {
                            Toast.makeText(mContext, "Please choose another file type.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }


                }
                break;

            case cameraRequestCode:
                if (resultCode == RESULT_OK) {

                    int actionType = Utils.checkFileExtension(mContext, Uri.parse(cameraImagePath));
                    FileDetails file = null;
                    //Log.e("Dalmia", cameraImagePath + " " + actionType);
                    try {
                        File img = new File(cameraImagePath);
                        InputStream input = getContentResolver().openInputStream(Uri.fromFile(img));
                        file = new FileDetails();
                        file.setFileName(img.getName());
                        file.setFilePath(Uri.fromFile(img).toString());
                        file.setSelectedFilePath(img.getAbsolutePath());
                        // file.setFileSize(img.length());
                        file.setInputStream(input);
                        file.setMimeType("image/jpeg");
                        file.setActionType(actionType);
                        file.setFromWhere("Camera");
                        imageDocFileList.add(file);// Add in File list Array
                        generateFileList(imageDocFileList);
                    } catch (FileNotFoundException e) {

                        e.printStackTrace();
                    }
                }
                break;
        }
    }


    private void generateFileList(final ArrayList<FileDetails> arrayFile) {
        if (arrayFile != null && arrayFile.size() > 0) {
            for (int i = 0; i < arrayFile.size(); i++) {
                tv_no_file_chosen.setText(arrayFile.get(i).getFileName());
                delete_btn.setVisibility(View.VISIBLE);
                final int position = i;
                if (arrayFile.get(i).getFileName() != null) {
                    tv_file_preview.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if (imageDocFileList.size() > 0) {
                                final Dialog imgDialog = new Dialog(mContext);
                                imgDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                imgDialog.setContentView(R.layout.dialog_image_fullimage);
                                imgDialog.show();
                                ImageView iv_imageFile = (ImageView) imgDialog.findViewById(R.id.imageViewFull);
                                String fileType = arrayFile.get(position).getFileName().substring(arrayFile.get(position).getFileName().lastIndexOf(".") + 1);
                                if (fileType.equalsIgnoreCase("jpg") || fileType.equalsIgnoreCase("png") || fileType.equalsIgnoreCase("jpeg")) {
                                    AppController.getInstance().displayUniversalImg(arrayFile.get(position).getFilePath(), iv_imageFile, R.drawable.no_image);
                                } else {
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(arrayFile.get(position).getStringFileUri()));
                                    if (browserIntent.resolveActivity(mContext.getPackageManager()) != null) {
                                        mContext.startActivity(browserIntent);
                                        if (imgDialog != null && imgDialog.isShowing())
                                            imgDialog.dismiss();
                                    } else {
                                        Toast.makeText(mContext, "Can't view the document.", Toast.LENGTH_LONG).show();
                                        if (imgDialog != null && imgDialog.isShowing())
                                            imgDialog.dismiss();
                                    }

                                }


                            } else
                                Toast.makeText(mContext, "You are not select any document for preview!", Toast.LENGTH_LONG).show();
                        }
                    });

                    delete_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Utils.showCallBackMessageWithOkCancelCustomButton(mContext, "Do you want to delete?", "Ok", "Cancel", new AlertDialogCallBack() {
                                @Override
                                public void onSubmit() {
                                    if (imageDocFileList.size() > 0) {
                                        imageDocFileList.remove(position);
                                        delete_btn.setVisibility(View.GONE);
                                        tv_no_file_chosen.setText("");
                                        generateFileList(imageDocFileList);
                                    }
                                }

                                @Override
                                public void onCancel() {


                                }
                            });
                        }
                    });
                }

            }
        }

    }

    public void onUploadOderMedicineFile(View view) {
        if(mPrefs.getUserEmailId().equals("")||mPrefs.getUserEmailId().isEmpty()){
            Utils.showAlertDialogWithOkButton(mContext, "Update User Profile!", "Please update your profile before booking");
        }
        else if(mPrefs.getUserPhoneNumber().equals("")||mPrefs.getUserPhoneNumber().isEmpty()){
            Utils.showAlertDialogWithOkButton(mContext, "Update User Profile!", "Please update your profile before booking");
        }
        else {
            if (!tv_description.getText().toString().trim().equals("")) {
                    UploadFileUsingMultiPart(imageDocFileList, tv_description.getText().toString());
            //    UploadFileUsingMultiPartRetrofit(imageDocFileList, tv_description.getText().toString());
             //   uploadToServer(imageDocFileList);
            } else
                Toast.makeText(mContext, "Please add any short description!", Toast.LENGTH_LONG).show();
        }
    }


    private void uploadToServer(ArrayList<FileDetails> imageDocFileList) {
        Retrofit retrofit = ApiClient.getRetrofitClient(this);
        ApiInterface uploadAPIs = retrofit.create(ApiInterface.class);
        //Create a file object using file path
        String filePath = imageDocFileList.get(0).getSelectedFilePath();
        File file = new File(filePath);

        // Create a request body with file and image media type
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("*/*"), file);

        // Create MultipartBody.Part using file request-body,file name and part name
        MultipartBody.Part part = MultipartBody.Part.createFormData("document", file.getName(), fileReqBody);

        //Create request body with text description and text media type
        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), mPrefs.getUserID());
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), tv_description.getText().toString());

        //
        Call call = uploadAPIs.uploadImage(part, user_id,description);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Utils.showCallBackMessageWithOkCancelCustomButton(mContext, "File uploaded successfully", "OK", "", new AlertDialogCallBack() {
                    @Override
                    public void onSubmit() {
                        finish();
                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });

    }
    private void UploadFileUsingMultiPartRetrofit(ArrayList<FileDetails> imageDocFileList, String desc){
        RequestBody user_id = RequestBody.create(MediaType.parse("multipart/form-data"),mPrefs.getUserID());
        RequestBody reamrks = RequestBody.create(MediaType.parse("multipart/form-data"),desc);
        MultipartBody.Part prescriptionFile = null;
        if(imageDocFileList.get(0).getSelectedFilePath()!= null){
            String path = Environment.getExternalStorageDirectory()+ "/HT_ImageFolder/" + imageDocFileList.get(0).getFileName();
            File file = new File(path);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"),file);
            prescriptionFile = MultipartBody.Part.createFormData("document",file.getName(),requestFile);
        }
        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.upload(user_id,reamrks,prescriptionFile);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Utils.showCallBackMessageWithOkCancelCustomButton(mContext, "File uploaded successfully", "OK", "", new AlertDialogCallBack() {
                        @Override
                        public void onSubmit() {
                            finish();
                        }

                        @Override
                        public void onCancel() {

                        }
                    });

                } else {
                    LocalModel.getInstance().cancelProgressDialog();
                }
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                LocalModel.getInstance().cancelProgressDialog();
            }
        });

    }
    private void UploadFileUsingMultiPart(ArrayList<FileDetails> imageDocFileList, String desc) {

        // new SendActivityLogViaMultipartATask(OrderMedicineActivity.this,desc,mPrefs.getUserID(),imageDocFileList).execute();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_id", mPrefs.getUserID());
        map.put("reamrks", desc);
        String _url = Urls.BASE_URL + Urls.UPLOAD_PREPCRIPTION;
        mVollyTaskManager.doSaveImageDoc(mContext, map, _url, imageDocFileList, new MultipartPostCallback() {
            @Override
            public void onMultipartPost(String response) {
                try {
                    JSONObject requestJsonObject = new JSONObject(response);
                    if (requestJsonObject.optBoolean("status")) {
                        Utils.showCallBackMessageWithOkCancelCustomButton(mContext, requestJsonObject.optString("message"), "OK", "", new AlertDialogCallBack() {
                            @Override
                            public void onSubmit() {
                                finish();
                            }

                            @Override
                            public void onCancel() {

                            }
                        });
                    } else {
                        Toast.makeText(mContext, "Error occurred while posting Data", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(mContext, "Error occurred while posting Data", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
}




