package com.petfolio.infinitus.petlover;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.activity.location.PickUpLocationAllowActivity;
import com.petfolio.infinitus.activity.location.PickUpLocationDenyActivity;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.requestpojo.UpdateStatusCancelRequest;
import com.petfolio.infinitus.requestpojo.VendorOrderDetailsRequest;
import com.petfolio.infinitus.responsepojo.DropDownListResponse;
import com.petfolio.infinitus.responsepojo.PetTypeListResponse;
import com.petfolio.infinitus.responsepojo.SuccessResponse;
import com.petfolio.infinitus.responsepojo.VendorOrderDetailsResponse;
import com.petfolio.infinitus.responsepojo.VendorReasonListResponse;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetVendorCancelOrderActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "PetVendorCancelOrderActivity" ;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.spr_reason)
    Spinner spr_reason;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_back)
    ImageView img_back;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_cancel_order)
    Button btn_cancel_order;



    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    private List<DropDownListResponse.DataBean.SpecialzationBean> petSpecilaziationList;
    private String _id;
    HashMap<String, String> hashMap_ReasonTypeid = new HashMap<>();
    private String strSelectedReason = "";
    private Dialog dialog;


    @SuppressLint({"LogNotTimber", "LongLogTag"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_vendor_cancel_order);
        ButterKnife.bind(this);

        img_back.setOnClickListener(this);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            _id = extras.getString("_id");
            Log.w(TAG,"_id : "+_id);


        }

        if (new ConnectionDetector(PetVendorCancelOrderActivity.this).isNetworkAvailable(PetVendorCancelOrderActivity.this)) {
            vendorReasonListResponseCall();

        }

        spr_reason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int arg2, long arg3) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.green));
                strSelectedReason = spr_reason.getSelectedItem().toString();
                Log.w(TAG,"strSelectedReason : "+strSelectedReason);


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        btn_cancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCancelAlert();
            }
        });





    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                onBackPressed();
                break;




        }

    }






    @SuppressLint({"LogNotTimber", "LongLogTag"})
    public void vendorReasonListResponseCall(){

        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<VendorReasonListResponse> call = apiInterface.vendorReasonListResponseCall(RestUtils.getContentType());
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<VendorReasonListResponse>() {
            @SuppressLint({"SetTextI18n", "LongLogTag"})
            @Override
            public void onResponse(@NonNull Call<VendorReasonListResponse> call, @NonNull Response<VendorReasonListResponse> response) {
                avi_indicator.smoothToHide();


                if (response.body() != null) {
                    if(200 == response.body().getCode()){
                        Log.w(TAG,"DropDownListResponse" + new Gson().toJson(response.body()));
                        if(response.body().getData().getCancel_status() != null &&response.body().getData().getCancel_status().size()>0 ) {
                            setReasonList(response.body().getData().getCancel_status());
                        }




                    }

                }

            }

            @Override
            public void onFailure(@NonNull Call<VendorReasonListResponse> call, @NonNull  Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"VendorReasonListResponse flr"+t.getMessage());
            }
        });

    }
    @SuppressLint("LongLogTag")
    private void setReasonList(List<VendorReasonListResponse.DataBean.CancelStatusBean> cancel_status) {
        ArrayList<String> pettypeArrayList = new ArrayList<>();
       // pettypeArrayList.add("Select Pet Type");
        for (int i = 0; i < cancel_status.size(); i++) {
            String petType = cancel_status.get(i).getTitle();
            Log.w(TAG,"petType-->"+petType);
            pettypeArrayList.add(petType);
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(PetVendorCancelOrderActivity.this, R.layout.spinner_item, pettypeArrayList);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            spr_reason.setAdapter(spinnerArrayAdapter);


        }
    }
    private void showCancelAlert() {

        try {

             dialog = new Dialog(PetVendorCancelOrderActivity.this);
            dialog.setContentView(R.layout.alert_cancel_layout);
            dialog.setCanceledOnTouchOutside(false);
            Button btn_ok = dialog.findViewById(R.id.btn_ok);
            Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (new ConnectionDetector(PetVendorCancelOrderActivity.this).isNetworkAvailable(PetVendorCancelOrderActivity.this)) {
                        update_status_cancelResponseCall();

                    }






                }
            });
            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();

                }
            });
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }




    }


    @SuppressLint({"LongLogTag", "LogNotTimber"})
    private void update_status_cancelResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<SuccessResponse> call = apiInterface.update_status_cancelResponseCall(RestUtils.getContentType(), updateStatusCancelRequest());
        Log.w(TAG,"vendorOrderDetailsResponseCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<SuccessResponse>() {
            @SuppressLint({"LongLogTag", "LogNotTimber", "SetTextI18n"})
            @Override
            public void onResponse(@NonNull Call<SuccessResponse> call, @NonNull Response<SuccessResponse> response) {

                Log.w(TAG,"update_status_cancelResponseCall"+ "--->" + new Gson().toJson(response.body()));

                avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        dialog.dismiss();
                        startActivity(new Intent(PetVendorCancelOrderActivity.this,PetMyOrdrersActivity.class));
                        finish();


                    }

                }


            }

            @SuppressLint({"LongLogTag", "LogNotTimber"})
            @Override
            public void onFailure(@NonNull Call<SuccessResponse> call, @NonNull Throwable t) {

                avi_indicator.smoothToHide();
                Log.w(TAG,"update_status_cancelResponseCall flr"+"--->" + t.getMessage());
            }
        });

    }
    @SuppressLint({"LongLogTag", "LogNotTimber"})
    private UpdateStatusCancelRequest updateStatusCancelRequest() {
        /*
         * _id : 604f4386a358454d3208f685
         * activity_id : 4
         * activity_title : Order Cancelled
         * activity_date : 11-03-2021 03:07 PM
         * order_status : Cancelled
         * user_cancell_info : I have order wrongly
         * user_cancell_date : 11-03-2021 03:07 PM
         */

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        UpdateStatusCancelRequest updateStatusCancelRequest = new UpdateStatusCancelRequest();
        updateStatusCancelRequest.set_id(_id);
        updateStatusCancelRequest.setActivity_id(4);
        updateStatusCancelRequest.setActivity_title("Order Cancelled");
        updateStatusCancelRequest.setActivity_date(currentDateandTime);
        updateStatusCancelRequest.setOrder_status("Cancelled");
        updateStatusCancelRequest.setUser_cancell_info(strSelectedReason);
        updateStatusCancelRequest.setUser_cancell_date(currentDateandTime);

        Log.w(TAG,"updateStatusCancelRequest"+ "--->" + new Gson().toJson(updateStatusCancelRequest));

        return updateStatusCancelRequest;
    }



}