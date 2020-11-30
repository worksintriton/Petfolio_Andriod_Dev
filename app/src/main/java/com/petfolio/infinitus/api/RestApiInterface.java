package com.petfolio.infinitus.api;

import com.petfolio.infinitus.requestpojo.AddYourPetRequest;
import com.petfolio.infinitus.requestpojo.BreedTypeRequest;
import com.petfolio.infinitus.requestpojo.CreateHolidayRequest;
import com.petfolio.infinitus.requestpojo.DocBusInfoUploadRequest;
import com.petfolio.infinitus.requestpojo.DoctorDetailsRequest;
import com.petfolio.infinitus.requestpojo.DoctorMyCalendarAvlDaysRequest;
import com.petfolio.infinitus.requestpojo.DoctorMyCalendarAvlTimesRequest;
import com.petfolio.infinitus.requestpojo.DoctorMyCalendarUpdateDocDateRequest;
import com.petfolio.infinitus.requestpojo.DoctorNewAppointmentRequest;
import com.petfolio.infinitus.requestpojo.HolidayDeleteRequest;
import com.petfolio.infinitus.requestpojo.HolidayListRequest;
import com.petfolio.infinitus.requestpojo.LocationAddRequest;
import com.petfolio.infinitus.requestpojo.LoginRequest;
import com.petfolio.infinitus.requestpojo.PetDetailsRequest;
import com.petfolio.infinitus.requestpojo.PetDoctorAvailableTimeRequest;
import com.petfolio.infinitus.requestpojo.PetLoverDashboardRequest;
import com.petfolio.infinitus.requestpojo.ResendOTPRequest;
import com.petfolio.infinitus.requestpojo.SignupRequest;
import com.petfolio.infinitus.requestpojo.UserStatusUpdateRequest;
import com.petfolio.infinitus.responsepojo.AddYourPetResponse;
import com.petfolio.infinitus.responsepojo.BreedTypeResponse;
import com.petfolio.infinitus.responsepojo.CreateHolidayResponse;
import com.petfolio.infinitus.responsepojo.DocBusInfoUploadResponse;
import com.petfolio.infinitus.responsepojo.DoctorCompletedAppointmentResponse;
import com.petfolio.infinitus.responsepojo.DoctorDetailsResponse;
import com.petfolio.infinitus.responsepojo.DoctorMissedAppointmentResponse;
import com.petfolio.infinitus.responsepojo.DoctorMyCalendarAvlDaysResponse;
import com.petfolio.infinitus.responsepojo.DoctorMyCalendarAvlTimesResponse;
import com.petfolio.infinitus.responsepojo.DoctorMyCalendarUpdateDocDateResponse;
import com.petfolio.infinitus.responsepojo.DoctorNewAppointmentResponse;
import com.petfolio.infinitus.responsepojo.DropDownListResponse;
import com.petfolio.infinitus.responsepojo.FileUploadResponse;
import com.petfolio.infinitus.responsepojo.HolidayDeleteResponse;
import com.petfolio.infinitus.responsepojo.HolidayListResponse;
import com.petfolio.infinitus.responsepojo.LocationAddResponse;
import com.petfolio.infinitus.responsepojo.LoginResponse;
import com.petfolio.infinitus.responsepojo.PetDetailsResponse;
import com.petfolio.infinitus.responsepojo.PetDoctorAvailableTimeResponse;
import com.petfolio.infinitus.responsepojo.PetLoverDashboardResponse;
import com.petfolio.infinitus.responsepojo.PetTypeListResponse;
import com.petfolio.infinitus.responsepojo.ResendOTPResponse;
import com.petfolio.infinitus.responsepojo.SignupResponse;
import com.petfolio.infinitus.responsepojo.UserStatusUpdateResponse;
import com.petfolio.infinitus.responsepojo.UserTypeListResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RestApiInterface {

    /*user types list*/
    @GET("usertype/mobile/getlist")
    Call<UserTypeListResponse> userTypeListResponseCall(@Header("Content-Type") String type);

    /*Signup create*/
    @POST("userdetails/create")
    Call<SignupResponse> signupResponseCall(@Header("Content-Type") String type, @Body SignupRequest signupRequest);

    /*User Staus update*/
    @POST("userdetails/mobile/edit")
    Call<UserStatusUpdateResponse> userStatusUpdateResponse(@Header("Content-Type") String type, @Body UserStatusUpdateRequest userStatusUpdateRequest );

    /*Resend otp*/
    @POST("userdetails/mobile/resendotp")
    Call<ResendOTPResponse> resendOTPResponsecall(@Header("Content-Type") String type, @Body ResendOTPRequest resendOTPRequest );


    /*Login*/
    @POST(" userdetails/mobile/login")
    Call<LoginResponse> loginResponseCall(@Header("Content-Type") String type, @Body LoginRequest loginRequest );

    /*dropdown list*/
    @GET("petdetails/mobile/dropdownslist")
    Call<DropDownListResponse> dropDownListResponseCall(@Header("Content-Type") String type);

    /*Add your pet*/
    @POST("petdetails/mobile/create")
    Call<AddYourPetResponse> addYourPetResponseCall(@Header("Content-Type") String type, @Body AddYourPetRequest addYourPetRequest );

    /*Pet lover dashboard*/
    @POST("userdetails/petlove/mobile/dashboard")
    Call<PetLoverDashboardResponse> petLoverDashboardResponseCall(@Header("Content-Type") String type, @Body PetLoverDashboardRequest petLoverDashboardRequest);

    /*Add location*/
    @POST("locationdetails/create")
    Call<LocationAddResponse> locationAddResponseCall(@Header("Content-Type") String type, @Body LocationAddRequest locationAddRequest);

    @Multipart
    @POST("upload")
    Call<FileUploadResponse> getImageStroeResponse(@Part MultipartBody.Part file);

    @POST("doctordetails/create")
    Call<DocBusInfoUploadResponse> docsBusInfoUpldResponse(@Header("Content-Type") String type, @Body DocBusInfoUploadRequest docBusInfoUploadRequest);


    /*Doctor Create holiday*/
    @POST("holiday/create")
    Call<CreateHolidayResponse>createHolidayResponseCall(@Header("Content-Type") String type, @Body CreateHolidayRequest createHolidayRequest );


    /*Doctor Listing added holidays*/
    @POST("holiday/getlist_id")
    Call<HolidayListResponse>holidayListResponseCall(@Header("Content-Type") String type, @Body HolidayListRequest holidayListRequest  );

    /*Doctor holiday delete*/
    @POST("holiday/delete")
    Call<HolidayDeleteResponse>holidayDeleteResponseCall(@Header("Content-Type") String type, @Body HolidayDeleteRequest holidayDeleteRequest  );

    /*doctor my calendar available days*/
    @POST("new_doctortime/fetch_dates")
    Call<DoctorMyCalendarAvlDaysResponse>doctorMyCalendarAvlDaysResponseCall(@Header("Content-Type") String type, @Body DoctorMyCalendarAvlDaysRequest doctorMyCalendarAvlDaysRequest);

    /*doctor my calendar available times*/
    @POST("new_doctortime/get_time_Details")
    Call<DoctorMyCalendarAvlTimesResponse>doctorMyCalendarAvlTimesResponseCall(@Header("Content-Type") String type, @Body DoctorMyCalendarAvlTimesRequest doctorMyCalendarAvlTimesRequest);


    /*doctor my calendar update*/
    @POST("new_doctortime/update_doc_date")
    Call<DoctorMyCalendarUpdateDocDateResponse>doctorMyCalendarUpdateDocDateResponseCall(@Header("Content-Type") String type, @Body DoctorMyCalendarUpdateDocDateRequest doctorMyCalendarUpdateDocDateRequest);

    /*Doctor New Appointment*/
    @POST("appointments/mobile/doc_getlist/newapp")
    Call<DoctorNewAppointmentResponse>doctorNewAppointmentResponseCall(@Header("Content-Type") String type, @Body DoctorNewAppointmentRequest doctorNewAppointmentRequest);

    /*Doctor Completed Appointment*/
    @POST("appointments/mobile/doc_getlist/comapp")
    Call<DoctorCompletedAppointmentResponse>doctorCompletedAppointmentResponseCall(@Header("Content-Type") String type, @Body DoctorNewAppointmentRequest doctorNewAppointmentRequest);

    /*Doctor Missed Appointment*/
    @POST("appointments/mobile/doc_getlist/missapp")
    Call<DoctorMissedAppointmentResponse>doctorMissedAppointmentResponseCall(@Header("Content-Type") String type, @Body DoctorNewAppointmentRequest doctorNewAppointmentRequest);


    /*Doctor Missed Appointment*/
    @POST("doctordetails/fetch_doctor_id")
    Call<DoctorDetailsResponse>doctorDetailsResponseCall(@Header("Content-Type") String type, @Body DoctorDetailsRequest doctorDetailsRequest );


    /*Listing all pets*/
    @GET("pettype/mobile/getlist")
    Call<PetTypeListResponse> petTypeListResponseCall(@Header("Content-Type") String type);

    /*Listing all breed by Pet ID*/
    @POST("breedtype/mobile/getlist_id")
    Call<BreedTypeResponse>breedTypeResponseByPetIdCall(@Header("Content-Type") String type, @Body BreedTypeRequest breedTypeRequest );

    /*Pet Details List by User ID*/
    @POST("petdetails/mobile/getlist_id")
    Call<PetDetailsResponse>petDetailsResponseByUserIdCall(@Header("Content-Type") String type, @Body PetDetailsRequest petDetailsRequest  );


    /*Patient Doctor available timeslot*/
    @POST("new_doctortime/get_doc_new")
    Call<PetDoctorAvailableTimeResponse>petDoctorAvailableTimeResponseCall(@Header("Content-Type") String type, @Body PetDoctorAvailableTimeRequest petDoctorAvailableTimeRequest   );

}
