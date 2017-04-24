package com.manage.hospital.hmapp.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sindhya on 4/18/17.
 */
public class PatientStructure {

    public String patient_fname;
    public String patient_lname;
    public String dob;
    public String gender;
    public String weight;
    public String age;
    public String email;
    public String contact_num;
    public String address;


    public String getPatient_fname() {
        return patient_fname;
    }

    public String getPatient_lname() {
        return patient_lname;
    }

    public String getDob() {
        return dob;
    }

    public String getGender() {
        return gender;
    }

    public String getWeight() {
        return weight;
    }

    public String getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getContact_num() {
        return contact_num;
    }

    public String getAddress() {
        return address;
    }

    public PatientStructure(JSONObject jsonObject){

        try {
            patient_fname=jsonObject.getString("");
            patient_lname=jsonObject.getString("");
            dob=jsonObject.getString("");
            gender=jsonObject.getString("");
            weight=jsonObject.getString("");
            age=jsonObject.getString("");
            email=jsonObject.getString("");
            contact_num=jsonObject.getString("");
            address=jsonObject.getString("");


        }catch (JSONException e){
            e.printStackTrace();
        }




    }
}
