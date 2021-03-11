package com.petfolio.infinitus.fragmentvendor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.adapter.SPMissedAppointmentAdapter;
import com.petfolio.infinitus.adapter.VendorMissedOrdersAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.requestpojo.SPAppointmentRequest;
import com.petfolio.infinitus.requestpojo.VendorOrderRequest;
import com.petfolio.infinitus.responsepojo.SPAppointmentResponse;
import com.petfolio.infinitus.responsepojo.VendorOrderResponse;
import com.petfolio.infinitus.sessionmanager.SessionManager;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentVendorMissedOrders extends Fragment implements View.OnClickListener {
    private String TAG = "FragmentVendorMissedAppointment";



    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_no_records)
    TextView txt_no_records;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_missedappointment)
    RecyclerView rv_missedappointment;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_load_more)
    Button btn_load_more;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_filter)
    Button btn_filter;



    SessionManager session;
    String type = "",username = "",userid = "";
    private SharedPreferences preferences;
    private Context mContext;
    private List<VendorOrderResponse.DataBean> newOrderResponseList;


    public FragmentVendorMissedOrders() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.w(TAG,"onCreateView");

        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        View view = inflater.inflate(R.layout.fragment_vendor_missed_orders, container, false);

        ButterKnife.bind(this, view);
        mContext = getActivity();

        avi_indicator.setVisibility(View.GONE);
        btn_load_more.setVisibility(View.GONE);
        btn_filter.setVisibility(View.GONE);

        btn_load_more.setOnClickListener(this);

        session = new SessionManager(getContext());
        HashMap<String, String> user = session.getProfileDetails();
        userid = user.get(SessionManager.KEY_ID);
        username = user.get(SessionManager.KEY_FIRST_NAME);

        Log.w(TAG,"userid"+userid +"username :"+username);

      

        if (new ConnectionDetector(getActivity()).isNetworkAvailable(getActivity())) {
            vendorOrderResponseCall();
        }

        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            //your method here
                            if (new ConnectionDetector(getActivity()).isNetworkAvailable(getActivity())) {
                                vendorOrderResponseCall();
                            }

                        } catch (Exception e) {
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 30000);//you can put 30000(30 secs)


        return view;
    }



    private void vendorOrderResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<VendorOrderResponse> call = ApiService.vendorOrderResponseCall(RestUtils.getContentType(),vendorOrderRequest());
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<VendorOrderResponse>() {
            @Override
            public void onResponse(@NonNull Call<VendorOrderResponse> call, @NonNull Response<VendorOrderResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"VendorOrderResponse"+ "--->" + new Gson().toJson(response.body()));


                if (response.body() != null) {

                    if(200 == response.body().getCode()){
                        newOrderResponseList = response.body().getData();
                        Log.w(TAG,"Size"+newOrderResponseList.size());
                        Log.w(TAG,"newOrderResponseList : "+new Gson().toJson(newOrderResponseList));
                        if(response.body().getData().isEmpty()){
                            txt_no_records.setVisibility(View.VISIBLE);
                            txt_no_records.setText("No missed orders");
                            rv_missedappointment.setVisibility(View.GONE);
                            btn_load_more.setVisibility(View.GONE);
                        }
                        else{
                            txt_no_records.setVisibility(View.GONE);
                            rv_missedappointment.setVisibility(View.VISIBLE);
                            if(newOrderResponseList.size()>3){
                                btn_load_more.setVisibility(View.VISIBLE);
                            }else{
                                btn_load_more.setVisibility(View.GONE);
                            }
                            setView();
                        }

                    }



                }
            }

            @Override
            public void onFailure(@NonNull Call<VendorOrderResponse> call, @NonNull Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG,"VendorOrderResponse flr"+"--->" + t.getMessage());
            }
        });

    }
    private VendorOrderRequest vendorOrderRequest() {
        /**
         * user_id : 6025040ee15519672cd0dc02
         * order_deliver_status : missed
         */
        VendorOrderRequest vendorOrderRequest = new VendorOrderRequest();
        vendorOrderRequest.setUser_id("6025040ee15519672cd0dc02");
        vendorOrderRequest.setOrder_deliver_status("Booked");
        Log.w(TAG,"vendorOrderRequest"+ "--->" + new Gson().toJson(vendorOrderRequest));
        return vendorOrderRequest;
    }
    private void setView() {
        rv_missedappointment.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_missedappointment.setItemAnimator(new DefaultItemAnimator());
        int size = 3;
        VendorMissedOrdersAdapter vendorMissedOrdersAdapter = new VendorMissedOrdersAdapter(getContext(), newOrderResponseList,size);
        rv_missedappointment.setAdapter(vendorMissedOrdersAdapter);

    }
    private void setViewLoadMore() {
        rv_missedappointment.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_missedappointment.setItemAnimator(new DefaultItemAnimator());
        int size = newOrderResponseList.size();
        VendorMissedOrdersAdapter vendorMissedOrdersAdapter = new VendorMissedOrdersAdapter(getContext(), newOrderResponseList,size);
        rv_missedappointment.setAdapter(vendorMissedOrdersAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_load_more:
                setViewLoadMore();
                break;
        }
    }
}