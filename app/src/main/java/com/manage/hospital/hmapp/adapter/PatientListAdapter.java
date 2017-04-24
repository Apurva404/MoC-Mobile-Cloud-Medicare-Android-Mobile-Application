package com.manage.hospital.hmapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.manage.hospital.hmapp.R;
import com.manage.hospital.hmapp.data.AppointmentStructure;
import com.manage.hospital.hmapp.data.PatientStructure;

import java.util.List;

/**
 * Created by sindhya on 4/18/17.
 */


public class PatientListAdapter extends RecyclerView.Adapter<PatientListAdapter.PatientViewHolder> {

    private Context context;
    private List<PatientStructure> patientList;

    public PatientListAdapter(Context mContext, List<PatientStructure> patient_list) {
        this.context = mContext;
        this.patientList = patient_list;
    }

    @Override
    public PatientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_list_item, parent, false);

        return new PatientViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PatientViewHolder holder, int position) {

        PatientStructure appObj = patientList.get(position);
        holder.patientfName.setText(appObj.getPatient_fname());
        holder.patientlName.setText(appObj.getPatient_lname());

    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    public class PatientViewHolder extends RecyclerView.ViewHolder {

        TextView patientfName, patientlName;

        public PatientViewHolder(View itemView) {
            super(itemView);

            patientfName = (TextView) itemView.findViewById(R.id.card_patient_fname);
            patientlName = (TextView) itemView.findViewById(R.id.card_patient_lname);

        }
    }
}

