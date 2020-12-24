package com.petfolio.infinitus.fragmentserviceprovider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.adapter.DoctorMissedAppointmentAdapter;
import com.petfolio.infinitus.adapter.SPMissedAppointmentAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.requestpojo.DoctorNewAppointmentRequest;
import com.petfolio.infinitus.requestpojo.SPAppointmentRequest;
import com.petfolio.infinitus.responsepojo.DoctorMissedAppointmentResponse;
import com.petfolio.infinitus.responsepojo.SPAppointmentResponse;
import com.petfolio.infinitus.sessionmanager.SessionManager;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentSPMissedAppointment extends Fragment implements View.OnClickListener {
    private String TAG = "FragmentSPMissedAppointment";



    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_no_records)
    TextView txt_no_records;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_missedappointment)
    RecyclerView rv_missedappointment;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_load_more)
    Button btn_load_more;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_filter)
    Button btn_filter;



    SessionManager session;
    String type = "",username = "",userid = "";
    private SharedPreferences preferences;
    private Context mContext;
    private List<SPAppointmentResponse.DataBean> missedAppointmentResponseList;


    public FragmentSPMissedAppointment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.w(TAG,"onCreateView");

        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        View view = inflater.inflate(R.layout.fragment_sp_missed, container, false);

        ButterKnife.bind(this, view);
        mContext = getActivity();

        avi_indicator.setVisibility(View.GONE);
        btn_load_more.setVisibility(View.GONE);
        btn_filter.setVisibility(View.GONE);

        btn_load_more.setOnClickListener(this);

        session = new SessionManager(getContext());
        HashMap<String, String> user = session.getProfileDetails();
        userid = user.get(SessionManager.KEY_ID);
        username = user.get(SessionManager.KEY_FIRST_NAME);

        Log.w(TAG,"userid"+userid +"username :"+username);

      

        if (new ConnectionDetector(getActivity()).isNetworkAvailable(Objects.requireNonNull(getActivity()))) {
            spMissedAppointmentResponseCall();
        }
        return view;
    }



    private void spMissedAppointmentResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<SPAppointmentResponse> call = ApiService.spMissedAppointmentResponseCall(RestUtils.getContentType(),spAppointmentRequest());
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<SPAppointmentResponse>() {
            @Override
            public void onResponse(@NonNull Call<SPAppointmentResponse> call, @NonNull Response<SPAppointmentResponse> response) {
               avi_indicator.smoothToHide();
                Log.w(TAG,"SPAppointmentResponse "+ "--->" + new Gson().toJson(response.body()));


               if (response.body() != null) {
                   if(200 == response.body().getCode()){
                       missedAppointmentResponseList = response.body().getData();
                       Log.w(TAG,"Size"+missedAppointmentResponseList.size());
                       Log.w(TAG,"spMissedAppointmentResponseCall : "+new Gson().toJson(missedAppointmentResponseList));
                       if(response.body().getData().isEmpty()){
                           txt_no_records.setVisibility(View.VISIBLE);
                           txt_no_records.setText("No missed appointments");
                           rv_missedappointment.setVisibility(View.GONE);
                           btn_load_more.setVisibility(View.GONE);
                           btn_filter.setVisibility(View.GONE);
                       }else{
                           txt_no_records.setVisibility(View.GONE);
                           rv_missedappointment.setVisibility(View.VISIBLE);
                           if(missedAppointmentResponseList.size()>3){
                               btn_load_more.setVisibility(View.VISIBLE);
                           }else{
                               btn_load_more.setVisibility(View.GONE);
                           }
                           setView();
                       }
                   }else{

                   }


                }
            }

            @Override
            public void onFailure(@NonNull Call<SPAppointmentResponse> call, @NonNull Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG,"SPAppointmentResponse flr"+"--->" + t.getMessage());
            }
        });

    }
    private SPAppointmentRequest spAppointmentRequest() {
        SPAppointmentRequest spAppointmentRequest = new SPAppointmentRequest();
        spAppointmentRequest.setSp_id(userid);
        Log.w(TAG,"spMissedAppointmentRequest"+ "--->" + new Gson().toJson(spAppointmentRequest));
        return spAppointmentRequest;
    }
    private void setView() {
        rv_missedappointment.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_missedappointment.setItemAnimator(new DefaultItemAnimator());
        int size = 3;
        SPMissedAppointmentAdapter spMissedAppointmentAdapter = new SPMissedAppointmentAdapter(getContext(), missedAppointmentResponseList, rv_missedappointment,size);
        rv_missedappointment.setAdapter(spMissedAppointmentAdapter);

    }
    private void setViewLoadMore() {
        rv_missedappointment.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_missedappointment.setItemAnimator(new DefaultItemAnimator());
        int size = missedAppointmentResponseList.size();
        SPMissedAppointmentAdapter spMissedAppointmentAdapter = new SPMissedAppointmentAdapter(getContext(), missedAppointmentResponseList, rv_missedappointment,size);
        rv_missedappointment.setAdapter(spMissedAppointmentAdapter);

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