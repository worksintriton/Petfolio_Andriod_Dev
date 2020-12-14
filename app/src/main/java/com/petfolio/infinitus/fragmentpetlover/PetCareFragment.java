package com.petfolio.infinitus.fragmentpetlover;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.location.Location;

import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.activity.location.PickUpLocationAllowActivity;
import com.petfolio.infinitus.activity.location.PickUpLocationDenyActivity;
import com.petfolio.infinitus.adapter.PetLoverNearByDoctorAdapter;

import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.requestpojo.DoctorSearchRequest;
import com.petfolio.infinitus.responsepojo.DoctorSearchResponse;
import com.petfolio.infinitus.service.GPSTracker;
import com.petfolio.infinitus.sessionmanager.SessionManager;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PetCareFragment extends Fragment implements Serializable, View.OnClickListener  {


    private String TAG = "PetCareFragment";



    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;





    String token = "";
    String type ="";
    String name = "",patientid = "";

    static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    double latitude, longitude;

    private Handler handler = new Handler();
    Runnable runnable;
    private TextView headertitle;


    @BindView(R.id.txt_communicationtype)
    TextView txt_communicationtype;

    @BindView(R.id.switchButton_communcationtype)
    SwitchCompat switchButton_communcationtype;









    Dialog dialog;
    private String userid;

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;


    @BindView(R.id.rv_nearbydoctors)
    RecyclerView rv_nearbydoctors;

    @BindView(R.id.txt_totaldrs)
    TextView txt_totaldrs;

    @BindView(R.id.edt_search)
    EditText edt_search;







    private String AddressLine;
    private SharedPreferences preferences;
    private Context mContext;

    private static final int REQUEST_CHECK_SETTINGS_GPS = 0x1;
    private GoogleApiClient googleApiClient;
    Location mLastLocation;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private GoogleMap mMap;
    private GPSTracker gpsTracker;
    private SupportMapFragment mapFragment;
    private List<DoctorSearchResponse.DataBean> doctorDetailsResponseList;
    private Dialog alertDialog;
    private String searchString = "";
    private int communication_type = 0;


    public PetCareFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.w(TAG,"onCreateView-->");
        View view = inflater.inflate(R.layout.fragment_pet_care, container, false);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        ButterKnife.bind(this, view);
        mContext = getActivity();

        gpsTracker = new GPSTracker(mContext);


        avi_indicator.setVisibility(View.GONE);




        SessionManager sessionManager = new SessionManager(mContext);
        HashMap<String, String> user = sessionManager.getProfileDetails();
        userid = user.get(SessionManager.KEY_ID);
        Log.w(TAG,"customerid-->"+userid);


        switchButton_communcationtype.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    txt_communicationtype.setText("Communicatoin Online");
                    communication_type = 1;
                    if (new ConnectionDetector(mContext).isNetworkAvailable(mContext)) {
                        doctorSearchResponseCall(searchString,communication_type);
                    }
                }else{
                    txt_communicationtype.setText("Communicatoin Offline");
                    communication_type = 0;
                    if (new ConnectionDetector(mContext).isNetworkAvailable(mContext)) {
                        doctorSearchResponseCall(searchString,communication_type);
                    }

                }
            }
        });


        if (new ConnectionDetector(mContext).isNetworkAvailable(mContext)) {
            doctorSearchResponseCall(searchString,communication_type);
        }
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                Log.w(TAG,"beforeTextChanged-->"+s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.w(TAG,"onTextChanged-->"+s.toString());
                searchString = s.toString();
                if(!searchString.isEmpty()){
                    doctorSearchResponseCall(searchString, communication_type);
                   /* img_search.setVisibility(View.VISIBLE);
                    img_clear.setVisibility(View.VISIBLE);*/

                }else{
                    searchString ="";
                    doctorSearchResponseCall(searchString, communication_type);
                    /*img_search.setVisibility(View.GONE);
                    img_clear.setVisibility(View.GONE);*/
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.w(TAG,"afterTextChanged-->"+s.toString());

            }
        });



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }










    @Override
    public void onClick(View v) {
        switch (v.getId()){



        }

    }


    private void doctorSearchResponseCall(String searchString, int communication_type) {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<DoctorSearchResponse> call = apiInterface.doctorSearchResponseCall(RestUtils.getContentType(), doctorSearchRequest(searchString,communication_type));
        Log.w(TAG,"DoctorSearchResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<DoctorSearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<DoctorSearchResponse> call, @NonNull Response<DoctorSearchResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"DoctorSearchResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    if (200 == response.body().getCode()) {


                        if (response.body().getData() != null) {
                            doctorDetailsResponseList = response.body().getData();
                            Log.w(TAG, "doctorDetailsResponseList Size" + doctorDetailsResponseList.size());
                            if (doctorDetailsResponseList != null && doctorDetailsResponseList.size()>0) {
                                rv_nearbydoctors.setVisibility(View.VISIBLE);
                                setViewDoctors(doctorDetailsResponseList);
                            } else {
                                rv_nearbydoctors.setVisibility(View.GONE);

                            }

                        }



                    }
                    else {
                        showErrorLoading(response.body().getMessage());
                    }

                }


            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<DoctorSearchResponse> call,@NonNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.e("DoctorSearchResponse", "--->" + t.getMessage());
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }



    private void setViewDoctors(List<DoctorSearchResponse.DataBean> doctorDetailsResponseList) {
        rv_nearbydoctors.setLayoutManager(new LinearLayoutManager(mContext));
        rv_nearbydoctors.setItemAnimator(new DefaultItemAnimator());
        PetLoverNearByDoctorAdapter petLoverNearByDoctorAdapter = new PetLoverNearByDoctorAdapter(mContext, doctorDetailsResponseList);
        rv_nearbydoctors.setAdapter(petLoverNearByDoctorAdapter);
    }


    private DoctorSearchRequest doctorSearchRequest(String searchString, int communication_type) {
        /*
         * search_string :
         * communication_type : 0
         * user_id : 5fd227ac80791a71361baad3
         */


        DoctorSearchRequest doctorSearchRequest = new DoctorSearchRequest();
        doctorSearchRequest.setSearch_string(searchString);
        doctorSearchRequest.setCommunication_type(communication_type);
        doctorSearchRequest.setUser_id(userid);
        Log.w(TAG,"doctorSearchRequest"+ new Gson().toJson(doctorSearchRequest));
        return doctorSearchRequest;
    }


    private void showLocationAlert() {

        try {

            Dialog dialog = new Dialog(mContext);
            dialog.setContentView(R.layout.alert_location_allow_deny_layout);
            dialog.setCanceledOnTouchOutside(false);
            Button btn_allow = dialog.findViewById(R.id.btn_allow);
            Button btn_deny = dialog.findViewById(R.id.btn_deny);
            btn_deny.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    startActivity(new Intent(mContext, PickUpLocationDenyActivity.class));

                   // showLocationDenyAlert();


                }
            });
            btn_allow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(mContext, PickUpLocationAllowActivity.class));
                    dialog.dismiss();

                }
            });
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }




    }
    private void showLocationDenyAlert() {

        try {

            Dialog dialog = new Dialog(mContext);
            dialog.setContentView(R.layout.alert_location_deny_layout);
            dialog.setCanceledOnTouchOutside(false);

            ImageView img_close = dialog.findViewById(R.id.img_close);
            img_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(mContext, PickUpLocationDenyActivity.class));
                    dialog.dismiss();

                }
            });
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }




    }

    public void showErrorLoading(String errormesage){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
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

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(Objects.requireNonNull(getActivity()),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(mContext,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {


                }
            } else {
                Toast.makeText(mContext, "permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }



    private void getLatandLong() {
        try {
                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                } else {
                    GPSTracker gps = new GPSTracker(mContext);
                    // Check if GPS enabled
                    if (gps.canGetLocation()) {
                        latitude = gps.getLatitude();
                        longitude = gps.getLongitude();

                        Log.w(TAG, "getLatandLong--->" + "latitude" + " " + latitude + "longitude" + " " + longitude);






                    }
                }




        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}
