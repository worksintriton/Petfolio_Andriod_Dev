package com.petfolio.infinitus.fragmentvendor;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.petfolio.infinitus.R;

import java.io.Serializable;


public class VendorCommunityFragment extends Fragment implements Serializable, View.OnClickListener {


    private String TAG = "VendorCommunityFragment";

    View view;

    public VendorCommunityFragment() {
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

        view = inflater.inflate(R.layout.fragment_sp_community, container, false);
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
