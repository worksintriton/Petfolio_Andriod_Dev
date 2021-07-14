package com.petfolio.infinituss.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.petfolio.infinituss.R;
import com.petfolio.infinituss.adapter.PetLoverSOSAdapter;
import com.petfolio.infinituss.api.APIClient;
import com.petfolio.infinituss.doctor.DoctorDashboardActivity;
import com.petfolio.infinituss.interfaces.SoSCallListener;
import com.petfolio.infinituss.petlover.PetLoverDashboardActivity;

import com.petfolio.infinituss.serviceprovider.ServiceProviderDashboardActivity;
import com.petfolio.infinituss.sessionmanager.SessionManager;

import com.petfolio.infinituss.vendor.VendorDashboardActivity;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SoSActivity extends AppCompatActivity implements SoSCallListener {

    String TAG = "SoSActivity";


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_no_records)
    TextView txt_no_records;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_sosnumbers)
    RecyclerView rv_sosnumbers;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_back)
    ImageView img_back;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_call)
    Button btn_call;





    SessionManager session;
    String type = "",name = "",userid = "";
    private String fromactivity;

    private static final int REQUEST_PHONE_CALL =1 ;
    private String sosPhonenumber;


    @SuppressLint("LogNotTimber")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);
        Log.w(TAG,"onCreate-->");
        ButterKnife.bind(this);
        avi_indicator.setVisibility(View.GONE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            fromactivity = extras.getString("fromactivity");
            Log.w(TAG,"Bundle "+" fromactivity : "+fromactivity);



        }



        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getProfileDetails();
        type = user.get(SessionManager.KEY_TYPE);
        name = user.get(SessionManager.KEY_FIRST_NAME);
        userid = user.get(SessionManager.KEY_ID);
        Log.w(TAG,"session--->"+"type :"+type+" "+"name :"+" "+name);
        img_back.setOnClickListener(v -> onBackPressed());

        if(APIClient.sosList != null && APIClient.sosList.size()>0){
            rv_sosnumbers.setVisibility(View.VISIBLE);
            btn_call.setVisibility(View.VISIBLE);
            txt_no_records.setVisibility(View.GONE);
            rv_sosnumbers.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            rv_sosnumbers.setItemAnimator(new DefaultItemAnimator());
            PetLoverSOSAdapter petLoverSOSAdapter = new PetLoverSOSAdapter(getApplicationContext(), APIClient.sosList,this);
            rv_sosnumbers.setAdapter(petLoverSOSAdapter);
        }
        else{
            rv_sosnumbers.setVisibility(View.GONE);
            btn_call.setVisibility(View.GONE);
            txt_no_records.setVisibility(View.VISIBLE);
            txt_no_records.setText(getResources().getString(R.string.no_phone_numbers));

        }

        btn_call.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SoSActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
            }
            else
            {
                gotoPhone();
            }

        });



    }

    private void gotoPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + sosPhonenumber));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();

    }








    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(fromactivity != null && fromactivity.equalsIgnoreCase("PetLoverNavigationDrawerNew")){
            startActivity(new Intent(getApplicationContext(), PetLoverDashboardActivity.class));
            finish();
        }else if(fromactivity != null && fromactivity.equalsIgnoreCase("DoctorNavigationDrawer")){
            startActivity(new Intent(getApplicationContext(), DoctorDashboardActivity.class));
            finish();
        }else if(fromactivity != null && fromactivity.equalsIgnoreCase("VendorNavigationDrawer")){
            startActivity(new Intent(getApplicationContext(), VendorDashboardActivity.class));
            finish();
        }else if(fromactivity != null && fromactivity.equalsIgnoreCase("ServiceProviderNavigationDrawer")){
            startActivity(new Intent(getApplicationContext(), ServiceProviderDashboardActivity.class));
            finish();
        }else{
            finish();
        }

    }



    @Override
    public void soSCallListener(long phonenumber) {
        if(phonenumber != 0){
            sosPhonenumber = String.valueOf(phonenumber);
        }
    }
}
