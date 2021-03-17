package com.petfolio.infinitus.petlover;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.doctor.DoctorPrescriptionDetailsActivity;
import com.petfolio.infinitus.requestpojo.UpdateStatusCancelRequest;
import com.petfolio.infinitus.requestpojo.UpdateStatusReturnRequest;
import com.petfolio.infinitus.responsepojo.SuccessResponse;
import com.petfolio.infinitus.responsepojo.VendorReasonListResponse;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import es.voghdev.pdfviewpager.library.util.FileUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetReturnOrderActivity extends AppCompatActivity implements View.OnClickListener, DownloadFile.Listener {

    String TAG = "PetReturnOrderActivity";

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
    @BindView(R.id.txt_products_price)
    TextView txt_products_price;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_delivered_date)
    TextView txt_delivered_date;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rg_return_reason)
    RadioGroup rg_return_reason;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_comment)
    EditText edt_comment;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_continue)
    Button btn_continue;

    private RemotePDFViewPager remotePDFViewPager;
    private PDFPagerAdapter pdfPagerAdapter;
    LinearLayout pdfLayout;




    private String _id,productimage,productname,completeddate,returnreason;
    private int productprice,productquantity;
    private Dialog dialog;
    private String termsandconditions;
    private Dialog alertDialog;


    @SuppressLint({"LogNotTimber", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_return_order);
        ButterKnife.bind(this);

        img_back.setOnClickListener(this);
        edt_comment.setVisibility(View.GONE);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            _id = extras.getString("_id");
            productimage = extras.getString("productimage");
            productname = extras.getString("productname");
            productprice = extras.getInt("productprice");
            productquantity = extras.getInt("productquantity");
            completeddate = extras.getString("completeddate");
            Log.w(TAG,"_id : "+_id);


            if(productname != null) {
                txt_product_title.setText(productname);
            }
            if(productprice != 0 && productquantity != 0) {
                txt_products_price.setText("\u20B9 " + productprice + " (" + productquantity + " items )");
            }
            if(completeddate != null){
                txt_delivered_date.setText("Delivered:"+" "+completeddate);

            }
            if (productimage != null && !productimage.isEmpty()) {
                Glide.with(getApplicationContext())
                        .load(productimage)
                        .into(img_products_image);

            }
            else{
                Glide.with(getApplicationContext())
                        .load(APIClient.PROFILE_IMAGE_URL)
                        .into(img_products_image);

            }




        }

        if (new ConnectionDetector(PetReturnOrderActivity.this).isNetworkAvailable(PetReturnOrderActivity.this)) {
            vendorReasonListResponseCall();

        }

        rg_return_reason.setOnCheckedChangeListener((group, checkedId) -> {
            if(rg_return_reason != null) {
                int radioButtonID = rg_return_reason.getCheckedRadioButtonId();
                RadioButton radioButton = rg_return_reason.findViewById(radioButtonID);
                if (radioButton != null) {
                    returnreason = (String) radioButton.getText();
                    Log.w(TAG, "selected returnreason : " + returnreason);

                    if(returnreason != null && returnreason.equalsIgnoreCase("Other")){
                        edt_comment.setVisibility(View.VISIBLE);
                    }else{
                        edt_comment.setVisibility(View.GONE);
                    }

                }
            }



        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReturnAlert();
            }
        });

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_back) {
            onBackPressed();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @SuppressLint({"LogNotTimber", "LongLogTag"})
    public void vendorReasonListResponseCall(){

        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<VendorReasonListResponse> call = apiInterface.vendorReasonListResponseCall(RestUtils.getContentType());
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<VendorReasonListResponse>() {
            @SuppressLint({"SetTextI18n", "LongLogTag"})
            @Override
            public void onResponse(@NonNull Call<VendorReasonListResponse> call, @NonNull Response<VendorReasonListResponse> response) {
                avi_indicator.smoothToHide();


                if (response.body() != null) {
                    if(200 == response.body().getCode()){
                        Log.w(TAG,"DropDownListResponse" + new Gson().toJson(response.body()));
                        if(response.body().getData().getReturn_status() != null &&response.body().getData().getReturn_status().size()>0 ) {
                            setReturnReasonList(response.body().getData().getReturn_status());
                        }
                        if(response.body().getData().getTerm_cond() != null){
                            termsandconditions = response.body().getData().getTerm_cond();
                        }




                    }

                }

            }

            @Override
            public void onFailure(@NonNull Call<VendorReasonListResponse> call, @NonNull  Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"VendorReasonListResponse flr"+t.getMessage());
            }
        });

    }

    private void setReturnReasonList(List<VendorReasonListResponse.DataBean.ReturnStatusBean> return_status) {
        for(int i=0; i<return_status.size();i++){
            RadioButton rb = new RadioButton(getApplicationContext());
            rb.setText(return_status.get(i).getTitle());
            rg_return_reason.addView(rb);

            ((RadioButton)rg_return_reason.getChildAt(0)).setChecked(true);


        }
    }

    @SuppressLint({"LongLogTag", "LogNotTimber"})
    private void update_status_returnResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<SuccessResponse> call = apiInterface.update_status_returnResponseCall(RestUtils.getContentType(), updateStatusReturnRequest());
        Log.w(TAG,"vendorOrderDetailsResponseCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<SuccessResponse>() {
            @SuppressLint({"LongLogTag", "LogNotTimber", "SetTextI18n"})
            @Override
            public void onResponse(@NonNull Call<SuccessResponse> call, @NonNull Response<SuccessResponse> response) {

                Log.w(TAG,"update_status_cancelResponseCall"+ "--->" + new Gson().toJson(response.body()));

                avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        startActivity(new Intent(PetReturnOrderActivity.this,PetMyOrdrersActivity.class));
                        finish();


                    }

                }


            }

            @SuppressLint({"LongLogTag", "LogNotTimber"})
            @Override
            public void onFailure(@NonNull Call<SuccessResponse> call, @NonNull Throwable t) {

                avi_indicator.smoothToHide();
                Log.w(TAG,"update_status_cancelResponseCall flr"+"--->" + t.getMessage());
            }
        });

    }
    @SuppressLint({"LongLogTag", "LogNotTimber"})
    private UpdateStatusReturnRequest updateStatusReturnRequest() {
        /*
         * _id : 6049e4f564a9296f3d7c3327
         * activity_id : 5
         * activity_title : Order Returned
         * activity_date : 11-03-2021 03:07 PM
         * order_status : Cancelled
         * user_return_info : the package was damage
         * user_return_date : 11-03-2021 03:07 PM
         * user_return_pic : http://pic.png
         */

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        String User_return_info;
        if(returnreason != null && returnreason.equalsIgnoreCase("Other")){
            User_return_info = edt_comment.getText().toString();
        }else{
            User_return_info = returnreason;
        }


        UpdateStatusReturnRequest updateStatusReturnRequest = new UpdateStatusReturnRequest();
        updateStatusReturnRequest.set_id(_id);
        updateStatusReturnRequest.setActivity_id(5);
        updateStatusReturnRequest.setActivity_title("Order Returned");
        updateStatusReturnRequest.setActivity_date(currentDateandTime);
        updateStatusReturnRequest.setOrder_status("Cancelled");
        updateStatusReturnRequest.setUser_return_info(User_return_info);
        updateStatusReturnRequest.setUser_return_date(currentDateandTime);
        updateStatusReturnRequest.setUser_return_pic("");

        Log.w(TAG,"updateStatusReturnRequest"+ "--->" + new Gson().toJson(updateStatusReturnRequest));

        return updateStatusReturnRequest;
    }
    private void showReturnAlert() {

        try {
            dialog = new Dialog(PetReturnOrderActivity.this);
            dialog.setContentView(R.layout.alert_return_layout);
            dialog.setCanceledOnTouchOutside(false);
            Button btn_return = dialog.findViewById(R.id.btn_return);
            WebView webView = dialog.findViewById(R.id.webView);

            //initialize the pdfLayout
             pdfLayout = dialog.findViewById(R.id.pdf_layout);
            CheckBox chxbox_acceptandterms = dialog.findViewById(R.id.chxbox_acceptandterms);





            try {
                Log.w(TAG,"termsandconditions : "+termsandconditions);
                if(termsandconditions != null) {
                    setPdfUrl(termsandconditions);


                }

            }
            catch (Exception e)
            {
                //Toast.makeText(DoctorPrescriptionDetailsActivity.this, "No PDF Viewer Installed", Toast.LENGTH_LONG).show();
            }
            btn_return.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(chxbox_acceptandterms.isChecked()){
                        dialog.dismiss();
                        if (new ConnectionDetector(PetReturnOrderActivity.this).isNetworkAvailable(PetReturnOrderActivity.this)) {
                            update_status_returnResponseCall();
                        }
                    }else{
                        showErrorLoading("Please check accept terms and conditions");
                    }

                }
            });

            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }




    }
    public void showErrorLoading(String errormesage){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
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


    @Override
    public void onSuccess(String url, String destinationPath) {
        // That's the positive case. PDF Download went fine
        pdfPagerAdapter = new PDFPagerAdapter(this, FileUtil.extractFileNameFromURL(url));
        remotePDFViewPager.setAdapter(pdfPagerAdapter);
        if(pdfLayout != null){
            updateLayout();
        }


    }

    @Override
    public void onFailure(Exception e) {

    }

    @Override
    public void onProgressUpdate(int progress, int total) {

    }


    private void setPdfUrl(String pdfurl) {

        //Create a RemotePDFViewPager object
        remotePDFViewPager = new RemotePDFViewPager(PetReturnOrderActivity.this, pdfurl, this);

    }
    private void updateLayout() {
        pdfLayout.addView(remotePDFViewPager,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pdfPagerAdapter != null) {
            pdfPagerAdapter.close();
        }
    }
}