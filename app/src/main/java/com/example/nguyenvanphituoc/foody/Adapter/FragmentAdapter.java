package com.example.nguyenvanphituoc.foody.Adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    private  FragmentManager mManager;

    public FragmentAdapter(FragmentManager fragmentManager, Context mContext) {

        super(fragmentManager);
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        this.mManager = fragmentManager;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public View getTabMainView(int position, TabLayout.Tab mTab) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        TextView textView ;
        if (position % 2 == 0) {
            textView = (TextView) mInflater.inflate(R.layout.tab_main_tabright, null);
        } else
            textView = (TextView) mInflater.inflate(R.layout.tab_main_tableft, null);
        textView.setText(mTab.getText());
        return textView;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public View getTabMainView(TabLayout.Tab mTab) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        TextView textView = new TextView(mContext);
        textView.setTextColor(mContext.getResources().getColor(R.color.clGrey, null));
        textView.setPadding(5, 3, 3, 5);
        textView.setHeight(72);
        textView.setGravity(0x00000001 | 0x00000011);
        textView.setTextSize(16);
        textView.setText(mTab.getText());
        return textView;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public View getTabOntopView(TabLayout.Tab mTab) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        TextView textView = new TextView(mContext);
        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null,
                mContext.getDrawable(R.drawable.tab_ontop_text_icon));
        textView.setCompoundDrawablePadding(5);
        textView.setPadding(5, 0, 0, 5);
        textView.setMinHeight(32);
        textView.setGravity(0x00000001 | 0x00000011);
        textView.setTextSize(14);
        textView.setText(mTab.getText());
        return textView;
    }

    @Override
    public int getCount() {
        return mFragmentTitleList.size();
    }
}