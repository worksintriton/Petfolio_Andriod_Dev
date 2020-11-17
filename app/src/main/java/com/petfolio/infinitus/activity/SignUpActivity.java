package com.petfolio.infinitus.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
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
import com.petfolio.infinitus.requestpojo.SignupRequest;
import com.petfolio.infinitus.responsepojo.SignupResponse;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.img_back)
    ImageView img_back;

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.txt_usertypes)
    TextView txt_usertypes;

    @BindView(R.id.btn_changeusertype)
    Button btn_changeusertype;

    @BindView(R.id.edt_firstname)
    EditText edt_firstname;

    @BindView(R.id.edt_lastname)
    EditText edt_lastname;

    @BindView(R.id.edt_email)
    EditText edt_email;

    @BindView(R.id.edt_phone)
    EditText edt_phone;

    @BindView(R.id.btn_continue)
    Button btn_continue;

    private String TAG = "SignUpActivity";

    private String UserType;
    private int UserTypeValue;
    private AlertDialog.Builder alertDialogBuilder;
    private Dialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        avi_indicator.setVisibility(View.GONE);


        img_back.setOnClickListener(this);
        btn_changeusertype.setOnClickListener(this);
        btn_continue.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            UserType = extras.getString("UserType");
            txt_usertypes.setText(UserType);
        }else{
            UserType = "Pet lovers";
            txt_usertypes.setText(UserType);
        }



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_continue:
                startActivity(new Intent(SignUpActivity.this,VerifyOtpActivity.class));
                break;
            case R.id.img_back:
                 onBackPressed();
                break;

                case R.id.btn_changeusertype:
                    Intent intent = new Intent(SignUpActivity.this,ChooseUserTypeActivity.class);
                    intent.putExtra("UserType",UserType);
                    startActivity(intent);
                    break;
        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void signupResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<SignupResponse> call = apiInterface.signupResponseCall(RestUtils.getContentType(), signupRequest());
        Log.w(TAG,"SignupResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(@NonNull Call<SignupResponse> call, @NonNull Response<SignupResponse> response) {
                  avi_indicator.smoothToHide();
                Log.w(TAG,"SignupResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {

                    if (200 == response.body().getCode()) {
                        Toasty.success(getApplicationContext(),response.body().getMessage(), Toast.LENGTH_SHORT, true).show();



                    } else {
                        showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<SignupResponse> call,@NonNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.e("OTP", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private SignupRequest signupRequest() {
        /*
         * first_name : mohammed
         * last_name : imthiyas
         * user_email : m@gmail.com
         * user_phone : 987987989
         * user_type : 1
         * date_of_reg : 23/10/2019 12:12:00
         */
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setFirst_name(edt_firstname.getText().toString().trim());
        signupRequest.setLast_name(edt_lastname.getText().toString().trim());
        signupRequest.setUser_email(edt_email.getText().toString());
        signupRequest.setUser_phone(edt_phone.getText().toString());
        signupRequest.setUser_type(UserTypeValue);
        signupRequest.setDate_of_reg(currentDateandTime);
        Log.w(TAG,"signupRequest "+ new Gson().toJson(signupRequest));
        return signupRequest;
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

    private boolean validateInputs() {
       String mobileNumber = edt_phone.getText().toString();
        if (mobileNumber.isEmpty()) {
            edt_phone.setError(getResources().getString(R.string.mobile_number_error));
            edt_phone.requestFocus();
            return false;
        }else if (mobileNumber.length() < 10) {
            edt_phone.setError("Please enter valid mobile number");
            edt_phone.requestFocus();
            return false;
        }

        return true;
    }
}