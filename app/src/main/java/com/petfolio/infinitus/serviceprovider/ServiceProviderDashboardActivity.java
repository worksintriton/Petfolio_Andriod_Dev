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
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.petfolio.infinitus.R;

import com.petfolio.infinitus.fragmentserviceprovider.FragmentSPDashboard;
import com.petfolio.infinitus.sessionmanager.SessionManager;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServiceProviderDashboardActivity extends ServiceProviderNavigationDrawer implements Serializable, BottomNavigationView.OnNavigationItemSelectedListener {


    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottom_navigation_view;

    private String TAG = "ServiceProviderDashboardActivity";

    final Fragment homeFragment = new FragmentSPDashboard();

    private String active_tag = "1";


    Fragment active = homeFragment;
    String tag;

    String fromactivity;
    private String userid;
    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_dashboard);
        ButterKnife.bind(this);
        Log.w(TAG,"onCreate-->");

        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getProfileDetails();
        userid = user.get(SessionManager.KEY_ID);




        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            fromactivity = extras.getString("fromactivity");

        }

        tag = getIntent().getStringExtra("tag");
        if(tag != null){
            if(tag.equalsIgnoreCase("1")){
                active = homeFragment;
                bottom_navigation_view.setSelectedItemId(R.id.home);
                loadFragment(new FragmentSPDashboard());
            }else if(tag.equalsIgnoreCase("2")){
                //active = searchFragment;
                bottom_navigation_view.setSelectedItemId(R.id.shop);
                // loadFragment(new SearchFragment());
            }else if(tag.equalsIgnoreCase("3")){
                // active = myVehicleFragment;
                bottom_navigation_view.setSelectedItemId(R.id.community);
                // loadFragment(new MyVehicleFragment());
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
        if (bottom_navigation_view.getSelectedItemId() == R.id.shop) {
            showExitAppAlert();
           /* new android.app.AlertDialog.Builder(ServiceProviderDashboardActivity.this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialog, id) -> ServiceProviderDashboardActivity.this.finishAffinity())
                    .setNegativeButton("No", null)
                    .show();*/
        }
        else if(tag != null ){
            Log.w(TAG,"Else IF--->"+"fromactivity : "+fromactivity);
            if(fromactivity != null){

            }else{
                bottom_navigation_view.setSelectedItemId(R.id.shop);
                // load fragment
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_container,new FragmentSPDashboard());
                transaction.commit();
            }


        }else{
            bottom_navigation_view.setSelectedItemId(R.id.shop);
            // load fragment
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_container,new FragmentSPDashboard());
            transaction.commit();
        }
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_container,fragment);
        transaction.commit();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                //replaceFragment(new SearchFragment());
                break;
            case R.id.shop:
                //replaceFragment(new MyVehicleFragment());
                break;
            case R.id.community:
                // replaceFragment(new CartFragment());
                break;


            default:
                return  false;
        }
        return true;
    }

    @SuppressLint("LogNotTimber")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.w(TAG,"onActivityResult--->");
        Log.w(TAG,"resultCode---->"+resultCode+"requestCode: "+requestCode);

        Fragment fragment = Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.main_container));
        fragment.onActivityResult(requestCode,resultCode,data);
    }

    private void showExitAppAlert() {
        try {

            dialog = new Dialog(ServiceProviderDashboardActivity.this);
            dialog.setContentView(R.layout.alert_exit_layout);
            Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
            Button btn_exit = dialog.findViewById(R.id.btn_exit);

            btn_exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    ServiceProviderDashboardActivity.this.finishAffinity();
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





}