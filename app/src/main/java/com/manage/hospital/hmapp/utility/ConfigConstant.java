package com.manage.hospital.hmapp.utility;

/**
 * Created by sindhya on 4/13/17.
 */
public class ConfigConstant {

    public static final String BASE_URL="http://ec2-34-201-144-36.compute-1.amazonaws.com:8080/MoC_Medicare_Backend";
    public static final String FITBIT_BASE_URL="http://10.250.40.201:8060";//192.168.29.237
    public static final String FITBIT_SUMMARY_ENDPOINT="health/activity/summary";
    public static final String DOC_APPOINTMENT_LIST_ENDPOINT="doctor/appointments";
    public static final String DOC_PATIENT_LIST_ENDPOINT="doctor/patients";
    public static final String APPOINTMENT_STATUS_UPDATE="doctor/appointmentStatus/update";

    public static final String PATIENT_DOC_LIST_ENDPOINT="patient/doctors";

    public static final String FITBIT_AUTH_URL="https://www.fitbit.com/oauth2/authorize?response_type=token&client_id=228BFB&redirect_uri=hospapp%3A%2F%2Fcallbackresponse&scope=activity%20heartrate%20location%20nutrition%20profile%20settings%20sleep%20social%20weight&expires_in=604800";

    public static final String PACKAGE_CUSTOM_TAB = "com.android.chrome";

    public static final String insertDoctor = "/doctor/addInformation";
    public static final String insertPatient = "/patient/addInformation";
    public static final String insertDoctorCredential = "/doctor/credentials/add";
    public static final String insertPatientCredential = "/patient/credentials/add";
    public static final String authenticateDoctor = "/doctor/credentials/verify";
    public static final String authenticatePatient = "/patient/credentials/verify";


}
