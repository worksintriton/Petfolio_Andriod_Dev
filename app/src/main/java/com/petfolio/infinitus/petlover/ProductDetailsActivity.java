package com.petfolio.infinitus.petlover;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.adapter.RelatedProductsAdapter;
import com.petfolio.infinitus.adapter.ViewPagerProductDetailsAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.requestpojo.FetchByIdRequest;
import com.petfolio.infinitus.responsepojo.FetchProductByIdResponse;
import com.petfolio.infinitus.responsepojo.SuccessResponse;
import com.petfolio.infinitus.sessionmanager.SessionManager;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsActivity extends AppCompatActivity {

    private final String TAG = "ProductDetailsActivity";

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
    @BindView(R.id.img_back)
    ImageView img_back;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_products_title)
    TextView txt_products_title;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_review_count)
    TextView txt_review_count;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_star_rating)
    TextView txt_star_rating;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_share)
    ImageView img_share;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_products_price)
    TextView txt_products_price;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_discount)
    TextView txt_discount;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_products_quantity)
    TextView txt_products_quantity;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_product_desc)
    TextView txt_product_desc;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_relatedproducts)
    RecyclerView rv_relatedproducts;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_cart_count)
    TextView txt_cart_count;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_remove_product)
    ImageView img_remove_product;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_add_product)
    ImageView img_add_product;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_add_to_cart)
    Button btn_add_to_cart;


    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;


    private String userid;
    private String productid;
    private int product_cart_counts;

    @SuppressLint("LogNotTimber")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Log.w(TAG,"onCreate -->");
        ButterKnife.bind(this);
        avi_indicator.setVisibility(View.GONE);

        img_back.setOnClickListener(v -> onBackPressed());
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getProfileDetails();
        userid = user.get(SessionManager.KEY_ID);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            productid = extras.getString("productid");
        }

        if(userid != null && productid != null){
            if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext())) {
                fetch_product_by_id_ResponseCall();
            }
        }

        img_remove_product.setOnClickListener(v -> {
            if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext())) {
                if(product_cart_counts != 0) {
                    remove_product_ResponseCall();
                }
            }

        });

        img_add_product.setOnClickListener(v -> {
            if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext())) {
                add_product_ResponseCall();
            }

        });

        btn_add_to_cart.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),PetCartActivity.class);
            intent.putExtra("productid",productid);
            intent.putExtra("fromactivity",TAG);
            startActivity(intent);
            finish();
        });




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        callDirections("2");
    }

    @SuppressLint("LogNotTimber")
    public void fetch_product_by_id_ResponseCall(){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<FetchProductByIdResponse> call = ApiService.fetch_product_by_id_ResponseCall(RestUtils.getContentType(),fetchByIdRequest());

        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<FetchProductByIdResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<FetchProductByIdResponse> call, @NonNull Response<FetchProductByIdResponse> response) {
                avi_indicator.smoothToHide();
                if (response.body() != null) {
                    if(200 == response.body().getCode()){
                        Log.w(TAG,"FetchProductByIdResponse" + new Gson().toJson(response.body()));

                        if(response.body().getProduct_details() != null){
                            String product_title = response.body().getProduct_details().getProduct_title();
                            int product_review = response.body().getProduct_details().getProduct_review();
                            double product_rating = response.body().getProduct_details().getProduct_rating();
                            double product_price = response.body().getProduct_details().getProduct_price();
                            int product_discount = response.body().getProduct_details().getProduct_discount();
                            String  product_discription = response.body().getProduct_details().getProduct_discription();
                            int product_cart_count = response.body().getProduct_details().getProduct_cart_count();
                            String threshould = response.body().getProduct_details().getThreshould();

                            if(response.body().getProduct_details().getProduct_img() != null && response.body().getProduct_details().getProduct_img().size()>0){
                                viewpageData(response.body().getProduct_details().getProduct_img());
                            }
                             if(response.body().getProduct_details().getProduct_related() != null && response.body().getProduct_details().getProduct_related().size()>0){
                                 setView(response.body().getProduct_details().getProduct_related());

                            }
                             setUIData(product_title,product_review,product_rating,product_price,product_discount,product_discription,product_cart_count,threshould);




                        }


                    }
                }
            }


            @SuppressLint("LogNotTimber")
            @Override
            public void onFailure(@NonNull Call<FetchProductByIdResponse> call, @NonNull  Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"FetchProductByIdResponse flr"+t.getMessage());
            }
        });

    }

    private void setView(List<FetchProductByIdResponse.ProductDetailsBean.ProductRelatedBean> product_related) {
        rv_relatedproducts.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        rv_relatedproducts.setItemAnimator(new DefaultItemAnimator());
        RelatedProductsAdapter relatedProductsAdapter = new RelatedProductsAdapter(getApplicationContext(), product_related);
        rv_relatedproducts.setAdapter(relatedProductsAdapter);

    }

    private void viewpageData(List<String> product_img) {
        tabLayout.setupWithViewPager(viewPager, true);

        ViewPagerProductDetailsAdapter viewPagerProductDetailsAdapter = new ViewPagerProductDetailsAdapter(getApplicationContext(), product_img);
        viewPager.setAdapter(viewPagerProductDetailsAdapter);
        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update = () -> {
            if (currentPage == product_img.size()) {
                currentPage = 0;
            }
            viewPager.setCurrentItem(currentPage++, false);
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
    }

    @SuppressLint("SetTextI18n")
    private void setUIData(String product_title,int product_review, double product_rating, double product_price, int product_discount, String product_discription, int product_cart_count, String threshould) {

        product_cart_counts = product_cart_count;

        if(product_title != null && !product_title.isEmpty()){
            txt_products_title.setText(product_title);
        }
        if(product_review != 0 ){
            txt_review_count.setText(product_review+"");
        }
        if(product_rating != 0 ){
            txt_star_rating.setText(product_rating+"");

        }
        if(product_price != 0 ){
            txt_products_price.setText(product_price+"");

        }
        if(product_discount != 0 ){
            txt_discount.setText(product_price+" % discount");

        }
        if(threshould != null && !threshould.isEmpty() ){
            txt_products_quantity.setText("Prodcut Quantity : "+threshould);

        }
        if(product_discription != null && !product_discription.isEmpty()){
            txt_product_desc.setText(product_discription);
        }
        if(product_cart_count != 0){
            txt_cart_count.setText(product_cart_count+"");
        }
    }

    @SuppressLint("LogNotTimber")
    private FetchByIdRequest fetchByIdRequest() {
        /*
         * user_id : 603e27792c2b43125f8cb802
         * product_id : 6034d6a5888af7628e7e17d4
         */
        FetchByIdRequest fetchByIdRequest = new FetchByIdRequest();
        fetchByIdRequest.setUser_id(userid);
        fetchByIdRequest.setProduct_id(productid);
        Log.w(TAG,"fetchByIdRequest"+ "--->" + new Gson().toJson(fetchByIdRequest));
        return fetchByIdRequest;
    }

    public void callDirections(String tag){
        Intent intent = new Intent(ProductDetailsActivity.this,PetLoverDashboardActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);
        finish();

    }

    @SuppressLint("LogNotTimber")
    public void remove_product_ResponseCall(){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<SuccessResponse> call = ApiService.remove_product_ResponseCall(RestUtils.getContentType(),fetchByIdRequest());

        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<SuccessResponse>() {
            @SuppressLint({"LogNotTimber", "SetTextI18n"})
            @Override
            public void onResponse(@NonNull Call<SuccessResponse> call, @NonNull Response<SuccessResponse> response) {
                avi_indicator.smoothToHide();


                if (response.body() != null) {
                    if(200 == response.body().getCode()){
                        Log.w(TAG,"Remove SuccessResponse" + new Gson().toJson(response.body()));
                        product_cart_counts--;
                        txt_cart_count.setText(product_cart_counts+"");
                    }
                }
            }


            @SuppressLint("LogNotTimber")
            @Override
            public void onFailure(@NonNull Call<SuccessResponse> call, @NonNull  Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"Remove SuccessResponse flr"+t.getMessage());
            }
        });

    }
    @SuppressLint("LogNotTimber")
    public void add_product_ResponseCall(){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<SuccessResponse> call = ApiService.add_product_ResponseCall(RestUtils.getContentType(),fetchByIdRequest());

        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<SuccessResponse>() {
            @SuppressLint({"LogNotTimber", "SetTextI18n"})
            @Override
            public void onResponse(@NonNull Call<SuccessResponse> call, @NonNull Response<SuccessResponse> response) {
                avi_indicator.smoothToHide();


                if (response.body() != null) {
                    if(200 == response.body().getCode()){
                        Log.w(TAG,"Add SuccessResponse" + new Gson().toJson(response.body()));
                        product_cart_counts++;
                        if(product_cart_counts != 0){
                            txt_cart_count.setText(product_cart_counts+"");
                        }



                    }
                }
            }


            @SuppressLint("LogNotTimber")
            @Override
            public void onFailure(@NonNull Call<SuccessResponse> call, @NonNull  Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"Add SuccessResponse flr"+t.getMessage());
            }
        });

    }



}