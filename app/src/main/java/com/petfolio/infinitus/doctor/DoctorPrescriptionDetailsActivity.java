package com.petfolio.infinitus.doctor;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import es.voghdev.pdfviewpager.library.util.FileUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DoctorPrescriptionDetailsActivity extends AppCompatActivity implements DownloadFile.Listener{
    EditText etdoctorcomments;
    String TAG = "DoctorPrescriptionDetailsActivity";
    AVLoadingIndicatorView avi_indicator;
    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;

    PrescriptionCreateRequest.PrescriptionDataBean prescriptionData;

    SessionManager session;


    private String userid;
    private String appoinmentid,doctor_id;

    RecyclerView rv_prescriptiondetails;
    TextView  txt_no_records;
    WebView webView;

    private List<PrescriptionCreateResponse.DataBean.PrescriptionDataBean> prescriptionDataList;
    private String pdfUrl;

    private RemotePDFViewPager remotePDFViewPager;

    private PDFPagerAdapter pdfPagerAdapter;

    private String url;

    private ProgressBar progressBar;

    private LinearLayout pdfLayout;


    @SuppressLint("LogNotTimber")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_prescription_details);
        Log.w(TAG,"Oncreate");

        //set the Visibility of the progressbar to visible
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        //initialize the pdfLayout
        pdfLayout = findViewById(R.id.pdf_layout);


        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getProfileDetails();


        //avi_indicator = findViewById(R.id.avi_indicator);
        //avi_indicator.setVisibility(View.GONE);
        //rv_prescriptiondetails = findViewById(R.id.rv_prescriptiondetails);
        //txt_no_records = findViewById(R.id.txt_no_records);
        //webView = findViewById(R.id.webView);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            appoinmentid = extras.getString("id");
            userid = extras.getString("userid");
            doctor_id = extras.getString("doctor_id");
            Log.w(TAG,"AppointID :"+" "+appoinmentid);
            Log.w(TAG,"userid :"+" "+userid);
            Log.w(TAG,"doctorid :"+" "+doctor_id);

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
//        avi_indicator.setVisibility(View.VISIBLE);
  //      avi_indicator.smoothToShow();
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<PrescriptionCreateResponse> call = ApiService.prescriptionDetailsResponseCall(RestUtils.getContentType(),prescriptionDetailsRequest());
        Log.w(TAG,"url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<PrescriptionCreateResponse>() {
            @SuppressLint({"SetJavaScriptEnabled", "LogNotTimber"})
            @Override
            public void onResponse(@NonNull Call<PrescriptionCreateResponse> call, @NonNull Response<PrescriptionCreateResponse> response) {
//                avi_indicator.smoothToHide();
                Log.w(TAG,"PrescriptionCreateResponse"+ "--->" + new Gson().toJson(response.body()));


                if (response.body() != null) {
                    if(response.body().getCode() == 200){

                        if(response.body().getData()!=null){

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
                                    Log.w(TAG,"pdfUrl : "+pdfUrl);
                                    if(pdfUrl != null) {
//                                  webView.requestFocus();
//                                  webView.getSettings().setJavaScriptEnabled(true);
//
//                                  final String googleDocs = "https://docs.google.com/viewer?url=";
//
//                                  String url = googleDocs + pdfUrl;
//                                  webView.loadUrl(pdfUrl);
//                                  webView.setWebViewClient(new WebViewClient() {
//                                      @Override
//                                      public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                                          view.loadUrl(url);
//                                          return true;
//                                      }
//                                  });
//                                  webView.setWebChromeClient(new WebChromeClient() {
//                                      public void onProgressChanged(WebView view, int progress) {
//                                          if (progress < 100) {
//
//                                          }
//                                          if (progress == 100) {
//
//                                          }
//                                      }
//                                  });

                                        //initialize the url variable
                                        url = pdfUrl;

                                        setPdfUrl(url);

                                    }




                             /* Intent intentUrl = new Intent(Intent.ACTION_VIEW);
                              intentUrl.setDataAndType(Uri.parse(pdfUrl), "application/pdf");
                              intentUrl.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                              startActivity(intentUrl);*/
                                }
                                catch (Exception e)
                                {
                                    //Toast.makeText(DoctorPrescriptionDetailsActivity.this, "No PDF Viewer Installed", Toast.LENGTH_LONG).show();
                                }
                            }

                        }


                    }
                    else{

                        showErrorLoading(response.body().getMessage());
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
    private void setPdfUrl(String pdfurl) {

        //Create a RemotePDFViewPager object
        remotePDFViewPager = new RemotePDFViewPager(DoctorPrescriptionDetailsActivity.this, pdfurl, this);

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

    public class WebViewController extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
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

    @Override
    public void onSuccess(String url, String destinationPath) {

        // That's the positive case. PDF Download went fine
        pdfPagerAdapter = new PDFPagerAdapter(this, FileUtil.extractFileNameFromURL(url));
        remotePDFViewPager.setAdapter(pdfPagerAdapter);
        updateLayout();
        progressBar.setVisibility(View.GONE);
    }

    private void updateLayout() {

        pdfLayout.addView(remotePDFViewPager,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void onFailure(Exception e) {
        // This will be called if download fails
    }

    @Override
    public void onProgressUpdate(int progress, int total) {
        // You will get download progress here
        // Always on UI Thread so feel free to update your views here
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (pdfPagerAdapter != null) {
            pdfPagerAdapter.close();
        }
    }
}
