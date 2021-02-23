package com.petfolio.infinitus.serviceprovider;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.petlover.PetLoverDashboardActivity;
import com.petfolio.infinitus.petlover.PetMyappointmentsActivity;
import com.petfolio.infinitus.petlover.PetSPNewAppointmentDetailsActivity;
import com.petfolio.infinitus.petlover.VideoCallPetLoverActivity;
import com.petfolio.infinitus.requestpojo.AppoinmentCancelledRequest;
import com.petfolio.infinitus.requestpojo.AppoinmentCompleteRequest;
import com.petfolio.infinitus.requestpojo.AppointmentDetailsRequest;
import com.petfolio.infinitus.requestpojo.SPNotificationSendRequest;
import com.petfolio.infinitus.responsepojo.AppoinmentCancelledResponse;
import com.petfolio.infinitus.responsepojo.AppoinmentCompleteResponse;
import com.petfolio.infinitus.responsepojo.NotificationSendResponse;
import com.petfolio.infinitus.responsepojo.SPAppointmentDetailsResponse;
import com.petfolio.infinitus.sessionmanager.SessionManager;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SPAppointmentDetailsActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "SPAppointmentDetailsActivity";


    AVLoadingIndicatorView avi_indicator;


    ImageView img_back;


    ImageView img_user;


    TextView txt_usrname;


    TextView txt_serv_name;


    TextView txt_serv_cost;


    Button btn_cancel,btn_complete;


    ImageView img_petimg;


    TextView txt_pet_name;


    TextView txt_pet_type;


    TextView txt_breed;


    TextView txt_gender;


    TextView txt_color;

    TextView txt_weight;

    TextView txt_age;

    TextView txt_vaccinated;

    TextView txt_order_date;

    TextView txt_order_id;

    TextView txt_payment_method;

    TextView txt_order_cost;

    TextView txt_address;

    String appointment_id;

    ImageView img_videocall;

    String appoinment_status;

    String start_appointment_status;

    private Dialog dialog;

    LinearLayout ll_petlastvacinateddate;
    TextView txt_petlastvaccinatedage;
    private String fromactivity;

    private String spid;
    private String appointmentid;
    private String userid;
    private String bookedat;
    private boolean isVaildDate;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_appointment_details);


        avi_indicator=findViewById(R.id.avi_indicator);

        SessionManager  session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getProfileDetails();
        //userid = user.get(SessionManager.KEY_ID);


        img_back=findViewById(R.id.img_back);


        img_user =findViewById(R.id.img_user);


        txt_usrname =findViewById(R.id.txt_usrname);


        txt_serv_name=findViewById(R.id.txt_serv_name);


        txt_serv_cost=findViewById(R.id.txt_serv_cost);


        btn_cancel=findViewById(R.id.btn_cancel);
        btn_complete=findViewById(R.id.btn_complete);


        img_petimg=findViewById(R.id.img_petimg);


        txt_pet_name=findViewById(R.id.txt_pet_name);


        txt_pet_type=findViewById(R.id.txt_pet_type);


        txt_breed=findViewById(R.id.txt_breed);


        txt_gender=findViewById(R.id.txt_gender);


        txt_color=findViewById(R.id.txt_color);


        txt_weight=findViewById(R.id.txt_weight);


        txt_age=findViewById(R.id.txt_age);


        txt_vaccinated =findViewById(R.id.txt_vaccinated);
        ll_petlastvacinateddate = findViewById(R.id.ll_petlastvacinateddate);
        ll_petlastvacinateddate.setVisibility(View.GONE);
        txt_petlastvaccinatedage = findViewById(R.id.txt_petlastvaccinatedage);


        txt_order_date=findViewById(R.id.txt_order_date);


        txt_order_id =findViewById(R.id.txt_order);


        txt_payment_method =findViewById(R.id.txt_payment_method);


        txt_order_cost=findViewById(R.id.txt_order_cost);


        txt_address=findViewById(R.id.txt_address);


        img_videocall=findViewById(R.id.img_videocall);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            appointment_id = extras.getString("appointment_id");
            fromactivity = extras.getString("fromactivity");
            bookedat = extras.getString("bookedat");

            Log.w(TAG, "fromactivity : " + fromactivity);
        }
        if(fromactivity != null && fromactivity.equalsIgnoreCase("SPCompletedAppointmentAdapter") || fromactivity.equalsIgnoreCase("SPMissedAppointmentAdapter")){
            btn_cancel.setVisibility(View.GONE);
            btn_complete.setVisibility(View.GONE);
        }else{
            btn_cancel.setVisibility(View.VISIBLE);
            btn_complete.setVisibility(View.VISIBLE);
        }


        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        if(bookedat != null){
            compareDatesandTime(currentDateandTime,bookedat);
        }
        btn_cancel=findViewById(R.id.btn_cancel);


        if(isVaildDate){
            btn_cancel.setVisibility(View.VISIBLE);
        }else{
            btn_cancel.setVisibility(View.GONE);
        }

        BottomNavigationView bottom_navigation_view = findViewById(R.id.bottom_navigation_view);

        bottom_navigation_view.setOnNavigationItemSelectedListener(this);


        if (new ConnectionDetector(SPAppointmentDetailsActivity.this).isNetworkAvailable(SPAppointmentDetailsActivity.this)) {
            spAppointmentDetailsResponse();
        }


    }


    @SuppressLint({"LongLogTag", "LogNotTimber"})
    private void spAppointmentDetailsResponse() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<SPAppointmentDetailsResponse> call = ApiService.spAppointmentDetailsResponse(RestUtils.getContentType(), appointmentDetailsRequest());
        Log.w(TAG, "url  :%s" + call.request().url().toString());

        call.enqueue(new Callback<SPAppointmentDetailsResponse>() {
            @SuppressLint({"LongLogTag", "LogNotTimber"})
            @Override
            public void onResponse(@NonNull Call<SPAppointmentDetailsResponse> call, @NonNull Response<SPAppointmentDetailsResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG, "SPAppointmentDetailsResponse" + "--->" + new Gson().toJson(response.body()));


                if (response.body() != null) {

                    if (200 == response.body().getCode()) {

                        String vaccinated, addr = null, usrname = null;

                        //  String usr_image = response.body().getData().getDoctor_id().getProfile_img();
                        String usr_image = "";
                        if (response.body().getData() != null) {
                            spid = response.body().getData().getSp_id().get_id();
                            appointmentid = response.body().getData().getAppointment_UID();
                            userid = response.body().getData().getUser_id().get_id();

                            usrname = response.body().getData().getSp_business_info().get(0).getBussiness_name();
                            Log.w(TAG,"usrname : "+usrname);

                            String servname = response.body().getData().getService_name();

                            String servcost = response.body().getData().getService_amount();

                            String pet_name = response.body().getData().getPet_id().getPet_name();

                            String pet_image = response.body().getData().getPet_id().getPet_img();

                            String pet_type = response.body().getData().getPet_id().getPet_type();

                            String breed = response.body().getData().getPet_id().getPet_breed();

                            String gender = response.body().getData().getPet_id().getPet_gender();

                            String colour = response.body().getData().getPet_id().getPet_color();

                            String weight = String.valueOf(response.body().getData().getPet_id().getPet_weight());

                            String age = String.valueOf(response.body().getData().getPet_id().getPet_age());

                            if (response.body().getData().getPet_id().isVaccinated()) {
                                vaccinated = "Yes";
                                ll_petlastvacinateddate.setVisibility(View.VISIBLE);
                                if (response.body().getData().getPet_id().getLast_vaccination_date() != null) {
                                    txt_petlastvaccinatedage.setText(response.body().getData().getPet_id().getLast_vaccination_date());
                                }

                            } else {
                                ll_petlastvacinateddate.setVisibility(View.GONE);
                                vaccinated = "No";
                            }

                            String order_date = response.body().getData().getBooking_date();

                            String orderid = response.body().getData().getAppointment_UID();

                            String payment_method = response.body().getData().getPayment_method();

                            String order_cost = response.body().getData().getService_amount();

                            addr = response.body().getData().getSp_business_info().get(0).getSp_loc();


                            appoinment_status = response.body().getData().getAppoinment_status();

                            start_appointment_status = response.body().getData().getStart_appointment_status();

                            setView(usrname, usr_image, servname, servcost, pet_name, pet_image, pet_type, breed

                                    , gender, colour, weight, age, order_date, orderid, payment_method, order_cost, vaccinated, addr);
                        }
                    }


                }
            }

            @Override
            public void onFailure(@NonNull Call<SPAppointmentDetailsResponse> call, @NonNull Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG, "PetNewAppointmentDetailsResponse" + "--->" + t.getMessage());
            }
        });

    }


    @SuppressLint({"LongLogTag", "LogNotTimber"})
    private AppointmentDetailsRequest appointmentDetailsRequest() {

        AppointmentDetailsRequest appointmentDetailsRequest = new AppointmentDetailsRequest();
        appointmentDetailsRequest.setApppointment_id(appointment_id);
        Log.w(TAG, "appointmentDetailsRequest" + "--->" + new Gson().toJson(appointmentDetailsRequest));
        return appointmentDetailsRequest;
    }

    @SuppressLint("SetTextI18n")
    private void setView(String usrname, String usr_image, String servname, String servcost, String pet_name, String pet_image, String pet_type, String breed, String gender, String colour, String weight, String age, String order_date, String orderid, String payment_method, String order_cost, String vaccinated, String addr) {


        if(usr_image != null && !usr_image.equals("")){

            Glide.with(SPAppointmentDetailsActivity.this)
                    .load(usr_image)
                    .into(img_user);

        }else{
            Glide.with(SPAppointmentDetailsActivity.this)
                    .load(APIClient.PROFILE_IMAGE_URL)
                    .into(img_user);
        }


        if(usrname != null && !usrname.isEmpty()){

            txt_usrname.setText(usrname);
        }

        if(servname != null && !servname.equals("")){

            txt_serv_name.setText(servname);
        }

        if(servcost != null && !servcost.equals("")){

            txt_serv_cost.setText(servcost);
        }


        if(pet_image != null && !pet_image.equals("")){

            Glide.with(SPAppointmentDetailsActivity.this)
                    .load(pet_image)
                    .into(img_petimg);


        }

        if(pet_name != null && !pet_name.equals("")){

            txt_pet_name.setText(pet_name);
        }

        if(pet_type != null && pet_type.equals("")){

            txt_pet_type.setText(pet_type);
        }

        if(breed != null && !breed.equals("")){

            txt_breed.setText(breed);
        }

        if(gender != null && !gender.equals("")){

            txt_gender.setText(gender);
        }

        if(colour != null && !colour.equals("")){

            txt_color.setText(colour);
        }

        if(weight != null && !weight.equals("")){

            txt_weight.setText(weight);
        }

        if(age != null && !age.equals("")){

            txt_age.setText(age);
        }

        txt_vaccinated.setText(vaccinated);

        if(order_date != null && !order_date.equals("")){

            txt_order_date.setText(order_date);
        }

        if(orderid != null && !orderid.equals("")){

            txt_order_id.setText(orderid);
        }

        if(payment_method != null && !payment_method.equals("")) {

            txt_payment_method.setText(payment_method);

        }

        if(order_cost != null && !order_cost.equals("")){
            txt_order_cost.setText("\u20B9 "+order_cost);
            txt_serv_cost.setText("\u20B9 "+order_cost);
        }

        if(addr != null && !addr.equals("")){

            txt_address.setText(addr);
        }

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        img_videocall.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                Log.w(TAG,"Start_appointment_status : "+start_appointment_status);
                if(start_appointment_status != null && start_appointment_status.equalsIgnoreCase("Not Started")){
                    Toasty.warning(SPAppointmentDetailsActivity.this,"Doctor is yet to start the Appointment. Please wait for the doctor to initiate the Appointment", Toast.LENGTH_SHORT, true).show();
                }else {
                    Intent i = new Intent(SPAppointmentDetailsActivity.this, VideoCallPetLoverActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("id", appointment_id);
                    Log.w(TAG, "ID-->" + appointment_id);
                    startActivity(i);
                }


            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showStatusAlert(appointment_id);
            }
        });

        btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showStatusAlertCompleteAppointment(appointment_id);
            }
        });

    }

    private void showStatusAlert(String id) {
        try {
            dialog = new Dialog(SPAppointmentDetailsActivity.this);
            dialog.setContentView(R.layout.alert_approve_reject_layout);
            TextView tvheader = (TextView)dialog.findViewById(R.id.tvInternetNotConnected);
            tvheader.setText(R.string.cancelappointment);
            Button dialogButtonApprove = (Button) dialog.findViewById(R.id.btnApprove);
            dialogButtonApprove.setText("Yes");
            Button dialogButtonRejected = (Button) dialog.findViewById(R.id.btnReject);
            dialogButtonRejected.setText("No");

            dialogButtonApprove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    //if(appointmenttype != null && appointmenttype.equalsIgnoreCase("Doctor")){
                        appoinmentCancelledResponseCall(id);
                   // } else if(appointmenttype != null && appointmenttype.equalsIgnoreCase("SP")){
                        //spappoinmentCancelledResponseCall(id);
                    //}



                }
            });
            dialogButtonRejected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Toasty.info(context, "Rejected Successfully", Toast.LENGTH_SHORT, true).show();
                    dialog.dismiss();




                }
            });
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }


    }

    @SuppressLint("LongLogTag")
    private void appoinmentCancelledResponseCall(String id) {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<AppoinmentCancelledResponse> call = apiInterface.spappoinmentCancelledResponseCall(RestUtils.getContentType(), appoinmentCancelledRequest(id));
        Log.w(TAG,"appoinmentCancelledResponseCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<AppoinmentCancelledResponse>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(@NonNull Call<AppoinmentCancelledResponse> call, @NonNull Response<AppoinmentCancelledResponse> response) {

                Log.w(TAG,"appoinmentCancelledResponseCall"+ "--->" + new Gson().toJson(response.body()));

                avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        spnotificationSendResponseCall();





                    }
                    else{
                        //showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<AppoinmentCancelledResponse> call, @NonNull Throwable t) {

                avi_indicator.smoothToHide();
                Log.w(TAG,"appoinmentCancelledResponseCall flr"+"--->" + t.getMessage());
            }
        });

    }
    @SuppressLint("LongLogTag")
    private AppoinmentCancelledRequest appoinmentCancelledRequest(String id) {

        /*
         * _id : 5fc639ea72fc42044bfa1683
         * missed_at : 23-10-2000 10 : 00 AM
         * doc_feedback : One Emergenecy work i am cancelling this appointment
         * appoinment_status : Missed
         */


        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        AppoinmentCancelledRequest appoinmentCancelledRequest = new AppoinmentCancelledRequest();
        appoinmentCancelledRequest.set_id(id);
        appoinmentCancelledRequest.setMissed_at(currentDateandTime);
        appoinmentCancelledRequest.setDoc_feedback("");
        appoinmentCancelledRequest.setAppoinment_status("Missed");
        appoinmentCancelledRequest.setAppoint_patient_st("Doctor Cancelled appointment");
        Log.w(TAG,"appoinmentCancelledRequest"+ "--->" + new Gson().toJson(appoinmentCancelledRequest));
        return appoinmentCancelledRequest;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                callDirections("1");
                break;
            case R.id.shop:
                callDirections("2");
                break;
            case R.id.services:
                callDirections("3");
                break;
            case R.id.care:
                callDirections("4");
                break;
            case R.id.community:
                callDirections("5");
                break;

            default:
                return  false;
        }
        return true;
    }
    public void callDirections(String tag){
        Intent intent = new Intent(getApplicationContext(), PetLoverDashboardActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);
        finish();
    }
    private void showStatusAlertCompleteAppointment(String id) {

        try {

            dialog = new Dialog(SPAppointmentDetailsActivity.this);
            dialog.setContentView(R.layout.alert_approve_reject_layout);
            TextView tvheader = (TextView)dialog.findViewById(R.id.tvInternetNotConnected);
            tvheader.setText(R.string.completeappointment);
            Button dialogButtonApprove = (Button) dialog.findViewById(R.id.btnApprove);
            dialogButtonApprove.setText("Yes");
            Button dialogButtonRejected = (Button) dialog.findViewById(R.id.btnReject);
            dialogButtonRejected.setText("No");

            dialogButtonApprove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    appoinmentCompleteResponseCall(id);


                }
            });
            dialogButtonRejected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Toasty.info(context, "Rejected Successfully", Toast.LENGTH_SHORT, true).show();
                    dialog.dismiss();




                }
            });
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }




    }
    @SuppressLint({"LongLogTag", "LogNotTimber"})
    private void appoinmentCompleteResponseCall(String id) {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<AppoinmentCompleteResponse> call = apiInterface.spappoinmentCompleteResponseCall(RestUtils.getContentType(), appoinmentCompleteRequest(id));
        Log.w(TAG,"AppoinmentCompleteResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<AppoinmentCompleteResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<AppoinmentCompleteResponse> call, @NonNull Response<AppoinmentCompleteResponse> response) {

                Log.w(TAG,"AppoinmentCompleteResponse"+ "--->" + new Gson().toJson(response.body()));

                avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        startActivity(new Intent(getApplicationContext(), ServiceProviderDashboardActivity.class));
                    }

                }


            }

            @Override
            public void onFailure(@NonNull Call<AppoinmentCompleteResponse> call, @NonNull Throwable t) {

                avi_indicator.smoothToHide();
                Log.w(TAG,"AppoinmentCompleteResponseflr"+"--->" + t.getMessage());
            }
        });

    }
    @SuppressLint("LongLogTag")
    private AppoinmentCompleteRequest appoinmentCompleteRequest(String id) {
        /*
         * _id : 5fc639ea72fc42044bfa1683
         * completed_at : 23-10-2000 10 : 00 AM
         * appoinment_status : Completed
         */

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        AppoinmentCompleteRequest appoinmentCompleteRequest = new AppoinmentCompleteRequest();
        appoinmentCompleteRequest.set_id(id);
        appoinmentCompleteRequest.setCompleted_at(currentDateandTime);
        appoinmentCompleteRequest.setAppoinment_status("Completed");
        Log.w(TAG,"appoinmentCompleteRequest"+ "--->" + new Gson().toJson(appoinmentCompleteRequest));
        return appoinmentCompleteRequest;
    }

    @SuppressLint("LongLogTag")
    private void spnotificationSendResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<NotificationSendResponse> call = ApiService.spnotificationSendResponseCall(RestUtils.getContentType(),spNotificationSendRequest());

        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<NotificationSendResponse>() {
            @Override
            public void onResponse(@NonNull Call<NotificationSendResponse> call, @NonNull Response<NotificationSendResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"notificationSendResponseCall"+ "--->" + new Gson().toJson(response.body()));


                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        startActivity(new Intent(getApplicationContext(), ServiceProviderDashboardActivity.class));

                    }

                }


            }

            @Override
            public void onFailure(@NonNull Call<NotificationSendResponse> call, @NonNull Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG,"NotificationSendResponse flr"+"--->" + t.getMessage());
            }
        });

    }
    @SuppressLint("LongLogTag")
    private SPNotificationSendRequest spNotificationSendRequest() {

        /*
         * status : Payment Failed
         * date : 23-10-2020 11:00 AM
         * appointment_UID : PET-2923029239123
         * user_id : 601b8ac3204c595ee52582f2
         * sp_id : 601ba9c6270cbe79fd900183
         */
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
        String currentDateandTime = simpleDateFormat.format(new Date());



        SPNotificationSendRequest spNotificationSendRequest = new SPNotificationSendRequest();
        spNotificationSendRequest.setStatus("Patient Appointment Cancelled");
        spNotificationSendRequest.setDate(currentDateandTime);
        spNotificationSendRequest.setAppointment_UID(appointmentid);
        spNotificationSendRequest.setUser_id(userid);
        spNotificationSendRequest.setSp_id(spid);


        Log.w(TAG,"spNotificationSendRequest"+ "--->" + new Gson().toJson(spNotificationSendRequest));
        return spNotificationSendRequest;
    }

    @SuppressLint({"LogNotTimber", "LongLogTag"})
    private void compareDatesandTime(String currentDateandTime, String bookingDateandTime) {
        try{

            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");

            String str1 = currentDateandTime;
            Date currentDate = formatter.parse(str1);

            String str2 = bookingDateandTime;
            Date responseDate = formatter.parse(str2);

            Log.w(TAG,"compareDatesandTime--->"+"responseDate :"+responseDate+" "+"currentDate :"+currentDate);

            if (currentDate.compareTo(responseDate)<0 || responseDate.compareTo(currentDate) == 0)
            {
                Log.w(TAG,"date is equal");
                isVaildDate = true;

            }else{
                Log.w(TAG,"date is not equal");
                isVaildDate = false;
            }



        }catch (ParseException e1){
            e1.printStackTrace();
        }
    }


}