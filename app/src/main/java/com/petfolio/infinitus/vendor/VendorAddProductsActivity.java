package com.petfolio.infinitus.vendor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.activity.NotificationActivity;
import com.petfolio.infinitus.adapter.ProductsSearchAdapter;
import com.petfolio.infinitus.adapter.VendorAddProductsAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.petlover.AddYourPetActivity;
import com.petfolio.infinitus.requestpojo.FetctProductByCatRequest;
import com.petfolio.infinitus.requestpojo.ShopDashboardRequest;
import com.petfolio.infinitus.responsepojo.CatgoryGetListResponse;
import com.petfolio.infinitus.responsepojo.FetctProductByCatDetailsResponse;
import com.petfolio.infinitus.responsepojo.FetctProductByCatResponse;
import com.petfolio.infinitus.responsepojo.PetTypeListResponse;
import com.petfolio.infinitus.responsepojo.ProductSearchResponse;
import com.petfolio.infinitus.responsepojo.ShopDashboardResponse;
import com.petfolio.infinitus.responsepojo.TodayDealMoreResponse;
import com.petfolio.infinitus.sessionmanager.SessionManager;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendorAddProductsActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private  String TAG = "VendorAddProductsActivity";

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

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.spr_category_type)
    Spinner spr_category_type;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_add_products);

        ButterKnife.bind(this);

        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getProfileDetails();
        userid = user.get(SessionManager.KEY_ID);

        bottom_navigation_view = include_vendor_footer.findViewById(R.id.bottom_navigation_view);
        bottom_navigation_view.setItemIconTintList(null);
        bottom_navigation_view.getMenu().findItem(R.id.home).setChecked(true);
        bottom_navigation_view.setOnNavigationItemSelectedListener(this);

        img_back.setOnClickListener(v -> onBackPressed());

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
                startActivity(intent);


            }
        });

        spr_category_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int arg2, long arg3) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.green));
                strCatType = spr_category_type.getSelectedItem().toString();
                strCatTypeId = hashMap_CatTypeid.get(strCatType);

                Log.w(TAG,"strCatType : "+strCatType+" strCatTypeId :"+strCatTypeId);
                  if(strCatTypeId != null) {
                      if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext())) {
                          fetctProductByCatDetailsResponse(strCatTypeId);
                      }
                  }else{
                      if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext())) {
                          fetctProductByCatDetailsResponse("");
                      }
                  }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });



        if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext())) {
            getlistCatResponseCall();
        }





    }





    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),VendorDashboardActivity.class));
        finish();
    }

    @SuppressLint("LogNotTimber")
    public void getlistCatResponseCall(){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<CatgoryGetListResponse> call = apiInterface.getlistCatResponseCall(RestUtils.getContentType());
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<CatgoryGetListResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<CatgoryGetListResponse> call, @NonNull Response<CatgoryGetListResponse> response) {
                avi_indicator.smoothToHide();
                if (response.body() != null) {
                    if(200 == response.body().getCode()){
                        Log.w(TAG,"CatgoryGetListResponse" + new Gson().toJson(response.body()));
                        if(response.body().getData() != null) {
                            catgoryGetList = response.body().getData();
                        }
                        if(catgoryGetList != null && catgoryGetList.size()>0){
                            setCategoryType(catgoryGetList);
                        }
                    }



                }

            }
            @Override
            public void onFailure(@NonNull Call<CatgoryGetListResponse> call,@NonNull  Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"CatgoryGetListResponse flr"+t.getMessage());
            }
        });

    }
    @SuppressLint("LogNotTimber")
    private void setCategoryType(List<CatgoryGetListResponse.DataBean> catgoryGetList) {
        ArrayList<String> cattypeArrayList = new ArrayList<>();
        cattypeArrayList.add("Select Category Type");
        for (int i = 0; i < catgoryGetList.size(); i++) {
            String catType = catgoryGetList.get(i).getProduct_cate_name();
            hashMap_CatTypeid.put(catgoryGetList.get(i).getProduct_cate_name(), catgoryGetList.get(i).get_id());
            Log.w(TAG,"catType-->"+catType);
            cattypeArrayList.add(catType);
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(VendorAddProductsActivity.this, R.layout.spinner_item, cattypeArrayList);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            spr_category_type.setAdapter(spinnerArrayAdapter);

        }
    }

    @SuppressLint("LogNotTimber")
    public void fetctProductByCatDetailsResponse(String strCatTypeId){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<FetctProductByCatDetailsResponse> call = ApiService.fetctProductByCatDetailsResponse(RestUtils.getContentType(),fetctProductByCatRequest(strCatTypeId));

        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<FetctProductByCatDetailsResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<FetctProductByCatDetailsResponse> call, @NonNull Response<FetctProductByCatDetailsResponse> response) {
                avi_indicator.smoothToHide();


                if (response.body() != null) {
                    if(200 == response.body().getCode()){
                        Log.w(TAG,"FetctProductByCatDetailsResponse" + new Gson().toJson(response.body()));

                        if(response.body().getData()!= null && response.body().getData().size()>0){
                            fetctProductByCatDetailsList = response.body().getData();
                            txt_no_records.setVisibility(View.GONE);
                            rv_manage_productlist.setVisibility(View.VISIBLE);
                            setViewProducts ();

                        }else{
                            rv_manage_productlist.setVisibility(View.GONE);
                            txt_no_records.setVisibility(View.VISIBLE);
                            txt_no_records.setText("No products found");
                        }

                    }
                }

            }


            @Override
            public void onFailure(@NonNull Call<FetctProductByCatDetailsResponse> call, @NonNull  Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"FetctProductByCatDetailsResponse flr"+t.getMessage());
            }
        });

    }
    @SuppressLint("LogNotTimber")
    private FetctProductByCatRequest fetctProductByCatRequest(String strCatTypeId) {
        /*
         * cat_id : 5fec14a5ea832e2e73c1fc79
         * skip_count : 1
         */

        FetctProductByCatRequest fetctProductByCatRequest = new FetctProductByCatRequest();
        fetctProductByCatRequest.setVendor_id(APIClient.VENDOR_ID);
        fetctProductByCatRequest.setCat_id(strCatTypeId);
        fetctProductByCatRequest.setSkip_count(1);
        Log.w(TAG,"fetctProductByCatRequest"+ "--->" + new Gson().toJson(fetctProductByCatRequest));
        return fetctProductByCatRequest;
    }


    public boolean validdSelectCategoryType() {
        if(strCatType.equalsIgnoreCase("Select Category Type")){
            final AlertDialog alertDialog = new AlertDialog.Builder(VendorAddProductsActivity.this).create();
            alertDialog.setMessage(getString(R.string.err_msg_type_of_cattype));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                    (dialog, which) -> alertDialog.cancel());
            alertDialog.show();

            return false;
        }

        return true;
    }

    private void setViewProducts() {
        rv_manage_productlist.setLayoutManager(new GridLayoutManager(this, 2));
        rv_manage_productlist.setItemAnimator(new DefaultItemAnimator());
        VendorAddProductsAdapter vendorAddProductsAdapter = new VendorAddProductsAdapter(getApplicationContext(), fetctProductByCatDetailsList,TAG,"");
        rv_manage_productlist.setAdapter(vendorAddProductsAdapter);
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
}
