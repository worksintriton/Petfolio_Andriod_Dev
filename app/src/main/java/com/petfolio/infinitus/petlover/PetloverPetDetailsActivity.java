package com.petfolio.infinitus.petlover;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.petfolio.infinitus.R;
import com.petfolio.infinitus.responsepojo.BreedTypeResponse;
import com.petfolio.infinitus.responsepojo.PetListResponse;
import com.petfolio.infinitus.responsepojo.PetTypeListResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PetloverPetDetailsActivity extends AppCompatActivity {

    private String TAG = "PetloverPetDetailsActivity";

    private boolean vaccinatedstatus,defaultstatus;
    private String petid,userid,petimage,petname,pettype,petbreed,petgender,petcolor;
    private double petweight;

    private String petAgeandMonth = "";

    private String strPetType;
    private String strPetBreedType;
    private String strPetGenderType;

    private int year, month, day;
    String SelectedLastVaccinateddate = "";
    private static final int DATE_PICKER_ID = 0 ;
    private static final int PET_DATE_PICKER_ID = 1 ;

    private Dialog alertDialog;

    private List<PetTypeListResponse.DataBean.UsertypedataBean> usertypedataBeanList;



    HashMap<String, String> hashMap_PetTypeid = new HashMap<>();
    private String petTypeId;
    private List<BreedTypeResponse.DataBean> breedTypedataBeanList;
    private String petdob;
    String SelectedPetDOB = "";


    private boolean pet_spayed;
    private boolean pet_purebred;
    private boolean pet_frnd_with_dog;
    private boolean pet_frnd_with_cat;
    private boolean pet_frnd_with_kit;
    private boolean pet_microchipped;
    private boolean pet_tick_free;
    private boolean pet_private_part;
    List<PetListResponse.DataBean.PetImgBean> petImgBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petlover_pet_details);

        Intent intent = getIntent();

        Bundle args = intent.getBundleExtra("petimage");

        if (args != null && !args.isEmpty()) {

            petImgBeanList = (ArrayList<PetListResponse.DataBean.PetImgBean>) args.getSerializable("PETLIST");
        }

        Log.w(TAG, petImgBeanList.toString());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            petid = extras.getString("id");
            userid = extras.getString("userid");
            // petimage = extras.getString("petimage");
            petname = extras.getString("petname");
            strPetType = extras.getString("pettype");
            strPetBreedType = extras.getString("petbreed");
            strPetGenderType = extras.getString("petgender");
            petcolor = extras.getString("petcolor");
            petweight = extras.getDouble("petweight");
            vaccinatedstatus = extras.getBoolean("vaccinatedstatus");
            SelectedLastVaccinateddate = extras.getString("vaccinateddate");
            defaultstatus = extras.getBoolean("defaultstatus");
            petdob = extras.getString("petdob");

            pet_spayed = extras.getBoolean("pet_spayed");
            pet_purebred = extras.getBoolean("pet_purebred");
            pet_frnd_with_dog = extras.getBoolean("pet_frnd_with_dog");
            pet_frnd_with_cat = extras.getBoolean("pet_frnd_with_cat");
            pet_frnd_with_kit = extras.getBoolean("pet_frnd_with_kit");
            pet_microchipped = extras.getBoolean("pet_microchipped");
            pet_tick_free = extras.getBoolean("pet_tick_free");
            pet_private_part = extras.getBoolean("pet_private_part");

            Log.w(TAG, "strPetType : " + strPetType + " strPetBreedType : " + strPetBreedType + " strPetGenderType : " + strPetGenderType);

        }
    }

}