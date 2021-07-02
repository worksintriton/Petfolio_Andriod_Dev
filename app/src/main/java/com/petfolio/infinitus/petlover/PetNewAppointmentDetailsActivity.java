package com.petfolio.infinitus.petlover;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.petfolio.infinitus.requestpojo.AppoinmentCancelledRequest;
import com.petfolio.infinitus.requestpojo.PetNewAppointmentDetailsRequest;
import com.petfolio.infinitus.responsepojo.AppoinmentCancelledResponse;
import com.petfolio.infinitus.responsepojo.PetNewAppointmentDetailsResponse;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetNewAppointmentDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "PetNewAppointmentDetailsActivity";


    AVLoadingIndicatorView avi_indicator;

    /* Petlover Bottom Navigation */

    /* Petlover Bottom Navigation */

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rl_home)
    RelativeLayout rl_home;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rl_care)
    RelativeLayout rl_care;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.title_care)
    TextView title_care;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_care)
    ImageView img_care;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rl_service)
    RelativeLayout rl_service;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.title_serv)
    TextView title_serv;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_serv)
    ImageView img_serv;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rl_shop)
    RelativeLayout rl_shop;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.title_shop)
    TextView title_shop;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_shop)
    ImageView img_shop;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rl_comn)
    RelativeLayout rl_comn;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.title_community)
    TextView title_community;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_community)
    ImageView img_community;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rl_homes)
    RelativeLayout rl_homes;





    ImageView img_user;


    TextView txt_usrname;


    TextView txt_serv_name;


    TextView txt_serv_cost;


    Button btn_cancel;


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
    private String bookedat;
    private String startappointmentstatus;
    private boolean isVaildDate;

    TextView txt_appointment_date;
    private String appointmentfor;
    private List<PetNewAppointmentDetailsResponse.DataBean.PetIdBean.PetImgBean> pet_image;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.include_petlover_header)
    View include_petlover_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_new_appointment_details);

        ButterKnife.bind(this);


        ImageView img_back = include_petlover_header.findViewById(R.id.img_back);
        ImageView img_sos = include_petlover_header.findViewById(R.id.img_sos);
        ImageView img_notification = include_petlover_header.findViewById(R.id.img_notification);
        ImageView img_cart = include_petlover_header.findViewById(R.id.img_cart);
        ImageView img_profile = include_petlover_header.findViewById(R.id.img_profile);
        TextView toolbar_title = include_petlover_header.findViewById(R.id.toolbar_title);
        toolbar_title.setText(getResources().getString(R.string.appointment));


        img_back.setOnClickListener(v -> onBackPressed());


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            appointment_id = extras.getString("appointment_id");
            bookedat = extras.getString("bookedat");
            startappointmentstatus = extras.getString("startappointmentstatus");
            appointmentfor = extras.getString("appointmentfor");
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
        if(startappointmentstatus != null && !startappointmentstatus.equalsIgnoreCase("Not Started")) {
            btn_cancel.setVisibility(View.GONE);
        }





        avi_indicator=findViewById(R.id.avi_indicator);




        img_user =findViewById(R.id.img_user);


        txt_usrname =findViewById(R.id.txt_usrname);
        txt_appointment_date =findViewById(R.id.txt_appointment_date);


        txt_serv_name=findViewById(R.id.txt_serv_name);
        txt_serv_name.setVisibility(View.GONE);


        txt_serv_cost=findViewById(R.id.txt_serv_cost);




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



      //  View include_petlover_footer  = findViewById(R.id.include_petlover_footer);

