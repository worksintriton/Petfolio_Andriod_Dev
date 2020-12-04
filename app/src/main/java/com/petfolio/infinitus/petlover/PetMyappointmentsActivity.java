package com.petfolio.infinitus.petlover;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.petfolio.infinitus.R;

import com.petfolio.infinitus.fragmentpetlover.FragmentPetMissedAppointment;
import com.petfolio.infinitus.fragmentpetlover.FragmentPetCompletedAppointment;
import com.petfolio.infinitus.fragmentpetlover.FragmentPetNewAppointment;
import com.wang.avi.AVLoadingIndicatorView;


import butterknife.BindView;


import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class PetMyappointmentsActivity extends AppCompatActivity {

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottom_navigation_view;

    private String TAG = "PetMyappointmentsActivity";



    private String active_tag = "1";


    String tag;

    String fromactivity;

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ImageView img_back;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_myappointments);
        Log.w(TAG,"onCreate");
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewPager);
        img_back = findViewById(R.id.img_back);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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