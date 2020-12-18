package com.petfolio.infinitus.fragmentserviceprovider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.tabs.TabLayout;

import com.petfolio.infinitus.R;
import com.petfolio.infinitus.activity.location.PickUpLocationAllowActivity;
import com.petfolio.infinitus.activity.location.PickUpLocationDenyActivity;

import com.petfolio.infinitus.petlover.PetLoverDashboardActivity;

import com.petfolio.infinitus.service.GPSTracker;
import com.petfolio.infinitus.sessionmanager.SessionManager;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;



public class ServiceProviderShopFragment extends Fragment implements Serializable, OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,View.OnClickListener  {


    private String TAG = "ServiceProviderShopFragment";
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;

    String token = "";
    String type ="";

    double latitude, longitude;

    private Handler handler = new Handler();
    Runnable runnable;








    Dialog dialog;
    private String userid;

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
    @BindView(R.id.edt_search)
    EditText edt_search;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_all_categories)
    Button btn_all_categories;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_recent_search)
    TextView txt_recent_search;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_recentsearch)
    RecyclerView rv_recentsearch;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_recent_search_norecord)
    TextView txt_recent_search_norecord;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_today_deal)
    TextView txt_today_deal;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_today_deal)
    RecyclerView rv_today_deal;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_today_deal_norecord)
    TextView txt_today_deal_norecord;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_most_searched_products)
    TextView txt_most_searched_products;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_most_searched_products)
    RecyclerView rv_most_searched_products;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_no_products)
    TextView txt_no_products;






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
    private AlertDialog.Builder alertDialogBuilder;

    private Dialog alertDialog;


    public ServiceProviderShopFragment() {
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
        View view = inflater.inflate(R.layout.fragment_shop_serviceprovider, container, false);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        ButterKnife.bind(this, view);
        mContext = getActivity();

        gpsTracker = new GPSTracker(mContext);

        // SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            mapFragment.getMapAsync(this);
        }
        // R.id.map is a FrameLayout, not a Fragment
        getChildFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();
        googleApiConnected();


        avi_indicator.setVisibility(View.GONE);



        SessionManager sessionManager = new SessionManager(mContext);
        HashMap<String, String> user = sessionManager.getProfileDetails();
        userid = user.get(SessionManager.KEY_ID);
        Log.w(TAG,"customerid-->"+userid);


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

    public void callDirections(String tag){
        Intent intent = new Intent(mContext, PetLoverDashboardActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);


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
                        getCompleteAddressString(latitude,longitude);

                        Log.w(TAG, "getLatandLong--->" + "latitude" + " " + latitude + "longitude" + " " + longitude);


                        if (new ConnectionDetector(mContext).isNetworkAvailable(mContext)) {
                        }



                    }
                }




        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @SuppressLint("LongLogTag")
    private void getCompleteAddressString(double LATITUDE, double LONGITUDE) {

        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                AddressLine = strReturnedAddress.toString();
                Log.w("My Current loction address", strReturnedAddress.toString());
            } else {
                Log.w("My Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My Current loction address", "Canont get Address!");
        }
    }

    private void googleApiConnected() {

        googleApiClient = new GoogleApiClient.Builder(Objects.requireNonNull(mContext)).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).
                addApi(LocationServices.API).build();
        googleApiClient.connect();

    }
    private void checkLocation() {
        try {
            LocationManager lm = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
            boolean gps_enabled = false;
            boolean network_enabled = false;

            try {
                gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch (Exception ignored) {
            }

            try {
                network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            } catch (Exception ignored) {
            }

            if (!gps_enabled && !network_enabled) {

                if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    getMyLocation();
                }

            } else {
                getLatandLong();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        latitude = mLastLocation.getLatitude();
        longitude = mLastLocation.getLongitude();

        Log.w(TAG,"onLocationChanged : "+" latitude : "+latitude+ " longitude : "+longitude);






    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        permissionChecking();
    }
    @Override
    public void onConnectionSuspended(int i) {

    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    @SuppressLint("LongLogTag")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


    }
    private void permissionChecking() {
        if (mContext != null) {
            if (Build.VERSION.SDK_INT >= 23 && (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) &&
                    (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {

                ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 5);

            } else {

                checkLocation();
            }
        }
    }
    public void getMyLocation() {

        if (googleApiClient != null) {

            if (googleApiClient.isConnected()) {
                if(mContext != null){
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.

                        return;
                    }

                }

                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                LocationRequest locationRequest = new LocationRequest();
                locationRequest.setInterval(2000);
                locationRequest.setFastestInterval(2000);
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
                builder.setAlwaysShow(true);
                LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
                PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
                result.setResultCallback(result1 -> {
                    Status status = result1.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            // All location settings are satisfied.
                            // You can initialize location requests here.
                            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

                            getCompleteAddressString(latitude,longitude);



                            if(mContext != null) {
                                if (new ConnectionDetector(mContext).isNetworkAvailable(mContext)) {



                                }
                            }

                            Handler handler = new Handler();
                            int delay = 1000; //milliseconds

                            handler.postDelayed(new Runnable() {
                                @SuppressLint("LongLogTag")
                                public void run() {
                                    //do something
                                    Log.w(TAG, "getMyLocation-->");

                                    //parkingListResponseCall(checkinhours,checkouthours,requestCheckinDate,requestCheckoutDate, selectedVehicleTypeId);


                                }
                            }, delay);


                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS_GPS);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            break;
                    }
                });
            }


        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS_GPS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        getMyLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        getMyLocation();
                        break;
                }
                break;
        }
    }

    public void showAlertDoctorNotAvlLoading(String errormesage){
        alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setMessage(errormesage);
        alertDialogBuilder.setPositiveButton("ok",
                (arg0, arg1) -> hideLoadingDoctornotavl());

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public void hideLoadingDoctornotavl() {
        try {
            if (new ConnectionDetector(mContext).isNetworkAvailable(mContext)) {
            }

            alertDialog.dismiss();

        } catch (Exception ignored) {

        }
    }

}
