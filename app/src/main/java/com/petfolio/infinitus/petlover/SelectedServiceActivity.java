package com.petfolio.infinitus.petlover;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.adapter.DoctorNewAppointmentAdapter;
import com.petfolio.infinitus.adapter.SelectedServiceProviderAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.requestpojo.ProfileUpdateRequest;
import com.petfolio.infinitus.requestpojo.SPSpecificServiceDetailsRequest;
import com.petfolio.infinitus.responsepojo.ProfileUpdateResponse;
import com.petfolio.infinitus.responsepojo.SPSpecificServiceDetailsResponse;
import com.petfolio.infinitus.sessionmanager.SessionManager;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectedServiceActivity extends AppCompatActivity implements View.OnClickListener {


    private String TAG = "SelectedServiceActivity";



    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_selectedserviceimage)
    ImageView img_selectedserviceimage;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_selected_service)
    TextView txt_selected_service;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_totalproviders)
    TextView txt_totalproviders;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_nearbyservices)
    RecyclerView rv_nearbyservices;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_no_records)
    TextView txt_no_records;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_currentlocation)
    TextView txt_currentlocation;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_back)
    ImageView img_back;






    private List<SPSpecificServiceDetailsResponse.DataBean.ServiceProviderBean> serviceProviderList;
    private String userid;
    private String catid;
    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_service);

        ButterKnife.bind(this);
        Log.w(TAG,"onCreate");
        avi_indicator.setVisibility(View.GONE);

        img_back.setOnClickListener(this);


        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getProfileDetails();
        userid = user.get(SessionManager.KEY_ID);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            catid = extras.getString("catid");
            from = extras.getString("from");
        }

        Log.w(TAG," userid : "+userid+ " catid : "+catid+" from : "+from);

       if(catid != null && userid != null) {
           if (new ConnectionDetector(SelectedServiceActivity.this).isNetworkAvailable(SelectedServiceActivity.this)) {
               SPSpecificServiceDetailsResponseCall();
           }
       }

    }


    private void SPSpecificServiceDetailsResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<SPSpecificServiceDetailsResponse> call = apiInterface.SPSpecificServiceDetailsResponseCall(RestUtils.getContentType(), spSpecificServiceDetailsRequest());
        Log.w(TAG,"SPSpecificServiceDetailsResponseCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<SPSpecificServiceDetailsResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<SPSpecificServiceDetailsResponse> call, @NonNull Response<SPSpecificServiceDetailsResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"SPSpecificServiceDetailsResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {

                    if (200 == response.body().getCode()) {
                        if (response.body().getData() != null) {
                            if (response.body().getData().getService_Details().getImage_path() != null) {
                                Glide.with(SelectedServiceActivity.this)
                                        .load(response.body().getData().getService_Details().getImage_path())
                                        .into(img_selectedserviceimage);
                            } else {
                                Glide.with(SelectedServiceActivity.this)
                                        .load(R.drawable.image_thumbnail)
                                        .into(img_selectedserviceimage);

                            }
                            if(response.body().getData().getService_Details().getTitle() != null){
                                txt_selected_service.setText(response.body().getData().getService_Details().getTitle());
                            }
                            catid = response.body().getData().getService_Details().get_id();
                            Log.w(TAG,"catid : "+catid);
                            serviceProviderList = response.body().getData().getService_provider();
                            if(serviceProviderList != null && serviceProviderList.size()>0){
                                txt_totalproviders.setText(serviceProviderList.size()+" Providers");
                                setViewListedSP(serviceProviderList);
                            }




                        }
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<SPSpecificServiceDetailsResponse> call,@NonNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"SPSpecificServiceDetailsResponse flr"+ t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private SPSpecificServiceDetailsRequest spSpecificServiceDetailsRequest() {
        /*
         * user_id : 5fd778437aa4cc1c6a1e5632
         * cata_id : 5fe185d61996f651f5133693
         */

        SPSpecificServiceDetailsRequest spSpecificServiceDetailsRequest = new SPSpecificServiceDetailsRequest();
        spSpecificServiceDetailsRequest.setUser_id(userid);
        spSpecificServiceDetailsRequest.setCata_id(catid);
        Log.w(TAG,"spSpecificServiceDetailsRequest "+ new Gson().toJson(spSpecificServiceDetailsRequest));
        return spSpecificServiceDetailsRequest;
    }

    private void setViewListedSP(List<SPSpecificServiceDetailsResponse.DataBean.ServiceProviderBean> serviceProviderList) {
        rv_nearbyservices.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_nearbyservices.setItemAnimator(new DefaultItemAnimator());
        SelectedServiceProviderAdapter doctorNewAppointmentAdapter = new SelectedServiceProviderAdapter(getApplicationContext(), serviceProviderList,catid,from);
        rv_nearbyservices.setAdapter(doctorNewAppointmentAdapter);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(from != null && from.equalsIgnoreCase("PetServices")){
            callDirections("3");
        }else{
            Intent intent = new Intent(getApplicationContext(), PetLoverDashboardActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public void callDirections(String tag){
        Intent intent = new Intent(getApplicationContext(), PetLoverDashboardActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);
        finish();
    }
}