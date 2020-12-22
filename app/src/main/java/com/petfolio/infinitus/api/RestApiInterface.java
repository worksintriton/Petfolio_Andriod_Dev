package com.petfolio.infinitus.api;

import com.petfolio.infinitus.requestpojo.AddReviewRequest;
import com.petfolio.infinitus.requestpojo.AddYourPetRequest;
import com.petfolio.infinitus.requestpojo.AppoinmentCancelledRequest;
import com.petfolio.infinitus.requestpojo.AppoinmentCompleteRequest;
import com.petfolio.infinitus.requestpojo.AppointmentCheckRequest;
import com.petfolio.infinitus.requestpojo.BreedTypeRequest;
import com.petfolio.infinitus.requestpojo.CreateHolidayRequest;
import com.petfolio.infinitus.requestpojo.DocBusInfoUploadRequest;
import com.petfolio.infinitus.requestpojo.DoctorCheckStatusRequest;
import com.petfolio.infinitus.requestpojo.DoctorDetailsRequest;
import com.petfolio.infinitus.requestpojo.DoctorMyCalendarAvlDaysRequest;
import com.petfolio.infinitus.requestpojo.DoctorMyCalendarAvlTimesRequest;
import com.petfolio.infinitus.requestpojo.DoctorMyCalendarUpdateDocDateRequest;
import com.petfolio.infinitus.requestpojo.DoctorNewAppointmentRequest;
import com.petfolio.infinitus.requestpojo.DoctorSearchRequest;
import com.petfolio.infinitus.requestpojo.FBTokenUpdateRequest;
import com.petfolio.infinitus.requestpojo.FilterDoctorRequest;
import com.petfolio.infinitus.requestpojo.HolidayDeleteRequest;
import com.petfolio.infinitus.requestpojo.HolidayListRequest;
import com.petfolio.infinitus.requestpojo.LocationAddRequest;
import com.petfolio.infinitus.requestpojo.LocationDeleteRequest;
import com.petfolio.infinitus.requestpojo.LocationListAddressRequest;
import com.petfolio.infinitus.requestpojo.LocationStatusChangeRequest;
import com.petfolio.infinitus.requestpojo.LocationUpdateRequest;
import com.petfolio.infinitus.requestpojo.LoginRequest;
import com.petfolio.infinitus.requestpojo.PetAddImageRequest;
import com.petfolio.infinitus.requestpojo.PetAppointmentCreateRequest;
import com.petfolio.infinitus.requestpojo.PetDeleteRequest;
import com.petfolio.infinitus.requestpojo.PetDetailsRequest;
import com.petfolio.infinitus.requestpojo.PetDoctorAvailableTimeRequest;
import com.petfolio.infinitus.requestpojo.PetEditRequest;
import com.petfolio.infinitus.requestpojo.PetListRequest;
import com.petfolio.infinitus.requestpojo.PetLoverAppointmentRequest;
import com.petfolio.infinitus.requestpojo.PetLoverDashboardRequest;
import com.petfolio.infinitus.requestpojo.PrescriptionCreateRequest;
import com.petfolio.infinitus.requestpojo.ResendOTPRequest;
import com.petfolio.infinitus.requestpojo.SPCheckStatusRequest;
import com.petfolio.infinitus.requestpojo.SPDetailsByUserIdRequest;
import com.petfolio.infinitus.requestpojo.SignupRequest;
import com.petfolio.infinitus.requestpojo.UserStatusUpdateRequest;
import com.petfolio.infinitus.requestpojo.ServiceProviderRegisterFormCreateRequest;
import com.petfolio.infinitus.responsepojo.AddReviewResponse;
import com.petfolio.infinitus.responsepojo.AddYourPetResponse;
import com.petfolio.infinitus.responsepojo.AppoinmentCancelledResponse;
import com.petfolio.infinitus.responsepojo.AppoinmentCompleteResponse;
import com.petfolio.infinitus.responsepojo.AppointmentCheckResponse;
import com.petfolio.infinitus.responsepojo.BreedTypeResponse;
import com.petfolio.infinitus.responsepojo.CreateHolidayResponse;
import com.petfolio.infinitus.responsepojo.DocBusInfoUploadResponse;
import com.petfolio.infinitus.responsepojo.DoctorCheckStatusResponse;
import com.petfolio.infinitus.responsepojo.DoctorCompletedAppointmentResponse;
import com.petfolio.infinitus.responsepojo.DoctorDetailsResponse;
import com.petfolio.infinitus.responsepojo.DoctorMissedAppointmentResponse;
import com.petfolio.infinitus.responsepojo.DoctorMyCalendarAvlDaysResponse;
import com.petfolio.infinitus.responsepojo.DoctorMyCalendarAvlTimesResponse;
import com.petfolio.infinitus.responsepojo.DoctorMyCalendarUpdateDocDateResponse;
import com.petfolio.infinitus.responsepojo.DoctorNewAppointmentResponse;
import com.petfolio.infinitus.responsepojo.DoctorSearchResponse;
import com.petfolio.infinitus.responsepojo.DropDownListResponse;
import com.petfolio.infinitus.responsepojo.FBTokenUpdateResponse;
import com.petfolio.infinitus.responsepojo.FileUploadResponse;
import com.petfolio.infinitus.responsepojo.FilterDoctorResponse;
import com.petfolio.infinitus.responsepojo.HolidayDeleteResponse;
import com.petfolio.infinitus.responsepojo.HolidayListResponse;
import com.petfolio.infinitus.responsepojo.LocationAddResponse;
import com.petfolio.infinitus.responsepojo.LocationDeleteResponse;
import com.petfolio.infinitus.responsepojo.LocationListAddressResponse;
import com.petfolio.infinitus.responsepojo.LocationStatusChangeResponse;
import com.petfolio.infinitus.responsepojo.LocationUpdateResponse;
import com.petfolio.infinitus.responsepojo.LoginResponse;
import com.petfolio.infinitus.responsepojo.PetAddImageResponse;
import com.petfolio.infinitus.responsepojo.PetAppointmentCreateResponse;
import com.petfolio.infinitus.responsepojo.PetDeleteResponse;
import com.petfolio.infinitus.responsepojo.PetDetailsResponse;
import com.petfolio.infinitus.responsepojo.PetDoctorAvailableTimeResponse;
import com.petfolio.infinitus.responsepojo.PetListResponse;
import com.petfolio.infinitus.responsepojo.PetLoverDashboardResponse;
import com.petfolio.infinitus.responsepojo.PetNewAppointmentResponse;
import com.petfolio.infinitus.responsepojo.PetTypeListResponse;
import com.petfolio.infinitus.responsepojo.PrescriptionCreateResponse;
import com.petfolio.infinitus.responsepojo.ResendOTPResponse;
import com.petfolio.infinitus.responsepojo.SPCheckStatusResponse;
import com.petfolio.infinitus.responsepojo.SPServiceListResponse;
import com.petfolio.infinitus.responsepojo.SignupResponse;
import com.petfolio.infinitus.responsepojo.UserStatusUpdateResponse;
import com.petfolio.infinitus.responsepojo.UserTypeListResponse;
import com.petfolio.infinitus.responsepojo.ServiceProviderRegisterFormCreateResponse;

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
    @POST("userdetails/mobile/login")
    Call<LoginResponse> loginResponseCall(@Header("Content-Type") String type, @Body LoginRequest loginRequest );

    /*Notification token update*/
    @POST("userdetails/mobile/update/fb_token")
    Call<FBTokenUpdateResponse>fBTokenUpdateResponseCall(@Header("Content-Type") String type, @Body FBTokenUpdateRequest fbTokenUpdateRequest);


    /*dropdown list*/
    @GET("petdetails/mobile/dropdownslist")
    Call<DropDownListResponse> dropDownListResponseCall(@Header("Content-Type") String type);

    /*service provider services list*/
    @GET("service_provider/sp_dropdown")
    Call<SPServiceListResponse> SPServiceListResponseCall(@Header("Content-Type") String type);

    /*Add your pet*/
    @POST("petdetails/mobile/create")
    Call<AddYourPetResponse> addYourPetResponseCall(@Header("Content-Type") String type, @Body AddYourPetRequest addYourPetRequest );

    /*Pet lover dashboard*/
    @POST("userdetails/petlove/mobile/dashboard")
    Call<PetLoverDashboardResponse> petLoverDashboardResponseCall(@Header("Content-Type") String type, @Body PetLoverDashboardRequest petLoverDashboardRequest);

    /*Add location*/
    @POST("locationdetails/create")
    Call<LocationAddResponse> locationAddResponseCall(@Header("Content-Type") String type, @Body LocationAddRequest locationAddRequest);

    /*Image upload*/
    @Multipart
    @POST("upload")
    Call<FileUploadResponse> getImageStroeResponse(@Part MultipartBody.Part file);

    /*Doctor Business info create*/
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


    /*Doctor fetch details based on id*/
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


    /*Pet Doctor available timeslot*/
    @POST("new_doctortime/get_doc_new")
    Call<PetDoctorAvailableTimeResponse>petDoctorAvailableTimeResponseCall(@Header("Content-Type") String type, @Body PetDoctorAvailableTimeRequest petDoctorAvailableTimeRequest   );


    /*Appointment check */
    @POST("appointments/check")
    Call<AppointmentCheckResponse>appointmentCheckResponseCall(@Header("Content-Type") String type, @Body AppointmentCheckRequest appointmentCheckRequest);

    /*Pet Appointment Create*/
    @POST("appointments/mobile/create")
    Call<PetAppointmentCreateResponse>petAppointmentCreateResponseCall(@Header("Content-Type") String type, @Body PetAppointmentCreateRequest petAppointmentCreateRequest);

    /*Pet Current Appointment*/
    @POST("appointments/mobile/plove_getlist/newapp")
    Call<PetNewAppointmentResponse>petNewAppointmentResponseCall(@Header("Content-Type") String type, @Body PetLoverAppointmentRequest petLoverAppointmentRequest);

    /*Pet Completed Appointment*/
    @POST("appointments/mobile/plove_getlist/comapp")
    Call<PetNewAppointmentResponse>petCompletedAppointmentResponseCall(@Header("Content-Type") String type, @Body PetLoverAppointmentRequest petLoverAppointmentRequest);

    /*Pet Cancelled Appointment*/
    @POST("appointments/mobile/plove_getlist/missapp")
    Call<PetNewAppointmentResponse>petMissedAppointmentResponseCall(@Header("Content-Type") String type, @Body PetLoverAppointmentRequest petLoverAppointmentRequest);

    /*Doctor Check status*/
    @POST("doctordetails/check_status")
    Call<DoctorCheckStatusResponse>doctorCheckStatusResponseCall(@Header("Content-Type") String type, @Body DoctorCheckStatusRequest doctorCheckStatusRequest );

    /*Prescriptoin Create*/
    @POST("prescription/create")
    Call<PrescriptionCreateResponse>prescriptionCreateRequestCall(@Header("Content-Type") String type, @Body PrescriptionCreateRequest prescriptionCreateRequest);

    /*Update Appointment Status complete*/
    @POST("appointments/edit")
    Call<AppoinmentCompleteResponse>appoinmentCompleteResponseCall(@Header("Content-Type") String type, @Body AppoinmentCompleteRequest appoinmentCompleteRequest );

    /*Update Appointment Status cancel*/
    @POST("appointments/edit")
    Call<AppoinmentCancelledResponse>appoinmentCancelledResponseCall(@Header("Content-Type") String type, @Body AppoinmentCancelledRequest appoinmentCancelledRequest );

    /*Listing Location by ID*/
    @POST("locationdetails/mobile/getlist_id")
    Call<LocationListAddressResponse>locationListAddressResponseCall(@Header("Content-Type") String type, @Body LocationListAddressRequest locationListAddressRequest  );

    /*Pet Details List by User ID*/
    @POST("petdetails/mobile/getlist_id")
    Call<PetListResponse>petListResponseCall(@Header("Content-Type") String type, @Body PetListRequest petListRequest  );


    /*update petimage Petdetails Using User id*/
    @POST("petdetails/edit")
    Call<PetAddImageResponse>PetAddImageResponseCall(@Header("Content-Type") String type, @Body PetAddImageRequest petAddImageRequest  );

    /*Edit Petdetails Using User id*/
    @POST("petdetails/edit")
    Call<PetAddImageResponse>petUpdateResponseCall(@Header("Content-Type") String type, @Body PetEditRequest petEditRequest   );

    /*Delete Petdetails Using User id*/
    @POST("petdetails/delete")
    Call<PetDeleteResponse>petDeleteResponseCall(@Header("Content-Type") String type, @Body PetDeleteRequest petDeleteRequest  );

    /*location list delete*/
    @POST("locationdetails/delete")
    Call<LocationDeleteResponse> locationDeleteResponseCall(@Header("Content-Type") String type, @Body LocationDeleteRequest locationDeleteRequest);

    /*Location status change*/
    @POST("locationdetails/default/edit")
    Call<LocationStatusChangeResponse>locationStatusChangeResponseCall(@Header("Content-Type") String type, @Body LocationStatusChangeRequest locationStatusChangeRequest);

    /*Location update*/
    @POST("locationdetails/edit")
    Call<LocationUpdateResponse>locationUpdateResponseCall(@Header("Content-Type") String type, @Body LocationUpdateRequest locationUpdateRequest);

    /*Doctor Name and specialization search*/
    @POST("doctordetails/text_search")
    Call<DoctorSearchResponse>doctorSearchResponseCall(@Header("Content-Type") String type, @Body DoctorSearchRequest doctorSearchRequest);

    /*Doctor specialization near by and review Filter api*/
    @POST("doctordetails/filter_doctor")
    Call<FilterDoctorResponse>filterDoctorResponseCall(@Header("Content-Type") String type, @Body FilterDoctorRequest filterDoctorRequest );


    /*Pet lover review add*/
    @POST("appointments/reviews/update")
    Call<AddReviewResponse>addReviewResponseCall(@Header("Content-Type") String type, @Body AddReviewRequest addReviewRequest  );




    /*Service provider register form create*/
    @POST("service_provider/create")
    Call<ServiceProviderRegisterFormCreateResponse>serviceProviderRegisterFormCreateResponseCall(@Header("Content-Type") String type, @Body ServiceProviderRegisterFormCreateRequest serviceProviderRegisterFormCreateRequest);

    /*Service provider details by id*/
    @POST("service_provider/getlist_id")
    Call<ServiceProviderRegisterFormCreateResponse>spDetailsReponseByUserIdCall(@Header("Content-Type") String type, @Body SPDetailsByUserIdRequest spDetailsByUserIdRequest);

    /*Service provider update*/
    @POST("service_provider/edit")
    Call<ServiceProviderRegisterFormCreateResponse>spUpdateReponseCall(@Header("Content-Type") String type, @Body ServiceProviderRegisterFormCreateRequest serviceProviderRegisterFormCreateRequest);


    /*SP Check status*/
    @POST("service_provider/check_status")
    Call<SPCheckStatusResponse>SPCheckStatusResponseCall(@Header("Content-Type") String type, @Body SPCheckStatusRequest spCheckStatusRequest );


    /*SP Create holiday*/
    @POST("sp_holiday/create")
    Call<CreateHolidayResponse>spCreateHolidayResponseCall(@Header("Content-Type") String type, @Body CreateHolidayRequest createHolidayRequest );

    /*SP Listing added holidays*/
    @POST("sp_holiday/getlist_id")
    Call<HolidayListResponse>spHolidayListResponseCall(@Header("Content-Type") String type, @Body HolidayListRequest holidayListRequest  );

    /*SP holiday delete*/
    @POST("sp_holiday/delete")
    Call<HolidayDeleteResponse>spHolidayDeleteResponseCall(@Header("Content-Type") String type, @Body HolidayDeleteRequest holidayDeleteRequest  );


}
