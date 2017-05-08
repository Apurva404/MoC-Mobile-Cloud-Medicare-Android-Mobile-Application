package com.manage.hospital.hmapp.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by suma khursheed on 4/25/2017.
 */

public class DoctorStructure {

    public String doctor_fname;
    public String doctor_lname;
    public String doctor_spec;
    public String email;
    public String contact_num;


    public String getDoctor_fname() {
        return doctor_fname;
    }

    public String getDoctor_lname() { return doctor_lname; }

    public String getEmail() {
        return email;
    }

    public String getContact_num() {
        return contact_num;
    }

    public String getSpectialization() {
        return doctor_spec;
    }

    public DoctorStructure(JSONObject jsonObject){

        try {
            doctor_fname=jsonObject.getString("FirstName");
            doctor_lname=jsonObject.getString("LastName");
            doctor_spec=jsonObject.getString("Spec");
            email=jsonObject.getString("Email_Id");
            contact_num=jsonObject.getString("ContactNo");


        }catch (JSONException e){
            e.printStackTrace();
        }




    }
}
