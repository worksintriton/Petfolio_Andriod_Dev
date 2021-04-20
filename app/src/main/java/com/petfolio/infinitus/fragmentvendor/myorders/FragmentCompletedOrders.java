package com.petfolio.infinitus.fragmentvendor.myorders;

import android.annotation.SuppressLint;
import android.app.Dialog;
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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.adapter.VendorCompletedOrdersAdapter;
import com.petfolio.infinitus.adapter.VendorOrdersAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.requestpojo.VendorGetsOrderIdRequest;
import com.petfolio.infinitus.requestpojo.VendorNewOrderRequest;
import com.petfolio.infinitus.requestpojo.VendorOrderListRequest;
import com.petfolio.infinitus.responsepojo.VendorGetsOrderIDResponse;
import com.petfolio.infinitus.responsepojo.VendorNewOrderResponse;
import com.petfolio.infinitus.responsepojo.VendorOrderListResponse;
import com.petfolio.infinitus.sessionmanager.SessionManager;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentCompletedOrders extends Fragment  {
    private final String TAG = "FragmentCompletedOrders";

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
    @BindView(R.id.btn_filter)
    Button btn_filter;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refresh_layout;

    Dialog alertDialog;


    SessionManager session;
    String type = "",username = "",userid = "";
    private Context mContext;

    private ShimmerFrameLayout mShimmerViewContainer;
    private View includelayout;

    private List<VendorOrderListResponse.DataBean> orderResponseList;
    private final List<VendorOrderListResponse.DataBean> orderResponseListAll = new ArrayList<>();
    public static final int PAGE_START = 1;
    private int CURRENT_PAGE = PAGE_START;
    private boolean isLoading = false;

    public FragmentCompletedOrders() {

    }

    @SuppressLint({"LogNotTimber", "LongLogTag"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.w(TAG,"onCreateView");

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        View view = inflater.inflate(R.layout.fragment_vendor_completed_orders, container, false);

        ButterKnife.bind(this, view);
        mContext = getActivity();

        includelayout = view.findViewById(R.id.includelayout);
        mShimmerViewContainer = includelayout.findViewById(R.id.shimmer_layout);

        avi_indicator.setVisibility(View.GONE);
        btn_filter.setVisibility(View.GONE);


        session = new SessionManager(getContext());
        HashMap<String, String> user = session.getProfileDetails();

        userid = user.get(SessionManager.KEY_ID);
        username = user.get(SessionManager.KEY_FIRST_NAME);

        Log.w(TAG,"userid"+userid +"username :"+username);

      

        if (new ConnectionDetector(getActivity()).isNetworkAvailable(getActivity())) {
            getVendorOrderIDResponseCall(userid);

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
                            if(APIClient.VENDOR_ID != null && !APIClient.VENDOR_ID.isEmpty()) {
                                vendorCompleteOrderResponseCall(APIClient.VENDOR_ID);
                            }

                        } catch (Exception ignored) {
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 30000);//you can put 30000(30 secs)

        refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        if (new ConnectionDetector(getActivity()).isNetworkAvailable(getActivity())) {
                            if (APIClient.VENDOR_ID != null && !APIClient.VENDOR_ID.isEmpty()) {
                                vendorCompleteOrderResponseCall(APIClient.VENDOR_ID);
                            }
                        }
                    }
                }
        );

        initResultRecylerView();




        return view;
    }



    @SuppressLint({"LongLogTag", "LogNotTimber"})
    private void getVendorOrderIDResponseCall(String userid) {
       /* avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();*/
        mShimmerViewContainer.startShimmerAnimation();

        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<VendorGetsOrderIDResponse> call = apiInterface.vendor_gets_orderbyId_ResponseCall(RestUtils.getContentType(), vendorGetsOrderIdRequest(userid));
        Log.w(TAG,"getVendorOrderIDResponseCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<VendorGetsOrderIDResponse>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(@NonNull Call<VendorGetsOrderIDResponse> call, @NonNull Response<VendorGetsOrderIDResponse> response) {

                Log.w(TAG,"getVendorOrderIDResponseCall"+ "--->" + new Gson().toJson(response.body()));

               // avi_indicator.smoothToHide();

                refresh_layout.setRefreshing(false);

                if (response.body() != null) {
                    if(response.body().getCode() == 200){

                        if(response.body().getData()!=null){

                            if(response.body().getData().get_id()!=null&&!(response.body().getData().get_id().isEmpty())){
                                APIClient.VENDOR_ID = response.body().getData().get_id();
                                vendorCompleteOrderResponseCall(response.body().getData().get_id());

                            }


                        }


                    }
                    else{
                        showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<VendorGetsOrderIDResponse> call, @NonNull Throwable t) {

                /*avi_indicator.smoothToHide();*/

                mShimmerViewContainer.stopShimmerAnimation();
                includelayout.setVisibility(View.GONE);
                Log.w(TAG,"getVendorOrderIDResponseCall flr"+"--->" + t.getMessage());
            }
        });

    }

    @SuppressLint({"LongLogTag", "LogNotTimber"})
    private VendorGetsOrderIdRequest vendorGetsOrderIdRequest(String userid) {

        VendorGetsOrderIdRequest vendorGetsOrderIdRequest = new VendorGetsOrderIdRequest();

        vendorGetsOrderIdRequest.setUser_id(userid);

        Log.w(TAG,"vendorGetsOrderIdRequest"+ "--->" + new Gson().toJson(vendorGetsOrderIdRequest));
        //  Toasty.success(getApplicationContext(),"fbTokenUpdateRequest : "+new Gson().toJson(fbTokenUpdateRequest), Toast.LENGTH_SHORT, true).show();

        return vendorGetsOrderIdRequest;
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





    @SuppressLint("LogNotTimber")
    private void vendorCompleteOrderResponseCall(String id) {
       /* avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();*/
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<VendorOrderListResponse> call = ApiService.get_grouped_order_by_vendor_ResponseCall(RestUtils.getContentType(),vendorOrderListRequest(id));
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<VendorOrderListResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<VendorOrderListResponse> call, @NonNull Response<VendorOrderListResponse> response) {
                // avi_indicator.smoothToHide();

                mShimmerViewContainer.stopShimmerAnimation();
                includelayout.setVisibility(View.GONE);
                refresh_layout.setRefreshing(false);
                Log.w(TAG,"VendorNewOrderResponse"+ "--->" + new Gson().toJson(response.body()));


                if (response.body() != null) {

                    if(200 == response.body().getCode()){
                        orderResponseList = response.body().getData();

                        for(int i=0;i<orderResponseList.size();i++) {
                            /*
                             * v_order_id : ORDER-1618830809052
                             * v_user_id : 603e27792c2b43125f8cb802
                             * v_shipping_address : 60797c16a20ca32d2668a30c
                             * v_payment_id : pay_H0gNJn2CM7xjO1
                             * v_vendor_id : 604866a50b3a487571a1c568
                             * v_order_product_count : 1
                             * v_order_price : 960
                             * v_order_image : http://54.212.108.156:3000/api/uploads/1615541391131.jpeg
                             * v_order_booked_on : 19-04-2021 12:05 PM
                             * v_order_status : New
                             * v_order_text : Food Products
                             */
                            VendorOrderListResponse.DataBean  dataBean = new VendorOrderListResponse.DataBean();
                            dataBean.setV_order_id(orderResponseList.get(i).getV_order_id());
                            dataBean.setV_user_id(orderResponseList.get(i).getV_user_id());
                            dataBean.setV_shipping_address(orderResponseList.get(i).getV_shipping_address());
                            dataBean.setV_payment_id(orderResponseList.get(i).getV_payment_id());
                            dataBean.setV_vendor_id(orderResponseList.get(i).getV_vendor_id());
                            dataBean.setV_order_product_count(orderResponseList.get(i).getV_order_product_count());
                            dataBean.setV_order_price(orderResponseList.get(i).getV_order_price());
                            dataBean.setV_order_image(orderResponseList.get(i).getV_order_image());
                            dataBean.setV_order_booked_on(orderResponseList.get(i).getV_order_booked_on());
                            dataBean.setV_order_status(orderResponseList.get(i).getV_order_status());
                            dataBean.setV_order_text(orderResponseList.get(i).getV_order_text());
                            orderResponseListAll.add(dataBean);


                        }

                        Log.w(TAG,"Size"+orderResponseListAll.size());
                        Log.w(TAG,"orderResponseListAll : "+new Gson().toJson(orderResponseListAll));
                        if(orderResponseList.size() > 0){
                            txt_no_records.setVisibility(View.GONE);
                            rv_completedappointment.setVisibility(View.VISIBLE);
                            setView(orderResponseList);

                        }
                        else{
                            txt_no_records.setVisibility(View.VISIBLE);
                            txt_no_records.setText("No complete orders");
                            rv_completedappointment.setVisibility(View.GONE);
                        }

                    }



                }
            }

            @Override
            public void onFailure(@NonNull Call<VendorOrderListResponse> call, @NonNull Throwable t) {
                // avi_indicator.smoothToHide();

                mShimmerViewContainer.stopShimmerAnimation();
                includelayout.setVisibility(View.GONE);

                Log.w(TAG,"VendorOrderResponse flr"+"--->" + t.getMessage());
            }
        });

    }
    @SuppressLint("LogNotTimber")
    private VendorOrderListRequest vendorOrderListRequest(String id) {
        /*
         * vendor_id : 604866a50b3a487571a1c568
         * order_status : New
         * skip_count : 1
         */

        VendorOrderListRequest vendorOrderListRequest = new VendorOrderListRequest();
        vendorOrderListRequest.setVendor_id(id);
        vendorOrderListRequest.setOrder_status("Complete");
        vendorOrderListRequest.setSkip_count(CURRENT_PAGE);
        Log.w(TAG,"vendorNewOrderRequest"+ "--->" + new Gson().toJson(vendorOrderListRequest));
        return vendorOrderListRequest;
    }
    private void initResultRecylerView() {
        rv_completedappointment.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @SuppressLint("LogNotTimber")
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == orderResponseListAll.size() - 1) {
                        //bottom of list!
                        CURRENT_PAGE += 1;

                        Log.w(TAG, "isLoading? " + isLoading + " currentPage " + CURRENT_PAGE);
                        isLoading = true;
                        if (APIClient.VENDOR_ID != null && !APIClient.VENDOR_ID.isEmpty()) {
                            vendorCompleteOrderResponseCall(APIClient.VENDOR_ID);
                        }

                    }
                }
            }
        });
    }
    private void setView(List<VendorOrderListResponse.DataBean> orderResponseListAll) {
        rv_completedappointment.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_completedappointment.setItemAnimator(new DefaultItemAnimator());
        VendorOrdersAdapter vendorOrdersAdapter = new VendorOrdersAdapter(getContext(), orderResponseListAll,TAG);
        rv_completedappointment.setAdapter(vendorOrdersAdapter);
        vendorOrdersAdapter.notifyDataSetChanged();
        isLoading = false;
    }





}