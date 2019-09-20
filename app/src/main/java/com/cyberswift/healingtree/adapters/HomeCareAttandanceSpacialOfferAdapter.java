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
import com.cyberswift.healingtree.model.SO_Model;

import java.util.ArrayList;

public class HomeCareAttandanceSpacialOfferAdapter  extends  RecyclerView.Adapter<HomeCareAttandanceSpacialOfferAdapter.ViewHolder> {

    public static ArrayList<SO_Model> spacialOffersList;
    private Context context;

    public static int lastSelectedPosition = -1;

    public HomeCareAttandanceSpacialOfferAdapter(Context _context, ArrayList<SO_Model> _spacialOffersList) {
        spacialOffersList = _spacialOffersList;
        context = _context;
    }

    @Override
    public HomeCareAttandanceSpacialOfferAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_care_charges_spacial_offer_item, parent, false);

        HomeCareAttandanceSpacialOfferAdapter.ViewHolder viewHolder =
                new HomeCareAttandanceSpacialOfferAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HomeCareAttandanceSpacialOfferAdapter.ViewHolder holder, int position) {
        SO_Model offersModel = spacialOffersList.get(position);
        holder.chargesAmount.setText(offersModel.getHomeCareServiceSpacialCharges()+"%");
        holder.chargesDays.setText(offersModel.getHomeCareServiceSpacialDuration());
        holder.selectionCharges.setChecked(lastSelectedPosition == position);
    }

    @Override
    public int getItemCount() {
        return spacialOffersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView chargesAmount;
        public TextView chargesDays;
        public RadioButton selectionCharges;

        public ViewHolder(View view) {
            super(view);
            chargesAmount = (TextView) view.findViewById(R.id.charges_amount);
            chargesDays = (TextView) view.findViewById(R.id.charges_hour);
            selectionCharges = (RadioButton) view.findViewById(R.id.charges_select);

            selectionCharges.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();

                    Toast.makeText(HomeCareAttandanceSpacialOfferAdapter.this.context,
                            "offer is " + chargesAmount.getText(),
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}


