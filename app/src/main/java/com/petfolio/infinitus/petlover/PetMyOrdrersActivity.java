package com.petfolio.infinitus.petlover;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.fragmentpetlover.myorders.FragmentPetCancelledOrders;
import com.petfolio.infinitus.fragmentpetlover.myorders.FragmentPetCompletedOrders;
import com.petfolio.infinitus.fragmentpetlover.myorders.FragmentPetNewOrders;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PetMyOrdrersActivity extends AppCompatActivity {
    private final String TAG = "PetMyOrdrersActivity";


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottom_navigation_view;




    private final String active_tag = "1";


    String tag;

    String fromactivity;


    @SuppressLint("LogNotTimber")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_myorders);
        Log.w(TAG,"onCreate");
        TabLayout tabLayout = findViewById(R.id.tablayout);
        ViewPager viewPager = findViewById(R.id.viewPager);
        ImageView img_back = findViewById(R.id.img_back);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        img_back.setOnClickListener(v -> onBackPressed());
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentPetNewOrders(), "New");
        adapter.addFragment(new FragmentPetCompletedOrders(), "Completed");
         adapter.addFragment(new FragmentPetCancelledOrders(), "Cancelled");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(PetMyOrdrersActivity.this, PetLoverDashboardActivity.class);
        startActivity(i);
        finish();
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public @NotNull Fragment getItem(int position) {
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