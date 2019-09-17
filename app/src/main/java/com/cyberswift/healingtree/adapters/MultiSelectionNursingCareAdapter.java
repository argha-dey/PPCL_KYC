package com.cyberswift.healingtree.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import com.cyberswift.healingtree.R;
import com.cyberswift.healingtree.model.THCA_Model;

import java.util.ArrayList;

public class MultiSelectionNursingCareAdapter extends RecyclerView.Adapter<MultiSelectionNursingCareAdapter.MultiViewHolder> {

    private Context context;
    private ArrayList<THCA_Model> taineHomeCareAttendanceList;

    public MultiSelectionNursingCareAdapter(Context context) {
        this.context = context;
        taineHomeCareAttendanceList = new ArrayList<>();
    }

    public void setTrainedHomeCareAttendanceListData(ArrayList<THCA_Model> employees) {
        this.taineHomeCareAttendanceList = new ArrayList<>();
        this.taineHomeCareAttendanceList = employees;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MultiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.nursing_care_offer_item, viewGroup, false);
        return new MultiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MultiViewHolder multiViewHolder, int position) {
        multiViewHolder.bind(taineHomeCareAttendanceList.get(position));
    }

    @Override
    public int getItemCount() {
        return taineHomeCareAttendanceList.size();
    }

    class MultiViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_trained_help_in;
        private CheckBox nursing_cb;
        private TextView   tv_nursing_care_details;


        MultiViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_trained_help_in = itemView.findViewById(R.id.tv_trained_help_in);
            nursing_cb = itemView.findViewById(R.id.nursing_cb);
            tv_nursing_care_details = itemView.findViewById(R.id.tv_nursing_care_details);
        }

        void bind(final THCA_Model trainedHomeCareAttendanceList) {
          //  cb.setVisibility(trainedHomeCareAttendanceList.isChecked() ? View.VISIBLE : View.GONE);
            tv_trained_help_in.setText(trainedHomeCareAttendanceList.getHomeCareServiceName());
            tv_nursing_care_details.setText(trainedHomeCareAttendanceList.getHomeCareServiceDescribtion());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    trainedHomeCareAttendanceList.setChecked(!trainedHomeCareAttendanceList.isChecked());
                   // cb.setVisibility(trainedHomeCareAttendanceList.isChecked() ? View.VISIBLE : View.GONE);
                }
            });
        }
    }

    public ArrayList<THCA_Model> getAll() {
        return taineHomeCareAttendanceList;
    }

    public ArrayList<THCA_Model> getSelected() {
        ArrayList<THCA_Model> selected = new ArrayList<>();
        for (int i = 0; i < taineHomeCareAttendanceList.size(); i++) {
            if (taineHomeCareAttendanceList.get(i).isChecked()) {
                selected.add(taineHomeCareAttendanceList.get(i));
            }
        }
        return selected;
    }
}
