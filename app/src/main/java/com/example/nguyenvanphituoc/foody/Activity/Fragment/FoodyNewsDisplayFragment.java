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
import android.widget.Toast;

import com.example.nguyenvanphituoc.foody.Activity.UIUtils;
import com.example.nguyenvanphituoc.foody.Adapter.FoodyNewsListCategoriesAdapter;
import com.example.nguyenvanphituoc.foody.Adapter.FoodyNewsListDisplayAdapter;
import com.example.nguyenvanphituoc.foody.DAO.DatabaseHandler;
import com.example.nguyenvanphituoc.foody.Interface.SendDataToChildFragment;
import com.example.nguyenvanphituoc.foody.Model.CategoriesModel;
import com.example.nguyenvanphituoc.foody.Model.DisplayModel;
import com.example.nguyenvanphituoc.foody.R;

import java.util.ArrayList;

/**
 * Created by Admin on 3/26/2017.
 */

public class FoodyNewsDisplayFragment extends Fragment implements SendDataToChildFragment {
    ListView myListView;
    String category;
    String service;

    @Override
    public void sendToChild(String service, String category) {
        this.service = service;
        this.category = category;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.inflate_onbottom_news_display, container, false);
        myListView = (ListView) contentView.findViewById(R.id.display_listview);
        ArrayList<DisplayModel> listData = InitialData(service, category);
        ArrayAdapter<DisplayModel> adapter;
        if (listData == null || listData.size() == 0)
            adapter = new FoodyNewsListDisplayAdapter(getContext(), DisplayModel.getAllPlaces(new DatabaseHandler(getContext())));
        else
            adapter = new FoodyNewsListDisplayAdapter(getContext(), listData);
        myListView.setAdapter(adapter);
        UIUtils.setListViewHeightBasedOnItems(myListView);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "CLicked", Toast.LENGTH_SHORT).show();
            }
        });
        return contentView;
    }

    private ArrayList<DisplayModel> InitialData(String service, String category) {
        return DisplayModel.getAllPlaces(new DatabaseHandler(getContext()), service, category);
    }
}
