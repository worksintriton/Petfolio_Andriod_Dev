package com.petfolio.infinitus.petlover;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.petfolio.infinitus.R;


import com.petfolio.infinitus.activity.NotificationActivity;
import com.petfolio.infinitus.adapter.PetLoverSOSAdapter;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.fragmentpetlover.myappointments.FragmentPetMissedAppointment;
import com.petfolio.infinitus.fragmentpetlover.myappointments.FragmentPetCompletedAppointment;
import com.petfolio.infinitus.fragmentpetlover.myappointments.FragmentPetNewAppointment;
import com.petfolio.infinitus.interfaces.SoSCallListener;
import com.petfolio.infinitus.responsepojo.PetLoverDashboardResponse;


import butterknife.BindView;
import butterknife.ButterKnife;


import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class PetMyappointmentsActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener, SoSCallListener {

    private String TAG = "PetMyappointmentsActivity";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.include_petlover_footer)
    View include_petlover_footer;

    BottomNavigationView bottom_navigation_view;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tablayout)
    TabLayout tabLayout;




    private String active_tag = "1";


    String tag;

    String fromactivity;
    private Dialog dialog;

    private static final int REQUEST_PHONE_CALL =1 ;
    private String sosPhonenumber;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.include_petlover_header)
    View include_petlover_header;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_myappointments);
        ButterKnife.bind(this);
        Log.w(TAG,"onCreate");

        ImageView img_back = include_petlover_header.findViewById(R.id.img_back);
        ImageView img_sos = include_petlover_header.findViewById(R.id.img_sos);
        ImageView img_notification = include_petlover_header.findViewById(R.id.img_notification);
        ImageView img_cart = include_petlover_header.findViewById(R.id.img_cart);
        ImageView img_profile = include_petlover_header.findViewById(R.id.img_profile);
        TextView toolbar_title = include_petlover_header.findViewById(R.id.toolbar_title);
        toolbar_title.setText(getResources().getString(R.string.my_appointments));

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        img_back.setOnClickListener(v -> onBackPressed());

        img_sos.setVisibility(View.GONE);
        img_cart.setVisibility(View.GONE);
        img_sos.setOnClickListener(this);
        img_notification.setOnClickListener(this);
        img_cart.setOnClickListener(this);
        img_profile.setOnClickListener(this);

        bottom_navigation_view = include_petlover_footer.findViewById(R.id.bottom_navigation_view);
        bottom_navigation_view.setItemIconTintList(null);
        bottom_navigation_view.setOnNavigationItemSelectedListener(this);
        bottom_navigation_view.getMenu().findItem(R.id.home).setChecked(true);

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

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_sos:
                showSOSAlert(APIClient.sosList);
                break;
                case R.id.img_notification:
                    startActivity(new Intent(getApplicationContext(), NotificationActivity.class));
                    break;
                case R.id.img_cart:
                break;
                case R.id.img_profile:
                    Intent intent = new Intent(getApplicationContext(),PetLoverProfileScreenActivity.class);
                    intent.putExtra("fromactivity",TAG);
                    startActivity(intent);
                break;

        }

    }

    @Override
    public void soSCallListener(long phonenumber) {
        if(phonenumber != 0){
            sosPhonenumber = String.valueOf(phonenumber);
        }

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
    private void showSOSAlert(List<PetLoverDashboardResponse.DataBean.SOSBean> sosList) {

        try {

            dialog = new Dialog(PetMyappointmentsActivity.this);
            dialog.setContentView(R.layout.sos_popup_layout);
            RecyclerView rv_sosnumbers = (RecyclerView)dialog.findViewById(R.id.rv_sosnumbers);
            Button btn_call = (Button)dialog.findViewById(R.id.btn_call);
            TextView txt_no_records = (TextView)dialog.findViewById(R.id.txt_no_records);
            ImageView img_close = (ImageView)dialog.findViewById(R.id.img_close);
            img_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            if(sosList != null && sosList.size()>0){
                rv_sosnumbers.setVisibility(View.VISIBLE);
                btn_call.setVisibility(View.VISIBLE);
                txt_no_records.setVisibility(View.GONE);
                rv_sosnumbers.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                rv_sosnumbers.setItemAnimator(new DefaultItemAnimator());
                PetLoverSOSAdapter petLoverSOSAdapter = new PetLoverSOSAdapter(getApplicationContext(), sosList,this);
                rv_sosnumbers.setAdapter(petLoverSOSAdapter);
            }else{
                rv_sosnumbers.setVisibility(View.GONE);
                btn_call.setVisibility(View.GONE);
                txt_no_records.setVisibility(View.VISIBLE);
                txt_no_records.setText("No phone numbers");

            }

            btn_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(PetMyappointmentsActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
                    }
                    else
                    {
                        gotoPhone();
                    }

                }
            });



            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();


        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }




    }
    private void gotoPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + sosPhonenumber));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(intent);
    }




}