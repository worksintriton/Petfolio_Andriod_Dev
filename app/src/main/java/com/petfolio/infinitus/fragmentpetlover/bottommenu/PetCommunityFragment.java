package com.petfolio.infinitus.fragmentpetlover.bottommenu;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.adapter.PetLoverDoctorFilterAdapter;
import com.petfolio.infinitus.adapter.PetLoverNearByDoctorAdapter;
import com.petfolio.infinitus.adapter.ViewPagerPetCareAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.petlover.FiltersActivity;
import com.petfolio.infinitus.requestpojo.DoctorSearchRequest;
import com.petfolio.infinitus.requestpojo.FilterDoctorRequest;
import com.petfolio.infinitus.responsepojo.DoctorSearchResponse;
import com.petfolio.infinitus.responsepojo.FilterDoctorResponse;
import com.petfolio.infinitus.sessionmanager.SessionManager;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PetCommunityFragment extends Fragment implements Serializable, View.OnClickListener {


    private String TAG = "PetCommunityFragment";

    View view;

    public PetCommunityFragment() {
        // Required empty public constructor
    }


    @SuppressLint("LogNotTimber")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w(TAG,"onCreate-->");



    }

//    private Activity mActivity;
//
//    @Override
//    public void onAttach(@NotNull Activity activity) {
//        super.onAttach(activity);
//        mActivity=activity;
//    }
//
//    public Activity getMyActivity() {
//        return mActivity;
//    }

    @SuppressLint({"SetTextI18n", "LogNotTimber"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.w(TAG,"onCreateView-->");

        view = inflater.inflate(R.layout.fragment_pet_community, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){



        }

    }

}
