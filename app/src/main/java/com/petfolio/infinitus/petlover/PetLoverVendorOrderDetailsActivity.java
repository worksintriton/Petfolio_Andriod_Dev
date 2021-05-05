package com.petfolio.infinitus.petlover;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.adapter.ProductDetailsAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.requestpojo.PetLoverCancelOrderRequest;
import com.petfolio.infinitus.requestpojo.PetLoverVendorOrderDetailsRequest;
import com.petfolio.infinitus.responsepojo.PetLoverVendorOrderDetailsResponse;
import com.petfolio.infinitus.responsepojo.VendorOrderUpdateResponse;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;

import com.wang.avi.AVLoadingIndicatorView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetLoverVendorOrderDetailsActivity extends AppCompatActivity implements View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "PetLoverVendorOrderDetailsActivity" ;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_no_records)
    TextView txt_no_records;



    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_products_image)
    ImageView img_products_image;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_product_title)
    TextView txt_product_title;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_products_price)
    TextView txt_products_price;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_order_status)
    TextView txt_order_status;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_delivered_date)
    TextView txt_delivered_date;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_order_date)
    TextView txt_order_date;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_booking_id)
    TextView txt_booking_id;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_payment_method)
    TextView txt_payment_method;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_total_order_cost)
    TextView txt_total_order_cost;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_quantity)
    TextView txt_quantity;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_shipping_address_name)
    TextView txt_shipping_address_name;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_shipping_address_street)
    TextView txt_shipping_address_street;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_shipping_address_city)
    TextView txt_shipping_address_city;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_shipping_address_state_pincode)
    TextView txt_shipping_address_state_pincode;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_shipping_address_phone)
    TextView txt_shipping_address_phone;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_shipping_address_landmark)
    TextView txt_shipping_address_landmark;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_order_status)
    ImageView img_order_status;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_expand_arrow)
    ImageView img_expand_arrow;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_orderdetails)
    LinearLayout ll_orderdetails;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_expand_arrow_shipping)
    ImageView img_expand_arrow_shipping;



    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_shippingaddress)
    LinearLayout ll_shippingaddress;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_productdetails)
    RecyclerView rv_productdetails;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_expand_arrow_productdetails)
    ImageView img_expand_arrow_productdetails;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_productdetails)
    LinearLayout ll_productdetails;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_no_products)
    TextView txt_no_products;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_cancell_order)
    TextView txt_cancell_order;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.scrollablContent)
    ScrollView scrollablContent;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.include_petlover_footer)
    View include_petlover_footer;

    BottomNavigationView bottom_navigation_view;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.include_petlover_header)
    View include_petlover_header;


    private String _id;
    private String fromactivity;

    private  boolean button1IsVisible = true;
    private  boolean ShippingIsVisible = true;
    private  boolean productsIsVisible = true;
    private String orderid;
    private List<PetLoverVendorOrderDetailsResponse.DataBean.ProductDetailsBean> productdetailslist;
    private List<Integer> product_id = new ArrayList<>();


    @SuppressLint({"LogNotTimber", "LongLogTag", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petlover_vendor_order_details);
        ButterKnife.bind(this);

        ImageView img_back = include_petlover_header.findViewById(R.id.img_back);
        ImageView img_sos = include_petlover_header.findViewById(R.id.img_sos);
        ImageView img_notification = include_petlover_header.findViewById(R.id.img_notification);
        ImageView img_cart = include_petlover_header.findViewById(R.id.img_cart);
        ImageView img_profile = include_petlover_header.findViewById(R.id.img_profile);
        TextView toolbar_title = include_petlover_header.findViewById(R.id.toolbar_title);
        toolbar_title.setText(getResources().getString(R.string.order_details));

        img_back.setOnClickListener(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            _id = extras.getString("_id");
            orderid = _id;
             fromactivity = extras.getString("fromactivity");
            Log.w(TAG,"_id : "+_id+" fromactivity : "+ fromactivity);



        }

        bottom_navigation_view = include_petlover_footer.findViewById(R.id.bottom_navigation_view);
        bottom_navigation_view.setItemIconTintList(null);
        bottom_navigation_view.setOnNavigationItemSelectedListener(this);
        bottom_navigation_view.getMenu().findItem(R.id.shop).setChecked(true);

        scrollablContent.setVisibility(View.GONE);
        txt_cancell_order.setVisibility(View.GONE);

        if (new ConnectionDetector(PetLoverVendorOrderDetailsActivity.this).isNetworkAvailable(PetLoverVendorOrderDetailsActivity.this)) {
            vendorOrderDetailsResponseCall();

        }

        ll_orderdetails.setVisibility(View.GONE);
        ll_shippingaddress.setVisibility(View.GONE);


        img_expand_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.w(TAG, "button1IsVisible : "+button1IsVisible);

                if(button1IsVisible) {
                    ll_orderdetails.setVisibility(View.VISIBLE);
                    img_expand_arrow.setImageResource(R.drawable.ic_up);
                    button1IsVisible = false;
                }
                else {
                    ll_orderdetails.setVisibility(View.GONE);
                    img_expand_arrow.setImageResource(R.drawable.ic_down);
                    button1IsVisible = true;

                }


            }
        });
        img_expand_arrow_shipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.w(TAG, "ShippingIsVisible : "+ShippingIsVisible);

                if(ShippingIsVisible) {
                    ll_shippingaddress.setVisibility(View.VISIBLE);
                    img_expand_arrow_shipping.setImageResource(R.drawable.ic_up);
                    ShippingIsVisible = false;
                } else {
                    ll_shippingaddress.setVisibility(View.GONE);
                    img_expand_arrow_shipping.setImageResource(R.drawable.ic_down);
                    ShippingIsVisible = true;

                }


            }
        });
        img_expand_arrow_productdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.w(TAG, "productsIsVisible : "+productsIsVisible);

                if(productsIsVisible) {
                    ll_productdetails.setVisibility(View.GONE);
                    img_expand_arrow_productdetails.setImageResource(R.drawable.ic_down);
                    productsIsVisible = false;
                } else {
                    ll_productdetails.setVisibility(View.VISIBLE);
                    img_expand_arrow_productdetails.setImageResource(R.drawable.ic_up);
                    productsIsVisible = true;

                }


            }
        });
        txt_cancell_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), PetVendorCancelOrderActivity.class);
                i.putExtra("orderid", orderid);
                i.putIntegerArrayListExtra("product_idList", (ArrayList<Integer>) product_id );
                i.putExtra("fromactivity", fromactivity);
                i.putExtra("cancelorder", "bulk");
                startActivity(i);

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),PetMyOrdrersNewActivity.class));
        finish();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_back) {
            onBackPressed();
        }

    }

    @SuppressLint({"LongLogTag", "LogNotTimber"})
    private void vendorOrderDetailsResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<PetLoverVendorOrderDetailsResponse> call = apiInterface.get_product_list_by_petlover_ResponseCall(RestUtils.getContentType(), petLoverVendorOrderDetailsRequest());
        Log.w(TAG,"vendorOrderDetailsResponseCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<PetLoverVendorOrderDetailsResponse>() {
            @SuppressLint({"LongLogTag", "LogNotTimber", "SetTextI18n"})
            @Override
            public void onResponse(@NonNull Call<PetLoverVendorOrderDetailsResponse> call, @NonNull Response<PetLoverVendorOrderDetailsResponse> response) {

                Log.w(TAG,"vendorOrderDetailsResponseCall"+ "--->" + new Gson().toJson(response.body()));

                avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        scrollablContent.setVisibility(View.VISIBLE);

                        if(response.body().getData()!=null){

                            if(response.body().getData().getOrder_details().getOrder_text() !=null && !response.body().getData().getOrder_details().getOrder_text().isEmpty()){
                                txt_product_title.setText(response.body().getData().getOrder_details().getOrder_text());
                            }
                            if(response.body().getData().getOrder_details().getOrder_price()!=0){
                                txt_products_price.setText("\u20B9 "+response.body().getData().getOrder_details().getOrder_price());
                            }

                            if (response.body().getData().getOrder_details().getOrder_price() != 0 && response.body().getData().getOrder_details().getOrder_product() != 0) {
                                if (response.body().getData().getOrder_details().getOrder_product() == 1) {
                                    txt_products_price.setText("\u20B9 " + response.body().getData().getOrder_details().getOrder_price() + " (" + response.body().getData().getOrder_details().getOrder_product() + " product )");
                                } else {
                                    txt_products_price.setText("\u20B9 " + response.body().getData().getOrder_details().getOrder_price() + " (" + response.body().getData().getOrder_details().getOrder_product() + " products )");

                                }
                            }
                            else { if (response.body().getData().getOrder_details().getOrder_product() == 1) {
                                txt_products_price.setText("\u20B9 " + 0 + " (" + response.body().getData().getOrder_details().getOrder_product() + " product )");
                            } else {
                                txt_products_price.setText("\u20B9 " + 0 + " (" + response.body().getData().getOrder_details().getOrder_product() + " products )"); } }






                            if(response.body().getData().getOrder_details().getOrder_booked()!=null){
                                txt_order_date.setText(response.body().getData().getOrder_details().getOrder_booked());
                            }
                            if(response.body().getData().getOrder_details().getOrder_id()!=null){
                                txt_booking_id.setText(response.body().getData().getOrder_details().getOrder_id());

                            }
                            if(response.body().getData().getOrder_details().getOrder_price() !=0){
                                txt_total_order_cost.setText("\u20B9 "+response.body().getData().getOrder_details().getOrder_price());
                            }
                            if(response.body().getData().getOrder_details().getOrder_product() !=0){
                                txt_quantity.setText(""+response.body().getData().getOrder_details().getOrder_product());
                            }

                            if(response.body().getData().getShipping_address() !=null){
                                txt_shipping_address_name.setText(response.body().getData().getShipping_address().getUser_first_name()+" "+response.body().getData().getShipping_address().getUser_last_name());
                                txt_shipping_address_street.setText(response.body().getData().getShipping_address().getUser_flat_no()+" ,"+response.body().getData().getShipping_address().getUser_stree()+", ");
                                txt_shipping_address_city.setText(response.body().getData().getShipping_address().getUser_city());
                                txt_shipping_address_state_pincode.setText(response.body().getData().getShipping_address().getUser_state()+" - "+response.body().getData().getShipping_address().getUser_picocode());
                               if(response.body().getData().getShipping_address().getUser_mobile() != null && !response.body().getData().getShipping_address().getUser_mobile().isEmpty()) {
                                   txt_shipping_address_phone.setText("Phone : " + response.body().getData().getShipping_address().getUser_mobile());
                               }
                                if(response.body().getData().getShipping_address().getUser_landmark() != null && !response.body().getData().getShipping_address().getUser_landmark().isEmpty()) {
                                    txt_shipping_address_landmark.setText("Landmark : " + response.body().getData().getShipping_address().getUser_landmark());
                                }

                            }

                            if (response.body().getData().getOrder_details().getOrder_image() != null && !response.body().getData().getOrder_details().getOrder_image().isEmpty()) {
                                Glide.with(getApplicationContext())
                                        .load(response.body().getData().getOrder_details().getOrder_image())
                                        .into(img_products_image);
                            }
                            else{
                                Glide.with(getApplicationContext())
                                        .load(APIClient.PROFILE_IMAGE_URL)
                                        .into(img_products_image);

                            }

                            if(fromactivity != null && fromactivity.equalsIgnoreCase("FragmentPetLoverNewOrders")){
                                txt_order_status.setText("Booked on");
                                img_order_status.setImageResource(R.drawable.completed);
                                if(response.body().getData().getOrder_details().getOrder_booked() != null){
                                    txt_delivered_date.setText(response.body().getData().getOrder_details().getOrder_booked());
                                }
                            }
                            else if(fromactivity != null && fromactivity.equalsIgnoreCase("FragmentPetLoverCompletedOrders")){
                                txt_order_status.setText("Delivered on");
                                img_order_status.setImageResource(R.drawable.completed);
                                if(response.body().getData().getOrder_details().getOrder_completed() != null){
                                    txt_delivered_date.setText(response.body().getData().getOrder_details().getOrder_completed());
                                }
                            }
                            else if(fromactivity != null && fromactivity.equalsIgnoreCase("FragmentPetLoverCancelledOrders")) {
                                txt_order_status.setText("Cancelled on");
                                img_order_status.setImageResource(R.drawable.ic_baseline_cancel_24);
                                if (response.body().getData().getOrder_details().getOrder_cancelled() != null && !response.body().getData().getOrder_details().getOrder_cancelled().isEmpty()) {
                                    txt_delivered_date.setText(response.body().getData().getOrder_details().getOrder_cancelled());
                                }


                            }

                            if(response.body().getData().getProduct_details() != null && response.body().getData().getProduct_details().size()>0){
                                productdetailslist = response.body().getData().getProduct_details();
                                Log.w(TAG,"ProductsDetails size : "+productdetailslist.size()+" productdetails "+new Gson().toJson(productdetailslist));
                                for(int i=0; i<productdetailslist.size();i++){
                                    int productid = response.body().getData().getProduct_details().get(i).getProduct_id();
                                    String productstatus =  response.body().getData().getProduct_details().get(i).getProduct_stauts();
                                    product_id.add(productid);
                                    if(productstatus != null){
                                        if(productstatus.equalsIgnoreCase("Order Booked")){
                                            txt_cancell_order.setVisibility(View.VISIBLE);
                                        }

                                    }
                                }

                                if(fromactivity != null && fromactivity.equalsIgnoreCase("FragmentPetLoverCancelledOrders")) {
                                    txt_cancell_order.setVisibility(View.GONE);
                                }
                                Log.w(TAG,"product_id List : "+new Gson().toJson(product_id));

                                rv_productdetails.setVisibility(View.VISIBLE);
                                txt_no_products.setVisibility(View.GONE);
                                setView(response.body().getData().getProduct_details());
                            }else{
                                rv_productdetails.setVisibility(View.GONE);
                                txt_no_products.setVisibility(View.VISIBLE);
                                txt_no_products.setText("No products found");
                            }






                        }


                    }

                }


            }

            @SuppressLint({"LongLogTag", "LogNotTimber"})
            @Override
            public void onFailure(@NonNull Call<PetLoverVendorOrderDetailsResponse> call, @NonNull Throwable t) {

                avi_indicator.smoothToHide();
                Log.w(TAG,"VendorOrderDetailsResponse flr"+"--->" + t.getMessage());
            }
        });

    }
    @SuppressLint({"LongLogTag", "LogNotTimber"})
    private PetLoverVendorOrderDetailsRequest petLoverVendorOrderDetailsRequest() {
        PetLoverVendorOrderDetailsRequest petLoverVendorOrderDetailsRequest = new PetLoverVendorOrderDetailsRequest();
        petLoverVendorOrderDetailsRequest.setOrder_id(_id);
        Log.w(TAG,"vendorOrderDetailsRequest"+ "--->" + new Gson().toJson(petLoverVendorOrderDetailsRequest));
        return petLoverVendorOrderDetailsRequest;
    }
    private void setView(List<PetLoverVendorOrderDetailsResponse.DataBean.ProductDetailsBean> product_details) {
        rv_productdetails.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_productdetails.setItemAnimator(new DefaultItemAnimator());
        ProductDetailsAdapter productDetailsAdapter = new ProductDetailsAdapter(getApplicationContext(),product_details,orderid,fromactivity);
        rv_productdetails.setAdapter(productDetailsAdapter);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}