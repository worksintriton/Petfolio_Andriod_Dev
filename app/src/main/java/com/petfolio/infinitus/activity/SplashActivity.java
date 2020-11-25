package com.petfolio.infinitus.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.petfolio.infinitus.R;
import com.petfolio.infinitus.doctor.DoctorBusinessInfoActivity;
import com.petfolio.infinitus.petlover.PetLoverDashboardActivity;
import com.petfolio.infinitus.sessionmanager.SessionManager;
import com.petfolio.infinitus.utils.ConnectionDetector;

import java.util.HashMap;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";
    private static final long SPLASH_TIME_OUT = 3000;
    private SessionManager session;
    private String usertype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        boolean islogedin = session.isLoggedIn();
        Log.w(TAG,"islogedin-->"+islogedin);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                boolean islogedin = session.isLoggedIn();
                if(!islogedin) {
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
                else{

                    session = new SessionManager(getApplicationContext());
                    HashMap<String, String> user = session.getProfileDetails();
                    usertype = user.get(SessionManager.KEY_TYPE);
                    Log.w(TAG,"usertype-->"+usertype);

                    if(usertype != null){
                        if(usertype.equalsIgnoreCase("1")){
                            startActivity(new Intent(SplashActivity.this, PetLoverDashboardActivity.class));
                            finish();

                        }else if(usertype.equalsIgnoreCase("4")){
                            startActivity(new Intent(SplashActivity.this, DoctorBusinessInfoActivity.class));
                            finish();

                        }

                    }
                      


                }



            }
        }, SPLASH_TIME_OUT);

    }
}