package com.example.nguyenvanphituoc.foody.Activity.Fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.nguyenvanphituoc.foody.Adapter.PageNewsAdapter;
import com.example.nguyenvanphituoc.foody.Model.CategoriesModel;
import com.example.nguyenvanphituoc.foody.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Admin on 3/16/2017.
 */

public class FoodyPage1Fragment extends Fragment {
    private String[] tabName;
    private ViewGroup displayViewContainer;
    private String model;
    private ViewGroup viewPager;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        assert savedInstanceState != null;
        if (savedInstanceState != null) {
            tabName = savedInstanceState.getStringArray("tab");
            model = savedInstanceState.getString("model");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.inflate_tab_onbottom_news, container, false);
        this.displayViewContainer = (ViewGroup) mView.findViewById(R.id.main_view_layout);
        viewPager = container;
        initialOntopTabbar(mView);
        return mView;
    }

    // create toolbar ontop
    private void initialOntopTabbar(View v) {
        final TabLayout ontopTabBar = (TabLayout) v.findViewById(R.id.tab_ontop);
        try {
            for (int i = 1, n = tabName.length; i < n; i++) {
                TabLayout.Tab mTab = ontopTabBar.newTab();
//                mTab.setCustomView(+R.color.clGrey);
                ontopTabBar.addTab(mTab.setText(tabName[i]));
            }
            ontopTabBar.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(@NonNull TabLayout.Tab LayoutTab) {
                    // do something
                    doTabChange(getObjName(LayoutTab.getText().toString()));
                }

                @Override
                public void onTabUnselected(TabLayout.Tab LayoutTab) {
                }

                @Override
                public void onTabReselected(@NonNull TabLayout.Tab LayoutTab) {
                    doTabChange(getObjName(LayoutTab.getText().toString()));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getObjName(String tabName) {
        return tabName;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void doTabChange(String objName) {
        ArrayList<CategoriesModel> listData = getDataFromModel(objName);
        assert listData != null;
        ArrayAdapter<CategoriesModel> adapter = new PageNewsAdapter(getContext(), 0, listData);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        displayViewContainer.removeAllViewsInLayout();
        displayViewContainer.clearFocus();
        displayViewContainer.setBackground(getResources().getDrawable(R.color.grey05, null));
        View viewContent = inflater.inflate(R.layout.tab_ontop_simple_listview, displayViewContainer);

        ListView myListView = (ListView) viewContent.findViewById(R.id.tabOnTopSimpleListView_List);
        myListView.setBackground(getResources().getDrawable(R.color.grey05, null));
        myListView.setAdapter(adapter);
    }

    private ArrayList<CategoriesModel> getDataFromModel(String objName) {
        try {
            JSONObject obj = new JSONObject(model).getJSONObject(objName);
            JSONArray m_jArry = obj.getJSONArray("data");
            String path = obj.getString("path");
            ArrayList<CategoriesModel> formList = new ArrayList<>();
            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                Log.d("Details-->", jo_inside.getString("name"));
                String name = jo_inside.getString("name");
                String img = jo_inside.getString("img");
                String stt = "";
                if (jo_inside.has("stt"))
                    stt = jo_inside.getString("stt");
                //Add your values in your `ArrayList` as below:
                CategoriesModel categoriesModel = new CategoriesModel(name, path + "/" + img, stt);
                formList.add(categoriesModel);
            }
            return formList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    private String[] getFragmentList(String jsonData){
//        String[] result ;
//        try {
//            JSONObject obj = new JSONObject(jsonData);
//            result = new String[obj.length()];
//            for(int i =0 ; i < obj.length(); i++){
//                result[i] = obj.names().getString(i);
//            }
//        }
//        catch (Exception ex){
//            ex.printStackTrace();
//        }
//        return null;
//    }

//    public void replaceFragment(Fragment fragment) {
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.replace(R.id.frame_container, fragment);
//        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        ft.commit();
//    }
}
