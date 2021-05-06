package com.petfolio.infinitus.doctor;



import android.annotation.SuppressLint;

import android.app.Dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;

import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;


import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.petfolio.infinitus.R;

import com.petfolio.infinitus.fragmentdoctor.DoctorShopFragment;
import com.petfolio.infinitus.fragmentdoctor.FragmentDoctorDashboard;

import com.petfolio.infinitus.sessionmanager.SessionManager;

import com.wang.avi.AVLoadingIndicatorView;


import java.io.Serializable;

import java.util.HashMap;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DoctorDashboardActivity  extends DoctorNavigationDrawer implements Serializable, BottomNavigationView.OnNavigationItemSelectedListener{

    private String TAG = "DoctorDashboardActivity";


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.include_doctor_footer)
    View include_doctor_footer;


    BottomNavigationView bottom_navigation_view;


    final Fragment fragmentDoctorDashboard = new FragmentDoctorDashboard();
    final Fragment doctorShopFragment = new DoctorShopFragment();

    public static String active_tag = "1";


    Fragment active = fragmentDoctorDashboard;
    String tag;

    String fromactivity;
    private int reviewcount;
    private String specialization;

    private static final int REQUEST_CHECK_SETTINGS_GPS = 0x1;
    private GoogleApiClient googleApiClient;
    Location mLastLocation;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private double latitude;
    private double longitude;
    public static String cityName;
    private Dialog dialog;
    private String userid;


    @SuppressLint("LogNotTimber")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dashboard);
        ButterKnife.bind(this);
        Log.w(TAG,"onCreate-->");

        bottom_navigation_view = include_doctor_footer.findViewById(R.id.bottom_navigation_view);
        bottom_navigation_view.setItemIconTintList(null);

        avi_indicator.setVisibility(View.GONE);


        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getProfileDetails();
        userid = user.get(SessionManager.KEY_ID);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            fromactivity = extras.getString("fromactivity");
            reviewcount = extras.getInt("reviewcount");
            specialization = extras.getString("specialization");


        }

        tag = getIntent().getStringExtra("tag");
        Log.w(TAG," tag : "+tag);
        if(tag != null){
            if(tag.equalsIgnoreCase("1")){
                active = fragmentDoctorDashboard;
                bottom_navigation_view.setSelectedItemId(R.id.home);
                loadFragment(new FragmentDoctorDashboard());
            }else if(tag.equalsIgnoreCase("2")){
                active = doctorShopFragment;
                bottom_navigation_view.setSelectedItemId(R.id.shop);
                loadFragment(new DoctorShopFragment());
            } else if(tag.equalsIgnoreCase("3")){
                bottom_navigation_view.setSelectedItemId(R.id.community);
            }
        }
        else{
            bottom_navigation_view.setSelectedItemId(R.id.home);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_schedule, active, active_tag);
            transaction.commitNowAllowingStateLoss();
        }
        bottom_navigation_view.setOnNavigationItemSelectedListener(this);
    }



    private void loadFragment(Fragment fragment) {
        Bundle bundle = new Bundle();
        if(fromactivity != null){
            Log.w(TAG,"fromactivity loadFragment : "+fromactivity);

            if(fromactivity.equalsIgnoreCase("FiltersActivity")) {
                bundle.putString("fromactivity", fromactivity);
                bundle.putString("specialization", specialization);
                bundle.putInt("reviewcount", reviewcount);
                // set Fragmentclass Arguments
                fragment.setArguments(bundle);
                Log.w(TAG,"fromactivity : "+fromactivity);
                // load fragment
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_schedule, fragment);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
            }
        }else {

            // set Fragmentclass Arguments
            fragment.setArguments(bundle);

            // load fragment
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_schedule, fragment);
            transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss();
        }
    }

    @Override
    public void onBackPressed() {
        Log.w(TAG,"tag : "+tag);
        if (bottom_navigation_view.getSelectedItemId() == R.id.home) {
            showExitAppAlert();
          /*  new android.app.AlertDialog.Builder(PetLoverDashboardActivity.this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialog, id) -> PetLoverDashboardActivity.this.finishAffinity())
                    .setNegativeButton("No", null)
                    .show();*/
        }
        else if(tag != null ){
            Log.w(TAG,"Else IF--->"+"fromactivity : "+fromactivity);
            if(fromactivity != null){


            }else{
                bottom_navigation_view.setSelectedItemId(R.id.home);
                // load fragment
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_schedule,new FragmentDoctorDashboard());
                transaction.commitNowAllowingStateLoss();
            }


        }else{
            bottom_navigation_view.setSelectedItemId(R.id.home);
            // load fragment
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_schedule,new FragmentDoctorDashboard());
            transaction.commitNowAllowingStateLoss();
        }
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_schedule,fragment);
        transaction.commitNowAllowingStateLoss();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                active_tag = "1";
                replaceFragment(new FragmentDoctorDashboard());
                break;
            case R.id.shop:
                active_tag = "2";
                replaceFragment(new DoctorShopFragment());
                break;

            case R.id.community:
                showComingSoonAlert();
                active_tag = "3";
                break;

            default:
                return  false;
        }
        return true;
    }




    private void showExitAppAlert() {
        try {

            dialog = new Dialog(DoctorDashboardActivity.this);
            dialog.setContentView(R.layout.alert_exit_layout);
            Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
            Button btn_exit = dialog.findViewById(R.id.btn_exit);

            btn_exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    DoctorDashboardActivity.this.finishAffinity();
                }
            });
            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }




    }

    private void showComingSoonAlert() {

        try {

            Dialog dialog = new Dialog(DoctorDashboardActivity.this);
            dialog.setContentView(R.layout.alert_comingsoon_layout);
            dialog.setCanceledOnTouchOutside(false);

            ImageView img_close = dialog.findViewById(R.id.img_close);
            img_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();

                }
            });
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }




    }





}