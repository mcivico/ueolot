package ueolot.com.ueolot.Fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ueolot.com.ueolot.Adapters.PagerAdapter_Femeni;
import ueolot.com.ueolot.R;

public class Fragment_Femeni extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        final View rootview = inflater.inflate(R.layout.fragment_femeni,container,false);

        TabLayout tabLayout = (TabLayout) rootview.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Cròniques"));
        tabLayout.addTab(tabLayout.newTab().setText("Calendari"));
        tabLayout.addTab(tabLayout.newTab().setText("Plantilla"));
        tabLayout.addTab(tabLayout.newTab().setText("Classificació"));
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) rootview.findViewById(R.id.pager);
        final PagerAdapter_Femeni adapter = new PagerAdapter_Femeni
                (getFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return rootview;
    }


    public void onBackPressed() {

    }


}
