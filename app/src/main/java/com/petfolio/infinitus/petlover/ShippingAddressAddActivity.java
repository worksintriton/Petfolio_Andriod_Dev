package com.petfolio.infinitus.petlover;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.adapter.PetCompletedAppointmentAdapter;
import com.petfolio.infinitus.adapter.ShippingAddressListAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.interfaces.OnDeleteShipAddrListener;
import com.petfolio.infinitus.interfaces.OnEditShipAddrListener;
import com.petfolio.infinitus.interfaces.OnSelectingShipIdListener;
import com.petfolio.infinitus.requestpojo.ShippingAddrMarkAsLastUsedRequest;
import com.petfolio.infinitus.requestpojo.ShippingAddrMarkAsLastUsedResponse;
import com.petfolio.infinitus.requestpojo.ShippingAddressDeleteRequest;
import com.petfolio.infinitus.requestpojo.ShippingAddressFetchByUserIDRequest;
import com.petfolio.infinitus.requestpojo.ShippingAddressListingByUserIDRequest;
import com.petfolio.infinitus.responsepojo.CartDetailsResponse;
import com.petfolio.infinitus.responsepojo.ShippingAddressDeleteResponse;
import com.petfolio.infinitus.responsepojo.ShippingAddressFetchByUserIDResponse;
import com.petfolio.infinitus.responsepojo.ShippingAddressListingByUserIDResponse;
import com.petfolio.infinitus.sessionmanager.SessionManager;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.Serializable;
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

public class ShippingAddressAddActivity extends AppCompatActivity implements View.OnClickListener, OnSelectingShipIdListener, OnEditShipAddrListener, OnDeleteShipAddrListener {

