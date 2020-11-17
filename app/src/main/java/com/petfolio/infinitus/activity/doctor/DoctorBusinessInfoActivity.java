package com.petfolio.infinitus.activity.doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.card.MaterialCardView;
import com.petfolio.infinitus.R;

public class DoctorBusinessInfoActivity extends AppCompatActivity {

    EditText edittext_clinic_name,edittext_education_name, edittext_education_year, edittext_company_name,edittext_from,edittext_to,edittext_add_more_specialisation,edittext_add_more_pet,edittext_clinic_address;

    Button buttonAddedu,buttonAddexpr,buttonAddmorespec,buttonsubmit;

    RecyclerView recylerView_added_education,recylerView_added_experience,recylerView_specializationlist,recylerView_added_specializationlist,recylerView_added_pettype_list,recylerView_choosen_images,recylerView_choosen_certificate_images,recylerView_choosen_govtid_images,recylerView_choosen_photo_id_images;

    MaterialCardView material_cardview_from,material_cardview_to,material_cardview_clinic_pics,material_cardview_certificate;

    String clinic_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_business_info);


        edittext_clinic_name = findViewById(R.id.edittext_clinic_name);

        edittext_education_name = findViewById(R.id.edittext_education_name);

        edittext_education_year = findViewById(R.id.edittext_education_year);

        edittext_company_name = findViewById(R.id.edittext_company_name);

        edittext_from = findViewById(R.id.edittext_from);

        edittext_to = findViewById(R.id.edittext_to);

        edittext_add_more_specialisation = findViewById(R.id.edittext_add_more_specialisation);

        edittext_add_more_pet = findViewById(R.id.edittext_add_more_pet);

        edittext_clinic_address = findViewById(R.id.edittext_clinic_address);




        buttonAddedu = findViewById(R.id.buttonAddedu);

        buttonAddexpr = findViewById(R.id.buttonAddexpr);

        buttonAddmorespec = findViewById(R.id.buttonAddmorespec);

        buttonsubmit = findViewById(R.id.buttonsubmit);



        material_cardview_from = findViewById(R.id.material_cardview_from);

        material_cardview_to = findViewById(R.id.material_cardview_to);

        material_cardview_clinic_pics = findViewById(R.id.material_cardview_clinic_pics);

        material_cardview_certificate = findViewById(R.id.material_cardview_certificate);




        recylerView_added_education = findViewById(R.id.recylerView_added_education);

        recylerView_added_experience = findViewById(R.id.recylerView_added_experience);

        recylerView_specializationlist = findViewById(R.id.recylerView_specializationlist);

        recylerView_added_specializationlist = findViewById(R.id.recylerView_added_specializationlist);

        recylerView_added_pettype_list = findViewById(R.id.recylerView_added_pettype_list);

        recylerView_choosen_images = findViewById(R.id.recylerView_choosen_clinic_images);

        recylerView_choosen_certificate_images = findViewById(R.id.recylerView_choosen_certificate_images);

        recylerView_choosen_govtid_images = findViewById(R.id.recylerView_choosen_govtid_images);

        recylerView_choosen_photo_id_images = findViewById(R.id.recylerView_choosen_photoid_images);


    }
}