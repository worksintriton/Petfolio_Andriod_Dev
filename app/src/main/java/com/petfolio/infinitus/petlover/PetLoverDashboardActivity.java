package com.petfolio.infinitus.petlover;




import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;


import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;

import android.provider.Settings;
import android.util.Log;


import android.view.View;


import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.activity.location.PickUpLocationAllowActivity;
import com.petfolio.infinitus.activity.location.PickUpLocationDenyActivity;
import com.petfolio.infinitus.adapter.PetLoverDoctorAdapter;
import com.petfolio.infinitus.adapter.PetLoverProductsAdapter;
import com.petfolio.infinitus.adapter.PetLoverServicesAdapter;
import com.petfolio.infinitus.adapter.ViewPagerDashboardAdapter;
import com.petfolio.infinitus.api.API;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.requestpojo.PetLoverDashboardRequest;
import com.petfolio.infinitus.responsepojo.GetAddressResultResponse;
import com.petfolio.infinitus.responsepojo.PetLoverDashboardResponse;
import com.petfolio.infinitus.service.GPSTracker;
import com.petfolio.infinitus.sessionmanager.SessionManager;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PetLoverDashboardActivity extends NavigationDrawer implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

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
    private Dialog alertDialog;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    GoogleApiClient mGoogleApiClient;
    private String AddressLine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w(TAG, "OnCreate-->");
        setContentView(R.layout.activity_pet_lover_dashboard);
        ButterKnife.bind(this);
        avi_indicator.setVisibility(View.GONE);

        showLocationAlert();

        SessionManager sessionManager = new SessionManager(PetLoverDashboardActivity.this);
        HashMap<String, String> user = sessionManager.getProfileDetails();
        userid = user.get(SessionManager.KEY_ID);





        toolbar.setVisibility(View.VISIBLE);

        txt_seemore_doctors.setOnClickListener(this);
        txt_seemore_services.setOnClickListener(this);
        txt_seemore_products.setOnClickListener(this);



        checkLocationPermission();
        checkLocation();

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
                         showErrorLoading(response.body().getMessage());
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
        petLoverDashboardRequest.setLat(latitude);
        petLoverDashboardRequest.setLongX(longitude);
        petLoverDashboardRequest.setUser_type(1);
        petLoverDashboardRequest.setAddress(AddressLine);


        Log.w(TAG,"petLoverDashboardRequest"+ new Gson().toJson(petLoverDashboardRequest));
        return petLoverDashboardRequest;
    }


    private void showLocationAlert() {

        try {

            Dialog dialog = new Dialog(PetLoverDashboardActivity.this);
            dialog.setContentView(R.layout.alert_location_allow_deny_layout);
            dialog.setCanceledOnTouchOutside(false);
            Button btn_allow = dialog.findViewById(R.id.btn_allow);
            Button btn_deny = dialog.findViewById(R.id.btn_deny);
            btn_deny.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showLocationDenyAlert();
                    dialog.dismiss();

                }
            });
            btn_allow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(PetLoverDashboardActivity.this, PickUpLocationAllowActivity.class));
                    dialog.dismiss();

                }
            });
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }




    }
    private void showLocationDenyAlert() {

        try {

           Dialog dialog = new Dialog(PetLoverDashboardActivity.this);
            dialog.setContentView(R.layout.alert_location_deny_layout);
            dialog.setCanceledOnTouchOutside(false);

            ImageView img_close = dialog.findViewById(R.id.img_close);
            img_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(PetLoverDashboardActivity.this, PickUpLocationDenyActivity.class));
                    dialog.dismiss();

                }
            });
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }




    }

    public void showErrorLoading(String errormesage){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(errormesage);
        alertDialogBuilder.setPositiveButton("ok",
                (arg0, arg1) -> hideLoading());




        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public void hideLoading(){
        try {
            alertDialog.dismiss();
        }catch (Exception ignored){

        }
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    if (mGoogleApiClient == null) {
                        buildGoogleApiClient();
                    }

                }
            } else {
                Toast.makeText(this, "permission denied",
                        Toast.LENGTH_LONG).show();
            }
        }
    }


    private void checkLocation(){
        try{
            LocationManager lm = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
            boolean gps_enabled = false;
            boolean network_enabled = false;

            try {
                if (lm != null) {
                    gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                }
            } catch(Exception ignored) {}

            try {
                if (lm != null) {
                    network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                }
            } catch(Exception ignored) {}

            if(!gps_enabled && !network_enabled) {

                if (lm != null && !lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    showSettingsAlert();
                }

            }else{
                getLatandLong();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private void getLatandLong(){
        try{
            if (ContextCompat.checkSelfPermission(PetLoverDashboardActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(PetLoverDashboardActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(PetLoverDashboardActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            }
            else {
                GPSTracker gps = new GPSTracker(PetLoverDashboardActivity.this);

                // Check if GPS enabled
                if (gps.canGetLocation()) {
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();

                    Log.w(TAG,"getLatandLong--->"+"latitude" + " " + latitude+"longitude" + " " + longitude);

                   /* String country = gps.getCountryName(MapsActivity.this);
                    String city = gps.getLocality(MapsActivity.this);
                    String postalCode = gps.getPostalCode(MapsActivity.this);
                    String addressLine = gps.getAddressLine(MapsActivity.this);
                    Log.w(TAG,"country : "+country+" "+"city : "+" "+city+"postalCode : "+" "+postalCode+" "+"addressLine :"+" "+addressLine);*/

                    //  Toasty.warning(getApplicationContext(), "latitude :"+latitude+"longitude :"+longitude+"address :"+addressLine, Toast.LENGTH_SHORT, true).show();


                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(PetLoverDashboardActivity.this);

        // Setting DialogHelp Title
        alertDialog.setTitle("GPS is settings");

        // Setting DialogHelp Message
        alertDialog
                .setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        // Showing Alert Message
        alertDialog.show();
    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void getAddressResultResponse(LatLng latLng) {
        Log.w(TAG,"GetAddressResultResponse-->"+latLng);
        //avi_indicator.setVisibility(View.VISIBLE);
        // avi_indicator.smoothToShow();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API service = retrofit.create(API.class);
        String strlatlng = String.valueOf(latLng);
        Log.w(TAG,"getAddressResultResponse strlatlng-->"+strlatlng);
        String newString = strlatlng.replace("lat/lng:", "");
        Log.w(TAG,"getAddressResultResponse latlng=="+newString);

        String latlngs = newString.trim().replaceAll("\\(", "").replaceAll("\\)","").trim();
        Log.w(TAG,"getAddressResultResponse latlngs=="+latlngs);



        String key = API.MAP_KEY;
        service.getAddressResultResponseCall(latlngs, key).enqueue(new Callback<GetAddressResultResponse>() {
            @Override
            public void onResponse(@NotNull Call<GetAddressResultResponse> call, @NotNull Response<GetAddressResultResponse> response) {
                //avi_indicator.smoothToHide();
                Log.w(TAG,"url  :%s"+ call.request().url().toString());


                Log.w(TAG,"GetAddressResultResponse" + new Gson().toJson(response.body()));


                if(response.body() != null) {
                    String currentplacename = null;
                    String compundcode = null;

                    if(response.body().getPlus_code().getCompound_code() != null){
                        compundcode = response.body().getPlus_code().getCompound_code();
                    }
                    if(compundcode != null) {
                        String[] separated = compundcode.split(",");
                        String placesname = separated[0];
                        String[] splitData = placesname.split("\\s", 2);
                        String code = splitData[0];
                        currentplacename = splitData[1];
                        Log.w(TAG, "code-->" + code + "currentplacename : " + currentplacename);
                    }


                    String localityName = null;
                    String cityName = null;
                    String sublocalityName = null;
                    String postalCode = null;


                    List<GetAddressResultResponse.ResultsBean> getAddressResultResponseList;
                    getAddressResultResponseList = response.body().getResults();
                    if (getAddressResultResponseList.size() > 0) {
                        AddressLine = getAddressResultResponseList.get(0).getFormatted_address();
                        Log.w(TAG, "FormateedAddress-->" + AddressLine);

                    }
                    List<GetAddressResultResponse.ResultsBean.AddressComponentsBean> addressComponentsBeanList = response.body().getResults().get(0).getAddress_components();
                    if(addressComponentsBeanList != null) {
                        if (addressComponentsBeanList.size() > 0) {
                            for (int i = 0; i < addressComponentsBeanList.size(); i++) {
                                Log.w(TAG, "addressComponentsBeanList size : " + addressComponentsBeanList.size());

                                for (int j = 0; j < addressComponentsBeanList.get(i).getTypes().size(); j++) {
                                    Log.w(TAG, "getTypes size : " + addressComponentsBeanList.get(i).getTypes().size());

                                    Log.w(TAG, "TYPES-->" + addressComponentsBeanList.get(i).getTypes());
                                    List<String> typesList = addressComponentsBeanList.get(i).getTypes();

                                    if (typesList.contains("postal_code")) {
                                        postalCode = addressComponentsBeanList.get(i).getShort_name();
                                       String PostalCode = postalCode;
                                        Log.w(TAG, "Postal Short name ---->" + postalCode);

                                    }
                                    if (typesList.contains("locality")) {
                                       String CityName = addressComponentsBeanList.get(i).getLong_name();
                                        localityName = addressComponentsBeanList.get(i).getShort_name();
                                        Log.w(TAG, "Locality Short name ---->" + localityName);
                                        Log.w(TAG, "Locality City  short name ---->" + cityName);


                                    }

                                    if (typesList.contains("administrative_area_level_2")) {
                                        cityName = addressComponentsBeanList.get(i).getShort_name();
                                        //  CityName = cityName;
                                        Log.w(TAG, "City  short name ---->" + cityName);

                                    }
                                    if (typesList.contains("sublocality_level_1")) {
                                        sublocalityName = addressComponentsBeanList.get(i).getShort_name();
                                        Log.w(TAG, "sublocality_level_1  short name ---->" + cityName);

                                    }

                                }

                            }



                        }
                    }
                }


            }

            @Override
            public void onFailure(@NotNull Call<GetAddressResultResponse> call, @NotNull Throwable t) {
                //avi_indicator.smoothToHide();
                t.printStackTrace();
            }
        });
    }

}








