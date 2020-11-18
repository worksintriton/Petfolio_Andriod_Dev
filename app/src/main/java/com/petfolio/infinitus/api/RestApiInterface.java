package com.petfolio.infinitus.api;

import com.petfolio.infinitus.requestpojo.LoginRequest;
import com.petfolio.infinitus.requestpojo.ResendOTPRequest;
import com.petfolio.infinitus.requestpojo.SignupRequest;
import com.petfolio.infinitus.responsepojo.LoginResponse;
import com.petfolio.infinitus.responsepojo.ResendOTPResponse;
import com.petfolio.infinitus.responsepojo.SignupResponse;
import com.petfolio.infinitus.responsepojo.UserTypeListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RestApiInterface {

    /*user types list*/
    @GET("usertype/mobile/getlist")
    Call<UserTypeListResponse> userTypeListResponseCall(@Header("Content-Type") String type);

    /*Signup create*/
    @POST("userdetails/create")
    Call<SignupResponse> signupResponseCall(@Header("Content-Type") String type, @Body SignupRequest signupRequest);

    /*Resend otp*/
    @POST("userdetails/mobile/resendotp")
    Call<ResendOTPResponse> resendOTPResponsecall(@Header("Content-Type") String type, @Body ResendOTPRequest resendOTPRequest );


    /*Login*/
    @POST(" userdetails/mobile/login")
    Call<LoginResponse> loginResponseCall(@Header("Content-Type") String type, @Body LoginRequest loginRequest );


}
