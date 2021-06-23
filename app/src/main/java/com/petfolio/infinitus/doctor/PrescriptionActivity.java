package com.petfolio.infinitus.doctor;

import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.appUtils.NumericKeyBoardTransformationMethod;
import com.petfolio.infinitus.requestpojo.AppoinmentCompleteRequest;
import com.petfolio.infinitus.requestpojo.PrescriptionCreateRequest;
import com.petfolio.infinitus.requestpojo.SubDiagnosisRequest;
import com.petfolio.infinitus.responsepojo.AppoinmentCompleteResponse;
import com.petfolio.infinitus.responsepojo.DiagnosisListResponse;
import com.petfolio.infinitus.responsepojo.PrescriptionCreateResponse;
import com.petfolio.infinitus.responsepojo.SubDiagnosisListResponse;
import com.petfolio.infinitus.sessionmanager.SessionManager;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.Serializable;
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


public class PrescriptionActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_tabletname)
    EditText et_tabletname;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_quanity)
    EditText et_quanity;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_consumption)
    EditText et_consumption;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.etdoctorcomments)
    EditText etdoctorcomments;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.chx_m)
    CheckBox chx_m;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.chx_a)
    CheckBox chx_a;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.chx_n)
    CheckBox chx_n;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.add)
    Button add;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_headername)
    LinearLayout ll_headername;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.container)
    LinearLayout container;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btnsubmit)
    Button btnSubmit;

      @SuppressLint("NonConstantResourceId")
      @BindView(R.id.avi_indicator)
      AVLoadingIndicatorView avi_indicator;

      @SuppressLint("NonConstantResourceId")
      @BindView(R.id.back_rela)
      RelativeLayout back_rela;

      @SuppressLint("NonConstantResourceId")
      @BindView(R.id.sprdiagnosistype)
      Spinner sprdiagnosistype;

      @SuppressLint("NonConstantResourceId")
      @BindView(R.id.rl_sub_diagnosis)
      RelativeLayout rl_sub_diagnosis;

      @SuppressLint("NonConstantResourceId")
      @BindView(R.id.sprsub_diagnosis)
      Spinner sprsub_diagnosis;

      @SuppressLint("NonConstantResourceId")
      @BindView(R.id.txt_sub_diagnosis)
      TextView txt_sub_diagnosis;


    String TAG = "PrescriptionActivity";


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
    private List<DiagnosisListResponse.DataBean> diagnosisList;

    HashMap<String, String> hashMap_diagnosis_id = new HashMap<>();
    private List<SubDiagnosisListResponse.DataBean> subDiagnosisList;
    private String DiagnosisType;
    private String DiagnosisTypeId;
    private String SubDiagnosisType;

    PrescriptionCreateRequest.PrescriptionDataBean.ConsumptionBean consumptionBean;



    @SuppressLint("LogNotTimber")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);

        ButterKnife.bind(this);

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getProfileDetails();
        Doctor_Name = user.get(SessionManager.KEY_FIRST_NAME);
        Doctor_ID = user.get(SessionManager.KEY_ID);

        avi_indicator.setVisibility(View.GONE);
        txt_sub_diagnosis.setVisibility(View.GONE);
        rl_sub_diagnosis.setVisibility(View.GONE);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            appoinmentid = extras.getString("id");
            userid = extras.getString("patient_id");
            Log.w(TAG,"appoinmentid :"+" "+appoinmentid);
            Log.w(TAG,"userid :"+" "+userid);

        }

        if (new ConnectionDetector(PrescriptionActivity.this).isNetworkAvailable(PrescriptionActivity.this)) {
              diagnosisListResponseCall();
        }

        back_rela.setOnClickListener(v -> onBackPressed());
        sprdiagnosistype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int arg2, long arg3) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.green));
                DiagnosisType = sprdiagnosistype.getSelectedItem().toString();
                DiagnosisTypeId = hashMap_diagnosis_id.get(DiagnosisType);
                subDiagnosisListResponseCall(DiagnosisTypeId);
                Log.w(TAG, "DiagnosisTypeId : " + DiagnosisTypeId + " DiagnosisType :" + DiagnosisType);

                if(DiagnosisType != null && !DiagnosisType.equalsIgnoreCase("Diagnosis Type")){
                    rl_sub_diagnosis.setVisibility(View.VISIBLE);
                }else{
                    rl_sub_diagnosis.setVisibility(View.GONE);
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        sprsub_diagnosis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int arg2, long arg3) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.green));
                SubDiagnosisType = sprsub_diagnosis.getSelectedItem().toString();
                Log.w(TAG, "SubDiagnosisType :" + SubDiagnosisType);


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        et_quanity.setTransformationMethod(new NumericKeyBoardTransformationMethod());
        et_consumption.setTransformationMethod(new NumericKeyBoardTransformationMethod());




        add.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {

                LayoutInflater layoutInflater =
                        (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.row, null);
                final TextView tvtabletname = addView.findViewById(R.id.tv_tabletname);
                tvtabletname.setText(et_tabletname.getText().toString());
                final TextView tvquantity = addView.findViewById(R.id.tv_quanity);
                tvquantity.setText(et_quanity.getText().toString());
                final TextView tvconsumption = addView.findViewById(R.id.tv_consumption);
                tvconsumption.setText(et_consumption.getText().toString());
                Button buttonRemove = addView.findViewById(R.id.remove);

                final CheckBox chx_mg = addView.findViewById(R.id.chx_m);
                final CheckBox chx_an = addView.findViewById(R.id.chx_a);
                final CheckBox chx_ng = addView.findViewById(R.id.chx_n);

                chx_mg.setChecked(chx_m.isChecked());
                chx_an.setChecked(chx_a.isChecked());
                chx_ng.setChecked(chx_n.isChecked());




              consumptionBean =  new PrescriptionCreateRequest.PrescriptionDataBean.ConsumptionBean();

                if(chx_m.isChecked()){
                    consumptionBean.setMorning(chx_m.isChecked());
                }else{
                    consumptionBean.setMorning(false);
                }if(chx_a.isChecked()){
                    consumptionBean.setEvening(chx_a.isChecked());
                }else{
                    consumptionBean.setEvening(false);
                }if(chx_n.isChecked()){
                    consumptionBean.setNight(chx_n.isChecked());
                }else{
                    consumptionBean.setNight(false);
                }



                Log.w(TAG,"Consumptions checked : m "+chx_m.isChecked()+" a "+chx_a.isChecked()+" n "+chx_n.isChecked());

                buttonRemove.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        ((LinearLayout)addView.getParent()).removeView(addView);
                    }});


                if(!et_tabletname.getText().toString().isEmpty() && !et_quanity.getText().toString().isEmpty() && chx_m.isChecked() || chx_a.isChecked() || chx_n.isChecked()){

                    Log.w(TAG,"prescriptionDataList  : tablet name "+et_tabletname.getText().toString()+" qty : "+et_quanity.getText().toString());
                    prescriptionData  = new PrescriptionCreateRequest.PrescriptionDataBean();

                    prescriptionData.setTablet_name(et_tabletname.getText().toString());
                    prescriptionData.setQuantity(et_quanity.getText().toString());
                    prescriptionData.setConsumption(consumptionBean);
                    prescriptionDataList.add(prescriptionData);

                    Log.w(TAG,"prescriptionDataList add : "+new Gson().toJson(prescriptionDataList));
                    ll_headername.setVisibility(View.VISIBLE);
                    container.addView(addView, 0);
                    clearField();


                }else{
                    showErrorLoading("Please fill all the fields");
                    //ll_headername.setVisibility(View.GONE);
                }


            }});

        LayoutTransition transition = new LayoutTransition();
        container.setLayoutTransition(transition);


        btnSubmit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {


                   // prescriptionDataList.clear();

                    String showallPrompt = "";

                    int childCount = container.getChildCount();
                    showallPrompt += "childCount: " + childCount + "\n\n";

                   /* for(int c=0; c<childCount; c++){
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
                        prescriptionData.setConsumption(consumptionBean);
                        prescriptionDataList.add(prescriptionData);

                    }*/


                    //  Toast.makeText(PrescriptionExActivity.this, showallPrompt, Toast.LENGTH_LONG).show();

                    Log.w(TAG,"showallPrompt-->"+showallPrompt);

                    if(validSelectDiagnosisType()){
                        if(validdSubDiagnosisType()){
                            if(etdoctorcomments.getText().toString().isEmpty()){
                    //showErrorLoading("Please fill all the fields");
                        etdoctorcomments.setError("Please enter the comments ");
                        etdoctorcomments.requestFocus();
                    }
                    else if(prescriptionDataList.isEmpty()){
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

                            Log.w(TAG,"prescriptionDataList : "+new Gson().toJson(prescriptionDataList));

                            Intent intent = new Intent(getApplicationContext(),PrescriptionDetailsActivity.class);
                            intent.putExtra("Doctor_ID",Doctor_ID);
                            intent.putExtra("Doctor_Comments",etdoctorcomments.getText().toString().trim());
                            intent.putExtra("prescriptionDataList", (Serializable) prescriptionDataList);
                            intent.putExtra("Treatment_Done_by", Treatment_Done_by);
                            intent.putExtra("id", appoinmentid);
                            intent.putExtra("userid", userid);
                            intent.putExtra("DiagnosisType", DiagnosisType);
                            intent.putExtra("SubDiagnosisType", SubDiagnosisType);
                            intent.putExtra("Doctor_ID", Doctor_ID);
                            intent.putExtra("Treatment_Done_by", Treatment_Done_by);
                            startActivity(intent);
                            //prescriptionCreateRequestCall();
                        }
                   }
                        }

                    }



            }});
    }

    public void clearField(){
        et_tabletname.setText("");
        et_quanity.setText("");
        et_consumption.setText("");
        et_tabletname.requestFocus();
        chx_m.setChecked(false);
        chx_a.setChecked(false);
        chx_n.setChecked(false);

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
        prescriptionCreateRequest.setDoctor_Comments(etdoctorcomments.getText().toString().trim());
        prescriptionCreateRequest.setPDF_format("");
        prescriptionCreateRequest.setPrescription_type("PDF");
        prescriptionCreateRequest.setPrescription_img("");
        prescriptionCreateRequest.setUser_id(userid);
        Log.w(TAG, "User_ID" + userid);
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
                        startActivity(new Intent(PrescriptionActivity.this, DoctorDashboardActivity.class));





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



    @SuppressLint("LogNotTimber")
    public void diagnosisListResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<DiagnosisListResponse> call = apiInterface.diagnosisListResponseCall(RestUtils.getContentType());
        Log.w(TAG, "url  :%s" + call.request().url().toString());

        call.enqueue(new Callback<DiagnosisListResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<DiagnosisListResponse> call, @NonNull Response<DiagnosisListResponse> response) {
                avi_indicator.smoothToHide();


                if (response.body() != null) {
                    if (200 == response.body().getCode()) {
                        Log.w(TAG, "diagnosisListResponseCall" + new Gson().toJson(response.body()));

                        if(response.body().getData() != null) {
                            diagnosisList = response.body().getData();
                        }
                        if (diagnosisList != null && diagnosisList.size() > 0) {
                            setDiagnosisType(diagnosisList);
                        }
                    }


                }


            }


            @Override
            public void onFailure(@NonNull Call<DiagnosisListResponse> call, @NonNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG, "DiagnosisListResponse flr" + t.getMessage());
            }
        });

    }
    private void setDiagnosisType(List<DiagnosisListResponse.DataBean> diagnosisList) {
        ArrayList<String> diagnosistypeArrayList = new ArrayList<>();
        diagnosistypeArrayList.add("Diagnosis Type");
        for (int i = 0; i < diagnosisList.size(); i++) {

            String diagnosisType = diagnosisList.get(i).getDiagnosis();
            hashMap_diagnosis_id.put(diagnosisList.get(i).getDiagnosis(), diagnosisList.get(i).get_id());

            Log.w(TAG, "diagnosisType-->" + diagnosisType);
            diagnosistypeArrayList.add(diagnosisType);

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(PrescriptionActivity.this, R.layout.spinner_item, diagnosistypeArrayList);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            sprdiagnosistype.setAdapter(spinnerArrayAdapter);



        }
    }
    @SuppressLint("LogNotTimber")
    private void subDiagnosisListResponseCall(String diagnosis_id) {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<SubDiagnosisListResponse> call = ApiService.subDiagnosisListResponseCall(RestUtils.getContentType(), subDiagnosisRequest(diagnosis_id));
        Log.w(TAG, "url  :%s" + call.request().url().toString());

        call.enqueue(new Callback<SubDiagnosisListResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<SubDiagnosisListResponse> call, @NonNull Response<SubDiagnosisListResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG, "BreedTypeResponse" + "--->" + new Gson().toJson(response.body()));


                if (response.body() != null) {
                    if (200 == response.body().getCode()) {
                        if(response.body().getData() != null) {
                            subDiagnosisList = response.body().getData();
                            if (subDiagnosisList != null && subDiagnosisList.size() > 0) {
                                txt_sub_diagnosis.setVisibility(View.VISIBLE);
                                setSubDiagnosisType(subDiagnosisList);
                            }else{
                                rl_sub_diagnosis.setVisibility(View.GONE);
                                txt_sub_diagnosis.setVisibility(View.GONE);
                            }
                        }

                    }

                }


            }


            @Override
            public void onFailure(@NonNull Call<SubDiagnosisListResponse> call, @NonNull Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG, "SubDiagnosisListResponse flr" + "--->" + t.getMessage());
            }
        });

    }

    private void setSubDiagnosisType(List<SubDiagnosisListResponse.DataBean> subDiagnosisList) {
        ArrayList<String> subDiagnosisArrayList = new ArrayList<>();
        subDiagnosisArrayList.add("SubDiagnosis Type");
        for (int i = 0; i < subDiagnosisList.size(); i++) {

            String SubDiagnosisType = subDiagnosisList.get(i).getSub_diagnosis();

            Log.w(TAG, "SubDiagnosisType-->" + SubDiagnosisType);
            subDiagnosisArrayList.add(SubDiagnosisType);

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(PrescriptionActivity.this, R.layout.spinner_item, subDiagnosisArrayList);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            sprsub_diagnosis.setAdapter(spinnerArrayAdapter);


        }
    }

    @SuppressLint("LogNotTimber")
    private SubDiagnosisRequest subDiagnosisRequest(String diagnosis_id) {
        SubDiagnosisRequest subDiagnosisRequest = new SubDiagnosisRequest();
        subDiagnosisRequest.setDiagnosis_id(diagnosis_id);
        Log.w(TAG, "subDiagnosisRequest" + "--->" + new Gson().toJson(subDiagnosisRequest));
        return subDiagnosisRequest;
    }

    public boolean validSelectDiagnosisType() {
        if (DiagnosisType != null && DiagnosisType.equalsIgnoreCase("Diagnosis Type")) {
            final AlertDialog alertDialog = new AlertDialog.Builder(PrescriptionActivity.this).create();
            alertDialog.setMessage(getString(R.string.err_msg_type_of_diagnosis));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                    (dialog, which) -> alertDialog.cancel());
            alertDialog.show();

            return false;
        }

        return true;
    }

    public boolean validdSubDiagnosisType() {
        if (SubDiagnosisType != null && SubDiagnosisType.equalsIgnoreCase("SubDiagnosis Type")) {
            final AlertDialog alertDialog = new AlertDialog.Builder(PrescriptionActivity.this).create();
            alertDialog.setMessage(getString(R.string.err_msg_type_of_sub_diagnosis));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                    (dialog, which) -> alertDialog.cancel());
            alertDialog.show();

            return false;
        }

        return true;
    }

}
