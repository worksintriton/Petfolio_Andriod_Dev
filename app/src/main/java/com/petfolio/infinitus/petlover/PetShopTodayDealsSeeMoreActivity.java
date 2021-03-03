package com.petfolio.infinitus.petlover;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.adapter.PetShopTodayDealsAdapter;
import com.petfolio.infinitus.adapter.PetShopTodayDealsSeeMoreAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.requestpojo.ShopDashboardRequest;
import com.petfolio.infinitus.requestpojo.TodayDealMoreRequest;
import com.petfolio.infinitus.responsepojo.ShopDashboardResponse;
import com.petfolio.infinitus.responsepojo.TodayDealMoreResponse;
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

public class PetShopTodayDealsSeeMoreActivity extends AppCompatActivity {

    private String TAG = "PetShopTodayDealsSeeMoreActivity";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_no_records)
    TextView txt_no_records;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_today_deal)
    RecyclerView rv_today_deal;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_back)
    ImageView img_back;


    @SuppressLint("LogNotTimber")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_products);
        ButterKnife.bind(this);
        avi_indicator.setVisibility(View.GONE);

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getProfileDetails();
        String userid = user.get(SessionManager.KEY_ID);
        Log.w(TAG,"customerid-->"+ userid);

        if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext())) {
            todayDealMoreResponseCall();
        }

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @SuppressLint("LogNotTimber")
    public void todayDealMoreResponseCall(){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<TodayDealMoreResponse> call = ApiService.todayDealMoreResponseCall(RestUtils.getContentType(),todayDealMoreRequest());

        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<TodayDealMoreResponse>() {
            @Override
            public void onResponse(@NonNull Call<TodayDealMoreResponse> call, @NonNull Response<TodayDealMoreResponse> response) {
                avi_indicator.smoothToHide();


                if (response.body() != null) {
                    if(200 == response.body().getCode()){
                        Log.w(TAG,"ShopDashboardResponse" + new Gson().toJson(response.body()));

                        if(response.body().getData()!= null && response.body().getData().size()>0){
                            setView(response.body().getData());

                        }




                    }



                }








            }


            @Override
            public void onFailure(@NonNull Call<TodayDealMoreResponse> call,@NonNull  Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"TodayDealMoreRespons flr"+t.getMessage());
            }
        });

    }
    @SuppressLint("LogNotTimber")
    private TodayDealMoreRequest todayDealMoreRequest() {
        /*
         * skip_count :

         */
        TodayDealMoreRequest todayDealMoreRequest = new TodayDealMoreRequest();
        todayDealMoreRequest.setSkip_count(0);
        Log.w(TAG,"todayDealMoreRequest"+ "--->" + new Gson().toJson(todayDealMoreRequest));
        return todayDealMoreRequest;
    }

    private void setView(List<TodayDealMoreResponse.DataBean> data) {
        rv_today_deal.setLayoutManager(new GridLayoutManager(this, 2));
        rv_today_deal.setItemAnimator(new DefaultItemAnimator());
        PetShopTodayDealsSeeMoreAdapter petShopTodayDealsSeeMoreAdapter = new PetShopTodayDealsSeeMoreAdapter(getApplicationContext(), data);
        rv_today_deal.setAdapter(petShopTodayDealsSeeMoreAdapter);

    }


}