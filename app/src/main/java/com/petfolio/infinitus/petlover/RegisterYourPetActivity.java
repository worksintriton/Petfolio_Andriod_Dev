package com.petfolio.infinitus.petlover;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.petfolio.infinitus.R;
import com.petfolio.infinitus.activity.LoginActivity;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterYourPetActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "RegisterYourPetActivity";
    @BindView(R.id.img_back)
    ImageView img_back;
    @BindView(R.id.txt_skip)
    TextView txt_skip;
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;
    @BindView(R.id.btn_choose)
    Button btn_choose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_your_pet);
        Log.w(TAG,"onCreate ");
        ButterKnife.bind(this);
        avi_indicator.setVisibility(View.GONE);
        img_back.setOnClickListener(this);
        txt_skip.setOnClickListener(this);
        btn_choose.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                onBackPressed();
                break;
                case R.id.txt_skip:
                    gotoPetloverDashboard();
                break;
                case R.id.btn_choose:
                break;
        }
    }

    private void gotoPetloverDashboard() {
        Intent intent = new Intent(RegisterYourPetActivity.this,PetLoverDashboardActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(RegisterYourPetActivity.this, LoginActivity.class));
        finish();
    }
}