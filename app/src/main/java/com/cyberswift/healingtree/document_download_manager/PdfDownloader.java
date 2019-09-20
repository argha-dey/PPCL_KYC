package com.cyberswift.healingtree.document_download_manager;

import android.app.DownloadManager;
import android.content.*;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

import static android.content.Context.DOWNLOAD_SERVICE;

public class PdfDownloader {
    private static final String TAG = "Logs PdfDownloadManager";

    private static final String GOOGLE_DRIVE_PDF_READER_PREFIX = "http://drive.google.com/viewer?dummy_url=";
    private static final String PDF_MIME_TYPE = "application/pdf";
    private static final String HTML_MIME_TYPE = "text/html";


    private File file;
    private Context mContext;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 99;

    public void downloadAndOpenPdf(final Context context, final String pdfUrl) {
        Log.d("pdf", "download: " + "Got call");
        file = new File(Environment.getExternalStorageDirectory(), "Report");
        this.mContext = context;



//        if (!file.exists()) {
//            file.mkdirs();
//            Log.d("pdf", "download: " + "File exist");
//            downloadAndOpenPdf(mContext,pdfUrl);
//
//        } else {

        download(pdfUrl);
        Log.d("pdf", "download: " + "Downloading");
        openPdfDocument(file);
//        }


//        downloadAndOpenPDF(context, pdfUrl);
    }




    private void download(String url) {

        DownloadManager dm = (DownloadManager) mContext.getSystemService(DOWNLOAD_SERVICE);
        Log.d("pdf", "download: " + 1);
        DownloadManager.Request req = new DownloadManager.Request(Uri.parse(url));
        Log.d("pdf", "download: " + 2);
        req.setDestinationUri(Uri.fromFile(file));
        Log.d("pdf", "download: " + 3);
        req.setTitle("Some title");

        BroadcastReceiver receiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("pdf", "download: " + 6);
                mContext.unregisterReceiver(this);
                Log.d("pdf", "download: " + 7);
                if (file.exists()) {
                    Log.d("pdf", "download: " + 8);
                    openPdfDocument(file);

                }
            }
        };
        mContext.registerReceiver(receiver, new IntentFilter(
                DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        Log.d("pdf", "download: " + 4);
        dm.enqueue(req);
        Log.d("pdf", "download: " + 5);
        Toast.makeText(mContext, "Download started", Toast.LENGTH_SHORT).show();
    }

    public boolean openPdfDocument(File file) {
        Log.d("pdf", "download: " + 9);
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(file), "application/pdf");
        Log.d("pdf", "download: " + 10);
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        try {
            Log.d("pdf", "download: " + 11);
            mContext.startActivity(target);
            Log.d("pdf", "download: " + 12);
            return true;
        } catch (ActivityNotFoundException e) {
            Toast.makeText(mContext, "No PDF reader found", Toast.LENGTH_LONG).show();
            return false;
        }

    }

}
