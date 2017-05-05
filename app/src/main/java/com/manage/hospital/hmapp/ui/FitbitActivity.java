package com.manage.hospital.hmapp.ui;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.manage.hospital.hmapp.R;
import com.manage.hospital.hmapp.adapter.AppointmentListAdapter;
import com.manage.hospital.hmapp.data.AppointmentData;
import com.manage.hospital.hmapp.data.AppointmentStructure;
import com.manage.hospital.hmapp.utility.ConfigConstant;
import com.manage.hospital.hmapp.utility.FitbitReferences;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FitbitActivity extends AppCompatActivity {

    private SharedPreferences fitbitSharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitbit);

        fitbitSharedPref= PreferenceManager.getDefaultSharedPreferences(this);
        boolean tokenAvail=fitbitSharedPref.getBoolean(FitbitReferences.HAS_ACCESS_TOKEN,false);

        if(!tokenAvail){
            Log.d("No token",fitbitSharedPref.getString(FitbitReferences.FITBIT_TOKEN,""));

        }else{
            Log.d("Token","I have token");
            Log.d("Token",fitbitSharedPref.getString(FitbitReferences.FITBIT_TOKEN,""));
            Log.d("UID",fitbitSharedPref.getString(FitbitReferences.FITBIT_UID,""));
            Log.d("Token type",fitbitSharedPref.getString(FitbitReferences.FITBIT_TOKEN_TYPE,""));
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_fitbit,new FitBitDetailsFragment()).addToBackStack(null).commit();

    }
}
