package com.petfolio.infinitus.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.appUtils.ApplicationData;
import com.petfolio.infinitus.petlover.AddYourPetActivity;
import com.petfolio.infinitus.receiver.OTPSmsListener;
import com.petfolio.infinitus.receiver.SmsBroadcastListener;
import com.petfolio.infinitus.requestpojo.ResendOTPRequest;
import com.petfolio.infinitus.responsepojo.ResendOTPResponse;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyOtpActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.img_back)
    ImageView img_back;

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.btn_verify)
    Button btn_verify;

    @BindView(R.id.edt_otp)
    EditText edt_otp;

    @BindView(R.id.txt_resend)
    TextView txt_resend;

    @BindView(R.id.txt_timer_count)
    TextView txt_timer_count;

    @BindView(R.id.llresendotp)
    LinearLayout llresendotp;

    private String TAG = "VerifyOtpActivity";
    private CountDownTimer timer;

    private ApplicationData applicationData;
    private String phonenumber;
    private int otp;
    private AlertDialog.Builder alertDialogBuilder;
    Dialog alertDialog;
    private String autoOTP;

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private String userstatus;
    private int usertype;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        applicationData = (ApplicationData) getApplication();

        ButterKnife.bind(this);

        
        avi_indicator.setVisibility(View.GONE);
        llresendotp.setVisibility(View.GONE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            phonenumber = extras.getString("phonemumber");
            otp = extras.getInt("otp");
            usertype = extras.getInt("usertype");
            userstatus = extras.getString("userstatus");
        }



        img_back.setOnClickListener(this);
        btn_verify.setOnClickListener(this);
        txt_resend.setOnClickListener(this);

        SmsBroadcastListener.bindListener(new OTPSmsListener() {
            @Override
            public void onMessageReceived(String otpText) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"extractedOTP--->"+otpText);
                autoOTP = otpText;

                if(autoOTP != null) {
                    edt_otp.setText(autoOTP);
                }


            }
        });


        timer = new CountDownTimer(applicationData.getTimer_milliseconds(), 1000) {
            @SuppressLint({"DefaultLocale", "SetTextI18n"})
            @Override
            public void onTick(long millisUntilFinished) {
                applicationData.setTimer_milliseconds(millisUntilFinished);
                txt_timer_count.setText(getResources().getString(R.string.resendotp)+" " + String.format("%02d : %02d ",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));

            }

            @Override
            public void onFinish() {
                txt_timer_count.setVisibility(View.GONE);
                llresendotp.setVisibility(View.VISIBLE);
                timer.cancel();
            }
        };
        timer.start();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_verify:
                verifyValidator();
                break;
            case R.id.img_back:
                onBackPressed();
                break;

                case R.id.txt_resend:
                    if (new ConnectionDetector(VerifyOtpActivity.this).isNetworkAvailable(VerifyOtpActivity.this)) {
                        resendOtpResponseCall();
                    }
                break;


        }


    }

    public void verifyValidator() {
        boolean can_proceed = true;
        String enteredotp = edt_otp.getText().toString();
        String responseotp = String.valueOf(otp);
         if (edt_otp.getText().toString().trim().equals("")) {
             edt_otp.setError("Please enter your OTP");
             edt_otp.requestFocus();
            can_proceed = false;
        }else if(!responseotp.equalsIgnoreCase(enteredotp)){
             edt_otp.setError("Invalid OTP.");
             edt_otp.requestFocus();
            can_proceed = false;
        }

         if (can_proceed) {
             if(usertype == 1 && userstatus.equalsIgnoreCase("Incomplete")){
                 startActivity(new Intent(VerifyOtpActivity.this, AddYourPetActivity.class));

             }else if(usertype == 1 ){

             }else if(usertype == 2 && userstatus.equalsIgnoreCase("Incomplete")){
                 startActivity(new Intent(VerifyOtpActivity.this, AddYourPetActivity.class));

             }else if(usertype == 2 ){

             }


        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(VerifyOtpActivity.this,LoginActivity.class));
        finish();
    }

    private void resendOtpResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<ResendOTPResponse> call = apiInterface.resendOTPResponsecall(RestUtils.getContentType(), resendOTPRequest());
        Log.w(TAG,"ResendOTPResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<ResendOTPResponse>() {
            @Override
            public void onResponse(@NonNull Call<ResendOTPResponse> call, @NonNull Response<ResendOTPResponse> response) {
                  avi_indicator.smoothToHide();
                Log.w(TAG,"ResendOTPResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    if (200 == response.body().getCode()) {
                        Toasty.success(getApplicationContext(),response.body().getMessage(), Toast.LENGTH_SHORT, true).show();
                        otp = response.body().getData().getUser_Details().getOtp();

                    } else {
                        showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<ResendOTPResponse> call,@NonNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.e("ResendOTPResponse flr", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private ResendOTPRequest resendOTPRequest() {
        ResendOTPRequest resendOTPRequest = new ResendOTPRequest();
        resendOTPRequest.setUser_phone(phonenumber);
        Log.w(TAG,"OTP resendOTPRequest"+ new Gson().toJson(resendOTPRequest));
        return resendOTPRequest;
    }
    public void showErrorLoading(String errormesage){
        alertDialogBuilder = new AlertDialog.Builder(this);
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
   
}