package com.petfolio.infinitus.fragmentpetlover.bottommenu;

import android.annotation.SuppressLint;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.google.android.material.tabs.TabLayout;

import com.google.gson.Gson;
import com.petfolio.infinitus.R;

import com.petfolio.infinitus.adapter.PetShopProductDetailsAdapter;
import com.petfolio.infinitus.adapter.PetShopTodayDealsAdapter;
import com.petfolio.infinitus.adapter.ViewPagerShopDashboardAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;

import com.petfolio.infinitus.petlover.PetShopTodayDealsSeeMoreActivity;
import com.petfolio.infinitus.petlover.PetLoverDashboardActivity;

import com.petfolio.infinitus.requestpojo.ShopDashboardRequest;

import com.petfolio.infinitus.responsepojo.ShopDashboardResponse;

import com.petfolio.infinitus.sessionmanager.SessionManager;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;


import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VendorShopFragment extends Fragment implements Serializable,View.OnClickListener  {


    private String TAG = "VendorShopFragment";
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;


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
    @BindView(R.id.edt_search)
    EditText edt_search;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_all_categories)
    Button btn_all_categories;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_lbl_todaydeal)
    TextView txt_lbl_todaydeal;



    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_no_records)
    TextView txt_no_records;



    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_today_deal)
    RecyclerView rv_today_deal;

   @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_productdetails)
    RecyclerView rv_productdetails;


   @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_seemore_todaydeals)
    TextView txt_seemore_todaydeals;









    private Context mContext;
    private Dialog alertDialog;
    private List<ShopDashboardResponse.DataBean.BannerDetailsBean> listHomeBannerResponse;
    private List<ShopDashboardResponse.DataBean.ProductDetailsBean.ProductListBean> productList;
    private String userid;


    public VendorShopFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @SuppressLint({"SetTextI18n", "LogNotTimber"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.w(TAG,"onCreateView-->");
        View view = inflater.inflate(R.layout.fragment_shop_vendor, container, false);
        ButterKnife.bind(this, view);
        mContext = getActivity();
        avi_indicator.setVisibility(View.GONE);

        SessionManager sessionManager = new SessionManager(mContext);
        HashMap<String, String> user = sessionManager.getProfileDetails();
         userid = user.get(SessionManager.KEY_ID);
        Log.w(TAG,"customerid-->"+ userid);

        if (new ConnectionDetector(mContext).isNetworkAvailable(mContext)) {
             shopDashboardResponseCall();
        }

        txt_seemore_todaydeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(mContext, PetShopTodayDealsSeeMoreActivity.class);
                intent.putExtra("from","");
                startActivity(intent);
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }










    @Override
    public void onClick(View v) {
        switch (v.getId()){


        }

    }

    public void callDirections(String tag){
        Intent intent = new Intent(mContext, PetLoverDashboardActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);


    }





    public void showErrorLoading(String errormesage){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setMessage(errormesage);
        alertDialogBuilder.setPositiveButton("ok",
                (arg0, arg1) -> hideLoading());




        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public void hideLoading(){
        try {
            alertDialog.dismiss();
        }catch (Exception ignored){

        }
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

                        if(response.body().getData().getBanner_details() != null && response.body().getData().getBanner_details().size()>0) {
                            listHomeBannerResponse = response.body().getData().getBanner_details();
                            for (int i = 0; i < listHomeBannerResponse.size(); i++) {
                                listHomeBannerResponse.get(i).getBanner_img();
                            }

                            if (listHomeBannerResponse != null) {
                                viewpageData(listHomeBannerResponse);
                            }
                        }
                        if(response.body().getData().getToday_Special() != null && response.body().getData().getToday_Special().size()>0){
                            txt_lbl_todaydeal.setVisibility(View.VISIBLE);
                            txt_seemore_todaydeals.setVisibility(View.VISIBLE);
                            rv_today_deal.setVisibility(View.VISIBLE);
                            setView(response.body().getData().getToday_Special());

                        }
                        else{
                            txt_lbl_todaydeal.setVisibility(View.GONE);
                            txt_seemore_todaydeals.setVisibility(View.GONE);
                            rv_today_deal.setVisibility(View.GONE);
                        }
                        if(response.body().getData().getProduct_details() != null && response.body().getData().getProduct_details().size()>0){
                            for(int i=0;i<response.body().getData().getProduct_details().size();i++){
                                productList = response.body().getData().getProduct_details().get(i).getProduct_list();
                                if(response.body().getData().getProduct_details().get(i).getProduct_list() != null && response.body().getData().getProduct_details().get(i).getProduct_list().size()>0){
                                    rv_productdetails.setVisibility(View.VISIBLE);
                                }else{
                                    rv_productdetails.setVisibility(View.GONE);
                                }

                            }
                            setViewProductDetails(response.body().getData().getProduct_details(),productList);

                        }



                    }



                }








            }


            @SuppressLint("LogNotTimber")
            @Override
            public void onFailure(@NonNull Call<ShopDashboardResponse> call,@NonNull  Throwable t) {
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


    private void setViewProductDetails(List<ShopDashboardResponse.DataBean.ProductDetailsBean> product_details, List<ShopDashboardResponse.DataBean.ProductDetailsBean.ProductListBean> productList) {
        rv_productdetails.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rv_productdetails.setItemAnimator(new DefaultItemAnimator());
        PetShopProductDetailsAdapter petShopProductDetailsAdapter = new PetShopProductDetailsAdapter(mContext, product_details,productList);
        rv_productdetails.setAdapter(petShopProductDetailsAdapter);
    }

    private void viewpageData(List<ShopDashboardResponse.DataBean.BannerDetailsBean> listHomeBannerResponse) {
        tabLayout.setupWithViewPager(viewPager, true);

        ViewPagerShopDashboardAdapter viewPagerShopDashboardAdapter = new ViewPagerShopDashboardAdapter(mContext, listHomeBannerResponse);
        viewPager.setAdapter(viewPagerShopDashboardAdapter);
        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update =  new Runnable() {
            public void run() {
                if (currentPage == VendorShopFragment.this.listHomeBannerResponse.size()) {
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


    private void setView(List<ShopDashboardResponse.DataBean.TodaySpecialBean> today_special) {
        rv_today_deal.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rv_today_deal.setItemAnimator(new DefaultItemAnimator());
        PetShopTodayDealsAdapter petShopTodayDealsAdapter = new PetShopTodayDealsAdapter(mContext, today_special);
        rv_today_deal.setAdapter(petShopTodayDealsAdapter);

    }




}
