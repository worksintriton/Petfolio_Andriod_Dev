package com.petfolio.infinitus.petlover;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.adapter.ViewPagerClinicDetailsAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.requestpojo.DoctorDetailsRequest;
import com.petfolio.infinitus.responsepojo.DoctorDetailsResponse;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorClinicDetailsActivity extends AppCompatActivity {

    private static  String TAG = "DoctorClinicDetailsActivity";

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.pager)
    ViewPager viewPager;

    @BindView(R.id.tabDots)
    TabLayout tabLayout;

    @BindView(R.id.txt_clinicname)
    TextView txt_clinicname;

    @BindView(R.id.txt_drname)
    TextView txt_drname;

    @BindView(R.id.txt_dr_specialization)
    TextView txt_dr_specialization;

    @BindView(R.id.txt_review_count)
    TextView txt_review_count;

    @BindView(R.id.txt_star_rating)
    TextView txt_star_rating;

    @BindView(R.id.txt_place)
    TextView txt_place;

    @BindView(R.id.txt_distance)
    TextView txt_distance;

    @BindView(R.id.txt_dr_desc)
    TextView txt_dr_desc;

    @BindView(R.id.btn_book_now)
    Button btn_book_now;

    @BindView(R.id.img_back)
    ImageView img_back;



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


            Log.w(TAG,"Bundle "+" doctorid : "+doctorid);
        }

        btn_book_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorClinicDetailsActivity.this,BookAppointmentActivity.class);
                intent.putExtra("doctorid",doctorid);
                intent.putExtra("fromactivity",fromactivity);
                startActivity(intent);
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext())) {
            if(doctorid != null){
                doctorDetailsResponseCall();
            }

        }


    }


    private void doctorDetailsResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<DoctorDetailsResponse> call = ApiService.doctorDetailsResponseCall(RestUtils.getContentType(),doctorDetailsRequest());
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<DoctorDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<DoctorDetailsResponse> call, @NonNull Response<DoctorDetailsResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"DoctorDetailsResponse"+ "--->" + new Gson().toJson(response.body()));


                if (response.body() != null) {
                    if(200 == response.body().getCode()){

                        doctorclinicdetailsResponseList  = response.body().getData().getClinic_pic();
                        clinicname =  response.body().getData().getClinic_name();
                        doctorname = response.body().getData().getDr_name();
                        reviewcount  = response.body().getData().getReview_count();
                        starcount =  response.body().getData().getStar_count();

                        ClinicLocationname = response.body().getData().getClinic_loc();


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

                       }

                    }else{

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

}