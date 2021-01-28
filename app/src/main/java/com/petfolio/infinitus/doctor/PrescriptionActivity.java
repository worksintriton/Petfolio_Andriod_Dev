package com.petfolio.infinitus.doctor;

import android.animation.LayoutTransition;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.requestpojo.AppoinmentCompleteRequest;
import com.petfolio.infinitus.requestpojo.DoctorCheckStatusRequest;
import com.petfolio.infinitus.requestpojo.PrescriptionCreateRequest;
import com.petfolio.infinitus.responsepojo.AppoinmentCompleteResponse;
import com.petfolio.infinitus.responsepojo.DoctorCheckStatusResponse;
import com.petfolio.infinitus.responsepojo.PrescriptionCreateResponse;
import com.petfolio.infinitus.sessionmanager.SessionManager;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PrescriptionActivity extends AppCompatActivity {
    EditText ettabletname,etquantity,etconsumption,etdoctorcomments;
    Button buttonAdd;
    LinearLayout container;
    Button btnSubmit;
    LinearLayout ll_headername;

    String TAG = "PrescriptionActivity";

    AVLoadingIndicatorView avi_indicator;
    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;

    PrescriptionCreateRequest.PrescriptionDataBean prescriptionData;
    List<PrescriptionCreateRequest.PrescriptionDataBean> prescriptionDataList = new ArrayList<>();

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getProfileDetails();
        Doctor_Name = user.get(SessionManager.KEY_FIRST_NAME);
        userid = user.get(SessionManager.KEY_ID);

        avi_indicator = findViewById(R.id.avi_indicator);
        avi_indicator.setVisibility(View.GONE);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            appoinmentid = extras.getString("id");
            Doctor_ID = extras.getString("doctorid");
            Log.w(TAG,"userid :"+" "+appoinmentid);

        }

        RelativeLayout back_rela = findViewById(R.id.back_rela);
        back_rela.setOnClickListener(v -> onBackPressed());



        ettabletname = findViewById(R.id.et_tabletname);
        etquantity = findViewById(R.id.et_quanity);
        etconsumption = findViewById(R.id.et_consumption);
        etdoctorcomments = findViewById(R.id.etdoctorcomments);

        buttonAdd = findViewById(R.id.add);
        ll_headername = findViewById(R.id.ll_headername);
        //ll_headername.setVisibility(View.GONE);

        container = findViewById(R.id.container);

        buttonAdd.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {

                LayoutInflater layoutInflater =
                        (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.row, null);
                final TextView tvtabletname = addView.findViewById(R.id.tv_tabletname);
                tvtabletname.setText(ettabletname.getText().toString());
                final TextView tvquantity = addView.findViewById(R.id.tv_quanity);
                tvquantity.setText(etquantity.getText().toString());
                final TextView tvconsumption = addView.findViewById(R.id.tv_consumption);
                tvconsumption.setText(etconsumption.getText().toString());
                Button buttonRemove = addView.findViewById(R.id.remove);

                buttonRemove.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        ((LinearLayout)addView.getParent()).removeView(addView);
                    }});


                if(!ettabletname.getText().toString().isEmpty() && !etquantity.getText().toString().isEmpty() && !etconsumption.getText().toString().isEmpty()){
                    ll_headername.setVisibility(View.VISIBLE);
                    container.addView(addView, 0);
                }else{
                    showErrorLoading("Please fill all the fields");
                    //ll_headername.setVisibility(View.GONE);
                }

                clearField();
            }});

        LayoutTransition transition = new LayoutTransition();
        container.setLayoutTransition(transition);


        btnSubmit = findViewById(R.id.btnsubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {


                    prescriptionDataList.clear();

                    String showallPrompt = "";

                    int childCount = container.getChildCount();
                    showallPrompt += "childCount: " + childCount + "\n\n";

                    for(int c=0; c<childCount; c++){
                        prescriptionData  = new PrescriptionCreateRequest.PrescriptionDataBean();
                        View childView = container.getChildAt(c);
                        TextView childTabletName = childView.findViewById(R.id.tv_tabletname);
                        String childTextTabletName = (String)(childTabletName.getText());

                        TextView childQuantity = childView.findViewById(R.id.tv_quanity);
                        String childTexQuantity = (String)(childQuantity.getText());

                        TextView childConsumption = childView.findViewById(R.id.tv_consumption);
                        String childTextConsumption = (String)(childConsumption.getText());

                        showallPrompt += c + ": " + childTextTabletName + "\n"+
                                c + ": " + childTexQuantity+"\n"+
                                c + ": " + childTextConsumption +"\n" ;

                        prescriptionData.setTablet_name(childTextTabletName);
                        prescriptionData.setQuantity(childTexQuantity);
                        prescriptionData.setConsumption(childTextConsumption);
                        prescriptionDataList.add(prescriptionData);

                    }


                    //  Toast.makeText(PrescriptionExActivity.this, showallPrompt, Toast.LENGTH_LONG).show();

                    Log.w(TAG,"showallPrompt-->"+showallPrompt);


                    if(etdoctorcomments.getText().toString().isEmpty()){
                    //showErrorLoading("Please fill all the fields");
                        etdoctorcomments.setError("Please enter the comments ");
                        etdoctorcomments.requestFocus();
                    }else if(prescriptionDataList.isEmpty()){
                    showErrorLoading("Please fill the prescription fields");
                     }
                    else{
                        if (new ConnectionDetector(PrescriptionActivity.this).isNetworkAvailable(PrescriptionActivity.this)) {
                            if(Treatment_Done_by.equalsIgnoreCase("Self")){
                                Family_ID = "";
                                Family_Name = "";

                            }else{
                                Family_Name = Family_Name;
                                Family_ID = Family_ID;
                            }
                            prescriptionCreateRequestCall();
                        }
                   }


            }});
    }

    public void clearField(){
        ettabletname.setText("");
        etquantity.setText("");
        etconsumption.setText("");
        ettabletname.requestFocus();

    }


    private void prescriptionCreateRequestCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<PrescriptionCreateResponse> call = ApiService.prescriptionCreateRequestCall(RestUtils.getContentType(),prescriptionCreateRequest());
        Log.w(TAG,"url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<PrescriptionCreateResponse>() {
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
        prescriptionCreateRequest.setDoctor_Comments(etdoctorcomments.getText().toString().trim());
        prescriptionCreateRequest.setPDF_format("");
        prescriptionCreateRequest.setPrescription_type("PDF");
        prescriptionCreateRequest.setPrescription_img("");
        prescriptionCreateRequest.setUser_id(userid);
        prescriptionCreateRequest.setPrescription_data(prescriptionDataList);
        prescriptionCreateRequest.setTreatment_Done_by(Treatment_Done_by);
        prescriptionCreateRequest.setAppointment_ID(appoinmentid);
        Log.w(TAG,"prescriptionCreateRequest"+ "--->" + new Gson().toJson(prescriptionCreateRequest));
        return prescriptionCreateRequest;
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
        Toasty.warning(getApplicationContext(), "This action is disabled in this screen..", Toast.LENGTH_SHORT, true).show();
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
                        startActivity(new Intent(PrescriptionActivity.this,DoctorDashboardActivity.class));





                    }
                    else{
                        //showErrorLoading(response.body().getMessage());
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
        Log.w(TAG,"appoinmentCompleteRequest"+ "--->" + new Gson().toJson(appoinmentCompleteRequest));
        return appoinmentCompleteRequest;
    }
}
