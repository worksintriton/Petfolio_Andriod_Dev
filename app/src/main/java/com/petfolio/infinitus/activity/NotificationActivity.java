package com.petfolio.infinitus.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.adapter.NotificationDashboardAdapter;

import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.doctor.DoctorDashboardActivity;
import com.petfolio.infinitus.petlover.PetLoverDashboardActivity;
import com.petfolio.infinitus.petlover.PetLoverProfileScreenActivity;
import com.petfolio.infinitus.requestpojo.NotificationGetlistRequest;
import com.petfolio.infinitus.responsepojo.NotificationGetlistResponse;
import com.petfolio.infinitus.serviceprovider.ServiceProviderDashboardActivity;
import com.petfolio.infinitus.sessionmanager.SessionManager;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;
import com.petfolio.infinitus.vendor.VendorDashboardActivity;
import com.wang.avi.AVLoadingIndicatorView;


import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotificationActivity extends AppCompatActivity {


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvNoRecords)
    TextView tvNorecords;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rvnotifiaction)
    RecyclerView rvnotifiaction;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_back)
    ImageView img_back;




    private String TAG = "NotificationActivity";

    SessionManager session;
    String type = "",name = "",userid = "";
    private List<NotificationGetlistResponse.DataBean> notificationGetlistResponseList;
    private String fromactivity;


    @SuppressLint("LogNotTimber")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaton);
        Log.w(TAG,"onCreate-->");
        ButterKnife.bind(this);
        avi_indicator.setVisibility(View.GONE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            fromactivity = extras.getString("fromactivity");
            Log.w(TAG,"Bundle "+" fromactivity : "+fromactivity);



        }



        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getProfileDetails();
        type = user.get(SessionManager.KEY_TYPE);
        name = user.get(SessionManager.KEY_FIRST_NAME);
        userid = user.get(SessionManager.KEY_ID);


        Log.w(TAG,"session--->"+"type :"+type+" "+"name :"+" "+name);


        img_back.setOnClickListener(v -> onBackPressed());


        if (new ConnectionDetector(NotificationActivity.this).isNetworkAvailable(NotificationActivity.this)) {
            notificationGetlistResponseCall();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @SuppressLint("LogNotTimber")
    private void notificationGetlistResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<NotificationGetlistResponse> call = ApiService.notificationGetlistResponseCall(RestUtils.getContentType(),notificationGetlistRequest());
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<NotificationGetlistResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<NotificationGetlistResponse> call, @NonNull Response<NotificationGetlistResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"NotificationGetlistResponse"+ "--->" + new Gson().toJson(response.body()));


                if (response.body() != null) {
                    if(response.body().getCode() == 200){

                        if(response.body().getData() != null && response.body().getData().size()>0){
                            notificationGetlistResponseList = response.body().getData();
                            tvNorecords.setVisibility(View.GONE);
                            rvnotifiaction.setVisibility(View.VISIBLE);
                            setView();
                        }else{
                            rvnotifiaction.setVisibility(View.GONE);
                            tvNorecords.setVisibility(View.VISIBLE);
                            tvNorecords.setText("No notifications");

                        }


                    }

                }


            }

            @SuppressLint("LogNotTimber")
            @Override
            public void onFailure(@NonNull Call<NotificationGetlistResponse> call, @NonNull Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG,"NotificationGetlistResponse"+"--->" + t.getMessage());
            }
        });

    }
    @SuppressLint("LogNotTimber")
    private NotificationGetlistRequest notificationGetlistRequest() {
        /*
         * user_id : 5ee3666a5dfb34019b13c3a2
         */
        NotificationGetlistRequest notificationGetlistRequest = new NotificationGetlistRequest();
        notificationGetlistRequest.setUser_id(userid);
        Log.w(TAG,"notificationGetlistRequest"+ "--->" + new Gson().toJson(notificationGetlistRequest));
        return notificationGetlistRequest;
    }






    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(fromactivity != null && fromactivity.equalsIgnoreCase("PetLoverNavigationDrawerNew")){
            startActivity(new Intent(getApplicationContext(), PetLoverDashboardActivity.class));
            finish();
        }else if(fromactivity != null && fromactivity.equalsIgnoreCase("DoctorNavigationDrawer")){
            startActivity(new Intent(getApplicationContext(), DoctorDashboardActivity.class));
            finish();
        }else if(fromactivity != null && fromactivity.equalsIgnoreCase("VendorNavigationDrawer")){
            startActivity(new Intent(getApplicationContext(), VendorDashboardActivity.class));
            finish();
        }else if(fromactivity != null && fromactivity.equalsIgnoreCase("ServiceProviderNavigationDrawer")){
            startActivity(new Intent(getApplicationContext(), ServiceProviderDashboardActivity.class));
            finish();
        }else{
            finish();
        }

    }

    private void setView() {
        rvnotifiaction.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvnotifiaction.setItemAnimator(new DefaultItemAnimator());
        NotificationDashboardAdapter notificationDashboardAdapter = new NotificationDashboardAdapter(getApplicationContext(), notificationGetlistResponseList);
        rvnotifiaction.setAdapter(notificationDashboardAdapter);

    }
}
