package com.petfolio.infinitus.doctor;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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

import com.petfolio.infinitus.adapter.PrescriptionsDetailsAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.requestpojo.AppoinmentCompleteRequest;
import com.petfolio.infinitus.requestpojo.PrescriptionCreateRequest;
import com.petfolio.infinitus.responsepojo.AppoinmentCompleteResponse;
import com.petfolio.infinitus.responsepojo.PrescriptionCreateResponse;
import com.petfolio.infinitus.sessionmanager.SessionManager;

import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PrescriptionDetailsActivity extends AppCompatActivity {
    EditText etdoctorcomments;
    String TAG = "PrescriptionDetailsActivity";
    AVLoadingIndicatorView avi_indicator;
    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;

    PrescriptionCreateRequest.PrescriptionDataBean prescriptionData;

    SessionManager session;


    private String userid;
    private String appoinmentid,doctor_id,DiagnosisType,SubDiagnosisType;

    RecyclerView rv_prescriptiondetails;
    TextView  txt_no_records;
    WebView webView;

    List<PrescriptionCreateRequest.PrescriptionDataBean> prescriptionDataList ;




    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.include_petlover_header)
    View include_petlover_header;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_lbl_diagnosis)
    TextView txt_lbl_diagnosis;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_diagnosis)
    TextView txt_diagnosis;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_lbl_sub_diagnosis)
    TextView txt_lbl_sub_diagnosis;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_sub_diagnosis)
    TextView txt_sub_diagnosis;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_doctor_comments)
    TextView txt_doctor_comments;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_edit)
    Button btn_edit;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_submit)
    Button btn_submit;

    private String Doctor_Comments;
    private String Doctor_ID;
    private String Treatment_Done_by;


    @SuppressLint("LogNotTimber")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_details);
        ButterKnife.bind(this);
        Log.w(TAG,"Oncreate");

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getProfileDetails();


        avi_indicator = findViewById(R.id.avi_indicator);
        avi_indicator.setVisibility(View.GONE);
        rv_prescriptiondetails = findViewById(R.id.rv_prescriptiondetails);
        txt_no_records = findViewById(R.id.txt_no_records);

        txt_lbl_diagnosis.setVisibility(View.GONE);
        txt_diagnosis.setVisibility(View.GONE);
        txt_lbl_sub_diagnosis.setVisibility(View.GONE);
        txt_sub_diagnosis.setVisibility(View.GONE);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            appoinmentid = extras.getString("id");
            userid = extras.getString("userid");
            doctor_id = extras.getString("doctor_id");
            DiagnosisType = extras.getString("DiagnosisType");
            SubDiagnosisType = extras.getString("SubDiagnosisType");
            Doctor_Comments = extras.getString("Doctor_Comments");
            Doctor_ID = extras.getString("Doctor_ID");
            Treatment_Done_by = extras.getString("Treatment_Done_by");
            Log.w(TAG,"AppointID :"+" "+appoinmentid);
            Log.w(TAG,"userid :"+" "+userid);
            Log.w(TAG,"doctorid :"+" "+doctor_id);
            prescriptionDataList = (ArrayList<PrescriptionCreateRequest.PrescriptionDataBean>) getIntent().getSerializableExtra("prescriptionDataList");

            Log.w(TAG,"prescriptionDataList : "+ new Gson().toJson(prescriptionDataList));


            if(prescriptionDataList != null && prescriptionDataList.size()>0){
                setView();
            }


        }

        if(DiagnosisType != null){
            txt_lbl_diagnosis.setVisibility(View.VISIBLE);
            txt_diagnosis.setVisibility(View.VISIBLE);
            txt_diagnosis.setText(DiagnosisType);
        }
        if(SubDiagnosisType != null){
            txt_lbl_sub_diagnosis.setVisibility(View.VISIBLE);
            txt_sub_diagnosis.setVisibility(View.VISIBLE);
            txt_sub_diagnosis.setText(SubDiagnosisType);
        }
        if(Doctor_Comments != null){
            txt_doctor_comments.setText(Doctor_Comments);
        }

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (new ConnectionDetector(PrescriptionDetailsActivity.this).isNetworkAvailable(PrescriptionDetailsActivity.this)) {

                    prescriptionCreateRequestCall();
                }
            }
        });

        ImageView img_back = include_petlover_header.findViewById(R.id.img_back);
        ImageView img_sos = include_petlover_header.findViewById(R.id.img_sos);
        ImageView img_notification = include_petlover_header.findViewById(R.id.img_notification);
        ImageView img_cart = include_petlover_header.findViewById(R.id.img_cart);
        ImageView img_profile = include_petlover_header.findViewById(R.id.img_profile);
        TextView toolbar_title = include_petlover_header.findViewById(R.id.toolbar_title);
        toolbar_title.setText(getResources().getString(R.string.prescriptiondetails));



        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        img_sos.setVisibility(View.GONE);
        img_cart.setVisibility(View.GONE);


        etdoctorcomments = findViewById(R.id.etdoctorcomments);
        etdoctorcomments.setEnabled(false);


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
        PrescriptionsDetailsAdapter prescriptionsDetailsAdapter = new PrescriptionsDetailsAdapter(getApplicationContext(), prescriptionDataList);
        rv_prescriptiondetails.setAdapter(prescriptionsDetailsAdapter);

    }


    @SuppressLint("LogNotTimber")
    private void prescriptionCreateRequestCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<PrescriptionCreateResponse> call = ApiService.prescriptionCreateRequestCall(RestUtils.getContentType(),prescriptionCreateRequest());
        Log.w(TAG,"url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<PrescriptionCreateResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<PrescriptionCreateResponse> call, @NonNull Response<PrescriptionCreateResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"PrescriptionCreateResponse"+ "--->" + new Gson().toJson(response.body()));


                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        Toasty.success(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT, true).show();
                        appoinmentCompleteResponseCall();

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
    @SuppressLint("LogNotTimber")
    private PrescriptionCreateRequest prescriptionCreateRequest() {
        /*
         * doctor_id : 5ef3472a4b9bd73eb1cff539
         * Date : 23-10-2020 12:00 AM
         * Doctor_Comments : test
         * PDF_format :
         * Prescription_type : Image / PDF
         * Prescription_img : http://mysalveo.com/api/public/prescriptions/231afd32-6d68-4288-a8e5-1c599833c0e8.pdf
         * user_id : 5ef2c092c006bb0ed174c771
         * Prescription_data : [{"Quantity":"3","Tablet_name":"dolo","consumption":"twice"}]
         * Treatment_Done_by : Self
         * Appointment_ID
         */
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        PrescriptionCreateRequest prescriptionCreateRequest = new PrescriptionCreateRequest();
        prescriptionCreateRequest.setDoctor_id(Doctor_ID);
        prescriptionCreateRequest.setDate(currentDateandTime);
        prescriptionCreateRequest.setDoctor_Comments(Doctor_Comments);
        prescriptionCreateRequest.setPDF_format("");
        prescriptionCreateRequest.setPrescription_type("PDF");
        prescriptionCreateRequest.setPrescription_img("");
        prescriptionCreateRequest.setUser_id(userid);
        Log.w(TAG, "User_ID" + userid);
        prescriptionCreateRequest.setPrescription_data(prescriptionDataList);
        prescriptionCreateRequest.setTreatment_Done_by(Treatment_Done_by);
        prescriptionCreateRequest.setAppointment_ID(appoinmentid);
        prescriptionCreateRequest.setDiagnosis(DiagnosisType);
        prescriptionCreateRequest.setSub_diagnosis(SubDiagnosisType);
        Log.w(TAG,"prescriptionCreateRequest"+ "--->" + new Gson().toJson(prescriptionCreateRequest));
        return prescriptionCreateRequest;
    }

    private void appoinmentCompleteResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<AppoinmentCompleteResponse> call = apiInterface.appoinmentCompleteResponseCall(RestUtils.getContentType(), appoinmentCompleteRequest());
        Log.w(TAG,"AppoinmentCompleteResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<AppoinmentCompleteResponse>() {
            @Override
            public void onResponse(@NonNull Call<AppoinmentCompleteResponse> call, @NonNull Response<AppoinmentCompleteResponse> response) {

                Log.w(TAG,"AppoinmentCompleteResponse"+ "--->" + new Gson().toJson(response.body()));

                avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        Toasty.success(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT, true).show();

                        startActivity(new Intent(PrescriptionDetailsActivity.this, DoctorDashboardActivity.class));
                    }

                }


            }

            @Override
            public void onFailure(@NonNull Call<AppoinmentCompleteResponse> call, @NonNull Throwable t) {

                avi_indicator.smoothToHide();
                Log.w(TAG,"AppoinmentCompleteResponseflr"+"--->" + t.getMessage());
            }
        });

    }
    private AppoinmentCompleteRequest appoinmentCompleteRequest() {
        /*
         * _id : 5fc639ea72fc42044bfa1683
         * completed_at : 23-10-2000 10 : 00 AM
         * appoinment_status : Completed
         */

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        AppoinmentCompleteRequest appoinmentCompleteRequest = new AppoinmentCompleteRequest();
        appoinmentCompleteRequest.set_id(appoinmentid);
        appoinmentCompleteRequest.setCompleted_at(currentDateandTime);
        appoinmentCompleteRequest.setAppoinment_status("Completed");
        appoinmentCompleteRequest.setDiagnosis(DiagnosisType);
        appoinmentCompleteRequest.setSub_diagnosis(SubDiagnosisType);
        appoinmentCompleteRequest.setDoctor_comment(Doctor_Comments);
        Log.w(TAG,"appoinmentCompleteRequest"+ "--->" + new Gson().toJson(appoinmentCompleteRequest));
        return appoinmentCompleteRequest;
    }


}
