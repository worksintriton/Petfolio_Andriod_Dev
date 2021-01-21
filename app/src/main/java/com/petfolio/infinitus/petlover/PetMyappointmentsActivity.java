package com.petfolio.infinitus.petlover;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.petfolio.infinitus.R;

import com.petfolio.infinitus.fragmentpetlover.bottommenu.PetCareFragment;
import com.petfolio.infinitus.fragmentpetlover.bottommenu.PetHomeFragment;
import com.petfolio.infinitus.fragmentpetlover.bottommenu.PetServicesFragment;
import com.petfolio.infinitus.fragmentpetlover.myappointments.FragmentPetMissedAppointment;
import com.petfolio.infinitus.fragmentpetlover.myappointments.FragmentPetCompletedAppointment;
import com.petfolio.infinitus.fragmentpetlover.myappointments.FragmentPetNewAppointment;
import com.wang.avi.AVLoadingIndicatorView;


import butterknife.BindView;
import butterknife.ButterKnife;


import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class PetMyappointmentsActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private String TAG = "PetMyappointmentsActivity";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottom_navigation_view;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tablayout)
    TabLayout tabLayout;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_back)
    ImageView img_back;




    private String active_tag = "1";


    String tag;

    String fromactivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_myappointments);
        ButterKnife.bind(this);
        Log.w(TAG,"onCreate");

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        bottom_navigation_view.setOnNavigationItemSelectedListener(this);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentPetNewAppointment(), "New");
        adapter.addFragment(new FragmentPetCompletedAppointment(), "Completed");
         adapter.addFragment(new FragmentPetMissedAppointment(), "Missed");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(PetMyappointmentsActivity.this, PetLoverDashboardActivity.class);
        startActivity(i);
        finish();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                callDirections("1");
                break;
            case R.id.shop:
                callDirections("2");
                break;
            case R.id.services:
                callDirections("3");
                break;
            case R.id.care:
                callDirections("4");
                break;
            case R.id.community:
                callDirections("5");
                break;

            default:
                return  false;
        }
        return true;
    }
    public void callDirections(String tag){
        Intent intent = new Intent(getApplicationContext(), PetLoverDashboardActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);
        finish();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }




}