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
import com.cyberswift.healingtreeorg.utils.Constants;

import java.util.ArrayList;

public class MultiSelectionAdapter extends RecyclerView.Adapter<MultiSelectionAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private String servicesType;
    public static ArrayList<THCA_Model> taineHomeCareAttendanceList;
    private Context ctx;

    public MultiSelectionAdapter(Context ctx, ArrayList<THCA_Model> _taineHomeCareAttendanceList,String _servicesType) {
        inflater = LayoutInflater.from(ctx);
        this.taineHomeCareAttendanceList = _taineHomeCareAttendanceList;
        this.servicesType =_servicesType;
        this.ctx = ctx;
    }

    @Override
    public MultiSelectionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.trained_home_care_help_in_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MultiSelectionAdapter.MyViewHolder holder, int position) {
        holder.checkBox.setChecked(taineHomeCareAttendanceList.get(position).getChecked());
        holder.tv_trained_help_in.setText(taineHomeCareAttendanceList.get(position).getHomeCareServiceName());
        holder.checkBox.setTag(position);

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer pos = (Integer) holder.checkBox.getTag();
            //    Toast.makeText(ctx, medicalEquipment.get(pos).getHomeCareServiceName() + " clicked!", Toast.LENGTH_SHORT).show();

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

        protected CheckBox checkBox;
        private TextView tv_trained_help_in;

        public MyViewHolder(View itemView) {
            super(itemView);
            checkBox = (CheckBox) itemView.findViewById(R.id.cb);
            tv_trained_help_in = (TextView) itemView.findViewById(R.id.tv_trained_help_in);
            if(servicesType.equals(Constants.MEDICALE_EQUIPMENT_SERVICE)){
              checkBox.setClickable(false);
            }
        }

    }
}