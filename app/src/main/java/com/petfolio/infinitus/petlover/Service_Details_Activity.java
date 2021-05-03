package com.petfolio.infinitus.petlover;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.adapter.PetLoverSOSAdapter;
import com.petfolio.infinitus.adapter.ViewPagerClinicDetailsAdapter;
import com.petfolio.infinitus.adapter.ViewPagerSPDetailsGalleryAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.interfaces.SoSCallListener;
import com.petfolio.infinitus.requestpojo.SPDetailsRequest;
import com.petfolio.infinitus.requestpojo.SPSpecificServiceDetailsRequest;
import com.petfolio.infinitus.responsepojo.PetLoverDashboardResponse;
import com.petfolio.infinitus.responsepojo.SPDetailsRepsonse;
import com.petfolio.infinitus.responsepojo.SPSpecificServiceDetailsResponse;
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

public class Service_Details_Activity extends AppCompatActivity implements View.OnClickListener, SoSCallListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private String TAG = "Service_Details_Activity";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_back)
    ImageView img_back;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_sp_companyname)
    TextView txt_sp_companyname;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_sp_name)
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
    @BindView(R.id.txt_review_count)
    TextView txt_review_count;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_star_rating)
    TextView txt_star_rating;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_selectedserviceimage)
    ImageView img_selectedserviceimage;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_selected_servicesname)
    TextView txt_selected_servicesname;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_book_now)
    Button btn_book_now;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.include_petlover_footer)
    View include_petlover_footer;

    BottomNavigationView bottom_navigation_view;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_sos)
    ImageView img_sos;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_notification)
    ImageView img_notification;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_cart)
    ImageView img_cart;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_profile)
    ImageView img_profile;


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

    private Dialog dialog;
    private static final int REQUEST_PHONE_CALL =1 ;
    private String sosPhonenumber;
    private String serviceprovidingcompanyname = "";
    private String spprovidername = "";
    private int ratingcount;
    private int comments;
    private int distance;
    private String location;
    private String selectedServiceImagepath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);


        ButterKnife.bind(this);
        Log.w(TAG,"onCreate");
        avi_indicator.setVisibility(View.GONE);

        img_back.setOnClickListener(this);
        btn_book_now.setOnClickListener(this);
        img_sos.setOnClickListener(this);
        img_notification.setOnClickListener(this);
        img_cart.setOnClickListener(this);
        img_profile.setOnClickListener(this);

        bottom_navigation_view = include_petlover_footer.findViewById(R.id.bottom_navigation_view);
        bottom_navigation_view.setItemIconTintList(null);
        bottom_navigation_view.setOnNavigationItemSelectedListener(this);
        bottom_navigation_view.getMenu().findItem(R.id.shop).setChecked(true);


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

        bottom_navigation_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:

                        active_tag = "1";
                        callDirections(active_tag);
                        break;
                    case R.id.shop:
                        active_tag = "2";
                        callDirections(active_tag);
                        break;


                    case R.id.services:
                        active_tag = "3";
                        callDirections(active_tag);
                        break;
                    case R.id.care:
                        active_tag = "4";
                        callDirections(active_tag);
                        break;
                    case R.id.community:
                        active_tag = "5";
                        callDirections(active_tag);
                        break;

                }
                return true;
            }

        });


    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                onBackPressed();
                break;
                case R.id.btn_book_now:
                gotoSPAvailableTimeActivity();
                break;
            case R.id.img_sos:
                goto_SOS();
                break;
            case R.id.img_profile:
                goto_Profile();
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
                        if(response.body().getData().getComments() != 0) {
                             comments = response.body().getData().getComments();
                        }
                        if(response.body().getData().getDistance() != 0) {
                             distance = response.body().getData().getDistance();
                        }
                        if( response.body().getData().getSp_loc() != null) {
                             location = response.body().getData().getSp_loc();
                        }
                        if(response.body().getDetails().getImage_path() != null) {
                            selectedServiceImagepath = response.body().getDetails().getImage_path();
                        }
                        if(response.body().getDetails().getTitle() != null) {
                            selectedServiceTitle = response.body().getDetails().getTitle();
                        }
                        if(response.body().getDetails().getAmount() != 0) {
                            serviceamount = response.body().getDetails().getAmount();
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
                            txt_sp_name.setText(serviceprovidingcompanyname);
                        }
                        if(ratingcount != 0 ){
                            txt_review_count.setText(ratingcount+"");
                        }if(comments != 0 ){
                            txt_star_rating.setText(comments+"");
                        }
                        if(location != null && !location.isEmpty()){
                            txt_place.setText(serviceprovidingcompanyname);
                        }
                        if(distance != 0 ){
                            txt_distance.setText(distance+" km away");
                        }


                        if(spServiceGalleryResponseList != null && spServiceGalleryResponseList.size()>0){

                            for (int i = 0; i < spServiceGalleryResponseList.size(); i++) {
                                spServiceGalleryResponseList.get(i).getBus_service_gall();
                                Log.w(TAG, "RES" + ", " +  spServiceGalleryResponseList.get(i).getBus_service_gall());
                            }


                            viewpageData(spServiceGalleryResponseList);

                        }

                        if (selectedServiceImagepath != null && !selectedServiceImagepath.isEmpty()) {

                            Glide.with(getApplicationContext())
                                    .load(selectedServiceImagepath)
                                    .into(img_selectedserviceimage);

                        }
                        else{
                            Glide.with(getApplicationContext())
                                    .load(APIClient.PROFILE_IMAGE_URL)
                                    .into(img_selectedserviceimage);

                        }
                        if(selectedServiceTitle != null && !selectedServiceTitle.isEmpty()){
                            txt_selected_servicesname.setText(selectedServiceTitle);
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

    private void goto_Profile() {
        Intent intent = new Intent(getApplicationContext(),PetLoverProfileScreenActivity.class);
        intent.putExtra("fromactivity",TAG);
        intent.putExtra("spid",spid);
        intent.putExtra("catid",catid);
        intent.putExtra("from",from);
        startActivity(intent);
    }
    private void goto_SOS() {
        showSOSAlert(APIClient.sosList);
    }
    private void showSOSAlert(List<PetLoverDashboardResponse.DataBean.SOSBean> sosList) {

        try {

            dialog = new Dialog(Service_Details_Activity.this);
            dialog.setContentView(R.layout.sos_popup_layout);
            RecyclerView rv_sosnumbers = (RecyclerView)dialog.findViewById(R.id.rv_sosnumbers);
            Button btn_call = (Button)dialog.findViewById(R.id.btn_call);
            TextView txt_no_records = (TextView)dialog.findViewById(R.id.txt_no_records);
            ImageView img_close = (ImageView)dialog.findViewById(R.id.img_close);
            img_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            if(sosList != null && sosList.size()>0){
                rv_sosnumbers.setVisibility(View.VISIBLE);
                btn_call.setVisibility(View.VISIBLE);
                txt_no_records.setVisibility(View.GONE);
                rv_sosnumbers.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                rv_sosnumbers.setItemAnimator(new DefaultItemAnimator());
                PetLoverSOSAdapter petLoverSOSAdapter = new PetLoverSOSAdapter(getApplicationContext(), sosList,this);
                rv_sosnumbers.setAdapter(petLoverSOSAdapter);
            }else{
                rv_sosnumbers.setVisibility(View.GONE);
                btn_call.setVisibility(View.GONE);
                txt_no_records.setVisibility(View.VISIBLE);
                txt_no_records.setText("No phone numbers");

            }

            btn_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(Service_Details_Activity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
                    }
                    else
                    {
                        gotoPhone();
                    }

                }
            });



            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();


        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }




    }
    private void gotoPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + sosPhonenumber));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(intent);
    }
    @Override
    public void soSCallListener(long phonenumber) {
        if(phonenumber != 0){
            sosPhonenumber = String.valueOf(phonenumber);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}