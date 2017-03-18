package com.example.nguyenvanphituoc.foody.Activity.Fragment;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nguyenvanphituoc.foody.R;

/**
 * Created by Admin on 3/16/2017.
 */

public class FoodyHomePageFragment extends Fragment {
    private int arrayID;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView =  inflater.inflate(R.layout.homepage, container, false);
        initialOntopToolbar(mView);
        return mView;

    }
    public void setArrayId(@ArrayRes int arrayID){
        this.arrayID = arrayID;
    }
    // create toolbar ontop
    private void initialOntopToolbar(View v) {
        TabLayout ontopToolbar = (TabLayout) v.findViewById(R.id.toolbar_ontop);
        TypedArray typedArray = v.getResources().obtainTypedArray(arrayID);
        try {
            for (int i = 0, n = typedArray.length(); i < n; i++) {
                TabLayout.Tab mTab = ontopToolbar.newTab();
                ontopToolbar.addTab(ontopToolbar.newTab().setText(typedArray.getString(i)));
            }
            ontopToolbar.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab LayoutTab) {
                    LayoutTab.select();
                }

                @Override
                public void onTabUnselected(TabLayout.Tab LayoutTab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab LayoutTab) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            typedArray.recycle();
        }
    }
}
