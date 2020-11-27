package com.petfolio.infinitus.petlover;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.sessionmanager.SessionManager;
import com.petfolio.infinitus.utils.ConnectionDetector;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PetAppointment_Doctor_Date_Time_Activity extends AppCompatActivity  {

    private Button proced_appoinment;
    private CheckBox chat, video;

    private ImageView backarrow;
    private ListView radioList;
    private List<String> radioName = new ArrayList<>();


    private TextView noRecordFound;
    private String TAG = "PatientAppointment_Doctor_Date_Time_Activity";

    CalendarView calendar;

    private ProgressDialog progressDialog;
    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;


    RadioButton radioButton1,radioButton2;

    RecyclerView rv_doctoravailabeslottime;
    //DoctorAvailabiltyTimeAdapter doctorAvailabiltyTimeAdapter;
    private SharedPreferences preferences;

    RelativeLayout sub_layer1;
    String selectedTimeSlot = "";

    private String _id = "";
    private String Doctor_name ="";
    private String Doctor_email_id ="";
    private String Doctor_ava_Date = "";
    private String Comm_type_chat = "";
    private String Comm_type_video = "";

    SessionManager session;
    String isCheckedChat = "No", isCheckedVideo = "No";

    String doctorChatAvailable, doctorVideoAvailable;


/*    PatientDoctorAvailableTimeResponse doctorDateAvailabilityResponse;
    private List<PatientDoctorAvailableTimeResponse.DataBean> doctorDateAvailabilityResponseList = null;
    private List<PatientDoctorAvailableTimeResponse.DataBean.TimesBean> timesBeanList = null;*/

    String patientname = "",patientemailid = "";

    String Languages = "",Specilization = "",Service = "",Id = "",Pic = "",Name = "",DOB = "",Qualifications = "",HighestQualifications = "",Experience = "",Information = "",
            Availabletype = "",Specialmention = "",Charge = "",Chargeper15mins = "",Gender ="";
    String DocEmail = "";

    private Boolean isAppointment = true;
    View view;
    TextView tvlblavailabletime,tvlbldoctoravailable;

    //List<TimeSlotDivider> timeSlotDividerList= new ArrayList<>();
    List<String> stringLinkedList= new LinkedList<>();

    LinearLayout rootContainer;

    int timeslotcount;
    private RadioButton lastCheckedRB = null;


    boolean isDoctorChatavl = false,isDoctorVideoavl = false;



    boolean isvalid = false;
    String doctortitle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petappointment_doctor_date_time);
        Log.w(TAG,"onCreateView");



        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getProfileDetails();
        patientname = user.get(SessionManager.KEY_NAME);
        patientemailid = user.get(SessionManager.KEY_EMAIL_ID);

        Log.w(TAG,"patientname :"+patientname+" "+"patientemailid :"+patientemailid);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        rv_doctoravailabeslottime = findViewById(R.id.rv_doctoravailabeslottime);

        rootContainer = findViewById(R.id.rootContainer);






        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            Languages = extras.getString("Languages");
            Specilization = extras.getString("Specilization");
            if(Languages != null && !Languages.isEmpty()){
                Languages = Languages.replaceAll("\\[", "").replaceAll("\\]","");
                Log.w(TAG,"Languages-->"+Languages);
            }

            if(Specilization != null && !Specilization.isEmpty()){
                Specilization = Specilization.replaceAll("\\[", "").replaceAll("\\]","");
                Log.w(TAG,"Specilization-->"+Specilization);
            }

            Service = extras.getString("Service");


            Id = extras.getString("Id");
            Log.w(TAG,"DoctorId--->"+Id);
            Pic = extras.getString("Pic");
            Name = extras.getString("Name");
            DOB = extras.getString("DOB");
            Qualifications = extras.getString("Qualifications");
            HighestQualifications = extras.getString("HighestQualifications");
            Experience = extras.getString("Experience");
            Information = extras.getString("Information");
            Availabletype = extras.getString("Availabletype");
            Specialmention = extras.getString("Specialmention");

            Charge = extras.getString("Charge");
            Chargeper15mins = extras.getString("Chargeper15mins");
            Gender = extras.getString("Gender");
            Log.w(TAG, "Specialmention :" + Specialmention + " " + "Charge :" + " " + Charge + "Gender:" + Gender);

            DocEmail = extras.getString("DocEmail");
            doctortitle = extras.getString("doctortitle");

            Log.w(TAG,"DocEmail :"+DocEmail);




        }


        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c);

        if (new ConnectionDetector(PetAppointment_Doctor_Date_Time_Activity.this).isNetworkAvailable(PetAppointment_Doctor_Date_Time_Activity.this)) {

            //doctorDateAvailabilityResponseCall(formattedDate);
        }


        calendar = findViewById(R.id.calender);
        calendar.setMinDate(System.currentTimeMillis() - 1000);

        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);

        proced_appoinment = findViewById(R.id.proced_appoinment);


        chat = findViewById(R.id.chat);
        video = findViewById(R.id.video);
        view = findViewById(R.id.view);
        tvlblavailabletime = findViewById(R.id.tvlblavailabletime);
        tvlbldoctoravailable = findViewById(R.id.tvlbldoctoravailable);



        backarrow = findViewById(R.id.backarrow);


        sub_layer1 = findViewById(R.id.sub_layer1);
        sub_layer1.setVisibility(View.GONE);





        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                String strdayOfMonth = "";
                String strMonth = "";
                int month1 =(month + 1);
                if(dayOfMonth == 9 || dayOfMonth <9){
                    strdayOfMonth = "0"+dayOfMonth;
                    Log.w(TAG,"Selected dayOfMonth-->"+strdayOfMonth);
                }else{
                    strdayOfMonth = String.valueOf(dayOfMonth);
                }

                if(month1 == 9 || month1 <9){
                    strMonth = "0"+month1;
                    Log.w(TAG,"Selected month1-->"+strMonth);
                }else{
                    strMonth = String.valueOf(month1);
                }
                //String Date = dayOfMonth + "-" + (month + 1) + "-" + year;

                String Date = strdayOfMonth + "-" + strMonth + "-" + year;
                Log.w(TAG,"Selected Date-->"+Date);

               /* if (new ConnectionDetector(PatientAppointment_Doctor_Date_Time_Activity.this).isNetworkAvailable(PatientAppointment_Doctor_Date_Time_Activity.this)) {

                    doctorDateAvailabilityResponseCall(Date);
                }
*/




            }
        });





        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });



        proced_appoinment.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                Log.w(TAG, "proced_appoinment----->" + "isCheckedChat :" + isCheckedChat + " " + "isCheckedVideo :" + isCheckedVideo);


                Log.w(TAG,"proced_appoinment----->"+"selectedTimeSlot :"+selectedTimeSlot);



                if (selectedTimeSlot.isEmpty()) {
                    showErrorLoading("Please select available time slot ");

                } else if (isCheckedChat.equalsIgnoreCase("No") && isCheckedVideo.equalsIgnoreCase("No")) {
                    Log.w(TAG, "isCheckedChat if :" + isCheckedChat);
                    showErrorLoading("Please select appointment type ");
                } else if (isCheckedChat.equalsIgnoreCase("True") && isCheckedVideo.equalsIgnoreCase("True")) {
                    Log.w(TAG, "isCheckedChat if" + isCheckedChat);
                    showErrorLoading("Please select chat or video ");
                }else{
                    /*if (new ConnectionDetector(PatientAppointment_Doctor_Date_Time_Activity.this).isNetworkAvailable(PatientAppointment_Doctor_Date_Time_Activity.this)) {
                        appointmentCheckResponseCall();
                        //appointmentCheckRequest();
                    }*/
                }


            }



        });



        chat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (chat.isChecked()) {
                    if(Comm_type_chat != null && !Comm_type_chat.isEmpty()){
                        if(Comm_type_chat.equalsIgnoreCase("No")){
                            showErrorLoading("Doctor available only for video");
                        }
                    }

                   // video.setChecked(false);
                    isCheckedChat = "True";
                   // isCheckedVideo = "No";
                    Log.w(TAG,"isCheckedChat"+isCheckedChat);
                } else if (!chat.isChecked()) {
                   // video.setChecked(true);
                    isCheckedChat = "No";
                   // isCheckedVideo = "True";
                    Log.w(TAG,"isCheckedChat"+isCheckedChat);


                }

            }
        });

        video.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (video.isChecked()) {
                    if(Comm_type_video != null &&  !Comm_type_video.isEmpty()){
                        if(Comm_type_video.equalsIgnoreCase("No")){
                            showErrorLoading("Doctor available only for chat");
                        }
                    }

                  //  chat.setChecked(false);
                    isCheckedVideo = "True";
                   // isCheckedChat = "No";
                    Log.w(TAG,"isCheckedVideo"+isCheckedVideo);


                } else if (!video.isChecked()) {
                   // chat.setChecked(true);
                    isCheckedVideo = "No";
                    //isCheckedChat = "True";
                    Log.w(TAG,"isCheckedVideo"+isCheckedVideo);



                }
            }
        });
    }



    private boolean validConversationType(String doctorChatAvailable, String doctorVideoAvailable) {
        Log.w(TAG, "validConversationType doctorChatAvailable-->" + doctorChatAvailable + "doctorVideoAvailable : " + doctorVideoAvailable);

        if(doctorChatAvailable.equalsIgnoreCase("True") && doctorVideoAvailable.equalsIgnoreCase("True")){

        }else {
            if (doctorChatAvailable.equalsIgnoreCase("True") && isCheckedChat.equalsIgnoreCase("No")) {
                showErrorLoading("Doctor available only for chat");
                isvalid = false;
                return false;
            } else if (doctorVideoAvailable.equalsIgnoreCase("True") && isCheckedVideo.equalsIgnoreCase("No")) {
                showErrorLoading("Doctor available only for video");
                isvalid = false;
                return false;

            }
        }

        isvalid = true;
        return true;
    }





    /*private void appointmentCheckResponseCall() {
        progressDialog = new ProgressDialog(PatientAppointment_Doctor_Date_Time_Activity.this);
        progressDialog.setMessage("Uploading Data, please wait..");
        progressDialog.show();
        APIInterface ApiService = APIClient.getClient().create(APIInterface.class);
        Call<AppointmentCheckResponse> call = ApiService.appointmentCheckResponseCall(RestUtils.getContentType(),appointmentCheckRequest());
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<AppointmentCheckResponse>() {
            @Override
            public void onResponse(@NonNull Call<AppointmentCheckResponse> call, @NonNull Response<AppointmentCheckResponse> response) {
                progressDialog.dismiss();
                Log.w(TAG,"appointmentCheckResponseCall"+ "--->" + new Gson().toJson(response.body()));


                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        Intent intent = new Intent(PatientAppointment_Doctor_Date_Time_Activity.this, AilmentActivity.class);
                        intent.putExtra("id", _id);
                        intent.putExtra("doctortitle", doctortitle);
                        intent.putExtra("doctor_name", Doctor_name);
                        intent.putExtra("doctor_email_id", Doctor_email_id);
                        intent.putExtra("doctor_ava_Date", Doctor_ava_Date);
                        intent.putExtra("isCheckedChat", isCheckedChat);
                        intent.putExtra("isCheckedVideo", isCheckedVideo);
                        intent.putExtra("selectedtimeslot", selectedTimeSlot);

                        intent.putExtra("DocID", Id);
                        intent.putExtra("DocPic", Pic);


                        intent.putExtra("Languages", Languages);
                        intent.putExtra("Specilization", Specilization);
                        intent.putExtra("Service", Service);
                        intent.putExtra("Id", Id);
                        intent.putExtra("Pic", Pic);
                        intent.putExtra("Name", Name);
                        intent.putExtra("DOB", DOB);
                        intent.putExtra("Qualifications", Qualifications);
                        intent.putExtra("HighestQualifications", HighestQualifications);
                        intent.putExtra("Experience", Experience);
                        intent.putExtra("Information", Information);
                        intent.putExtra("Availabletype", Availabletype);
                        intent.putExtra("Specialmention", Specialmention);
                        intent.putExtra("Charge", Charge);
                        intent.putExtra("Chargeper15mins", Chargeper15mins);
                        intent.putExtra("Gender", Gender);
                        intent.putExtra("DocEmail", DocEmail);

                        startActivity(intent);
                    }else{
                        showErrorLoading(response.body().getMessage());
                    }

                }


            }

            @Override
            public void onFailure(@NonNull Call<AppointmentCheckResponse> call, @NonNull Throwable t) {
                progressDialog.dismiss();

                Log.w(TAG,"AppointmentCheckResponseflr"+"--->" + t.getMessage());
            }
        });

    }
    private AppointmentCheckRequest appointmentCheckRequest() {

        *//**
         * Doctor_ID : 5ecb8f24ddb1815b3f9be8ad
         * Booking_Date : 08-06-2020
         * Booking_Time : 08:45 PM - 09:00 PM
         *//*

        AppointmentCheckRequest appointmentCheckRequest = new AppointmentCheckRequest();
        appointmentCheckRequest.setDoctor_ID(Id);
        appointmentCheckRequest.setBooking_Date(Doctor_ava_Date);
        appointmentCheckRequest.setBooking_Time(selectedTimeSlot);
        Log.w(TAG,"appointmentCheckRequest"+ "--->" + new Gson().toJson(appointmentCheckRequest));
        return appointmentCheckRequest;
    }*/


    /*private void doctorDateAvailabilityResponseCall(String Date) {
        progressDialog = new ProgressDialog(PatientAppointment_Doctor_Date_Time_Activity.this);
        progressDialog.setMessage("Uploading Data, please wait..");
        progressDialog.show();
        APIInterface ApiService = APIClient.getClient().create(APIInterface.class);
        Call<PatientDoctorAvailableTimeResponse> call = ApiService.patientDoctorAvailableTimeResponseCall(RestUtils.getContentType(),patientDoctorAvailableTimeRequest(Date));

        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<PatientDoctorAvailableTimeResponse>() {
            @Override
            public void onResponse(@NonNull Call<PatientDoctorAvailableTimeResponse> call, @NonNull Response<PatientDoctorAvailableTimeResponse> response) {
                progressDialog.dismiss();
                Log.w(TAG,"doctorDateAvailabilityResponseCall"+ "--->" + new Gson().toJson(response.body()));


                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        doctorDateAvailabilityResponse = response.body();
                        doctorDateAvailabilityResponseList = response.body().getData();
                        timesBeanList = response.body().getData().get(0).getTimes();
                        Log.w(TAG,"Size"+doctorDateAvailabilityResponseList.size());
                        if(!response.body().getData().isEmpty()){


                            Doctor_name = response.body().getData().get(0).getDoctor_name();
                            Doctor_email_id = response.body().getData().get(0).getDoctor_email_id();
                            Doctor_ava_Date = response.body().getData().get(0).getDoctor_ava_Date();
                            Comm_type_chat = response.body().getData().get(0).getComm_type_chat();
                            Comm_type_video = response.body().getData().get(0).getComm_type_video();
                            Log.w(TAG,"doctorDateAvailabilityResponseCall Comm_type_chat : "+Comm_type_chat+" Comm_type_video : "+Comm_type_video);
                            sub_layer1.setVisibility(View.VISIBLE);
                            proced_appoinment.setVisibility(View.VISIBLE);
                            List<DoctorDateAvailabilityResponse.DataBean.TimeBean>timeBeanList = new ArrayList<>();

                            if(doctorDateAvailabilityResponseList.size()>0) {


                                setViewAvlDays();

                            }

                            chat.setChecked(false);
                            video.setChecked(false);
                            chat.setVisibility(View.GONE);
                            video.setVisibility(View.GONE);
                            view.setVisibility(View.GONE);
                            tvlblavailabletime.setVisibility(View.VISIBLE);
                            tvlbldoctoravailable.setVisibility(View.VISIBLE);
                            Comm_type_chat = response.body().getData().get(0).getComm_type_chat();
                            Comm_type_video = response.body().getData().get(0).getComm_type_video();
                            Log.w(TAG,"doctorDateAvailabilityResponseCall11 Comm_type_chat : "+Comm_type_chat+" Comm_type_video : "+Comm_type_video);

                              String  doctorChatAvailable = response.body().getData().get(0).getComm_type_chat();
                              String doctorVideoAvailable = response.body().getData().get(0).getComm_type_video();



                            if (doctorChatAvailable.equalsIgnoreCase("Yes")) {
                                chat.setVisibility(View.VISIBLE);
                                chat.setChecked(true);
                                chat.setClickable(false);

                            }
                            if (doctorVideoAvailable.equalsIgnoreCase("Yes")) {
                                video.setVisibility(View.VISIBLE);
                                video.setChecked(true);
                                video.setClickable(false);
                            }
                            if(doctorChatAvailable.equalsIgnoreCase("Yes") && doctorVideoAvailable.equalsIgnoreCase("Yes")){
                                chat.setChecked(false);
                                video.setChecked(false);
                                chat.setClickable(true);
                                video.setClickable(true);
                                view.setVisibility(View.VISIBLE);


                            }



                          *//*  for (int i = 0; i < doctorDateAvailabilityResponseList.size(); i++) {
                                Log.w(TAG,"size------->"+doctorDateAvailabilityResponseList.size());
                             //  String doctorChatAvailable =doctorDateAvailabilityResponseList.get(i).getComm_type_chat();
                              // String  doctorVideoAvailable = doctorDateAvailabilityResponseList.get(i).getComm_type_video();
                                Log.w(TAG,"doctorChatAvailable : "+doctorChatAvailable+" doctorVideoAvailable : "+doctorVideoAvailable);

                               *//**//* if(doctorDateAvailabilityResponseList.size() == 1) {
                                    if (doctorChatAvailable.equalsIgnoreCase("True")) {
                                        chat.setVisibility(View.VISIBLE);
                                        chat.setChecked(true);

                                    }
                                    if (doctorVideoAvailable.equalsIgnoreCase("True")) {
                                        video.setVisibility(View.VISIBLE);
                                        video.setChecked(true);
                                    }
                                    if(doctorChatAvailable.equalsIgnoreCase("True") && doctorVideoAvailable.equalsIgnoreCase("True")){
                                        chat.setChecked(false);
                                        video.setChecked(false);
                                        view.setVisibility(View.VISIBLE);


                                    }
                                }
                                else{
                                    if (doctorChatAvailable.equalsIgnoreCase("True")) {
                                        chat.setVisibility(View.VISIBLE);
                                    }
                                    if (doctorVideoAvailable.equalsIgnoreCase("True")) {
                                        video.setVisibility(View.VISIBLE);
                                    }
                                    if(doctorChatAvailable.equalsIgnoreCase("True") && doctorVideoAvailable.equalsIgnoreCase("True")){
                                        chat.setChecked(false);
                                        video.setChecked(false);
                                        view.setVisibility(View.VISIBLE);


                                    }

                                }*//**//*





                            }*//*













                        }


                    }
                    else{
                        sub_layer1.setVisibility(View.GONE);
                        proced_appoinment.setVisibility(View.GONE);
                        tvlblavailabletime.setVisibility(View.GONE);
                        tvlbldoctoravailable.setVisibility(View.GONE);
                        showErrorLoading(response.body().getMessage());
                        rv_doctoravailabeslottime.setVisibility(View.GONE);
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<PatientDoctorAvailableTimeResponse> call, @NonNull Throwable t) {
                progressDialog.dismiss();

                Log.w(TAG,"doctorDateAvailabilityResponseCallFlr"+"--->" + t.getMessage());
            }
        });

    }
    private PatientDoctorAvailableTimeRequest patientDoctorAvailableTimeRequest(String Date) {

        *//*
         * Doctor_email_id : mohammedimthi23951@gmail.com
         * Doctor_ava_Date : 16-05-2020
         *//*
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
        String currentDateandTime24hrs = simpleDateFormat.format(new Date());
        String currenttime = currentDateandTime24hrs.substring(currentDateandTime24hrs.indexOf(' ') + 1);
        String currentdate =  currentDateandTime24hrs.substring(0, currentDateandTime24hrs.indexOf(' '));

        PatientDoctorAvailableTimeRequest patientDoctorAvailableTimeRequest = new PatientDoctorAvailableTimeRequest();
        patientDoctorAvailableTimeRequest.setDoctor_email_id(DocEmail);
        patientDoctorAvailableTimeRequest.setDate(Date);
        patientDoctorAvailableTimeRequest.setCur_time(currenttime);
        patientDoctorAvailableTimeRequest.setCur_date(currentdate);
        Log.w(TAG,"patientDoctorAvailableTimeRequest"+ "--->" + new Gson().toJson(patientDoctorAvailableTimeRequest));
        return patientDoctorAvailableTimeRequest;
    }
    private void setViewAvlDays() {
        rv_doctoravailabeslottime.setVisibility(View.VISIBLE);
        rv_doctoravailabeslottime.setLayoutManager(new GridLayoutManager(this, 4));
        //rv_doctor_mycalendar_avldays.setLayoutManager(new LinearLayoutManager(this));
        rv_doctoravailabeslottime.setItemAnimator(new DefaultItemAnimator());
        PatientMyCalendarAvailableAdapter patientMyCalendarAvailableAdapter = new PatientMyCalendarAvailableAdapter(getApplicationContext(), timesBeanList, rv_doctoravailabeslottime, this);
        rv_doctoravailabeslottime.setAdapter(patientMyCalendarAvailableAdapter);

        patientMyCalendarAvailableAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (preferences.getInt(Constants.INBOX_TOTAL, 0) > timesBeanList.size()) {
                    Log.e("haint", "Load More");
                }


            }


        });







    }*/




    public void showErrorLoading(String errormesage){
        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(errormesage);
        alertDialogBuilder.setPositiveButton("ok",
                (arg0, arg1) -> hideLoading());



        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public void hideLoading(){
        try {
            alertDialog.dismiss();
        }catch (Exception ignored){

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), BookAppointmentActivity.class);
        startActivity(intent);
        finish();
    }


    /*@Override
    public void onItemSelectedTime(String selectedTime) {
        Log.w(TAG,"onItemSelectedTime : "+selectedTime);
        selectedTimeSlot = selectedTime;

    }*/
}
