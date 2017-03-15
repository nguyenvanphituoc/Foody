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

/**
 * Created by Admin on 3/12/2017.
 */

public class FragmentAdapterToolbarOnTop extends FragmentStatePagerAdapter {

    private int TabCount;
    private Context myContext;

    public FragmentAdapterToolbarOnTop(FragmentManager fragmentManager, int CountTabs, Context mContext) {

        super(fragmentManager);

        this.TabCount = CountTabs;
        this.myContext = mContext;
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    public View getTabView(int position, TabLayout.Tab tab) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        TextView tv = new TextView(myContext);
        tv.setText(tab.getText());
        if (position % 2 == 0)
            tv.setBackgroundResource(R.drawable.buttonleft_menu_style);
        else
            tv.setBackgroundResource(R.drawable.buttonright_menu_style);
        tv.setTextColor(myContext.getResources().getColorStateList(+R.drawable.button_menu_colorstyle, null));
        return tv;
    }

    @Override
    public int getCount() {
        return TabCount;
    }
}