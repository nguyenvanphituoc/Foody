package com.example.nguyenvanphituoc.foody.Activity.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nguyenvanphituoc.foody.Activity.UIUtils;
import com.example.nguyenvanphituoc.foody.Adapter.FoodyNewsListCategoriesAdapter;
import com.example.nguyenvanphituoc.foody.DAO.DatabaseHandler;
import com.example.nguyenvanphituoc.foody.Interface.SendDataFromChildFragment;
import com.example.nguyenvanphituoc.foody.Model.CategoriesModel;
import com.example.nguyenvanphituoc.foody.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Admin on 3/26/2017.
 */

public class FoodyNewsListCategoriesFragment extends Fragment {
    String model;
    String tabName;
    ListView myListView;
    Button btnBackStack;
    Fragment myFragment;
    SendDataFromChildFragment mCallback;

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            model = savedInstanceState.getString("model");
            tabName = savedInstanceState.getString("tabName");
            mCallback = (SendDataFromChildFragment) savedInstanceState.getSerializable("fragment");
        }
        myFragment = this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewContent = inflater.inflate(R.layout.tab_simple_listview, container, false);
        btnBackStack = (Button) viewContent.findViewById(R.id.tabOnTopSimpleListView_Txt);
        myListView = (ListView) viewContent.findViewById(R.id.tabOnTopSimpleListView_List);
        myListView.setBackground(getResources().getDrawable(R.color.grey05, null));

        final int[] position = new int[1];
//        ArrayList<CategoriesModel> listData = getDataFromModel(findDataName(tabName, position));

        ArrayList<CategoriesModel> listData = CategoriesModel.getAllCategory(new DatabaseHandler(getContext()));
        findDataName(listData, tabName, position);

        if (listData == null) return null;
        ArrayAdapter<CategoriesModel> adapter = new FoodyNewsListCategoriesAdapter(getContext(), listData);
        myListView.setAdapter(adapter);
        UIUtils.setListViewHeightBasedOnItems(myListView);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txt = (TextView) view.findViewById(R.id.custom_inline_item_TextView);
                mCallback.sendTabName(txt.getText().toString());
                btnBackStack.performClick();
            }
        });
        myListView.post(new Runnable() {
            @Override
            public void run() {
                myListView.setSelection(position[0]);
                View view = getViewByPosition(position[0], myListView);
                TextView txt = (TextView) view.findViewById(R.id.custom_inline_item_TextView);
                txt.setTextColor(getResources().getColor(R.color.clRed, null));
            }
        });
        btnBackStack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myFragment.getActivity().onBackPressed();
            }
        });

        return viewContent;
    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    private void findDataName(ArrayList<CategoriesModel> listData, String Name, int[] o) {
        for (int i = 0, n = listData.size(); i < n; i++) {
            CategoriesModel data = listData.get(i);
            if (data.getName().equals(Name)) {
                o[0] = i;
                return;
            }
        }
    }

    private JSONObject findDataName(String tabName, int[] position) {
        try {
            //obj -> obj{3} -> {dataArray -> obj{nameString, imageString}, pathString}
            JSONObject obj = new JSONObject(model);
            Iterator keys = obj.keys();
            while (keys.hasNext()) {
                String dynamicKey = (String) keys.next();
                //obj 3
                JSONObject line = obj.getJSONObject(dynamicKey);
                //dataArray
                JSONArray m_JsonArray = line.getJSONArray("data");
                for (int i = 0; i < m_JsonArray.length(); i++) {
                    //obj{nameString, imageString}
                    JSONObject jo_inside = m_JsonArray.getJSONObject(i);
                    String name = jo_inside.getString("name");
                    if (name.toLowerCase().equals(tabName.toLowerCase())) {
                        position[0] = i;
                        return line;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private ArrayList<CategoriesModel> getDataFromModel(JSONObject obj) {
        try {
            JSONArray m_jArray = obj.getJSONArray("data");
            String path = obj.getString("path");
            ArrayList<CategoriesModel> formList = new ArrayList<>();
            for (int i = 0; i < m_jArray.length(); i++) {
                JSONObject jo_inside = m_jArray.getJSONObject(i);
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
}