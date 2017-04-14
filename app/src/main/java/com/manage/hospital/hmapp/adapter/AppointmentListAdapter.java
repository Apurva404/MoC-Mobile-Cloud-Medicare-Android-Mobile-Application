package com.manage.hospital.hmapp.adapter;

import android.content.Context;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.manage.hospital.hmapp.R;

/**
 * Created by sindhya on 4/13/17.
 */
public class AppointmentListAdapter extends BaseAdapter {

    Context mContext;
    private static LayoutInflater inflater=null;

    public AppointmentListAdapter(Context context){
        context=mContext;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View row_view;
        if(view==null)
            row_view=inflater.inflate(R.layout.fragment_appointment,null);
        else
            row_view=view;



        return null;
    }
}
