package com.petfolio.infinitus.doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.adapter.DoctorPaymDetailsAdapter;
import com.petfolio.infinitus.adapter.PetLoverPaymDetailsAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.petlover.PetLoverDashboardActivity;
import com.petfolio.infinitus.petlover.PetloverPaymentDetailsActivity;
import com.petfolio.infinitus.requestpojo.FetchDoctorPaymDetaRequest;
import com.petfolio.infinitus.requestpojo.FetchPetloverPaymDetaRequest;
import com.petfolio.infinitus.responsepojo.FetchDoctorPaymDetaResponse;
import com.petfolio.infinitus.responsepojo.FetchPetloverPaymDetaResponse;
import com.petfolio.infinitus.sessionmanager.SessionManager;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorPaymentDetailsActivity extends  AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private final String TAG = "DoctorPaymentDetailsActivity";


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.include_doctor_footer)
    View include_doctor_footer;

    BottomNavigationView bottom_navigation_view;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.include_doctor_header)
    View include_doctor_header;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_norecords)
    TextView txt_no_records;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_recenttransc)
    RecyclerView rv_recenttransc;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refresh_layout;

    SessionManager session;
    String userid = "";
    private Context mContext;

    List<FetchDoctorPaymDetaResponse.DataBean> dataBeanList;
    private Dialog alertDialog;

    private String active_tag = "1";


    String tag;

    String fromactivity;
    private Dialog dialog;


    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_payment_details);

        Log.w(TAG,"onCreate");
        ButterKnife.bind(this);


        ImageView img_back = include_doctor_header.findViewById(R.id.img_back);
        ImageView img_notification = include_doctor_header.findViewById(R.id.img_notification);
        ImageView img_cart = include_doctor_header.findViewById(R.id.img_cart);
        ImageView img_profile = include_doctor_header.findViewById(R.id.img_profile);
        TextView toolbar_title = include_doctor_header.findViewById(R.id.toolbar_title);
        toolbar_title.setText("Payment Details");


        img_back.setOnClickListener(v -> onBackPressed());

        bottom_navigation_view = include_doctor_footer.findViewById(R.id.bottom_navigation_view);
        bottom_navigation_view.setItemIconTintList(null);
        bottom_navigation_view.setOnNavigationItemSelectedListener(this);
        bottom_navigation_view.getMenu().findItem(R.id.home).setChecked(true);

        avi_indicator.setVisibility(View.GONE);

        session = new SessionManager(this);
        HashMap<String, String> user = session.getProfileDetails();
        //userid = user.get(SessionManager.KEY_ID);

        userid = "603e2a7b2c2b43125f8cb805";

        Log.w(TAG," userid : "+userid);



        if (new ConnectionDetector(DoctorPaymentDetailsActivity.this).isNetworkAvailable(DoctorPaymentDetailsActivity.this)) {
            fetchpaymntdetailsResponseCall();
        }

        refresh_layout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        refresh_layout.setEnabled(false);
                        if (new ConnectionDetector(DoctorPaymentDetailsActivity.this).isNetworkAvailable(DoctorPaymentDetailsActivity.this)) {
                            fetchpaymntdetailsResponseCall();
                        }

                    }
                }
        );
    }

    @SuppressLint("LongLogTag")
    private void fetchpaymntdetailsResponseCall() {

        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<FetchDoctorPaymDetaResponse> call = apiInterface.fetchdrpaymetaillistResponseCall(RestUtils.getContentType(), fetchDoctorPaymDetaRequest());
        Log.w(TAG,"FetchDoctorPaymDetaResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<FetchDoctorPaymDetaResponse>() {
            @SuppressLint({"SetTextI18n", "LogNotTimber"})
            @Override
            public void onResponse(@NonNull Call<FetchDoctorPaymDetaResponse> call, @NonNull Response<FetchDoctorPaymDetaResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"SPFavCreateResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {

                    if (200 == response.body().getCode()) {
                        if (response.body().getData() != null&&!response.body().getData().isEmpty()) {

                            dataBeanList = response.body().getData();

                            if(dataBeanList.size()>0){

                                setViewPaymtDetails(dataBeanList);
                            }
                            else {

                                txt_no_records.setText("No Payments Found");

                                rv_recenttransc.setVisibility(View.GONE);
                            }
                        }
                    }

                }


            }

            @Override
            public void onFailure(@NonNull Call<FetchDoctorPaymDetaResponse> call,@NonNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"FetchDoctorPaymDetaResponse flr"+ t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setViewPaymtDetails(List<FetchDoctorPaymDetaResponse.DataBean> dataBeanList) {

        rv_recenttransc.setLayoutManager(new LinearLayoutManager(DoctorPaymentDetailsActivity.this));
        rv_recenttransc.setItemAnimator(new DefaultItemAnimator());
        DoctorPaymDetailsAdapter doctorPaymDetailsAdapter = new DoctorPaymDetailsAdapter(DoctorPaymentDetailsActivity.this, dataBeanList,dataBeanList.size());
        rv_recenttransc.setAdapter(doctorPaymDetailsAdapter);
    }

    @SuppressLint({"LogNotTimber", "LongLogTag"})
    private FetchDoctorPaymDetaRequest fetchDoctorPaymDetaRequest() {

        /**
         * doctor_id : 603e2a7b2c2b43125f8cb805
         */


        FetchDoctorPaymDetaRequest fetchDoctorPaymDetaRequest = new FetchDoctorPaymDetaRequest();
        fetchDoctorPaymDetaRequest.setDoctor_id(userid);

        Log.w(TAG,"fetchPetloverPaymDetaRequest "+ new Gson().toJson(fetchDoctorPaymDetaRequest));
        return fetchDoctorPaymDetaRequest;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(DoctorPaymentDetailsActivity.this, DoctorDashboardActivity.class);
        startActivity(i);
        finish();
    }


    public void callDirections(String tag){
        Intent intent = new Intent(getApplicationContext(), DoctorDashboardActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);
        finish();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                callDirections("1");
                break;
            case R.id.shop:
                callDirections("2");
                break;

            case R.id.community:
                callDirections("3");
                break;

            default:
                return  false;
        }
        return true;
    }

}