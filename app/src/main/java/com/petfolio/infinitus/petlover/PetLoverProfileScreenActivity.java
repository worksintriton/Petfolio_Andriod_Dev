package com.petfolio.infinitus.petlover;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.petfolio.infinitus.R;
import com.petfolio.infinitus.activity.LoginActivity;
import com.petfolio.infinitus.sessionmanager.SessionManager;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PetLoverProfileScreenActivity extends AppCompatActivity implements View.OnClickListener {
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

    @BindView(R.id.txt_logout)
    TextView txt_logout;
    private SessionManager session;
    String name,emailid,phoneNo,userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_lover_profile_screen);
        ButterKnife.bind(this);


        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getProfileDetails();
        name = user.get(SessionManager.KEY_FIRST_NAME);
        emailid = user.get(SessionManager.KEY_EMAIL_ID);
        phoneNo = user.get(SessionManager.KEY_MOBILE);
        userid = user.get(SessionManager.KEY_ID);

        txt_usrname.setText(name);
        txt_mail.setText(emailid);
        txt_phn_num.setText(phoneNo);

        img_back.setOnClickListener(this);
        txt_manage_address.setOnClickListener(this);
        txt_change_password.setOnClickListener(this);
        txt_logout.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
        }
    }

    private void gotoManageAddress() {
        startActivity(new Intent(PetLoverProfileScreenActivity.this,ManageAddressActivity.class));
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


}