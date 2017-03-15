package com.example.nguyenvanphituoc.foody.Adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nguyenvanphituoc.foody.Activity.Fragment.TabPlacesBookingFragment;
import com.example.nguyenvanphituoc.foody.Activity.Fragment.TabPlacesCategoriesFragment;
import com.example.nguyenvanphituoc.foody.Activity.Fragment.TabPlacesCityFragment;
import com.example.nguyenvanphituoc.foody.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 3/12/2017.
 */

public class FragmentAdapterToolbarOnTop extends FragmentStatePagerAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    public FragmentAdapterToolbarOnTop(FragmentManager fragmentManager,  Context mContext) {

        super(fragmentManager);
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
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

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public View getTabView(int position, TabLayout.Tab tab) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        if (position % 2 == 0) {
            return mInflater.inflate(R.layout.tab_ontop_places, null);
        }
        else
            return mInflater.inflate(R.layout.tab_ontop_food, null);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}