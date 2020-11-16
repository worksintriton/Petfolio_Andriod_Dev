package com.petfolio.infinitus.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.petfolio.infinitus.R;
import com.petfolio.infinitus.petlover.AddYourPetActivity;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VerifyOtpActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.img_back)
    ImageView img_back;

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.btn_verify)
    Button btn_verify;

    @BindView(R.id.edt_otp)
    EditText edt_otp;

    @BindView(R.id.txt_resend)
    TextView txt_resend;

    private String TAG = "VerifyOtpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        ButterKnife.bind(this);
        avi_indicator.setVisibility(View.GONE);


        img_back.setOnClickListener(this);
        btn_verify.setOnClickListener(this);
        txt_resend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_verify:
                startActivity(new Intent(VerifyOtpActivity.this, AddYourPetActivity.class));
                break;
            case R.id.img_back:
                onBackPressed();
                break;

                case R.id.txt_resend:
                break;


        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}