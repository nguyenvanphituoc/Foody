package com.example.nguyenvanphituoc.foody.Adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;

import com.example.nguyenvanphituoc.foody.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 3/12/2017.
 */

public class FragmentAdapter extends FragmentStatePagerAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    public FragmentAdapter(FragmentManager fragmentManager, Context mContext) {

        super(fragmentManager);
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public View getTabMainView(int position) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        if (position % 2 == 0) {
            return mInflater.inflate(R.layout.toolbar_main_tab_tabplaces, null);
        }
        else
            return mInflater.inflate(R.layout.toolbar_main_tab_tabfood, null);
    }

    @Override
    public int getCount() {
        return mFragmentTitleList.size();
    }
}