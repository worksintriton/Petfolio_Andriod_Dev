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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.adapter.PetLoverSOSAdapter;
import com.petfolio.infinitus.adapter.ViewPagerClinicDetailsAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.interfaces.SoSCallListener;
import com.petfolio.infinitus.requestpojo.DoctorDetailsRequest;
import com.petfolio.infinitus.responsepojo.DoctorDetailsResponse;
import com.petfolio.infinitus.responsepojo.PetLoverDashboardResponse;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorClinicDetailsActivity extends AppCompatActivity implements View.OnClickListener, SoSCallListener {

    private static  String TAG = "DoctorClinicDetailsActivity";

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

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_dr_eduname)
    TextView txt_dr_eduname;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_dr_specialization)
    TextView txt_dr_specialization;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_review_count)
    TextView txt_review_count;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_star_rating)
    TextView txt_star_rating;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_place)
    TextView txt_place;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_distance)
    TextView txt_distance;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_dr_desc)
    TextView txt_dr_desc;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_dr_experience)
    TextView txt_dr_experience;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_dr_consultationfees)
    TextView txt_dr_consultationfees;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_book_now)
    Button btn_book_now;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_back)
    ImageView img_back;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.bottom_navigation_view)
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



    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;

    private String doctorid;
    private List<DoctorDetailsResponse.DataBean.ClinicPicBean> doctorclinicdetailsResponseList;
    private String concatenatedStarNames = "";
    private String doctorname;
    private Integer reviewcount;
    private Integer starcount;
    private String clinicname;
    private String distance;
    private String ClinicLocationname;
    private String fromactivity;
    private int amount;
    private String communicationtype;
    private String active_tag;
    private Dialog dialog;
    private static final int REQUEST_PHONE_CALL =1 ;
    private String sosPhonenumber;
    private String education = "";
    private int Doctor_exp;

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

        btn_book_now.setOnClickListener(this);
        img_back.setOnClickListener(this);
        img_sos.setOnClickListener(this);
        img_notification.setOnClickListener(this);
        img_cart.setOnClickListener(this);
        img_profile.setOnClickListener(this);



        if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext())) {
            if(doctorid != null){
                doctorDetailsResponseCall();
            }

        }

        if(fromactivity != null && fromactivity.equalsIgnoreCase("PetCareFragment")){
            bottom_navigation_view.setSelectedItemId(R.id.care);
        }


        bottom_navigation_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        //active = homeFragment;
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
                                txt_dr_consultationfees.setText("\u20B9 "+response.body().getData().getAmount());
                            }
                        }

                        if(Doctor_exp != 0) {
                            txt_dr_experience.setText(Doctor_exp+" Years");
                        }





                        if(clinicname != null){
                            txt_clinicname.setText(clinicname);
                        }
                        if(doctorname != null){
                            txt_drname.setText(doctorname);

                        }

                        if(reviewcount != null){
                            txt_review_count.setText(reviewcount+"");
                        }
                        if(starcount != null ){
                            txt_star_rating.setText(starcount+"");
                        }
                        if(ClinicLocationname != null ){
                            txt_place.setText(ClinicLocationname+"");
                        }


                        if(distance != null && ClinicLocationname != null){
                            txt_distance.setText(distance+" KM.");
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
                       if(response.body().getData().getSpecialization() != null){
                           for (int i = 0; i < response.body().getData().getSpecialization().size(); i++) {
                               concatenatedStarNames += response.body().getData().getSpecialization().get(i).getSpecialization();
                               if (i < response.body().getData().getSpecialization().size() - 1) concatenatedStarNames += ", ";
                           }
                           txt_dr_specialization.setText(concatenatedStarNames);

                       }if(response.body().getData().getEducation_details() != null){
                           for (int i = 0; i < response.body().getData().getEducation_details().size(); i++) {
                               education += response.body().getData().getEducation_details().get(i).getEducation();
                               if (i < response.body().getData().getEducation_details().size() - 1) education += ", ";
                           }
                           txt_dr_eduname.setText(education);

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
    @SuppressLint("LogNotTimber")
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
        final Runnable Update =  new Runnable() {
            public void run() {
                if (currentPage == doctorclinicdetailsResponseList.size()) {
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
            case R.id.btn_book_now:
                goto_PetAppointment_Doctor_Date_Time_Activity();
                break;
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.img_sos:
                goto_SOS();
                break;
            case R.id.img_profile:
                goto_Profile();
                break;
        }

    }

    private void goto_Profile() {
        Intent intent = new Intent(getApplicationContext(),PetLoverProfileScreenActivity.class);
        intent.putExtra("fromactivity",TAG);
        intent.putExtra("doctorid",doctorid);
        intent.putExtra("doctorname",doctorname);
        intent.putExtra("distance",distance);
        startActivity(intent);
    }
    private void goto_SOS() {
        showSOSAlert(APIClient.sosList);
    }
    private  void goto_PetAppointment_Doctor_Date_Time_Activity(){
        Intent intent = new Intent(DoctorClinicDetailsActivity.this,PetAppointment_Doctor_Date_Time_Activity.class);
        intent.putExtra("doctorid",doctorid);
        intent.putExtra("fromactivity",fromactivity);
        intent.putExtra("amount",amount);
        intent.putExtra("communicationtype",communicationtype);
        startActivity(intent);
    }
    private void showSOSAlert(List<PetLoverDashboardResponse.DataBean.SOSBean> sosList) {

        try {

            dialog = new Dialog(DoctorClinicDetailsActivity.this);
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
                        ActivityCompat.requestPermissions(DoctorClinicDetailsActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
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
}