//        BottomNavigationView  bottom_navigation_view = include_petlover_footer.findViewById(R.id.bottom_navigation_view);
//        bottom_navigation_view.setItemIconTintList(null);
//        bottom_navigation_view.setOnNavigationItemSelectedListener(this);
//        bottom_navigation_view.getMenu().findItem(R.id.shop).setChecked(true);

        /*shop*/
        title_care.setTextColor(getResources().getColor(R.color.darker_grey_new,getTheme()));
        img_care.setImageResource(R.drawable.grey_care);
        title_serv.setTextColor(getResources().getColor(R.color.darker_grey_new,getTheme()));
        img_serv.setImageResource(R.drawable.grey_servc);
        title_community.setTextColor(getResources().getColor(R.color.darker_grey_new,getTheme()));
        img_community.setImageResource(R.drawable.grey_community);
        title_shop.setTextColor(getResources().getColor(R.color.new_gree_color,getTheme()));
        img_shop.setImageResource(R.drawable.green_shop);

        rl_home.setOnClickListener(this);

        rl_care.setOnClickListener(this);

        rl_service.setOnClickListener(this);

        rl_shop.setOnClickListener(this);

        rl_comn.setOnClickListener(this);


        rl_homes.setOnClickListener(this);



        if (new ConnectionDetector(PetNewAppointmentDetailsActivity.this).isNetworkAvailable(PetNewAppointmentDetailsActivity.this)) {
            petNewAppointmentResponseCall();
        }


    }


    @SuppressLint({"LongLogTag", "LogNotTimber"})
    private void petNewAppointmentResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<PetNewAppointmentDetailsResponse> call = ApiService.petNewAppointDetailResponseCall(RestUtils.getContentType(), petNewAppointmentDetailsRequest());
        Log.w(TAG, "url  :%s" + call.request().url().toString());

        call.enqueue(new Callback<PetNewAppointmentDetailsResponse>() {
            @SuppressLint({"LongLogTag", "LogNotTimber", "SetTextI18n"})
            @Override
            public void onResponse(@NonNull Call<PetNewAppointmentDetailsResponse> call, @NonNull Response<PetNewAppointmentDetailsResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG, "PetNewAppointmentDetailsResponse" + "--->" + new Gson().toJson(response.body()));


                if (response.body() != null) {

                    if (200 == response.body().getCode()) {

                        String vaccinated, addr = null, usrname = null;


                        if (response.body().getData() != null) {

                            String usr_image = response.body().getData().getDoctor_id().getProfile_img();

                            String servname = response.body().getData().getService_name();

                            String servcost = response.body().getData().getService_amount();

                            String pet_name = response.body().getData().getPet_id().getPet_name();

                             pet_image = response.body().getData().getPet_id().getPet_img();

                            String pet_type = response.body().getData().getPet_id().getPet_type();

                            String breed = response.body().getData().getPet_id().getPet_breed();

                            String gender = response.body().getData().getPet_id().getPet_gender();

                            String colour = response.body().getData().getPet_id().getPet_color();

                            String weight = String.valueOf(response.body().getData().getPet_id().getPet_weight());

                            String age = String.valueOf(response.body().getData().getPet_id().getPet_age());

                            if(response.body().getData().getBooking_date_time() != null){
                                txt_appointment_date.setText(response.body().getData().getBooking_date_time());
                            }

                            if (response.body().getData().getPet_id().isVaccinated()) {
                                vaccinated = "Yes";
                                ll_petlastvacinateddate.setVisibility(View.VISIBLE);
                                if (response.body().getData().getPet_id().getLast_vaccination_date() != null && !response.body().getData().getPet_id().getLast_vaccination_date().isEmpty()) {
                                    txt_petlastvaccinatedage.setText(": "+response.body().getData().getPet_id().getLast_vaccination_date());
                                }

                            } else {
                                ll_petlastvacinateddate.setVisibility(View.GONE);
                                vaccinated = "No";
                            }

                            String order_date = response.body().getData().getBooking_date();

                            String orderid = response.body().getData().getAppointment_UID();

                            String payment_method = response.body().getData().getPayment_method();

                            String order_cost = response.body().getData().getAmount();

                            List<PetNewAppointmentDetailsResponse.DataBean.DocBusinessInfoBean> Address = response.body().getData().getDoc_business_info();

                            for (int i = 0; i < Address.size(); i++) {

                                addr = Address.get(i).getClinic_loc();

                                usrname = Address.get(i).getDr_name();
                            }

                            appoinment_status = response.body().getData().getAppoinment_status();

                            start_appointment_status = response.body().getData().getStart_appointment_status();

                            setView(usrname, usr_image, servname, servcost, pet_name, pet_image, pet_type, breed

                                    , gender, colour, weight, age, order_date, orderid, payment_method, order_cost, vaccinated, addr);
                        }
                    }


                }
            }

            @Override
            public void onFailure(@NonNull Call<PetNewAppointmentDetailsResponse> call, @NonNull Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG, "PetNewAppointmentDetailsResponse" + "--->" + t.getMessage());
            }
        });

    }


    @SuppressLint({"LongLogTag", "LogNotTimber"})
    private PetNewAppointmentDetailsRequest petNewAppointmentDetailsRequest() {

        PetNewAppointmentDetailsRequest petNewAppointmentDetailsRequest = new PetNewAppointmentDetailsRequest();
        petNewAppointmentDetailsRequest.setApppointment_id(appointment_id);
        Log.w(TAG, "petNewAppointmentDetailsRequest" + "--->" + new Gson().toJson(petNewAppointmentDetailsRequest));
        return petNewAppointmentDetailsRequest;
    }

    @SuppressLint({"SetTextI18n", "LongLogTag", "LogNotTimber"})
    private void setView(String usrname, String usr_image, String servname, String servcost, String pet_name, List<PetNewAppointmentDetailsResponse.DataBean.PetIdBean.PetImgBean> pet_image, String pet_type, String breed, String gender, String colour, String weight, String age, String order_date, String orderid, String payment_method, String order_cost, String vaccinated, String addr) {


        if(usr_image != null && !usr_image.isEmpty()){
            Glide.with(PetNewAppointmentDetailsActivity.this)
                    .load(usr_image)
                    .into(img_user);

        }else{
            Glide.with(PetNewAppointmentDetailsActivity.this)
                    .load(APIClient.PROFILE_IMAGE_URL)
                    .into(img_user);
        }


        if(usrname != null && !usrname.isEmpty()){

            txt_usrname.setText(usrname);
        }

        if(servname != null && !servname.isEmpty()){

            txt_serv_name.setText(servname);
        }

        if(pet_image != null && !pet_image.isEmpty()){
            Glide.with(PetNewAppointmentDetailsActivity.this)
                    .load(pet_image)
                    .into(img_petimg);
        }else{
            Glide.with(PetNewAppointmentDetailsActivity.this)
                    .load(APIClient.PROFILE_IMAGE_URL)
                    .into(img_petimg);
        }

        if(pet_name != null && !pet_name.isEmpty()){
            txt_pet_name.setText(": "+pet_name);
        }

        if(pet_type != null && !pet_type.isEmpty()){

            txt_pet_type.setText(": "+pet_type);
        }

        if(breed != null && !breed.isEmpty()){

            txt_breed.setText(": "+breed);
        }

        if(gender != null && !gender.isEmpty()){

            txt_gender.setText(": "+gender);
        }

        if(colour != null && !colour.isEmpty()){

            txt_color.setText(": "+colour);
        }

        if(weight != null && !weight.isEmpty()){

            txt_weight.setText(": "+weight);
        }

        if(age != null && !age.isEmpty()){

            txt_age.setText(": "+age);
        }
        if(vaccinated != null && !vaccinated.isEmpty()) {
            txt_vaccinated.setText(": "+vaccinated);
        }

        if(order_date != null && !order_date.isEmpty()){

            txt_order_date.setText(": "+order_date);
        }

        if(orderid != null && !orderid.isEmpty()){

            txt_order_id.setText(": "+orderid);
        }

        if(payment_method != null && !payment_method.isEmpty()) {

            txt_payment_method.setText(": "+payment_method);

        }

        if(order_cost != null && !order_cost.isEmpty()){

            txt_order_cost.setText(": "+"\u20B9 "+order_cost);
            txt_serv_cost.setText("\u20B9 "+order_cost);
        }

        if(addr != null && !addr.isEmpty()){

            txt_address.setText(addr);
        }


        img_videocall.setOnClickListener(v -> {
            Log.w(TAG,"Start_appointment_status : "+start_appointment_status);
            if(start_appointment_status != null && start_appointment_status.equalsIgnoreCase("Not Started")){
                Toasty.warning(PetNewAppointmentDetailsActivity.this,"Doctor is yet to start the Appointment. Please wait for the doctor to initiate the Appointment", Toast.LENGTH_SHORT, true).show();
            }else {
                Intent i = new Intent(PetNewAppointmentDetailsActivity.this, VideoCallPetLoverActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("id", appointment_id);
                Log.w(TAG, "ID-->" + appointment_id);
                startActivity(i);
            }


        });

        btn_cancel.setOnClickListener(v -> showStatusAlert(appointment_id));

    }

    @SuppressLint("SetTextI18n")
    private void showStatusAlert(String id) {
        try {
            dialog = new Dialog(PetNewAppointmentDetailsActivity.this);
            dialog.setContentView(R.layout.alert_approve_reject_layout);
            TextView tvheader = dialog.findViewById(R.id.tvInternetNotConnected);
            tvheader.setText(R.string.cancelappointment);
            Button dialogButtonApprove = dialog.findViewById(R.id.btnApprove);
            dialogButtonApprove.setText("Yes");
            Button dialogButtonRejected = dialog.findViewById(R.id.btnReject);
            dialogButtonRejected.setText("No");

            dialogButtonApprove.setOnClickListener(view -> {
                dialog.dismiss();
                    appoinmentCancelledResponseCall(id);



            });
            dialogButtonRejected.setOnClickListener(view -> dialog.dismiss());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }


    }

    @SuppressLint({"LongLogTag", "LogNotTimber"})
    private void appoinmentCancelledResponseCall(String id) {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<AppoinmentCancelledResponse> call = apiInterface.appoinmentCancelledResponseCall(RestUtils.getContentType(), appoinmentCancelledRequest(id));
        Log.w(TAG,"appoinmentCancelledResponseCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<AppoinmentCancelledResponse>() {
            @SuppressLint({"LongLogTag", "LogNotTimber"})
            @Override
            public void onResponse(@NonNull Call<AppoinmentCancelledResponse> call, @NonNull Response<AppoinmentCancelledResponse> response) {

                Log.w(TAG,"appoinmentCancelledResponseCall"+ "--->" + new Gson().toJson(response.body()));

                avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        startActivity(new Intent(PetNewAppointmentDetailsActivity.this, PetMyappointmentsActivity.class));





                    }

                }


            }

            @SuppressLint("LogNotTimber")
            @Override
            public void onFailure(@NonNull Call<AppoinmentCancelledResponse> call, @NonNull Throwable t) {

                avi_indicator.smoothToHide();
                Log.w(TAG,"appoinmentCancelledResponseCall flr"+"--->" + t.getMessage());
            }
        });

    }

    @SuppressLint({"LongLogTag", "LogNotTimber"})
    private AppoinmentCancelledRequest appoinmentCancelledRequest(String id) {

        /*
         * _id : 5fc639ea72fc42044bfa1683
         * missed_at : 23-10-2000 10 : 00 AM
         * doc_feedback : One Emergenecy work i am cancelling this appointment
         * appoinment_status : Missed
         */


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        AppoinmentCancelledRequest appoinmentCancelledRequest = new AppoinmentCancelledRequest();
        appoinmentCancelledRequest.set_id(id);
        appoinmentCancelledRequest.setMissed_at(currentDateandTime);
        appoinmentCancelledRequest.setDoc_feedback("");
        appoinmentCancelledRequest.setAppoint_patient_st("Petowner Cancelled appointment");
        appoinmentCancelledRequest.setAppoinment_status("Missed");
        Log.w(TAG,"appoinmentCancelledRequest"+ "--->" + new Gson().toJson(appoinmentCancelledRequest));
        return appoinmentCancelledRequest;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
//
//    @SuppressLint("NonConstantResourceId")
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.home:
//                callDirections("1");
//                break;
//            case R.id.shop:
//                callDirections("2");
//                break;
//            case R.id.services:
//                callDirections("3");
//                break;
//            case R.id.care:
//                callDirections("4");
//                break;
//            case R.id.community:
//                callDirections("5");
//                break;
//
//            default:
//                return  false;
//        }
//        return true;
//    }
    public void callDirections(String tag){
        Intent intent = new Intent(getApplicationContext(), PetLoverDashboardActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);
        finish();
    }

    @SuppressLint({"LogNotTimber", "LongLogTag"})
    private void compareDatesandTime(String currentDateandTime, String bookingDateandTime) {
        try{

            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");

            Date currentDate = formatter.parse(currentDateandTime);

            Date responseDate = formatter.parse(bookingDateandTime);

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

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.rl_homes:
                callDirections("1");
                break;

            case R.id.rl_home:
                callDirections("1");
                break;

            case R.id.rl_shop:
                callDirections("2");
                break;

            case R.id.rl_service:
                callDirections("3");
                break;

            case R.id.rl_care:
                callDirections("4");
                break;

            case R.id.rl_comn:
                callDirections("5");
                break;
        }
    }


    private void setMargins(RelativeLayout rl_layout, int i, int i1, int i2, int i3) {

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)rl_layout.getLayoutParams();
        params.setMargins(i, i1, i2, i3);
        rl_layout.setLayoutParams(params);
    }
}