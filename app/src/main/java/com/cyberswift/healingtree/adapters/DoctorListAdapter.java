package com.cyberswift.healingtree.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cyberswift.healingtree.R;
import com.cyberswift.healingtree.patientModuleActivity.DoctorTimeSlot;
import com.cyberswift.healingtree.model.DoctorListModel;
import com.cyberswift.healingtree.utils.Constants;
import com.cyberswift.healingtree.utils.Utils;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.ArrayList;


public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<DoctorListModel> arrList;
    private ArrayList<DoctorListModel> arrListCopy = new ArrayList<>();


    public DoctorListAdapter(Context context, ArrayList<DoctorListModel> wishCardList) {
        this.mContext = context;
        this.arrList = wishCardList;
        arrListCopy.addAll(arrList);

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor_list, parent, false);
        return new MyViewHolder(itemView);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        DoctorListModel doctorListModel = arrList.get(position);

        if (doctorListModel.getDocName() != null && !doctorListModel.getDocName().equals("")) {
            holder.tv_dr_name.setText(doctorListModel.getDocName());
            holder.tv_dr_name.setVisibility(View.VISIBLE);
        } else
            holder.tv_dr_name.setVisibility(View.GONE);

        if (doctorListModel.getQualification() != null && !doctorListModel.getQualification().equals("")) {
            holder.tv_dr_qualification.setText(doctorListModel.getQualification());
            holder.tv_dr_qualification.setVisibility(View.VISIBLE);
        } else
            holder.tv_dr_qualification.setVisibility(View.GONE);

        if (doctorListModel.getDeptName() != null && !doctorListModel.getDeptName().equals("")) {
            holder.tv_dr_department.setText(doctorListModel.getDeptName());
            holder.tv_dr_department.setVisibility(View.VISIBLE);
        } else
            holder.tv_dr_department.setVisibility(View.GONE);

        if (doctorListModel.getExperience() != null && !doctorListModel.getExperience().equals("")) {
            holder.tv_dr_experience.setText(doctorListModel.getExperience());
            holder.tv_dr_experience.setVisibility(View.VISIBLE);
            holder.tv_dr_experience_title.setVisibility(View.VISIBLE);
        } else {
            holder.tv_dr_experience.setVisibility(View.GONE);
            holder.tv_dr_experience_title.setVisibility(View.GONE);
        }

        if (doctorListModel.getSpecialization() != null && !doctorListModel.getSpecialization().equals("")) {
            holder.tv_dr_specialization.setText(doctorListModel.getSpecialization());
            holder.tv_dr_specialization.setVisibility(View.VISIBLE);
        } else
            holder.tv_dr_specialization.setVisibility(View.GONE);

        if (doctorListModel.getDay() != null && !doctorListModel.getDay().equals("")) {
            holder.tv_day.setText(doctorListModel.getDay());
            holder.tv_day.setVisibility(View.VISIBLE);
        } else
            holder.tv_day.setVisibility(View.GONE);

        if (doctorListModel.getDate() != null && !doctorListModel.getDate().equals("")) {
            holder.tv_date.setText(Utils.changeDateNTimeFormat(doctorListModel.getDate(), Constants.DATE_TIME_FORMAT_1, Constants.DATE_TIME_FORMAT_8));
            holder.tv_date.setVisibility(View.VISIBLE);
        } else
            holder.tv_date.setVisibility(View.GONE);

      /*  if (doctorListModel.getTime() != null && !doctorListModel.getTime().equals("")) {
            holder.tv_dr_schedule.setText(doctorListModel.getTime());
            holder.tv_dr_schedule.setVisibility(View.VISIBLE);
        } else
            holder.tv_dr_schedule.setVisibility(View.GONE);*/

        if (Utils.isSelectedDateBeforeCurrentDate(doctorListModel.getDate())) {
            holder.btn_book_via_app.setEnabled(false);
            holder.btn_book_via_app.setTextColor(mContext.getResources().getColor(R.color.colorGrey));
        } else
            holder.btn_book_via_app.setEnabled(true);


        performClickEvents(holder);
        /*Glide
                .with(mContext)
                .load(wishCardModel.getBitmapImage())
                //.override(200, 130)
                //.centerCrop() // .fitCenter()
                //.placeholder(R.drawable.user)
                //.error(R.drawable.user)
                .into(holder.riv_wishcard_image);*/
    }


    @Override
    public int getItemCount() {
        return arrList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView civ_dr_profile_image;
        private TextView tv_dr_details, tv_dr_name, tv_dr_qualification, tv_dr_department,
                tv_dr_experience, tv_dr_specialization, tv_day, tv_date,
                tv_dr_schedule, tv_dr_experience_title;
        private Button btn_book_via_call, btn_book_via_app;
        private LinearLayout ll_doctor_details_view;

        MyViewHolder(View itemView) {
            super(itemView);
            civ_dr_profile_image = itemView.findViewById(R.id.civ_dr_profile_image);
            tv_dr_details = itemView.findViewById(R.id.tv_dr_details);
            tv_dr_name = itemView.findViewById(R.id.tv_dr_name);
            tv_dr_qualification = itemView.findViewById(R.id.tv_dr_qualification);
            tv_dr_department = itemView.findViewById(R.id.tv_dr_department);
            tv_dr_experience = itemView.findViewById(R.id.tv_dr_experience);
            tv_dr_specialization = itemView.findViewById(R.id.tv_dr_specialization);
            tv_day = itemView.findViewById(R.id.tv_day);
            tv_date = itemView.findViewById(R.id.tv_date);
            btn_book_via_call = itemView.findViewById(R.id.btn_book_via_call);
            btn_book_via_app = itemView.findViewById(R.id.btn_book_via_app);
            tv_dr_schedule = itemView.findViewById(R.id.tv_dr_schedule);

            tv_dr_experience_title = itemView.findViewById(R.id.tv_dr_experience_title);

        }
    }


    private void performClickEvents(MyViewHolder holder) {
        final int position = holder.getAdapterPosition();

        holder.btn_book_via_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.checkAndRequestAllPermissions(mContext)) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:02692228777"));
                    mContext.startActivity(callIntent);
                }
            }
        });

        holder.btn_book_via_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHome = new Intent(mContext, DoctorTimeSlot.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("DOCTOR_DETAILS",arrList.get(position));
                intentHome.putExtras(bundle);
                mContext.startActivity(intentHome);
            }
        });




    }


    public void filter(CharSequence sequence) {
        arrList.clear();
        arrList.addAll(arrListCopy);
        ArrayList<DoctorListModel> tempArrList = new ArrayList<>();
        if (!TextUtils.isEmpty(sequence)) {
            for (DoctorListModel model : arrList) {
                if (model.getDocName().toLowerCase().contains(sequence.toString().toLowerCase()))
                    tempArrList.add(model);
            }
        } else
            tempArrList.addAll(arrListCopy);
        arrList.clear();
        arrList.addAll(tempArrList);
        notifyDataSetChanged();
        tempArrList.clear();
    }


    public void updateArrListCopy(ArrayList<DoctorListModel> arrList) {
        arrListCopy.clear();
        arrListCopy.addAll(arrList);
    }


    public void updateList(ArrayList<DoctorListModel> list){
        arrList = list;
        notifyDataSetChanged();
    }


}
