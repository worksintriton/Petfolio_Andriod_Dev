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

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.img_back)
    ImageView img_back;

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.txt_usertypes)
    TextView txt_usertypes;

    @BindView(R.id.btn_changeusertype)
    Button btn_changeusertype;

    @BindView(R.id.edt_firstname)
    EditText edt_firstname;

    @BindView(R.id.edt_lastname)
    EditText edt_lastname;

    @BindView(R.id.edt_email)
    EditText edt_email;

    @BindView(R.id.edt_phone)
    EditText edt_phone;

    @BindView(R.id.btn_continue)
    Button btn_continue;

    private String TAG = "SignUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        avi_indicator.setVisibility(View.GONE);


        img_back.setOnClickListener(this);
        btn_changeusertype.setOnClickListener(this);
        btn_continue.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_continue:
                startActivity(new Intent(SignUpActivity.this,VerifyOtpActivity.class));
                break;
            case R.id.img_back:
                 onBackPressed();
                break;

                case R.id.btn_changeusertype:

                break;
        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}