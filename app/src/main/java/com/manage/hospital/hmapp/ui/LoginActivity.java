package com.manage.hospital.hmapp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.manage.hospital.hmapp.R;
import com.manage.hospital.hmapp.data.LoginInfo;
import com.manage.hospital.hmapp.utility.ConfigConstant;
import com.manage.hospital.hmapp.utility.encryptPasscode;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;


public class LoginActivity extends Activity
{

    EditText txtUsername, txtPassword;

    Button btnLogin;
    AlertDialogManager alert = new AlertDialogManager();
    SessionManager session;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        session = new SessionManager(getApplicationContext());

        txtUsername = (EditText) findViewById(R.id.uname_login);
        txtPassword = (EditText) findViewById(R.id.password_login);
        RadioButton rd1 = (RadioButton) findViewById(R.id.doc_rd);
        RadioButton rd2 = (RadioButton) findViewById(R.id.patient_rd);

        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        btnLogin = (Button) findViewById(R.id.login);


        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {


                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();
                Bundle bundle = getIntent().getExtras();
                RadioButton rd1 = (RadioButton) findViewById(R.id.doc_rd);
                RadioButton rd2 = (RadioButton) findViewById(R.id.patient_rd);


                LoginInfo L = new LoginInfo();

                if (username.trim().length() > 0 && password.trim().length() > 0)
                {
                    L.setUsername(username);
                    L.setPassword(password);
                    if (rd1.isChecked())
                        new AsyncTaskLoginDoc().execute(L);
                    else
                        new AsyncTaskLoginPatient().execute(L);
                }

                else
                {

                    alert.showAlertDialog(LoginActivity.this, "Login failed..", "Please enter username and password", false);
                }

            }

        });
    }

    public class AsyncTaskLoginDoc extends AsyncTask<LoginInfo, String, LoginInfo> {

        HttpResponse response;
        SessionManager session;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected LoginInfo doInBackground(LoginInfo... params)
        {

            String password = encryptPasscode.encryptPassword(params[0].Password);

            try
            {
                JSONObject requestBody = new JSONObject();
                requestBody.put("username", params[0].Username);
                requestBody.put("password",password); //ToDo hashcode of password
                String request = requestBody.toString();
                StringEntity request_param = new StringEntity(request);

                String Url= ConfigConstant.BASE_URL+ConfigConstant.authenticateDoctor;
                HttpPost post = new HttpPost(Url);
                post.setHeader("Content-Type","application/json");
                post.setEntity(request_param);
                HttpClient httpClient = new DefaultHttpClient();
                response = httpClient.execute(post);
                System.out.println("Reached after coming back from Backend API");
                if (response.getStatusLine().getStatusCode() != 200)
                {
                    if(response.getStatusLine().getStatusCode() == 401)
                    {
                        System.out.println("Verification failed");
                    }
                    else if(response.getStatusLine().getStatusCode() == 400)
                    {
                        System.out.println("Verification failed");
                    }
                    else
                      throw new RuntimeException("Failed: HTTP error code :" + response.getStatusLine().getStatusCode());
                }
                else
                {
                    HttpEntity e = response.getEntity();
                    String i = EntityUtils.toString(e);
                    JSONObject j = new JSONObject(i);
                    if(!i.equals("Request Failed"))
                    {
                        //int userID = Integer.parseInt(i);
                        int userID = j.getInt("Doctor ID");
                        params[0].setID(userID);
                    }
                }
            }
            catch(Exception x)
            {
                throw new RuntimeException("Error from authenticate doc api",x);
            }
            return params[0];
        }

        @Override
        protected void onPostExecute(LoginInfo L)
        {
            super.onPostExecute(L);
            String userID;
            userID = String.valueOf(L.ID);
            if(L.ID != 0)
            {
                session = new SessionManager(getApplicationContext());
                session.createLoginSession(L.Username, userID, "Doctor");
                Intent i = new Intent(getApplicationContext(), LauncherActivity.class); //ToDo doctor dashboard
                startActivity(i);
                finish();
            }
            else
            {
                alert.showAlertDialog(LoginActivity.this, "Login failed..", "Username/Password is incorrect", false);
            }
        }

    }

    public class AsyncTaskLoginPatient extends AsyncTask<LoginInfo, String, LoginInfo> {

        HttpResponse response;
        SessionManager session;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected LoginInfo doInBackground(LoginInfo... params)
        {

            String password = encryptPasscode.encryptPassword(params[0].Password);

            try
            {
                JSONObject requestBody = new JSONObject();
                requestBody.put("username", params[0].Username);
                requestBody.put("password",password); //ToDo hashcode of password
                String request = requestBody.toString();
                StringEntity request_param = new StringEntity(request);

                String Url= ConfigConstant.BASE_URL+ConfigConstant.authenticatePatient;
                HttpPost post = new HttpPost(Url);
                post.setHeader("Content-Type","application/json");
                post.setEntity(request_param);
                HttpClient httpClient = new DefaultHttpClient();
                response = httpClient.execute(post);
                System.out.println("Reached after coming back from Backend API");
                if (response.getStatusLine().getStatusCode() != 200)
                {
                    if(response.getStatusLine().getStatusCode() == 401)
                    {
                        System.out.println("Verification failed");
                    }
                    else if(response.getStatusLine().getStatusCode() == 400)
                    {
                        System.out.println("Verification failed");
                    }
                    else
                    {
                        throw new RuntimeException("Failed: HTTP error code :" + response.getStatusLine().getStatusCode());
                    }
                }
                else
                {
                    HttpEntity e = response.getEntity();
                    String i = EntityUtils.toString(e);
                    JSONObject j = new JSONObject(i);
                    if(!i.equals("Request Failed"))
                    {
                        //int userID = Integer.parseInt(i);
                        int userID = j.getInt("Patient ID");
                        params[0].setID(userID);
                    }
                }
            }
            catch(Exception x)
            {
                throw new RuntimeException("Error from authenticate patient api",x);
            }
            return params[0];
        }

        @Override
        protected void onPostExecute(LoginInfo L)
        {
            super.onPostExecute(L);
            String userID;
            userID = String.valueOf(L.ID);
            if(L.ID != 0)
            {
                session = new SessionManager(getApplicationContext());
                session.createLoginSession(L.Username, userID,"Patient");
                Intent i = new Intent(getApplicationContext(), LauncherActivity.class); //ToDo Patient dashboard
                startActivity(i);
                finish();
            }
            else
            {
                alert.showAlertDialog(LoginActivity.this, "Login failed..", "Username/Password is incorrect", false);
            }
        }

    }
    public void gotoHome(View V)
    {
        Intent intent = new Intent(LoginActivity.this,LauncherActivity.class);
        startActivity(intent);
    }

    public void finishLoginActivity(View V)
    {
        LoginActivity.this.finish();;
    }

}

