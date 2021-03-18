package com.petfolio.infinitus.vendor;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.requestpojo.VendorFetchOrderDetailsIdRequest;
import com.petfolio.infinitus.responsepojo.VendorFetchOrderDetailsResponse;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendorOrderDetailsActivity extends AppCompatActivity implements View.OnClickListener {


    private  String TAG = "VendorOrderDetailsActivity";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_back)
    ImageView img_back;

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
    @BindView(R.id.txt_order_status)
    TextView txt_order_status;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_order_status)
    ImageView img_order_status;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_date)
    TextView txt_date;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_shipping_address)
    TextView txt_shipping_address;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    String product_title, product_image, order_date, order_id, payment_mode,updated_order_status,fromActivity,order_id_display,order_status_title;

    int order_total, quantity;

    Boolean order_image;

    Double product_pr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_order_details);

        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            order_id = extras.getString("order_id");

            fromActivity = extras.getString("fromactivity");

        }

        if (new ConnectionDetector(VendorOrderDetailsActivity.this).isNetworkAvailable(VendorOrderDetailsActivity.this)) {

                fetch_order_details_id(order_id);

        }

        img_back.setOnClickListener(this);

    }

    @SuppressLint("LogNotTimber")
    private void fetch_order_details_id(String order_id) {

        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<VendorFetchOrderDetailsResponse> call = apiInterface.vendor_order_booking_order_fetches_ResponseCall(RestUtils.getContentType(), vendorFetchOrderDetailsIdRequest(order_id));

        Log.w(TAG,"fetch_order_details_id url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<VendorFetchOrderDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<VendorFetchOrderDetailsResponse> call, @NonNull Response<VendorFetchOrderDetailsResponse> response) {

                Log.w(TAG,"fetch_order_details_id_responseCall"+ "--->" + new Gson().toJson(response.body()));

                avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getCode() == 200){

                        if(response.body().getData()!=null){

                            product_image = response.body().getData().getProdcut_image();

                            product_title = response.body().getData().getProduct_name();

                            product_pr = response.body().getData().getProduct_price();

                            order_id_display = response.body().getData().getOrder_id();

                            payment_mode = "Online";

                            order_total = response.body().getData().getGrand_total();

                            quantity = response.body().getData().getProduct_quantity();

                            updated_order_status = response.body().getData().getOrder_status();

                            if(updated_order_status.equals("New")){

                                order_date = response.body().getData().getDate_of_booking_display();

                                order_status_title = "Delivering on";

                                order_image = false;

                            }

                            else if(updated_order_status.equals("cancelled")){

                                order_date = response.body().getData().getVendor_cancell_date();

                                order_status_title = "Cancelled on";

                                order_image = true;

                            }

                            else {

                                order_date = response.body().getData().getDelivery_date_display();

                                order_status_title = "Delivered on";

                                order_image = false;


                            }


                            setView();


                        }

                    }
                    else{
                        //showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<VendorFetchOrderDetailsResponse> call, @NonNull Throwable t) {

                avi_indicator.smoothToHide();
                Log.w(TAG,"appoinmentCancelledResponseCall flr"+"--->" + t.getMessage());
            }
        });


    }



    @SuppressLint("LogNotTimber")
    private VendorFetchOrderDetailsIdRequest vendorFetchOrderDetailsIdRequest(String order_id) {


        /**
         * _id : 604b387942cb073ec4dfef16
         */


        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        VendorFetchOrderDetailsIdRequest vendorFetchOrderDetailsIdRequest = new VendorFetchOrderDetailsIdRequest();
        vendorFetchOrderDetailsIdRequest.set_id(order_id);


        Log.w(TAG,"appoinmentCancelledRequest"+ "--->" + new Gson().toJson(vendorFetchOrderDetailsIdRequest));
        return vendorFetchOrderDetailsIdRequest;
    }

    private void setView() {

        if(product_image != null&&!product_image.isEmpty()){

            Glide.with(this)
                    .load(product_image)
                    .into(img_products_image);

        }
        else{
            Glide.with(this)
                    .load(R.drawable.image_thumbnail)
                    .into(img_products_image);

        }

        if(product_title != null&&!product_title.isEmpty()){

            txt_product_title.setText(product_title);
        }

        if(product_pr!=0){

            txt_products_price.setText(" \u20B9 "+ product_pr);
        }

        if(order_date != null&&!order_date.isEmpty()){

            txt_order_date.setText(order_date);

        }

        if(order_id != null&&!order_id.isEmpty()){

            txt_booking_id.setText(product_title);
        }

        if(payment_mode != null&&!payment_mode.isEmpty()){

            txt_payment_method.setText(payment_mode);
        }

        if(order_total != 0){

            txt_total_order_cost.setText(""+order_total);
        }

        if(quantity != 0){

            txt_quantity.setText("" +quantity);
        }

        if(order_image){

            img_order_status.setImageResource(R.drawable.ic_baseline_check_circle_24);

        }

        else {

            img_order_status.setImageResource(R.drawable.completed);

        }


        txt_order_status.setText(order_status_title);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.img_back:
                onBackPressed();
                break;

        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}