package com.manage.hospital.hmapp.ui;


import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.manage.hospital.hmapp.R;
import com.manage.hospital.hmapp.adapter.AppointmentListAdapter;
import com.manage.hospital.hmapp.data.AppointmentData;
import com.manage.hospital.hmapp.data.AppointmentStructure;
import com.manage.hospital.hmapp.utility.ConfigConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sindhya on 4/13/17.
 */
public class AppointmentFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerViewAppointment;
    AppointmentListAdapter appointmentListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root_view=inflater.inflate(R.layout.fragment_appointment,container,false);
        swipeRefreshLayout=(SwipeRefreshLayout)root_view.findViewById(R.id.appointment_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerViewAppointment=(RecyclerView) root_view.findViewById(R.id.appointment_list_view);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView.LayoutManager layoutManager=linearLayoutManager;

        recyclerViewAppointment.setLayoutManager(layoutManager);
        recyclerViewAppointment.setItemAnimator(new DefaultItemAnimator());

        getAppointmentList();

        return root_view;

    }

    public void getAppointmentList(){
        FetchAppointmentListTask fetchAppointmentListTask=new FetchAppointmentListTask();
        fetchAppointmentListTask.execute();
    }

    @Override
    public void onRefresh() {

        getAppointmentList();
        swipeRefreshLayout.setRefreshing(false);

    }


    public class FetchAppointmentListTask extends AsyncTask<Void,Void,AppointmentData> {

        private final String LOG_TAG=FetchAppointmentListTask.class.getSimpleName();

        private AppointmentData getAppointmentListFromJson(String appJsonStr) throws JSONException {

            AppointmentData appointmentData=AppointmentData.getInstance();
            appointmentData.clear();
            AppointmentStructure appObj;
            JSONArray jsonArray=new JSONArray(appJsonStr);

            for(int i=0;i<jsonArray.length();i++){

                appObj=new AppointmentStructure(jsonArray.getJSONObject(i));
                appointmentData.add(appObj);

            }

            return appointmentData;

        }


        @Override
        protected AppointmentData doInBackground(Void... voids) {


            HttpURLConnection urlConnection=null;
            BufferedReader reader=null;

            String appListJson=null;

            try{
                String baseUrl= ConfigConstant.BASE_URL;
                final String PATH_PARAM = ConfigConstant.DOC_APPOINTMENT_LIST_ENDPOINT;
                final String DOCTOR_ID="1";


                Uri appointmtUri=Uri.parse(baseUrl).buildUpon().appendEncodedPath(PATH_PARAM).appendEncodedPath(DOCTOR_ID).build();

                URL url=new URL(appointmtUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();


                InputStream inputStream=urlConnection.getInputStream();
                StringBuffer buffer=new StringBuffer();
                if(inputStream==null){
                    return null;
                }

                reader=new BufferedReader(new InputStreamReader(inputStream));

                String line;

                while((line=reader.readLine())!=null){
                    buffer.append(line+"\n");
                }

                if(buffer.length()==0){
                    return null;
                }

                appListJson=buffer.toString();


                Log.v(LOG_TAG,"AppointmentListStr: "+appListJson);

            }catch (IOException e){

                Log.e(LOG_TAG,e.getMessage(),e);
                return null;

            }
            finally {
                if(urlConnection!=null){
                    urlConnection.disconnect();
                }
                if(reader!=null){
                    try{
                        reader.close();
                    }catch (final IOException e){
                        Log.e(LOG_TAG,"Error closing stream",e);
                    }
                }

            }

            try{
                return getAppointmentListFromJson(appListJson);
            }catch (JSONException e){
                Log.e(LOG_TAG,e.getMessage(),e);
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(AppointmentData result){
            if(result!=null){
                if(appointmentListAdapter==null) {
                    appointmentListAdapter = new AppointmentListAdapter(getContext(), result.data);
                    recyclerViewAppointment.setAdapter(appointmentListAdapter);
                }else{
                    appointmentListAdapter.notifyDataSetChanged();
                }
            }
        }
    }

}
