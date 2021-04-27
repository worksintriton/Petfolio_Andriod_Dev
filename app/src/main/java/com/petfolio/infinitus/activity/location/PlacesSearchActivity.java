package com.petfolio.infinitus.activity.location;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import com.petfolio.infinitus.R;
import com.petfolio.infinitus.adapter.PlacesResultsAdapter;
import com.petfolio.infinitus.api.API;
import com.petfolio.infinitus.interfaces.PlacesNameListener;
import com.petfolio.infinitus.responsepojo.AddressResultsResponse;
import com.petfolio.infinitus.responsepojo.PlacesResultsResponse;
import com.wang.avi.AVLoadingIndicatorView;


import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlacesSearchActivity extends AppCompatActivity implements PlacesNameListener {

    String TAG = "PlacesSearchActivity";


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_placessearch)
    EditText edtPlacesSearch;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.imgBack)
    ImageView imgBack;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_close)
    ImageView img_close;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_placesresults)
    RecyclerView rv_placesresults;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_norecords)
    TextView tv_norecords;

    private Context mContext;

    private List<PlacesResultsResponse.PredictionsBean> predictionsBeanList;

    String placesresults = "";
    private String fromactivity;

    private String id,userid,locationnickname,LocationType;
    private boolean defaultstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_search);
        Log.w(TAG,"onCreate--->");


        ButterKnife.bind(this);
        mContext = PlacesSearchActivity.this;


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            fromactivity = extras.getString("fromactivity");

            Log.w(TAG,"fromactivity if : "+fromactivity);

            id = extras.getString("id");
            userid = extras.getString("userid");
            locationnickname = extras.getString("nickname");
            LocationType = extras.getString("locationtype");
            defaultstatus = extras.getBoolean("defaultstatus");

        }


        edtPlacesSearch.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.showSoftInput(edtPlacesSearch, InputMethodManager.SHOW_IMPLICIT);

        avi_indicator.setVisibility(View.GONE);
        img_close.setVisibility(View.GONE);
        imgBack.setOnClickListener(v -> onBackPressed());




        img_close.setOnClickListener(v -> {

            if(placesresults != null && !placesresults.isEmpty()){
                edtPlacesSearch.setText("");
                img_close.setVisibility(View.GONE);
            }

        });



        edtPlacesSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                Log.w(TAG,"beforeTextChanged-->"+s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.w(TAG,"onTextChanged-->"+s.toString());
                placesresults = s.toString();
                placesSearchResponseCall(s.toString());
                if(placesresults != null && !placesresults.isEmpty()){
                    img_close.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.w(TAG,"afterTextChanged-->"+s.toString());
                //  placesSearchResponseCall(s.toString());

            }
        });



    }

    private void placesSearchResponseCall(String places) {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API service = retrofit.create(API.class);

        String key = API.MAP_KEY;
        service.getCityResults(places, key).enqueue(new Callback<PlacesResultsResponse>() {
            @Override
            public void onResponse(@NotNull Call<PlacesResultsResponse> call, @NotNull Response<PlacesResultsResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"url  :%s"+ call.request().url().toString());


                Log.w(TAG,"placesSearchResponseCall" + new Gson().toJson(response.body()));


                assert response.body() != null;
                predictionsBeanList = response.body().getPredictions();
                if (predictionsBeanList.size() > 0 ) {
                    rv_placesresults.setVisibility(View.VISIBLE);
                    tv_norecords.setVisibility(View.GONE);
                    setViewPlacesResulsts();
                }else{
                    rv_placesresults.setVisibility(View.GONE);
                    tv_norecords.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onFailure(@NotNull Call<PlacesResultsResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();
                t.printStackTrace();
            }
        });
    }

    private void setViewPlacesResulsts() {
        rv_placesresults.setLayoutManager(new LinearLayoutManager(this));
        rv_placesresults.setItemAnimator(new DefaultItemAnimator());
        PlacesResultsAdapter placesResultsAdapter = new PlacesResultsAdapter(mContext, predictionsBeanList, rv_placesresults,PlacesSearchActivity.this);
        rv_placesresults.setAdapter(placesResultsAdapter);

          }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void selectedPlacesName(String PlacesName,String selectedPlaceName) {
        addressResponseCall(PlacesName,selectedPlaceName);
    }

    private void addressResponseCall(String PlacesName, String selectedPlaceName) {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API service = retrofit.create(API.class);

        String key = API.MAP_KEY;
        service.getaddressResults(PlacesName, key).enqueue(new Callback<AddressResultsResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NotNull Call<AddressResultsResponse> call, @NotNull Response<AddressResultsResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"url  :%s"+ call.request().url().toString());


                Log.w(TAG,"addressResponseCall" + new Gson().toJson(response.body()));


                assert response.body() != null;
                List<AddressResultsResponse.ResultsBean> resultsBeanList = response.body().getResults();
                if (resultsBeanList.size() > 0 ) {
                    double lat = resultsBeanList.get(0).getGeometry().getLocation().getLat();
                    double lon = resultsBeanList.get(0).getGeometry().getLocation().getLng();

                    Log.w(TAG,"addressResponseCall lat-->"+lat+" lon : "+lon);
                    Log.w(TAG,"addressResponseCall cityname-->"+selectedPlaceName+" fromactivity : "+fromactivity);

                    if(fromactivity != null && fromactivity.equalsIgnoreCase("PickUpLocationDenyActivity")){
                        Log.w(TAG,"if-->"+fromactivity);

                        Intent i = new Intent(PlacesSearchActivity.this, PickUpLocationDenyActivity.class);
                        i.putExtra("cityname",selectedPlaceName);
                        i.putExtra("placesearchactivity","placesearchactivity");
                        Bundle b = new Bundle();
                        b.putDouble("lat", lat);
                        b.putDouble("lon", lon);
                        i.putExtras(b);
                        startActivity(i);
                    }else if(fromactivity != null && fromactivity.equalsIgnoreCase("PickUpLocationEditActivity")){
                        Log.w(TAG,"else if-->"+fromactivity);

                        Intent i = new Intent(PlacesSearchActivity.this, PickUpLocationEditActivity.class);
                        i.putExtra("cityname",selectedPlaceName);
                        i.putExtra("placesearchactivity","placesearchactivity");
                        Bundle b = new Bundle();
                        b.putDouble("lat", lat);
                        b.putDouble("lon", lon);
                        i.putExtras(b);

                        i.putExtra("id",id);
                        i.putExtra("userid",userid);
                        i.putExtra("nickname",locationnickname);
                        i.putExtra("locationtype",LocationType);
                        i.putExtra("defaultstatus",defaultstatus);
                        startActivity(i);
                    }else if(fromactivity != null && fromactivity.equalsIgnoreCase("PickUpLocationActivity")){
                        Log.w(TAG,"else if-->"+fromactivity);

                        Intent i = new Intent(PlacesSearchActivity.this, PickUpLocationActivity.class);
                        i.putExtra("cityname",selectedPlaceName);
                        i.putExtra("placesearchactivity","placesearchactivity");
                        Bundle b = new Bundle();
                        b.putDouble("lat", lat);
                        b.putDouble("lon", lon);
                        i.putExtras(b);
                        startActivity(i);
                    }
                    else if(fromactivity != null && fromactivity.equalsIgnoreCase("SetLocationDoctorNewActivity")){
                        Log.w(TAG,"else if-->"+fromactivity);
                        Intent i = new Intent(PlacesSearchActivity.this, SetLocationDoctorNewActivity.class);
                        i.putExtra("cityname",selectedPlaceName);
                        i.putExtra("placesearchactivity","placesearchactivity");
                        Bundle b = new Bundle();
                        b.putDouble("lat", lat);
                        b.putDouble("lon", lon);
                        i.putExtras(b);
                        startActivity(i);
                    }
                    else if(fromactivity != null && fromactivity.equalsIgnoreCase("SetLocationDoctorOldActivity")) {
                        Log.w(TAG,"else if-->"+fromactivity);
                        Intent i = new Intent(PlacesSearchActivity.this, SetLocationDoctorOldActivity.class);
                        i.putExtra("cityname",selectedPlaceName);
                        i.putExtra("placesearchactivity","placesearchactivity");
                        Bundle b = new Bundle();
                        b.putDouble("lat", lat);
                        b.putDouble("lon", lon);
                        i.putExtras(b);
                        startActivity(i);
                    }
                    else if(fromactivity != null && fromactivity.equalsIgnoreCase("SetLocationSPNewActivity")){
                        Log.w(TAG,"else if-->"+fromactivity);
                        Intent i = new Intent(PlacesSearchActivity.this, SetLocationSPNewActivity.class);
                        i.putExtra("cityname",selectedPlaceName);
                        i.putExtra("placesearchactivity","placesearchactivity");
                        Bundle b = new Bundle();
                        b.putDouble("lat", lat);
                        b.putDouble("lon", lon);
                        i.putExtras(b);
                        startActivity(i);
                    }
                    else if(fromactivity != null && fromactivity.equalsIgnoreCase("SetLocationSPOldActivity")) {
                        Log.w(TAG,"else if-->"+fromactivity);
                        Intent i = new Intent(PlacesSearchActivity.this, SetLocationSPOldActivity.class);
                        i.putExtra("cityname",selectedPlaceName);
                        i.putExtra("placesearchactivity","placesearchactivity");
                        Bundle b = new Bundle();
                        b.putDouble("lat", lat);
                        b.putDouble("lon", lon);
                        i.putExtras(b);
                        startActivity(i);
                    }
                    else{
                        Log.w(TAG,"else -->"+"PickUpLocationAllowActivity");

                        Intent i = new Intent(PlacesSearchActivity.this, PickUpLocationAllowActivity.class);
                        i.putExtra("cityname",selectedPlaceName);
                        i.putExtra("placesearchactivity","placesearchactivity");
                        Bundle b = new Bundle();
                        b.putDouble("lat", lat);
                        b.putDouble("lon", lon);
                        i.putExtras(b);
                        startActivity(i);
                    }


                }


            }

            @Override
            public void onFailure(@NotNull Call<AddressResultsResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();
                t.printStackTrace();
            }
        });
    }
}