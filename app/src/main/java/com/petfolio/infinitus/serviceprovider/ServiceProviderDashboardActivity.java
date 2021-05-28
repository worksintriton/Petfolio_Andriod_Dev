package com.petfolio.infinitus.serviceprovider;

import android.annotation.SuppressLint;

import android.app.Dialog;

import android.content.Intent;
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
import android.widget.TextView;


import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.petfolio.infinitus.R;

import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.doctor.ManageAddressDoctorActivity;

import com.petfolio.infinitus.fragmentserviceprovider.FragmentSPDashboard;
import com.petfolio.infinitus.fragmentserviceprovider.SPShopFragment;
import com.petfolio.infinitus.requestpojo.ShippingAddressFetchByUserIDRequest;
import com.petfolio.infinitus.responsepojo.ShippingAddressFetchByUserIDResponse;
import com.petfolio.infinitus.sessionmanager.SessionManager;

import com.petfolio.infinitus.utils.ConnectionDetector;
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


public class ServiceProviderDashboardActivity  extends ServiceProviderNavigationDrawer implements Serializable, BottomNavigationView.OnNavigationItemSelectedListener {

    private String TAG = "ServiceProviderDashboardActivity";


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.include_doctor_footer)
    View include_doctor_footer;


    BottomNavigationView bottom_navigation_view;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_location)
    TextView txt_location;


    final Fragment fragmentSPDashboard = new FragmentSPDashboard();
    final Fragment sPShopFragment = new SPShopFragment();

    public static String active_tag = "1";


    Fragment active = fragmentSPDashboard;
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
        setContentView(R.layout.activity_service_provider_dashboard);
        ButterKnife.bind(this);
        Log.w(TAG, "onCreate-->");

        bottom_navigation_view = include_doctor_footer.findViewById(R.id.bottom_navigation_view);
        bottom_navigation_view.setItemIconTintList(null);
        bottom_navigation_view.getMenu().findItem(R.id.home).setChecked(true);
        bottom_navigation_view.setOnNavigationItemSelectedListener(this);



        avi_indicator.setVisibility(View.GONE);


        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getProfileDetails();
        userid = user.get(SessionManager.KEY_ID);

        if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext())) {
            shippingAddressresponseCall();
        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            fromactivity = extras.getString("fromactivity");
            reviewcount = extras.getInt("reviewcount");
            specialization = extras.getString("specialization");


        }

        tag = getIntent().getStringExtra("tag");
        Log.w(TAG, " tag : " + tag);
        if (tag != null) {
            if (tag.equalsIgnoreCase("1")) {
                active = fragmentSPDashboard;
                bottom_navigation_view.getMenu().findItem(R.id.home).setChecked(true);
                loadFragment(new FragmentSPDashboard());
            } else if (tag.equalsIgnoreCase("2")) {
                active = sPShopFragment;
                bottom_navigation_view.getMenu().findItem(R.id.shop).setChecked(true);
                loadFragment(new SPShopFragment());
            } else if (tag.equalsIgnoreCase("3")) {
                bottom_navigation_view.getMenu().findItem(R.id.community).setChecked(true);

            }
        } else {
            bottom_navigation_view.getMenu().findItem(R.id.home).setChecked(true);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_schedule, active, active_tag);
            transaction.commitNowAllowingStateLoss();
        }

        txt_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ManageAddressSPActivity.class));
            }
        });
    }


    private void loadFragment(Fragment fragment) {
        Bundle bundle = new Bundle();
        if (fromactivity != null) {
            Log.w(TAG, "fromactivity loadFragment : " + fromactivity);

            if (fromactivity.equalsIgnoreCase("FiltersActivity")) {
                bundle.putString("fromactivity", fromactivity);
                bundle.putString("specialization", specialization);
                bundle.putInt("reviewcount", reviewcount);
                // set Fragmentclass Arguments
                fragment.setArguments(bundle);
                Log.w(TAG, "fromactivity : " + fromactivity);
                // load fragment
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_schedule, fragment);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
            }
        } else {

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
        Log.w(TAG, "tag : " + tag);
        if (bottom_navigation_view.getSelectedItemId() == R.id.home) {
            showExitAppAlert();
          /*  new android.app.AlertDialog.Builder(PetLoverDashboardActivity.this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialog, id) -> PetLoverDashboardActivity.this.finishAffinity())
                    .setNegativeButton("No", null)
                    .show();*/
        } else if (tag != null) {
            Log.w(TAG, "Else IF--->" + "fromactivity : " + fromactivity);
            if (fromactivity != null) {


            } else {
                bottom_navigation_view.setSelectedItemId(R.id.home);
                // load fragment
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_schedule, new FragmentSPDashboard());
                transaction.commitNowAllowingStateLoss();
            }


        } else {
            bottom_navigation_view.setSelectedItemId(R.id.home);
            // load fragment
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_schedule, new FragmentSPDashboard());
            transaction.commitNowAllowingStateLoss();
        }

    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_schedule, fragment);
        transaction.commitNowAllowingStateLoss();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                active_tag = "1";
                replaceFragment(new FragmentSPDashboard());
                break;
            case R.id.shop:
                active_tag = "2";
                replaceFragment(new SPShopFragment());
                break;

            case R.id.community:
                showComingSoonAlert();
                active_tag = "3";
                break;

            default:
                return false;
        }
        return true;
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

    private void showComingSoonAlert() {

        try {

            Dialog dialog = new Dialog(ServiceProviderDashboardActivity.this);
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

    @SuppressLint("LogNotTimber")
    private void shippingAddressresponseCall() {
        /* avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();*/
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<ShippingAddressFetchByUserIDResponse> call = apiInterface.fetch_shipp_addr_ResponseCall(RestUtils.getContentType(), shippingAddressFetchByUserIDRequest());
        Log.w(TAG, "ShippingAddressFetchByUserIDResponse url  :%s" + " " + call.request().url().toString());
        call.enqueue(new Callback<ShippingAddressFetchByUserIDResponse>() {
            @Override
            public void onResponse(@NonNull Call<ShippingAddressFetchByUserIDResponse> call, @NonNull Response<ShippingAddressFetchByUserIDResponse> response) {
                Log.w(TAG, "ShippingAddressFetchByUserIDResponse" + "--->" + new Gson().toJson(response.body()));
                //  avi_indicator.smoothToHide();
                if (response.body() != null) {
                    if (response.body().getCode() == 200) {
                        if (response.body().getData() != null) {
                            ShippingAddressFetchByUserIDResponse.DataBean dataBeanList = response.body().getData();

                            if (dataBeanList != null) {
                                if (dataBeanList.isDefault_status()) {
                                    Log.w(TAG, "true-->");
                                    String city = dataBeanList.getLocation_city();
                                    if (city != null) {
                                        txt_location.setText(city);
                                    }

                                }


                            }

                        }
                    }


                }


            }

            @Override
            public void onFailure(@NonNull Call<ShippingAddressFetchByUserIDResponse> call, @NonNull Throwable t) {

                //  avi_indicator.smoothToHide();
                Log.w(TAG, "ShippingAddressFetchByUserIDResponse flr" + "--->" + t.getMessage());
            }
        });


    }

    @SuppressLint("LogNotTimber")
    private ShippingAddressFetchByUserIDRequest shippingAddressFetchByUserIDRequest() {
        /*
         * user_id : 6048589d0b3a487571a1c567
         */

        ShippingAddressFetchByUserIDRequest shippingAddressFetchByUserIDRequest = new ShippingAddressFetchByUserIDRequest();
        shippingAddressFetchByUserIDRequest.setUser_id(userid);

        Log.w(TAG, "shippingAddressFetchByUserIDRequest" + "--->" + new Gson().toJson(shippingAddressFetchByUserIDRequest));
        return shippingAddressFetchByUserIDRequest;
    }


}