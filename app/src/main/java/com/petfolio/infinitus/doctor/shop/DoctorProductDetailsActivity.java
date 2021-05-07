package com.petfolio.infinitus.doctor.shop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.activity.NotificationActivity;
import com.petfolio.infinitus.adapter.RelatedProductsAdapter;
import com.petfolio.infinitus.adapter.ViewPagerProductDetailsAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.doctor.DoctorDashboardActivity;

import com.petfolio.infinitus.doctor.DoctorProfileScreenActivity;
import com.petfolio.infinitus.requestpojo.CartAddProductRequest;
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
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorProductDetailsActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private final String TAG = "DoctorProductDetailsActivity";

    String prod_type ="";

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

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_discount)
    LinearLayout ll_discount;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.footerView)
    LinearLayout footerView;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_product_title)
    LinearLayout ll_product_title;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.scrollablContent)
    ScrollView scrollablContent;

   /* @SuppressLint("NonConstantResourceId")
    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottom_navigation_view;*/

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.include_doctor_footer)
    View include_doctor_footer;

    BottomNavigationView bottom_navigation_view;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.include_doctor_header)
    View include_doctor_header;






    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;


    private String userid;
    private String productid;
    private int product_cart_counts = 1;
    private String threshould;
    private String fromactivity;
    private String cat_id;
    private int productqty;
    private String tag;

    @SuppressLint({"LogNotTimber", "SetTextI18n", "LongLogTag"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_product_details);
        Log.w(TAG,"onCreate -->");
        ButterKnife.bind(this);
        avi_indicator.setVisibility(View.GONE);
        ll_product_title.setVisibility(View.GONE);
        scrollablContent.setVisibility(View.GONE);
        footerView.setVisibility(View.GONE);

        ImageView img_back = include_doctor_header.findViewById(R.id.img_back);
        ImageView img_notification = include_doctor_header.findViewById(R.id.img_notification);
        ImageView img_cart = include_doctor_header.findViewById(R.id.img_cart);
        ImageView img_profile = include_doctor_header.findViewById(R.id.img_profile);
        TextView toolbar_title = include_doctor_header.findViewById(R.id.toolbar_title);
        toolbar_title.setText(getResources().getString(R.string.product_details));


        img_back.setOnClickListener(v -> onBackPressed());
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getProfileDetails();
        userid = user.get(SessionManager.KEY_ID);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            productid = extras.getString("productid");
            cat_id = extras.getString("cat_id");
            fromactivity = extras.getString("fromactivity");
            tag = extras.getString("tag");
        }
        if(userid != null && productid != null){
            if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext())) {
                fetch_product_by_id_ResponseCall();
            }
        }
        txt_cart_count.setText("1");
        img_remove_product.setOnClickListener(v -> {
            Log.w(TAG,"img_remove_product setOnClickListener : product_cart_counts "+product_cart_counts);
            if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext())) {
                if(product_cart_counts != 1) {
                    //remove_product_ResponseCall();
                    product_cart_counts--;
                    txt_cart_count.setText(product_cart_counts+"");
                    if(product_cart_counts == 1){
                        btn_add_to_cart.setText("Add to cart");
                    }else{
                        btn_add_to_cart.setText("Go to cart");
                    }

                }else{
                    btn_add_to_cart.setText("Add to cart");
                }
            }

        });
        img_add_product.setOnClickListener(v -> {
            if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext())) {

                if(threshould != null){
                    productqty = Integer.parseInt(threshould);
                    int cartcount = Integer.parseInt(txt_cart_count.getText().toString());
                    Log.w(TAG," productqty : "+productqty+" cartcount : "+cartcount);
                    if(cartcount == productqty || cartcount >productqty){
                        Toasty.warning(getApplicationContext(), "You can buy only up to "+productqty+" quantity of this product", Toast.LENGTH_SHORT, true).show();
                    }else{
                       /* if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext())) {
                            add_product_ResponseCall();
                        }*/
                        Log.w(TAG," productqty : "+productqty+" product_cart_counts : "+product_cart_counts);


                        product_cart_counts++;
                        if(threshould != null){
                             productqty = Integer.parseInt(threshould);
                            if(product_cart_counts > productqty){
                                Toasty.warning(getApplicationContext(), "You can buy only up to "+productqty+" quantity of this product", Toast.LENGTH_SHORT, true).show();
                            }else{
                                if(product_cart_counts != 1){
                                    txt_cart_count.setText(product_cart_counts+"");
                                    btn_add_to_cart.setText("Go to cart");
                                }else{
                                    btn_add_to_cart.setText("Add to cart");
                                }

                            }
                        }






                    }
                }



            }

        });
        btn_add_to_cart.setOnClickListener(v -> {
            if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext())) {
                cart_add_product_ResponseCall();
            }
        });


        bottom_navigation_view = include_doctor_footer.findViewById(R.id.bottom_navigation_view);
        bottom_navigation_view.setItemIconTintList(null);
        bottom_navigation_view.setOnNavigationItemSelectedListener(this);


        img_notification.setOnClickListener(this);
        img_cart.setOnClickListener(this);
        img_profile.setOnClickListener(this);

        Log.w(TAG," tag test : "+tag);
        if(tag != null){
            if(tag.equalsIgnoreCase("1")){
                bottom_navigation_view.getMenu().findItem(R.id.home).setChecked(true);
            }else if(tag.equalsIgnoreCase("2")){
                bottom_navigation_view.getMenu().findItem(R.id.shop).setChecked(true);
                //bottom_navigation_view.setSelectedItemId(R.id.shop);
            } else if(tag.equalsIgnoreCase("3")){
                bottom_navigation_view.getMenu().findItem(R.id.community).setChecked(true);
            }
        }else{
            bottom_navigation_view.getMenu().findItem(R.id.shop).setChecked(true);

        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(fromactivity != null && fromactivity.equalsIgnoreCase("SearchDoctorActivity")){
            Intent intent = new Intent(DoctorProductDetailsActivity.this, SearchDoctorActivity.class);
            startActivity(intent);
            finish();
        }else if(fromactivity != null && fromactivity.equalsIgnoreCase("DoctorListOfProductsSeeMoreActivity")){
            Intent intent = new Intent(DoctorProductDetailsActivity.this, DoctorListOfProductsSeeMoreActivity.class);
            intent.putExtra("cat_id",cat_id);
            startActivity(intent);
            finish();
        }else if(fromactivity != null && fromactivity.equalsIgnoreCase("DoctorShopTodayDealsSeeMoreActivity")){
            Intent intent = new Intent(DoctorProductDetailsActivity.this, DoctorShopTodayDealsSeeMoreActivity.class);
            startActivity(intent);
            finish();
        }else if(fromactivity != null && fromactivity.equalsIgnoreCase("Cart_Adapter")){
            Intent intent = new Intent(DoctorProductDetailsActivity.this, DoctorCartActivity.class);
            startActivity(intent);
            finish();
        }else if(fromactivity != null && fromactivity.equalsIgnoreCase("PetLoverShopNewAdapter")){
            callDirections("1");
        }else {
            callDirections("2");
        }
    }

    @SuppressLint({"LogNotTimber", "LongLogTag"})
    public void fetch_product_by_id_ResponseCall(){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<FetchProductByIdResponse> call = ApiService.fetch_product_by_id_ResponseCall(RestUtils.getContentType(),fetchByIdRequest());

        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<FetchProductByIdResponse>() {
            @SuppressLint({"LogNotTimber", "LongLogTag"})
            @Override
            public void onResponse(@NonNull Call<FetchProductByIdResponse> call, @NonNull Response<FetchProductByIdResponse> response) {
                avi_indicator.smoothToHide();
                if (response.body() != null) {
                    if(200 == response.body().getCode()){
                        ll_product_title.setVisibility(View.VISIBLE);
                        scrollablContent.setVisibility(View.VISIBLE);
                        footerView.setVisibility(View.VISIBLE);
                        Log.w(TAG,"FetchProductByIdResponse" + new Gson().toJson(response.body()));
                        if(response.body().getProduct_details() != null){
                            String product_title = response.body().getProduct_details().getProduct_title();
                            int product_review = response.body().getProduct_details().getProduct_review();
                            double product_rating = response.body().getProduct_details().getProduct_rating();
                            int product_price = response.body().getProduct_details().getProduct_price();
                            int product_discount = response.body().getProduct_details().getProduct_discount();
                            String  product_discription = response.body().getProduct_details().getProduct_discription();
                            int product_cart_count = response.body().getProduct_details().getProduct_cart_count();
                            threshould = response.body().getProduct_details().getThreshould();

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
        RelatedProductsAdapter relatedProductsAdapter = new RelatedProductsAdapter(getApplicationContext(), product_related, prod_type,true);
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
    private void setUIData(String product_title,int product_review, double product_rating, int product_price, int product_discount, String product_discription, int product_cart_count, String threshould) {

        //product_cart_counts = product_cart_count;

        if(product_title != null && !product_title.isEmpty()){
            txt_products_title.setText(product_title);
        }
        if(product_review != 0 ){
            txt_review_count.setText(product_review+"");
        }else{
            txt_review_count.setText("0");
        }
        if(product_rating != 0 ){
            txt_star_rating.setText(product_rating+"");
        }else{
            txt_star_rating.setText("0");
        }
        if(product_price != 0 ){
            txt_products_price.setText("\u20B9 "+product_price);

        }else{
            txt_products_price.setText("\u20B9 "+0);
        }
        if(product_discount != 0 ){
            ll_discount.setVisibility(View.VISIBLE);
            txt_discount.setText(product_discount+" % discount");
        }else{
            ll_discount.setVisibility(View.GONE);
        }
        if(threshould != null && !threshould.isEmpty() ){
            if(threshould.equalsIgnoreCase("0")){
                txt_products_quantity.setText("Out Of Stock");
                txt_products_quantity.setTextColor(ContextCompat.getColor(DoctorProductDetailsActivity.this, R.color.vermillion));
                img_add_product.setVisibility(View.GONE);
                txt_cart_count.setVisibility(View.GONE);
                img_remove_product.setVisibility(View.GONE);
                btn_add_to_cart.setVisibility(View.GONE);
            }else{
                img_add_product.setVisibility(View.VISIBLE);
                txt_cart_count.setVisibility(View.VISIBLE);
                img_remove_product.setVisibility(View.VISIBLE);
                btn_add_to_cart.setVisibility(View.VISIBLE);
                txt_products_quantity.setText("Prodcut Quantity : "+threshould);
                txt_products_quantity.setTextColor(ContextCompat.getColor(DoctorProductDetailsActivity.this, R.color.black));

            }


        }
        if(product_discription != null && !product_discription.isEmpty()){
            txt_product_desc.setText(product_discription);
        }
       /* if(product_cart_count != 0){
            txt_cart_count.setText(product_cart_count+"");
        }*/
    }

    @SuppressLint({"LogNotTimber", "LongLogTag"})
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
    @SuppressLint({"LogNotTimber", "LongLogTag"})
    private CartAddProductRequest cartAddProductRequest() {
        /*
         * user_id : 603e27792c2b43125f8cb802
         * product_id : 602e4940f62e8d2089fba978
         * count : 3
         */
        CartAddProductRequest cartAddProductRequest = new CartAddProductRequest();
        cartAddProductRequest.setUser_id(userid);
        cartAddProductRequest.setProduct_id(productid);
        cartAddProductRequest.setCount(Integer.parseInt(txt_cart_count.getText().toString()));
        Log.w(TAG,"cartAddProductRequest"+ "--->" + new Gson().toJson(cartAddProductRequest));
        return cartAddProductRequest;
    }
    public void callDirections(String tag){
        Intent intent = new Intent(DoctorProductDetailsActivity.this, DoctorDashboardActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);
        finish();

    }

    @SuppressLint({"LogNotTimber", "LongLogTag"})
    public void remove_product_ResponseCall(){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<SuccessResponse> call = ApiService.remove_product_ResponseCall(RestUtils.getContentType(),fetchByIdRequest());

        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<SuccessResponse>() {
            @SuppressLint({"LogNotTimber", "SetTextI18n", "LongLogTag"})
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


            @SuppressLint({"LogNotTimber", "LongLogTag"})
            @Override
            public void onFailure(@NonNull Call<SuccessResponse> call, @NonNull  Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"Remove SuccessResponse flr"+t.getMessage());
            }
        });

    }
    @SuppressLint({"LogNotTimber", "LongLogTag"})
    public void cart_add_product_ResponseCall(){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<SuccessResponse> call = ApiService.cart_add_product_ResponseCall(RestUtils.getContentType(),cartAddProductRequest());

        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<SuccessResponse>() {
            @SuppressLint({"LogNotTimber", "SetTextI18n"})
            @Override
            public void onResponse(@NonNull Call<SuccessResponse> call, @NonNull Response<SuccessResponse> response) {
                avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(200 == response.body().getCode()){
                        Intent intent = new Intent(getApplicationContext(),DoctorCartActivity.class);
                        intent.putExtra("productid",productid);
                        intent.putExtra("fromactivity",TAG);
                        startActivity(intent);
                        finish();
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


    @SuppressLint({"LogNotTimber", "LongLogTag"})
    public void add_product_ResponseCall(){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<SuccessResponse> call = ApiService.add_product_ResponseCall(RestUtils.getContentType(),fetchByIdRequest());

        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<SuccessResponse>() {
            @SuppressLint({"LogNotTimber", "SetTextI18n", "LongLogTag"})
            @Override
            public void onResponse(@NonNull Call<SuccessResponse> call, @NonNull Response<SuccessResponse> response) {
                avi_indicator.smoothToHide();


                if (response.body() != null) {
                    if(200 == response.body().getCode()){
                        Log.w(TAG,"Add SuccessResponse" + new Gson().toJson(response.body()));
                        product_cart_counts++;
                        if(threshould != null){
                            int productqty = Integer.parseInt(threshould);
                            if(product_cart_counts > productqty){
                                Toasty.warning(getApplicationContext(), "You can buy only up to "+productqty+" quantity of this product", Toast.LENGTH_SHORT, true).show();
                            }else{
                                if(product_cart_counts != 0){
                                    txt_cart_count.setText(product_cart_counts+"");
                                }

                            }
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

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){

                case R.id.img_notification:
                    startActivity(new Intent(getApplicationContext(), NotificationActivity.class));
                break;
                case R.id.img_cart:
                    Intent i = new Intent(getApplicationContext(), DoctorCartActivity.class);
                    i.putExtra("productid",productid);
                    i.putExtra("cat_id",cat_id);
                    i.putExtra("fromactivity",TAG);
                    startActivity(i);
                    break;
                case R.id.img_profile:
                    Intent intent = new Intent(getApplicationContext(), DoctorProfileScreenActivity.class);
                    intent.putExtra("fromactivity",TAG);
                    if(DoctorDashboardActivity.active_tag != null){
                        intent.putExtra("active_tag",DoctorDashboardActivity.active_tag);

                    }
                    startActivity(intent);
                break;
        }

    }
}