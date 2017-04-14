package com.manage.hospital.hmapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.manage.hospital.hmapp.R;

/**
 * Created by sindhya on 4/13/17.
 */
public class AppointmentActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        if(savedInstanceState==null){
            Fragment fragment=new AppointmentFragment();
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_appointments,fragment).commit();
        }
    }
}
