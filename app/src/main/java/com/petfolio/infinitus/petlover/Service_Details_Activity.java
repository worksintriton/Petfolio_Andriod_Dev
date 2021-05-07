package com.petfolio.infinitus.petlover;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.adapter.DoctorClinicSpecTypesListAdapter;
import com.petfolio.infinitus.adapter.PetLoverSOSAdapter;
import com.petfolio.infinitus.adapter.SPDetails_SpecTypesListAdapter;
import com.petfolio.infinitus.adapter.ViewPagerClinicDetailsAdapter;
import com.petfolio.infinitus.adapter.ViewPagerSPDetailsGalleryAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.interfaces.SoSCallListener;
import com.petfolio.infinitus.requestpojo.SPDetailsRequest;
import com.petfolio.infinitus.requestpojo.SPSpecificServiceDetailsRequest;
import com.petfolio.infinitus.responsepojo.DoctorDetailsResponse;
import com.petfolio.infinitus.responsepojo.PetLoverDashboardResponse;
import com.petfolio.infinitus.responsepojo.SPDetailsRepsonse;
import com.petfolio.infinitus.responsepojo.SPSpecificServiceDetailsResponse;
import com.petfolio.infinitus.sessionmanager.SessionManager;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.GridSpacingItemDecoration;
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

public class Service_Details_Activity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

    private String TAG = "Service_Details_Activity";

    // BottomSheetBehavior variable
    @SuppressWarnings("rawtypes")
    public BottomSheetBehavior bottomSheetBehavior;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_sp_companyname)
    TextView txt_sp_companyname;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rl_back)
    RelativeLayout rl_back;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_spname)
    TextView txt_sp_name;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.pager)
    ViewPager viewPager;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tabDots)
    TabLayout tabLayout;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_place)
    TextView txt_place;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_distance)
    TextView txt_distance;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_book_now)
    LinearLayout ll_book_now;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_speclist)
    RecyclerView rv_speclist;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_dr_consultationfees)
    TextView txt_dr_consultationfees;


