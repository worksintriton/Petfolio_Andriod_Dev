package com.petfolio.infinitus.vendor;

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

import com.petfolio.infinitus.adapter.ViewPagerVendorDetailsAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;

import com.petfolio.infinitus.requestpojo.VendorGetsOrderIdRequest;

import com.petfolio.infinitus.responsepojo.VendorGetsOrderIDResponse;
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

public class VendorProfileScreenActivity extends AppCompatActivity implements View.OnClickListener {
    private  String TAG = "VendorProfileScreenActivity";

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
    @BindView(R.id.txt_business_name)
    TextView txt_business_name;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_business_email)
    TextView txt_business_email;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_phone)
    TextView txt_phone;



    private SessionManager session;
    String name,emailid,phoneNo,userid;
    private Dialog dialog;


    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;
    private String profileimage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_profile_screen);
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
            Glide.with(VendorProfileScreenActivity.this)
                    .load(profileimage)
                    .into(img_profile);
        }else{
            Glide.with(VendorProfileScreenActivity.this)
                    .load(R.drawable.upload)
                    .into(img_profile);

        }






        if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext())) {
            getVendorOrderIDResponseCall();
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
        startActivity(new Intent(getApplicationContext(), VendorDashboardActivity.class));
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
                startActivity(new Intent(getApplicationContext(), VendorEditProfileActivity.class));
                break;
                case R.id.txt_edit_image:
                startActivity(new Intent(getApplicationContext(), EditVendorProfileImageActivity.class));
                break;
                case R.id.txt_edit_doc_business_info:
                startActivity(new Intent(getApplicationContext(), EditVendorRegisterFormActivity.class));
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



    private void confirmLogoutDialog(){
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(VendorProfileScreenActivity.this);
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

    private void getVendorOrderIDResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<VendorGetsOrderIDResponse> call = apiInterface.vendor_gets_orderbyId_ResponseCall(RestUtils.getContentType(), vendorGetsOrderIdRequest());
        Log.w(TAG,"getVendorOrderIDResponseCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<VendorGetsOrderIDResponse>() {
            @SuppressLint({"LongLogTag", "LogNotTimber"})
            @Override
            public void onResponse(@NonNull Call<VendorGetsOrderIDResponse> call, @NonNull Response<VendorGetsOrderIDResponse> response) {

                Log.w(TAG,"getVendorOrderIDResponseCall"+ "--->" + new Gson().toJson(response.body()));

                 avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getCode() == 200){

                        if(response.body().getData()!=null){

                            if(response.body().getData().get_id()!=null&&!(response.body().getData().get_id().isEmpty())){
                                APIClient.VENDOR_ID = response.body().getData().get_id();
                            }


                            if(response.body().getData().getBussiness_gallery() != null && response.body().getData().getBussiness_gallery().size()>0){
                                for (int i = 0; i < response.body().getData().getBussiness_gallery().size(); i++) {
                                    response.body().getData().getBussiness_gallery().get(i).getBussiness_gallery();
                                }


                                viewpageData(response.body().getData().getBussiness_gallery());

                                if(response.body().getData().getBussiness_name() != null){
                                    txt_business_name.setText(response.body().getData().getBussiness_name());
                                }
                                if(response.body().getData().getBussiness_email() != null){
                                    txt_business_email.setText(response.body().getData().getBussiness_email());
                                }
                                if(response.body().getData().getBussiness_phone() != null){
                                    txt_phone.setText(response.body().getData().getBussiness_phone());
                                }

                            }


                        }


                    }

                }


            }

            @SuppressLint({"LongLogTag", "LogNotTimber"})
            @Override
            public void onFailure(@NonNull Call<VendorGetsOrderIDResponse> call, @NonNull Throwable t) {

                  avi_indicator.smoothToHide();

                Log.w(TAG,"getVendorOrderIDResponseCall flr"+"--->" + t.getMessage());
            }
        });

    }
    private VendorGetsOrderIdRequest vendorGetsOrderIdRequest() {
        VendorGetsOrderIdRequest vendorGetsOrderIdRequest = new VendorGetsOrderIdRequest();
        vendorGetsOrderIdRequest.setUser_id(userid);
        Log.w(TAG,"vendorGetsOrderIdRequest"+ "--->" + new Gson().toJson(vendorGetsOrderIdRequest));
        return vendorGetsOrderIdRequest;
    }
    private void viewpageData(List<VendorGetsOrderIDResponse.DataBean.BussinessGalleryBean> bussiness_gallery) {
        tabLayout.setupWithViewPager(viewPager, true);

        ViewPagerVendorDetailsAdapter viewPagerVendorDetailsAdapter = new ViewPagerVendorDetailsAdapter(getApplicationContext(), bussiness_gallery);
        viewPager.setAdapter(viewPagerVendorDetailsAdapter);
        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update =  new Runnable() {
            public void run() {
                if (currentPage == bussiness_gallery.size()) {
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