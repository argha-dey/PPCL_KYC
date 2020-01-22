package com.cyberswift.pankaj.healingtree.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cyberswift.pankaj.healingtree.R;
import com.cyberswift.pankaj.healingtree.Utils;
import com.cyberswift.pankaj.healingtree.models.BookingAppointmentModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class BookingAppointmentAdapter extends RecyclerView.Adapter<BookingAppointmentAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<BookingAppointmentModel> arrList;

    public BookingAppointmentAdapter(Context context, ArrayList<BookingAppointmentModel> wishCardList) {
        this.mContext = context;
        this.arrList = wishCardList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking_appointment, parent, false);
        return new BookingAppointmentAdapter.MyViewHolder(itemView);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        BookingAppointmentModel bookingAppointmentModel = arrList.get(position);

        holder.tv_dr_name.setText(bookingAppointmentModel.getDrName());
        holder.tv_dr_qualification.setText(bookingAppointmentModel.getDrQualification());
        holder.tv_dr_department.setText(bookingAppointmentModel.getDrDepartment());
        holder.tv_dr_experience.setText(bookingAppointmentModel.getDrExperience());
        holder.tv_dr_specialization.setText(bookingAppointmentModel.getDrSpecializedIn());
        holder.tv_date.setText(bookingAppointmentModel.getDate());

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
                tv_dr_experience, tv_dr_specialization, tv_day, tv_date, tv_book_via_call, tv_book_via_app;

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
            tv_book_via_call = itemView.findViewById(R.id.tv_book_via_call);
            tv_book_via_app = itemView.findViewById(R.id.tv_book_via_app);
        }
    }


    private void performClickEvents(MyViewHolder holder) {
        int position = holder.getAdapterPosition();

        holder.tv_book_via_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.checkAndRequestAllPermissions(mContext)) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:9876543210"));
                    mContext.startActivity(callIntent);
                }
            }
        });

        holder.tv_book_via_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Working...", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
