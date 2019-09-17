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

public class MultiSelectionAdapter extends RecyclerView.Adapter<MultiSelectionAdapter.MultiViewHolder> {

    private Context context;
    private ArrayList<THCA_Model> taineHomeCareAttendanceList;

    public MultiSelectionAdapter(Context context) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.trained_home_care_help_in_item, viewGroup, false);
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
        private CheckBox cb;

        MultiViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_trained_help_in = itemView.findViewById(R.id.tv_trained_help_in);
            cb = itemView.findViewById(R.id.cb);
        }

        void bind(final THCA_Model trainedHomeCareAttendanceList) {
          //  cb.setVisibility(trainedHomeCareAttendanceList.isChecked() ? View.VISIBLE : View.GONE);
            tv_trained_help_in.setText(trainedHomeCareAttendanceList.getHomeCareServiceName());

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
