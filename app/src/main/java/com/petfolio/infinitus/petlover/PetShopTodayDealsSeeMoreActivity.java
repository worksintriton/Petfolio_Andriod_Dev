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
import com.petfolio.infinitus.adapter.PetShopTodayDealsSeeMoreAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.requestpojo.TodayDealMoreRequest;
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

    private PetShopTodayDealsSeeMoreAdapter petShopTodayDealsSeeMoreAdapter;
    private GridLayoutManager gridLayoutManager;

    public static final int PAGE_START = 1;
    private int CURRENT_PAGE = PAGE_START;
    private boolean isLoading = false, isLastPage = false;
    private List<TodayDealMoreResponse.DataBean> todayDealsList;
    private List<TodayDealMoreResponse.DataBean> todayDealsListSeeMore = new ArrayList<>();

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



        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        rv_today_deal.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(this, 2);
        rv_today_deal.setLayoutManager(gridLayoutManager);
        rv_today_deal.setItemAnimator(new DefaultItemAnimator());

        if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext())) {
            todayDealMoreResponseCall();
        }

        initResultRecylerView();




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
                           todayDealsList =  response.body().getData();
                           for(int i=0;i<todayDealsList.size();i++) {
                               /**
                                * _id : 602e11404775fa0735d7bf40
                                * product_img : http://54.212.108.156:3000/api/uploads/resize-1613548631141238608collar.jpg
                                * product_title : DOGISTA PET PRODUCTS Dog Rope Leash,Brass
                                * product_price : 180
                                * product_discount : 0
                                * product_fav : false
                                * product_rating : 4.8
                                * product_review : 232
                                */
                               TodayDealMoreResponse.DataBean  dataBean = new TodayDealMoreResponse.DataBean();
                               dataBean.set_id(todayDealsList.get(i).get_id());
                               dataBean.setProduct_img(todayDealsList.get(i).getProduct_img());
                               dataBean.setProduct_title(todayDealsList.get(i).getProduct_title());
                               dataBean.setProduct_price(todayDealsList.get(i).getProduct_price());
                               dataBean.setProduct_discount(todayDealsList.get(i).getProduct_discount());
                               dataBean.setProduct_fav(todayDealsList.get(i).isProduct_fav());
                               dataBean.setProduct_rating(todayDealsList.get(i).getProduct_rating());
                               dataBean.setProduct_rating(todayDealsList.get(i).getProduct_review());
                               todayDealsListSeeMore.add(dataBean);


                           }
                           Log.w(TAG,"todayDealsListSeeMore : "+new Gson().toJson(todayDealsListSeeMore));
                           Log.w(TAG,"todayDealsListSeeMore size : "+todayDealsListSeeMore.size());
                           setView(todayDealsListSeeMore);
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
        todayDealMoreRequest.setSkip_count(CURRENT_PAGE);
        Log.w(TAG,"todayDealMoreRequest"+ "--->" + new Gson().toJson(todayDealMoreRequest));
        return todayDealMoreRequest;
    }

    private void setView(List<TodayDealMoreResponse.DataBean> data) {
       /* rv_today_deal.setLayoutManager(new GridLayoutManager(this, 2));
        rv_today_deal.setItemAnimator(new DefaultItemAnimator());*/
         petShopTodayDealsSeeMoreAdapter = new PetShopTodayDealsSeeMoreAdapter(getApplicationContext(), data);
        rv_today_deal.setAdapter(petShopTodayDealsSeeMoreAdapter);
        petShopTodayDealsSeeMoreAdapter.notifyDataSetChanged();
        isLoading = false;

    }

    private void initResultRecylerView() {

        rv_today_deal.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == todayDealsListSeeMore.size() - 1) {
                        //bottom of list!
                        CURRENT_PAGE += 1;

                        Log.w(TAG, "isLoading? " + isLoading + " currentPage " + CURRENT_PAGE);
                        isLoading = true;
                        todayDealMoreResponseCall();
                    }
                }
            }
        });
    }
}