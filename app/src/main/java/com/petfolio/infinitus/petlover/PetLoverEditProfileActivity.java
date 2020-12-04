package com.petfolio.infinitus.petlover;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.petfolio.infinitus.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PetLoverEditProfileActivity extends AppCompatActivity {

    @BindView(R.id.img_back)
    ImageView  img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_lover_edit_profile);
        ButterKnife.bind(this);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}