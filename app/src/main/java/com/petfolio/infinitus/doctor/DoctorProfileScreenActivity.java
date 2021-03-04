package com.petfolio.infinitus.doctor;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.activity.LoginActivity;
import com.petfolio.infinitus.activity.location.ManageAddressActivity;

import com.petfolio.infinitus.adapter.ViewPagerDoctorClinicDetailsAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.petlover.AddYourPetOldUserActivity;

import com.petfolio.infinitus.requestpojo.DoctorDetailsByUserIdRequest;

import com.petfolio.infinitus.responsepojo.DoctorDetailsByUserIdResponse;

import com.petfolio.infinitus.responsepojo.PetListResponse;
import com.petfolio.infinitus.sessionmanager.SessionManager;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;


import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorProfileScreenActivity extends AppCompatActivity implements View.OnClickListener {
    private  String TAG = "DoctorProfileScreenActivity";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_back)
    ImageView img_back;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_profile)
    ImageView img_profile;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_edit_image)
    TextView txt_edit_image;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_usrname)
    TextView txt_usrname;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_mail)
    TextView txt_mail;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_phn_num)
    TextView txt_phn_num;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_edit_profile)
    TextView txt_edit_profile;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_manage_address)
    TextView txt_manage_address;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_change_password)
    TextView txt_change_password;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;



    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_logout)
    TextView txt_logout;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.pager)
    ViewPager viewPager;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tabDots)
    TabLayout tabLayout;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_edit_doc_business_info)
    TextView txt_edit_doc_business_info;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_dr_specialization)
    TextView txt_dr_specialization;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_pets_handled)
    TextView txt_pets_handled;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_clinic_address)
    TextView txt_clinic_address;




    private SessionManager session;
    String name,emailid,phoneNo,userid;
    private List<PetListResponse.DataBean> petList;
    private Dialog dialog;
    private List<DoctorDetailsByUserIdResponse.DataBean.ClinicPicBean> doctorclinicdetailsResponseList;
    private String clinicname,doctorname;
    private String concatenatedSpcNames = "";
    private String concatenatedPetHandled = "";

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;
    private String profileimage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile_screen);
        ButterKnife.bind(this);

        Log.w(TAG,"onCreate : ");
        avi_indicator.setVisibility(View.GONE);


        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getProfileDetails();
        name = user.get(SessionManager.KEY_FIRST_NAME);
        emailid = user.get(SessionManager.KEY_EMAIL_ID);
        phoneNo = user.get(SessionManager.KEY_MOBILE);
        userid = user.get(SessionManager.KEY_ID);
        profileimage = user.get(SessionManager.KEY_PROFILE_IMAGE);

        Log.w(TAG,"session profileimage : "+profileimage);


        txt_usrname.setText(name);
        txt_mail.setText(emailid);
        txt_phn_num.setText(phoneNo);


        if(profileimage != null && !profileimage.isEmpty()){
            Glide.with(DoctorProfileScreenActivity.this)
                    .load(profileimage)
                    .into(img_profile);
        }else{
            Glide.with(DoctorProfileScreenActivity.this)
                    .load(R.drawable.upload)
                    .into(img_profile);

        }






        if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext())) {
            doctorDetailsByUserIdResponseCall();
        }


        img_back.setOnClickListener(this);
        txt_manage_address.setOnClickListener(this);
        txt_change_password.setOnClickListener(this);
        txt_logout.setOnClickListener(this);
        txt_edit_profile.setOnClickListener(this);
        txt_edit_image.setOnClickListener(this);
        txt_edit_doc_business_info.setOnClickListener(this);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), DoctorDashboardActivity.class));
        finish();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                onBackPressed();
                break;
                case R.id.txt_edit_profile:
                startActivity(new Intent(getApplicationContext(), DoctorEditProfileActivity.class));
                break;
                case R.id.txt_edit_image:
                startActivity(new Intent(getApplicationContext(), EditDoctorProfileImageActivity.class));
                break;
                case R.id.txt_edit_doc_business_info:
                startActivity(new Intent(getApplicationContext(), EditDoctorBusinessInfoActivity.class));
                break;
                case R.id.txt_manage_address:
                    //gotoManageAddress();
                break;
                case R.id.txt_change_password:
                break;
                case R.id.txt_logout:
                    confirmLogoutDialog();
                break; 

        }
    }

    private void gotoAddYourPet() {
        startActivity(new Intent(getApplicationContext(), AddYourPetOldUserActivity.class));
    }

    private void gotoManageAddress() {
        startActivity(new Intent(DoctorProfileScreenActivity.this, ManageAddressActivity.class));
    }

    private void confirmLogoutDialog(){
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(DoctorProfileScreenActivity.this);
        alertDialogBuilder.setMessage("Are you sure want to logout?");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1)
                    {

                        gotoLogout();


                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialogBuilder.setCancelable(true);
            }
        });

        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
    private void gotoLogout() {
        session.logoutUser();
        session.setIsLogin(false);
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();



    }






    @SuppressLint("LogNotTimber")
    private void doctorDetailsByUserIdResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<DoctorDetailsByUserIdResponse> call = ApiService.doctorDetailsByUserIdResponseCall(RestUtils.getContentType(),doctorDetailsByUserIdRequest());
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<DoctorDetailsByUserIdResponse>() {
            @Override
            public void onResponse(@NonNull Call<DoctorDetailsByUserIdResponse> call, @NonNull Response<DoctorDetailsByUserIdResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"DoctorDetailsByUserIdResponse"+ "--->" + new Gson().toJson(response.body()));


                if (response.body() != null) {
                    if(200 == response.body().getCode()){

                        if(response.body().getData().getClinic_pic() != null) {
                            doctorclinicdetailsResponseList = response.body().getData().getClinic_pic();
                            Log.w(TAG,"Size"+doctorclinicdetailsResponseList.size());
                            Log.w(TAG,"doctorclinicdetailsResponseList : "+new Gson().toJson(doctorclinicdetailsResponseList));
                        }
                        if(response.body().getData().getClinic_name() != null) {
                            clinicname = response.body().getData().getClinic_name();
                        }
                        if(response.body().getData().getDr_name() != null) {
                            doctorname = response.body().getData().getDr_name();
                        }
                        if(response.body().getData().getClinic_loc() != null){
                        txt_clinic_address.setText(response.body().getData().getClinic_loc());
                        }






                        if(doctorclinicdetailsResponseList != null && doctorclinicdetailsResponseList.size()>0){

                            for (int i = 0; i < doctorclinicdetailsResponseList.size(); i++) {
                                doctorclinicdetailsResponseList.get(i).getClinic_pic();
                                Log.w(TAG, "RES" + ", " +  doctorclinicdetailsResponseList.get(i).getClinic_pic());
                            }


                            viewpageData(doctorclinicdetailsResponseList);

                        }


                        if(response.body().getData().getSpecialization() != null){
                            for (int i = 0; i < response.body().getData().getSpecialization().size(); i++) {
                                concatenatedSpcNames += response.body().getData().getSpecialization().get(i).getSpecialization();
                                if (i < response.body().getData().getSpecialization().size() - 1) concatenatedSpcNames += ", ";
                            }
                            txt_dr_specialization.setText(concatenatedSpcNames);

                        }
                        if(response.body().getData().getPet_handled() != null){
                            for (int i = 0; i < response.body().getData().getPet_handled().size(); i++) {
                                concatenatedPetHandled += response.body().getData().getPet_handled().get(i).getPet_handled();
                                if (i < response.body().getData().getPet_handled().size() - 1) concatenatedPetHandled += ", ";
                            }
                            txt_pets_handled.setText(concatenatedPetHandled);

                        }

                    }


                }
            }

            @Override
            public void onFailure(@NonNull Call<DoctorDetailsByUserIdResponse> call, @NonNull Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG,"DoctorDetailsResponse flr"+"--->" + t.getMessage());
            }
        });

    }
    @SuppressLint("LogNotTimber")
    private DoctorDetailsByUserIdRequest doctorDetailsByUserIdRequest() {
        DoctorDetailsByUserIdRequest doctorDetailsByUserIdRequest = new DoctorDetailsByUserIdRequest();
        doctorDetailsByUserIdRequest.setUser_id(userid);
        Log.w(TAG,"doctorDetailsByUserIdRequest"+ "--->" + new Gson().toJson(doctorDetailsByUserIdRequest));
        return doctorDetailsByUserIdRequest;
    }
    private void viewpageData(List<DoctorDetailsByUserIdResponse.DataBean.ClinicPicBean> doctorclinicdetailsResponseList) {
        tabLayout.setupWithViewPager(viewPager, true);

        ViewPagerDoctorClinicDetailsAdapter viewPagerClinicDetailsAdapter = new ViewPagerDoctorClinicDetailsAdapter(getApplicationContext(), doctorclinicdetailsResponseList);
        viewPager.setAdapter(viewPagerClinicDetailsAdapter);
        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update =  new Runnable() {
            public void run() {
                if (currentPage == DoctorProfileScreenActivity.this.doctorclinicdetailsResponseList.size()) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, false);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

    }

}