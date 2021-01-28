package com.petfolio.infinitus.petlover;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.adapter.PetMyCalendarAvailableAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.interfaces.OnItemSelectedTime;
import com.petfolio.infinitus.requestpojo.AppointmentCheckRequest;
import com.petfolio.infinitus.requestpojo.PetAppointmentCreateRequest;
import com.petfolio.infinitus.requestpojo.PetDoctorAvailableTimeRequest;
import com.petfolio.infinitus.responsepojo.AppointmentCheckResponse;
import com.petfolio.infinitus.responsepojo.PetAppointmentCreateResponse;
import com.petfolio.infinitus.responsepojo.PetDoctorAvailableTimeResponse;
import com.petfolio.infinitus.sessionmanager.SessionManager;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.vivekkaushik.datepicker.DatePickerTimeline;
import com.vivekkaushik.datepicker.OnDateSelectedListener;
import com.wang.avi.AVLoadingIndicatorView;


import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PetAppointment_Doctor_Date_Time_Activity extends AppCompatActivity implements OnItemSelectedTime {

    private Button btn_bookappointment;
    private CheckBox chat, video;

    private ImageView img_back;
    private ListView radioList;
    private List<String> radioName = new ArrayList<>();


    private TextView noRecordFound;
    private String TAG = "PetAppointment_Doctor_Date_Time_Activity";

   // CalendarView calendar;

    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;


    RadioButton radioButton1,radioButton2;

    RecyclerView rv_doctoravailabeslottime;
    //DoctorAvailabiltyTimeAdapter doctorAvailabiltyTimeAdapter;
    private SharedPreferences preferences;

    RelativeLayout sub_layer1;


    private String _id = "";
    private String Doctor_name ="";
    private String Doctor_email_id ="";
    private String Doctor_ava_Date = "";
    private String selectedTimeSlot = "";
    private String Comm_type_chat = "";
    private String Comm_type_video = "";

    SessionManager session;




    private Boolean isAppointment = true;
    View view;
    TextView tvlblavailabletime,tvlbldoctoravailable;





    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.datePickerTimeline)
    DatePickerTimeline datePickerTimeline ;

    private List<PetDoctorAvailableTimeResponse.DataBean> doctorDateAvailabilityResponseList;
    private List<PetDoctorAvailableTimeResponse.DataBean.TimesBean> timesBeanList;
    private String petid,allergies,probleminfo;
    private String userid;
    private String doctorid;
    private String selectedAppointmentType;
    private double totalamount =1;
    private String Payment_id;
    private String fromactivity;
    private String fromto;
    private int amount;
    private String communicationtype;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petappointment_doctor_date_time);
        Log.w(TAG,"onCreateView");

        ButterKnife.bind(this);

        avi_indicator.setVisibility(View.GONE);

        /*Razorpay init*/
        Checkout.preload(getApplicationContext());



        SessionManager sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getProfileDetails();
        userid = user.get(SessionManager.KEY_ID);


        Log.w(TAG,"userid :"+userid);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        rv_doctoravailabeslottime = findViewById(R.id.rv_doctoravailabeslottime);








        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            doctorid = extras.getString("doctorid");
            fromactivity = extras.getString("fromactivity");
            fromto = extras.getString("fromto");
            Log.w(TAG,"fromactivity : "+fromactivity+ " fromto : "+fromto);


            petid = extras.getString("petid");
            allergies = extras.getString("allergies");
            probleminfo = extras.getString("probleminfo");
            doctorid = extras.getString("doctorid");
            selectedAppointmentType = extras.getString("selectedAppointmentType");

            amount = extras.getInt("amount");
            communicationtype = extras.getString("communicationtype");

            Log.w(TAG,"petid-->"+petid+ "allergies : "+allergies+"  probleminfo : "+probleminfo+" selectedAppointmentType : "+selectedAppointmentType+" communicationtype : "+communicationtype);









        }


        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c);

        if (new ConnectionDetector(PetAppointment_Doctor_Date_Time_Activity.this).isNetworkAvailable(PetAppointment_Doctor_Date_Time_Activity.this)) {
            petDoctorAvailableTimeResponseCall(formattedDate);
        }


      /*  calendar = findViewById(R.id.calender);
        calendar.setMinDate(System.currentTimeMillis() - 1000);*/

        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);

        btn_bookappointment = findViewById(R.id.btn_bookappointment);


        chat = findViewById(R.id.chat);
        video = findViewById(R.id.video);
        view = findViewById(R.id.view);
        tvlblavailabletime = findViewById(R.id.tvlblavailabletime);
        tvlbldoctoravailable = findViewById(R.id.tvlbldoctoravailable);



        img_back = findViewById(R.id.img_back);


        sub_layer1 = findViewById(R.id.sub_layer1);
        sub_layer1.setVisibility(View.GONE);

        btn_bookappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedTimeSlot != null && !selectedTimeSlot.isEmpty()){

                    if (new ConnectionDetector(PetAppointment_Doctor_Date_Time_Activity.this).isNetworkAvailable(PetAppointment_Doctor_Date_Time_Activity.this)) {
                        appointmentCheckResponseCall();
                    }
                }else{
                    showErrorLoading("Please select time slot ");

                }

            }
        });




      /*  calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
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

                if (new ConnectionDetector(PetAppointment_Doctor_Date_Time_Activity.this).isNetworkAvailable(PetAppointment_Doctor_Date_Time_Activity.this)) {

                    petDoctorAvailableTimeResponseCall(formattedDate);
                }



            }
        });*/

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DATE);

        // Set a Start date (Default, 1 Jan 1970)
        datePickerTimeline.setInitialDate(year, month, date);

        datePickerTimeline.setDateTextColor(Color.parseColor("#009675"));
        datePickerTimeline.setDayTextColor(Color.parseColor("#009675"));
        datePickerTimeline.setMonthTextColor(Color.parseColor("#009675"));
      // Set a date Selected Listener
        datePickerTimeline.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int dayOfMonth, int dayOfWeek) {
                // Do Something

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

                String Date = strdayOfMonth + "-" + strMonth + "-" + year;
                Log.w(TAG,"Selected Date-->"+Date);

                if (new ConnectionDetector(PetAppointment_Doctor_Date_Time_Activity.this).isNetworkAvailable(PetAppointment_Doctor_Date_Time_Activity.this)) {
                    petDoctorAvailableTimeResponseCall(Date);
                }


            }

            @Override
            public void onDisabledDateSelected(int year, int month, int day, int dayOfWeek, boolean isDisabled) {
                // Do Something
            }
        });

      /*// Disable date
        Date[] dates = {Calendar.getInstance().getTime()};
        datePickerTimeline.deactivateDates(dates);*/




        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });




    }
    private void petDoctorAvailableTimeResponseCall(String Date) {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<PetDoctorAvailableTimeResponse> call = ApiService.petDoctorAvailableTimeResponseCall(RestUtils.getContentType(),petDoctorAvailableTimeRequest(Date));

        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<PetDoctorAvailableTimeResponse>() {
            @Override
            public void onResponse(@NonNull Call<PetDoctorAvailableTimeResponse> call, @NonNull Response<PetDoctorAvailableTimeResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"PetDoctorAvailableTimeResponse"+ "--->" + new Gson().toJson(response.body()));


                if (response.body() != null) {
                    if(response.body().getCode() == 200){
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
                            btn_bookappointment.setVisibility(View.VISIBLE);
                            List<PetDoctorAvailableTimeResponse.DataBean.TimesBean>timeBeanList = new ArrayList<>();

                            if(doctorDateAvailabilityResponseList.size()>0) {


                                setViewAvlDays();

                            }

                            chat.setChecked(false);
                            video.setChecked(false);
                            chat.setVisibility(View.GONE);
                            video.setVisibility(View.GONE);
                            view.setVisibility(View.GONE);
                            tvlblavailabletime.setVisibility(View.VISIBLE);
                            //tvlbldoctoravailable.setVisibility(View.VISIBLE);
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

                        }


                    }
                    else{
                        sub_layer1.setVisibility(View.GONE);
                        btn_bookappointment.setVisibility(View.GONE);
                        tvlblavailabletime.setVisibility(View.GONE);
                        tvlbldoctoravailable.setVisibility(View.GONE);
                        showErrorLoading(response.body().getMessage());
                        rv_doctoravailabeslottime.setVisibility(View.GONE);
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<PetDoctorAvailableTimeResponse> call, @NonNull Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG,"PetDoctorAvailableTimeResponseflr"+"--->" + t.getMessage());
            }
        });

    }
    private PetDoctorAvailableTimeRequest petDoctorAvailableTimeRequest(String Date) {

        /*
         * Date : 31-11-2020
         * user_id : 1234567890
         * cur_date : 31-11-2020
         * cur_time : 01:00 AM
         */
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
        String currentDateandTime24hrs = simpleDateFormat.format(new Date());
        String currenttime = currentDateandTime24hrs.substring(currentDateandTime24hrs.indexOf(' ') + 1);
        String currentdate =  currentDateandTime24hrs.substring(0, currentDateandTime24hrs.indexOf(' '));

        PetDoctorAvailableTimeRequest petDoctorAvailableTimeRequest = new PetDoctorAvailableTimeRequest();
        petDoctorAvailableTimeRequest.setUser_id(doctorid);
        petDoctorAvailableTimeRequest.setDate(Date);
        petDoctorAvailableTimeRequest.setCur_time(currenttime);
        petDoctorAvailableTimeRequest.setCur_date(currentdate);
        Log.w(TAG,"petDoctorAvailableTimeRequest"+ "--->" + new Gson().toJson(petDoctorAvailableTimeRequest));
        return petDoctorAvailableTimeRequest;
    }
    private void setViewAvlDays() {
        rv_doctoravailabeslottime.setVisibility(View.VISIBLE);
        rv_doctoravailabeslottime.setLayoutManager(new GridLayoutManager(this, 4));
        rv_doctoravailabeslottime.setItemAnimator(new DefaultItemAnimator());
        PetMyCalendarAvailableAdapter petMyCalendarAvailableAdapter = new PetMyCalendarAvailableAdapter(getApplicationContext(), timesBeanList, rv_doctoravailabeslottime, this);
        rv_doctoravailabeslottime.setAdapter(petMyCalendarAvailableAdapter);







    }

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

        if(fromto != null && fromto.equalsIgnoreCase("direct")){
            callDirections("4");
        } else if(fromactivity != null && fromactivity.equalsIgnoreCase("PetCareFragment")){
            Intent intent = new Intent(getApplicationContext(),DoctorClinicDetailsActivity.class);
            intent.putExtra("doctorid",doctorid);
            intent.putExtra("fromactivity",fromactivity);
            startActivity(intent);

        }else{
            Intent intent = new Intent(getApplicationContext(), DoctorClinicDetailsActivity.class);
            intent.putExtra("doctorid",doctorid);
            startActivity(intent);
            finish();

        }


    }
    public void callDirections(String tag){
        Intent intent = new Intent(getApplicationContext(),PetLoverDashboardActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);
        finish();

    }



    @Override
    public void onItemSelectedTime(String selectedTime) {
        Log.w(TAG,"onItemSelectedTime : "+selectedTime);
        selectedTimeSlot = selectedTime;

    }


    private void appointmentCheckResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<AppointmentCheckResponse> call = ApiService.appointmentCheckResponseCall(RestUtils.getContentType(),appointmentCheckRequest());
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<AppointmentCheckResponse>() {
            @Override
            public void onResponse(@NonNull Call<AppointmentCheckResponse> call, @NonNull Response<AppointmentCheckResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"appointmentCheckResponseCall"+ "--->" + new Gson().toJson(response.body()));


                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        Intent intent = new Intent(PetAppointment_Doctor_Date_Time_Activity.this,BookAppointmentActivity.class);
                        intent.putExtra("doctorid",doctorid);
                        intent.putExtra("fromactivity",fromactivity);
                        intent.putExtra("Doctor_ava_Date",Doctor_ava_Date);
                        intent.putExtra("selectedTimeSlot",selectedTimeSlot);
                        intent.putExtra("amount",amount);
                        intent.putExtra("communicationtype",communicationtype);
                        intent.putExtra("fromto",fromto);
                        startActivity(intent);
                        Log.w(TAG,"communicationtype : "+communicationtype);

                     /*  // startPayment();
                        if (new ConnectionDetector(PetAppointment_Doctor_Date_Time_Activity.this).isNetworkAvailable(PetAppointment_Doctor_Date_Time_Activity.this)) {
                            petAppointmentCreateResponseCall();
                        }*/

                    }else{
                        showErrorLoading(response.body().getMessage());
                    }

                }


            }

            @Override
            public void onFailure(@NonNull Call<AppointmentCheckResponse> call, @NonNull Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG,"AppointmentCheckResponseflr"+"--->" + t.getMessage());
            }
        });

    }
    private AppointmentCheckRequest appointmentCheckRequest() {
        /*
         * Booking_Date : 02-12-2020
         * Booking_Time : 09:00 AM
         * doctor_id : 5fc4eb2c913fec4204e4b15d
         */

        AppointmentCheckRequest appointmentCheckRequest = new AppointmentCheckRequest();
        appointmentCheckRequest.setDoctor_id(doctorid);
        appointmentCheckRequest.setBooking_Date(Doctor_ava_Date);
        appointmentCheckRequest.setBooking_Time(selectedTimeSlot);
        Log.w(TAG,"appointmentCheckRequest"+ "--->" + new Gson().toJson(appointmentCheckRequest));
        return appointmentCheckRequest;
    }



}
