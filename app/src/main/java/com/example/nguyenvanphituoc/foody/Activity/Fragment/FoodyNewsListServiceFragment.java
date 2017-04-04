package com.example.nguyenvanphituoc.foody.Activity.Fragment;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nguyenvanphituoc.foody.Activity.UIUtils;
import com.example.nguyenvanphituoc.foody.Adapter.FoodyNewsListServiceAdapter;
import com.example.nguyenvanphituoc.foody.DAO.DatabaseHandler;
import com.example.nguyenvanphituoc.foody.Interface.SendDataFromChildFragment;
import com.example.nguyenvanphituoc.foody.Model.ServiceModel;
import com.example.nguyenvanphituoc.foody.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by PhiTuocPC on 3/31/2017.
 */

public class FoodyNewsListServiceFragment extends Fragment {
//    String model;
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
//            model = savedInstanceState.getString("model");
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
//        ArrayList<ServiceModel> listData = getDataFromModel(findDataName(tabName, position));


        ArrayList<ServiceModel> listData = ServiceModel.getAllService(new DatabaseHandler(getContext()));
        findDataName(listData, tabName, position);

        if (listData == null) return null;
        ArrayAdapter<ServiceModel> adapter = new FoodyNewsListServiceAdapter(getContext(), listData);
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

        myListView.setItemChecked(position[0], true);
        myListView.post(new Runnable() {
            @Override
            public void run() {
                myListView.setSelection(position[0]);
                View view = getViewByPosition(position[0], myListView);
                TextView txt = (TextView) view.findViewById(R.id.custom_inline_item_TextView);
                ImageView img = (ImageView) view.findViewById(R.id.custom_inline_item_tImageView);
                img.setColorFilter(getResources().getColor(R.color.clBlue2, null), PorterDuff.Mode.SRC_ATOP);
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

    private void findDataName(ArrayList<ServiceModel> listData, String Name, int[] o) {
        for (int i = 0, n = listData.size(); i < n; i++) {
            ServiceModel data = listData.get(i);
            if (data.getName().equals(Name)) {
                o[0] = i;
                return;
            }
        }
        o[0] = -1;
    }
}