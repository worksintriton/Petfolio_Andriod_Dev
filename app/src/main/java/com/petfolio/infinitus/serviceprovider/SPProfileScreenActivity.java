package com.petfolio.infinitus.serviceprovider;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.activity.LoginActivity;
import com.petfolio.infinitus.activity.location.ManageAddressActivity;
import com.petfolio.infinitus.adapter.ViewPagerSPGalleryDetailsAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;

import com.petfolio.infinitus.doctor.EditDoctorProfileImageActivity;
import com.petfolio.infinitus.requestpojo.SPDetailsByUserIdRequest;
import com.petfolio.infinitus.responsepojo.PetListResponse;
import com.petfolio.infinitus.responsepojo.ServiceProviderRegisterFormCreateResponse;
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

public class SPProfileScreenActivity extends AppCompatActivity implements View.OnClickListener {
    private  String TAG = "SPProfileScreenActivity";

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
    @BindView(R.id.txt_myservices)
    TextView txt_myservices;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_sp_specialization)
    TextView txt_sp_specialization;








    private SessionManager session;
    String name,emailid,phoneNo,userid;
    private List<PetListResponse.DataBean> petList;
    private Dialog dialog;
    private String clinicname,doctorname;
    private String concatenatedSpcNames = "";
    private String concatenatedServiceNames = "";

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;
    private String profileimage;
    private List<ServiceProviderRegisterFormCreateResponse.DataBean.BusServiceGallBean> servieGalleryResponseList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_profile_screen);
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
            Glide.with(SPProfileScreenActivity.this)
                    .load(profileimage)
                    .into(img_profile);
        }else{
            Glide.with(SPProfileScreenActivity.this)
                    .load(R.drawable.upload)
                    .into(img_profile);

        }






        if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext())) {
            spDetailsReponseByUserIdCall();
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
        startActivity(new Intent(getApplicationContext(), ServiceProviderDashboardActivity.class));
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
                startActivity(new Intent(getApplicationContext(), SPEditProfileActivity.class));
                break;
                case R.id.txt_edit_image:
                startActivity(new Intent(getApplicationContext(), EditDoctorProfileImageActivity.class));
                break;
                case R.id.txt_edit_doc_business_info:
                startActivity(new Intent(getApplicationContext(), ServiceProviderEditFormActivity.class));
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



    private void gotoManageAddress() {
        startActivity(new Intent(SPProfileScreenActivity.this, ManageAddressActivity.class));
    }

    private void confirmLogoutDialog(){
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(SPProfileScreenActivity.this);
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






    private void spDetailsReponseByUserIdCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<ServiceProviderRegisterFormCreateResponse> call = ApiService.spDetailsReponseByUserIdCall(RestUtils.getContentType(),spDetailsByUserIdRequest());
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<ServiceProviderRegisterFormCreateResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServiceProviderRegisterFormCreateResponse> call, @NonNull Response<ServiceProviderRegisterFormCreateResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"ServiceProviderRegisterFormCreateResponse"+ "--->" + new Gson().toJson(response.body()));


               if (response.body() != null) {
                    if(200 == response.body().getCode()){
                        servieGalleryResponseList  = response.body().getData().getBus_service_gall();
                        clinicname =  response.body().getData().getBussiness_name();





                        if(servieGalleryResponseList != null && servieGalleryResponseList.size()>0){

                            for (int i = 0; i < servieGalleryResponseList.size(); i++) {
                                servieGalleryResponseList.get(i).getBus_service_gall();
                            }


                            viewpageData(servieGalleryResponseList);

                        }


                        if(response.body().getData().getBus_spec_list() != null){
                            for (int i = 0; i < response.body().getData().getBus_spec_list().size(); i++) {
                                concatenatedSpcNames += response.body().getData().getBus_spec_list().get(i).getBus_spec_list();
                                if (i < response.body().getData().getBus_spec_list().size() - 1) concatenatedSpcNames += ", ";
                            }
                            txt_sp_specialization.setText(concatenatedSpcNames);

                        }
                        if(response.body().getData().getBus_service_list() != null){
                            for (int i = 0; i < response.body().getData().getBus_service_list().size(); i++) {
                                concatenatedServiceNames += response.body().getData().getBus_service_list().get(i).getBus_service_list();
                                if (i < response.body().getData().getBus_service_list().size() - 1) concatenatedServiceNames += ", ";
                            }
                            txt_myservices.setText(concatenatedServiceNames);

                        }

                    }else{

                    }


                }
            }

            @Override
            public void onFailure(@NonNull Call<ServiceProviderRegisterFormCreateResponse> call, @NonNull Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG,"ServiceProviderRegisterFormCreateResponse flr"+"--->" + t.getMessage());
            }
        });

    }
    @SuppressLint("LongLogTag")
    private SPDetailsByUserIdRequest spDetailsByUserIdRequest() {
        /* user_id : 5fc61b82b750da703e48da78 */
        SPDetailsByUserIdRequest spDetailsByUserIdRequest = new SPDetailsByUserIdRequest();
        spDetailsByUserIdRequest.setUser_id(userid);
        Log.w(TAG,"spDetailsByUserIdRequest"+ "--->" + new Gson().toJson(spDetailsByUserIdRequest));
        return spDetailsByUserIdRequest;
    }
    private void viewpageData(List<ServiceProviderRegisterFormCreateResponse.DataBean.BusServiceGallBean> servieGalleryResponseList) {
        tabLayout.setupWithViewPager(viewPager, true);
        ViewPagerSPGalleryDetailsAdapter viewPagerSPGalleryDetailsAdapter = new ViewPagerSPGalleryDetailsAdapter(getApplicationContext(), servieGalleryResponseList);
        viewPager.setAdapter(viewPagerSPGalleryDetailsAdapter);
        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update =  new Runnable() {
            public void run() {
                if (currentPage == servieGalleryResponseList.size()) {
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