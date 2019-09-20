package com.cyberswift.healingtree.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.cyberswift.healingtree.R;
import com.cyberswift.healingtree.model.HCAC_Model;


import java.util.ArrayList;

public class HomeCareAttendanceChargesAdapter extends  RecyclerView.Adapter<HomeCareAttendanceChargesAdapter.ViewHolder> {

public static ArrayList<HCAC_Model> chargesList;
private Context context;

    public static  int lastSelectedPosition = -1;

public HomeCareAttendanceChargesAdapter(Context _context, ArrayList<HCAC_Model> _chargesList) {
        chargesList = _chargesList;
        context = _context;
        }

@Override
public HomeCareAttendanceChargesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_care_charges_item, parent, false);

    HomeCareAttendanceChargesAdapter.ViewHolder viewHolder =
        new HomeCareAttendanceChargesAdapter.ViewHolder(view);
        return viewHolder;
        }

@Override
public void onBindViewHolder(HomeCareAttendanceChargesAdapter.ViewHolder holder, int position) {
    HCAC_Model offersModel = chargesList.get(position);
        holder.chargesAmount.setText("₹"+offersModel.getHomeCareAttandanceCharges());
        holder.chargesHour.setText(offersModel.getHomeCareAttandanceDuration());
        holder.selectionCharges.setChecked(lastSelectedPosition == position);
        }

@Override
public int getItemCount() {
        return chargesList.size();
        }

public class ViewHolder extends RecyclerView.ViewHolder {

    public TextView chargesAmount;
    public TextView chargesHour;
    public RadioButton selectionCharges;

    public ViewHolder(View view) {
        super(view);
        chargesAmount = (TextView) view.findViewById(R.id.charges_amount);
        chargesHour = (TextView) view.findViewById(R.id.charges_hour);
        selectionCharges = (RadioButton) view.findViewById(R.id.charges_select);

        selectionCharges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastSelectedPosition = getAdapterPosition();
                notifyDataSetChanged();

                Toast.makeText(HomeCareAttendanceChargesAdapter.this.context,
                        "selected offer is " + chargesAmount.getText(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
}

