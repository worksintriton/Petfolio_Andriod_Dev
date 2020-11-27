package com.petfolio.infinitus.petlover;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.adapter.ViewPagerClinicDetailsAdapter;
import com.petfolio.infinitus.adapter.ViewPagerDashboardAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.requestpojo.DoctorDetailsRequest;
import com.petfolio.infinitus.requestpojo.DoctorNewAppointmentRequest;
import com.petfolio.infinitus.responsepojo.DoctorDetailsResponse;
import com.petfolio.infinitus.responsepojo.DoctorMissedAppointmentResponse;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

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

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;

    private String doctorid;
    private DoctorDetailsResponse.DataBean doctorclinicdetailsResponse;
    private List<DoctorDetailsResponse.DataBean.ClinicPicBean> doctorclinicdetailsResponseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_clinic_details);

        ButterKnife.bind(this);


        avi_indicator.setVisibility(View.GONE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            doctorid = extras.getString("doctorid");

            Log.w(TAG,"Bundle "+" doctorid : "+doctorid);
        }

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

                        doctorclinicdetailsResponse = response.body().getData();
                        doctorclinicdetailsResponseList  = response.body().getData().getClinic_pic();
                        Log.w(TAG,"Size"+doctorclinicdetailsResponseList.size());
                        Log.w(TAG,"doctorclinicdetailsResponseList : "+new Gson().toJson(doctorclinicdetailsResponseList));

                        if(doctorclinicdetailsResponseList != null && doctorclinicdetailsResponseList.size()>0){

                            for (int i = 0; i < doctorclinicdetailsResponseList.size(); i++) {
                                doctorclinicdetailsResponseList.get(i).getClinic_pic();
                                Log.w(TAG, "RES" + " " +  doctorclinicdetailsResponseList.get(i).getClinic_pic());
                            }


                                viewpageData(doctorclinicdetailsResponseList);

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

    private DoctorDetailsRequest doctorDetailsRequest() {
        DoctorDetailsRequest doctorDetailsRequest = new DoctorDetailsRequest();
        doctorDetailsRequest.setUser_id(doctorid);
        Log.w(TAG,"doctorDetailsRequest"+ "--->" + new Gson().toJson(doctorDetailsRequest));
        return doctorDetailsRequest;
    }
}