package com.petfolio.infinitus.vendor;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.activity.NotificationActivity;
import com.petfolio.infinitus.adapter.VendorAddProductsAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.appUtils.NumericKeyBoardTransformationMethod;
import com.petfolio.infinitus.requestpojo.FetctProductByCatRequest;
import com.petfolio.infinitus.requestpojo.ProductEditRequest;
import com.petfolio.infinitus.requestpojo.ProductVendorCreateRequest;
import com.petfolio.infinitus.responsepojo.CatgoryGetListResponse;
import com.petfolio.infinitus.responsepojo.FetctProductByCatDetailsResponse;
import com.petfolio.infinitus.responsepojo.SuccessResponse;
import com.petfolio.infinitus.responsepojo.TodayDealMoreResponse;
import com.petfolio.infinitus.responsepojo.VendorOrderUpdateResponse;
import com.petfolio.infinitus.sessionmanager.SessionManager;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.nio.channels.InterruptedByTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendorCreateProductsActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private  String TAG = "VendorCreateProductsActivity";

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
    @BindView(R.id.edt_product_price)
    EditText edt_product_price;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_product_thresould)
    EditText edt_product_thresould;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_product_descriptions)
    EditText edt_product_descriptions;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_update)
    Button btn_update;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_notification)
    ImageView img_notification;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_profile)
    ImageView img_profile;



    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.include_vendor_footer)
    View include_vendor_footer;

    BottomNavigationView bottom_navigation_view;

    private String userid;
    private List<CatgoryGetListResponse.DataBean> catgoryGetList;

    HashMap<String, String> hashMap_CatTypeid = new HashMap<>();
    private String strCatType;
    private String strCatTypeId;
    private List<FetctProductByCatDetailsResponse.DataBean> fetctProductByCatDetailsList;
    private String productid;
    private String producttitle;
    private String productdesc;
    private String productimage;
    private Dialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_create_products);

        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            productid = extras.getString("productid");
            producttitle = extras.getString("producttitle");
            productdesc = extras.getString("productdesc");
            productimage = extras.getString("productimage");




            if(producttitle != null){
                txt_product_title.setText(producttitle);
            }
            if(productdesc != null){
                edt_product_descriptions.setText(productdesc);
            }

            if (productimage != null && !productimage.isEmpty()) {
                Glide.with(getApplicationContext())
                        .load(productimage)
                        .into(img_products_image);

            }
            else{
                Glide.with(getApplicationContext())
                        .load(APIClient.PROFILE_IMAGE_URL)
                        .into(img_products_image);

            }

            btn_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addProductValidator();
                }
            });

            img_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });



        }

        img_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NotificationActivity.class));
                finish();

            }
        });
        img_profile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(),VendorProfileScreenActivity.class);
                        intent.putExtra("fromactivity",TAG);
                        intent.putExtra("productid",productid);
                        intent.putExtra("producttitle",producttitle);
                        intent.putExtra("productdesc",productdesc);
                        intent.putExtra("productimage",productimage);
                        startActivity(intent);


                    }
                });

        edt_product_thresould.setTransformationMethod(new NumericKeyBoardTransformationMethod());


        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getProfileDetails();
        userid = user.get(SessionManager.KEY_ID);

        bottom_navigation_view = include_vendor_footer.findViewById(R.id.bottom_navigation_view);
        bottom_navigation_view.setItemIconTintList(null);
        bottom_navigation_view.getMenu().findItem(R.id.home).setChecked(true);
        bottom_navigation_view.setOnNavigationItemSelectedListener(this);

        img_back.setOnClickListener(v -> onBackPressed());












    }





    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),VendorAddProductsActivity.class));
        finish();
    }








    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                callDirections("1");
                break;
            case R.id.feeds:
                callDirections("2");
                break;

            case R.id.community:
                callDirections("3");
                break;

            default:
                return  false;
        }

        return false;
    }
    public void callDirections(String tag){
        Intent intent = new Intent(getApplicationContext(), VendorDashboardActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);
        finish();
    }

    public void addProductValidator() {
        boolean can_proceed = true;



        if (edt_product_price.getText().toString().isEmpty() && edt_product_descriptions.getText().toString().isEmpty() && edt_product_thresould.getText().toString().isEmpty() ) {
            Toasty.warning(getApplicationContext(), "Please enter the fields", Toast.LENGTH_SHORT, true).show();
            can_proceed = false;
        } else if (edt_product_price.getText().toString().trim().equals("")) {
            edt_product_price.setError("Please enter product price");
            edt_product_price.requestFocus();
            can_proceed = false;
        }
        else if (edt_product_thresould.getText().toString().trim().equals("")) {
            edt_product_thresould.setError("Please enter product thresould");
            edt_product_thresould.requestFocus();
            can_proceed = false;
        }else if (edt_product_descriptions.getText().toString().trim().equals("")) {
            edt_product_descriptions.setError("Please enter product descriptions");
            edt_product_descriptions.requestFocus();
            can_proceed = false;
        }


        if (can_proceed) {
            if (new ConnectionDetector(VendorCreateProductsActivity.this).isNetworkAvailable(VendorCreateProductsActivity.this)) {
                vendor_product_create_ResponseCall();
            }

        }





    }

    @SuppressLint({"LogNotTimber", "LongLogTag"})
    private void vendor_product_create_ResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<SuccessResponse> call = apiInterface.vendor_product_create_ResponseCall(RestUtils.getContentType(), productVendorCreateRequest());
        Log.w(TAG,"vendor_product_create_ResponseCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<SuccessResponse>() {
            @Override
            public void onResponse(@NonNull Call<SuccessResponse> call, @NonNull Response<SuccessResponse> response) {

                Log.w(TAG,"vendor_product_create_ResponseCall"+ "--->" + new Gson().toJson(response.body()));

                avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        Toasty.success(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT, true).show();
                        startActivity(new Intent(getApplicationContext(),VendorAddProductsActivity.class));
                        finish();
                    }
                    else{
                        showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<SuccessResponse> call, @NonNull Throwable t) {

                avi_indicator.smoothToHide();
                Log.w(TAG,"productEditResponseCall flr"+"--->" + t.getMessage());
            }
        });

    }
    @SuppressLint({"LogNotTimber", "LongLogTag"})
    private ProductVendorCreateRequest productVendorCreateRequest() {

        /*
         * _id : 60a5df0f785e571920ac46f0
         * cost : 200
         * threshould : 20
         * product_discription : this is good
         * date_and_time : Thu Feb 18 2021 15:05:46 GMT+0530 (India Standard Time)
         * vendor_id : 60ae1b6c48ffef65a41bc545
         * mobile_type : Admin
         */
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        ProductVendorCreateRequest productVendorCreateRequest = new ProductVendorCreateRequest();
        productVendorCreateRequest.set_id(productid);
        productVendorCreateRequest.setCost(Integer.parseInt(edt_product_price.getText().toString()));
        productVendorCreateRequest.setThreshould(edt_product_thresould.getText().toString());
        productVendorCreateRequest.setProduct_discription(edt_product_descriptions.getText().toString());
        productVendorCreateRequest.setVendor_id(APIClient.VENDOR_ID);
        productVendorCreateRequest.setDate_and_time(currentDateandTime);
        productVendorCreateRequest.setMobile_type("Android");
        Log.w(TAG,"productEditRequest"+ "--->" + new Gson().toJson(productVendorCreateRequest));
        return productVendorCreateRequest;
    }

    public void showErrorLoading(String errormesage){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
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


}
