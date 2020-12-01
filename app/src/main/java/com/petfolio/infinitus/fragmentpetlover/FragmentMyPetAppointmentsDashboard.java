package com.petfolio.infinitus.fragmentpetlover;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.fragmentdoctor.FragmentCompletedAppointment;
import com.petfolio.infinitus.fragmentdoctor.FragmentMissedAppointment;
import com.petfolio.infinitus.fragmentdoctor.FragmentNewAppointment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FragmentMyPetAppointmentsDashboard extends Fragment  {



    @BindView(R.id.tablayout)
    TabLayout tablayout;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private SharedPreferences preferences;
    private Context mContext;
    public FragmentMyPetAppointmentsDashboard() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pet_myppointments_dashboard, container, false);


        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        ButterKnife.bind(this, view);
        mContext = getActivity();


        setupViewPager(viewPager);
        tablayout.setupWithViewPager(viewPager);





        return view;


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(),3);
        adapter.addFragment(new FragmentPetCurrentAppointment(), "Current");
        adapter.addFragment(new FragmentPetCompletedAppointment(), "Completed");
        adapter.addFragment(new FragmentPetCancelledAppointment(), "Canceleed");
        viewPager.setAdapter(adapter);
    }




    static class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager,int number) {
            super(manager,number);
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