package com.manage.hospital.hmapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.manage.hospital.hmapp.Extras.Interface.DocDashboardFragmentToActivity;
import com.manage.hospital.hmapp.Extras.Interface.PatientDashboardFragmentToActivity;
import com.manage.hospital.hmapp.R;
import com.manage.hospital.hmapp.adapter.FitbitListAdapter;
import com.manage.hospital.hmapp.utility.ConfigConstant;
import com.manage.hospital.hmapp.utility.FitbitReferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static com.manage.hospital.hmapp.ui.PatientMainActivity.contactNo;

/**
 * Created by sindhya on 4/29/17.
 */
public class PatientDashboardFragment extends Fragment implements View.OnClickListener {

    private PatientDashboardFragmentToActivity mListener;
    SharedPreferences sharedPreferences;
    FitbitListAdapter fitbitAdapter;
    RecyclerView recyclerViewFitbit;
    SessionManager session;



    Button btnCallEmergency,btnCall911;

    public PatientDashboardFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_patient_dashboard, container, false);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getActivity());
        recyclerViewFitbit=(RecyclerView)view.findViewById(R.id.recycler_view_fitbit);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView.LayoutManager layoutManager = linearLayoutManager;

        recyclerViewFitbit.setLayoutManager(layoutManager);
        recyclerViewFitbit.setItemAnimator(new DefaultItemAnimator());

        btnCallEmergency=(Button)view.findViewById(R.id.btn_call_emergency);
        btnCallEmergency.setOnClickListener(this);
        btnCall911=(Button)view.findViewById(R.id.btn_call_911);
        btnCall911.setOnClickListener(this);

        String token=sharedPreferences.getString(FitbitReferences.FITBIT_TOKEN,"");
        String uid=sharedPreferences.getString(FitbitReferences.FITBIT_UID,"");

        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDateTime[]=sdf.format(calendar.getTime()).split(" ");
        String date=strDateTime[0];


        FetchFitbitListTask fitbitTask=new FetchFitbitListTask();
        fitbitTask.execute(token,uid,date);

        return view;
    }


    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PatientDashboardFragmentToActivity) {
            mListener = (PatientDashboardFragmentToActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        if(id==R.id.btn_call_emergency)
        {
            session = new SessionManager(getActivity());
            HashMap<String,String> emergencyContact= session.getEmergencyContact();
            //String contactNo = emergencyContact.get(SessionManager.EMERGENCY_CONTACT);
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + contactNo));
            startActivity(callIntent);

        }else if(id==R.id.btn_call_911){
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" +"911"));
            startActivity(callIntent);
        }
    }


    public class FetchFitbitListTask extends AsyncTask<String,Void,LinkedHashMap<String,Double>> {

        private final String LOG_TAG=FetchFitbitListTask.class.getSimpleName();

        private LinkedHashMap<String,Double> getFitbitListFromJson(String fitbitJson) throws JSONException {


            LinkedHashMap<String,Double> fitbitMap=new LinkedHashMap<>();
            JSONObject jsonObject=new JSONObject(fitbitJson);

            String sleep_key="totalMinutesAsleep";
            String calories_key="caloriesBurnt";
            String hr_key="restingHeartRate";
            String steps_key="steps";

            Double sleep_data=jsonObject.getDouble(sleep_key);
            String calories_data=jsonObject.getString(calories_key);
            Double hr_data=jsonObject.getDouble(hr_key);
            Double steps_data=jsonObject.getDouble(steps_key);

            if(sleep_data!=0d) {
                fitbitMap.put(sleep_key, sleep_data);
            }else{
                fitbitMap.put(sleep_key, 0d);
            }
            if(calories_data!=null) {
                fitbitMap.put(calories_key, Double.parseDouble(calories_data));
            }else{
                fitbitMap.put(calories_key,0d);
            }
            if(hr_data!=0d) {
                fitbitMap.put(hr_key, hr_data);
            }else{
                fitbitMap.put(hr_key,0d);
            }
            if(steps_data!=0d) {
                fitbitMap.put(steps_key, steps_data);
            }else{
                fitbitMap.put(steps_key,0d);
            }

            return fitbitMap;

        }


        @Override
        protected LinkedHashMap<String,Double> doInBackground(String... params) {


            HttpURLConnection urlConnection=null;
            BufferedReader reader=null;

            String token=params[0];
            String uid=params[1];
            String date=params[2];

            String fitbitListJson=null;

            try{
                String baseUrl= ConfigConstant.FITBIT_BASE_URL;
                final String PATH_PARAM = ConfigConstant.FITBIT_SUMMARY_ENDPOINT;
                final String QUERY_PARAM= "date";

                Uri fitbitUri=Uri.parse(baseUrl).buildUpon().appendEncodedPath(PATH_PARAM).appendEncodedPath(uid).appendQueryParameter(QUERY_PARAM,date).build();

                URL url=new URL(fitbitUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Authorization",token);
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

                fitbitListJson=buffer.toString();


                Log.v(LOG_TAG,"FitbitListStr: "+fitbitListJson);

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
                return getFitbitListFromJson(fitbitListJson);
            }catch (JSONException e){
                Log.e(LOG_TAG,e.getMessage(),e);
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(LinkedHashMap<String,Double> result){
            if(result!=null){

                if(fitbitAdapter==null) {
                    fitbitAdapter = new FitbitListAdapter(getContext(), result);
                    recyclerViewFitbit.setAdapter(fitbitAdapter);
                }else{
                    fitbitAdapter.refreshList(result);
                }
            }
        }
    }

}
