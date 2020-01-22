package com.cyberswift.healingtreeorg.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.cyberswift.healingtreeorg.R;
import com.cyberswift.healingtreeorg.patientModuleActivity.DoctorListActivity;
import com.cyberswift.healingtreeorg.model.DoctorDidDName;

import java.util.ArrayList;

public class DoctorDepartmentAdapter extends RecyclerView.Adapter<DoctorDepartmentAdapter.viewHolder> {

    Context context;
    ArrayList<DoctorDidDName> arrayList;
    public DoctorDepartmentAdapter(Context context, ArrayList<DoctorDidDName> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public  viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.icon_list, viewGroup, false);
        return new viewHolder(view);
    }
    @Override
    public  void onBindViewHolder(viewHolder viewHolder,final int position) {

        viewHolder.iconName.setText(arrayList.get(position).getDEPT_NAME());
        String imageName =  arrayList.get(position).getDEPT_NAME().trim().toLowerCase().replaceAll("\\s+", "");
        int resourceId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        viewHolder.icon.setImageResource(resourceId);

        viewHolder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHome = new Intent(context, DoctorListActivity.class);
                 intentHome.putExtra("DEPARTMENT_ID",arrayList.get(position).getDEPT_ID());
                context.startActivity(intentHome);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void updateList(ArrayList<DoctorDidDName> list){
        if(list.size()>0&&list!=null) {
            arrayList = list;
            notifyDataSetChanged();
        }
        else {
            Toast.makeText(context, "Search result Not found !", Toast.LENGTH_SHORT).show();
        }
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView iconName;
       LinearLayout ll_item;

        public viewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            iconName = (TextView) itemView.findViewById(R.id.icon_name);
            ll_item = itemView.findViewById(R.id.ll_item);

        }
    }







}
