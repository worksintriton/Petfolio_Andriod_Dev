package com.petfolio.infinitus.vendor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.adapter.ProductsSearchAdapter;
import com.petfolio.infinitus.adapter.VendorAddProductsAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.requestpojo.ShopDashboardRequest;
import com.petfolio.infinitus.responsepojo.ProductSearchResponse;
import com.petfolio.infinitus.responsepojo.ShopDashboardResponse;
import com.petfolio.infinitus.responsepojo.TodayDealMoreResponse;
import com.petfolio.infinitus.sessionmanager.SessionManager;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendorAddProductsActivity extends AppCompatActivity {

    private  String TAG = "VendorAddProductsActivity";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_back)
    ImageView img_back;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_manage_productlist)
    RecyclerView rv_manage_productlist;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_no_records)
    TextView txt_no_records;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.spr_category_type)
    Spinner spr_category_type;
    private String userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_add_products);

        ButterKnife.bind(this);

        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getProfileDetails();
        userid = user.get(SessionManager.KEY_ID);

        img_back.setOnClickListener(v -> onBackPressed());
        List<TodayDealMoreResponse.DataBean> data = new ArrayList<>();


        if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext())) {
            shopDashboardResponseCall();
        }




    }

    @SuppressLint("LogNotTimber")
    public void shopDashboardResponseCall(){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<ShopDashboardResponse> call = ApiService.shopDashboardResponseCall(RestUtils.getContentType(),shopDashboardRequest());

        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<ShopDashboardResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<ShopDashboardResponse> call, @NonNull Response<ShopDashboardResponse> response) {
                avi_indicator.smoothToHide();
                if (response.body() != null) {
                    if(200 == response.body().getCode()){
                        Log.w(TAG,"ShopDashboardResponse" + new Gson().toJson(response.body()));


                        if(response.body().getData().getToday_Special() != null && response.body().getData().getToday_Special().size()>0){
                            txt_no_records.setVisibility(View.GONE);
                            rv_manage_productlist.setVisibility(View.VISIBLE);
                            setViewProducts(response.body().getData().getToday_Special());

                        }
                        else{
                            rv_manage_productlist.setVisibility(View.GONE);
                            txt_no_records.setVisibility(View.VISIBLE);
                        }

                    }
                }
            }


            @SuppressLint("LogNotTimber")
            @Override
            public void onFailure(@NonNull Call<ShopDashboardResponse> call, @NonNull  Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"ShopDashboardResponse flr"+t.getMessage());
            }
        });

    }
    @SuppressLint("LogNotTimber")
    private ShopDashboardRequest shopDashboardRequest() {
        /*
         * user_id : 6025040ee15519672cd0dc02

         */
        ShopDashboardRequest shopDashboardRequest = new ShopDashboardRequest();
        shopDashboardRequest.setUser_id(userid);
        Log.w(TAG,"shopDashboardRequest"+ "--->" + new Gson().toJson(shopDashboardRequest));
        return shopDashboardRequest;
    }


    private void setViewProducts(List<ShopDashboardResponse.DataBean.TodaySpecialBean> today_special) {
        rv_manage_productlist.setLayoutManager(new GridLayoutManager(this, 2));
        rv_manage_productlist.setItemAnimator(new DefaultItemAnimator());
        VendorAddProductsAdapter vendorAddProductsAdapter = new VendorAddProductsAdapter(getApplicationContext(), today_special,TAG,"");
        rv_manage_productlist.setAdapter(vendorAddProductsAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),VendorDashboardActivity.class));
        finish();
    }
}
