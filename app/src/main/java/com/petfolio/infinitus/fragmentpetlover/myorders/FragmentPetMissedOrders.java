package com.petfolio.infinitus.fragmentpetlover.myorders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.RecyclerView;

import com.petfolio.infinitus.R;

import com.petfolio.infinitus.responsepojo.PetNewAppointmentResponse;
import com.petfolio.infinitus.sessionmanager.SessionManager;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



public class FragmentPetMissedOrders extends Fragment implements View.OnClickListener {
    private String TAG = "FragmentPetMissedOrders";

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.txt_no_records)
    TextView txt_no_records;

    @BindView(R.id.rv_missedappointment)
    RecyclerView rv_missedappointment;


    @BindView(R.id.btn_load_more)
    Button btn_load_more;

    @BindView(R.id.btn_filter)
    Button btn_filter;





    SessionManager session;
    String type = "",name = "",doctorid = "";
    private SharedPreferences preferences;
    private Context mContext;
    private List<PetNewAppointmentResponse.DataBean> missedAppointmentResponseList;
    private String userid;


    public FragmentPetMissedOrders() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.w(TAG,"onCreateView");

        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        View view = inflater.inflate(R.layout.fragment_pet_missed, container, false);

        ButterKnife.bind(this, view);
        mContext = getActivity();

        avi_indicator.setVisibility(View.GONE);
        btn_load_more.setVisibility(View.GONE);
        btn_filter.setVisibility(View.GONE);

        btn_load_more.setOnClickListener(this);


        session = new SessionManager(getContext());
        HashMap<String, String> user = session.getProfileDetails();
        userid = user.get(SessionManager.KEY_ID);
        Log.w(TAG," userid : "+userid);

      

        if (new ConnectionDetector(getActivity()).isNetworkAvailable(getActivity())) {

        }
        return view;
    }





    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_load_more:

                break;
        }
    }
}