//    @SuppressLint("NonConstantResourceId")
//    @BindView(R.id.txt_sp_spname)
//    TextView txt_sp_spname;
//
//    @SuppressLint("NonConstantResourceId")
//    @BindView(R.id.txt_serv_name)
//    TextView txt_serv_name;
//
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_selected_servicesname)
    TextView txt_selected_servicesname;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_selectedserviceimage)
    ImageView img_selectedserviceimage;

    SPDetails_SpecTypesListAdapter spDetails_specTypesListAdapter;

    private String active_tag;

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;




    private String userid;
    private String spid,catid;
    private List<SPDetailsRepsonse.DataBean.BusServiceGallBean> spServiceGalleryResponseList;
    private String from;
    private String spuserid;
    private String selectedServiceTitle;
    private String servicetime;
    private int serviceamount;


    private String serviceprovidingcompanyname = "";
    private String spprovidername = "";
    private int ratingcount;

    private int distance;
    private String location;

    private SupportMapFragment mapFragment;
    private double latitude;
    private double longitude;
    private GoogleMap mMap;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.hand_img1)
    ImageView hand_img1;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.hand_img2)
    ImageView hand_img2;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.hand_img3)
    ImageView hand_img3;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.hand_img4)
    ImageView hand_img4;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.hand_img5)
    ImageView hand_img5;

    List<SPDetailsRepsonse.DataBean.BusSpecListBean> specializationBeanList;

    String serv_name,selectedServiceImagepath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);
        ButterKnife.bind(this);
        Log.w(TAG,"onCreate");




        avi_indicator.setVisibility(View.GONE);

        rl_back.setOnClickListener(this);
        ll_book_now.setOnClickListener(this);


        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getProfileDetails();
        userid = user.get(SessionManager.KEY_ID);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            spid = extras.getString("spid");
            catid = extras.getString("catid");
            from = extras.getString("from");
        }

        Log.w(TAG," userid : "+userid+ " spid : "+spid+" catid : "+catid+" from : "+from);

        if(spid != null && userid != null) {
            if (new ConnectionDetector(Service_Details_Activity.this).isNetworkAvailable(Service_Details_Activity.this)) {
                SPDetailsRepsonseCall();
            }
        }

        setBottomSheet();


        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            mapFragment.getMapAsync(this);
        }


    }


    /**
     * method to setup the bottomsheet
     */
    private void setBottomSheet() {

        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottomSheetLayoutsp));

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);

        bottomSheetBehavior.setHideable(false);

        bottomSheetBehavior.setFitToContents(false);

        bottomSheetBehavior.setHalfExpandedRatio(0.7f);


        // Capturing the callbacks for bottom sheet
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        Log.w("Bottom Sheet Behaviour", "STATE_COLLAPSED");
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        Log.w("Bottom Sheet Behaviour", "STATE_DRAGGING");
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        Log.w("Bottom Sheet Behaviour", "STATE_EXPANDED");
                        //  bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        Log.w("Bottom Sheet Behaviour", "STATE_HIDDEN");
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        Log.w("Bottom Sheet Behaviour", "STATE_SETTLING");
                        break;
                    case BottomSheetBehavior.STATE_HALF_EXPANDED:
                        Log.w("Bottom Sheet Behaviour", "STATE_HALF_EXPANDED");
                        break;
                }


            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {


            }


        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                onBackPressed();
                break;
                case R.id.ll_book_now:
                gotoSPAvailableTimeActivity();
                break;

        }
    }
    public void callDirections(String tag){
        Intent intent = new Intent(getApplicationContext(), PetLoverDashboardActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);
        finish();
    }
    private void gotoSPAvailableTimeActivity() {
        Intent intent = new Intent(getApplicationContext(),PetServiceAppointment_Doctor_Date_Time_Activity.class);
        intent.putExtra("spid",spid);
        intent.putExtra("catid",catid);
        intent.putExtra("from",from);
        intent.putExtra("spuserid",spuserid);
        intent.putExtra("selectedServiceTitle",selectedServiceTitle);
        intent.putExtra("serviceamount",serviceamount);
        intent.putExtra("servicetime",servicetime);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),SelectedServiceActivity.class);
        intent.putExtra("spid",spid);
        intent.putExtra("catid",catid);
        intent.putExtra("from",from);
        startActivity(intent);
        finish();
    }
    @SuppressLint("LogNotTimber")
    private void SPDetailsRepsonseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<SPDetailsRepsonse> call = apiInterface.SPDetailsRepsonseCall(RestUtils.getContentType(), spDetailsRequest());
        Log.w(TAG,"SPDetailsRepsonseCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<SPDetailsRepsonse>() {
            @SuppressLint({"SetTextI18n", "LogNotTimber"})
            @Override
            public void onResponse(@NonNull Call<SPDetailsRepsonse> call, @NonNull Response<SPDetailsRepsonse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"SPDetailsRepsonse" + new Gson().toJson(response.body()));
                if (response.body() != null) {

                    if (200 == response.body().getCode()) {
                        if(response.body().getData().getBus_service_gall() != null) {
                            spServiceGalleryResponseList = response.body().getData().getBus_service_gall();
                        }
                        if(response.body().getData().getBussiness_name() != null) {
                             serviceprovidingcompanyname = response.body().getData().getBussiness_name();
                        }
                        if(response.body().getData().getBus_user_name() != null) {
                            spprovidername = response.body().getData().getBus_user_name();
                        }
                        if(response.body().getData().getRating() != 0) {
                             ratingcount = response.body().getData().getRating();
                        }
//                        if(response.body().getData().getComments() != 0) {
//                             comments = response.body().getData().getComments();
//                        }
                        if(response.body().getData().getDistance() != 0) {
                             distance = response.body().getData().getDistance();
                        }
                        if( response.body().getData().getSp_loc() != null) {
                             location = response.body().getData().getSp_loc();

                            latitude = response.body().getData().getSp_lat();

                            longitude = response.body().getData().getSp_long();

                            Log.w(TAG,"latitude"+ latitude );

                            Log.w(TAG,"longitude"+ longitude );

                            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                    .findFragmentById(R.id.map);
                            assert mapFragment != null;
                            mapFragment.getMapAsync(Service_Details_Activity.this);

                        }
                        if(response.body().getDetails().getImage_path() != null) {
                            selectedServiceImagepath = response.body().getDetails().getImage_path();
                        }



                        if(response.body().getDetails().getTitle() != null) {
                            selectedServiceTitle = response.body().getDetails().getTitle();
                        }
                        if(response.body().getDetails().getAmount() != 0) {
                            serviceamount = response.body().getDetails().getAmount();

                            txt_dr_consultationfees.setText("INR "+serviceamount);


                        }

                        if(response.body().getDetails().getTitle() != null) {

                            serv_name = response.body().getDetails().getTitle();
                        }

                        if(serv_name != null && !serv_name.isEmpty()){
                            txt_selected_servicesname.setText(serv_name);

                        }

                        if(selectedServiceImagepath != null && !selectedServiceImagepath.isEmpty()){
                            Glide.with(Service_Details_Activity.this)
                                    .load(selectedServiceImagepath)
                                    .into(img_selectedserviceimage);

                        }

                        else {

                            img_selectedserviceimage.setImageResource(R.drawable.services);
                        }



                        if(response.body().getDetails().getTime() != null) {
                            servicetime = response.body().getDetails().getTime();
                        }

                        if(response.body().getDetails().getTime() != null) {
                            servicetime = response.body().getDetails().getTime();
                        }
                        if(response.body().getData().getUser_id() != null) {
                            spuserid = response.body().getData().getUser_id();
                        }

                        if(serviceprovidingcompanyname != null && !serviceprovidingcompanyname.isEmpty()){
                            txt_sp_companyname.setText(serviceprovidingcompanyname);
                        }
                        if(spprovidername != null && !spprovidername.isEmpty()){
                            txt_sp_name.setText(spprovidername);
                        }
                        if(ratingcount != 0 ) {

                            if(ratingcount == 1){
                                hand_img1.setBackgroundResource(R.drawable.ic_logo_color);
                                hand_img2.setBackgroundResource(R.drawable.ic_logo_graycolor);
                                hand_img3.setBackgroundResource(R.drawable.ic_logo_graycolor);
                                hand_img4.setBackgroundResource(R.drawable.ic_logo_graycolor);
                                hand_img5.setBackgroundResource(R.drawable.ic_logo_graycolor);
                            } else if(ratingcount == 2){
                                hand_img1.setBackgroundResource(R.drawable.ic_logo_color);
                                hand_img2.setBackgroundResource(R.drawable.ic_logo_color);
                                hand_img3.setBackgroundResource(R.drawable.ic_logo_graycolor);
                                hand_img4.setBackgroundResource(R.drawable.ic_logo_graycolor);
                                hand_img5.setBackgroundResource(R.drawable.ic_logo_graycolor);
                            }else if(ratingcount == 3){
                                hand_img1.setBackgroundResource(R.drawable.ic_logo_color);
                                hand_img2.setBackgroundResource(R.drawable.ic_logo_color);
                                hand_img3.setBackgroundResource(R.drawable.ic_logo_color);
                                hand_img4.setBackgroundResource(R.drawable.ic_logo_graycolor);
                                hand_img5.setBackgroundResource(R.drawable.ic_logo_graycolor);
                            }else if(ratingcount == 4){
                                hand_img1.setBackgroundResource(R.drawable.ic_logo_color);
                                hand_img2.setBackgroundResource(R.drawable.ic_logo_color);
                                hand_img3.setBackgroundResource(R.drawable.ic_logo_color);
                                hand_img4.setBackgroundResource(R.drawable.ic_logo_color);
                                hand_img5.setBackgroundResource(R.drawable.ic_logo_graycolor);
                            } else if(ratingcount == 5){
                                hand_img1.setBackgroundResource(R.drawable.ic_logo_color);
                                hand_img2.setBackgroundResource(R.drawable.ic_logo_color);
                                hand_img3.setBackgroundResource(R.drawable.ic_logo_color);
                                hand_img4.setBackgroundResource(R.drawable.ic_logo_color);
                                hand_img5.setBackgroundResource(R.drawable.ic_logo_color);
                            }


                        }
                        if(location != null && !location.isEmpty()){
                            txt_place.setText(location);
                        }
                        if(distance != 0 ){
                            txt_distance.setText(distance+"");
                        }


                        if(spServiceGalleryResponseList != null && spServiceGalleryResponseList.size()>0){

                            for (int i = 0; i < spServiceGalleryResponseList.size(); i++) {
                                spServiceGalleryResponseList.get(i).getBus_service_gall();
                                Log.w(TAG, "RES" + ", " +  spServiceGalleryResponseList.get(i).getBus_service_gall());
                            }


                            viewpageData(spServiceGalleryResponseList);

                        }

                        if(response.body().getData().getBus_spec_list() != null&&response.body().getData().getBus_spec_list().size()>0){

                            // specializationBeanList = new ArrayList<>();

                            specializationBeanList=response.body().getData().getBus_spec_list();

                            Log.w(TAG,"SpecilaziationList : "+new Gson().toJson(specializationBeanList));

                            setSpecList(specializationBeanList);



                        }





                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<SPDetailsRepsonse> call,@NonNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"SPDetailsRepsonse flr"+ t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void viewpageData(List<SPDetailsRepsonse.DataBean.BusServiceGallBean> spServiceGalleryResponseList) {
        tabLayout.setupWithViewPager(viewPager, true);

        ViewPagerSPDetailsGalleryAdapter viewPagerSPDetailsGalleryAdapter = new ViewPagerSPDetailsGalleryAdapter(getApplicationContext(), spServiceGalleryResponseList);
        viewPager.setAdapter(viewPagerSPDetailsGalleryAdapter);
        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update =  new Runnable() {
            public void run() {
                if (currentPage == spServiceGalleryResponseList.size()) {
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
    @SuppressLint("LogNotTimber")
    private SPDetailsRequest spDetailsRequest() {
        /*
         * user_id : 5fd778437aa4cc1c6a1e5632
         * sp_id : 5fe1e675094d0471dabf9295
         * cata_id : 5fe185d61996f651f5133693
         */


        SPDetailsRequest spDetailsRequest = new SPDetailsRequest();
        spDetailsRequest.setUser_id(userid);
        spDetailsRequest.setSp_id(spid);
        spDetailsRequest.setCata_id(catid);
        Log.w(TAG,"spSpecificServiceDetailsRequest "+ new Gson().toJson(spDetailsRequest));
        return spDetailsRequest;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * In this case, we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device.
     * This method will only be triggered once the user has installed
     Google Play services and returned to the app.
     */

    @SuppressLint({"LongLogTag", "LogNotTimber"})
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        Log.w(TAG,"Map latitude"+ latitude );

        Log.w(TAG,"Map longitude"+ longitude );

        if(latitude!=0&&longitude!=0){

            LatLng currentLocation = new LatLng(latitude, longitude);

            mMap.addMarker(new
                    MarkerOptions().position(currentLocation));

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,15));
            // Zoom in, animating the camera.
            mMap.animateCamera(CameraUpdateFactory.zoomIn());
            // Zoom out to zoom level 10, animating with a duration of 2 seconds.
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

        }

    }



    private void setSpecList(List<SPDetailsRepsonse.DataBean.BusSpecListBean> specializationBeanList) {

        int spanCount = 2; // 3 columns
        int spacing = 0; // 50px
        boolean includeEdge = true;
        rv_speclist.setLayoutManager(new GridLayoutManager(this, 2));
        rv_speclist.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        rv_speclist.setItemAnimator(new DefaultItemAnimator());
        spDetails_specTypesListAdapter = new SPDetails_SpecTypesListAdapter(Service_Details_Activity.this, specializationBeanList);
        rv_speclist.setAdapter(spDetails_specTypesListAdapter);

    }

}