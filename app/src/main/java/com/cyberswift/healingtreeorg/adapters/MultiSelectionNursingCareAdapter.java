package com.cyberswift.healingtreeorg.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import com.cyberswift.healingtreeorg.R;
import com.cyberswift.healingtreeorg.model.THCA_Model;

import java.util.ArrayList;

public class MultiSelectionNursingCareAdapter extends RecyclerView.Adapter<MultiSelectionNursingCareAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private String servicesType;
    public static ArrayList<THCA_Model> taineHomeCareAttendanceList;
    private Context ctx;

    public MultiSelectionNursingCareAdapter(Context ctx, ArrayList<THCA_Model> _taineHomeCareAttendanceList,String _servicesType) {
        inflater = LayoutInflater.from(ctx);
        this.taineHomeCareAttendanceList = _taineHomeCareAttendanceList;
        this.servicesType =_servicesType;
        this.ctx = ctx;
    }

    @Override
    public MultiSelectionNursingCareAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.nursing_care_offer_item, parent, false);
        MultiSelectionNursingCareAdapter.MyViewHolder holder = new MultiSelectionNursingCareAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MultiSelectionNursingCareAdapter.MyViewHolder holder, int position) {


        holder.nursing_cb.setChecked(taineHomeCareAttendanceList.get(position).getChecked());
        holder.tv_trained_help_in.setText(taineHomeCareAttendanceList.get(position).getHomeCareServiceName());
        holder.tv_nursing_care_details.setText(taineHomeCareAttendanceList.get(position).getHomeCareServiceDescribtion());
        holder.nursing_cb.setTag(position);
        holder.nursing_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer pos = (Integer) holder.nursing_cb.getTag();
             //   Toast.makeText(ctx, medicalEquipment.get(pos).getHomeCareServiceName() + " clicked!", Toast.LENGTH_SHORT).show();

                if (taineHomeCareAttendanceList.get(pos).getChecked()) {
                    taineHomeCareAttendanceList.get(pos).setChecked(false);
                } else {
                    taineHomeCareAttendanceList.get(pos).setChecked(true);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return taineHomeCareAttendanceList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_trained_help_in;
        private CheckBox nursing_cb;
        private TextView   tv_nursing_care_details;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_trained_help_in = itemView.findViewById(R.id.tv_trained_help_in);
            nursing_cb = itemView.findViewById(R.id.nursing_cb);
            tv_nursing_care_details = itemView.findViewById(R.id.tv_nursing_care_details);
        }

    }
}