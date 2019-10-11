package com.cyberswift.healingtree.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.cyberswift.healingtree.R;
import com.cyberswift.healingtree.model.OrderMedicineHistoryListModel;
import com.cyberswift.healingtree.utils.Constants;
import com.cyberswift.healingtree.utils.Prefs;
import com.cyberswift.healingtree.utils.Utils;

import java.util.ArrayList;

public class OrderMedicineRecordRecyclerViewAdapter extends RecyclerView.Adapter<OrderMedicineRecordRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<OrderMedicineHistoryListModel>  medicineRecordListDataDos;
    private Prefs mPrefs;

    public OrderMedicineRecordRecyclerViewAdapter(Context context, ArrayList<OrderMedicineHistoryListModel> _medicineRecordListDataDos) {
        this.medicineRecordListDataDos = _medicineRecordListDataDos;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_medicine_list_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if (medicineRecordListDataDos != null && medicineRecordListDataDos.size() > 0) {
            viewHolder.setDataToViews(medicineRecordListDataDos, position);
        }

    }

    @Override
    public int getItemCount() {
        if (medicineRecordListDataDos != null && !medicineRecordListDataDos.isEmpty()) {
            return medicineRecordListDataDos.size();
        } else {
            return 0;

        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mDateTextView;
        private TextView mPatientName;
        private TextView mDocumentname;
        private TextView mTreatementType;
        private TextView mRemarks_value;
        private TextView mTime;
        private ImageView img_file_preview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initViews();

        }

        private void initViews() {
            mPrefs = new Prefs(context);
            mDateTextView = (TextView) itemView.findViewById(R.id.date_textview);
            mPatientName = (TextView) itemView.findViewById(R.id.patient_name);
            mDocumentname = (TextView) itemView.findViewById(R.id.doc_name);
            mRemarks_value = (TextView) itemView.findViewById(R.id.remarks_value);
            mTime = (TextView) itemView.findViewById(R.id.time_value);
            img_file_preview = itemView.findViewById(R.id.img_file_preview);


        }

        public void setDataToViews(final ArrayList<OrderMedicineHistoryListModel> _medicineRecordListDataDos, int position) {


            String date = Utils.changeDateNTimeFormat(_medicineRecordListDataDos.get(position).getPP_CRT_TS(), Constants.DATE_TIME_FORMAT_11,Constants.DATE_TIME_FORMAT_2);
            mDateTextView.setText(date);
            mPatientName.setText(mPrefs.getUserFirstname().toUpperCase());
            mDocumentname.setText(_medicineRecordListDataDos.get(position).getPP_UPLOADED_DOC());
            String time = Utils.changeDateNTimeFormat(_medicineRecordListDataDos.get(position).getPP_CRT_TS(), Constants.DATE_TIME_FORMAT_11,Constants.DATE_TIME_FORMAT_12);
            mTime.setText(context.getResources().getString(R.string.time_label)+time);
            mRemarks_value.setText(context.getResources().getString(R.string.remarks_label)+_medicineRecordListDataDos.get(position).getPP_REMARKS());
            img_file_preview.setTag(position);

            img_file_preview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int _position = Integer.valueOf((Integer) img_file_preview.getTag());
                    String fileUrl = _medicineRecordListDataDos.get(_position).getPP_IMAGE_URL();
                    Utils.filePreviewDialog(context,fileUrl);
                }
            });
        }
    }
}
