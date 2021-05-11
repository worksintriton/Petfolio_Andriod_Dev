package com.petfolio.infinitus.fragmentpetlover.favourites;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.petfolio.infinitus.R;


public class ShopFavFragment extends Fragment {


    private String TAG = "ShopFavFragment";

    public ShopFavFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.w(TAG,"onCreateView");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shop_fav, container, false);
    }
}