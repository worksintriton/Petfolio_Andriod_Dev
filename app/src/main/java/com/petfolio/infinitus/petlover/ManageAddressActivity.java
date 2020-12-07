package com.petfolio.infinitus.petlover;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.adapter.ManageAddressListAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.doctor.DoctorDashboardActivity;
import com.petfolio.infinitus.requestpojo.LocationListAddressRequest;
import com.petfolio.infinitus.responsepojo.LocationListAddressResponse;
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

public class ManageAddressActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ManageAddressActivity" ;
    @BindView(R.id.img_back)
    ImageView img_back;

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;


    @BindView(R.id.txt_no_records)
    TextView txt_no_records;

    @BindView(R.id.txt_savedaddress)
    TextView txt_savedaddress;

    @BindView(R.id.rv_adddress_list)
    RecyclerView rv_adddress_list;

    private String userid;
    private List<LocationListAddressResponse.DataBean> addressList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_address);
        ButterKnife.bind(this);
        avi_indicator.setVisibility(View.GONE);

        Log.w(TAG,"onCreate : ");
        img_back.setOnClickListener(this);

        SessionManager  session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getProfileDetails();
        userid = user.get(SessionManager.KEY_ID);

        if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext())) {
           locationListAddressResponseCall();
        }
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
        finish();
    }


    private void locationListAddressResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<LocationListAddressResponse> call = apiInterface.locationListAddressResponseCall(RestUtils.getContentType(), locationListAddressRequest());
        Log.w(TAG,"locationListAddressResponseCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<LocationListAddressResponse>() {
            @Override
            public void onResponse(@NonNull Call<LocationListAddressResponse> call, @NonNull Response<LocationListAddressResponse> response) {

                Log.w(TAG,"locationListAddressResponseCall"+ "--->" + new Gson().toJson(response.body()));

                avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getCode() == 200){

                        if(response.body().getData().isEmpty()){
                            txt_no_records.setVisibility(View.VISIBLE);
                            txt_no_records.setText("No new address");
                            rv_adddress_list.setVisibility(View.GONE);
                            txt_savedaddress.setVisibility(View.GONE);
                        }
                        else{
                            txt_no_records.setVisibility(View.GONE);
                            rv_adddress_list.setVisibility(View.VISIBLE);
                            txt_savedaddress.setVisibility(View.VISIBLE);
                            addressList = response.body().getData();
                            txt_savedaddress.setText(addressList.size()+" Saved Address");
                            setView();
                        }



                    }
                    else{
                        //showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<LocationListAddressResponse> call, @NonNull Throwable t) {

                avi_indicator.smoothToHide();
                Log.w(TAG,"locationListAddressResponseCall flr"+"--->" + t.getMessage());
            }
        });

    }
    private LocationListAddressRequest locationListAddressRequest() {
        LocationListAddressRequest locationListAddressRequest = new LocationListAddressRequest();
        locationListAddressRequest.setUser_id(userid);
        Log.w(TAG,"locationListAddressRequest"+ "--->" + new Gson().toJson(locationListAddressRequest));
        return locationListAddressRequest;
    }
    private void setView() {
        rv_adddress_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_adddress_list.setItemAnimator(new DefaultItemAnimator());
        ManageAddressListAdapter manageAddressListAdapter = new ManageAddressListAdapter(getApplicationContext(), addressList, rv_adddress_list);
        rv_adddress_list.setAdapter(manageAddressListAdapter);

    }

}