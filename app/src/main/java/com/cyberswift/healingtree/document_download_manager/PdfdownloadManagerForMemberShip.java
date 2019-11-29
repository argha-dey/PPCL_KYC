package com.cyberswift.healingtree.document_download_manager;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.*;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import com.cyberswift.healingtree.BuildConfig;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;



public class PdfdownloadManagerForMemberShip extends FileProvider {

    private static final String TAG = "Logs PdfDownloadManager";

    private static final String GOOGLE_DRIVE_PDF_READER_PREFIX = "http://drive.google.com/viewer?dummy_url=";
    private static final String PDF_MIME_TYPE = "application/pdf";
    private static final String HTML_MIME_TYPE = "text/html";


    public void showPDFUrl(final Context context, final String pdfUrl) {
        if (isPDFSupported(context)) {
            downloadAndOpenPDF(context, pdfUrl);
        } else {
            askToOpenPDFThroughGoogleDrive(context, pdfUrl);
        }

    }


    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public void downloadAndOpenPDF(final Context context, final String pdfUrl) {
        String filename = "";
        try {
            filename = new GetFileInfo().execute(pdfUrl).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

//        final File tempFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), filename);
        // The place where the downloaded PDF file will be put
        File d = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS); // -> filename = maven.pdf
        final File pdfFile = new File(d, "Healing_tree_club_membership.pdf");

        Log.e(TAG, "File Path:" + pdfFile);
        if (pdfFile.exists()) {
            Log.v(TAG, " pdf File path " + pdfFile.getAbsolutePath());
            openPDF(context, pdfFile);
            return;
        }

        // Show progress dialog while downloading
        final ProgressDialog progress = ProgressDialog.show(context, "Downloading", "Please Wait.... ", true);

        // Create the download request
        DownloadManager.Request r = new DownloadManager.Request(Uri.parse(pdfUrl));
        r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
        final DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        BroadcastReceiver onComplete = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (!progress.isShowing()) {
                    return;
                }
                context.unregisterReceiver(this);

                progress.dismiss();
                long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                Cursor c = dm.query(new DownloadManager.Query().setFilterById(downloadId));

                if (c.moveToFirst()) {
                    int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    if (status == DownloadManager.STATUS_SUCCESSFUL) {
                        openPDF(context, pdfFile);
                    }
                }
                c.close();
            }
        };
        context.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        // Enqueue the request
        dm.enqueue(r);
    }


    public void askToOpenPDFThroughGoogleDrive(final Context context, final String pdfUrl) {
        new AlertDialog.Builder(context)
                .setTitle("title")
                .setMessage("show_online")
                .setNegativeButton("NO", null)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openPDFThroughGoogleDrive(context, pdfUrl);
                    }
                })
                .show();
    }

    public void openPDFThroughGoogleDrive(final Context context, final String pdfUrl) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse(GOOGLE_DRIVE_PDF_READER_PREFIX + pdfUrl), HTML_MIME_TYPE);
        context.startActivity(i);
    }

    public final void openPDF(Context context, File pdfFile) {

        Uri path = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", pdfFile);

        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(pdfIntent);
    }

    public boolean isPDFSupported(Context context) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        File d = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS); // -> filename = maven.pdf
        final File pdfFile = new File(d, "Healing_tree_club_membership.pdf");
        Uri path = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", pdfFile);
        i.setDataAndType(path, PDF_MIME_TYPE);
        return context.getPackageManager().queryIntentActivities(i, PackageManager.MATCH_DEFAULT_ONLY).size() > 0;
    }

    // get File name from dummy_url
    class GetFileInfo extends AsyncTask<String, Integer, String> {
        protected String doInBackground(String... urls) {
            URL url;
            String filename = null;
            try {
                url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                conn.setInstanceFollowRedirects(false);
                if (conn.getHeaderField("Content-Disposition") != null) {
                    String depo = conn.getHeaderField("Content-Disposition");

                    String depoSplit[] = depo.split("filename=");
                    filename = depoSplit[1].replace("filename=", "").replace("\"", "").trim();
                } else {
                    filename = "Healing_tree_club_membership.pdf";
                }
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
            }

            Log.d("pdf", "doInBackground: " + filename);
            return filename;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // use result as file name
        }
    }

}
