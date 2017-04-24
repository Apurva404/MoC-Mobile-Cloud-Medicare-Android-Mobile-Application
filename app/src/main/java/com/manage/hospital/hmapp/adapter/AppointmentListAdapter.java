package com.manage.hospital.hmapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.manage.hospital.hmapp.data.AppointmentStructure;
import com.manage.hospital.hmapp.R;

import java.util.List;

/**
 * Created by sindhya on 4/13/17.
 */


public class AppointmentListAdapter extends RecyclerView.Adapter<AppointmentListAdapter.AppointmentViewHolder> {

    private Context context;
    private List<AppointmentStructure> appointmentList;

    public AppointmentListAdapter(Context mContext, List<AppointmentStructure> appointment_list) {
        this.context = mContext;
        this.appointmentList = appointment_list;
    }

    @Override
    public AppointmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_list_item, parent, false);

        return new AppointmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AppointmentViewHolder holder, int position) {

        AppointmentStructure appObj = appointmentList.get(position);
        holder.patientName.setText(appObj.getPatient_name());
        holder.status.setText(appObj.getAppointment_status());
        holder.appDate.setText(appObj.getAppointment_date_time());

    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public class AppointmentViewHolder extends RecyclerView.ViewHolder {

        TextView patientName, status, appDate;

        public AppointmentViewHolder(View itemView) {
            super(itemView);

            patientName = (TextView) itemView.findViewById(R.id.card_patient_name);
            status = (TextView) itemView.findViewById(R.id.card_status);
            appDate = (TextView) itemView.findViewById(R.id.card_date);

        }
    }
}
