package com.petfolio.infinitus.api;

import com.petfolio.infinitus.requestpojo.AddReviewRequest;
import com.petfolio.infinitus.requestpojo.AddYourPetRequest;
import com.petfolio.infinitus.requestpojo.AppoinmentCancelledRequest;
import com.petfolio.infinitus.requestpojo.AppoinmentCloseRequest;
import com.petfolio.infinitus.requestpojo.AppoinmentCompleteRequest;
import com.petfolio.infinitus.requestpojo.AppointmentCheckRequest;
import com.petfolio.infinitus.requestpojo.AppointmentDetailsRequest;
import com.petfolio.infinitus.requestpojo.BreedTypeRequest;
import com.petfolio.infinitus.requestpojo.CreateHolidayRequest;
import com.petfolio.infinitus.requestpojo.DocBusInfoUploadRequest;
import com.petfolio.infinitus.requestpojo.DoctorBusinessInfoUpdateRequest;
import com.petfolio.infinitus.requestpojo.DoctorCheckStatusRequest;
import com.petfolio.infinitus.requestpojo.DoctorDetailsByUserIdRequest;
import com.petfolio.infinitus.requestpojo.DoctorDetailsRequest;
import com.petfolio.infinitus.requestpojo.DoctorMyCalendarAvlDaysRequest;
import com.petfolio.infinitus.requestpojo.DoctorMyCalendarAvlTimesRequest;
import com.petfolio.infinitus.requestpojo.DoctorMyCalendarUpdateDocDateRequest;
import com.petfolio.infinitus.requestpojo.DoctorNewAppointmentRequest;
import com.petfolio.infinitus.requestpojo.DoctorSearchRequest;
import com.petfolio.infinitus.requestpojo.DoctorStartAppointmentRequest;
import com.petfolio.infinitus.requestpojo.DoctorUpdateProfileImageRequest;
import com.petfolio.infinitus.requestpojo.EmailOTPRequest;
import com.petfolio.infinitus.requestpojo.FBTokenUpdateRequest;
import com.petfolio.infinitus.requestpojo.FetchByIdRequest;
import com.petfolio.infinitus.requestpojo.FetctProductByCatRequest;
import com.petfolio.infinitus.requestpojo.FilterDoctorRequest;
import com.petfolio.infinitus.requestpojo.HolidayDeleteRequest;
import com.petfolio.infinitus.requestpojo.HolidayListRequest;
import com.petfolio.infinitus.requestpojo.LocationAddRequest;
import com.petfolio.infinitus.requestpojo.LocationDeleteRequest;
import com.petfolio.infinitus.requestpojo.LocationListAddressRequest;
import com.petfolio.infinitus.requestpojo.LocationStatusChangeRequest;
import com.petfolio.infinitus.requestpojo.LocationUpdateRequest;
import com.petfolio.infinitus.requestpojo.LoginRequest;
import com.petfolio.infinitus.requestpojo.ManageProductsListRequest;
import com.petfolio.infinitus.requestpojo.NotificationGetlistRequest;
import com.petfolio.infinitus.requestpojo.NotificationSendRequest;
import com.petfolio.infinitus.requestpojo.PetAddImageRequest;
import com.petfolio.infinitus.requestpojo.PetAppointmentCreateRequest;
import com.petfolio.infinitus.requestpojo.PetDeleteRequest;
import com.petfolio.infinitus.requestpojo.PetDetailsRequest;
import com.petfolio.infinitus.requestpojo.PetDoctorAvailableTimeRequest;
import com.petfolio.infinitus.requestpojo.PetEditRequest;
import com.petfolio.infinitus.requestpojo.PetListRequest;
import com.petfolio.infinitus.requestpojo.PetLoverAppointmentRequest;
import com.petfolio.infinitus.requestpojo.PetLoverDashboardRequest;
import com.petfolio.infinitus.requestpojo.PetNewAppointmentDetailsRequest;
import com.petfolio.infinitus.requestpojo.PetNoShowRequest;
import com.petfolio.infinitus.requestpojo.PetUpdateOtherInformationRequest;
import com.petfolio.infinitus.requestpojo.PetVendorOrderRequest;
import com.petfolio.infinitus.requestpojo.PrescriptionCreateRequest;
import com.petfolio.infinitus.requestpojo.PrescriptionDetailsRequest;
import com.petfolio.infinitus.requestpojo.ProfileUpdateRequest;
import com.petfolio.infinitus.requestpojo.ResendOTPRequest;
import com.petfolio.infinitus.requestpojo.SPAppointmentRequest;
import com.petfolio.infinitus.requestpojo.SPCheckStatusRequest;
import com.petfolio.infinitus.requestpojo.SPCreateAppointmentRequest;
import com.petfolio.infinitus.requestpojo.SPDetailsByUserIdRequest;
import com.petfolio.infinitus.requestpojo.SPDetailsRequest;
import com.petfolio.infinitus.requestpojo.SPMyCalendarAvlDaysRequest;
import com.petfolio.infinitus.requestpojo.SPNotificationSendRequest;
import com.petfolio.infinitus.requestpojo.SPSpecificServiceDetailsRequest;
import com.petfolio.infinitus.requestpojo.ServiceCatRequest;
import com.petfolio.infinitus.requestpojo.ShopDashboardRequest;
import com.petfolio.infinitus.requestpojo.SignupRequest;
import com.petfolio.infinitus.requestpojo.TodayDealMoreRequest;
import com.petfolio.infinitus.requestpojo.UpdateStatusCancelRequest;
import com.petfolio.infinitus.requestpojo.UpdateStatusReturnRequest;
import com.petfolio.infinitus.requestpojo.UserStatusUpdateRequest;
import com.petfolio.infinitus.requestpojo.ServiceProviderRegisterFormCreateRequest;
import com.petfolio.infinitus.requestpojo.VendorAcceptReturnOrderRequest;
import com.petfolio.infinitus.requestpojo.VendorCancelsOrderRequest;
import com.petfolio.infinitus.requestpojo.VendorConfirmsOrderRequest;
import com.petfolio.infinitus.requestpojo.VendorDispatchesOrderRequest;
import com.petfolio.infinitus.requestpojo.VendorFetchOrderDetailsIdRequest;
import com.petfolio.infinitus.requestpojo.VendorGetsOrderIdRequest;
import com.petfolio.infinitus.requestpojo.VendorNewOrderRequest;
import com.petfolio.infinitus.requestpojo.VendorOrderDetailsRequest;
import com.petfolio.infinitus.requestpojo.VendorOrderRequest;
import com.petfolio.infinitus.requestpojo.VendorRegisterFormCreateRequest;
import com.petfolio.infinitus.responsepojo.AddReviewResponse;
import com.petfolio.infinitus.responsepojo.AddYourPetResponse;
import com.petfolio.infinitus.responsepojo.AppoinmentCancelledResponse;
import com.petfolio.infinitus.responsepojo.AppoinmentCompleteResponse;
import com.petfolio.infinitus.responsepojo.AppointmentCheckResponse;
import com.petfolio.infinitus.responsepojo.AppointmentsUpdateResponse;
import com.petfolio.infinitus.responsepojo.BreedTypeResponse;
import com.petfolio.infinitus.responsepojo.CartDetailsResponse;
import com.petfolio.infinitus.responsepojo.CartSuccessResponse;
import com.petfolio.infinitus.responsepojo.CreateHolidayResponse;
import com.petfolio.infinitus.responsepojo.DocBusInfoUploadResponse;
import com.petfolio.infinitus.responsepojo.DoctorAppointmentsResponse;
import com.petfolio.infinitus.responsepojo.DoctorCheckStatusResponse;
import com.petfolio.infinitus.responsepojo.DoctorDetailsByUserIdResponse;
import com.petfolio.infinitus.responsepojo.DoctorDetailsResponse;
import com.petfolio.infinitus.responsepojo.DoctorMyCalendarAvlDaysResponse;
import com.petfolio.infinitus.responsepojo.DoctorMyCalendarAvlTimesResponse;
import com.petfolio.infinitus.responsepojo.DoctorMyCalendarUpdateDocDateResponse;
import com.petfolio.infinitus.responsepojo.DoctorSearchResponse;
import com.petfolio.infinitus.responsepojo.DoctorUpdateProfileImageResponse;
import com.petfolio.infinitus.responsepojo.DropDownListResponse;
import com.petfolio.infinitus.responsepojo.EmailOTPResponse;
import com.petfolio.infinitus.responsepojo.FBTokenUpdateResponse;
import com.petfolio.infinitus.responsepojo.FetchProductByIdResponse;
import com.petfolio.infinitus.responsepojo.FetctProductByCatResponse;
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
import com.petfolio.infinitus.responsepojo.ManageProductsListResponse;
import com.petfolio.infinitus.responsepojo.NotificationGetlistResponse;
import com.petfolio.infinitus.responsepojo.NotificationSendResponse;
import com.petfolio.infinitus.responsepojo.PetAddImageResponse;
import com.petfolio.infinitus.responsepojo.PetAppointmentCreateResponse;
import com.petfolio.infinitus.responsepojo.PetAppointmentResponse;
import com.petfolio.infinitus.responsepojo.PetDeleteResponse;
import com.petfolio.infinitus.responsepojo.PetDetailsResponse;
import com.petfolio.infinitus.responsepojo.PetDoctorAvailableTimeResponse;
import com.petfolio.infinitus.responsepojo.PetListResponse;
import com.petfolio.infinitus.responsepojo.PetLoverDashboardResponse;
import com.petfolio.infinitus.responsepojo.PetNewAppointmentDetailsResponse;
import com.petfolio.infinitus.responsepojo.PetNewAppointmentResponse;
import com.petfolio.infinitus.responsepojo.PetTypeListResponse;
import com.petfolio.infinitus.responsepojo.PetVendorOrderResponse;
import com.petfolio.infinitus.responsepojo.PrescriptionCreateResponse;
import com.petfolio.infinitus.responsepojo.ProfileUpdateResponse;
import com.petfolio.infinitus.responsepojo.ResendOTPResponse;
import com.petfolio.infinitus.responsepojo.SPAppointmentDetailsResponse;
import com.petfolio.infinitus.responsepojo.SPAppointmentResponse;
import com.petfolio.infinitus.responsepojo.SPAvailableTimeResponse;
import com.petfolio.infinitus.responsepojo.SPCheckStatusResponse;
import com.petfolio.infinitus.responsepojo.SPCreateAppointmentResponse;
import com.petfolio.infinitus.responsepojo.SPDetailsRepsonse;
import com.petfolio.infinitus.responsepojo.SPFilterPriceListResponse;
import com.petfolio.infinitus.responsepojo.SPServiceListResponse;
import com.petfolio.infinitus.responsepojo.SPSpecificServiceDetailsResponse;
import com.petfolio.infinitus.responsepojo.ServiceCatResponse;
import com.petfolio.infinitus.responsepojo.ShopDashboardResponse;
import com.petfolio.infinitus.responsepojo.SignupResponse;
import com.petfolio.infinitus.responsepojo.SplashScreenResponse;
import com.petfolio.infinitus.responsepojo.SuccessResponse;
import com.petfolio.infinitus.responsepojo.TodayDealMoreResponse;
import com.petfolio.infinitus.responsepojo.UserStatusUpdateResponse;
import com.petfolio.infinitus.responsepojo.UserTypeListResponse;
import com.petfolio.infinitus.responsepojo.ServiceProviderRegisterFormCreateResponse;
import com.petfolio.infinitus.responsepojo.VendorAcceptsReturnOrderResponse;
import com.petfolio.infinitus.responsepojo.VendorCancelsOrderResponse;
import com.petfolio.infinitus.responsepojo.VendorConfirmsOrderResponse;
import com.petfolio.infinitus.responsepojo.VendorDispatchesOrderResponse;
import com.petfolio.infinitus.responsepojo.VendorFetchOrderDetailsResponse;
import com.petfolio.infinitus.responsepojo.VendorGetsOrderIDResponse;
import com.petfolio.infinitus.responsepojo.VendorNewOrderResponse;
import com.petfolio.infinitus.responsepojo.VendorOrderDetailsResponse;
import com.petfolio.infinitus.responsepojo.VendorOrderResponse;
import com.petfolio.infinitus.responsepojo.VendorReasonListResponse;
import com.petfolio.infinitus.responsepojo.VendorRegisterFormCreateResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RestApiInterface {

    /*splash screen list*/
    @GET("splashscreen/getlist")
    Call<SplashScreenResponse> splashScreenResponseCall(@Header("Content-Type") String type);


    /*user types list*/
    @GET("usertype/mobile/getlist")
    Call<UserTypeListResponse> userTypeListResponseCall(@Header("Content-Type") String type);

    /*Signup create*/
    @POST("userdetails/create")
    Call<SignupResponse> signupResponseCall(@Header("Content-Type") String type, @Body SignupRequest signupRequest);

    /*Email OTP */
    @POST("userdetails/send/emailotp")
    Call<EmailOTPResponse> emailOTPResponseCall(@Header("Content-Type") String type, @Body EmailOTPRequest emailOTPRequest);


    /*Profile update*/
    @POST("userdetails/mobile/update/profile")
    Call<ProfileUpdateResponse> profileUpdateResponseCall(@Header("Content-Type") String type, @Body ProfileUpdateRequest profileUpdateRequest);

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

    /*Pet Lover*/
    /*dropdown list*/
    @GET("petdetails/mobile/dropdownslist")
    Call<DropDownListResponse> dropDownListResponseCall(@Header("Content-Type") String type);

    /*Add your pet*/
    @POST("petdetails/mobile/create")
    Call<AddYourPetResponse> addYourPetResponseCall(@Header("Content-Type") String type, @Body AddYourPetRequest addYourPetRequest );

    /*Pet lover dashboard*/
    @POST("userdetails/petlove/mobile/dashboard1")
    Call<PetLoverDashboardResponse> petLoverDashboardResponseCall(@Header("Content-Type") String type, @Body PetLoverDashboardRequest petLoverDashboardRequest);

    /*Add location*/
    @POST("locationdetails/create")
    Call<LocationAddResponse> locationAddResponseCall(@Header("Content-Type") String type, @Body LocationAddRequest locationAddRequest);

    /*Image upload*/
    @Multipart
    @POST("upload")
    Call<FileUploadResponse> getImageStroeResponse(@Part MultipartBody.Part file);

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
    @POST("new_doctortime/get_doc_new1")
    Call<PetDoctorAvailableTimeResponse>petDoctorAvailableTimeResponseCall(@Header("Content-Type") String type, @Body PetDoctorAvailableTimeRequest petDoctorAvailableTimeRequest   );

    /*Appointment check */
    @POST("appointments/check")
    Call<AppointmentCheckResponse>appointmentCheckResponseCall(@Header("Content-Type") String type, @Body AppointmentCheckRequest appointmentCheckRequest);

    /*Pet Appointment Create*/
    @POST("appointments/mobile/create")
    Call<PetAppointmentCreateResponse>petAppointmentCreateResponseCall(@Header("Content-Type") String type, @Body PetAppointmentCreateRequest petAppointmentCreateRequest);

    /*Pet Current Appointment*/
    @POST("appointments/mobile/plove_getlist/newapp1")
    Call<PetAppointmentResponse>petAppointmentResponseCall(@Header("Content-Type") String type, @Body PetLoverAppointmentRequest petLoverAppointmentRequest);


    /*Pet Current Appointment*/
    @POST("appointments/mobile/plove_getlist/newapp")
    Call<PetNewAppointmentResponse>petNewAppointmentResponseCall(@Header("Content-Type") String type, @Body PetLoverAppointmentRequest petLoverAppointmentRequest);

    /*Pet Completed Appointment*/
    @POST("appointments/mobile/plove_getlist/comapp1")
    Call<PetAppointmentResponse>petCompletedAppointmentResponseCall(@Header("Content-Type") String type, @Body PetLoverAppointmentRequest petLoverAppointmentRequest);

    /*Pet Cancelled Appointment*/
    @POST("appointments/mobile/plove_getlist/missapp1")
    Call<PetAppointmentResponse>petMissedAppointmentResponseCall(@Header("Content-Type") String type, @Body PetLoverAppointmentRequest petLoverAppointmentRequest);


    /*Listing Location by ID*/
    @POST("locationdetails/mobile/getlist_id")
    Call<LocationListAddressResponse>locationListAddressResponseCall(@Header("Content-Type") String type, @Body LocationListAddressRequest locationListAddressRequest  );

    /*Pet Details List by User ID*/
    @POST("petdetails/mobile/getlist_id")
    Call<PetListResponse>petListResponseCall(@Header("Content-Type") String type, @Body PetListRequest petListRequest  );

    /*update other information Petdetails Using User id*/
    @POST("petdetails/edit")
    Call<PetAddImageResponse>petUpdateOtherInformationResponseCall(@Header("Content-Type") String type, @Body PetUpdateOtherInformationRequest petUpdateOtherInformationRequest  );


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

    /*Pet lover review add*/
    @POST("appointments/reviews/update")
    Call<AddReviewResponse>addReviewResponseCall(@Header("Content-Type") String type, @Body AddReviewRequest addReviewRequest  );

    /*SP lover review add*/
    @POST("sp_appointments/reviews/update")
    Call<AddReviewResponse>spaddReviewResponseCall(@Header("Content-Type") String type, @Body AddReviewRequest addReviewRequest  );


    /*Specific service*/
    @POST("service_provider/mobile/servicedetails")
    Call<SPSpecificServiceDetailsResponse>SPSpecificServiceDetailsResponseCall(@Header("Content-Type") String type, @Body SPSpecificServiceDetailsRequest spSpecificServiceDetailsRequest);

    /*Service category list*/
    @POST("service_provider/mobile/service_cat")
    Call<ServiceCatResponse>ServiceCatResponseCall(@Header("Content-Type") String type, @Body ServiceCatRequest serviceCatRequest );

    /*Service Details*/
    @POST("service_provider/mobile/sp_fetch_by_id")
    Call<SPDetailsRepsonse>SPDetailsRepsonseCall(@Header("Content-Type") String type, @Body SPDetailsRequest spDetailsRequest );


    /*SP available timeslot*/
    @POST("sp_available_time/get_sp_new")
    Call<SPAvailableTimeResponse>spAvailableTimeResponseCall(@Header("Content-Type") String type, @Body PetDoctorAvailableTimeRequest petDoctorAvailableTimeRequest   );


    /*Doctor*/

    /*Doctor Business info create*/
    @POST("doctordetails/create")
    Call<DocBusInfoUploadResponse> docsBusInfoUpldResponse(@Header("Content-Type") String type, @Body DocBusInfoUploadRequest docBusInfoUploadRequest);

    /*update doctor profile image Using User id*/
    @POST("userdetails/mobile/update/profile")
    Call<DoctorUpdateProfileImageResponse>DoctorUpdateProfileImageResponseCall(@Header("Content-Type") String type, @Body DoctorUpdateProfileImageRequest doctorUpdateProfileImageRequest  );


    /*Doctor Business info getby id*/
    @POST("doctordetails/fetch_doctor_user_id")
    Call<DoctorDetailsByUserIdResponse> doctorDetailsByUserIdResponseCall(@Header("Content-Type") String type, @Body DoctorDetailsByUserIdRequest doctorDetailsByUserIdRequest);

   /*Doctor Business info update*/
    @POST("doctordetails/edit")
    Call<DocBusInfoUploadResponse> doctorBusinessInfoUpdateResponseCall(@Header("Content-Type") String type, @Body DoctorBusinessInfoUpdateRequest doctorBusinessInfoUpdateRequest);


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
    Call<DoctorAppointmentsResponse>doctorNewAppointmentResponseCall(@Header("Content-Type") String type, @Body DoctorNewAppointmentRequest doctorNewAppointmentRequest);

    /*Doctor Completed Appointment*/
    @POST("appointments/mobile/doc_getlist/comapp")
    Call<DoctorAppointmentsResponse>doctorCompletedAppointmentResponseCall(@Header("Content-Type") String type, @Body DoctorNewAppointmentRequest doctorNewAppointmentRequest);

    /*Doctor Missed Appointment*/
    @POST("appointments/mobile/doc_getlist/missapp")
    Call<DoctorAppointmentsResponse>doctorMissedAppointmentResponseCall(@Header("Content-Type") String type, @Body DoctorNewAppointmentRequest doctorNewAppointmentRequest);


    /*Doctor fetch details based on id*/
    @POST("doctordetails/fetch_doctor_id")
    Call<DoctorDetailsResponse>doctorDetailsResponseCall(@Header("Content-Type") String type, @Body DoctorDetailsRequest doctorDetailsRequest );


    /*Doctor Check status*/
    @POST("doctordetails/check_status")
    Call<DoctorCheckStatusResponse>doctorCheckStatusResponseCall(@Header("Content-Type") String type, @Body DoctorCheckStatusRequest doctorCheckStatusRequest );

    /*Prescriptoin Create*/
    @POST("prescription/create")
    Call<PrescriptionCreateResponse>prescriptionCreateRequestCall(@Header("Content-Type") String type, @Body PrescriptionCreateRequest prescriptionCreateRequest);

    /*Prescriptoin Details*/
    @POST("prescription/fetch_by_appointment_id")
    Call<PrescriptionCreateResponse>prescriptionDetailsResponseCall(@Header("Content-Type") String type, @Body PrescriptionDetailsRequest prescriptionDetailsRequest);

    /*Update Appointment Status complete*/
    @POST("appointments/edit")
    Call<AppoinmentCompleteResponse>appoinmentCompleteResponseCall(@Header("Content-Type") String type, @Body AppoinmentCompleteRequest appoinmentCompleteRequest );

    /*Update Appointment Status cancel*/
    @POST("appointments/edit")
    Call<AppoinmentCancelledResponse>appoinmentCancelledResponseCall(@Header("Content-Type") String type, @Body AppoinmentCancelledRequest appoinmentCancelledRequest );

    /*doctor appointment close conversation*/
    @POST("appointments/edit")
    Call<AppointmentsUpdateResponse>appoinmentcloseconversationResponseCall(@Header("Content-Type") String type, @Body AppoinmentCloseRequest appoinmentCloseRequest );

    /*doctor appointment pet no show*/
    @POST("appointments/edit")
    Call<AppointmentsUpdateResponse>appoinmentpetnoResponseCall(@Header("Content-Type") String type, @Body PetNoShowRequest petNoShowRequest );

   /*doctor appointment start appointment*/
    @POST("appointments/edit")
    Call<AppointmentsUpdateResponse>doctorStartAppointmentResponseCall(@Header("Content-Type") String type, @Body DoctorStartAppointmentRequest doctorStartAppointmentRequest );


    /*Doctor Name and specialization search*/
    @POST("doctordetails/text_search")
    Call<DoctorSearchResponse>doctorSearchResponseCall(@Header("Content-Type") String type, @Body DoctorSearchRequest doctorSearchRequest);

    /*Doctor specialization near by and review Filter api*/
    @POST("doctordetails/filter_doctor")
    Call<FilterDoctorResponse>filterDoctorResponseCall(@Header("Content-Type") String type, @Body FilterDoctorRequest filterDoctorRequest );




    /*Service Provider*/

    /*service provider services list*/
    @GET("service_provider/sp_dropdown")
    Call<SPServiceListResponse> SPServiceListResponseCall(@Header("Content-Type") String type);

    /*service provider filter price list*/
    @GET("service_provider/filter_price_list")
    Call<SPFilterPriceListResponse> SPFilterPriceListResponseCall(@Header("Content-Type") String type);

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

    /*sp my calendar available days*/
    @POST("sp_available_time/fetch_dates")
    Call<DoctorMyCalendarAvlDaysResponse>spMyCalendarAvlDaysResponseCall(@Header("Content-Type") String type, @Body SPMyCalendarAvlDaysRequest spMyCalendarAvlDaysRequest);

    /*sp my calendar available times*/
    @POST("sp_available_time/get_time_Details")
    Call<DoctorMyCalendarAvlTimesResponse>spMyCalendarAvlTimesResponseCall(@Header("Content-Type") String type, @Body DoctorMyCalendarAvlTimesRequest doctorMyCalendarAvlTimesRequest);

    /*sp my calendar update*/
    @POST("sp_available_time/update_doc_date")
    Call<DoctorMyCalendarUpdateDocDateResponse>spMyCalendarUpdateDocDateResponseCall(@Header("Content-Type") String type, @Body DoctorMyCalendarUpdateDocDateRequest doctorMyCalendarUpdateDocDateRequest);


    /*SP Create holiday*/
    @POST("sp_holiday/create")
    Call<CreateHolidayResponse>spCreateHolidayResponseCall(@Header("Content-Type") String type, @Body CreateHolidayRequest createHolidayRequest );

    /*SP Listing added holidays*/
    @POST("sp_holiday/getlist_id")
    Call<HolidayListResponse>spHolidayListResponseCall(@Header("Content-Type") String type, @Body HolidayListRequest holidayListRequest  );

    /*SP holiday delete*/
    @POST("sp_holiday/delete")
    Call<HolidayDeleteResponse>spHolidayDeleteResponseCall(@Header("Content-Type") String type, @Body HolidayDeleteRequest holidayDeleteRequest  );

    /*SP Create Appointment*/
    @POST("sp_appointments/mobile/create")
    Call<SPCreateAppointmentResponse>SPCreateAppointmentResponseCall(@Header("Content-Type") String type, @Body SPCreateAppointmentRequest spCreateAppointmentRequest);

    /*SP New Appointment*/
    @POST("sp_appointments/mobile/sp_getlist/newapp")
    Call<SPAppointmentResponse>spNewAppointmentResponseCall(@Header("Content-Type") String type, @Body SPAppointmentRequest spAppointmentRequest);

    /*SP Completed Appointment*/
    @POST("sp_appointments/mobile/sp_getlist/comapp")
    Call<SPAppointmentResponse>spCompletedAppointmentResponseCall(@Header("Content-Type") String type, @Body SPAppointmentRequest spAppointmentRequest);

    /*SP Missed Appointment*/
    @POST("sp_appointments/mobile/sp_getlist/missapp")
    Call<SPAppointmentResponse>spMissedAppointmentResponseCall(@Header("Content-Type") String type, @Body SPAppointmentRequest spAppointmentRequest);

    /*Update Appointment Status complete*/
    @POST("sp_appointments/edit")
    Call<AppoinmentCompleteResponse>spappoinmentCompleteResponseCall(@Header("Content-Type") String type, @Body AppoinmentCompleteRequest appoinmentCompleteRequest );

    /*Update Appointment Status cancel*/
    @POST("sp_appointments/edit")
    Call<AppoinmentCancelledResponse>spappoinmentCancelledResponseCall(@Header("Content-Type") String type, @Body AppoinmentCancelledRequest appoinmentCancelledRequest );



    /*Vendor*/

    /*Service provider register form create*/
    @POST("product_vendor/create")
    Call<VendorRegisterFormCreateResponse>vendorRegisterFormCreateResponseCall(@Header("Content-Type") String type, @Body VendorRegisterFormCreateRequest vendorRegisterFormCreateRequest );


    /*Vendor Check status*/
    @POST("product_vendor/check_status")
    Call<SPCheckStatusResponse>VendorCheckStatusResponseCall(@Header("Content-Type") String type, @Body SPCheckStatusRequest spCheckStatusRequest );

    /*Pet New Appointment Details*/
    @POST("appointments/mobile/fetch_appointment_id")
    Call<PetNewAppointmentDetailsResponse> petNewAppointDetailResponseCall(@Header("Content-Type") String type, @Body PetNewAppointmentDetailsRequest petNewAppointmentDetailsRequest);

   /* SP Appointment Details*/
    @POST("sp_appointments/mobile/fetch_appointment_id")
    Call<SPAppointmentDetailsResponse> spAppointmentDetailsResponse(@Header("Content-Type") String type, @Body AppointmentDetailsRequest appointmentDetailsRequest);


    /*notifications list*/
    @POST("notification/mobile/getlist_id")
    Call<NotificationGetlistResponse> notificationGetlistResponseCall(@Header("Content-Type") String type, @Body NotificationGetlistRequest notificationGetlistRequest);

    /*notifications send request*/
    @POST("notification/mobile/alert/notification")
    Call<NotificationSendResponse> notificationSendResponseCall(@Header("Content-Type") String type, @Body NotificationSendRequest notificationSendRequest);

    /*notifications spsend request*/
    @POST("notification/mobile/alert/sp_notification")
    Call<NotificationSendResponse> spnotificationSendResponseCall(@Header("Content-Type") String type, @Body SPNotificationSendRequest spNotificationSendRequest);

    /*Vendor Order*/
    @POST("order_details/getorder_list")
    Call<VendorOrderResponse>vendorOrderResponseCall(@Header("Content-Type") String type, @Body VendorOrderRequest vendorOrderRequest);

    /*shop dashboard list*/
    @POST("product_details/getproductdetails_list")
    Call<ShopDashboardResponse> shopDashboardResponseCall(@Header("Content-Type") String type,@Body ShopDashboardRequest ShopDashboardRequest);

    /*View Todays Deal Products see more*/
    @POST("product_details/today_deal")
    Call<TodayDealMoreResponse> todayDealMoreResponseCall(@Header("Content-Type") String type, @Body TodayDealMoreRequest todayDealMoreRequest);


    /*View the Product's by cat id see more*/
    @POST("product_details/fetch_product_by_cat")
    Call<FetctProductByCatResponse> fetctProductByCatResponseCall(@Header("Content-Type") String type, @Body FetctProductByCatRequest fetctProductByCatRequest);

    /*View Single Product by Id*/
    @POST("product_details/fetch_product_by_id")
    Call<FetchProductByIdResponse> fetch_product_by_id_ResponseCall(@Header("Content-Type") String type, @Body FetchByIdRequest fetchByIdRequest);

    /*Add product*/
    @POST("product_cart_detail/add_product")
    Call<SuccessResponse> add_product_ResponseCall(@Header("Content-Type") String type, @Body FetchByIdRequest fetchByIdRequest);

    /*Remove product*/
    @POST("product_cart_detail/remove_product")
    Call<SuccessResponse> remove_product_ResponseCall(@Header("Content-Type") String type, @Body FetchByIdRequest fetchByIdRequest);

    /*Remove all product*/
    @POST("product_cart_detail/remove_overall_products")
    Call<SuccessResponse> remove_overall_products_ResponseCall(@Header("Content-Type") String type, @Body FetchByIdRequest fetchByIdRequest);


    /*Cart page fetch details*/
    @POST("product_cart_detail/fetch_cart_details_by_userid")
    Call<CartDetailsResponse> fetch_cart_details_by_userid_ResponseCall(@Header("Content-Type") String type, @Body FetchByIdRequest fetchByIdRequest);

    /*Vendor booking create*/
    @POST("vendor_order_booking/create")
    Call<CartSuccessResponse> vendor_order_booking_create_ResponseCall(@Header("Content-Type") String type, @Body CartDetailsResponse cartDetailsResponse);


    /*Vendor booked view orders*/
    @POST("vendor_order_booking/get_order_details_user_id")
    Call<PetVendorOrderResponse> get_order_details_user_id_ResponseCall(@Header("Content-Type") String type, @Body PetVendorOrderRequest petVendorOrderRequest);


    /*Vendor views orders*/
    @POST("vendor_order_booking/get_order_details_vendor_id")
    Call<VendorNewOrderResponse> get_order_details_vendordid_ResponseCall(@Header("Content-Type") String type, @Body VendorNewOrderRequest vendorNewOrderRequest);

    /*Vendor accepts order*/
    @POST("vendor_order_booking/update_status_accept")
    Call<VendorConfirmsOrderResponse> vendor_order_booking_accept_ResponseCall(@Header("Content-Type") String type, @Body VendorConfirmsOrderRequest vendorConfirmsOrderRequest);


    /*Vendor dipatches order*/
    @POST("vendor_order_booking/update_status_dispatch")
    Call<VendorDispatchesOrderResponse> vendor_order_booking_dispatch_ResponseCall(@Header("Content-Type") String type, @Body VendorDispatchesOrderRequest vendorDispatchesOrderRequest);


    /*Vendor cancels order*/
    @POST("vendor_order_booking/update_status_vendor_cancel")
    Call<VendorCancelsOrderResponse> vendor_order_booking_cancels_ResponseCall(@Header("Content-Type") String type, @Body VendorCancelsOrderRequest vendorCancelsOrderRequest);


    /*Vendor fetches order details*/
    @POST("vendor_order_booking/fetch_order_details_id")
    Call<VendorFetchOrderDetailsResponse> vendor_order_booking_order_fetches_ResponseCall(@Header("Content-Type") String type, @Body VendorFetchOrderDetailsIdRequest vendorFetchOrderDetailsIdRequest);


    /*Vendor gets order ID*/
    @POST("product_vendor/getlist_id")
    Call<VendorGetsOrderIDResponse> vendor_gets_orderbyId_ResponseCall(@Header("Content-Type") String type, @Body VendorGetsOrderIdRequest vendorGetsOrderIdRequest);

    /*Vendor gets order ID*/
    @GET("vendor_order_booking/cancel_status")
    Call<VendorReasonListResponse> vendorReasonListResponseCall(@Header("Content-Type") String type);

    /*Order Details*/
    @POST("vendor_order_booking/fetch_order_details_id")
    Call<VendorOrderDetailsResponse> vendorOrderDetailsResponseCall(@Header("Content-Type") String type, @Body VendorOrderDetailsRequest vendorOrderDetailsRequest);

    /*Cancel the order*/
    @POST("vendor_order_booking/update_status_cancel")
    Call<SuccessResponse> update_status_cancelResponseCall(@Header("Content-Type") String type, @Body UpdateStatusCancelRequest updateStatusCancelRequest);

    /*Return the order*/
    @POST("vendor_order_booking/update_status_return")
    Call<SuccessResponse> update_status_returnResponseCall(@Header("Content-Type") String type, @Body UpdateStatusReturnRequest updateStatusReturnRequest);

    /*Manage products list*/
    @POST("product_details/mobile/getlist_from_vendor_id")
    Call<ManageProductsListResponse> getlist_from_vendor_id_ResponseCall(@Header("Content-Type") String type, @Body ManageProductsListRequest manageProductsListRequest);

    /* Vendor Accepts Return order*/
    @POST("vendor_order_booking/update_status_return")
    Call<VendorAcceptsReturnOrderResponse> update_status_vendor_accept_returnResponseCall(@Header("Content-Type") String type, @Body VendorAcceptReturnOrderRequest vendorAcceptReturnOrderRequest);


}
