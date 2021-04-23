package com.petfolio.infinitus.fragmentpetlover.myordersnew;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
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
import com.petfolio.infinitus.adapter.PetLoverVendorOrdersAdapter;
import com.petfolio.infinitus.adapter.PetVendorCompletedOrdersAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.interfaces.AddReviewListener;
import com.petfolio.infinitus.requestpojo.AddShopReviewRequest;
import com.petfolio.infinitus.requestpojo.PetLoverVendorOrderListRequest;
import com.petfolio.infinitus.requestpojo.PetVendorOrderRequest;
import com.petfolio.infinitus.responsepojo.AddReviewResponse;
import com.petfolio.infinitus.responsepojo.PetLoverVendorOrderListResponse;
import com.petfolio.infinitus.responsepojo.PetVendorOrderResponse;
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


public class FragmentPetLoverCompletedOrders extends Fragment implements AddReviewListener {
    private final String TAG = "FragmentPetLoverCompletedOrders";

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

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_load_more)
    Button btn_load_more;

    private ShimmerFrameLayout mShimmerViewContainer;
    private View includelayout;




    SessionManager session;
    String doctorid = "";

    private String userid;
    Dialog alertDialog;

    Context mContext;
    private String userrate;

    private List<PetLoverVendorOrderListResponse.DataBean> orderResponseList;
    private final List<PetLoverVendorOrderListResponse.DataBean> orderResponseListAll = new ArrayList<>();

    public static final int PAGE_START = 1;
    private int CURRENT_PAGE = PAGE_START;
    private boolean isLoading = false;


    public FragmentPetLoverCompletedOrders() {

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
        btn_filter.setVisibility(View.GONE);
        btn_load_more.setVisibility(View.GONE);





        session = new SessionManager(getContext());
        HashMap<String, String> user = session.getProfileDetails();
        userid = user.get(SessionManager.KEY_ID);
        Log.w(TAG," userid : "+userid);

        String patientname = user.get(SessionManager.KEY_FIRST_NAME);

        Log.w(TAG,"Doctorid"+doctorid +"patientname :"+patientname);



        if (new ConnectionDetector(getActivity()).isNetworkAvailable(mContext)) {
            get_grouped_order_by_petlover_ResponseCall();
        }

        refresh_layout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        if (new ConnectionDetector(getActivity()).isNetworkAvailable(mContext)) {
                            get_grouped_order_by_petlover_ResponseCall();

                        }

                    }
                }
        );


       /* final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(() -> {
                    try {
                        //your method here
                        get_grouped_order_by_petlover_ResponseCall();
                    }catch (Exception ignored) {
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 30000);//you can put 30000(30 secs)*/

        initResultRecylerView();
        return view;
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




    @SuppressLint({"LongLogTag", "LogNotTimber"})
    @Override
    public void addReviewListener(String id, String userrate, String userfeedback, String appointment_for) {
        Log.w(TAG,"addReviewListener : "+"id : "+id+" userrate : "+userrate+" userfeedback : "+userfeedback+" appointment_for : "+appointment_for);
        showAddReview(id,appointment_for);
    }

    @SuppressLint({"LogNotTimber", "LongLogTag"})
    private void showAddReview(String id,String appointment_for) {
        try {
            Dialog dialog = new Dialog(mContext);
            dialog.setContentView(R.layout.addreview_popup_layout);
            dialog.setCancelable(true);
            RatingBar ratingBar = dialog.findViewById(R.id.ratingBar);
            EditText edt_addreview = dialog.findViewById(R.id.edt_addreview);
            Button btn_addreview = dialog.findViewById(R.id.btn_addreview);

            ratingBar.setOnRatingBarChangeListener((ratingBar1, rating, fromUser) -> {
                userrate = String.valueOf(rating);
                Log.w(TAG,"onRatingChanged userrate : "+userrate);
            });

            btn_addreview.setOnClickListener(view -> {
                if(userrate != null){
                    dialog.dismiss();
                    if (new ConnectionDetector(getActivity()).isNetworkAvailable(getActivity())) {
                            addReviewResponseCall(id, edt_addreview.getText().toString(), userrate);
                    }
                }else{
                    showErrorLoading("Please choose a star.");
                }


            });
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }




    }
    @SuppressLint({"LogNotTimber", "LongLogTag"})
    private void addReviewResponseCall(String id, String userfeedback, String userrate) {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<AddReviewResponse> call = apiInterface.shopaddReviewResponseCall(RestUtils.getContentType(), addShopReviewRequest(id,userfeedback,userrate));
        Log.w(TAG,"addReviewResponseCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<AddReviewResponse>() {
            @Override
            public void onResponse(@NonNull Call<AddReviewResponse> call, @NonNull Response<AddReviewResponse> response) {

                Log.w(TAG,"AddReviewResponse"+ "--->" + new Gson().toJson(response.body()));

                avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        showAddReviewSuccess();



                    }
                    else{
                        showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<AddReviewResponse> call, @NonNull Throwable t) {

                avi_indicator.smoothToHide();
                Log.w(TAG,"AddReviewResponse flr"+"--->" + t.getMessage());
            }
        });

    }
    @SuppressLint({"LogNotTimber", "LongLogTag"})
    private AddShopReviewRequest addShopReviewRequest(String id, String userfeedback, String userrate) {

        /*
         * order_id : 5fd30a701978e618628c966c
         * user_feedback :
         * user_rate : 0
         */

        AddShopReviewRequest addShopReviewRequest = new AddShopReviewRequest();
        addShopReviewRequest.setOrder_id(id);
        if(userfeedback != null){
            addShopReviewRequest.setUser_feedback(userfeedback);

        }else{
            addShopReviewRequest.setUser_feedback("");

        }if(userrate != null){
            addShopReviewRequest.setUser_rate(userrate);

        }else{
            addShopReviewRequest.setUser_rate("");

        }
        Log.w(TAG,"addShopReviewRequest"+ "--->" + new Gson().toJson(addShopReviewRequest));
        return addShopReviewRequest;
    }

    private void showAddReviewSuccess() {
        try {

            Dialog dialog = new Dialog(mContext);
            dialog.setContentView(R.layout.addreview_review_success_layout);
            dialog.setCancelable(false);

            Button btn_back = dialog.findViewById(R.id.btn_back);


            btn_back.setOnClickListener(view -> {
                dialog.dismiss();
                if (new ConnectionDetector(getActivity()).isNetworkAvailable(mContext)) {
                    get_grouped_order_by_petlover_ResponseCall();
                }


            });
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }




    }


    @SuppressLint({"LogNotTimber", "LongLogTag"})
    private void get_grouped_order_by_petlover_ResponseCall() {
       /* avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();*/
        mShimmerViewContainer.startShimmerAnimation();

        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<PetLoverVendorOrderListResponse> call = ApiService.get_grouped_order_by_petlover_ResponseCall(RestUtils.getContentType(),petLoverVendorOrderListRequest());
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<PetLoverVendorOrderListResponse>() {
            @SuppressLint({"LogNotTimber", "SetTextI18n"})
            @Override
            public void onResponse(@NonNull Call<PetLoverVendorOrderListResponse> call, @NonNull Response<PetLoverVendorOrderListResponse> response) {
                /*  avi_indicator.smoothToHide();*/
                mShimmerViewContainer.stopShimmerAnimation();
                includelayout.setVisibility(View.GONE);
                refresh_layout.setRefreshing(false);
                Log.w(TAG,"PetVendorOrderResponse"+ "--->" + new Gson().toJson(response.body()));


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
                            PetLoverVendorOrderListResponse.DataBean  dataBean = new PetLoverVendorOrderListResponse.DataBean();
                            dataBean.setP_order_id(orderResponseList.get(i).getP_order_id());
                            dataBean.setP_user_id(orderResponseList.get(i).getP_user_id());
                            dataBean.setP_shipping_address(orderResponseList.get(i).getP_shipping_address());
                            dataBean.setP_payment_id(orderResponseList.get(i).getP_payment_id());
                            dataBean.setP_vendor_id(orderResponseList.get(i).getP_vendor_id());
                            dataBean.setP_order_product_count(orderResponseList.get(i).getP_order_product_count());
                            dataBean.setP_order_id(orderResponseList.get(i).getP_order_id());
                            dataBean.setP_order_image(orderResponseList.get(i).getP_order_image());
                            dataBean.setP_order_booked_on(orderResponseList.get(i).getP_order_booked_on());
                            dataBean.setP_order_status(orderResponseList.get(i).getP_order_status());
                            dataBean.setP_order_text(orderResponseList.get(i).getP_order_text());
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

            @SuppressLint("LogNotTimber")
            @Override
            public void onFailure(@NonNull Call<PetLoverVendorOrderListResponse> call, @NonNull Throwable t) {
                /*   avi_indicator.smoothToHide();*/
                mShimmerViewContainer.stopShimmerAnimation();
                includelayout.setVisibility(View.GONE);

                Log.w(TAG,"PetVendorOrderResponse"+"--->" + t.getMessage());
            }
        });

    }
    @SuppressLint({"LogNotTimber", "LongLogTag"})
    private PetLoverVendorOrderListRequest petLoverVendorOrderListRequest() {
        /*
         * petlover_id : 603e27792c2b43125f8cb802
         * status : New
         * skip_count : 1
         */
        PetLoverVendorOrderListRequest petLoverVendorOrderListRequest = new PetLoverVendorOrderListRequest();
        petLoverVendorOrderListRequest.setPetlover_id(userid);
        petLoverVendorOrderListRequest.setStatus("Complete");
        petLoverVendorOrderListRequest.setSkip_count(CURRENT_PAGE);
        Log.w(TAG,"petLoverVendorOrderListRequest"+ "--->" + new Gson().toJson(petLoverVendorOrderListRequest));
        return petLoverVendorOrderListRequest;
    }
    private void initResultRecylerView() {
        rv_completedappointment.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @SuppressLint({"LogNotTimber", "LongLogTag"})
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
                        get_grouped_order_by_petlover_ResponseCall();


                    }
                }
            }
        });
    }
    private void setView(List<PetLoverVendorOrderListResponse.DataBean> orderResponseListAll) {
        rv_completedappointment.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_completedappointment.setItemAnimator(new DefaultItemAnimator());
        PetLoverVendorOrdersAdapter vendorOrdersAdapter = new PetLoverVendorOrdersAdapter(getContext(), orderResponseListAll,TAG);
        rv_completedappointment.setAdapter(vendorOrdersAdapter);
        vendorOrdersAdapter.notifyDataSetChanged();
        isLoading = false;
    }


}