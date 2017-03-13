package com.example.nguyenvanphituoc.foody.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.nguyenvanphituoc.foody.Activity.Fragment.TabPlacesBookingFragment;
import com.example.nguyenvanphituoc.foody.Activity.Fragment.TabPlacesCategoriesFragment;
import com.example.nguyenvanphituoc.foody.Activity.Fragment.TabPlacesCityFragment;

/**
 * Created by Admin on 3/12/2017.
 */

public class FragmentOntopAdapterClass extends FragmentStatePagerAdapter {

    private int TabCount;

    public FragmentOntopAdapterClass(FragmentManager fragmentManager, int CountTabs) {

        super(fragmentManager);

        this.TabCount = CountTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new TabPlacesBookingFragment();

            case 1:
                return new TabPlacesCategoriesFragment();

            case 2:
                return new TabPlacesCityFragment();
            default:
                return new TabPlacesBookingFragment();
        }
    }

    @Override
    public int getCount() {
        return TabCount;
    }
}