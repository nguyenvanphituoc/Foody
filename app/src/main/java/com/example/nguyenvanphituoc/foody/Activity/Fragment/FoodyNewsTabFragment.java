package com.example.nguyenvanphituoc.foody.Activity.Fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyenvanphituoc.foody.Adapter.FragmentAdapter;
import com.example.nguyenvanphituoc.foody.Interface.SendDataFromChildFragment;
import com.example.nguyenvanphituoc.foody.Model.CityModel;
import com.example.nguyenvanphituoc.foody.Model.DistrictModel;
import com.example.nguyenvanphituoc.foody.Model.WardModel;
import com.example.nguyenvanphituoc.foody.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 3/16/2017.
 */

public class FoodyNewsTabFragment extends Fragment implements SendDataFromChildFragment, Serializable {
    private String[] tabName;
    private FrameLayout displayViewContainer;
//    private String model;
    private ViewGroup tabOnBottom;
    private List<String> listFragment = new ArrayList<>();
    private TabLayout.Tab clickedTab;
    @Override
    public void sendTabName(String name) {
        if(name != null) {
            clickedTab.setText(name);
            View v = clickedTab.getCustomView();
            assert v != null;
            ((TextView) v).setText(name);
            ((TextView) v).setTextColor(getResources().getColor(R.color.clRed, null));
            if(name.contains("categories"))
                ((TextView) v).setTextColor(getResources().getColor(R.color.clBlack, null));
        }
    }

    @Override
    public void sendAddress(int city, String district, String ward) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        assert savedInstanceState != null;
        if (savedInstanceState != null) {
            tabName = savedInstanceState.getStringArray("tab");
//            model = savedInstanceState.getString("model");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.inflate_tab_onbottom_news, null, false);
        this.displayViewContainer = (FrameLayout) mView.findViewById(R.id.main_view_layout);
        tabOnBottom = (ViewGroup) ((View) container.getParent()).findViewById(R.id.tabbar_onBottom);
        initialOnTopTab(mView);
        initFragment(FoodyNewsDisplayFragment.class.getName());
        return mView;
    }

    // create toolbar ontop
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initialOnTopTab(View v) {
        TabLayout ontopTabBar = (TabLayout) v.findViewById(R.id.tab_ontop);
        try {
            FragmentAdapter fragmentAdapter = new FragmentAdapter(getFragmentManager(), getContext());
            for (int i = 1, n = tabName.length; i < n; i++) {
                TabLayout.Tab mTab = ontopTabBar.newTab();
//                mTab.setCustomView(+R.color.clGrey);
                mTab.setText(tabName[i]);
                mTab.setCustomView(fragmentAdapter.getTabOntopView(mTab));
                ontopTabBar.addTab(mTab);
            }
            //
            String[] listFragmentName = {FoodyNewsListServiceFragment.class.getName(),
                    FoodyNewsListCategoriesFragment.class.getName(), FoodyNewsListCityFragment.class.getName()};
            //
            for (int i = 0, n = ontopTabBar.getTabCount(); i < n; i++) {
                listFragment.add(listFragmentName[i % listFragmentName.length]);
            }

            ontopTabBar.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(@NonNull TabLayout.Tab LayoutTab) {
                    // do something
                    tabOnBottom.setElevation(1);
                    tabOnBottom.setTranslationZ(1);
                    clickedTab = LayoutTab;
                    doTabChange(clickedTab.getPosition(), clickedTab.getText().toString());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab LayoutTab) {
                }

                @Override
                public void onTabReselected(@NonNull TabLayout.Tab LayoutTab) {
                    tabOnBottom.setElevation(1);
                    tabOnBottom.setTranslationZ(1);
                    clickedTab = LayoutTab;
                    doTabChange(clickedTab.getPosition(), clickedTab.getText().toString());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void doTabChange(int position, String tabName) {
        addFragment(listFragment.get(position), tabName);
    }

    private void initFragment(String fragmentName) {

        try {
            Class<?> clazz = Class.forName(fragmentName);
            Fragment mainFragment = (Fragment) clazz.newInstance();
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.add(displayViewContainer.getId(), mainFragment);
            ft.commit();
        } catch (Exception ex) {
            Toast.makeText(this.getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void addFragment(String fragmentName, String tabName) {
        try {
            Class<?> clazz = Class.forName(fragmentName);
            Fragment mainFragment = (Fragment) clazz.newInstance();
            Bundle sendData = new Bundle();
//            sendData.putString("model", model);
            sendData.putString("tabName", tabName);
            sendData.putSerializable("fragment", this);
            mainFragment.onCreate(sendData);
            FragmentManager fragmentManager = getChildFragmentManager();

            for (int i = 0, n = fragmentManager.getBackStackEntryCount(); i < n; ++i)
                fragmentManager.popBackStack();

            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.add(displayViewContainer.getId(), mainFragment);
            ft.addToBackStack(mainFragment.getClass().getSimpleName());
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        } catch (Exception ex) {
            Toast.makeText(this.getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}
