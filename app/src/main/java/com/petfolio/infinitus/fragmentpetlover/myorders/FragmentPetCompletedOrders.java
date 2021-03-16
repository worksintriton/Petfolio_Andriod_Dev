package com.petfolio.infinitus.fragmentpetlover.myorders;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.adapter.PetVendorCompletedOrdersAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;

import com.petfolio.infinitus.requestpojo.PetVendorOrderRequest;

import com.petfolio.infinitus.responsepojo.PetVendorOrderResponse;
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


public class FragmentPetCompletedOrders extends Fragment implements View.OnClickListener {
    private final String TAG = "FragmentPetCompletedOrders";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_no_records)
    TextView txt_no_records;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_completedappointment)
    RecyclerView rv_completedappointment;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_load_more)
    Button btn_load_more;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_filter)
    Button btn_filter;

    private ShimmerFrameLayout mShimmerViewContainer;
    private View includelayout;




    SessionManager session;
    String doctorid = "";

    private String userid;
    Dialog alertDialog;

    private List<PetVendorOrderResponse.DataBean> newOrderResponseList;
    Context mContext;


    public FragmentPetCompletedOrders() {

    }

    @SuppressLint({"LogNotTimber", "LongLogTag"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.w(TAG,"onCreateView");

        View view = inflater.inflate(R.layout.fragment_pet_completed_appointment, container, false);

        ButterKnife.bind(this, view);
        mContext = getActivity();


        includelayout = view.findViewById(R.id.includelayout);
        mShimmerViewContainer = includelayout.findViewById(R.id.shimmer_layout);

        avi_indicator.setVisibility(View.GONE);
        btn_load_more.setVisibility(View.GONE);
        btn_filter.setVisibility(View.GONE);

        btn_load_more.setOnClickListener(this);


        session = new SessionManager(getContext());
        HashMap<String, String> user = session.getProfileDetails();
        userid = user.get(SessionManager.KEY_ID);
        Log.w(TAG," userid : "+userid);

        String patientname = user.get(SessionManager.KEY_FIRST_NAME);

        Log.w(TAG,"Doctorid"+doctorid +"patientname :"+patientname);



        if (new ConnectionDetector(getActivity()).isNetworkAvailable(getActivity())) {
            get_order_details_user_id_ResponseCall();
        }
        return view;
    }





    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_load_more) {
            setViewLoadMore();
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


    @SuppressLint({"LogNotTimber", "LongLogTag"})
    private void get_order_details_user_id_ResponseCall() {
       /* avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();*/
        mShimmerViewContainer.startShimmerAnimation();

        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<PetVendorOrderResponse> call = ApiService.get_order_details_user_id_ResponseCall(RestUtils.getContentType(),petVendorOrderRequest());
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<PetVendorOrderResponse>() {
            @SuppressLint({"LogNotTimber", "SetTextI18n"})
            @Override
            public void onResponse(@NonNull Call<PetVendorOrderResponse> call, @NonNull Response<PetVendorOrderResponse> response) {
                /*  avi_indicator.smoothToHide();*/
                mShimmerViewContainer.stopShimmerAnimation();
                includelayout.setVisibility(View.GONE);
                Log.w(TAG,"PetVendorOrderResponse"+ "--->" + new Gson().toJson(response.body()));


                if (response.body() != null) {

                    if(200 == response.body().getCode()) {
                        if (response.body().getData() != null ) {
                            newOrderResponseList = response.body().getData();
                            Log.w(TAG, "Size" + newOrderResponseList.size());
                            Log.w(TAG, "newOrderResponseList : " + new Gson().toJson(newOrderResponseList));
                            if (response.body().getData().isEmpty()) {
                                txt_no_records.setVisibility(View.VISIBLE);
                                txt_no_records.setText("No completed orders");
                                rv_completedappointment.setVisibility(View.GONE);
                                btn_load_more.setVisibility(View.GONE);
                            } else {
                                txt_no_records.setVisibility(View.GONE);
                                rv_completedappointment.setVisibility(View.VISIBLE);
                                if (newOrderResponseList.size() > 3) {
                                    btn_load_more.setVisibility(View.VISIBLE);
                                } else {
                                    btn_load_more.setVisibility(View.GONE);
                                }
                                setView();
                            }

                        }
                    }



                }
            }

            @SuppressLint("LogNotTimber")
            @Override
            public void onFailure(@NonNull Call<PetVendorOrderResponse> call, @NonNull Throwable t) {
                /*   avi_indicator.smoothToHide();*/
                mShimmerViewContainer.stopShimmerAnimation();
                includelayout.setVisibility(View.GONE);

                Log.w(TAG,"PetVendorOrderResponse"+"--->" + t.getMessage());
            }
        });

    }
    @SuppressLint({"LogNotTimber", "LongLogTag"})
    private PetVendorOrderRequest petVendorOrderRequest() {
        /*
         * user_id : 603e27792c2b43125f8cb802
         * order_status : New
         */
        PetVendorOrderRequest petVendorOrderRequest = new PetVendorOrderRequest();
        petVendorOrderRequest.setUser_id(userid);
        petVendorOrderRequest.setOrder_status("Complete");
        Log.w(TAG,"petVendorOrderRequest"+ "--->" + new Gson().toJson(petVendorOrderRequest));
        return petVendorOrderRequest;
    }
    private void setView() {
        rv_completedappointment.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_completedappointment.setItemAnimator(new DefaultItemAnimator());
        int size = 3;
        PetVendorCompletedOrdersAdapter petVendorCompletedOrdersAdapter = new PetVendorCompletedOrdersAdapter(mContext, newOrderResponseList,size);
        rv_completedappointment.setAdapter(petVendorCompletedOrdersAdapter);

    }
    private void setViewLoadMore() {
        rv_completedappointment.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_completedappointment.setItemAnimator(new DefaultItemAnimator());
        int size = newOrderResponseList.size();
        PetVendorCompletedOrdersAdapter petVendorCompletedOrdersAdapter = new PetVendorCompletedOrdersAdapter(mContext, newOrderResponseList,size);
        rv_completedappointment.setAdapter(petVendorCompletedOrdersAdapter);
    }

}