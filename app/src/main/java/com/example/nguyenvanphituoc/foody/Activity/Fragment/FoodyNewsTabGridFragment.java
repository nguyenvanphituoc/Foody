package com.example.nguyenvanphituoc.foody.Activity.Fragment;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
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
import com.example.nguyenvanphituoc.foody.DAO.ExtraSupport.StaticSupportResources;
import com.example.nguyenvanphituoc.foody.Interface.GetDataFromChildFragment;
import com.example.nguyenvanphituoc.foody.Interface.SendDataToChildFragment;
import com.example.nguyenvanphituoc.foody.Model.WardModel;
import com.example.nguyenvanphituoc.foody.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PhiTuocPC on 4/5/2017.
 * nguyen van phi tuoc
 */

public class FoodyNewsTabGridFragment  extends Fragment implements GetDataFromChildFragment, Serializable {
    private String[] tabName;
    private FrameLayout displayViewContainer;
    //    private String model;
    private ViewGroup tabOnBottom;
    private List<String> listFragment = new ArrayList<>();
    private TabLayout.Tab clickedTab;
    private WardModel ward;
    private TabLayout onTopTab;
    // send data to child fragment
    private Fragment displayFragment;

    @Override
    public void getTabName(String name) {
        if (name != null) {
            StaticSupportResources.ISLOADEDPLACES = false;
            String[] names = name.split(":");

            clickedTab.setText(names[1]);
            clickedTab.setTag(names[0]);
            View v = clickedTab.getCustomView();
            assert v != null;
            ((TextView) v).setText(names[1]);
            ((TextView) v).setTextColor(getResources().getColor(R.color.clRed, null));
            if (names[1].contains("categories"))
                ((TextView) v).setTextColor(getResources().getColor(R.color.clBlack, null));


            Bundle senData = new Bundle();
            senData.putInt("service", Integer.parseInt(onTopTab.getTabAt(0).getTag().toString()));
            senData.putInt("category", Integer.parseInt(onTopTab.getTabAt(1).getTag().toString()));
            senData.putSerializable("ward", this.ward);
            final FragmentTransaction ft = getFragmentManager().beginTransaction();
            ((SendDataToChildFragment) displayFragment).sendBundleToChild(senData);
            ft.detach(displayFragment);
            ft.attach(displayFragment);
            ft.commit();
        }
    }

    @Override
    public void getAddress(WardModel sender) {
        this.ward = new WardModel(sender.getId(), sender.getDistrict(), sender.getStreet());

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
        onTopTab = initialOnTopTab(mView);

        onTopTab.getTabAt(0).setTag(0);
        onTopTab.getTabAt(1).setTag(0);
        onTopTab.getTabAt(2).setTag(0);

        initFragment(FoodyNewsDisplayGridFragment.class.getName());
        return mView;
    }

    // create toolbar ontop
    @RequiresApi(api = Build.VERSION_CODES.M)
    private TabLayout initialOnTopTab(View v) {
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
            return ontopTabBar;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void doTabChange(int position, String tabName) {
        addFragment(listFragment.get(position), tabName);
    }

    //main fragment to display
    private void initFragment(String fragmentName) {

        try {
            Class<?> clazz = Class.forName(fragmentName);
            Fragment mainFragment = (Fragment) clazz.newInstance();
            Bundle senData = new Bundle();
            senData.putInt("service", Integer.parseInt(onTopTab.getTabAt(0).getTag().toString()));
            senData.putInt("category", Integer.parseInt(onTopTab.getTabAt(1).getTag().toString()));
            senData.putSerializable("ward", this.ward);
            if (mainFragment instanceof SendDataToChildFragment) {
                ((SendDataToChildFragment) mainFragment).sendBundleToChild(senData);
            }
            //store child fragment to display and send data to
            displayFragment = mainFragment;
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.add(displayViewContainer.getId(), mainFragment, mainFragment.getClass().getName());
            ft.commit();
        } catch (Exception ex) {
            Toast.makeText(this.getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //fragment display when click ontop tablayout
    private void addFragment(String fragmentName, String tabName) {
        try {
            Class<?> clazz = Class.forName(fragmentName);
            Fragment mainFragment = (Fragment) clazz.newInstance();
            Bundle sendData = new Bundle();
//            sendData.putString("model", model);
            sendData.putString("tabName", tabName);
            sendData.putSerializable("fragment", this);
            //choose city
            if (ward != null)
                sendData.putSerializable("ward", this.ward);
            mainFragment.onCreate(sendData);
            FragmentManager fragmentManager = getChildFragmentManager();

            for (int i = 0, n = fragmentManager.getBackStackEntryCount(); i < n; ++i)
                fragmentManager.popBackStack();

            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.add(displayViewContainer.getId(), mainFragment, mainFragment.getClass().getName());
            ft.addToBackStack(mainFragment.getClass().getSimpleName());
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        } catch (Exception ex) {
            Toast.makeText(this.getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}