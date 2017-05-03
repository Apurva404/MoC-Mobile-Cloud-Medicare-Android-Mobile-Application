package com.manage.hospital.hmapp.Extras.fitbit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.manage.hospital.hmapp.R;
import com.manage.hospital.hmapp.ui.FitbitActivity;
import com.manage.hospital.hmapp.utility.FitbitReferences;

/**
 * Created by sindhya on 4/30/17.
 */
public class FitbitTokenResponse extends AppCompatActivity {


    String respStrUrl;
    String LOG_TAG=FitbitTokenResponse.class.getSimpleName();

    @Override
    protected void onNewIntent(Intent intent){
        respStrUrl=intent.getDataString();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onNewIntent(getIntent());

        Log.d(LOG_TAG,respStrUrl);
        String token=respStrUrl.substring(respStrUrl.indexOf(getResources().getString(R.string.fitbit_token))+14,respStrUrl.indexOf(getResources().getString(R.string.fitbit_uid)));
        Log.d(LOG_TAG,token);
        String user_id=respStrUrl.substring(respStrUrl.indexOf(getResources().getString(R.string.fitbit_uid))+9,respStrUrl.indexOf(getResources().getString(R.string.fibit_scope)));
        Log.d(LOG_TAG,user_id);
        String token_type=respStrUrl.substring(respStrUrl.indexOf(getResources().getString(R.string.fitbit_token_type))+12,respStrUrl.indexOf(getResources().getString(R.string.fitbit_token_expiry)));
        Log.d(LOG_TAG,token_type);

        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(this);
        sp.edit().putBoolean(FitbitReferences.HAS_ACCESS_TOKEN,true).apply();
        sp.edit().putString(FitbitReferences.FITBIT_TOKEN,token).apply();
        sp.edit().putString(FitbitReferences.FITBIT_UID,user_id).apply();
        sp.edit().putString(FitbitReferences.FITBIT_TOKEN_TYPE,token_type).apply();
        sp.edit().putString(FitbitReferences.FITBIT_FULL_AUTH,token_type+" "+token).apply();

        Intent intent=new Intent(FitbitTokenResponse.this,FitbitActivity.class);
        startActivity(intent);


    }
}
