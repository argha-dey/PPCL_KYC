package com.cyberswift.healingtree.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.cyberswift.healingtree.R;
import com.cyberswift.healingtree.activity.AwarenessArticleActivity;
import com.cyberswift.healingtree.model.AwarnessData;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

import static com.cyberswift.healingtree.utils.Constants.ARTICLE_ID_CONSTANTS;

public class HealthLibraryAdapter extends RecyclerView.Adapter<HealthLibraryAdapter.ViewHolder> {
    private Context context;
    private ArrayList<AwarnessData> awarnessDataArrayList;

    public HealthLibraryAdapter(Context context, ArrayList<AwarnessData> awarnessDataArrayList) {
        this.context = context;
        this.awarnessDataArrayList = awarnessDataArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.health_library_recyclerview_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if (awarnessDataArrayList != null) {
            viewHolder.setDataToViews(position, awarnessDataArrayList);
        }
    }

    @Override
    public int getItemCount() {
        if (awarnessDataArrayList != null && awarnessDataArrayList.size() > 0) {
            return awarnessDataArrayList.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView libraryImage;
        private TextView headingTextview;
        private TextView providerTextView;
        private TextView contentTextview;
        private ImageView arrowImageView;
        private RelativeLayout arrowImageRl;
        private RelativeLayout rl_health_library;
        private URL url = null;
        private Bitmap bitmap = null;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initViews();
            registerListenersToViews();
        }

        private void registerListenersToViews() {
//            arrowImageView.setOnClickListener(this);
          //  arrowImageRl.setOnClickListener(this);
            rl_health_library.setOnClickListener(this);
        }

        private void initViews() {
            libraryImage = (ImageView) itemView.findViewById(R.id.library_image);
            headingTextview = (TextView) itemView.findViewById(R.id.heading_tv);
            providerTextView = (TextView) itemView.findViewById(R.id.provider_tv);
            contentTextview = (TextView) itemView.findViewById(R.id.content_tv);
            arrowImageView = (ImageView) itemView.findViewById(R.id.arrow_image);
            arrowImageRl = (RelativeLayout) itemView.findViewById(R.id.arrow_image_rl);
            rl_health_library = itemView.findViewById(R.id.rl_health_library);

        }

        public void setDataToViews(final int position, final ArrayList<AwarnessData> awarnessDataArrayList) {


//            url = new URL(awarnessDataArrayList.get(position).getAwarnessImageUrl());
//            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//            libraryImage.setImageBitmap(bitmap);

         Picasso.with(context).load(awarnessDataArrayList.get(position).getAwarnessImageUrl()).into(libraryImage);


           // Picasso.with(context).load("http://192.168.1.88/healingtree/assets/files/awarness_image/32/Koala.jpg").into(libraryImage);
            headingTextview.setText(awarnessDataArrayList.get(position).getAwarnessName());
            contentTextview.setText(awarnessDataArrayList.get(position).getAwarnessDescription());
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.rl_health_library:
                    onClickOnArrowLayout();
                    break;
            }
        }

        private void onClickOnArrowLayout() {
            Intent intent = new Intent(context, AwarenessArticleActivity.class);
            intent.putExtra(ARTICLE_ID_CONSTANTS, awarnessDataArrayList.get(getAdapterPosition()).getAwarnessID());
            context.startActivity(intent);


        }
    }

}
