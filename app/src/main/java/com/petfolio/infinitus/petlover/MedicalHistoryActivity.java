package com.petfolio.infinitus.petlover;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.petfolio.infinitus.R;

import com.petfolio.infinitus.adapter.ManagePetListAdapter;
import com.petfolio.infinitus.adapter.ManagePetListMedicalHistoryAdapter;
import com.petfolio.infinitus.adapter.MedicalHistoryListAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.doctor.DoctorPrescriptionDetailsActivity;
import com.petfolio.infinitus.interfaces.PetDeleteListener;

import com.petfolio.infinitus.interfaces.PrescriptionListener;
import com.petfolio.infinitus.requestpojo.MedicalHistoryRequest;
import com.petfolio.infinitus.requestpojo.PetDeleteRequest;
import com.petfolio.infinitus.requestpojo.PetListRequest;
import com.petfolio.infinitus.requestpojo.PrescriptionDetailsRequest;
import com.petfolio.infinitus.responsepojo.MedicalHistoryResponse;
import com.petfolio.infinitus.responsepojo.PetDeleteResponse;
import com.petfolio.infinitus.responsepojo.PetListResponse;
import com.petfolio.infinitus.responsepojo.PrescriptionCreateResponse;
import com.petfolio.infinitus.sessionmanager.SessionManager;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicalHistoryActivity extends AppCompatActivity implements View.OnClickListener, PetDeleteListener, BottomNavigationView.OnNavigationItemSelectedListener, PrescriptionListener {
    private  String TAG = "MedicalHistoryActivity";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_back)
    ImageView img_back;



    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_pet)
    RecyclerView rv_pet;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_no_records)
    TextView txt_no_records;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_medical_history)
    RecyclerView rv_medical_history;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_no_medical_records)
    TextView txt_no_medical_records;





    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.include_petlover_footer)
    View include_petlover_footer;

    BottomNavigationView bottom_navigation_view;





    private SessionManager session;
    String name,emailid,phoneNo,userid;
    private List<PetListResponse.DataBean> petList;
    private Dialog dialog;
    private String profileimage;
    private String fromactivity;
    private String doctorid,doctorname,distance;

    private String catid;
    private String spid;
    private String from;

    private static final int REQUEST_PHONE_CALL =1 ;
    private String sosPhonenumber;
    private String active_tag;
    private String pdfUrl;
    private String url;
    private String petid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_history);
        ButterKnife.bind(this);

        Log.w(TAG,"onCreate : ");
        avi_indicator.setVisibility(View.GONE);


        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getProfileDetails();
        name = user.get(SessionManager.KEY_FIRST_NAME);
        emailid = user.get(SessionManager.KEY_EMAIL_ID);
        phoneNo = user.get(SessionManager.KEY_MOBILE);
        userid = user.get(SessionManager.KEY_ID);
        profileimage = user.get(SessionManager.KEY_PROFILE_IMAGE);
        Log.w(TAG,"profileimage"+ "--->" + profileimage);











        if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext())) {
            petListResponseCall();
        }


        img_back.setOnClickListener(this);





        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            fromactivity = extras.getString("fromactivity");
            active_tag = extras.getString("active_tag");
            /*DoctorClinicDetailsActivity*/
            doctorid = extras.getString("doctorid");
            doctorname = extras.getString("doctorname");
            distance = extras.getString("distance");
            Log.w(TAG,"fromactivity : "+fromactivity+" active_tag : "+active_tag);

            /*SelectedServiceActivity*/
            catid = extras.getString("catid");
            from = extras.getString("from");

            /*Service_Details_Activity*/
            spid = extras.getString("spid");
            catid = extras.getString("catid");
            from = extras.getString("from");
        }

        bottom_navigation_view = include_petlover_footer.findViewById(R.id.bottom_navigation_view);
        bottom_navigation_view.setItemIconTintList(null);
        bottom_navigation_view.setOnNavigationItemSelectedListener(this);
        bottom_navigation_view.getMenu().findItem(R.id.home).setChecked(true);
        if(active_tag != null){
            if(active_tag.equalsIgnoreCase("3")) {
                bottom_navigation_view.getMenu().findItem(R.id.services).setChecked(true);

            }else if(active_tag.equalsIgnoreCase("4")) {
                bottom_navigation_view.getMenu().findItem(R.id.care).setChecked(true);

            }

        }



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),PetLoverDashboardActivity.class));
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                onBackPressed();
                break;

                case R.id.ll_add:
                    gotoAddYourPet();
                break;

        }
    }


    private void gotoAddYourPet() {
        startActivity(new Intent(getApplicationContext(),AddYourPetOldUserActivity.class));
    }



    private void petListResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<PetListResponse> call = apiInterface.petListResponseCall(RestUtils.getContentType(), petListRequest());
        Log.w(TAG,"PetListResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<PetListResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<PetListResponse> call, @NonNull Response<PetListResponse> response) {

                Log.w(TAG,"PetListResponse"+ "--->" + new Gson().toJson(response.body()));

                avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getCode() == 200){

                        if(response.body().getData() != null && response.body().getData().size()>0){
                            txt_no_records.setVisibility(View.GONE);
                            rv_pet.setVisibility(View.VISIBLE);
                            petList = response.body().getData();
                            for(int i=0;i<petList.size();i++){
                                petid = petList.get(0).get_id();
                            }
                            if(petid != null){
                                medicalHistoryResponseCall(petid);
                            }
                            setView();

                        }
                        else{
                            txt_no_records.setVisibility(View.VISIBLE);
                            txt_no_records.setText("No new pets");
                            rv_pet.setVisibility(View.GONE);
                        }



                    }

                }


            }

            @Override
            public void onFailure(@NonNull Call<PetListResponse> call, @NonNull Throwable t) {

                avi_indicator.smoothToHide();
                Log.w(TAG,"PetListResponse flr"+"--->" + t.getMessage());
            }
        });

    }
    private PetListRequest petListRequest() {
        PetListRequest petListRequest = new PetListRequest();
        petListRequest.setUser_id(userid);
        Log.w(TAG,"petListRequest"+ "--->" + new Gson().toJson(petListRequest));
        return petListRequest;
    }
    private void setView() {
        rv_pet.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv_pet.setItemAnimator(new DefaultItemAnimator());
        ManagePetListMedicalHistoryAdapter managePetListMedicalHistoryAdapter = new ManagePetListMedicalHistoryAdapter(getApplicationContext(), petList, this);
        rv_pet.setAdapter(managePetListMedicalHistoryAdapter);

    }


    @Override
    public void petDeleteListener(boolean status, String petid) {
        if(petid != null){
            medicalHistoryResponseCall(petid);
        }

    }

    @SuppressLint("LogNotTimber")
    private void medicalHistoryResponseCall(String petid) {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();

        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<MedicalHistoryResponse> call = apiInterface.medicalHistoryResponseCall(RestUtils.getContentType(),medicalHistoryRequest(petid));

        Log.w(TAG,"url  :%s"+call.request().url().toString());

        call.enqueue(new Callback<MedicalHistoryResponse>() {
            @SuppressLint({"LogNotTimber", "SetTextI18n"})
            @Override
            public void onResponse(@NotNull Call<MedicalHistoryResponse> call, @NotNull Response<MedicalHistoryResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"MedicalHistoryResponse"+ "--->" + new Gson().toJson(response.body()));

                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        if(response.body().getData() != null && response.body().getData().size()>0){
                           rv_medical_history.setVisibility(View.VISIBLE);
                           txt_no_medical_records.setVisibility(View.GONE);
                            setViewMedicalHistory(response.body().getData());

                        }else {
                            rv_medical_history.setVisibility(View.GONE);
                            txt_no_medical_records.setVisibility(View.VISIBLE);
                            txt_no_medical_records.setText("No medical history");
                        }

                    }
                }



            }

            @Override
            public void onFailure(@NotNull Call<MedicalHistoryResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG,"MedicalHistoryResponse flr"+"--->" + t.getMessage());
            }
        });

    }
    @SuppressLint("LogNotTimber")
    private MedicalHistoryRequest medicalHistoryRequest(String petid) {
        /**
         * user_id : 603e27792c2b43125f8cb802
         * pet_id : 60407c392c2b43125f8cb83c
         */
        MedicalHistoryRequest medicalHistoryRequest = new MedicalHistoryRequest();
        medicalHistoryRequest.setPet_id(petid);
        medicalHistoryRequest.setUser_id(userid);
        Log.w(TAG,"medicalHistoryRequest"+ "--->" + new Gson().toJson(medicalHistoryRequest));
        return medicalHistoryRequest;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                callDirections("1");
                break;
            case R.id.shop:
                callDirections("2");
                break;
            case R.id.services:
                callDirections("3");
                break;
            case R.id.care:
                callDirections("4");
                break;
            case R.id.community:
                callDirections("5");
                break;

            default:
                return  false;
        }
        return true;
    }
    public void callDirections(String tag){
        Intent intent = new Intent(getApplicationContext(), PetLoverDashboardActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);
        finish();
    }

    private void setViewMedicalHistory(List<MedicalHistoryResponse.DataBean> data) {
        rv_medical_history.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_medical_history.setItemAnimator(new DefaultItemAnimator());
        MedicalHistoryListAdapter medicalHistoryListAdapter = new MedicalHistoryListAdapter(getApplicationContext(), data,this);
        rv_medical_history.setAdapter(medicalHistoryListAdapter);

    }


    @Override
    public void prescriptionListener(String appointmentid) {
        if(appointmentid != null){
            prescriptionDetailsResponseCall(appointmentid);
        }
    }

    private void prescriptionDetailsResponseCall(String appointmentid) {
          avi_indicator.setVisibility(View.VISIBLE);
          avi_indicator.smoothToShow();
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<PrescriptionCreateResponse> call = ApiService.prescriptionDetailsResponseCall(RestUtils.getContentType(),prescriptionDetailsRequest(appointmentid));
        Log.w(TAG,"url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<PrescriptionCreateResponse>() {
            @SuppressLint({"SetJavaScriptEnabled", "LogNotTimber"})
            @Override
            public void onResponse(@NonNull Call<PrescriptionCreateResponse> call, @NonNull Response<PrescriptionCreateResponse> response) {
              avi_indicator.smoothToHide();
                Log.w(TAG,"PrescriptionCreateResponse"+ "--->" + new Gson().toJson(response.body()));


                if (response.body() != null) {
                    if(response.body().getCode() == 200){

                        if(response.body().getData()!=null){


                            if(response.body().getData().getPrescription_data() != null){
                                pdfUrl = response.body().getData().getPDF_format();


                                try
                                {
                                    Log.w(TAG,"pdfUrl : "+pdfUrl);
                                    if(pdfUrl != null) {

                                        url = pdfUrl;
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(pdfUrl)));




                                    }





                                }
                                catch (Exception e)
                                {
                                }
                            }

                        }


                    }

                }


            }

            @SuppressLint("LogNotTimber")
            @Override
            public void onFailure(@NonNull Call<PrescriptionCreateResponse> call, @NonNull Throwable t) {
                //               avi_indicator.smoothToHide();

                Log.w(TAG,"PrescriptionCreateResponseflr"+"--->" + t.getMessage());
            }
        });

    }
   /* private void setPdfUrl(String pdfurl) {

        //Create a RemotePDFViewPager object
        remotePDFViewPager = new RemotePDFViewPager(MedicalHistoryActivity.this, pdfurl, this);

    }*/
    private PrescriptionDetailsRequest prescriptionDetailsRequest(String appointmentid) {
        /*
         * Appointment_ID
         */


        PrescriptionDetailsRequest prescriptionDetailsRequest = new PrescriptionDetailsRequest();
        prescriptionDetailsRequest.setAppointment_ID(appointmentid);
        Log.w(TAG,"prescriptionDetailsRequest"+ "--->" + new Gson().toJson(prescriptionDetailsRequest));
        return prescriptionDetailsRequest;
    }

    public void downloadPdfContent(String urlToDownload){

        try {

            String fileName="xyz";
            String fileExtension=".pdf";

//           download pdf file.

            URL url = new URL(urlToDownload);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();
            String PATH = Environment.getExternalStorageDirectory() + "/mydownload/";
            File file = new File(PATH);
            file.mkdirs();
            File outputFile = new File(file, fileName+fileExtension);
            FileOutputStream fos = new FileOutputStream(outputFile);
            InputStream is = c.getInputStream();
            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);
            }
            fos.close();
            is.close();

            System.out.println("--pdf downloaded--ok--"+urlToDownload);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}