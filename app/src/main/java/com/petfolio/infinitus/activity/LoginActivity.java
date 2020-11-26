package com.petfolio.infinitus.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.session.MediaSessionManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;

import com.petfolio.infinitus.requestpojo.LoginRequest;


import com.petfolio.infinitus.responsepojo.LoginResponse;

import com.petfolio.infinitus.sessionmanager.SessionManager;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.img_loginheader)
    ImageView img_loginheader;

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.edt_emailorphone)
    EditText edt_emailorphone;

    @BindView(R.id.txt_signup)
    TextView txt_signup;

    @BindView(R.id.btn_verifyotp)
    Button btn_verifyotp;

    private String TAG = "LoginActivity";
    private AlertDialog.Builder alertDialogBuilder;
    Dialog alertDialog;

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    String[] permissionString = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.RECEIVE_SMS,
            "check"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        avi_indicator.setVisibility(View.GONE);

        txt_signup.setOnClickListener(this);
        btn_verifyotp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_signup:
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
                break;
            case R.id.btn_verifyotp:
                verifyValidator();
                break;
        }

    }

    public void verifyValidator() {
        boolean can_proceed = true;
        if (edt_emailorphone.getText().toString().trim().equals("")) {
            edt_emailorphone.setError("Please enter your Email or Phone number");
            edt_emailorphone.requestFocus();
            can_proceed = false;
        }




        if (can_proceed) {
            insertmappermission();

        }

    }


    private void loginResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<LoginResponse> call = apiInterface.loginResponseCall(RestUtils.getContentType(), loginRequest());
        Log.w(TAG,"ResendOTPResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"ResendOTPResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    if (200 == response.body().getCode()) {

                        Toasty.success(getApplicationContext(),response.body().getMessage(), Toast.LENGTH_SHORT, true).show();
                       SessionManager sessionManager = new SessionManager(LoginActivity.this);
                        sessionManager.logoutUser();
                        sessionManager.setIsLogin(true);
                        sessionManager.createLoginSession(
                               response.body().getData().getUser_details().get_id(),
                               response.body().getData().getUser_details().getFirst_name(),
                               response.body().getData().getUser_details().getLast_name(),
                               response.body().getData().getUser_details().getUser_email(),
                               response.body().getData().getUser_details().getUser_phone(),
                               String.valueOf(response.body().getData().getUser_details().getUser_type()),
                               response.body().getData().getUser_details().getUser_status()
                       );

                        Intent intent = new Intent(LoginActivity.this,VerifyOtpActivity.class);
                        intent.putExtra("phonemumber",response.body().getData().getUser_details().getUser_phone());
                        intent.putExtra("otp",response.body().getData().getUser_details().getOtp());
                        intent.putExtra("userstatus",response.body().getData().getUser_details().getUser_status());
                        intent.putExtra("usertype",response.body().getData().getUser_details().getUser_type());
                        intent.putExtra("fromactivity",TAG);
                        startActivity(intent);

                    } else {
                        showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call,@NonNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.e("LoginResponse flr", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private LoginRequest loginRequest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUser_phone(edt_emailorphone.getText().toString());
        Log.w(TAG,"loginRequest"+ new Gson().toJson(loginRequest));
        return loginRequest;
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

    private void insertmappermission() {

        int haslocationpermission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            haslocationpermission = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) | checkSelfPermission(Manifest.permission.RECEIVE_SMS);

            if (haslocationpermission != PackageManager.PERMISSION_GRANTED) {

                if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) | !shouldShowRequestPermissionRationale(Manifest.permission.RECEIVE_SMS)) {

                    requestPermissions(permissionString,
                            REQUEST_CODE_ASK_PERMISSIONS);

                    return;
                }
                requestPermissions(permissionString,
                        REQUEST_CODE_ASK_PERMISSIONS);
            }else{
                if (new ConnectionDetector(LoginActivity.this).isNetworkAvailable(LoginActivity.this)) {

                    loginResponseCall();
                }

            }
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               /* startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();*/
                // Permission Granted
                 if (new ConnectionDetector(LoginActivity.this).isNetworkAvailable(LoginActivity.this)) {

                    loginResponseCall();
                }




            } else {
                // Permission Denied
              /*  Toast.makeText(Permission_Activity.this, "WRITE_CONTACTS Denied", Toast.LENGTH_SHORT)
                        .show();*/
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        new android.app.AlertDialog.Builder(LoginActivity.this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LoginActivity.this.finishAffinity();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}