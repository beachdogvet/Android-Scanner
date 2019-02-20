package com.lm.scanner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentContainer;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private String tabTitles[] = new String[]{"Barcodes", "View 2", "Locations"};
    private int NUMBER_OF_VIEWS = 3;


    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        position = position +1;
        Bundle bundle;

        switch(position) {
            case 1:
                Fragment1 fragment1 = new Fragment1();
//                bundle = new Bundle();
//                bundle.putString("messageToFragment", "View One");
//                fragment1.setArguments(bundle);
                return fragment1;

            case 2:
                Fragment2 fragment2 = new Fragment2();
                bundle = new Bundle();
                bundle.putString("messageToFragment", "View 2");
                fragment2.setArguments(bundle);
                return fragment2;

            case 3:
                Fragment3 fragment3 = new Fragment3();
//                bundle = new Bundle();
//                bundle.putString("messageToFragment", "Locations");
//                fragment3.setArguments(bundle);
                return fragment3;
        }



        return null;
    }

    @Override
    public int getCount() {

        return NUMBER_OF_VIEWS;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        return tabTitles[position];
    }




}
