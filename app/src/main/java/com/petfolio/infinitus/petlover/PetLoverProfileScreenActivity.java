package com.petfolio.infinitus.petlover;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.activity.LoginActivity;
import com.petfolio.infinitus.activity.location.ManageAddressActivity;
import com.petfolio.infinitus.adapter.ManagePetListAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.interfaces.PetDeleteListener;
import com.petfolio.infinitus.requestpojo.LocationDeleteRequest;
import com.petfolio.infinitus.requestpojo.PetDeleteRequest;
import com.petfolio.infinitus.requestpojo.PetListRequest;
import com.petfolio.infinitus.responsepojo.LocationDeleteResponse;
import com.petfolio.infinitus.responsepojo.PetDeleteResponse;
import com.petfolio.infinitus.responsepojo.PetListResponse;
import com.petfolio.infinitus.sessionmanager.SessionManager;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetLoverProfileScreenActivity extends AppCompatActivity implements View.OnClickListener, PetDeleteListener {
    private  String TAG = "PetLoverProfileScreenActivity";
    @BindView(R.id.img_back)
    ImageView img_back;

    @BindView(R.id.txt_usrname)
    TextView txt_usrname;

    @BindView(R.id.txt_mail)
    TextView txt_mail;

    @BindView(R.id.txt_phn_num)
    TextView txt_phn_num;

    @BindView(R.id.txt_manage_address)
    TextView txt_manage_address;

    @BindView(R.id.txt_change_password)
    TextView txt_change_password;

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.rv_pet)
    RecyclerView rv_pet;


    @BindView(R.id.txt_no_records)
    TextView txt_no_records;

    @BindView(R.id.txt_logout)
    TextView txt_logout;

    @BindView(R.id.ll_add)
    LinearLayout ll_add;

    private SessionManager session;
    String name,emailid,phoneNo,userid;
    private List<PetListResponse.DataBean> petList;
    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_lover_profile_screen);
        ButterKnife.bind(this);

        Log.w(TAG,"onCreate : ");
        avi_indicator.setVisibility(View.GONE);
        ll_add.setVisibility(View.GONE);


        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getProfileDetails();
        name = user.get(SessionManager.KEY_FIRST_NAME);
        emailid = user.get(SessionManager.KEY_EMAIL_ID);
        phoneNo = user.get(SessionManager.KEY_MOBILE);
        userid = user.get(SessionManager.KEY_ID);


        txt_usrname.setText(name);
        txt_mail.setText(emailid);
        txt_phn_num.setText(phoneNo);




        if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext())) {
            petListResponseCall();
        }


        img_back.setOnClickListener(this);
        txt_manage_address.setOnClickListener(this);
        txt_change_password.setOnClickListener(this);
        txt_logout.setOnClickListener(this);
        ll_add.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),PetLoverDashboardActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                onBackPressed();
                break;
                case R.id.txt_manage_address:
                    gotoManageAddress();
                break;
                case R.id.txt_change_password:
                break;
                case R.id.txt_logout:
                    confirmLogoutDialog();
                break; 
                case R.id.ll_add:
                    gotoAddYourPet();
                break;
        }
    }

    private void gotoAddYourPet() {
        startActivity(new Intent(getApplicationContext(),AddYourPetOldUserActivity.class));
    }

    private void gotoManageAddress() {
        startActivity(new Intent(PetLoverProfileScreenActivity.this, ManageAddressActivity.class));
    }

    private void confirmLogoutDialog(){
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(PetLoverProfileScreenActivity.this);
        alertDialogBuilder.setMessage("Are you sure want to logout?");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1)
                    {

                        gotoLogout();


                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialogBuilder.setCancelable(true);
            }
        });

        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
    private void gotoLogout() {
        session.logoutUser();
        session.setIsLogin(false);
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();



    }
    private void petListResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<PetListResponse> call = apiInterface.petListResponseCall(RestUtils.getContentType(), petListRequest());
        Log.w(TAG,"PetListResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<PetListResponse>() {
            @Override
            public void onResponse(@NonNull Call<PetListResponse> call, @NonNull Response<PetListResponse> response) {

                Log.w(TAG,"PetListResponse"+ "--->" + new Gson().toJson(response.body()));

                avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getCode() == 200){

                        if(response.body().getData().isEmpty()){
                            txt_no_records.setVisibility(View.VISIBLE);
                            txt_no_records.setText("No new pets");
                            ll_add.setVisibility(View.VISIBLE);
                            rv_pet.setVisibility(View.GONE);
                        }
                        else{
                            txt_no_records.setVisibility(View.GONE);
                            rv_pet.setVisibility(View.VISIBLE);
                            ll_add.setVisibility(View.GONE);
                            petList = response.body().getData();
                            setView();
                        }



                    }
                    else{
                        //showErrorLoading(response.body().getMessage());
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

      //  rv_pet.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_pet.setItemAnimator(new DefaultItemAnimator());
        ManagePetListAdapter managePetListAdapter = new ManagePetListAdapter(getApplicationContext(), petList, rv_pet,this);
        rv_pet.setAdapter(managePetListAdapter);

    }


    @Override
    public void petDeleteListener(boolean status, String petid) {
        if(petid != null){
            showStatusAlert(petid);
        }

    }

    private void showStatusAlert(final String petid) {

        try {

            dialog = new Dialog(PetLoverProfileScreenActivity.this);
            dialog.setContentView(R.layout.alert_approve_reject_layout);
            TextView tvheader = (TextView)dialog.findViewById(R.id.tvInternetNotConnected);
            tvheader.setText(R.string.deletepetmsg);
            Button dialogButtonApprove = (Button) dialog.findViewById(R.id.btnApprove);
            dialogButtonApprove.setText("Yes");
            Button dialogButtonRejected = (Button) dialog.findViewById(R.id.btnReject);
            dialogButtonRejected.setText("No");

            dialogButtonApprove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();

                    petDeleteResponseCall(petid);


                }
            });
            dialogButtonRejected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();




                }
            });
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }




    }
    private void petDeleteResponseCall( String petid) {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();

        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<PetDeleteResponse> call = apiInterface.petDeleteResponseCall(RestUtils.getContentType(),petDeleteRequest(petid));

        Log.w(TAG,"url  :%s"+call.request().url().toString());

        call.enqueue(new Callback<PetDeleteResponse>() {
            @Override
            public void onResponse(@NotNull Call<PetDeleteResponse> call, @NotNull Response<PetDeleteResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"PetDeleteResponse"+ "--->" + new Gson().toJson(response.body()));

                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        Toasty.success(getApplicationContext(), "Pet Removed Successfully", Toast.LENGTH_SHORT, true).show();
                        finish();
                        overridePendingTransition( 0, 0);
                        startActivity(getIntent());
                        overridePendingTransition( 0, 0);

                    }
                }



            }

            @Override
            public void onFailure(@NotNull Call<PetDeleteResponse> call, @NotNull Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG,"PetDeleteResponse flr"+"--->" + t.getMessage());
            }
        });

    }
    private PetDeleteRequest petDeleteRequest(String petid) {

        /*
          _id : 5f05d911f3090625a91f40c7
         */
        PetDeleteRequest petDeleteRequest = new PetDeleteRequest();
        petDeleteRequest.set_id(petid);
        Log.w(TAG,"petDeleteRequest"+ "--->" + new Gson().toJson(petDeleteRequest));
        return petDeleteRequest;
    }
}