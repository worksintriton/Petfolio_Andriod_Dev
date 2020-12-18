package com.petfolio.infinitus.serviceprovider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.fragmentpetlover.PetHomeFragment;
import com.petfolio.infinitus.fragmentserviceprovider.ServiceProviderShopFragment;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.Serializable;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServiceProviderDashboardActivity  extends ServiceProviderNavigationDrawer implements Serializable, BottomNavigationView.OnNavigationItemSelectedListener {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottom_navigation_view;

    private final String TAG = "ServiceProviderDashboardActivity";

    final Fragment serviceProviderShopFragment = new ServiceProviderShopFragment();


    Fragment active = serviceProviderShopFragment;
    String tag;
     String fromactivity;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_dashboard);
        ButterKnife.bind(this);
        Log.w(TAG,"onCreate-->");

        avi_indicator.setVisibility(View.GONE);

        tag = getIntent().getStringExtra("tag");
        if(tag != null){
            if(tag.equalsIgnoreCase("1")){
                active = serviceProviderShopFragment;
                bottom_navigation_view.setSelectedItemId(R.id.shop);
                loadFragment(new ServiceProviderShopFragment());
            }else if(tag.equalsIgnoreCase("2")){
                bottom_navigation_view.setSelectedItemId(R.id.feed);
            }else if(tag.equalsIgnoreCase("3")){
                bottom_navigation_view.setSelectedItemId(R.id.market);
            }
        }else{
            bottom_navigation_view.setSelectedItemId(R.id.home);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            String active_tag = "1";
            transaction.replace(R.id.main_container, active, active_tag);
            transaction.commit();
        }
        bottom_navigation_view.setOnNavigationItemSelectedListener(this);

    }



    @SuppressLint("LongLogTag")
    private void loadFragment(Fragment fragment) {
        Bundle bundle = new Bundle();
        if(fromactivity != null){
            Log.w(TAG,"fromactivity loadFragment : "+fromactivity);

            if(fromactivity.equalsIgnoreCase("FiltersActivity")) {
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
    @SuppressLint("LongLogTag")
    @Override
    public void onBackPressed() {
        Log.w(TAG,"tag : "+tag);
        if (bottom_navigation_view.getSelectedItemId() == R.id.shop) {
            new android.app.AlertDialog.Builder(ServiceProviderDashboardActivity.this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialog, id) -> ServiceProviderDashboardActivity.this.finishAffinity())
                    .setNegativeButton("No", null)
                    .show();
        }
        else if(tag != null ){
            Log.w(TAG,"Else IF--->"+"fromactivity : "+fromactivity);
            if(fromactivity == null){
                bottom_navigation_view.setSelectedItemId(R.id.shop);
                // load fragment
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_container,new PetHomeFragment());
                transaction.commit();
            }


        }else{
            bottom_navigation_view.setSelectedItemId(R.id.shop);
            // load fragment
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_container,new PetHomeFragment());
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
            case R.id.shop:
                replaceFragment(new ServiceProviderShopFragment());
                break;
            case R.id.feed:
                break;
            case R.id.market:
                break;


            default:
                return  false;
        }
        return true;
    }
    @SuppressLint("LongLogTag")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.w(TAG,"onActivityResult--->");
        Log.w(TAG,"resultCode---->"+resultCode+"requestCode: "+requestCode);

        Fragment fragment = Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.main_container));
        fragment.onActivityResult(requestCode,resultCode,data);
    }
}