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
import com.petfolio.infinitus.responsepojo.ShopDashboardResponse;
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

public class ListOfProductsActivity extends AppCompatActivity {

    private String TAG = "ListOfProductsActivity";

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
    private String userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_products);
        ButterKnife.bind(this);
        avi_indicator.setVisibility(View.GONE);

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getProfileDetails();
        userid = user.get(SessionManager.KEY_ID);
        Log.w(TAG,"customerid-->"+ userid);

        if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext())) {
            shopDashboardResponseCall();
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

    public void shopDashboardResponseCall(){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<ShopDashboardResponse> call = ApiService.shopDashboardResponseCall(RestUtils.getContentType(),shopDashboardRequest());

        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<ShopDashboardResponse>() {
            @Override
            public void onResponse(@NonNull Call<ShopDashboardResponse> call, @NonNull Response<ShopDashboardResponse> response) {
                avi_indicator.smoothToHide();


                if (response.body() != null) {
                    if(200 == response.body().getCode()){
                        Log.w(TAG,"ShopDashboardResponse" + new Gson().toJson(response.body()));

                        if(response.body().getData().getToday_Special() != null && response.body().getData().getToday_Special().size()>0){
                            setView(response.body().getData().getToday_Special());

                        }




                    }



                }








            }


            @Override
            public void onFailure(@NonNull Call<ShopDashboardResponse> call,@NonNull  Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"ShopDashboardResponse flr"+t.getMessage());
            }
        });

    }
    private ShopDashboardRequest shopDashboardRequest() {
        /*
         * user_id : 6025040ee15519672cd0dc02

         */
        ShopDashboardRequest shopDashboardRequest = new ShopDashboardRequest();
        shopDashboardRequest.setUser_id(userid);
        Log.w(TAG,"shopDashboardRequest"+ "--->" + new Gson().toJson(shopDashboardRequest));
        return shopDashboardRequest;
    }

    private void setView(List<ShopDashboardResponse.DataBean.TodaySpecialBean> today_special) {
        rv_today_deal.setLayoutManager(new GridLayoutManager(this, 2));
        rv_today_deal.setItemAnimator(new DefaultItemAnimator());
        PetShopTodayDealsSeeMoreAdapter petShopTodayDealsSeeMoreAdapter = new PetShopTodayDealsSeeMoreAdapter(getApplicationContext(), today_special);
        rv_today_deal.setAdapter(petShopTodayDealsSeeMoreAdapter);

    }


}