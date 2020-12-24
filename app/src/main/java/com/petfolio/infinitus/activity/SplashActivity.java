package com.petfolio.infinitus.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.petfolio.infinitus.R;
import com.petfolio.infinitus.doctor.DoctorDashboardActivity;
import com.petfolio.infinitus.petlover.PetLoverDashboardActivity;
import com.petfolio.infinitus.serviceprovider.ServiceProviderDashboardActivity;
import com.petfolio.infinitus.sessionmanager.SessionManager;

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
        boolean islogedin = session.isLoggedIn();
        Log.w(TAG,"islogedin-->"+islogedin);


        new Handler().postDelayed(() -> {

            boolean islogedin1 = session.isLoggedIn();
            if(!islogedin1) {
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

                    }else if(usertype.equalsIgnoreCase("2")){
                        startActivity(new Intent(SplashActivity.this, ServiceProviderDashboardActivity.class));
                        finish();

                    }else if(usertype.equalsIgnoreCase("3")){
                      /*  startActivity(new Intent(SplashActivity.this, ServiceProviderDashboardActivity.class));
                        finish();
*/
                    }else if(usertype.equalsIgnoreCase("4")){
                        startActivity(new Intent(SplashActivity.this, DoctorDashboardActivity.class));
                        finish();

                    }

                }



            }



        }, SPLASH_TIME_OUT);

    }
}