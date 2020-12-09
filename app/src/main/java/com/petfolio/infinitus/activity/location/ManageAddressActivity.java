package com.petfolio.infinitus.activity.location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


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
import android.widget.Toast;

import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.adapter.ManageAddressListAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.interfaces.LocationDefaultListener;
import com.petfolio.infinitus.interfaces.LocationDeleteListener;
import com.petfolio.infinitus.petlover.PetLoverProfileScreenActivity;
import com.petfolio.infinitus.requestpojo.LocationDeleteRequest;
import com.petfolio.infinitus.requestpojo.LocationListAddressRequest;
import com.petfolio.infinitus.requestpojo.LocationStatusChangeRequest;
import com.petfolio.infinitus.responsepojo.LocationDeleteResponse;
import com.petfolio.infinitus.responsepojo.LocationListAddressResponse;
import com.petfolio.infinitus.responsepojo.LocationStatusChangeResponse;
import com.petfolio.infinitus.sessionmanager.SessionManager;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;


import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageAddressActivity extends AppCompatActivity implements View.OnClickListener, LocationDeleteListener, LocationDefaultListener {

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

    @BindView(R.id.ll_add_newaddress)
    LinearLayout ll_add_newaddress;


    private String userid;
    private List<LocationListAddressResponse.DataBean> addressList;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_address);
        ButterKnife.bind(this);
        avi_indicator.setVisibility(View.GONE);

        Log.w(TAG,"onCreate : ");
        img_back.setOnClickListener(this);
        ll_add_newaddress.setOnClickListener(this);

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
                case R.id.ll_add_newaddress:
                gotoAddNewAddress();
                break;
        }
    }

    private void gotoAddNewAddress() {
        Intent intent = new Intent(getApplicationContext(),PickUpLocationActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), PetLoverProfileScreenActivity.class));
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
        ManageAddressListAdapter manageAddressListAdapter = new ManageAddressListAdapter(getApplicationContext(), addressList, rv_adddress_list,this,this);
        rv_adddress_list.setAdapter(manageAddressListAdapter);

    }


    @Override
    public void locationDeleteListener(boolean status, String locationid) {
        Log.w(TAG,"locationDeleteListener : "+" status : "+status+" locationid : "+locationid);

        if(!status){
            showStatusAlert(locationid);
        }else{
            Toasty.warning(getApplicationContext(), "Default location cannot be deleted.", Toast.LENGTH_SHORT, true).show();


        }
    }

    private void showStatusAlert(final String locationid) {

        try {

            dialog = new Dialog(ManageAddressActivity.this);
            dialog.setContentView(R.layout.alert_approve_reject_layout);
            TextView tvheader = (TextView)dialog.findViewById(R.id.tvInternetNotConnected);
            tvheader.setText(R.string.deletemsg);
            Button dialogButtonApprove = (Button) dialog.findViewById(R.id.btnApprove);
            dialogButtonApprove.setText("Yes");
            Button dialogButtonRejected = (Button) dialog.findViewById(R.id.btnReject);
            dialogButtonRejected.setText("No");

            dialogButtonApprove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();

                    locationDeleteResponseCall(locationid);


                }
            });
            dialogButtonRejected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();




                }
            });
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }




    }
    private void locationDeleteResponseCall( String locationid) {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();

        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<LocationDeleteResponse> call = apiInterface.locationDeleteResponseCall(RestUtils.getContentType(),locationDeleteRequest(locationid));

        Log.w(TAG,"url  :%s"+call.request().url().toString());

        call.enqueue(new Callback<LocationDeleteResponse>() {
            @Override
            public void onResponse(@NotNull Call<LocationDeleteResponse> call, @NotNull Response<LocationDeleteResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"LocationDeleteResponse"+ "--->" + new Gson().toJson(response.body()));

                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        Toasty.success(getApplicationContext(), "Address Removed Successfully", Toast.LENGTH_SHORT, true).show();
                        finish();
                        overridePendingTransition( 0, 0);
                        startActivity(getIntent());
                        overridePendingTransition( 0, 0);

                    }
                }



            }

            @Override
            public void onFailure(@NotNull Call<LocationDeleteResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG,"LocationDeleteResponseflr"+"--->" + t.getMessage());
            }
        });

    }
    private LocationDeleteRequest locationDeleteRequest(String locationid) {

        /*
          _id : 5f05d911f3090625a91f40c7
         */
        LocationDeleteRequest locationDeleteRequest = new LocationDeleteRequest();
        locationDeleteRequest.set_id(locationid);
        Log.w(TAG,"locationDeleteRequest"+ "--->" + new Gson().toJson(locationDeleteRequest));
        return locationDeleteRequest;
    }

    @Override
    public void locationDefaultListener(boolean status, String locationid,String userid) {
        showLocationStatusChangeAlert(locationid,userid);
    }

    private void showLocationStatusChangeAlert(String locationid, String userid) {

        try {

            dialog = new Dialog(ManageAddressActivity.this);
            dialog.setContentView(R.layout.alert_approve_reject_layout);
            TextView tvheader = (TextView)dialog.findViewById(R.id.tvInternetNotConnected);
            tvheader.setText(R.string.locationstatuschange);
            Button dialogButtonApprove = (Button) dialog.findViewById(R.id.btnApprove);
            dialogButtonApprove.setText("Yes");
            Button dialogButtonRejected = (Button) dialog.findViewById(R.id.btnReject);
            dialogButtonRejected.setText("No");

            dialogButtonApprove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();

                    locationStatusChangeResponseCall(locationid,userid);


                }
            });
            dialogButtonRejected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Toasty.info(context, "Rejected Successfully", Toast.LENGTH_SHORT, true).show();
                    dialog.dismiss();




                }
            });
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }




    }
    private void locationStatusChangeResponseCall(String locationid, String userid) {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<LocationStatusChangeResponse> call = apiInterface.locationStatusChangeResponseCall(RestUtils.getContentType(),locationStatusChangeRequest(locationid,userid));

        Log.w(TAG,"url  :%s"+call.request().url().toString());

        call.enqueue(new Callback<LocationStatusChangeResponse>() {
            @Override
            public void onResponse(@NotNull Call<LocationStatusChangeResponse> call, @NotNull Response<LocationStatusChangeResponse> response) {
                Log.w(TAG,"LocationStatusChangeResponse"+ "--->" + new Gson().toJson(response.body()));
                avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        Toasty.success(getApplicationContext(), "Default Location Changed Successfully", Toast.LENGTH_SHORT, true).show();
                        finish();
                        overridePendingTransition( 0, 0);
                        startActivity(getIntent());
                        overridePendingTransition( 0, 0);


                    }
                }



            }

            @Override
            public void onFailure(@NotNull Call<LocationStatusChangeResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG,"LocationStatusChangeResponseflr"+"--->" + t.getMessage());
            }
        });

    }
    private LocationStatusChangeRequest locationStatusChangeRequest(String locationid,String userid) {

        /*
         * _id : 5fcf2d2a57c8326d894e4d7e
         * user_id : 5fc61b82b750da703e48da78
         * default_status : true
         */

        LocationStatusChangeRequest locationStatusChangeRequest = new LocationStatusChangeRequest();
        locationStatusChangeRequest.set_id(locationid);
        locationStatusChangeRequest.setUser_id(userid);
        locationStatusChangeRequest.setDefault_status(true);
        Log.w(TAG,"locationStatusChangeRequest"+ "--->" + new Gson().toJson(locationStatusChangeRequest));
        return locationStatusChangeRequest;
    }

}