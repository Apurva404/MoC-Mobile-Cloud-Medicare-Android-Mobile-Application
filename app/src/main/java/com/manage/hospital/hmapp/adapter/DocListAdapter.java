package com.manage.hospital.hmapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.manage.hospital.hmapp.R;
import com.manage.hospital.hmapp.data.DoctorStructure;

import java.util.List;

/**
 * Created by suma khursheed on 4/25/2017.
 */

public class DocListAdapter extends RecyclerView.Adapter<DocListAdapter.DoctorViewHolder> {

    private Context context;
    private List<DoctorStructure> doctorList;

    public DocListAdapter(Context mContext, List<DoctorStructure> doctor_list) {
        this.context = mContext;
        this.doctorList = doctor_list;
    }

    @Override
    public DoctorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.doc_list_item, parent, false);

        return new DoctorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DoctorViewHolder holder, int position) {

        DoctorStructure appObj = doctorList.get(position);
        holder.doctorfName.setText(appObj.getDoctor_fname());
        holder.doctorlName.setText(appObj.getDoctor_lname());

    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public class DoctorViewHolder extends RecyclerView.ViewHolder {

        TextView doctorfName, doctorlName;

        public DoctorViewHolder(View itemView) {
            super(itemView);

            doctorfName = (TextView) itemView.findViewById(R.id.card_doctor_fname);
            doctorlName = (TextView) itemView.findViewById(R.id.card_doctor_lname);

        }
    }
}

