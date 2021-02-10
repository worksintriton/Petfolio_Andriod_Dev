package com.petfolio.infinitus.fragmentpetlover.myorders;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.interfaces.OnAppointmentCancel;
import com.petfolio.infinitus.petlover.PetMyappointmentsActivity;
import com.petfolio.infinitus.requestpojo.AppoinmentCancelledRequest;
import com.petfolio.infinitus.requestpojo.PetLoverAppointmentRequest;
import com.petfolio.infinitus.responsepojo.AppoinmentCancelledResponse;
import com.petfolio.infinitus.responsepojo.PetNewAppointmentResponse;
import com.petfolio.infinitus.sessionmanager.SessionManager;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentPetNewOrders extends Fragment implements OnAppointmentCancel, View.OnClickListener {
    private String TAG = "FragmentPetNewOrders";

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.txt_no_records)
    TextView txt_no_records;

    @BindView(R.id.rv_newappointment)
    RecyclerView rv_newappointment;

    @BindView(R.id.btn_load_more)
    Button btn_load_more;


    SessionManager session;
    String type = "",name = "",userid = "";
    private SharedPreferences preferences;
    private Context mContext;
    private List<PetNewAppointmentResponse.DataBean> newAppointmentResponseList;
    private Dialog dialog;


    public FragmentPetNewOrders() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.w(TAG,"onCreateView");

        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        View view = inflater.inflate(R.layout.fragment_pet_new_appointment, container, false);

        ButterKnife.bind(this, view);
        mContext = getActivity();

        avi_indicator.setVisibility(View.GONE);
        btn_load_more.setVisibility(View.GONE);
        btn_load_more.setOnClickListener(this);

        session = new SessionManager(getContext());
        HashMap<String, String> user = session.getProfileDetails();
        userid = user.get(SessionManager.KEY_ID);
        Log.w(TAG," userid : "+userid);

      

        if (new ConnectionDetector(getActivity()).isNetworkAvailable(getActivity())) {
            petNewAppointmentResponseCall();
        }
        return view;
    }



    private void petNewAppointmentResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<PetNewAppointmentResponse> call = ApiService.petNewAppointmentResponseCall(RestUtils.getContentType(),petLoverAppointmentRequest());
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<PetNewAppointmentResponse>() {
            @Override
            public void onResponse(@NonNull Call<PetNewAppointmentResponse> call, @NonNull Response<PetNewAppointmentResponse> response) {
               avi_indicator.smoothToHide();
                Log.w(TAG,"PetNewAppointmentResponse"+ "--->" + new Gson().toJson(response.body()));


               if (response.body() != null) {

                   if(200 == response.body().getCode()){
                       newAppointmentResponseList = response.body().getData();
                       Log.w(TAG,"Size"+newAppointmentResponseList.size());
                       Log.w(TAG,"newAppointmentResponseList : "+new Gson().toJson(newAppointmentResponseList));
                       if(response.body().getData().isEmpty()){
                           txt_no_records.setVisibility(View.VISIBLE);
                           txt_no_records.setText("No new appointments");
                           rv_newappointment.setVisibility(View.GONE);
                           btn_load_more.setVisibility(View.GONE);
                       }else{
                           txt_no_records.setVisibility(View.GONE);
                           rv_newappointment.setVisibility(View.VISIBLE);
                           if(newAppointmentResponseList.size()>3){
                               btn_load_more.setVisibility(View.VISIBLE);
                           }else{
                               btn_load_more.setVisibility(View.GONE);
                           }
                           setView();
                       }

                   }



                }
            }

            @Override
            public void onFailure(@NonNull Call<PetNewAppointmentResponse> call, @NonNull Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG,"PetNewAppointmentResponse"+"--->" + t.getMessage());
            }
        });

    }
    private PetLoverAppointmentRequest petLoverAppointmentRequest() {
        PetLoverAppointmentRequest petLoverAppointmentRequest = new PetLoverAppointmentRequest();
        petLoverAppointmentRequest.setUser_id(userid);
        Log.w(TAG,"petLoverAppointmentRequest"+ "--->" + new Gson().toJson(petLoverAppointmentRequest));
        return petLoverAppointmentRequest;
    }
    private void setView() {
        rv_newappointment.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_newappointment.setItemAnimator(new DefaultItemAnimator());
        int size = 3;
        //PetNewAppointmentAdapter petNewAppointmentAdapter = new PetNewAppointmentAdapter(getContext(), newAppointmentResponseList, rv_newappointment,size,this);
        //rv_newappointment.setAdapter(petNewAppointmentAdapter);

    }
    private void setViewLoadMore() {
        rv_newappointment.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_newappointment.setItemAnimator(new DefaultItemAnimator());
        int size = newAppointmentResponseList.size();
       // PetNewAppointmentAdapter petNewAppointmentAdapter = new PetNewAppointmentAdapter(getContext(), newAppointmentResponseList, rv_newappointment,size,this);
       // rv_newappointment.setAdapter(petNewAppointmentAdapter);

    }

    @Override
    public void onAppointmentCancel(String id,String appointmenttype,String userid, String doctorid,String appointmentid,String spid) {
        if(id != null){
            showStatusAlert(id);
        }
    }

    private void showStatusAlert(String id) {
        try {
            dialog = new Dialog(mContext);
            dialog.setContentView(R.layout.alert_approve_reject_layout);
            TextView tvheader = (TextView)dialog.findViewById(R.id.tvInternetNotConnected);
            tvheader.setText(R.string.cancelappointment);
            Button dialogButtonApprove = (Button) dialog.findViewById(R.id.btnApprove);
            dialogButtonApprove.setText("Yes");
            Button dialogButtonRejected = (Button) dialog.findViewById(R.id.btnReject);
            dialogButtonRejected.setText("No");

            dialogButtonApprove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    appoinmentCancelledResponseCall(id);


                }
            });
            dialogButtonRejected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Toasty.info(context, "Rejected Successfully", Toast.LENGTH_SHORT, true).show();
                    dialog.dismiss();




                }
            });
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }




    }
    private void appoinmentCancelledResponseCall(String id) {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<AppoinmentCancelledResponse> call = apiInterface.appoinmentCancelledResponseCall(RestUtils.getContentType(), appoinmentCancelledRequest(id));
        Log.w(TAG,"appoinmentCancelledResponseCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<AppoinmentCancelledResponse>() {
            @Override
            public void onResponse(@NonNull Call<AppoinmentCancelledResponse> call, @NonNull Response<AppoinmentCancelledResponse> response) {

                Log.w(TAG,"appoinmentCancelledResponseCall"+ "--->" + new Gson().toJson(response.body()));

                avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                       startActivity(new Intent(mContext, PetMyappointmentsActivity.class));





                    }
                    else{
                        //showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<AppoinmentCancelledResponse> call, @NonNull Throwable t) {

                avi_indicator.smoothToHide();
                Log.w(TAG,"appoinmentCancelledResponseCall flr"+"--->" + t.getMessage());
            }
        });

    }
    private AppoinmentCancelledRequest appoinmentCancelledRequest(String id) {

        /*
         * _id : 5fc639ea72fc42044bfa1683
         * missed_at : 23-10-2000 10 : 00 AM
         * doc_feedback : One Emergenecy work i am cancelling this appointment
         * appoinment_status : Missed
         */


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        AppoinmentCancelledRequest appoinmentCancelledRequest = new AppoinmentCancelledRequest();
        appoinmentCancelledRequest.set_id(id);
        appoinmentCancelledRequest.setMissed_at(currentDateandTime);
        appoinmentCancelledRequest.setDoc_feedback("");
        appoinmentCancelledRequest.setAppoinment_status("Missed");
        Log.w(TAG,"appoinmentCancelledRequest"+ "--->" + new Gson().toJson(appoinmentCancelledRequest));
        return appoinmentCancelledRequest;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_load_more:
                setViewLoadMore();
                break;
        }
    }
}