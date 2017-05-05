package com.manage.hospital.hmapp.utility;

/**
 * Created by sindhya on 4/13/17.
 */
public class ConfigConstant {

    public static final String BASE_URL="http://ec2-34-207-70-56.compute-1.amazonaws.com:8080/MoC_Medicare_Backend";
    public static final String FITBIT_BASE_URL="http://192.168.29.237:8060";
    public static final String FITBIT_SUMMARY_ENDPOINT="health/activity/summary";
    public static final String DOC_APPOINTMENT_LIST_ENDPOINT="doctor/appointments";
    public static final String DOC_PATIENT_LIST_ENDPOINT="patient";

    public static final String FITBIT_AUTH_URL="https://www.fitbit.com/oauth2/authorize?response_type=token&client_id=228BFB&redirect_uri=hospapp%3A%2F%2Fcallbackresponse&scope=activity%20heartrate%20location%20nutrition%20profile%20settings%20sleep%20social%20weight&expires_in=604800";

    public static final String PACKAGE_CUSTOM_TAB = "com.android.chrome";

}
