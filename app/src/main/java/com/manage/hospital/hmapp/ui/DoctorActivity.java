package com.manage.hospital.hmapp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.manage.hospital.hmapp.R;

/**
 * Created by suma khursheed on 4/25/2017.
 */

public class DoctorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc);

        if(savedInstanceState==null){
            Fragment fragment=new DoctorFragment();
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_doclist,fragment).commit();
        }


    }


}