    private String TAG = "ShippingAddressAddActivity";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_no_records)
    TextView txt_no_records;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_back)
    ImageView img_back;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_shipping_address)
    RecyclerView rv_shipping_address;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_add_address)
    LinearLayout ll_add_address;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_cancel)
    Button btn_cancel;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_use_this_addreess)
    Button btn_use_this_addreess;

    String userid,fromactivity;

    List<ShippingAddressListingByUserIDResponse.DataBean> dataBeanList;

    Dialog dialog;

    String shippid;

    List<CartDetailsResponse.DataBean> Data = new ArrayList<>();

    private int prodouct_total;

    private int shipping_charge;

    private int discount_price;

    private int grand_total;

    private int prodcut_count;

    private int prodcut_item_count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_address_add);

        Log.w(TAG,"onCreate ");

        ButterKnife.bind(this);

        SessionManager session = new SessionManager(getApplicationContext());

        HashMap<String, String> user = session.getProfileDetails();

        userid = user.get(SessionManager.KEY_ID);

        Log.w(TAG,"User ID:  "+userid);

        ll_add_address.setOnClickListener(this);

        img_back.setOnClickListener(this);

        btn_cancel.setOnClickListener(this);

        btn_use_this_addreess.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            fromactivity = extras.getString("fromactivity");

            Log.w(TAG,"From "+ fromactivity +" : true-->");

            Data = (List<CartDetailsResponse.DataBean>) extras.getSerializable("data");

            prodouct_total = extras.getInt("product_total");

            shipping_charge = extras.getInt("shipping_charge");

            discount_price = extras.getInt("discount_price");

            grand_total = extras.getInt("grand_total");

            prodcut_count = extras.getInt("prodcut_count");

            prodcut_item_count = extras.getInt("prodcut_item_count");


        }


        if (new ConnectionDetector(ShippingAddressAddActivity.this).isNetworkAvailable(ShippingAddressAddActivity.this)) {

            shippingAddressresponseCall(userid);

        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.img_back:
                onBackPressed();
                break;

            case R.id.ll_add_address:
                gotoShippingaddressCreate();
                break;

            case R.id.btn_cancel:
                onBackPressed();
                break;

            case R.id.btn_use_this_addreess:
                gotoShippingAddressActivity();
                break;
        }

    }

    @SuppressLint("LogNotTimber")
    private void shippingAddressresponseCall(String userid) {

        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<ShippingAddressListingByUserIDResponse> call = apiInterface.list_shipp_addr_ResponseCall(RestUtils.getContentType(), shippingAddressListingByUserIDRequest(userid));

        Log.w(TAG,"ShippingAddressListingByUserIDResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<ShippingAddressListingByUserIDResponse>() {
            @Override
            public void onResponse(@NonNull Call<ShippingAddressListingByUserIDResponse> call, @NonNull Response<ShippingAddressListingByUserIDResponse> response) {

                Log.w(TAG,"ShippingAddressListingByUserIDResponse"+ "--->" + new Gson().toJson(response.body()));

                avi_indicator.smoothToHide();

                if (response.body() != null) {

                    if(response.body().getCode() == 200){

                        if(response.body().getData()!=null&&!(response.body().getData().isEmpty()))
                        {
                            dataBeanList = response.body().getData();

                            if(dataBeanList.size()>0)
                            {

                                setView();

                            }

                        }

                            else {

                                showNoAddressAlert();

                                rv_shipping_address.setVisibility(View.GONE);

                                txt_no_records.setVisibility(View.VISIBLE);

                                avi_indicator.smoothToHide();

                            }

                        }

                    }
                    else{
                        //showErrorLoading(response.body().getMessage());
                        avi_indicator.smoothToHide();

                        Toasty.warning(ShippingAddressAddActivity.this,"Server Issue",Toasty.LENGTH_LONG).show();
                    }
                }

            @Override
            public void onFailure(@NonNull Call<ShippingAddressListingByUserIDResponse> call, @NonNull Throwable t) {

                avi_indicator.smoothToHide();
                Log.w(TAG,"ShippingAddressFetchByUserIDResponse flr"+"--->" + t.getMessage());
            }
        });


    }

    @SuppressLint("LogNotTimber")
    private ShippingAddressListingByUserIDRequest shippingAddressListingByUserIDRequest(String userid) {


        /**
         * user_id : 6048589d0b3a487571a1c567
         */


        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        ShippingAddressListingByUserIDRequest shippingAddressListingByUserIDRequest = new ShippingAddressListingByUserIDRequest();
        shippingAddressListingByUserIDRequest.setUser_id(userid);


        Log.w(TAG,"shippingAddressFetchByUserIDRequest"+ "--->" + new Gson().toJson(shippingAddressListingByUserIDRequest));
        return shippingAddressListingByUserIDRequest;
    }

    private void markAsLastUsedResponseCall(String shipsid){

        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<ShippingAddrMarkAsLastUsedResponse> call = apiInterface.mark_shipp_addr_ResponseCall(RestUtils.getContentType(), shippingAddrMarkAsLastUsedRequest(shipsid));

        Log.w(TAG,"ShippingAddrMarkAsLastUsedResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<ShippingAddrMarkAsLastUsedResponse>() {
            @Override
            public void onResponse(@NonNull Call<ShippingAddrMarkAsLastUsedResponse> call, @NonNull Response<ShippingAddrMarkAsLastUsedResponse> response) {

                Log.w(TAG,"ShippingAddrMarkAsLastUsedResponse"+ "--->" + new Gson().toJson(response.body()));

                avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getCode() == 200){

                        Intent intent = new Intent(ShippingAddressAddActivity.this, ShippingAddressActivity.class);

                        intent.putExtra("fromactivity",TAG);

                        intent.putExtra("data", (Serializable) Data);

                        intent.putExtra("product_total",prodouct_total);

                        intent.putExtra("shipping_charge",shipping_charge);

                        intent.putExtra("discount_price",discount_price);

                        intent.putExtra("grand_total",grand_total);

                        intent.putExtra("prodcut_count",prodcut_count);

                        intent.putExtra("prodcut_item_count",prodcut_item_count);

                        startActivity(intent);

                        finish();

                    }
                    else{
                        //showErrorLoading(response.body().getMessage());
                        avi_indicator.smoothToHide();

                        Toasty.warning(ShippingAddressAddActivity.this,"Server Issue",Toasty.LENGTH_LONG).show();
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<ShippingAddrMarkAsLastUsedResponse> call, @NonNull Throwable t) {

                avi_indicator.smoothToHide();
                Log.w(TAG,"ShippingAddrMarkAsLastUsedResponse flr"+"--->" + t.getMessage());
            }
        });


    }

    @SuppressLint("LogNotTimber")
    private ShippingAddrMarkAsLastUsedRequest shippingAddrMarkAsLastUsedRequest(String shipid) {

        /**
         * _id : 6058f4ebe748565ddb1fc515
         * user_id : 604081d12c2b43125f8cb840
         * user_address_stauts : Last Used
         */

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        ShippingAddrMarkAsLastUsedRequest shippingAddrMarkAsLastUsedRequest = new ShippingAddrMarkAsLastUsedRequest();
        shippingAddrMarkAsLastUsedRequest.set_id(shipid);
        shippingAddrMarkAsLastUsedRequest.setUser_id(userid);
        shippingAddrMarkAsLastUsedRequest.setUser_address_stauts("Last Used");


        Log.w(TAG,"shippingAddrMarkAsLastUsedRequest"+ "--->" + new Gson().toJson(shippingAddrMarkAsLastUsedRequest));
        return shippingAddrMarkAsLastUsedRequest;
    }


    private void deleteshipAddrresponseCall(String shippingid) {

        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<ShippingAddressDeleteResponse> call = apiInterface.delete_shipp_addr_ResponseCall(RestUtils.getContentType(), shippingAddressDeleteRequest(shippingid));

        Log.w(TAG,"ShippingAddressDeleteResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<ShippingAddressDeleteResponse>() {
            @Override
            public void onResponse(@NonNull Call<ShippingAddressDeleteResponse> call, @NonNull Response<ShippingAddressDeleteResponse> response) {

                Log.w(TAG,"ShippingAddressDeleteResponse"+ "--->" + new Gson().toJson(response.body()));

                avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getCode() == 200){

                        startActivity(getIntent());

                        finish();

                    }
                    else{
                        //showErrorLoading(response.body().getMessage());
                        avi_indicator.smoothToHide();

                        Toasty.warning(ShippingAddressAddActivity.this,"Server Issue",Toasty.LENGTH_LONG).show();
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<ShippingAddressDeleteResponse> call, @NonNull Throwable t) {

                avi_indicator.smoothToHide();
                Log.w(TAG,"ShippingAddressDeleteResponse flr"+"--->" + t.getMessage());
            }
        });


    }

    @SuppressLint("LogNotTimber")
    private ShippingAddressDeleteRequest shippingAddressDeleteRequest(String shipid) {


        /**
         * _id : 6057129e2e3b894a69767d40
         */

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        ShippingAddressDeleteRequest shippingAddressDeleteRequest = new ShippingAddressDeleteRequest();
        shippingAddressDeleteRequest.set_id(shipid);


        Log.w(TAG,"shippingAddressDeleteRequest"+ "--->" + new Gson().toJson(shippingAddressDeleteRequest));
        return shippingAddressDeleteRequest;
    }


    private void showNoAddressAlert() {

        try{

            dialog = new Dialog(ShippingAddressAddActivity.this);
            dialog.setContentView(R.layout.alert_cancel_layout);
            dialog.setCanceledOnTouchOutside(false);
            Button btn_ok = dialog.findViewById(R.id.btn_ok);
            Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
            TextView txt_msg = dialog.findViewById(R.id.txt_text_message);
            txt_msg.setText("No Address Found ! Please Add Some Address");
            txt_msg.setTextSize(12);
            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    gotoShippingaddressCreate();

                    dialog.dismiss();

                }
            });
            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();

                }
            });

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            dialog.show();


        }
        catch(
                WindowManager.BadTokenException e){
            e.printStackTrace();
        }

    }

    private void showWaring(String shippingid) {

        try{

            dialog = new Dialog(ShippingAddressAddActivity.this);
            dialog.setContentView(R.layout.alert_cancel_layout);
            dialog.setCanceledOnTouchOutside(false);
            Button btn_ok = dialog.findViewById(R.id.btn_ok);
            Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
            TextView txt_msg = dialog.findViewById(R.id.txt_text_message);
            txt_msg.setText("Are you sure want to delete");
            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (new ConnectionDetector(ShippingAddressAddActivity.this).isNetworkAvailable(ShippingAddressAddActivity.this)) {

                        deleteshipAddrresponseCall(shippingid);

                    }

                    dialog.dismiss();

                }
            });
            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();

                }
            });

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            dialog.show();


        }
        catch(
                WindowManager.BadTokenException e){
            e.printStackTrace();
        }
    }


    private void setView() {

        rv_shipping_address.setLayoutManager(new LinearLayoutManager(ShippingAddressAddActivity.this));
        rv_shipping_address.setItemAnimator(new DefaultItemAnimator());
        int size = 3;
        ShippingAddressListAdapter shippingAddressListAdapter = new ShippingAddressListAdapter(ShippingAddressAddActivity.this,dataBeanList,size,this,this,this);
        rv_shipping_address.setAdapter(shippingAddressListAdapter);


    }

    private void gotoShippingaddressCreate() {

        Intent intent = new Intent(ShippingAddressAddActivity.this, ShippingAddressCreateActivity.class);

        intent.putExtra("data", (Serializable) Data);

        intent.putExtra("product_total",prodouct_total);

        intent.putExtra("shipping_charge",shipping_charge);

        intent.putExtra("discount_price",discount_price);

        intent.putExtra("grand_total",grand_total);

        intent.putExtra("prodcut_count",prodcut_count);

        intent.putExtra("prodcut_item_count",prodcut_item_count);


        startActivity(intent);

    }

    private void gotoShippingAddressActivity() {

        if(shippid!=null&&!shippid.isEmpty()){

            markAsLastUsedResponseCall(shippid);

        }

        else
        {

            Toasty.warning(ShippingAddressAddActivity.this,"Plz choose shipping address ", Toasty.LENGTH_LONG).show();
        }


    }

    @Override
    public void OnEditShipAddr(String shipid, String first_name, String last_name, String phonum, String alt_phonum, String flat_no, String state, String street, String landmark, String pincode, String address_type, String date, String address_status, String city) {

        Intent intent = new Intent(getApplicationContext(), ShippingAddressEditActivity.class);

        intent.putExtra("fromactivity", TAG);

        intent.putExtra("shipid",shipid);

        intent.putExtra("first_name",first_name);

        intent.putExtra("last_name",last_name);

        intent.putExtra("phonum",phonum);

        intent.putExtra("alt_phonum",alt_phonum);

        intent.putExtra("flat_no",flat_no);

        intent.putExtra("state",state);

        intent.putExtra("street",street);

        intent.putExtra("landmark",landmark);

        intent.putExtra("pincode",pincode);

        intent.putExtra("address_type",address_type);

        intent.putExtra("date",date);

        intent.putExtra("address_status",address_status);

        intent.putExtra("city",city);

        intent.putExtra("data", (Serializable) Data);

        intent.putExtra("product_total",prodouct_total);

        intent.putExtra("shipping_charge",shipping_charge);

        intent.putExtra("discount_price",discount_price);

        intent.putExtra("grand_total",grand_total);

        intent.putExtra("prodcut_count",prodcut_count);

        intent.putExtra("prodcut_item_count",prodcut_item_count);

        startActivity(intent);

        finish();

    }

    @Override
    public void onSelectShipID(String shipid, String first_name, String last_name, String phonum, String alt_phonum, String flat_no, String state, String street, String landmark, String pincode, String address_type, String date, String address_status) {

        shippid = shipid;
    }

    @Override
    public void OnDeleteShipAddr(String shipid, String first_name, String last_name, String phonum, String alt_phonum, String flat_no, String state, String street, String landmark, String pincode, String address_type, String date, String address_status) {

        showWaring(shipid);
    }
}