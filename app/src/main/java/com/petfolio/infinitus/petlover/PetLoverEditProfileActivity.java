package com.petfolio.infinitus.petlover;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import com.petfolio.infinitus.requestpojo.ProfileUpdateRequest;
import com.petfolio.infinitus.responsepojo.ProfileUpdateResponse;
import com.petfolio.infinitus.sessionmanager.SessionManager;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetLoverEditProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private String TAG = "PetLoverEditProfileActivity";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_back)
    ImageView  img_back;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_firstname)
    EditText edt_firstname;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_lastname)
    EditText edt_lastname;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_email)
    EditText edt_email;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_phone)
    TextView txt_phone;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_save_changes)
    Button btn_save_changes;

    private String firstname,lastname,useremail,phonenumber,userid,usertype,userstatus;
    private Dialog alertDialog;
    private String profileimage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_lover_edit_profile);
        ButterKnife.bind(this);
        Log.w(TAG,"onCreate");

        avi_indicator.setVisibility(View.GONE);

        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getProfileDetails();
        firstname = user.get(SessionManager.KEY_FIRST_NAME);
        lastname = user.get(SessionManager.KEY_LAST_NAME);
        useremail = user.get(SessionManager.KEY_EMAIL_ID);
        phonenumber = user.get(SessionManager.KEY_MOBILE);
        userid = user.get(SessionManager.KEY_ID);
        usertype = user.get(SessionManager.KEY_TYPE);
        userstatus = user.get(SessionManager.KEY_PROFILE_STATUS);
        profileimage = user.get(SessionManager.KEY_PROFILE_IMAGE);

        if(firstname != null){
            edt_firstname.setText(firstname);
        } if(lastname != null){
            edt_lastname.setText(lastname);
        } if(useremail != null){
            edt_email.setText(useremail);
        } if(phonenumber != null){
            txt_phone.setText(phonenumber);
        }

        btn_save_changes.setOnClickListener(this);
        img_back.setOnClickListener(this);





    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void profileUpdateResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<ProfileUpdateResponse> call = apiInterface.profileUpdateResponseCall(RestUtils.getContentType(), profileUpdateRequest());
        Log.w(TAG,"SignupResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<ProfileUpdateResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProfileUpdateResponse> call, @NonNull Response<ProfileUpdateResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"SignupResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {

                    if (200 == response.body().getCode()) {

                        Toasty.success(getApplicationContext(),response.body().getMessage(), Toast.LENGTH_SHORT, true).show();

                        SessionManager sessionManager = new SessionManager(getApplicationContext());
                        sessionManager.setIsLogin(true);
                        sessionManager.createLoginSession(
                                userid,
                                edt_firstname.getText().toString(),
                                edt_lastname.getText().toString(),
                                edt_email.getText().toString(),
                                txt_phone.getText().toString(),
                                String.valueOf(usertype),
                                userstatus,
                                profileimage

                        );
                        Intent intent = new Intent(getApplicationContext(),PetLoverDashboardActivity.class);
                        startActivity(intent);


                    } else {
                        showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<ProfileUpdateResponse> call,@NonNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.e("OTP", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private ProfileUpdateRequest profileUpdateRequest() {
        /*
         * user_id : 5fd778437aa4cc1c6a1e5632
         * first_name : Sam
         * last_name : san
         * user_email : santhoshvsk94@gmail.com
         */


        ProfileUpdateRequest profileUpdateRequest = new ProfileUpdateRequest();
        profileUpdateRequest.setFirst_name(edt_firstname.getText().toString().trim());
        profileUpdateRequest.setLast_name(edt_lastname.getText().toString().trim());
        profileUpdateRequest.setUser_email(edt_email.getText().toString());
        profileUpdateRequest.setUser_id(userid);
        Log.w(TAG,"profileUpdateRequest "+ new Gson().toJson(profileUpdateRequest));
        return profileUpdateRequest;
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

    public void updateProfileValidator() {
        boolean can_proceed = true;
        int firstnamelength = edt_firstname.getText().toString().trim().length();
        int lastnamelength = edt_firstname.getText().toString().trim().length();
        String emailAddress = edt_email.getText().toString().trim();
        String emailPattern = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,4})$";


        if (Objects.requireNonNull(edt_firstname.getText()).toString().trim().equals("") && Objects.requireNonNull(edt_lastname.getText()).toString().trim().equals("")){
            Toasty.warning(getApplicationContext(), "Please enter the fields", Toast.LENGTH_SHORT, true).show();
            can_proceed = false;
        } else if (edt_firstname.getText().toString().trim().equals("")) {
            edt_firstname.setError("Please enter first name");
            edt_firstname.requestFocus();
            can_proceed = false;
        }else if (firstnamelength > 25) {
            edt_firstname.setError("The maximum length for an first name is 25 characters.");
            edt_firstname.requestFocus();
            can_proceed = false;
        }
        else if (edt_lastname.getText().toString().trim().equals("")) {
            edt_lastname.setError("Please enter last name");
            edt_lastname.requestFocus();
            can_proceed = false;
        }
        else if (lastnamelength > 25) {
            edt_lastname.setError("The maximum length for an last name is 25 characters.");
            edt_lastname.requestFocus();
            can_proceed = false;
        }
        else if(!emailAddress.matches(emailPattern)){
            edt_email.setError("Please enter correct E_mail address");
            edt_email.requestFocus();
            can_proceed = false;
        }




        if (can_proceed) {
            if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext())) {
                profileUpdateResponseCall();
            }

        }

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.btn_save_changes:
                updateProfileValidator();
                break;
        }
    }
}
