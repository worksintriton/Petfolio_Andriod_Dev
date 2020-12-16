package com.petfolio.infinitus.fragmentpetlover;

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
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.adapter.PetCompletedAppointmentAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.interfaces.AddReviewListener;
import com.petfolio.infinitus.requestpojo.AddReviewRequest;
import com.petfolio.infinitus.requestpojo.PetLoverAppointmentRequest;
import com.petfolio.infinitus.responsepojo.AddReviewResponse;
import com.petfolio.infinitus.responsepojo.PetNewAppointmentResponse;
import com.petfolio.infinitus.sessionmanager.SessionManager;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentPetCompletedAppointment extends Fragment implements View.OnClickListener, AddReviewListener {
    private String TAG = "FragmentPetCompletedAppointment";

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.txt_no_records)
    TextView txt_no_records;

    @BindView(R.id.rv_completedappointment)
    RecyclerView rv_completedappointment;

    @BindView(R.id.btn_load_more)
    Button btn_load_more;

    @BindView(R.id.btn_filter)
    Button btn_filter;




    SessionManager session;
    String type = "",name = "",doctorid = "";
    private SharedPreferences preferences;
    private Context mContext;
    private List<PetNewAppointmentResponse.DataBean> completedAppointmentResponseList;
    private String userid;
    private String userrate;
    private AlertDialog.Builder alertDialogBuilder;
    private Dialog alertDialog;


    public FragmentPetCompletedAppointment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.w(TAG,"onCreateView");

        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        View view = inflater.inflate(R.layout.fragment_pet_completed_appointment, container, false);

        ButterKnife.bind(this, view);
        mContext = getActivity();

        avi_indicator.setVisibility(View.GONE);
        btn_load_more.setVisibility(View.GONE);
        btn_filter.setVisibility(View.GONE);

        btn_load_more.setOnClickListener(this);


        session = new SessionManager(getContext());
        HashMap<String, String> user = session.getProfileDetails();
        userid = user.get(SessionManager.KEY_ID);
        Log.w(TAG," userid : "+userid);

        String patientname = user.get(SessionManager.KEY_FIRST_NAME);

        Log.w(TAG,"Doctorid"+doctorid +"patientname :"+patientname);

      

        if (new ConnectionDetector(getActivity()).isNetworkAvailable(getActivity())) {
            petCompletedAppointmentResponseCall();
        }
        return view;
    }



    private void petCompletedAppointmentResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<PetNewAppointmentResponse> call = ApiService.petCompletedAppointmentResponseCall(RestUtils.getContentType(),petLoverAppointmentRequest());
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<PetNewAppointmentResponse>() {
            @Override
            public void onResponse(@NonNull Call<PetNewAppointmentResponse> call, @NonNull Response<PetNewAppointmentResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"petCompletedAppointmentResponseCall"+ "--->" + new Gson().toJson(response.body()));


                if (response.body() != null) {

                    if(200 == response.body().getCode()){
                        completedAppointmentResponseList = response.body().getData();
                        Log.w(TAG,"Size"+completedAppointmentResponseList.size());
                        Log.w(TAG,"completedAppointmentResponseList : "+new Gson().toJson(completedAppointmentResponseList));
                        if(response.body().getData().isEmpty()){
                            txt_no_records.setVisibility(View.VISIBLE);
                            txt_no_records.setText("No completed appointments");
                            rv_completedappointment.setVisibility(View.GONE);
                            btn_load_more.setVisibility(View.GONE);
                            btn_filter.setVisibility(View.GONE);
                        }else{
                            txt_no_records.setVisibility(View.GONE);
                            rv_completedappointment.setVisibility(View.VISIBLE);
                            if(completedAppointmentResponseList.size() > 3){
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
        rv_completedappointment.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_completedappointment.setItemAnimator(new DefaultItemAnimator());
        int size = 3;
        PetCompletedAppointmentAdapter petCompletedAppointmentAdapter = new PetCompletedAppointmentAdapter(getContext(), completedAppointmentResponseList, rv_completedappointment,size,this);
        rv_completedappointment.setAdapter(petCompletedAppointmentAdapter);

    }
    private void setViewLoadMore() {
        rv_completedappointment.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_completedappointment.setItemAnimator(new DefaultItemAnimator());
        int size = completedAppointmentResponseList.size();
        PetCompletedAppointmentAdapter petCompletedAppointmentAdapter = new PetCompletedAppointmentAdapter(getContext(), completedAppointmentResponseList, rv_completedappointment,size,this);
        rv_completedappointment.setAdapter(petCompletedAppointmentAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_load_more:
                setViewLoadMore();
                break;
        }
    }

    @Override
    public void addReviewListener(String id, String userrate, String userfeedback) {
        Log.w(TAG,"addReviewListener : "+"id : "+id+" userrate : "+userrate+" userfeedback : "+userfeedback);
        showAddReview(id);
    }

    private void showAddReview(String id) {
        try {

            Dialog dialog = new Dialog(mContext);
            dialog.setContentView(R.layout.addreview_popup_layout);
            dialog.setCancelable(false);
            RatingBar ratingBar = dialog.findViewById(R.id.ratingBar);
            EditText edt_addreview = dialog.findViewById(R.id.edt_addreview);
            Button btn_addreview = dialog.findViewById(R.id.btn_addreview);

            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                     userrate = String.valueOf(rating);
                    Log.w(TAG,"onRatingChanged userrate : "+userrate);
                }
            });

            btn_addreview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(userrate != null){
                        dialog.dismiss();
                    if (new ConnectionDetector(getActivity()).isNetworkAvailable(getActivity())) {
                        addReviewResponseCall(id,edt_addreview.getText().toString(),userrate);
                    }
                    }else{
                        showErrorLoading("Please choose a star.");
                    }


                }
            });
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }




    }

    private void addReviewResponseCall(String id,String userfeedback,String userrate) {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<AddReviewResponse> call = apiInterface.addReviewResponseCall(RestUtils.getContentType(), addReviewRequest(id,userfeedback,userrate));
        Log.w(TAG,"addReviewResponseCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<AddReviewResponse>() {
            @Override
            public void onResponse(@NonNull Call<AddReviewResponse> call, @NonNull Response<AddReviewResponse> response) {

                Log.w(TAG,"AddReviewResponse"+ "--->" + new Gson().toJson(response.body()));

                avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        showAddReviewSuccess();



                    }
                    else{
                        showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<AddReviewResponse> call, @NonNull Throwable t) {

                avi_indicator.smoothToHide();
                Log.w(TAG,"AddReviewResponse flr"+"--->" + t.getMessage());
            }
        });

    }
    private AddReviewRequest addReviewRequest(String id, String userfeedback, String userrate) {

        AddReviewRequest addReviewRequest = new AddReviewRequest();
        addReviewRequest.set_id(id);
        if(userfeedback != null){
            addReviewRequest.setUser_feedback(userfeedback);

        }else{
            addReviewRequest.setUser_feedback("");

        }if(userrate != null){
            addReviewRequest.setUser_rate(userrate);

        }else{
            addReviewRequest.setUser_rate("");

        }
        Log.w(TAG,"addReviewRequest"+ "--->" + new Gson().toJson(addReviewRequest));
        return addReviewRequest;
    }


    private void showAddReviewSuccess() {
        try {

            Dialog dialog = new Dialog(mContext);
            dialog.setContentView(R.layout.addreview_review_success_layout);
            dialog.setCancelable(false);

            Button btn_back = dialog.findViewById(R.id.btn_back);


            btn_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   dialog.dismiss();
                    if (new ConnectionDetector(getActivity()).isNetworkAvailable(getActivity())) {
                        petCompletedAppointmentResponseCall();
                    }


                }
            });
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }




    }

    public void showErrorLoading(String errormesage){
        alertDialogBuilder = new AlertDialog.Builder(mContext);
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