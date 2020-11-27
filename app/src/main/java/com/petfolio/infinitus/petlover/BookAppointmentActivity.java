package com.petfolio.infinitus.petlover;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.requestpojo.BreedTypeRequest;
import com.petfolio.infinitus.requestpojo.PetDetailsRequest;
import com.petfolio.infinitus.responsepojo.BreedTypeResponse;
import com.petfolio.infinitus.responsepojo.PetDetailsResponse;
import com.petfolio.infinitus.responsepojo.PetTypeListResponse;
import com.petfolio.infinitus.sessionmanager.SessionManager;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookAppointmentActivity extends AppCompatActivity {

    private static  String TAG = "BookAppointmentActivity";

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.spr_selectyourpettype)
    Spinner spr_selectyourpettype;
    
    @BindView(R.id.sprpettype)
    Spinner sprpettype;

    @BindView(R.id.sprpetbreed)
    Spinner sprpetbreed;

    @BindView(R.id.btn_continue)
    Button btn_continue;

    @BindView(R.id.txt_pettype)
    TextView txt_pettype;

    @BindView(R.id.txt_petbreed)
    TextView txt_petbreed;
    
    private List<PetTypeListResponse.DataBean.UsertypedataBean> usertypedataBeanList;
    private String strPetType;
    private String strPetBreedType;
    private String petTypeid;
    private String userid;
    private String strSelectyourPetType;

    HashMap<String, String> hashMap_PetTypeid = new HashMap<>();
    private String petTypeId;
    private List<PetDetailsResponse.DataBean> petDetailsResponseByUserIdList;
    private List<BreedTypeResponse.DataBean> breedTypedataBeanList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        ButterKnife.bind(this);

        txt_pettype.setVisibility(View.GONE);
        txt_petbreed.setVisibility(View.GONE);

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getProfileDetails();
        userid = user.get(SessionManager.KEY_ID);

        if(userid != null){
            if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext())) {
                petDetailsResponseByUserIdCall();
            }

        }

        spr_selectyourpettype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int arg2, long arg3) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.green));
                strSelectyourPetType = spr_selectyourpettype.getSelectedItem().toString();
                Log.w(TAG,"strPetType :"+strSelectyourPetType);





            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        sprpettype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int arg2, long arg3) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.green));
                strPetType = sprpettype.getSelectedItem().toString();
                petTypeId = hashMap_PetTypeid.get(strPetType);
                breedTypeResponseByPetIdCall(petTypeId);

                Log.w(TAG,"petTypeId : "+petTypeId+" strPetType :"+strPetType);


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        sprpetbreed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int arg2, long arg3) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.green));
                strPetBreedType = sprpetbreed.getSelectedItem().toString();
                Log.w(TAG,"strPetBreedType :"+strPetBreedType);


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookAppointmentActivity.this,PetAppointment_Doctor_Date_Time_Activity.class);
                startActivity(intent);
            }
        });

    }

    public void petTypeListResponseCall(){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<PetTypeListResponse> call = apiInterface.petTypeListResponseCall(RestUtils.getContentType());
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<PetTypeListResponse>() {
            @Override
            public void onResponse(@NonNull Call<PetTypeListResponse> call, @NonNull Response<PetTypeListResponse> response) {
                avi_indicator.smoothToHide();


                if (response.body() != null) {
                    if(200 == response.body().getCode()){
                        Log.w(TAG,"PetTypeListResponse" + new Gson().toJson(response.body()));

                        usertypedataBeanList = response.body().getData().getUsertypedata();
                        if(usertypedataBeanList != null && usertypedataBeanList.size()>0){
                            setPetType(usertypedataBeanList);
                        }
                    }



                }








            }


            @Override
            public void onFailure(@NonNull Call<PetTypeListResponse> call,@NonNull  Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"PetTypeListResponse flr"+t.getMessage());
            }
        });

    }

    private void setPetType(List<PetTypeListResponse.DataBean.UsertypedataBean> usertypedataBeanList) {
        ArrayList<String> pettypeArrayList = new ArrayList<>();
        pettypeArrayList.add("Select Pet Type");
        for (int i = 0; i < usertypedataBeanList.size(); i++) {

            String petType = usertypedataBeanList.get(i).getPet_type_title();
            hashMap_PetTypeid.put(usertypedataBeanList.get(i).getPet_type_title(), usertypedataBeanList.get(i).get_id());

            Log.w(TAG,"petType-->"+petType);
            pettypeArrayList.add(petType);

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(BookAppointmentActivity.this, R.layout.spinner_item, pettypeArrayList);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            sprpettype.setAdapter(spinnerArrayAdapter);


        }
    }


    private void breedTypeResponseByPetIdCall(String petTypeId) {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<BreedTypeResponse> call = ApiService.breedTypeResponseByPetIdCall(RestUtils.getContentType(),breedTypeRequest(petTypeId));
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<BreedTypeResponse>() {
            @Override
            public void onResponse(@NonNull Call<BreedTypeResponse> call, @NonNull Response<BreedTypeResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG, "BreedTypeResponse" + "--->" + new Gson().toJson(response.body()));


                if (response.body() != null) {
                    if (200 == response.body().getCode()) {
                        breedTypedataBeanList = response.body().getData();
                        if(breedTypedataBeanList != null && breedTypedataBeanList.size()>0){
                            setBreedType(breedTypedataBeanList);
                        }

                    }

                } else {

                }


            }



            @Override
            public void onFailure(@NonNull Call<BreedTypeResponse> call, @NonNull Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG,"BreedTypeResponse flr"+"--->" + t.getMessage());
            }
        });

    }

    private void setBreedType(List<BreedTypeResponse.DataBean> breedTypedataBeanList) {
        ArrayList<String> pettypeArrayList = new ArrayList<>();
        pettypeArrayList.add("Select Pet Breed");
        for (int i = 0; i < breedTypedataBeanList.size(); i++) {

            String petType = breedTypedataBeanList.get(i).getPet_breed();

            Log.w(TAG,"petType-->"+petType);
            pettypeArrayList.add(petType);

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(BookAppointmentActivity.this, R.layout.spinner_item, pettypeArrayList);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            sprpetbreed.setAdapter(spinnerArrayAdapter);


        }
    }

    private BreedTypeRequest breedTypeRequest(String petTypeId) {
        BreedTypeRequest breedTypeRequest = new BreedTypeRequest();
        breedTypeRequest.setPet_type_id(petTypeId);
        Log.w(TAG,"breedTypeRequest"+ "--->" + new Gson().toJson(breedTypeRequest));
        return breedTypeRequest;
    }


    private void petDetailsResponseByUserIdCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<PetDetailsResponse> call = ApiService.petDetailsResponseByUserIdCall(RestUtils.getContentType(),petDetailsRequest());
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<PetDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<PetDetailsResponse> call, @NonNull Response<PetDetailsResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG, "PetDetailsResponse" + "--->" + new Gson().toJson(response.body()));

                if (new ConnectionDetector(getApplicationContext()).isNetworkAvailable(getApplicationContext())) {

                    petTypeListResponseCall();
                }

                if (response.body() != null) {
                    if (200 == response.body().getCode()) {
                        petDetailsResponseByUserIdList = response.body().getData();
                        if(petDetailsResponseByUserIdList != null && petDetailsResponseByUserIdList.size()>0){
                            setSelectYourPetType(petDetailsResponseByUserIdList);
                        }

                    }

                } else {

                }


            }



            @Override
            public void onFailure(@NonNull Call<PetDetailsResponse> call, @NonNull Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG,"PetDetailsResponse flr"+"--->" + t.getMessage());
            }
        });

    }
    private PetDetailsRequest petDetailsRequest() {
        PetDetailsRequest petDetailsRequest = new PetDetailsRequest();
        petDetailsRequest.setUser_id(userid);
        Log.w(TAG,"petDetailsRequest"+ "--->" + new Gson().toJson(petDetailsRequest));
        return petDetailsRequest;
    }

    private void setSelectYourPetType(List<PetDetailsResponse.DataBean> petDetailsResponseByUserIdList) {
        ArrayList<String> pettypeArrayList = new ArrayList<>();
        pettypeArrayList.add("Select Your Pet");
        for (int i = 0; i < petDetailsResponseByUserIdList.size(); i++) {

            String petType = petDetailsResponseByUserIdList.get(i).getPet_type();

            Log.w(TAG,"petType-->"+petType);
            pettypeArrayList.add(petType);

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(BookAppointmentActivity.this, R.layout.spinner_item, pettypeArrayList);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            spr_selectyourpettype.setAdapter(spinnerArrayAdapter);


        }
    }

    public boolean validdSelectPetType() {
        if(strPetType.equalsIgnoreCase("Select Pet Type")){
            final AlertDialog alertDialog = new AlertDialog.Builder(BookAppointmentActivity.this).create();
            alertDialog.setMessage(getString(R.string.err_msg_type_of_pettype));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                    (dialog, which) -> alertDialog.cancel());
            alertDialog.show();

            return false;
        }

        return true;
    }
    public boolean validdSelectPetBreedType() {
        if(strPetBreedType.equalsIgnoreCase("Select Pet Breed")){
            final AlertDialog alertDialog = new AlertDialog.Builder(BookAppointmentActivity.this).create();
            alertDialog.setMessage(getString(R.string.err_msg_type_of_petbreedtype));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                    (dialog, which) -> alertDialog.cancel());
            alertDialog.show();

            return false;
        }

        return true;
    }

}