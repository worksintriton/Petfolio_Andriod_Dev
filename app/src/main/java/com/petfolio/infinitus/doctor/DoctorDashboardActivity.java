package com.petfolio.infinitus.doctor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;

import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.activity.VerifyOtpActivity;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.fragmentdoctor.FragmentDoctorDashboard;
import com.petfolio.infinitus.fragmentpetlover.HomeFragment;
import com.petfolio.infinitus.petlover.AddYourPetActivity;
import com.petfolio.infinitus.petlover.PetLoverDashboardActivity;
import com.petfolio.infinitus.requestpojo.DoctorCheckStatusRequest;
import com.petfolio.infinitus.requestpojo.FBTokenUpdateRequest;
import com.petfolio.infinitus.responsepojo.DoctorCheckStatusResponse;
import com.petfolio.infinitus.responsepojo.FBTokenUpdateResponse;
import com.petfolio.infinitus.sessionmanager.SessionManager;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;


import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorDashboardActivity  extends DoctorNavigationDrawer implements Serializable, BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottom_navigation_view;

    private String TAG = "DoctorDashboardActivity";

    final Fragment homeFragment = new FragmentDoctorDashboard();
    /*final Fragment searchFragment = new SearchFragment();
    final Fragment myVehicleFragment = new MyVehicleFragment();
    final Fragment cartFragment = new CartFragment();
    final Fragment accountFragment = new AccountFragment();*/
    private String active_tag = "1";


    Fragment active = homeFragment;
    String tag;

    String fromactivity;
    private String userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dashboard);
        ButterKnife.bind(this);
        Log.w(TAG,"onCreate-->");

        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getProfileDetails();
        userid = user.get(SessionManager.KEY_ID);

        avi_indicator.setVisibility(View.GONE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            fromactivity = extras.getString("fromactivity");

        }

        tag = getIntent().getStringExtra("tag");
        if(tag != null){
            if(tag.equalsIgnoreCase("1")){
                active = homeFragment;
                bottom_navigation_view.setSelectedItemId(R.id.home);
                loadFragment(new HomeFragment());
            }else if(tag.equalsIgnoreCase("2")){
                //active = searchFragment;
                bottom_navigation_view.setSelectedItemId(R.id.search);
                // loadFragment(new SearchFragment());
            }else if(tag.equalsIgnoreCase("3")){
                // active = myVehicleFragment;
                bottom_navigation_view.setSelectedItemId(R.id.myvehicle);
                // loadFragment(new MyVehicleFragment());
            }else if(tag.equalsIgnoreCase("4")){
                //active = cartFragment;
                bottom_navigation_view.setSelectedItemId(R.id.cart);
                //loadFragment(new CartFragment());
            } else if(tag.equalsIgnoreCase("5")){
                //active = accountFragment;
                bottom_navigation_view.setSelectedItemId(R.id.account);
                //loadFragment(new AccountFragment());
            }
        }
        else{
            bottom_navigation_view.setSelectedItemId(R.id.home);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_container, active, active_tag);
            transaction.commit();
        }
        bottom_navigation_view.setOnNavigationItemSelectedListener(this);



    }



    private void loadFragment(Fragment fragment) {
        Bundle bundle = new Bundle();
        if(fromactivity != null){
            Log.w(TAG,"fromactivity loadFragment : "+fromactivity);

            if(fromactivity.equalsIgnoreCase("PopularServiceActivity")) {
                bundle.putString("fromactivity", fromactivity);

                // set Fragmentclass Arguments
                fragment.setArguments(bundle);
                Log.w(TAG,"fromactivity : "+fromactivity);
                // load fragment
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_container, fragment);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
            }
            else if(fromactivity.equalsIgnoreCase("SubServicesActivity")) {
                bundle.putString("fromactivity", fromactivity);


                // set Fragmentclass Arguments
                fragment.setArguments(bundle);
                Log.w(TAG,"fromactivity : "+fromactivity);

                // load fragment
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_container, fragment);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
            }
        }else {





            // set Fragmentclass Arguments
            fragment.setArguments(bundle);

            // load fragment
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_container, fragment);
            transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss();
        }
    }

    @Override
    public void onBackPressed() {
        Log.w(TAG,"tag : "+tag);
        if (bottom_navigation_view.getSelectedItemId() == R.id.home) {
            new android.app.AlertDialog.Builder(DoctorDashboardActivity.this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialog, id) -> DoctorDashboardActivity.this.finishAffinity())
                    .setNegativeButton("No", null)
                    .show();
        }
        else if(tag != null ){
            Log.w(TAG,"Else IF--->"+"fromactivity : "+fromactivity);
            if(fromactivity != null){
                if(fromactivity.equalsIgnoreCase("PopularServiceActivity")) {
                    Intent intent = new Intent(DoctorDashboardActivity.this, com.petfolio.infinitus.petlover.PetLoverDashboardActivity.class);
                    intent.putExtra("fromactivity", "PopularServiceActivity");

                    startActivity(intent);
                }

            }else{
                bottom_navigation_view.setSelectedItemId(R.id.home);
                // load fragment
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_container,new HomeFragment());
                transaction.commit();
            }


        }else{
            bottom_navigation_view.setSelectedItemId(R.id.home);
            // load fragment
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_container,new HomeFragment());
            transaction.commit();
        }
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_container,fragment);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                replaceFragment(new HomeFragment());
                break;
            case R.id.search:
                //replaceFragment(new SearchFragment());
                break;
            case R.id.myvehicle:
                //replaceFragment(new MyVehicleFragment());
                break;
            case R.id.cart:
                // replaceFragment(new CartFragment());
                break;
            case R.id.account:
                // replaceFragment(new AccountFragment());
                break;

            default:
                return  false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.w(TAG,"onActivityResult--->");
        Log.w(TAG,"resultCode---->"+resultCode+"requestCode: "+requestCode);

        Fragment fragment = Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.main_container));
        fragment.onActivityResult(requestCode,resultCode,data);
    }


    private void doctorCheckStatusResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<DoctorCheckStatusResponse> call = apiInterface.doctorCheckStatusResponseCall(RestUtils.getContentType(), doctorCheckStatusRequest());
        Log.w(TAG,"doctorCheckStatusResponseCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<DoctorCheckStatusResponse>() {
            @Override
            public void onResponse(@NonNull Call<DoctorCheckStatusResponse> call, @NonNull Response<DoctorCheckStatusResponse> response) {

                Log.w(TAG,"doctorCheckStatusResponseCall"+ "--->" + new Gson().toJson(response.body()));

                avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getCode() == 200){

                    }
                    else{
                        showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<DoctorCheckStatusResponse> call, @NonNull Throwable t) {

                avi_indicator.smoothToHide();
                Log.w(TAG,"doctorCheckStatusResponseCall"+"--->" + t.getMessage());
            }
        });

    }
    private DoctorCheckStatusRequest doctorCheckStatusRequest() {
        DoctorCheckStatusRequest doctorCheckStatusRequest = new DoctorCheckStatusRequest();
        doctorCheckStatusRequest.setUserId(userid);
        Log.w(TAG,"doctorCheckStatusRequest"+ "--->" + new Gson().toJson(doctorCheckStatusRequest));
        return doctorCheckStatusRequest;
    }
}