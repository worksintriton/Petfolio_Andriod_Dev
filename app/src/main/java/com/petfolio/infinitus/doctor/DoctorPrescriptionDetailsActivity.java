package com.petfolio.infinitus.doctor;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.adapter.DoctorPrescriptionsDetailsAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.requestpojo.PrescriptionCreateRequest;
import com.petfolio.infinitus.requestpojo.PrescriptionDetailsRequest;
import com.petfolio.infinitus.responsepojo.PrescriptionCreateResponse;
import com.petfolio.infinitus.sessionmanager.SessionManager;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;


import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DoctorPrescriptionDetailsActivity extends AppCompatActivity {
    EditText etdoctorcomments;


    String TAG = "DoctorPrescriptionDetailsActivity";

    AVLoadingIndicatorView avi_indicator;
    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;

    PrescriptionCreateRequest.PrescriptionDataBean prescriptionData;

    SessionManager session;

    private String Doctor_Name = "";
    private String Doctor_Image = "";
    private String Doctor_ID = "";
    private String Treatment_Done_by = "";
    private String Patient_Name = "";
    private String Patient_Image = "";
    private String Patient_ID = "";
    private String Family_ID = "";
    private String Family_Name = "";
    private String Family_Image = "";
    private String Date = "";
    private String Doctor_Email_id = "";
    private String Patient_Email_id = "";
    private String userid;
    private String appoinmentid;

    RecyclerView rv_prescriptiondetails;
    TextView  txt_no_records;
    private List<PrescriptionCreateResponse.DataBean.PrescriptionDataBean> prescriptionDataList;
    private String pdfUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_prescription_details);




        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getProfileDetails();
        Doctor_Name = user.get(SessionManager.KEY_FIRST_NAME);
        Doctor_ID = user.get(SessionManager.KEY_ID);

        avi_indicator = findViewById(R.id.avi_indicator);
        avi_indicator.setVisibility(View.GONE);
        rv_prescriptiondetails = findViewById(R.id.rv_prescriptiondetails);
        txt_no_records = findViewById(R.id.txt_no_records);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            appoinmentid = extras.getString("id");
            userid = extras.getString("userid");
            Log.w(TAG,"userid :"+" "+userid);

        }

        if(appoinmentid != null){
            prescriptionDetailsResponseCall();
        }

        RelativeLayout back_rela = findViewById(R.id.back_rela);
        back_rela.setOnClickListener(v -> onBackPressed());




        etdoctorcomments = findViewById(R.id.etdoctorcomments);
        etdoctorcomments.setEnabled(false);


    }




    private void prescriptionDetailsResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<PrescriptionCreateResponse> call = ApiService.prescriptionDetailsResponseCall(RestUtils.getContentType(),prescriptionDetailsRequest());
        Log.w(TAG,"url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<PrescriptionCreateResponse>() {
            @Override
            public void onResponse(@NonNull Call<PrescriptionCreateResponse> call, @NonNull Response<PrescriptionCreateResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"PrescriptionCreateResponse"+ "--->" + new Gson().toJson(response.body()));


                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        if(response.body().getData().getDoctor_Comments() != null) {
                            etdoctorcomments.setText(response.body().getData().getDoctor_Comments());
                        }
                      if(response.body().getData().getPrescription_data() != null){
                          prescriptionDataList = response.body().getData().getPrescription_data();
                          pdfUrl = response.body().getData().getPDF_format();

                          /*if(prescriptionDataList.size()>0){
                              rv_prescriptiondetails.setVisibility(View.VISIBLE);
                              txt_no_records.setVisibility(View.GONE);
                              setView();
                          }else{
                              rv_prescriptiondetails.setVisibility(View.GONE);
                              txt_no_records.setVisibility(View.VISIBLE);

                          }*/

                          try
                          {
                              Intent intentUrl = new Intent(Intent.ACTION_VIEW);
                              intentUrl.setDataAndType(Uri.parse(pdfUrl), "application/pdf");
                              intentUrl.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                              startActivity(intentUrl);
                          }
                          catch (ActivityNotFoundException e)
                          {
                              Toast.makeText(DoctorPrescriptionDetailsActivity.this, "No PDF Viewer Installed", Toast.LENGTH_LONG).show();
                          }
                      }

                    }
                    else{

                        showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<PrescriptionCreateResponse> call, @NonNull Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG,"PrescriptionCreateResponseflr"+"--->" + t.getMessage());
            }
        });

    }
    private PrescriptionDetailsRequest prescriptionDetailsRequest() {
        /*
          * Appointment_ID
         */


        PrescriptionDetailsRequest prescriptionDetailsRequest = new PrescriptionDetailsRequest();
        prescriptionDetailsRequest.setAppointment_ID(appoinmentid);
        Log.w(TAG,"prescriptionDetailsRequest"+ "--->" + new Gson().toJson(prescriptionDetailsRequest));
        return prescriptionDetailsRequest;
    }



    public void showErrorLoading(String errormesage){
        alertDialogBuilder = new AlertDialog.Builder(this);
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }



    private void setView() {
        rv_prescriptiondetails.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_prescriptiondetails.setItemAnimator(new DefaultItemAnimator());
        DoctorPrescriptionsDetailsAdapter doctorPrescriptionsDetailsAdapter = new DoctorPrescriptionsDetailsAdapter(getApplicationContext(), prescriptionDataList);
        rv_prescriptiondetails.setAdapter(doctorPrescriptionsDetailsAdapter);

    }
}
