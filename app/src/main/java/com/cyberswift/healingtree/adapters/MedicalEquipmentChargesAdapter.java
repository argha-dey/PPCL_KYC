package com.cyberswift.healingtree.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import com.cyberswift.healingtree.R;
import com.cyberswift.healingtree.interfaces.OnChargesDataChangeListener;
import com.cyberswift.healingtree.model.HCAC_Model;

import java.util.ArrayList;

public class MedicalEquipmentChargesAdapter extends  RecyclerView.Adapter<MedicalEquipmentChargesAdapter.ViewHolder> {
public static ArrayList<HCAC_Model> chargesList;
private Context context;

    public static  int lastSelectedPositionOfACA = -1;

public MedicalEquipmentChargesAdapter(Context _context, ArrayList<HCAC_Model> _chargesList) {
        chargesList = _chargesList;
        context = _context;
        }

@Override
public MedicalEquipmentChargesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_care_charges_item, parent, false);

    MedicalEquipmentChargesAdapter.ViewHolder viewHolder =
        new MedicalEquipmentChargesAdapter.ViewHolder(view);
        return viewHolder;
        }

@Override
public void onBindViewHolder(MedicalEquipmentChargesAdapter.ViewHolder holder, int position) {
    HCAC_Model offersModel = chargesList.get(position);
        holder.chargesAmount.setText(offersModel.getHomeCareAttandanceCharges());
        holder.chargesHour.setText(offersModel.getHomeCareAttandanceDuration());
        holder.selectionCharges.setChecked(lastSelectedPositionOfACA == position);
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
                lastSelectedPositionOfACA = getAdapterPosition();
                ((OnChargesDataChangeListener)context).onChargesDataChanged(chargesList.get(lastSelectedPositionOfACA).getHomeCareAttandanceCharges());
                notifyDataSetChanged();

            }
        });
    }

}


}

