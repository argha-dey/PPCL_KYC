package com.cyberswift.healingtree.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import com.cyberswift.healingtree.R;
import com.cyberswift.healingtree.model.TimeSlotModel;
import com.cyberswift.healingtree.utils.Prefs;

import java.util.ArrayList;

public class MorningTimeSlotAdapter extends  RecyclerView.Adapter<MorningTimeSlotAdapter.ViewHolder> {

    private ArrayList<TimeSlotModel> timeSlotModels;
    private Context context;
    private Prefs mPrefs;
    private int lastSelectedPosition = -1;

    public MorningTimeSlotAdapter(Context _context, ArrayList<TimeSlotModel> _timeSlotList) {
        this.timeSlotModels = _timeSlotList;
        context = _context;
        mPrefs = new Prefs(context);
    }

    @Override
    public MorningTimeSlotAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.morning_time_slot_item, parent, false);
        MorningTimeSlotAdapter.ViewHolder viewHolder = new MorningTimeSlotAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MorningTimeSlotAdapter.ViewHolder holder, int position) {
         String time = timeSlotModels.get(position).getTime();
         holder.selectionCharges.setText(time);
         holder.selectionCharges.setChecked(lastSelectedPosition == position);
    }

    @Override
    public int getItemCount() {
        return timeSlotModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        /*public TextView chargesDays;*/
        public RadioButton selectionCharges;

        public ViewHolder(View view) {
            super(view);

           /* chargesDays = (TextView) view.findViewById(R.id.charges_hour);*/
            selectionCharges = (RadioButton) view.findViewById(R.id.rb_charges_select);

            selectionCharges.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedPosition = getAdapterPosition();
                    mPrefs.setDoctorBookSelectTime(timeSlotModels.get(lastSelectedPosition).getTime());
                    notifyDataSetChanged();
                }
            });
        }
    }
}


