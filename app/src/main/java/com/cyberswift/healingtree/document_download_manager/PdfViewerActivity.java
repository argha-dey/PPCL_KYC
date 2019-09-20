package com.cyberswift.healingtree.document_download_manager;

import android.app.Activity;
import android.os.Bundle;
import com.cyberswift.healingtree.R;
import com.github.barteksc.pdfviewer.PDFView;

public class PdfViewerActivity extends Activity {

    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf_viewer_activity);
        pdfView=findViewById(R.id.pdfv);
        pdfView.fromAsset("privilege_club_brochure.pdf").load();
    }
}
