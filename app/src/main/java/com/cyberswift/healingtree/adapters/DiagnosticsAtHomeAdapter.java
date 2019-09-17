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

public class DiagnosticsAtHomeAdapter extends  RecyclerView.Adapter<DiagnosticsAtHomeAdapter.ViewHolder> {

    private ArrayList<HCAC_Model> chargesList;
    private Context context;

    private int lastSelectedPosition = -1;

    public DiagnosticsAtHomeAdapter (Context _context, ArrayList<HCAC_Model> _chargesList) {
        chargesList = _chargesList;
        context = _context;
    }

    @Override
    public DiagnosticsAtHomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diagnostics_at_home_item, parent, false);

        DiagnosticsAtHomeAdapter.ViewHolder viewHolder =
                new DiagnosticsAtHomeAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DiagnosticsAtHomeAdapter.ViewHolder holder, int position) {
        HCAC_Model offersModel = chargesList.get(position);
        holder.item_name.setText(offersModel.getHomeCareAttandanceDuration());
        holder.item_id.setText(offersModel.getHomeCareAttandanceChargeId());
        holder.charges_amount.setText("₹"+offersModel.getHomeCareAttandanceCharges());
        holder.selectionCharges.setChecked(lastSelectedPosition == position);
    }

    @Override
    public int getItemCount() {
        return chargesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView item_name;
        public TextView item_id;
        public TextView charges_amount;
        public RadioButton selectionCharges;

        public ViewHolder(View view) {
            super(view);
            item_name = (TextView) view.findViewById(R.id.item_name);
            item_id = (TextView) view.findViewById(R.id.item_id);
            charges_amount = (TextView) view.findViewById(R.id.charges_amount);

            selectionCharges = (RadioButton) view.findViewById(R.id.charges_select);

            selectionCharges.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();

                    Toast.makeText(DiagnosticsAtHomeAdapter.this.context,
                            "selected offer is " + charges_amount.getText(),
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}


