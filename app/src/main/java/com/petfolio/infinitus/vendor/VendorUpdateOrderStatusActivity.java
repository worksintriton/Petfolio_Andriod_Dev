package com.petfolio.infinitus.vendor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.requestpojo.VendorCancelsOrderRequest;
import com.petfolio.infinitus.requestpojo.VendorConfirmsOrderRequest;
import com.petfolio.infinitus.requestpojo.VendorDispatchesOrderRequest;
import com.petfolio.infinitus.requestpojo.VendorFetchOrderDetailsIdRequest;
import com.petfolio.infinitus.responsepojo.VendorCancelsOrderResponse;
import com.petfolio.infinitus.responsepojo.VendorConfirmsOrderResponse;
import com.petfolio.infinitus.responsepojo.VendorDispatchesOrderResponse;
import com.petfolio.infinitus.responsepojo.VendorFetchOrderDetailsResponse;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendorUpdateOrderStatusActivity extends AppCompatActivity implements View.OnClickListener {

    private  String TAG = "VendorUpdateOrderStatusActivity";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_back)
    ImageView img_back;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_products_image)
    ImageView img_products_image;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_product_title)
    TextView txt_product_title;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_toolbar_title)
    TextView txt_toolbar_title;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_products_price)
    TextView txt_products_price;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_order_date)
    TextView txt_order_date;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_booking_id)
    TextView txt_booking_id;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_payment_method)
    TextView txt_payment_method;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_total_order_cost)
    TextView txt_total_order_cost;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_quantity)
    TextView txt_quantity;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_vendor_booked)
    ImageView img_vendor_booked;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_booked_date)
    TextView txt_booked_date;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_vendor_confirmed)
    ImageView img_vendor_confirmed;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_order_confirm_date)
    TextView txt_order_confirm_date;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_edit_order_confirm)
    TextView txt_edit_order_confirm;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_order_reject_date)
    TextView txt_order_reject_date;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_order_vendor_reject_date_reason)
    TextView txt_order_vendor_reject_date_reason;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_vendor_order_rejected)
    ImageView img_vendor_order_rejected;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_edit_order_reject)
    TextView txt_edit_order_reject;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_order_reject_bypetlover)
    LinearLayout ll_order_reject_bypetlover;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_order_reject_date_petlover)
    TextView txt_order_reject_date_petlover;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_order_reject_date_reason)
    TextView txt_order_reject_date_reason;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_vendor_order_rejected_bypetlover)
    ImageView img_vendor_order_rejected_bypetlover;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_edit_order_reject_petlover)
    TextView txt_edit_order_reject_petlover;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_vendor_order_dispatched)
    ImageView img_vendor_order_dispatched;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_order_dispatch_date)
    TextView txt_order_dispatch_date;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_order_dispatch_packdetails)
    TextView txt_order_dispatch_packdetails;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_vendor_order_transit)
    ImageView img_vendor_order_transit;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_order_transit_date)
    TextView txt_order_transit_date;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_order_reject)
    LinearLayout ll_order_reject;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.spr_ordertype)
    Spinner spr_ordertype;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_submit)
    Button btn_submit;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.view_book_to_confirm)
    View view_book_to_confirm;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.view_confirm_to_dipatch)
    View view_confirm_to_dipatch;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.view_dispatch_to_transit)
    View view_dispatch_to_transit;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.view_cancel_to_dispatch)
    View view_cancel_to_dispatch;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.view_reject_to_dispatch)
    View view_reject_to_dispatch;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rl_spinner)
    RelativeLayout rl_spinner;

    String product_title, product_image, order_date, order_id, payment_mode,updated_order_status,order_id_display,order_status;

    int product_pr, order_total, quantity;

    List<VendorFetchOrderDetailsResponse.DataBean.ProdcutTrackDetailsBean> prodcutTrackDetailsBeanList;

    VendorFetchOrderDetailsResponse.DataBean dataBeanList;

    private Dialog alertDialog;

    String fromactivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_update_order_status);

        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            order_id = extras.getString("order_id");

            fromactivity = extras.getString("fromactivity");


        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());

        String currentDateandTime = sdf.format(new Date());


        ArrayList<String> orderTypeArrayList = new ArrayList<>();

        orderTypeArrayList.add("Select Order Status");

        orderTypeArrayList.add("Order Confirmation");

        orderTypeArrayList.add("Order Cancellation");

        orderTypeArrayList.add("Order Dispatched");

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(VendorUpdateOrderStatusActivity.this, R.layout.spinner_item, orderTypeArrayList);

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view


        if (new ConnectionDetector(VendorUpdateOrderStatusActivity.this).isNetworkAvailable(VendorUpdateOrderStatusActivity.this)) {

            fetch_order_details_id(order_id);
        }

        if(fromactivity.equals("VendorNewOrdersAdapter")){

            btn_submit.setVisibility(View.VISIBLE);

            btn_submit.setOnClickListener(this);

            txt_toolbar_title.setText("Update Status");

            rl_spinner.setVisibility(View.VISIBLE);

            spr_ordertype.setAdapter(spinnerArrayAdapter);

            spr_ordertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int arg2, long arg3) {
                    ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.green));
                    updated_order_status = spr_ordertype.getSelectedItem().toString();
                    Log.w(TAG,"selected_order_type : "+updated_order_status);


                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub

                }
            });

        }

        else {

            btn_submit.setVisibility(View.GONE);

            txt_toolbar_title.setText("Track Order");

            rl_spinner.setVisibility(View.GONE);

        }

        img_back.setOnClickListener(this);

    }


    private void checkValidation() {

        if(!(updated_order_status.equals("Select Order Status"))){

            if(updated_order_status.equals("Order Confirmation")){

                if (new ConnectionDetector(VendorUpdateOrderStatusActivity.this).isNetworkAvailable(VendorUpdateOrderStatusActivity.this)) {

                    vendorConfirmsOrder(1, "Order Accept");
                }
            }

            else if(updated_order_status.equals("Order Cancellation")){

                if (new ConnectionDetector(VendorUpdateOrderStatusActivity.this).isNetworkAvailable(VendorUpdateOrderStatusActivity.this)) {

                    vendorCancelsOrder(5, "Vendor cancelled");
                }

            }

            else {

                if (new ConnectionDetector(VendorUpdateOrderStatusActivity.this).isNetworkAvailable(VendorUpdateOrderStatusActivity.this)) {

                    addPackageID();

                }

            }


        }

        else {

            Toasty.warning(VendorUpdateOrderStatusActivity.this, "Please Select Order Type", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint({"LogNotTimber", "LongLogTag"})
    private void addPackageID() {
        try {

            Dialog dialog = new Dialog(VendorUpdateOrderStatusActivity.this);
            dialog.setContentView(R.layout.add_trackid_popup);
            dialog.setCancelable(true);
            EditText edt_addtrackid = dialog.findViewById(R.id.edt_addtrackid);
            Button btn_addtrackid = dialog.findViewById(R.id.btn_addtrackid);

            btn_addtrackid.setOnClickListener(view -> {
                if(edt_addtrackid.getText().toString() != null){
                    dialog.dismiss();
                    if (new ConnectionDetector(VendorUpdateOrderStatusActivity.this).isNetworkAvailable(VendorUpdateOrderStatusActivity.this)) {

                        vendorDispatches(2, edt_addtrackid.getText().toString());
                    }
                }else{
                    showErrorLoading("Please Enter the Package Details");
                }


            });
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }

    }

    public void showErrorLoading(String errormesage){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(VendorUpdateOrderStatusActivity.this);
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
    private void fetch_order_details_id(String order_id) {

        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<VendorFetchOrderDetailsResponse> call = apiInterface.vendor_order_booking_order_fetches_ResponseCall(RestUtils.getContentType(), vendorFetchOrderDetailsIdRequest(order_id));

        Log.w(TAG,"VendorFetchOrderDetailsResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<VendorFetchOrderDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<VendorFetchOrderDetailsResponse> call, @NonNull Response<VendorFetchOrderDetailsResponse> response) {

                Log.w(TAG,"VendorFetchOrderDetailsResponse"+ "--->" + new Gson().toJson(response.body()));

                avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getCode() == 200){

                        if(response.body().getData()!=null){

                            dataBeanList = response.body().getData();

                            product_image = response.body().getData().getProdcut_image();

                            product_title = response.body().getData().getProduct_name();

                            product_pr = response.body().getData().getProduct_price();

                            order_id_display = response.body().getData().getOrder_id();

                            payment_mode = "Online";

                            order_total = response.body().getData().getGrand_total();

                            quantity = response.body().getData().getProduct_quantity();

                            order_status = response.body().getData().getOrder_status();

                            if(response.body().getData().getProdcut_track_details()!=null&&!(response.body().getData().getProdcut_track_details().isEmpty())){

                                prodcutTrackDetailsBeanList = response.body().getData().getProdcut_track_details();

                            }

                            Log.w(TAG + "TL", prodcutTrackDetailsBeanList.toString());

                            setView();
                        }

                    }
                    else{
                        //showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<VendorFetchOrderDetailsResponse> call, @NonNull Throwable t) {

                avi_indicator.smoothToHide();
                Log.w(TAG,"appoinmentCancelledResponseCall flr"+"--->" + t.getMessage());
            }
        });


    }

    @SuppressLint("LogNotTimber")
    private VendorFetchOrderDetailsIdRequest vendorFetchOrderDetailsIdRequest(String order_id) {


        /**
         * _id : 604b387942cb073ec4dfef16
         */


        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        VendorFetchOrderDetailsIdRequest vendorFetchOrderDetailsIdRequest = new VendorFetchOrderDetailsIdRequest();
        vendorFetchOrderDetailsIdRequest.set_id(order_id);


        Log.w(TAG,"appoinmentCancelledRequest"+ "--->" + new Gson().toJson(vendorFetchOrderDetailsIdRequest));
        return vendorFetchOrderDetailsIdRequest;
    }


    @SuppressLint("LogNotTimber")
    private void vendorConfirmsOrder(int activity_id, String activity_title) {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<VendorConfirmsOrderResponse> call = apiInterface.vendor_order_booking_accept_ResponseCall(RestUtils.getContentType(), vendorConfirmsOrderRequest(activity_id,activity_title));

        Log.w(TAG,"vendorConfirmsOrderRequest url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<VendorConfirmsOrderResponse>() {
            @Override
            public void onResponse(@NonNull Call<VendorConfirmsOrderResponse> call, @NonNull Response<VendorConfirmsOrderResponse> response) {

                Log.w(TAG,"appoinmentCancelledResponseCall"+ "--->" + new Gson().toJson(response.body()));

                avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getCode() == 200){

                        startActivity(new Intent(VendorUpdateOrderStatusActivity.this, VendorDashboardActivity.class));

                    }
                    else{
                        //showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<VendorConfirmsOrderResponse> call, @NonNull Throwable t) {

                avi_indicator.smoothToHide();
                Log.w(TAG,"appoinmentCancelledResponseCall flr"+"--->" + t.getMessage());
            }
        });

    }

    @SuppressLint("LogNotTimber")
    private VendorConfirmsOrderRequest vendorConfirmsOrderRequest(int id,String title) {

        /**
         * _id : 6049e4f564a9296f3d7c3327
         * activity_id : 1
         * activity_title : Order Confirm
         * activity_date : 11-03-2021 03:07 PM
         */



        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        VendorConfirmsOrderRequest vendorConfirmsOrderRequest = new VendorConfirmsOrderRequest();
        vendorConfirmsOrderRequest.set_id(order_id);
        vendorConfirmsOrderRequest.setActivity_id(id);
        vendorConfirmsOrderRequest.setActivity_title(title);
        vendorConfirmsOrderRequest.setActivity_date(currentDateandTime);

        Log.w(TAG,"appoinmentCancelledRequest"+ "--->" + new Gson().toJson(vendorConfirmsOrderRequest));
        return vendorConfirmsOrderRequest;
    }

    @SuppressLint("LogNotTimber")
    private void vendorDispatches(int activity_id, String activity_title) {

        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<VendorDispatchesOrderResponse> call = apiInterface.vendor_order_booking_dispatch_ResponseCall(RestUtils.getContentType(), vendorDispatchesOrderRequest(activity_id,activity_title));

        Log.w(TAG,"vendorConfirmsOrderRequest url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<VendorDispatchesOrderResponse>() {
            @Override
            public void onResponse(@NonNull Call<VendorDispatchesOrderResponse> call, @NonNull Response<VendorDispatchesOrderResponse> response) {

                Log.w(TAG,"appoinmentCancelledResponseCall"+ "--->" + new Gson().toJson(response.body()));

                avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getCode() == 200){

                        startActivity(new Intent(VendorUpdateOrderStatusActivity.this, VendorDashboardActivity.class));

                    }
                    else{
                        //showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<VendorDispatchesOrderResponse> call, @NonNull Throwable t) {

                avi_indicator.smoothToHide();
                Log.w(TAG,"appoinmentCancelledResponseCall flr"+"--->" + t.getMessage());
            }
        });

    }

    @SuppressLint("LogNotTimber")
    private VendorDispatchesOrderRequest vendorDispatchesOrderRequest(int id, String title) {

        /**
         * _id : 604b10cc8788633a05dbf018
         * activity_id : 2
         * activity_title : Order Dispatch
         * activity_date : 12-03-2021 12:27 PM
         * vendor_complete_date : 12-03-2021 12:35 PM
         * vendor_complete_info : Tracking-Id : 1234568, You can check the product taacking witn this id
         * order_status : Complete
         */

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        VendorDispatchesOrderRequest vendorDispatchesOrderRequest = new VendorDispatchesOrderRequest();
        vendorDispatchesOrderRequest.set_id(order_id);
        vendorDispatchesOrderRequest.setActivity_id(id);
        vendorDispatchesOrderRequest.setActivity_title(title);
        vendorDispatchesOrderRequest.setActivity_date(currentDateandTime);
        vendorDispatchesOrderRequest.setVendor_complete_date(currentDateandTime);
        vendorDispatchesOrderRequest.setVendor_complete_info("Tracking-Id : 1234568, You can check the product taacking witn this id");
        vendorDispatchesOrderRequest.setOrder_status("Complete");

        Log.w(TAG,"appoinmentCancelledRequest"+ "--->" + new Gson().toJson(vendorDispatchesOrderRequest));
        return vendorDispatchesOrderRequest;
    }

    @SuppressLint("LogNotTimber")
    private void vendorCancelsOrder(int activity_id, String activity_title) {

        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<VendorCancelsOrderResponse> call = apiInterface.vendor_order_booking_cancels_ResponseCall(RestUtils.getContentType(), vendorCancelsOrderRequest(activity_id,activity_title));

        Log.w(TAG,"vendorConfirmsOrderRequest url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<VendorCancelsOrderResponse>() {
            @Override
            public void onResponse(@NonNull Call<VendorCancelsOrderResponse> call, @NonNull Response<VendorCancelsOrderResponse> response) {

                Log.w(TAG,"appoinmentCancelledResponseCall"+ "--->" + new Gson().toJson(response.body()));

                avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getCode() == 200){

                        startActivity(new Intent(VendorUpdateOrderStatusActivity.this, VendorDashboardActivity.class));

                    }
                    else{
                        //showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<VendorCancelsOrderResponse> call, @NonNull Throwable t) {

                avi_indicator.smoothToHide();
                Log.w(TAG,"appoinmentCancelledResponseCall flr"+"--->" + t.getMessage());
            }
        });

    }

    @SuppressLint("LogNotTimber")
    private VendorCancelsOrderRequest vendorCancelsOrderRequest(int id, String title) {

        /**
         * _id : 604b387942cb073ec4dfef16
         * activity_id : 5
         * activity_title : Vendor cancelled
         * activity_date : 11-03-2021 03:07 PM
         * order_status : cancelled
         * vendor_cancell_info : We don't have stock in our company
         * vendor_cancell_date : 11-03-2021 03:07 PM
         */


        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        VendorCancelsOrderRequest vendorCancelsOrderRequest = new VendorCancelsOrderRequest();
        vendorCancelsOrderRequest.set_id(order_id);
        vendorCancelsOrderRequest.setActivity_id(id);
        vendorCancelsOrderRequest.setActivity_title(title);
        vendorCancelsOrderRequest.setActivity_date(currentDateandTime);
        vendorCancelsOrderRequest.setVendor_cancell_date(currentDateandTime);
        vendorCancelsOrderRequest.setVendor_cancell_info("We don't have stock in our company");
        vendorCancelsOrderRequest.setOrder_status("cancelled");

        Log.w(TAG,"appoinmentCancelledRequest"+ "--->" + new Gson().toJson(vendorCancelsOrderRequest));
        return vendorCancelsOrderRequest;
    }

    private void setView() {

        if (product_image != null && !product_image.isEmpty()) {

            Glide.with(this)
                    .load(product_image)
                    .into(img_products_image);

        } else {
            Glide.with(this)
                    .load(R.drawable.image_thumbnail)
                    .into(img_products_image);

        }

        if (product_title != null && !product_title.isEmpty()) {

            txt_product_title.setText(product_title);
        }

        if (product_pr != 0) {

            txt_products_price.setText(" \u20B9 " + product_pr);
        }

        if (order_date != null && !order_date.isEmpty()) {

            txt_order_date.setText(order_date);

        }

        if (order_id != null && !order_id.isEmpty()) {

            txt_booking_id.setText(product_title);
        }

        if (payment_mode != null && !payment_mode.isEmpty()) {

            txt_payment_method.setText(payment_mode);
        }

        if (order_total != 0) {

            txt_total_order_cost.setText(""+order_total);
        }

        if (quantity != 0) {

            txt_quantity.setText(""+quantity);
        }

        Log.w(TAG, "size " + prodcutTrackDetailsBeanList.size());

        for(int i=0; i<prodcutTrackDetailsBeanList.size();i++){

            if(prodcutTrackDetailsBeanList.get(i).getTitle()!=null&&!(prodcutTrackDetailsBeanList.get(i).getTitle().isEmpty())){

                Log.w(TAG, "Title " + i + prodcutTrackDetailsBeanList.get(i).getTitle());

                if(prodcutTrackDetailsBeanList.get(i).getTitle().equals("Order Booked")){

                    if(prodcutTrackDetailsBeanList.get(i).isStatus()){

                        txt_booked_date.setText(" " + prodcutTrackDetailsBeanList.get(i).getDate());

                        txt_booked_date.setTextColor(ContextCompat.getColor(VendorUpdateOrderStatusActivity.this, R.color.black));

                        img_vendor_booked.setImageResource(R.drawable.completed);
                    }

                    else
                    {
                        txt_booked_date.setText(" " );

                        txt_booked_date.setTextColor(ContextCompat.getColor(VendorUpdateOrderStatusActivity.this, R.color.coolGrey));

                        img_vendor_booked.setImageResource(R.drawable.button_grey_circle);

                    }

                }

                else if(prodcutTrackDetailsBeanList.get(i).getTitle().equals("Order Accept")) {

                    if (prodcutTrackDetailsBeanList.get(i).isStatus()) {

                        txt_order_confirm_date.setText(" " + prodcutTrackDetailsBeanList.get(i).getDate());

                        txt_order_confirm_date.setTextColor(ContextCompat.getColor(VendorUpdateOrderStatusActivity.this, R.color.black));

                        view_book_to_confirm.setBackground(ContextCompat.getDrawable(VendorUpdateOrderStatusActivity.this, R.drawable.vertical_lines_green));

                        img_vendor_confirmed.setImageResource(R.drawable.completed);


                    } else {
                        txt_booked_date.setText(" ");

                        txt_order_confirm_date.setTextColor(ContextCompat.getColor(VendorUpdateOrderStatusActivity.this, R.color.coolGrey));

                        view_book_to_confirm.setBackground(ContextCompat.getDrawable(VendorUpdateOrderStatusActivity.this, R.drawable.vertical_dotted_lines_grey));

                        img_vendor_confirmed.setImageResource(R.drawable.button_grey_circle);

                    }

                }

                else if(prodcutTrackDetailsBeanList.get(i).getTitle().equals("Order Dispatch")) {

                    if (prodcutTrackDetailsBeanList.get(i).isStatus()) {

                        txt_order_dispatch_date.setText(" " + prodcutTrackDetailsBeanList.get(i).getDate());

                        txt_order_dispatch_date.setTextColor(ContextCompat.getColor(VendorUpdateOrderStatusActivity.this, R.color.black));

                        txt_order_transit_date.setText(" " + prodcutTrackDetailsBeanList.get(i).getDate());

                        txt_order_transit_date.setTextColor(ContextCompat.getColor(VendorUpdateOrderStatusActivity.this, R.color.black));

                        img_vendor_order_dispatched.setImageResource(R.drawable.completed);

                        img_vendor_order_transit.setImageResource(R.drawable.completed);

                        view_confirm_to_dipatch.setBackground(ContextCompat.getDrawable(VendorUpdateOrderStatusActivity.this, R.drawable.vertical_lines_green));

                        view_dispatch_to_transit.setBackground(ContextCompat.getDrawable(VendorUpdateOrderStatusActivity.this, R.drawable.vertical_lines_green));

                        if(dataBeanList.getVendor_complete_info()!=null&&dataBeanList.getVendor_complete_info().isEmpty()){

                            txt_order_dispatch_packdetails.setText(""+dataBeanList.getVendor_complete_info());

                            txt_order_dispatch_packdetails.setTextColor(ContextCompat.getColor(VendorUpdateOrderStatusActivity.this, R.color.black));
                        }


                    } else {
                        txt_order_dispatch_date.setText(" ");

                        txt_order_transit_date.setText(" ");

                        img_vendor_order_dispatched.setImageResource(R.drawable.button_grey_circle);

                        img_vendor_order_transit.setImageResource(R.drawable.button_grey_circle);

                        txt_order_transit_date.setTextColor(ContextCompat.getColor(VendorUpdateOrderStatusActivity.this, R.color.coolGrey));

                        txt_order_dispatch_date.setTextColor(ContextCompat.getColor(VendorUpdateOrderStatusActivity.this, R.color.coolGrey));

                        view_confirm_to_dipatch.setBackground(ContextCompat.getDrawable(VendorUpdateOrderStatusActivity.this, R.drawable.vertical_dotted_lines_grey));

                        view_dispatch_to_transit.setBackground(ContextCompat.getDrawable(VendorUpdateOrderStatusActivity.this, R.drawable.vertical_dotted_lines_grey));

                        txt_order_dispatch_packdetails.setVisibility(View.GONE);

                    }

                }

                else if(prodcutTrackDetailsBeanList.get(i).getTitle().equals("Order Cancelled")) {

                    if (prodcutTrackDetailsBeanList.get(i).isStatus()) {

                        ll_order_reject_bypetlover.setVisibility(View.VISIBLE);

                        txt_order_reject_date_petlover.setText(" " + prodcutTrackDetailsBeanList.get(i).getDate());

                        txt_order_reject_date_petlover.setTextColor(ContextCompat.getColor(VendorUpdateOrderStatusActivity.this, R.color.black));

                        if(dataBeanList.getUser_cancell_info()!=null&&dataBeanList.getUser_cancell_info().isEmpty()){

                            txt_order_reject_date_reason.setText(""+dataBeanList.getUser_cancell_info());

                        }

                        view_cancel_to_dispatch.setBackground(ContextCompat.getDrawable(VendorUpdateOrderStatusActivity.this, R.drawable.vertical_lines_green));

                       img_vendor_order_rejected_bypetlover.setImageResource(R.drawable.ic_baseline_check_circle_24);


                    } else {

                        ll_order_reject_bypetlover.setVisibility(View.GONE);

                        //txt_order_reject_date_petlover.setText(" " + prodcutTrackDetailsBeanList.get(0).getDate());

                        //img_vendor_order_rejected_bypetlover.setImageResource(R.drawable.ic_baseline_check_circle_24)

                    }

                }

                else if(prodcutTrackDetailsBeanList.get(i).getTitle().equals("Vendor cancelled")) {

                    if (prodcutTrackDetailsBeanList.get(i).isStatus()) {

                        ll_order_reject.setVisibility(View.VISIBLE);

                        txt_order_reject_date.setText(" " + prodcutTrackDetailsBeanList.get(i).getDate());

                        txt_order_reject_date.setTextColor(ContextCompat.getColor(VendorUpdateOrderStatusActivity.this, R.color.black));

                        if(dataBeanList.getVendor_cancell_info()!=null&&dataBeanList.getVendor_cancell_info().isEmpty()){

                            txt_order_vendor_reject_date_reason.setText(""+dataBeanList.getVendor_cancell_info());

                        }

                        view_reject_to_dispatch.setBackground(ContextCompat.getDrawable(VendorUpdateOrderStatusActivity.this, R.drawable.vertical_dotted_line_red));

                        img_vendor_order_rejected.setImageResource(R.drawable.ic_baseline_check_circle_24);


                    } else {

                        ll_order_reject.setVisibility(View.GONE);

                        //txt_order_reject_date.setText(" ");

                       // img_vendor_order_rejected.setImageResource(R.drawable.radio);

                    }

                }


            }

        }


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_submit:
                checkValidation();
                break;

            case R.id.img_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



}