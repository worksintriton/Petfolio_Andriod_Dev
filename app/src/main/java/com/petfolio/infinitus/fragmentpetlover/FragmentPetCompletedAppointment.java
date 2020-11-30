package com.petfolio.infinitus.fragmentpetlover;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.adapter.DoctorCompletedAppointmentAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.requestpojo.DoctorNewAppointmentRequest;
import com.petfolio.infinitus.responsepojo.DoctorCompletedAppointmentResponse;
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


public class FragmentPetCompletedAppointment extends Fragment {
    private String TAG = "FragmentPetCompletedAppointment";

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.txt_no_records)
    TextView txt_no_records;

    @BindView(R.id.rv_completedappointment)
    RecyclerView rv_completedappointment;
    /*DoctorPastAppointmentAdapter doctorPastAppointmentAdapter;
    private SharedPreferences preferences;

    DoctorPastAppointmentResponse pastAppointmentResponse;
    private List<DoctorPastAppointmentResponse.DataBean> pastAppointmentResponseList = null;
*/



    SessionManager session;
    String type = "",name = "",doctorid = "";
    private SharedPreferences preferences;
    private Context mContext;
    private List<DoctorCompletedAppointmentResponse.DataBean> completedAppointmentResponseList;


    public FragmentPetCompletedAppointment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.w(TAG,"onCreateView");

        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        View view = inflater.inflate(R.layout.fragment_completed_appointment, container, false);

        ButterKnife.bind(this, view);
        mContext = getActivity();

        avi_indicator.setVisibility(View.GONE);

        session = new SessionManager(getContext());
        HashMap<String, String> user = session.getProfileDetails();

       doctorid = user.get(SessionManager.KEY_ID);

        String patientname = user.get(SessionManager.KEY_FIRST_NAME);

        Log.w(TAG,"Doctorid"+doctorid +"patientname :"+patientname);

      

        if (new ConnectionDetector(getActivity()).isNetworkAvailable(getActivity())) {


            doctorCompletedAppointmentResponseCall();
        }
        return view;
    }



    private void doctorCompletedAppointmentResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<DoctorCompletedAppointmentResponse> call = ApiService.doctorCompletedAppointmentResponseCall(RestUtils.getContentType(),doctorNewAppointmentRequest());
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<DoctorCompletedAppointmentResponse>() {
            @Override
            public void onResponse(@NonNull Call<DoctorCompletedAppointmentResponse> call, @NonNull Response<DoctorCompletedAppointmentResponse> response) {
               avi_indicator.smoothToHide();
                Log.w(TAG,"DoctorCompletedAppointmentResponse"+ "--->" + new Gson().toJson(response.body()));


               if (response.body() != null) {

                   if(200 == response.body().getCode()){
                       completedAppointmentResponseList = response.body().getData();
                       Log.w(TAG,"Size"+completedAppointmentResponseList.size());
                       Log.w(TAG,"completedAppointmentResponseList : "+new Gson().toJson(completedAppointmentResponseList));
                       if(response.body().getData().isEmpty()){
                           txt_no_records.setVisibility(View.VISIBLE);
                           txt_no_records.setText("No new appointments");
                           rv_completedappointment.setVisibility(View.GONE);
                       }else{
                           txt_no_records.setVisibility(View.GONE);
                           rv_completedappointment.setVisibility(View.VISIBLE);
                           setView();
                       }

                   }



                }
            }

            @Override
            public void onFailure(@NonNull Call<DoctorCompletedAppointmentResponse> call, @NonNull Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG,"DoctorCompletedAppointmentResponseflr"+"--->" + t.getMessage());
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
        rv_completedappointment.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_completedappointment.setItemAnimator(new DefaultItemAnimator());
        DoctorCompletedAppointmentAdapter doctorCompletedAppointmentAdapter = new DoctorCompletedAppointmentAdapter(getContext(), completedAppointmentResponseList, rv_completedappointment);
        rv_completedappointment.setAdapter(doctorCompletedAppointmentAdapter);

    }
}