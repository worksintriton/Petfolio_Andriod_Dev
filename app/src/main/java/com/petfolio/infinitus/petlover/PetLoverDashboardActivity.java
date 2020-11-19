package com.petfolio.infinitus.petlover;




import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import android.preference.PreferenceManager;
import android.util.Log;


import android.view.View;


import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.adapter.PetLoverDoctorAdapter;
import com.petfolio.infinitus.adapter.PetLoverProductsAdapter;
import com.petfolio.infinitus.adapter.PetLoverServicesAdapter;
import com.petfolio.infinitus.adapter.ViewPagerDashboardAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.requestpojo.AddYourPetRequest;
import com.petfolio.infinitus.requestpojo.PetLoverDashboardRequest;
import com.petfolio.infinitus.responsepojo.AddYourPetResponse;
import com.petfolio.infinitus.responsepojo.PetLoverDashboardResponse;
import com.petfolio.infinitus.sessionmanager.SessionManager;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class PetLoverDashboardActivity extends NavigationDrawer implements View.OnClickListener {

    private String TAG = "PetLoverDashboardActivity";



    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;





    String token = "";
    String type ="";
    String name = "",patientid = "";

    static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    double latitude, longitude;

    private Handler handler = new Handler();
    Runnable runnable;
    private TextView headertitle;








    Dialog dialog;
    private String userid;

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.pager)
    ViewPager viewPager;

    @BindView(R.id.tabDots)
    TabLayout tabLayout;

    @BindView(R.id.rvdoctors)
    RecyclerView rvdoctors;

    @BindView(R.id.txt_doctors)
    TextView txt_doctors;

    @BindView(R.id.txt_seemore_doctors)
    TextView txt_seemore_doctors;


    @BindView(R.id.rvservice)
    RecyclerView rvservice;

    @BindView(R.id.txt_services)
    TextView txt_services;
    
    @BindView(R.id.txt_seemore_services)
    TextView txt_seemore_services;

    @BindView(R.id.rvproducts)
    RecyclerView rvproducts;

    @BindView(R.id.txt_products)
    TextView txt_products;
    
    @BindView(R.id.txt_seemore_products)
    TextView txt_seemore_products;

    
    private List<PetLoverDashboardResponse.DataBean.DashboarddataBean.BannerDetailsBean> listHomeBannerResponse;
    private List<PetLoverDashboardResponse.DataBean.DashboarddataBean.DoctorDetailsBean> doctorDetailsResponseList;
    private List<PetLoverDashboardResponse.DataBean.DashboarddataBean.ServiceDetailsBean> serviceDetailsResponseList;
    private List<PetLoverDashboardResponse.DataBean.DashboarddataBean.ProductsDetailsBean> productDetailsResponseList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w(TAG, "OnCreate-->");
        setContentView(R.layout.activity_pet_lover_dashboard);

        ButterKnife.bind(this);
        avi_indicator.setVisibility(View.GONE);


        SessionManager sessionManager = new SessionManager(PetLoverDashboardActivity.this);
        HashMap<String, String> user = sessionManager.getProfileDetails();
       // userid = user.get(SessionManager.KEY_ID);
        userid = "5fb63307b223363ad0039b0e";




        toolbar.setVisibility(View.VISIBLE);

        txt_seemore_doctors.setOnClickListener(this);
        txt_seemore_services.setOnClickListener(this);
        txt_seemore_products.setOnClickListener(this);

        petLoverDashboardResponseCall();







        tvWelcomeName.setText("Home " );




        if (null == name || name.isEmpty()) {

            if (getIntent().getExtras() != null) {

                try {
                    name = getIntent().getExtras().getString("Name");
                    Log.w(TAG, "onCreate name---> " + name);
                    if (null != name) {
                        Log.w(TAG, "onCreate name1---> " + name);
                        tvWelcomeName.setText("Home ");
                        headertitle.setText("Home " );
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }












    }//end of oncreate












    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("BackPressed", "onDestroy");
        Log.w(TAG, "on destroy working");
        if (progressDialog != null && progressDialog.isShowing() ){
            progressDialog.cancel();
        }

    }

    @Override
    public void finish() {
        super.finish();
        if (progressDialog != null && progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }
    }















    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_seemore_doctors:
                if(doctorDetailsResponseList.size()>0){
                    rvdoctors.setVisibility(View.VISIBLE);
                    txt_doctors.setVisibility(View.VISIBLE);
                    setViewDoctorsSeeMore();
                }
                else{
                    rvdoctors.setVisibility(View.GONE);
                    txt_doctors.setVisibility(View.GONE);

                }
                break;
                case R.id.txt_seemore_services:
                if(serviceDetailsResponseList.size()>0){
                    rvservice.setVisibility(View.VISIBLE);
                    txt_services.setVisibility(View.VISIBLE);
                    setViewServicesSeeMore();
                }
                else{
                    rvservice.setVisibility(View.GONE);
                    txt_services.setVisibility(View.GONE);

                }
                break;

            case R.id.txt_seemore_products:
                if(serviceDetailsResponseList.size()>0){
                    rvproducts.setVisibility(View.VISIBLE);
                    txt_products.setVisibility(View.VISIBLE);
                    setViewProductsSeeMore();
                }
                else{
                    rvproducts.setVisibility(View.GONE);
                    txt_products.setVisibility(View.GONE);

                }
                break;


        }

    }




    @Override
    protected void onResume() {
        super.onResume();


    }




    @Override
    public void onBackPressed() {
        new android.app.AlertDialog.Builder(PetLoverDashboardActivity.this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        PetLoverDashboardActivity.this.finishAffinity();
                    }
                })
                .setNegativeButton("No", null)
                .show();


    }

    private void petLoverDashboardResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<PetLoverDashboardResponse> call = apiInterface.petLoverDashboardResponseCall(RestUtils.getContentType(), petLoverDashboardRequest());
        Log.w(TAG,"PetLoverDashboardResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<PetLoverDashboardResponse>() {
            @Override
            public void onResponse(@NonNull Call<PetLoverDashboardResponse> call, @NonNull Response<PetLoverDashboardResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"PetLoverDashboardResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    if (200 == response.body().getCode()) {
                        if (response.body().getData().getDashboarddata() != null) {
                            listHomeBannerResponse = response.body().getData().getDashboarddata().getBanner_details();
                            for (int i = 0; i < listHomeBannerResponse.size(); i++) {
                                listHomeBannerResponse.get(i).getImg_path();
                                Log.w(TAG, "RES" + " " + listHomeBannerResponse.get(i).getImg_path());
                            }

                            if (listHomeBannerResponse != null) {
                                viewpageData(listHomeBannerResponse);
                            }
                        }
                        if (response.body().getData().getDashboarddata().getDoctor_details() != null) {
                            doctorDetailsResponseList = response.body().getData().getDashboarddata().getDoctor_details();
                            Log.w(TAG, "doctorDetailsResponseList Size" + doctorDetailsResponseList.size());
                            if (doctorDetailsResponseList != null && doctorDetailsResponseList.size()>0) {
                                rvdoctors.setVisibility(View.VISIBLE);
                                txt_doctors.setVisibility(View.VISIBLE);
                                setViewDoctors(doctorDetailsResponseList);
                            } else {
                                rvdoctors.setVisibility(View.GONE);
                                txt_doctors.setVisibility(View.GONE);

                            }

                        }
                        if (response.body().getData().getDashboarddata().getService_details() != null) {
                            serviceDetailsResponseList = response.body().getData().getDashboarddata().getService_details();
                            Log.w(TAG, "serviceDetailsResponseList Size" + serviceDetailsResponseList.size());
                            if (serviceDetailsResponseList != null && serviceDetailsResponseList.size()>0) {
                                rvservice.setVisibility(View.VISIBLE);
                                txt_services.setVisibility(View.VISIBLE);
                                setViewServices(serviceDetailsResponseList);
                            } else {
                                rvservice.setVisibility(View.GONE);
                                txt_services.setVisibility(View.GONE);

                            }

                        }
                        if (response.body().getData().getDashboarddata().getProducts_details() != null) {
                            productDetailsResponseList = response.body().getData().getDashboarddata().getProducts_details();
                            Log.w(TAG, "productDetailsResponseList Size" + productDetailsResponseList.size());
                            if (productDetailsResponseList != null && productDetailsResponseList.size()>0) {
                                rvproducts.setVisibility(View.VISIBLE);
                                txt_products.setVisibility(View.VISIBLE);
                                setViewProducts(productDetailsResponseList);
                            } else {
                                rvproducts.setVisibility(View.GONE);
                                txt_products.setVisibility(View.GONE);

                            }

                        }


                    }else {
                        // showErrorLoading(response.body().getMessage());
                    }

                }


            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<PetLoverDashboardResponse> call,@NonNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.e("PetLoverDashboardResponseflr", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setViewProducts(List<PetLoverDashboardResponse.DataBean.DashboarddataBean.ProductsDetailsBean> productDetailsResponseList) {
        rvproducts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvproducts.setMotionEventSplittingEnabled(false);
        int size =3;
        rvproducts.setItemAnimator(new DefaultItemAnimator());
        PetLoverProductsAdapter petLoverProductsAdapter = new PetLoverProductsAdapter(getApplicationContext(), productDetailsResponseList, rvproducts, size);
        rvproducts.setAdapter(petLoverProductsAdapter);
    }
    private void setViewProductsSeeMore() {
        rvproducts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvproducts.setMotionEventSplittingEnabled(false);
        int size = productDetailsResponseList.size();
        rvproducts.setItemAnimator(new DefaultItemAnimator());
        PetLoverProductsAdapter petLoverProductsAdapter = new PetLoverProductsAdapter(getApplicationContext(), productDetailsResponseList, rvproducts, size);
        rvproducts.setAdapter(petLoverProductsAdapter);
    }

    private void setViewServices(List<PetLoverDashboardResponse.DataBean.DashboarddataBean.ServiceDetailsBean> serviceDetailsResponseList) {
        rvservice.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvservice.setMotionEventSplittingEnabled(false);
        int size =3;
        rvservice.setItemAnimator(new DefaultItemAnimator());
        PetLoverServicesAdapter petLoverServicesAdapter = new PetLoverServicesAdapter(getApplicationContext(), serviceDetailsResponseList, rvservice, size);
        rvservice.setAdapter(petLoverServicesAdapter);
    }
    private void setViewServicesSeeMore() {
        rvservice.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvservice.setMotionEventSplittingEnabled(false);
        int size = serviceDetailsResponseList.size();
        rvservice.setItemAnimator(new DefaultItemAnimator());
        PetLoverServicesAdapter petLoverServicesAdapter = new PetLoverServicesAdapter(getApplicationContext(), serviceDetailsResponseList, rvservice, size);
        rvservice.setAdapter(petLoverServicesAdapter);
        petLoverServicesAdapter.notifyDataSetChanged();
    }

    private void setViewDoctors(List<PetLoverDashboardResponse.DataBean.DashboarddataBean.DoctorDetailsBean> doctorDetailsResponseList) {
        rvdoctors.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvdoctors.setMotionEventSplittingEnabled(false);
        int size =3;
        rvdoctors.setItemAnimator(new DefaultItemAnimator());
        PetLoverDoctorAdapter petLoverDoctorAdapter = new PetLoverDoctorAdapter(getApplicationContext(), doctorDetailsResponseList, rvdoctors, size);
        rvdoctors.setAdapter(petLoverDoctorAdapter);
    }

    private void setViewDoctorsSeeMore() {
        rvdoctors.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvdoctors.setMotionEventSplittingEnabled(false);
        int size = doctorDetailsResponseList.size();
        rvdoctors.setItemAnimator(new DefaultItemAnimator());
        PetLoverDoctorAdapter petLoverDoctorAdapter = new PetLoverDoctorAdapter(getApplicationContext(), doctorDetailsResponseList, rvdoctors, size);
        rvdoctors.setAdapter(petLoverDoctorAdapter);
        petLoverDoctorAdapter.notifyDataSetChanged();

    }
    private void viewpageData(List<PetLoverDashboardResponse.DataBean.DashboarddataBean.BannerDetailsBean> listHomeBannerResponse) {
            tabLayout.setupWithViewPager(viewPager, true);

        ViewPagerDashboardAdapter viewPagerDashboardAdapter = new ViewPagerDashboardAdapter(this, listHomeBannerResponse);
            viewPager.setAdapter(viewPagerDashboardAdapter);
            /*After setting the adapter use the timer */
            final Handler handler = new Handler();
            final Runnable Update =  new Runnable() {
                public void run() {
                    if (currentPage == listHomeBannerResponse.size()) {
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

    private PetLoverDashboardRequest petLoverDashboardRequest() {
        /*
         * user_id : 5fb22773e70b0d3cc5b2c19a
         * lat : 12.0909
         * long : 80.09093
         * user_type : 1
         * address : Muthamil nager, Kodugaiyur, Chennai - 600 118
         */


        PetLoverDashboardRequest petLoverDashboardRequest = new PetLoverDashboardRequest();
        petLoverDashboardRequest.setUser_id(userid);
        petLoverDashboardRequest.setLat(12.09090);
        petLoverDashboardRequest.setLongX(80.09093);
        petLoverDashboardRequest.setUser_type(1);
        petLoverDashboardRequest.setAddress("Muthamil nager, Kodugaiyur, Chennai - 600 118");


        Log.w(TAG,"petLoverDashboardRequest"+ new Gson().toJson(petLoverDashboardRequest));
        return petLoverDashboardRequest;
    }





}








