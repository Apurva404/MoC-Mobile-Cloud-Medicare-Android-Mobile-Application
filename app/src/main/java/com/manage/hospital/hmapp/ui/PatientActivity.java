package com.manage.hospital.hmapp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.manage.hospital.hmapp.R;

/**
 * Created by sindhya on 4/18/17.
 */
public class PatientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        if(savedInstanceState==null){
            Fragment fragment=new PatientFragment();
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_appointments,fragment).commit();
        }
    }


}
