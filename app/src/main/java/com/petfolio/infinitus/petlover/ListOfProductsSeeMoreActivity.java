package com.petfolio.infinitus.petlover;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.adapter.PetShopCategorySeeMoreAdapter;
import com.petfolio.infinitus.adapter.PetShopTodayDealsSeeMoreAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.requestpojo.FetctProductByCatRequest;
import com.petfolio.infinitus.requestpojo.TodayDealMoreRequest;
import com.petfolio.infinitus.responsepojo.FetctProductByCatResponse;
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

public class ListOfProductsSeeMoreActivity extends AppCompatActivity {

    private String TAG = "ListOfProductsSeeMoreActivity";

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
    private String cat_id;
    private int skipcount = 0;


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

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            cat_id = extras.getString("cat_id");
        }

        if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext())) {
            fetctProductByCatResponseCall();
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
    public void fetctProductByCatResponseCall(){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<FetctProductByCatResponse> call = ApiService.fetctProductByCatResponseCall(RestUtils.getContentType(),fetctProductByCatRequest());

        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<FetctProductByCatResponse>() {
            @Override
            public void onResponse(@NonNull Call<FetctProductByCatResponse> call, @NonNull Response<FetctProductByCatResponse> response) {
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
            public void onFailure(@NonNull Call<FetctProductByCatResponse> call,@NonNull  Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"FetctProductByCatResponse flr"+t.getMessage());
            }
        });

    }
    @SuppressLint("LogNotTimber")
    private FetctProductByCatRequest fetctProductByCatRequest() {
        /*
         * cat_id : 5fec14a5ea832e2e73c1fc79
         * skip_count : 6
         */

        FetctProductByCatRequest fetctProductByCatRequest = new FetctProductByCatRequest();
        fetctProductByCatRequest.setCat_id(cat_id);
        fetctProductByCatRequest.setSkip_count(skipcount);
        Log.w(TAG,"fetctProductByCatRequest"+ "--->" + new Gson().toJson(fetctProductByCatRequest));
        return fetctProductByCatRequest;
    }

    private void setView(List<FetctProductByCatResponse.DataBean> data) {
        rv_today_deal.setLayoutManager(new GridLayoutManager(this, 2));
        rv_today_deal.setItemAnimator(new DefaultItemAnimator());
        PetShopCategorySeeMoreAdapter petShopCategorySeeMoreAdapter = new PetShopCategorySeeMoreAdapter(getApplicationContext(), data);
        rv_today_deal.setAdapter(petShopCategorySeeMoreAdapter);

    }


}