package com.petfolio.infinitus.doctor;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.adapter.DoctorMyCalendarTimeAvailableAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.interfaces.OnItemClickSpecialization;
import com.petfolio.infinitus.requestpojo.DoctorMyCalendarAvlTimesRequest;
import com.petfolio.infinitus.requestpojo.DoctorMyCalendarUpdateDocDateRequest;
import com.petfolio.infinitus.responsepojo.DoctorMyCalendarAvlTimesResponse;
import com.petfolio.infinitus.responsepojo.DoctorMyCalendarUpdateDocDateResponse;
import com.petfolio.infinitus.sessionmanager.SessionManager;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorMyCalendarTimeActivity extends AppCompatActivity implements OnItemClickSpecialization {
    private static final String TAG = "DoctorMyCalendarTimeActivity" ;
    private ProgressDialog progressDialog;
    RecyclerView rv_doctor_mycalendar_avldays;
    private SharedPreferences preferences;

    private List<DoctorMyCalendarAvlTimesResponse.DataBean> dataBeanList = null;
    private List<DoctorMyCalendarUpdateDocDateRequest.TimingBean> timingBeanList= new ArrayList<>();
    private ArrayList<String> dateList = new ArrayList<>();
    private String days = "";
    private SessionManager session;
    String doctorname = "",doctoremailid = "";
    private String userid;

    String date;


    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_my_calendar_time);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getProfileDetails();
        doctorname = user.get(SessionManager.KEY_NAME);
        doctoremailid = user.get(SessionManager.KEY_EMAIL_ID);
       // userid = user.get(SessionManager.KEY_ID);
        userid = "1234567890";

        rv_doctor_mycalendar_avldays = findViewById(R.id.rv_doctor_mycalendar_avldays);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            dateList = (ArrayList<String>) getIntent().getSerializableExtra("dateList");
            Log.w(TAG,"dateList : "+new Gson().toJson(dateList));

        }


        if(dateList != null && dateList.size()>1){
            date = "";
        }else if(dateList != null){
            date = dateList.toString().replaceAll("\\[", "").replaceAll("\\]","");

        }

        ImageView backarrow = findViewById(R.id.backarrow);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Button btn_sumbit = findViewById(R.id.btn_submit);
        btn_sumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doctorMyCalendarUpdateDocDateResponseCall();
            }
        });

        if (new ConnectionDetector(DoctorMyCalendarTimeActivity.this).isNetworkAvailable(DoctorMyCalendarTimeActivity.this)) {
            doctorMyCalendarAvlTimesResponseCall();
        }
    }
    @SuppressLint("LongLogTag")
    private void doctorMyCalendarAvlTimesResponseCall() {
        progressDialog = new ProgressDialog(DoctorMyCalendarTimeActivity.this);
        progressDialog.setMessage("Uploading Data, please wait..");
        progressDialog.show();
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<DoctorMyCalendarAvlTimesResponse> call = ApiService.doctorMyCalendarAvlTimesResponseCall(RestUtils.getContentType(),doctorMyCalendarAvlTimesRequest());
        Log.w(TAG,"url  :%s"+" "+call.request().url().toString());

        call.enqueue(new Callback<DoctorMyCalendarAvlTimesResponse>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(@NonNull Call<DoctorMyCalendarAvlTimesResponse> call, @NonNull Response<DoctorMyCalendarAvlTimesResponse> response) {
                progressDialog.dismiss();
                Log.w(TAG,"DoctorMyCalendarAvlDaysResponse"+ "--->" + new Gson().toJson(response.body()));


                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        dataBeanList = response.body().getData();
                        DoctorMyCalendarUpdateDocDateRequest.TimingBean  timingBean = null;
                        for(int i = 0;i<dataBeanList.size();i++) {
                             timingBean = new DoctorMyCalendarUpdateDocDateRequest.TimingBean();
                            timingBean.setTime(dataBeanList.get(i).getTime());
                            timingBean.setStatus(dataBeanList.get(i).isStatus());
                            timingBeanList.add(timingBean);

                        }


                        if(dataBeanList.size()>0){
                            setViewAvlTimes();
                        }

                    }

                }


            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<DoctorMyCalendarAvlTimesResponse> call, @NonNull Throwable t) {
                progressDialog.dismiss();

                Log.w(TAG,"DoctorMyCalendarAvlDaysResponse"+"--->" + t.getMessage());
            }
        });

    }

    @SuppressLint("LongLogTag")
    private DoctorMyCalendarAvlTimesRequest doctorMyCalendarAvlTimesRequest() {
        /*
         * Doctor_email_id : mohammedimthi23956@gmail.com
         * Day :
         */
        DoctorMyCalendarAvlTimesRequest doctorMyCalendarAvlTimesRequest = new DoctorMyCalendarAvlTimesRequest();
        doctorMyCalendarAvlTimesRequest.setDay(date);
        doctorMyCalendarAvlTimesRequest.setUser_id(userid);
        Log.w(TAG,"doctorMyCalendarAvlTimesRequest"+ "--->" + new Gson().toJson(doctorMyCalendarAvlTimesRequest));
        return doctorMyCalendarAvlTimesRequest;
    }

    private void setViewAvlTimes() {
        //rv_doctor_mycalendar_avldays.setLayoutManager(new GridLayoutManager(this, 3));
        rv_doctor_mycalendar_avldays.setLayoutManager(new LinearLayoutManager(this));
        rv_doctor_mycalendar_avldays.setItemAnimator(new DefaultItemAnimator());
        DoctorMyCalendarTimeAvailableAdapter doctorMyCalendarAvailableAdapter = new DoctorMyCalendarTimeAvailableAdapter(getApplicationContext(), dataBeanList, rv_doctor_mycalendar_avldays, DoctorMyCalendarTimeActivity.this);
        rv_doctor_mycalendar_avldays.setAdapter(doctorMyCalendarAvailableAdapter);

    }

    @SuppressLint("LongLogTag")
    @Override
    public void onItemCheckSpecialization(String item) {

        Log.w(TAG,"onItemCheckSpecialization : "+item);



        for(int i=0; i<timingBeanList.size();i++){
            if(item.equalsIgnoreCase(timingBeanList.get(i).getTime())){
                timingBeanList.get(i).setStatus(true);
                break;
            }
        }
        Log.w(TAG,"List--->"+"dataBeanList Checked: "+new Gson().toJson(timingBeanList));


    }

    @SuppressLint("LongLogTag")
    @Override
    public void onItemUncheckSpecialization(String item) {
        Log.w(TAG,"onItemUncheckSpecialization : "+item);

        for(int i=0; i<timingBeanList.size();i++){
            if(item.equalsIgnoreCase(timingBeanList.get(i).getTime())){
                timingBeanList.get(i).setStatus(false);
                break;
            }
        }
        Log.w(TAG,"List--->"+"dataBeanList UnChecked: "+new Gson().toJson(timingBeanList));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @SuppressLint("LongLogTag")
    private void doctorMyCalendarUpdateDocDateResponseCall() {
        progressDialog = new ProgressDialog(DoctorMyCalendarTimeActivity.this);
        progressDialog.setMessage("Uploading Data, please wait..");
        progressDialog.show();
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<DoctorMyCalendarUpdateDocDateResponse> call = ApiService.doctorMyCalendarUpdateDocDateResponseCall(RestUtils.getContentType(),doctorMyCalendarUpdateDocDateRequest());
        Log.w(TAG,"url  :%s"+" "+call.request().url().toString());

        call.enqueue(new Callback<DoctorMyCalendarUpdateDocDateResponse>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(@NonNull Call<DoctorMyCalendarUpdateDocDateResponse> call, @NonNull Response<DoctorMyCalendarUpdateDocDateResponse> response) {
                progressDialog.dismiss();
                Log.w(TAG,"DoctorMyCalendarAvlDaysResponse"+ "--->" + new Gson().toJson(response.body()));


                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        Toasty.success(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT, true).show();
                        startActivity(new Intent(DoctorMyCalendarTimeActivity.this, DoctorDashboardActivity.class));

                    }else{
                        Toasty.error(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT, true).show();

                    }

                }


            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<DoctorMyCalendarUpdateDocDateResponse> call, @NonNull Throwable t) {
                progressDialog.dismiss();

                Log.w(TAG,"DoctorMyCalendarAvlDaysResponse"+"--->" + t.getMessage());
            }
        });

    }
    @SuppressLint("LongLogTag")
    private DoctorMyCalendarUpdateDocDateRequest doctorMyCalendarUpdateDocDateRequest() {
        /*
         * Doctor_email_id : 123123131313123123123123
         * days : ["Monday","Tuesday","Wednesday"]
         * timing : [{"Time":"01:00 AM","Status":true},{"Time":"02:00 AM","Status":true},{"Time":"03:00 AM","Status":true},{"Time":"04:00 AM","Status":true}]
         */

        DoctorMyCalendarUpdateDocDateRequest doctorMyCalendarUpdateDocDateRequest = new DoctorMyCalendarUpdateDocDateRequest();
        doctorMyCalendarUpdateDocDateRequest.setDoctor_email_id(doctoremailid);
        doctorMyCalendarUpdateDocDateRequest.setDays(dateList);
        doctorMyCalendarUpdateDocDateRequest.setTiming(timingBeanList);
        Log.w(TAG,"doctorMyCalendarUpdateDocDateRequest"+ "--->" + new Gson().toJson(doctorMyCalendarUpdateDocDateRequest));
        return doctorMyCalendarUpdateDocDateRequest;
    }
}