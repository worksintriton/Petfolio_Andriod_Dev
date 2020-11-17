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
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.img_loginheader)
    ImageView img_loginheader;

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.edt_emailorphone)
    EditText edt_emailorphone;

    @BindView(R.id.txt_signup)
    TextView txt_signup;

    @BindView(R.id.btn_verifyotp)
    Button btn_verifyotp;

    private String TAG = "LoginActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        avi_indicator.setVisibility(View.GONE);

        txt_signup.setOnClickListener(this);
        btn_verifyotp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_signup:
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
                break;
            case R.id.btn_verifyotp:

                break;
        }

    }
}