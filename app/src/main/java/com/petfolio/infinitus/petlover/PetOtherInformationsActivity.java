package com.petfolio.infinitus.petlover;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.activity.LoginActivity;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.requestpojo.PetUpdateOtherInformationRequest;
import com.petfolio.infinitus.responsepojo.PetAddImageResponse;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetOtherInformationsActivity extends AppCompatActivity {
    private final String TAG = "PetOtherInformationsActivity";


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_back)
    ImageView img_back;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_skip)
    TextView txt_skip;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_continue)
    TextView btn_continue;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rg_spayed)
    RadioGroup rg_spayed;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rg_purebreed)
    RadioGroup rg_purebreed;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rg_friendlywithdogs)
    RadioGroup rg_friendlywithdogs;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rg_friendlywithcats)
    RadioGroup rg_friendlywithcats;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rg_friendlywithkids)
    RadioGroup rg_friendlywithkids;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rg_microchipped)
    RadioGroup rg_microchipped;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rg_tickfree)
    RadioGroup rg_tickfree;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rg_allowscleanprivate)
    RadioGroup rg_allowscleanprivate;



    private String petid;
    private String fromactivity;

    private boolean pet_spayed = false;
    private boolean pet_purebred = false;
    private boolean pet_frnd_with_dog = false;
    private boolean pet_frnd_with_cat = false;
    private boolean pet_frnd_with_kit = false;
    private boolean pet_microchipped = false;
    private boolean pet_tick_free = false;
    private boolean pet_private_part = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_other_informations);
        ButterKnife.bind(this);

        avi_indicator.setVisibility(View.GONE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            petid = extras.getString("petid");
            fromactivity = extras.getString("fromactivity");
        }

        txt_skip.setOnClickListener(v -> {
            if(fromactivity != null && fromactivity.equalsIgnoreCase("AddYourPetOldUserActivity")) {
                Intent intent = new Intent(getApplicationContext(), AddYourPetImageOlduserActivity.class);
                intent.putExtra("petid", petid);
                intent.putExtra("fromactivity",TAG);
                startActivity(intent);
            }else{
                Intent intent = new Intent(getApplicationContext(), RegisterYourPetActivity.class);
                intent.putExtra("petid", petid);
                intent.putExtra("fromactivity",TAG);
                startActivity(intent);
            }
        });

        btn_continue.setOnClickListener(v -> {
            if (new ConnectionDetector(PetOtherInformationsActivity.this).isNetworkAvailable(PetOtherInformationsActivity.this)) {
                petUpdateOtherInformationResponseCall();
            }
        });

        img_back.setOnClickListener(v -> onBackPressed());

        rg_spayed.setOnCheckedChangeListener((group, checkedId) -> {
            int radioButtonID = group.getCheckedRadioButtonId();
            View radioButton = group.findViewById(radioButtonID);
            int position = group.indexOfChild(radioButton);
            pet_spayed = position == 0;


        });
        rg_purebreed.setOnCheckedChangeListener((group, checkedId) -> {
            int radioButtonID = group.getCheckedRadioButtonId();
            View radioButton = group.findViewById(radioButtonID);
            int position = group.indexOfChild(radioButton);
            pet_purebred = position == 0;



        });
        rg_friendlywithdogs.setOnCheckedChangeListener((group, checkedId) -> {
            int radioButtonID = group.getCheckedRadioButtonId();
            View radioButton = group.findViewById(radioButtonID);
            int position = group.indexOfChild(radioButton);
            pet_frnd_with_dog = position == 0;

        });
        rg_friendlywithcats.setOnCheckedChangeListener((group, checkedId) -> {
            int radioButtonID = group.getCheckedRadioButtonId();
            View radioButton = group.findViewById(radioButtonID);
            int position = group.indexOfChild(radioButton);
            pet_frnd_with_cat = position == 0;

        });
        rg_friendlywithkids.setOnCheckedChangeListener((group, checkedId) -> {
            int radioButtonID = group.getCheckedRadioButtonId();
            View radioButton = group.findViewById(radioButtonID);
            int position = group.indexOfChild(radioButton);
            pet_frnd_with_kit = position == 0;

        });
        rg_microchipped.setOnCheckedChangeListener((group, checkedId) -> {
            int radioButtonID = group.getCheckedRadioButtonId();
            View radioButton = group.findViewById(radioButtonID);
            int position = group.indexOfChild(radioButton);
            pet_microchipped = position == 0;

        });
        rg_tickfree.setOnCheckedChangeListener((group, checkedId) -> {
            int radioButtonID = group.getCheckedRadioButtonId();
            View radioButton = group.findViewById(radioButtonID);
            int position = group.indexOfChild(radioButton);
            pet_tick_free = position == 0;

        });
        rg_allowscleanprivate.setOnCheckedChangeListener((group, checkedId) -> {
            int radioButtonID = group.getCheckedRadioButtonId();
            View radioButton = group.findViewById(radioButtonID);
            int position = group.indexOfChild(radioButton);
            pet_private_part= position == 0;

        });



    }


    @SuppressLint({"LogNotTimber", "LongLogTag"})
    private void petUpdateOtherInformationResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<PetAddImageResponse> call = apiInterface.petUpdateOtherInformationResponseCall(RestUtils.getContentType(), petUpdateOtherInformationRequest());
        Log.w(TAG,"petUpdateOtherInformationResponseCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<PetAddImageResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<PetAddImageResponse> call, @NonNull Response<PetAddImageResponse> response) {

                Log.w(TAG,"petUpdateOtherInformationResponseCall"+ "--->" + new Gson().toJson(response.body()));

                avi_indicator.smoothToHide();

                if (response.body() != null) {
                    if(response.body().getData().get_id() != null) {
                        if(fromactivity != null && fromactivity.equalsIgnoreCase("AddYourPetOldUserActivity")) {
                            Intent intent = new Intent(getApplicationContext(), AddYourPetImageOlduserActivity.class);
                            intent.putExtra("petid", response.body().getData().get_id());
                            intent.putExtra("fromactivity",TAG);
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(getApplicationContext(), RegisterYourPetActivity.class);
                            intent.putExtra("petid", response.body().getData().get_id());
                            intent.putExtra("fromactivity",TAG);
                            startActivity(intent);
                        }



                    }


                }


            }

            @SuppressLint("LogNotTimber")
            @Override
            public void onFailure(@NonNull Call<PetAddImageResponse> call, @NonNull Throwable t) {

                avi_indicator.smoothToHide();
                Log.w(TAG,"petUpdateOtherInformationResponseCall flr"+"--->" + t.getMessage());
            }
        });

    }
    @SuppressLint({"LogNotTimber", "LongLogTag"})
    private PetUpdateOtherInformationRequest petUpdateOtherInformationRequest() {
        /*
         * _id : 603e098e2c2b43125f8cb7f8
         * pet_spayed : false
         * pet_purebred : true
         * pet_frnd_with_dog : false
         * pet_frnd_with_cat : false
         * pet_frnd_with_kit : false
         * pet_microchipped : false
         * pet_tick_free : false
         * pet_private_part : false
         */


        PetUpdateOtherInformationRequest petUpdateOtherInformationRequest = new PetUpdateOtherInformationRequest();
        petUpdateOtherInformationRequest.set_id(petid);
        petUpdateOtherInformationRequest.setPet_spayed(pet_spayed);
        petUpdateOtherInformationRequest.setPet_purebred(pet_purebred);
        petUpdateOtherInformationRequest.setPet_frnd_with_dog(pet_frnd_with_dog);
        petUpdateOtherInformationRequest.setPet_frnd_with_cat(pet_frnd_with_cat);
        petUpdateOtherInformationRequest.setPet_frnd_with_kit(pet_frnd_with_kit);
        petUpdateOtherInformationRequest.setPet_microchipped(pet_microchipped);
        petUpdateOtherInformationRequest.setPet_tick_free(pet_tick_free);
        petUpdateOtherInformationRequest.setPet_private_part(pet_private_part);
        Log.w(TAG,"petUpdateOtherInformationRequest"+ "--->" + new Gson().toJson(petUpdateOtherInformationRequest));
        return petUpdateOtherInformationRequest;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(fromactivity != null && fromactivity.equalsIgnoreCase("AddYourPetOldUserActivity")) {
            startActivity(new Intent(getApplicationContext(), PetLoverProfileScreenActivity.class));
            finish();
        }else{
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }

    }
}