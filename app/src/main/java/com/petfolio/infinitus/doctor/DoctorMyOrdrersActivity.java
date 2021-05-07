package com.petfolio.infinitus.doctor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.fragmentdoctor.myorders.FragmentDoctorCancelledOrders;
import com.petfolio.infinitus.fragmentdoctor.myorders.FragmentDoctorCompletedOrders;
import com.petfolio.infinitus.fragmentdoctor.myorders.FragmentDoctorNewOrders;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DoctorMyOrdrersActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private final String TAG = "DoctorMyOrdrersActivity";




    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tablayout)
    TabLayout tablayout;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.viewPager)
    ViewPager viewPager;



    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.include_doctor_footer)
    View include_doctor_footer;

    BottomNavigationView bottom_navigation_view;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.include_doctor_header)
    View include_doctor_header;


    @SuppressLint({"LogNotTimber", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_myorders);
        Log.w(TAG,"onCreate");
        ButterKnife.bind(this);


        ImageView img_back = include_doctor_header.findViewById(R.id.img_back);
        ImageView img_notification = include_doctor_header.findViewById(R.id.img_notification);
        ImageView img_cart = include_doctor_header.findViewById(R.id.img_cart);
        ImageView img_profile = include_doctor_header.findViewById(R.id.img_profile);
        TextView toolbar_title = include_doctor_header.findViewById(R.id.toolbar_title);
        toolbar_title.setText(getResources().getString(R.string.my_orders));

        setupViewPager(viewPager);
        tablayout.setupWithViewPager(viewPager);
        img_back.setOnClickListener(v -> onBackPressed());

        bottom_navigation_view = include_doctor_footer.findViewById(R.id.bottom_navigation_view);
        bottom_navigation_view.setItemIconTintList(null);
        bottom_navigation_view.setOnNavigationItemSelectedListener(this);
        bottom_navigation_view.getMenu().findItem(R.id.home).setChecked(true);



    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentDoctorNewOrders(), "New");
        adapter.addFragment(new FragmentDoctorCompletedOrders(), "Completed");
         adapter.addFragment(new FragmentDoctorCancelledOrders(), "Cancelled");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(DoctorMyOrdrersActivity.this, DoctorDashboardActivity.class);
        startActivity(i);
        finish();
    }


    public void callDirections(String tag){
        Intent intent = new Intent(getApplicationContext(), DoctorDashboardActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);
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

            case R.id.community:
                callDirections("3");
                break;

            default:
                return  false;
        }
        return true;
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