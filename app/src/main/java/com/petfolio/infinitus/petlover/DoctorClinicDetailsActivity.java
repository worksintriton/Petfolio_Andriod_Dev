package com.petfolio.infinitus.petlover;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import android.os.Handler;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
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
import com.petfolio.infinitus.adapter.DoctorClinicPetsHandledListAdapter;
import com.petfolio.infinitus.adapter.DoctorClinicSpecTypesListAdapter;
import com.petfolio.infinitus.adapter.SpecTypesListAdapter;
import com.petfolio.infinitus.adapter.ViewPagerClinicDetailsAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.doctor.DoctorBusinessInfoActivity;
import com.petfolio.infinitus.requestpojo.DoctorDetailsRequest;
import com.petfolio.infinitus.responsepojo.DoctorDetailsResponse;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.GridSpacingItemDecoration;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorClinicDetailsActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback
{

    // BottomSheetBehavior variable
    @SuppressWarnings("rawtypes")
    public BottomSheetBehavior bottomSheetBehavior;

    private static final String TAG = "DoctorClinicDetailsActivity";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.pager)
    ViewPager viewPager;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tabDots)
    TabLayout tabLayout;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_clinicname)
    TextView txt_clinicname;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_drname)
    TextView txt_drname;

    private SupportMapFragment mapFragment;
    private double latitude;
    private double longitude;
    private GoogleMap mMap;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_place)
    TextView txt_place;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_distance)
    TextView txt_distance;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_about_vet_desc)
    TextView txt_dr_desc;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_dr_experience)
    TextView txt_dr_experience;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_dr_consultationfees)
    TextView txt_dr_consultationfees;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_book_now)
    LinearLayout ll_book_now;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rl_back)
    RelativeLayout rl_back;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_fav)
    ImageView img_fav;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_speclist)
    RecyclerView rv_speclist;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_pet_hanldle)
    RecyclerView rv_pet_hanldle;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.cl_root)
    CoordinatorLayout cl_root;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.footerView)
    LinearLayout footerView;



    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;

    private String doctorid;
    private List<DoctorDetailsResponse.DataBean.ClinicPicBean> doctorclinicdetailsResponseList;
    private String doctorname;
    private Integer reviewcount;
    private Integer starcount;
    private String clinicname;
    private String distance;
    private String ClinicLocationname;
    private String fromactivity;
    private int amount;
    private String communicationtype;
    private int Doctor_exp;

    List<DoctorDetailsResponse.DataBean.SpecializationBean> specializationBeanList;

    List<DoctorDetailsResponse.DataBean.PetHandledBean> petHandledBeanList;

    DoctorClinicSpecTypesListAdapter doctorClinicSpecTypesListAdapter;

    DoctorClinicPetsHandledListAdapter doctorClinicPetsHandledListAdapter;

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

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.toolbar_header)
    Toolbar toolbar_header;


    @SuppressLint({"LongLogTag", "LogNotTimber"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_clinic_details);

        ButterKnife.bind(this);


        avi_indicator.setVisibility(View.GONE);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            doctorid = extras.getString("doctorid");
            doctorname = extras.getString("doctorname");
            distance = extras.getString("distance");
            fromactivity = extras.getString("fromactivity");
            Log.w(TAG,"Bundle "+" doctorid : "+doctorid+ "fromactivity : "+fromactivity);
        }

        ll_book_now.setOnClickListener(this);

        rl_back.setOnClickListener(this);

        avi_indicator.setVisibility(View.VISIBLE);

       // toolbar_header.setVisibility(View.GONE);


        //footerView.setVisibility(View.GONE);

        setBottomSheet();

        if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext())) {
            if(doctorid != null){
                doctorDetailsResponseCall();
            }

        }

        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            mapFragment.getMapAsync(this);
        }



    }



    /**
     * method to setup the bottomsheet
     */
    private void setBottomSheet() {

        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottomSheetLayouts));

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


    @SuppressLint({"LongLogTag", "LogNotTimber"})
    private void doctorDetailsResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<DoctorDetailsResponse> call = ApiService.doctorDetailsResponseCall(RestUtils.getContentType(),doctorDetailsRequest());
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<DoctorDetailsResponse>() {
            @SuppressLint({"SetTextI18n", "LogNotTimber"})
            @Override
            public void onResponse(@NonNull Call<DoctorDetailsResponse> call, @NonNull Response<DoctorDetailsResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"DoctorDetailsResponse"+ "--->" + new Gson().toJson(response.body()));


                if (response.body() != null) {
                    if(200 == response.body().getCode()){
                        if(response.body().getData().getClinic_pic() != null) {
                            doctorclinicdetailsResponseList = response.body().getData().getClinic_pic();
                        }
                        if(response.body().getData() != null) {
                            clinicname = response.body().getData().getClinic_name();
                            doctorname = response.body().getData().getDr_name();
                            reviewcount = response.body().getData().getReview_count();
                            starcount = response.body().getData().getStar_count();
                            amount = response.body().getData().getAmount();
                            Log.w(TAG, "amount : " + amount);
                            communicationtype = response.body().getData().getCommunication_type();
                            ClinicLocationname = response.body().getData().getClinic_loc();
                            Doctor_exp = response.body().getData().getDoctor_exp();
                            if(response.body().getData().getAmount() != 0){
                                txt_dr_consultationfees.setText("INR "+response.body().getData().getAmount());
                            }


                            avi_indicator.setVisibility(View.GONE);

//                            toolbar_header.setVisibility(View.VISIBLE);

                          /////////  scrollView.setVisibility(View.VISIBLE);

                          //  footerView.setVisibility(View.GONE);
                        }

                        if(Doctor_exp != 0) {
                            txt_dr_experience.setText(""+Doctor_exp);
                        }





                        if(clinicname != null){
                            txt_clinicname.setText(clinicname);
                        }
                        if(doctorname != null){
                            txt_drname.setText("DR "+doctorname);

                        }


                        if(starcount != null ){

                            if(starcount == 1){
                                hand_img1.setBackgroundResource(R.drawable.ic_logo_color);
                                hand_img2.setBackgroundResource(R.drawable.ic_logo_graycolor);
                                hand_img3.setBackgroundResource(R.drawable.ic_logo_graycolor);
                                hand_img4.setBackgroundResource(R.drawable.ic_logo_graycolor);
                                hand_img5.setBackgroundResource(R.drawable.ic_logo_graycolor);
                            } else if(starcount == 2){
                                hand_img1.setBackgroundResource(R.drawable.ic_logo_color);
                                hand_img2.setBackgroundResource(R.drawable.ic_logo_color);
                                hand_img3.setBackgroundResource(R.drawable.ic_logo_graycolor);
                                hand_img4.setBackgroundResource(R.drawable.ic_logo_graycolor);
                                hand_img5.setBackgroundResource(R.drawable.ic_logo_graycolor);
                            }else if(starcount == 3){
                                hand_img1.setBackgroundResource(R.drawable.ic_logo_color);
                                hand_img2.setBackgroundResource(R.drawable.ic_logo_color);
                                hand_img3.setBackgroundResource(R.drawable.ic_logo_color);
                                hand_img4.setBackgroundResource(R.drawable.ic_logo_graycolor);
                                hand_img5.setBackgroundResource(R.drawable.ic_logo_graycolor);
                            }else if(starcount == 4){
                                hand_img1.setBackgroundResource(R.drawable.ic_logo_color);
                                hand_img2.setBackgroundResource(R.drawable.ic_logo_color);
                                hand_img3.setBackgroundResource(R.drawable.ic_logo_color);
                                hand_img4.setBackgroundResource(R.drawable.ic_logo_color);
                                hand_img5.setBackgroundResource(R.drawable.ic_logo_graycolor);
                            } else if(starcount == 5){
                                hand_img1.setBackgroundResource(R.drawable.ic_logo_color);
                                hand_img2.setBackgroundResource(R.drawable.ic_logo_color);
                                hand_img3.setBackgroundResource(R.drawable.ic_logo_color);
                                hand_img4.setBackgroundResource(R.drawable.ic_logo_color);
                                hand_img5.setBackgroundResource(R.drawable.ic_logo_color);
                            }

                        }



                        if(ClinicLocationname != null ){
                            txt_place.setText(ClinicLocationname+"");

                            latitude = response.body().getData().getClinic_lat();

                            longitude = response.body().getData().getClinic_long();

                            Log.w(TAG,"latitude"+ latitude );

                            Log.w(TAG,"longitude"+ longitude );

                            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                    .findFragmentById(R.id.map);
                            assert mapFragment != null;
                            mapFragment.getMapAsync(DoctorClinicDetailsActivity.this);


                        }


                        if(distance != null && ClinicLocationname != null){
                            txt_distance.setText(""+distance);
                        }


                        Log.w(TAG,"Size"+doctorclinicdetailsResponseList.size());
                        Log.w(TAG,"doctorclinicdetailsResponseList : "+new Gson().toJson(doctorclinicdetailsResponseList));

                        if(doctorclinicdetailsResponseList != null && doctorclinicdetailsResponseList.size()>0){

                            for (int i = 0; i < doctorclinicdetailsResponseList.size(); i++) {
                                doctorclinicdetailsResponseList.get(i).getClinic_pic();
                                Log.w(TAG, "RES" + ", " +  doctorclinicdetailsResponseList.get(i).getClinic_pic());
                            }


                            viewpageData(doctorclinicdetailsResponseList);

                        }

                        Log.w(TAG," Descri : "+response.body().getData().getDescri());

                        if(response.body().getData().getDescri() != null){
                            txt_dr_desc.setText(response.body().getData().getDescri());

                        }


                        if(response.body().getData().getSpecialization() != null&&response.body().getData().getSpecialization().size()>0){

                           // specializationBeanList = new ArrayList<>();

                            specializationBeanList=response.body().getData().getSpecialization();

                            Log.w(TAG,"SpecilaziationList : "+new Gson().toJson(specializationBeanList));

                            setSpecList(specializationBeanList);



                        }

                        if(response.body().getData().getPet_handled() != null&&response.body().getData().getPet_handled().size()>0){

                            // specializationBeanList = new ArrayList<>();

                            petHandledBeanList=response.body().getData().getPet_handled();

                            Log.w(TAG,"petHandledBeanList : "+new Gson().toJson(petHandledBeanList));

                            setPetHandle(petHandledBeanList);



                        }


                    }


                }
            }

            @Override
            public void onFailure(@NonNull Call<DoctorDetailsResponse> call, @NonNull Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG,"DoctorDetailsResponse flr"+"--->" + t.getMessage());
            }
        });

    }



    @SuppressLint({"LogNotTimber", "LongLogTag"})
    private DoctorDetailsRequest doctorDetailsRequest() {
        DoctorDetailsRequest doctorDetailsRequest = new DoctorDetailsRequest();
        doctorDetailsRequest.setUser_id(doctorid);
        Log.w(TAG,"doctorDetailsRequest"+ "--->" + new Gson().toJson(doctorDetailsRequest));
        return doctorDetailsRequest;
    }
    private void viewpageData(List<DoctorDetailsResponse.DataBean.ClinicPicBean> doctorclinicdetailsResponseList) {
        tabLayout.setupWithViewPager(viewPager, true);

        ViewPagerClinicDetailsAdapter viewPagerClinicDetailsAdapter = new ViewPagerClinicDetailsAdapter(getApplicationContext(), doctorclinicdetailsResponseList);
        viewPager.setAdapter(viewPagerClinicDetailsAdapter);
        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update = () -> {
            if (currentPage == doctorclinicdetailsResponseList.size()) {
                currentPage = 0;
            }
            viewPager.setCurrentItem(currentPage++, false);
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(fromactivity != null && fromactivity.equalsIgnoreCase("PetCareFragment")){
            callDirections("4");
        }else {
            Intent intent = new Intent(DoctorClinicDetailsActivity.this,PetLoverDashboardActivity.class);
            startActivity(intent);

        }



    }

    public void callDirections(String tag){
        Intent intent = new Intent(DoctorClinicDetailsActivity.this,PetLoverDashboardActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);
        finish();

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_book_now:
                goto_PetAppointment_Doctor_Date_Time_Activity();
                break;
            case R.id.rl_back:
                onBackPressed();
                break;

        }

    }

    private  void goto_PetAppointment_Doctor_Date_Time_Activity(){
        Intent intent = new Intent(DoctorClinicDetailsActivity.this,PetAppointment_Doctor_Date_Time_Activity.class);
        intent.putExtra("doctorid",doctorid);
        intent.putExtra("fromactivity",fromactivity);
        intent.putExtra("amount",amount);
        intent.putExtra("communicationtype",communicationtype);
        startActivity(intent);
    }

    private void setSpecList(List<DoctorDetailsResponse.DataBean.SpecializationBean> specializationBeanList) {

        int spanCount = 2; // 3 columns
        int spacing = 0; // 50px
        boolean includeEdge = true;
        rv_speclist.setLayoutManager(new GridLayoutManager(this, 2));
        rv_speclist.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        rv_speclist.setItemAnimator(new DefaultItemAnimator());
        doctorClinicSpecTypesListAdapter = new DoctorClinicSpecTypesListAdapter(DoctorClinicDetailsActivity.this, specializationBeanList);
        rv_speclist.setAdapter(doctorClinicSpecTypesListAdapter);

    }

    private void setPetHandle(List<DoctorDetailsResponse.DataBean.PetHandledBean> petHandledBeanList) {

        int spanCount = 3; // 3 columns
        int spacing = 0; // 50px
        boolean includeEdge = true;
        rv_pet_hanldle.setLayoutManager(new GridLayoutManager(this, 3));
        rv_speclist.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        rv_pet_hanldle.setItemAnimator(new DefaultItemAnimator());
        doctorClinicPetsHandledListAdapter = new DoctorClinicPetsHandledListAdapter(DoctorClinicDetailsActivity.this, petHandledBeanList);
        rv_pet_hanldle.setAdapter(doctorClinicPetsHandledListAdapter);

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

}