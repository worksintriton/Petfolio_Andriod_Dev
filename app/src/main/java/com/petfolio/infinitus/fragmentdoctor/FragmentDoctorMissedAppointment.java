package com.petfolio.infinitus.fragmentdoctor;

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
import com.petfolio.infinitus.adapter.DoctorNewAppointmentAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.requestpojo.DoctorNewAppointmentRequest;
import com.petfolio.infinitus.responsepojo.DoctorMissedAppointmentResponse;
import com.petfolio.infinitus.responsepojo.DoctorNewAppointmentResponse;
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


public class FragmentDoctorMissedAppointment extends Fragment implements View.OnClickListener {
    private String TAG = "FragmentDoctorMissedAppointment";



    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.txt_no_records)
    TextView txt_no_records;

    @BindView(R.id.rv_missedappointment)
    RecyclerView rv_missedappointment;

    @BindView(R.id.btn_load_more)
    Button btn_load_more;

    @BindView(R.id.btn_filter)
    Button btn_filter;



    SessionManager session;
    String type = "",name = "",doctorid = "";
    private SharedPreferences preferences;
    private Context mContext;
    private List<DoctorMissedAppointmentResponse.DataBean> missedAppointmentResponseList;


    public FragmentDoctorMissedAppointment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.w(TAG,"onCreateView");

        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        View view = inflater.inflate(R.layout.fragment_doctor_missed, container, false);

        ButterKnife.bind(this, view);
        mContext = getActivity();

        avi_indicator.setVisibility(View.GONE);
        btn_load_more.setVisibility(View.GONE);
        btn_filter.setVisibility(View.GONE);

        btn_load_more.setOnClickListener(this);

        session = new SessionManager(getContext());
        HashMap<String, String> user = session.getProfileDetails();

        doctorid = user.get(SessionManager.KEY_ID);

        String patientname = user.get(SessionManager.KEY_FIRST_NAME);

        Log.w(TAG,"Doctorid"+doctorid +"patientname :"+patientname);

      

        if (new ConnectionDetector(getActivity()).isNetworkAvailable(Objects.requireNonNull(getActivity()))) {
            doctorMissedAppointmentResponseCall();
        }
        return view;
    }



    private void doctorMissedAppointmentResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<DoctorMissedAppointmentResponse> call = ApiService.doctorMissedAppointmentResponseCall(RestUtils.getContentType(),doctorNewAppointmentRequest());
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<DoctorMissedAppointmentResponse>() {
            @Override
            public void onResponse(@NonNull Call<DoctorMissedAppointmentResponse> call, @NonNull Response<DoctorMissedAppointmentResponse> response) {
               avi_indicator.smoothToHide();
                Log.w(TAG,"DoctorMissedAppointmentResponse"+ "--->" + new Gson().toJson(response.body()));


               if (response.body() != null) {
                   if(200 == response.body().getCode()){
                       missedAppointmentResponseList = response.body().getData();
                       Log.w(TAG,"Size"+missedAppointmentResponseList.size());
                       Log.w(TAG,"missedAppointmentResponseList : "+new Gson().toJson(missedAppointmentResponseList));
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
            public void onFailure(@NonNull Call<DoctorMissedAppointmentResponse> call, @NonNull Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG,"DoctorMissedAppointmentResponse flr"+"--->" + t.getMessage());
            }
        });

    }
    private DoctorNewAppointmentRequest doctorNewAppointmentRequest() {
        DoctorNewAppointmentRequest doctorNewAppointmentRequest = new DoctorNewAppointmentRequest();
        doctorNewAppointmentRequest.setDoctor_id(doctorid);
        Log.w(TAG,"doctorNewAppointmentRequest"+ "--->" + new Gson().toJson(doctorNewAppointmentRequest));
        return doctorNewAppointmentRequest;
    }
    private void setView() {
        rv_missedappointment.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_missedappointment.setItemAnimator(new DefaultItemAnimator());
        int size = 3;
        DoctorMissedAppointmentAdapter doctorMissedAppointmentAdapter = new DoctorMissedAppointmentAdapter(getContext(), missedAppointmentResponseList, rv_missedappointment,size);
        rv_missedappointment.setAdapter(doctorMissedAppointmentAdapter);

    }
    private void setViewLoadMore() {
        rv_missedappointment.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_missedappointment.setItemAnimator(new DefaultItemAnimator());
        int size = missedAppointmentResponseList.size();
        DoctorMissedAppointmentAdapter doctorMissedAppointmentAdapter = new DoctorMissedAppointmentAdapter(getContext(), missedAppointmentResponseList, rv_missedappointment,size);
        rv_missedappointment.setAdapter(doctorMissedAppointmentAdapter);

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