package com.example.nguyenvanphituoc.foody.Activity.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nguyenvanphituoc.foody.Activity.MainActivity;
import com.example.nguyenvanphituoc.foody.Activity.UIUtils;
import com.example.nguyenvanphituoc.foody.Adapter.FoodyNewsListDisplayAdapter;
import com.example.nguyenvanphituoc.foody.DAO.ExtraSupport.StaticSupportResources;
import com.example.nguyenvanphituoc.foody.DAO.PlaceServiceImpl;
import com.example.nguyenvanphituoc.foody.DAO.ServiceAbs;
import com.example.nguyenvanphituoc.foody.Interface.SendDataToChildFragment;
import com.example.nguyenvanphituoc.foody.Model.DisplayModel;
import com.example.nguyenvanphituoc.foody.Model.WardModel;
import com.example.nguyenvanphituoc.foody.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * Created by Admin on 3/26/2017.
 * nguyễn văn phi tước
 */

public class FoodyNewsDisplayFragment extends Fragment implements SendDataToChildFragment {
    int category = 0;
    int service = 0;
    WardModel ward;
    ServiceAbs<DisplayModel> model;
    static int count = 0;

    @Override
    public void sendACKInitialData() {

        if (category != 0 || service != 0 || ward != null) {
            model = new PlaceServiceImpl(PlaceServiceImpl.OPERATION.FilterPlaces.toString(), MainActivity.mContext);
            Map<String, Object> mapping =  new HashMap<>();
            mapping.put("serviceId", service);
            mapping.put("categoryId", category);
            mapping.put("ward", ward);
            model.acceptACKInitial(model, mapping);
        } else {
            model = new PlaceServiceImpl(PlaceServiceImpl.OPERATION.GetAllPlaces.toString(), MainActivity.mContext);
            if (StaticSupportResources.ISLOADEDPLACES)
                model.getData();
            else
                model.acceptACKInitial(model, null);
        }

        count++;
    }

    @Override
    public boolean getWaitingACK() {
        return !model.dataMode;
    }

    @Override
    public void sendBundleToChild(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            service = savedInstanceState.getInt("service");
            category = savedInstanceState.getInt("category");
            ward = (WardModel) savedInstanceState.getSerializable("ward");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.inflate_onbottom_news_display, container, false);
        final ListView myListView = (ListView) contentView.findViewById(R.id.display_listview);
        ArrayAdapter<DisplayModel> adapter;

        try {
            ProgressDialog progressDialog;
            progressDialog = ProgressDialog.show(this.getContext(), "Load data",
                    "Please wait for a while.", true);
            this.sendACKInitialData();
            while (this.getWaitingACK()) {
                Thread.sleep(10);
            }
            progressDialog.dismiss();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (model.listData != null) {
            adapter = new FoodyNewsListDisplayAdapter(getContext(), model.listData);
            myListView.setAdapter(null);
            myListView.setAdapter(adapter);
        }
        UIUtils.setListViewHeightBasedOnItems(myListView);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "CLicked", Toast.LENGTH_SHORT).show();
            }
        });
        return contentView;
    }

}
