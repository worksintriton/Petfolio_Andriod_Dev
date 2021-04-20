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
import com.petfolio.infinitus.fragmentpetlover.myordersnew.FragmentPetLoverCancelledOrders;
import com.petfolio.infinitus.fragmentpetlover.myordersnew.FragmentPetLoverCompletedOrders;
import com.petfolio.infinitus.fragmentpetlover.myordersnew.FragmentPetLoverNewOrders;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PetMyOrdrersNewActivity extends AppCompatActivity  {
    private  String TAG = "PetMyOrdrersNewActivity";


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottom_navigation_view;


    @SuppressLint({"LogNotTimber", "NonConstantResourceId"})
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
        adapter.addFragment(new FragmentPetLoverNewOrders(), "New");
        adapter.addFragment(new FragmentPetLoverCompletedOrders(), "Completed");
         adapter.addFragment(new FragmentPetLoverCancelledOrders(), "Cancelled");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(PetMyOrdrersNewActivity.this, PetLoverDashboardActivity.class);
        startActivity(i);
        finish();
    }


    public void callDirections(String tag){
        Intent intent = new Intent(getApplicationContext(), PetLoverDashboardActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);
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