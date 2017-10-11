package com.manage.hospital.hmapp.ui;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.manage.hospital.hmapp.R;
import com.manage.hospital.hmapp.data.PatientData;
import com.manage.hospital.hmapp.utility.ConfigConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

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
    Button btnRequestHealthData;
    SessionManager sessionManager;
    String doc_id;
    String pat_id;

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

        sessionManager=new SessionManager(PatientDetailActivity.this);
        HashMap<String, String> user = sessionManager.getUserDetails();
        doc_id = user.get(SessionManager.KEY_ID);

        position=getIntent().getExtras().getInt("position");

        patientNameTitle=(TextView)findViewById(R.id.card_detail_patient_title);
        patientDetailGender=(TextView)findViewById(R.id.card_pat_detail_gender);
        patientDetailAge=(TextView)findViewById(R.id.card_pat_detail_age);
        patientDetailDOB=(TextView)findViewById(R.id.card_pat_detail_dob);
        patientDetailAddress=(TextView)findViewById(R.id.card_pat_detail_address);
        patientDetailContact=(TextView)findViewById(R.id.card_pat_detail_contact);
        patientDetailEmail=(TextView)findViewById(R.id.card_pat_detail_email);
        patientDetailWeight=(TextView)findViewById(R.id.card_pat_detail_weight);
        btnRequestHealthData=(Button)findViewById(R.id.btn_request_health_data);
        btnRequestHealthData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openHealDataRequestDialog();

            }
        });

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

    public void openHealDataRequestDialog(){

        AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(this);
        LayoutInflater layoutInflater=this.getLayoutInflater();
        View dialog_layout=layoutInflater.inflate(R.layout.dialog_doc_health_request,null);
        dialogBuilder.setView(dialog_layout);

        final EditText editTxtHealthReqDesc=(EditText)dialog_layout.findViewById(R.id.edit_dialog_health_req_desc);

        dialogBuilder.setTitle(getResources().getString(R.string.dialog_request_title));

        dialogBuilder.setPositiveButton(getResources().getString(R.string.doc_health_req_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                pat_id=PatientData.getInstance().get(position).getPatient_id();
                String doctor_id=doc_id;
                String health_data_req=editTxtHealthReqDesc.getText().toString();


                AddHealthDataRequestTask healthDataRequestTask=new AddHealthDataRequestTask();
                healthDataRequestTask.execute(doctor_id,pat_id,health_data_req,"Requested");

            }
        });

        dialogBuilder.setNegativeButton(getResources().getString(R.string.doc_health_req_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog=dialogBuilder.create();
        alertDialog.show();

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

    public class AddHealthDataRequestTask extends AsyncTask<String,Void,Boolean> {

        private final String LOG_TAG=AddHealthDataRequestTask.class.getSimpleName();

        @Override
        protected Boolean doInBackground(String... param) {

            HttpURLConnection urlConnection=null;

            StringBuilder sb=new StringBuilder();
            try {
                String base_url = ConfigConstant.BASE_URL;
                final String ADD_REQUEST_PATH_PARAM = ConfigConstant.DOCTOR_ADD_HEALTH_REQUEST;


                Uri addReqUri = Uri.parse(base_url).buildUpon().appendEncodedPath(ADD_REQUEST_PATH_PARAM).build();

                URL url = new URL(addReqUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type","application/json");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.connect();

                try {

                    JSONObject volunteerObj = new JSONObject();
                    volunteerObj.put("doctorId",param[0]);
                    volunteerObj.put("patientId",param[1]);
                    volunteerObj.put("description",param[2]);
                    volunteerObj.put("status",param[3]);


                    OutputStreamWriter os = new OutputStreamWriter(urlConnection.getOutputStream());
                    os.write(volunteerObj.toString());
                    os.close();

                    int HttpResult =urlConnection.getResponseCode();
                    if(HttpResult ==HttpURLConnection.HTTP_OK){
                        return true;
                    }

                }catch (JSONException e){
                    Log.e(LOG_TAG,e.getMessage());
                }

            }catch (IOException e){
                Log.e(LOG_TAG,e.getMessage());
            }finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean res) {
            if(res) {
                new AlertDialog.Builder(PatientDetailActivity.this).setTitle(getResources().getString(R.string.dialog_request_title))
                        .setMessage(getResources().getString(R.string.doc_health_req_success))
                        .setPositiveButton(R.string.dialog_health_msg_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                //closeActivity();
                            }
                        })
                        .show();
            }else{
                new AlertDialog.Builder(PatientDetailActivity.this).setTitle(getResources().getString(R.string.dialog_request_title))
                        .setMessage(getResources().getString(R.string.doc_health_req_failure))
                        .setPositiveButton(R.string.dialog_health_msg_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();
            }
        }

        public void closeActivity(){
            setResult(RESULT_OK);
            finish();
        }
    }

}
