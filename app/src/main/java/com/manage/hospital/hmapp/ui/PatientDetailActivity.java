package com.manage.hospital.hmapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.manage.hospital.hmapp.R;
import com.manage.hospital.hmapp.data.PatientData;

/**
 * Created by sindhya on 5/8/17.
 */
public class PatientDetailActivity extends AppCompatActivity {

    int position;
    TextView patientNameTitle;
    TextView patientDetailGender;
    TextView patientDetailDOB;
    TextView patientDetailAge;
    TextView patientDetailAddress;
    TextView patientDetailContact;
    TextView patientDetailEmail;
    TextView patientDetailWeight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_patient_detail);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getResources().getString(R.string.doctor_patient_detail_title));
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        position=getIntent().getExtras().getInt("position");

        patientNameTitle=(TextView)findViewById(R.id.card_detail_patient_title);
        patientDetailGender=(TextView)findViewById(R.id.card_pat_detail_gender);
        patientDetailAge=(TextView)findViewById(R.id.card_pat_detail_age);
        patientDetailDOB=(TextView)findViewById(R.id.card_pat_detail_dob);
        patientDetailAddress=(TextView)findViewById(R.id.card_pat_detail_address);
        patientDetailContact=(TextView)findViewById(R.id.card_pat_detail_contact);
        patientDetailEmail=(TextView)findViewById(R.id.card_pat_detail_email);
        patientDetailWeight=(TextView)findViewById(R.id.card_pat_detail_weight);

        setValues();
    }

    public void setValues(){
        patientNameTitle.setText(PatientData.getInstance().get(position).getPatient_fname()+" "+PatientData.getInstance().get(position).getPatient_lname());

        patientDetailGender.setText(PatientData.getInstance().get(position).getGender());
        patientDetailDOB.setText(PatientData.getInstance().get(position).getDob());
        patientDetailAge.setText(PatientData.getInstance().get(position).getAge());
        patientDetailAddress.setText(PatientData.getInstance().get(position).getAddress());
        patientDetailContact.setText(PatientData.getInstance().get(position).getContact_num());
        patientDetailEmail.setText(PatientData.getInstance().get(position).getEmail());
        patientDetailWeight.setText(PatientData.getInstance().get(position).getWeight());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }
}
