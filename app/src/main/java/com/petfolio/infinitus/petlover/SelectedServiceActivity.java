package com.petfolio.infinitus.petlover;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.petfolio.infinitus.R;
import com.petfolio.infinitus.sessionmanager.SessionManager;

import java.util.HashMap;

public class SelectedServiceActivity extends AppCompatActivity {

    private String userid;
    private String serviceid;
    private String TAG = "SelectedServiceActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_service);

        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getProfileDetails();
        userid = user.get(SessionManager.KEY_ID);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            serviceid = extras.getString("serviceid");
        }

        Log.w(TAG," userid : "+userid+ " serviceid : "+serviceid);
    }
}