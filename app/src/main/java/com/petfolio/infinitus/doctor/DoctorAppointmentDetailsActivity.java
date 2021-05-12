package com.petfolio.infinitus.doctor;

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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.petlover.PetAppointmentDetailsActivity;
import com.petfolio.infinitus.requestpojo.AppoinmentCancelledRequest;
import com.petfolio.infinitus.requestpojo.DoctorStartAppointmentRequest;
import com.petfolio.infinitus.requestpojo.PetNewAppointmentDetailsRequest;
import com.petfolio.infinitus.responsepojo.AppoinmentCancelledResponse;
import com.petfolio.infinitus.responsepojo.AppointmentsUpdateResponse;
import com.petfolio.infinitus.responsepojo.PetNewAppointmentDetailsResponse;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorAppointmentDetailsActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "DoctorAppointmentDetailsActivity";


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_back)
    ImageView img_back;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_user)
    ImageView img_user;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_usrname)
    TextView txt_usrname;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_serv_name)
    TextView txt_serv_name;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_serv_cost)
    TextView txt_serv_cost;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_cancel)
    Button btn_cancel;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_complete)
    Button btn_complete;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_prescriptiondetails)
    Button btn_prescriptiondetails;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_petimg)
    ImageView img_petimg;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_pet_name)
    TextView txt_pet_name;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_pet_type)
    TextView txt_pet_type;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_breed)
    TextView txt_breed;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_gender)
    TextView txt_gender;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_color)
    TextView txt_color;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_weight)
    TextView txt_weight;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_age)
    TextView txt_age;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_vaccinated)
    TextView txt_vaccinated;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_order_date)
    TextView txt_order_date;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_booking_id)
    TextView txt_booking_id;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_payment_method)
    TextView txt_payment_method;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_order_cost)
    TextView txt_order_cost;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_address)
    TextView txt_address;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_videocall)
    ImageView img_videocall;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_petlastvacinateddate)
    LinearLayout ll_petlastvacinateddate;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_petlastvaccinatedage)
    TextView txt_petlastvaccinatedage;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_appointment_date)
    TextView txt_appointment_date;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.include_doctor_footer)
    View include_doctor_footer;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_home_address)
    LinearLayout ll_home_address;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_home_address)
    TextView txt_home_address;

    BottomNavigationView bottom_navigation_view;


    String appointment_id;



    String appoinment_status;

    String start_appointment_status;

    String userid, allergies,problem_info,pet_name,pet_type,doctorid;

    private Dialog dialog;


    private String bookedat;
    private boolean isVaildDate;

    private String from;
    private List<PetNewAppointmentDetailsResponse.DataBean.PetIdBean.PetImgBean> pet_image;
    private String petAgeandMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_appointment_details);

        ButterKnife.bind(this);
        avi_indicator.setVisibility(View.GONE);
        txt_serv_name.setVisibility(View.GONE);
        ll_home_address.setVisibility(View.GONE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            appointment_id = extras.getString("appointment_id");
            bookedat = extras.getString("bookedat");
            from = extras.getString("from");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        if(bookedat != null){
            compareDatesandTime(currentDateandTime,bookedat);
        }


        if(from != null && from.equalsIgnoreCase("DoctorNewAppointmentAdapter")){
            btn_prescriptiondetails.setVisibility(View.GONE);
            btn_cancel.setVisibility(View.VISIBLE);
            btn_complete.setVisibility(View.VISIBLE);
            img_videocall.setVisibility(View.VISIBLE);
            if(isVaildDate){
                btn_cancel.setVisibility(View.VISIBLE);
            }else{
                btn_cancel.setVisibility(View.GONE);
            }

        }else if(from != null && from.equalsIgnoreCase("DoctorCompletedAppointmentAdapter")){
            btn_prescriptiondetails.setVisibility(View.VISIBLE);
            btn_cancel.setVisibility(View.GONE);
            btn_complete.setVisibility(View.GONE);
            img_videocall.setVisibility(View.GONE);

        }else{
            btn_prescriptiondetails.setVisibility(View.GONE);
            btn_cancel.setVisibility(View.GONE);
            btn_complete.setVisibility(View.GONE);
            img_videocall.setVisibility(View.GONE);

        }

        bottom_navigation_view = include_doctor_footer.findViewById(R.id.bottom_navigation_view);
        bottom_navigation_view.setItemIconTintList(null);
        bottom_navigation_view.setOnNavigationItemSelectedListener(this);
        bottom_navigation_view.getMenu().findItem(R.id.home).setChecked(true);






        btn_prescriptiondetails.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), DoctorPrescriptionDetailsActivity.class);
            intent.putExtra("id",appointment_id);
            startActivity(intent);
        });





        

        if (new ConnectionDetector(DoctorAppointmentDetailsActivity.this).isNetworkAvailable(DoctorAppointmentDetailsActivity.this)) {
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

                            String pet_name = response.body().getData().getPet_id().getPet_name();

                             pet_image = response.body().getData().getPet_id().getPet_img();

                            String pet_type = response.body().getData().getPet_id().getPet_type();

                            String breed = response.body().getData().getPet_id().getPet_breed();

                            String gender = response.body().getData().getPet_id().getPet_gender();

                            String colour = response.body().getData().getPet_id().getPet_color();

                            String weight = String.valueOf(response.body().getData().getPet_id().getPet_weight());

                            String pet_dob = response.body().getData().getPet_id().getPet_dob();
                            if(pet_dob != null){
                                String[] separated = pet_dob.split("-");
                                String day = separated[0];
                                String month = separated[1];
                                String year = separated[2];
                                Log.w(TAG,"day : "+day+" month: "+month+" year : "+year);

                                getAge(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day));
                            }


                            if(response.body().getData().getBooking_date_time() != null){
                                txt_appointment_date.setText(response.body().getData().getBooking_date_time());
                            }

                            if (response.body().getData().getPet_id().isVaccinated()) {
                                vaccinated = "Yes";
                                ll_petlastvacinateddate.setVisibility(View.VISIBLE);
                                if (response.body().getData().getPet_id().getLast_vaccination_date() != null && !response.body().getData().getPet_id().getLast_vaccination_date().isEmpty()) {
                                    txt_petlastvaccinatedage.setText(response.body().getData().getPet_id().getLast_vaccination_date());
                                }
                            } else {
                                ll_petlastvacinateddate.setVisibility(View.GONE);
                                vaccinated = "No";
                            }

                            String order_date = response.body().getData().getDate_and_time();

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
                            setView(usrname, usr_image, pet_name, pet_type, breed

                                    , gender, colour, weight, order_date, orderid, payment_method, order_cost, vaccinated, addr);

                            if(response.body().getData().getVisit_type() != null &&response.body().getData().getVisit_type().equalsIgnoreCase("Home")){
                                ll_home_address.setVisibility(View.VISIBLE);
                                if(response.body().getAddress() != null){
                                    if(response.body().getAddress().getLocation_address() != null){
                                        txt_home_address.setText(response.body().getAddress().getLocation_address());
                                    }
                                }
                            }else{
                                ll_home_address.setVisibility(View.GONE);
                            }
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
    private void setView(String usrname, String usr_image, String pet_name, String pet_type, String breed, String gender, String colour, String weight, String order_date, String orderid, String payment_method, String order_cost, String vaccinated, String addr) {
        if(usr_image != null && !usr_image.isEmpty()){
            Glide.with(DoctorAppointmentDetailsActivity.this)
                    .load(usr_image)
                    .into(img_user);

        }else{
            Glide.with(DoctorAppointmentDetailsActivity.this)
                    .load(APIClient.PROFILE_IMAGE_URL)
                    .into(img_user);
        }


        if(usrname!= null && !usrname.isEmpty()){
            txt_usrname.setText(usrname);
        }




        if(pet_image != null && pet_image.size()>0){
            String petimage = null;
            for(int i=0;i<pet_image.size();i++){
                petimage = pet_image.get(i).getPet_img();
            }

            Glide.with(DoctorAppointmentDetailsActivity.this)
                    .load(petimage)
                    .into(img_petimg);
        }else{
            Glide.with(DoctorAppointmentDetailsActivity.this)
                    .load(APIClient.PROFILE_IMAGE_URL)
                    .into(img_petimg);
        }


        if(pet_name != null && !pet_name.isEmpty()){
            txt_pet_name.setText(pet_name);
        }

        if(pet_type != null && !pet_type.isEmpty()){

            txt_pet_type.setText(pet_type);
        }

        if(breed != null && !breed.isEmpty()){

            txt_breed.setText(breed);
        }

        if(gender != null && !gender.isEmpty()){

            txt_gender.setText(gender);
        }

        if(colour != null && !colour.isEmpty()){

            txt_color.setText(colour);
        }

        if(weight != null && !weight.isEmpty()){

            txt_weight.setText(weight);
        }

        if(petAgeandMonth != null && !petAgeandMonth.isEmpty()){

            txt_age.setText(petAgeandMonth);
        }

        if(vaccinated != null && !vaccinated.isEmpty()){
            txt_vaccinated.setText(vaccinated);
        }

        if(order_date != null && !order_date.isEmpty()){

            txt_order_date.setText(order_date);
        }

        if(orderid != null && !orderid.isEmpty()){

            txt_booking_id.setText(orderid);
        }

        if(payment_method != null && !payment_method.isEmpty()) {

            txt_payment_method.setText(payment_method);

        }

        if(order_cost != null && !order_cost.isEmpty()){
            txt_order_cost.setText("\u20B9 "+order_cost);
            txt_serv_cost.setText("\u20B9 "+order_cost);
        }

        if(addr != null && !addr.isEmpty()){

            txt_address.setText(addr);
        }


        img_back.setOnClickListener(v -> onBackPressed());

        btn_complete.setOnClickListener(v -> {
            Intent i = new Intent(DoctorAppointmentDetailsActivity.this, PrescriptionActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("id", appointment_id);
            i.putExtra("petname", pet_name);
            i.putExtra("pettype", pet_type);
            i.putExtra("userid", userid );
            i.putExtra("allergies", allergies);
            i.putExtra("probleminfo", problem_info );
            i.putExtra("doctorid",doctorid);
            Log.w(TAG, "ID-->" + appointment_id);
            startActivity(i);

        });

    img_videocall.setOnClickListener(v -> {

        Log.w(TAG, "Start_appointment_status : " + appoinment_status);
        if (appoinment_status != null && appoinment_status.equalsIgnoreCase("Not Started")) {
            doctorStartAppointmentResponseCall(appointment_id);
        } else {
            Intent i = new Intent(DoctorAppointmentDetailsActivity.this, VideoCallDoctorActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("id", appointment_id);
            i.putExtra("petname", pet_name);
            i.putExtra("pettype", pet_type);
            i.putExtra("userid", userid );
            i.putExtra("allergies", allergies);
            i.putExtra("probleminfo", problem_info );
            Log.w(TAG, "ID-->" + appointment_id);
            startActivity(i);


        }
    });
       btn_cancel.setOnClickListener(v -> showStatusAlert(appointment_id));

    }

    @SuppressLint("SetTextI18n")
    private void showStatusAlert(String id) {
        try {
            dialog = new Dialog(DoctorAppointmentDetailsActivity.this);
            dialog.setContentView(R.layout.alert_approve_reject_layout);
            TextView tvheader = (TextView)dialog.findViewById(R.id.tvInternetNotConnected);
            tvheader.setText(R.string.cancelappointment);
            Button dialogButtonApprove = (Button) dialog.findViewById(R.id.btnApprove);
            dialogButtonApprove.setText("Yes");
            Button dialogButtonRejected = (Button) dialog.findViewById(R.id.btnReject);
            dialogButtonRejected.setText("No");

            dialogButtonApprove.setOnClickListener(view -> {
                dialog.dismiss();
                appoinmentCancelledResponseCall(id);
               });
            dialogButtonRejected.setOnClickListener(view -> dialog.dismiss());
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }


    }

    @SuppressLint({"LongLogTag", "LogNotTimber"})
    private void doctorStartAppointmentResponseCall(String id) {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<AppointmentsUpdateResponse> call = apiInterface.doctorStartAppointmentResponseCall(RestUtils.getContentType(), doctorStartAppointmentRequest(id));
        Log.w(TAG,"startAppointmentResponseCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<AppointmentsUpdateResponse>() {
            @SuppressLint({"LongLogTag", "LogNotTimber"})
            @Override
            public void onResponse(@NonNull Call<AppointmentsUpdateResponse> call, @NonNull Response<AppointmentsUpdateResponse> response) {

                Log.w(TAG,"startAppointmentResponseCall"+ "--->" + new Gson().toJson(response.body()));

                avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        Intent i = new Intent(DoctorAppointmentDetailsActivity.this, VideoCallDoctorActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.putExtra("id", appointment_id);
                        i.putExtra("petname", pet_name);
                        i.putExtra("pettype", pet_type);
                        i.putExtra("userid", userid );
                        i.putExtra("allergies", allergies);
                        i.putExtra("probleminfo", problem_info );
                        Log.w(TAG, "ID-->" + appointment_id);
                        startActivity(i);

                    }

                }


            }

            @SuppressLint("LogNotTimber")
            @Override
            public void onFailure(@NonNull Call<AppointmentsUpdateResponse> call, @NonNull Throwable t) {

                avi_indicator.smoothToHide();
                Log.w(TAG,"startAppointmentResponseCall flr"+"--->" + t.getMessage());
            }
        });

    }

    @SuppressLint({"LongLogTag", "LogNotTimber"})
    private DoctorStartAppointmentRequest doctorStartAppointmentRequest(String id) {
        /*
         * _id : 5fc639ea72fc42044bfa1683
         * appoinment_status : In-Progress
         */
        DoctorStartAppointmentRequest doctorStartAppointmentRequest = new DoctorStartAppointmentRequest();
        doctorStartAppointmentRequest.set_id(id);
        doctorStartAppointmentRequest.setStart_appointment_status("In-Progress");
        Log.w(TAG,"doctorStartAppointmentRequest"+ "--->" + new Gson().toJson(doctorStartAppointmentRequest));
        return doctorStartAppointmentRequest;
    }

    @SuppressLint({"LongLogTag", "LogNotTimber"})
    private void appoinmentCancelledResponseCall(String id) {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<AppoinmentCancelledResponse> call = apiInterface.appoinmentCancelledResponseCall(RestUtils.getContentType(), appoinmentCancelledRequest(id));
        Log.w(TAG,"appoinmentCancelledResponseCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<AppoinmentCancelledResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<AppoinmentCancelledResponse> call, @NonNull Response<AppoinmentCancelledResponse> response) {

                Log.w(TAG,"appoinmentCancelledResponseCall"+ "--->" + new Gson().toJson(response.body()));

                avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        startActivity(new Intent(DoctorAppointmentDetailsActivity.this, DoctorDashboardActivity.class));

                    }

                }


            }

            @SuppressLint("LongLogTag")
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
        appoinmentCancelledRequest.setAppoint_patient_st("Doctor Cancelled appointment");
        appoinmentCancelledRequest.setAppoinment_status("Missed");
        Log.w(TAG,"appoinmentCancelledRequest"+ "--->" + new Gson().toJson(appoinmentCancelledRequest));
        return appoinmentCancelledRequest;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @SuppressLint({"LogNotTimber", "LongLogTag"})
    private void compareDatesandTime(String currentDateandTime, String bookingDateandTime) {
        try{

            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");

            Date currentDate = formatter.parse(currentDateandTime);

            Date responseDate = formatter.parse(bookingDateandTime);

            Log.w(TAG,"compareDatesandTime--->"+"responseDate :"+responseDate+" "+"currentDate :"+currentDate);

            if (currentDate != null) {
                if (responseDate != null) {
                    if (currentDate.compareTo(responseDate)<0 || responseDate.compareTo(currentDate) == 0)
                    {
                        Log.w(TAG,"date is equal");
                        isVaildDate = true;

                    }else{
                        Log.w(TAG,"date is not equal");
                        isVaildDate = false;
                    }
                }
            }


        }catch (ParseException e1){
            e1.printStackTrace();
        }
    }

    @SuppressLint("LongLogTag")
    private void getAge(int year, int month, int day){

        Log.w(TAG,"day : "+day+" month: "+month+" year : "+year);

        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        int months = dob.get(Calendar.MONTH) - today.get(Calendar.MONTH);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        Integer monthsInt = new Integer(months);
        String ageS = ageInt.toString();
        String monthsS = monthsInt.toString();

        if(ageInt != 0){
            petAgeandMonth = ageS+" years "+monthsS+" months";
        }else{
            petAgeandMonth = monthsS+" months";

        }



        Log.w(TAG,"ageS: "+ageS+" months : "+monthsS);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                callDirections("1");
                break;
            case R.id.shop:
                callDirections("2");
                break;

            case R.id.community:
                callDirections("3");
                break;

            default:
                return  false;
        }
        return true;
    }

    public void callDirections(String tag){
        Intent intent = new Intent(getApplicationContext(), DoctorDashboardActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);
        finish();
    }
}