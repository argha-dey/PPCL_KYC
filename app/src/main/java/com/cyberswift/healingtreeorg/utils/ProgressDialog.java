package com.cyberswift.healingtreeorg.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cyberswift.healingtreeorg.R;

public class ProgressDialog extends Dialog {

    private ProgressBar progressBar;

    private Context context;
    private int identifier;
    private String loadingText = "Loading...";

    private TextView loadingTextView;
    private ImageView monkeyGifImageView;

    public ProgressDialog(@NonNull Context context, String loadingText) {
        super(context);
        this.context = context;
        this.loadingText = loadingText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_dialog_layout);

        initViews();
        setInfoToTheViews();
    }

    private void setInfoToTheViews() {
        loadingTextView.setText(loadingText);
    }

    private void initViews() {
        loadingTextView = (TextView) findViewById(R.id.loading_tv);
       // monkeyGifImageView = (ImageView)findViewById(R.id.monkey_loading_gif);
    }
}
