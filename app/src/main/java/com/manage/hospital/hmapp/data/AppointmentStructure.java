package com.manage.hospital.hmapp.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sindhya on 4/13/17.
 */
public class AppointmentStructure {


    public String patient_name;
    public String appointment_date_time;
    public String appointment_status;

    public AppointmentStructure(JSONObject jsonObject){

        try {
            patient_name = jsonObject.getString("");
            appointment_date_time = jsonObject.getString("");
            appointment_status = jsonObject.getString("");
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public String getPatient_name() {
        return patient_name;
    }


    public String getAppointment_date_time() {
        return appointment_date_time;
    }

    public String getAppointment_status() {
        return appointment_status;
    }

}
