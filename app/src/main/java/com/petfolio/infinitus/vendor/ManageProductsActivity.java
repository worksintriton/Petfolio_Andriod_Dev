package com.petfolio.infinitus.vendor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
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
import com.petfolio.infinitus.adapter.ManageProductsListAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.requestpojo.ManageProductsListRequest;
import com.petfolio.infinitus.responsepojo.ManageProductsListResponse;
import com.petfolio.infinitus.sessionmanager.SessionManager;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageProductsActivity extends AppCompatActivity {

    private String TAG = "ManageProductsActivity";

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
    private String userid;
    private List<ManageProductsListResponse.DataBean> manageProductsListResponseList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_products);
        Log.w(TAG,"onCreate ");
        ButterKnife.bind(this);

        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getProfileDetails();
        userid = user.get(SessionManager.KEY_ID);


        img_back.setOnClickListener(v -> onBackPressed());

        if (new ConnectionDetector(ManageProductsActivity.this).isNetworkAvailable(ManageProductsActivity.this)) {
            getlist_from_vendor_id_ResponseCall();
        }



    }


    @SuppressLint("LogNotTimber")
    private void getlist_from_vendor_id_ResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<ManageProductsListResponse> call = ApiService.getlist_from_vendor_id_ResponseCall(RestUtils.getContentType(),manageProductsListRequest());
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<ManageProductsListResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<ManageProductsListResponse> call, @NonNull Response<ManageProductsListResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"NotificationGetlistResponse"+ "--->" + new Gson().toJson(response.body()));


                if (response.body() != null) {
                    if(response.body().getCode() == 200){

                        if(response.body().getData() != null && response.body().getData().size()>0){
                            manageProductsListResponseList = response.body().getData();
                            txt_no_records.setVisibility(View.GONE);
                            rv_manage_productlist.setVisibility(View.VISIBLE);
                            setView();
                        }else{
                            rv_manage_productlist.setVisibility(View.GONE);
                            txt_no_records.setVisibility(View.VISIBLE);
                            txt_no_records.setText("No products found");

                        }


                    }

                }


            }

            @SuppressLint("LogNotTimber")
            @Override
            public void onFailure(@NonNull Call<ManageProductsListResponse> call, @NonNull Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG,"ManageProductsListResponse flr"+"--->" + t.getMessage());
            }
        });

    }
    @SuppressLint("LogNotTimber")
    private ManageProductsListRequest manageProductsListRequest() {
        /*
         * vendor_id : 5ee3666a5dfb34019b13c3a2
         */
        ManageProductsListRequest manageProductsListRequest = new ManageProductsListRequest();
        manageProductsListRequest.setVendor_id(APIClient.VENDOR_ID);
        Log.w(TAG,"manageProductsListRequest"+ "--->" + new Gson().toJson(manageProductsListRequest));
        return manageProductsListRequest;
    }

    private void setView() {
        rv_manage_productlist.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_manage_productlist.setItemAnimator(new DefaultItemAnimator());
        ManageProductsListAdapter manageProductsListAdapter = new ManageProductsListAdapter(getApplicationContext(), manageProductsListResponseList);
        rv_manage_productlist.setAdapter(manageProductsListAdapter);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}