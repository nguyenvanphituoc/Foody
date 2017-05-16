package com.example.nguyenvanphituoc.foody.Activity.Fragment;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nguyenvanphituoc.foody.Activity.MainActivity;
import com.example.nguyenvanphituoc.foody.DAO.OtherServiceImpl;
import com.example.nguyenvanphituoc.foody.DAO.ServiceAbs;
import com.example.nguyenvanphituoc.foody.Interface.GetDataFromChildFragment;
import com.example.nguyenvanphituoc.foody.Interface.SendDataToChildFragment;
import com.example.nguyenvanphituoc.foody.Model.CategoriesModel;
import com.example.nguyenvanphituoc.foody.Model.WardModel;
import com.example.nguyenvanphituoc.foody.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by PhiTuocPC on 5/15/2017.
 */

public class FoodyAddNewDialogFragment extends DialogFragment implements SendDataToChildFragment {

    String operation;
    Object ward;
    Fragment myFragment;
    GetDataFromChildFragment myCallBack;
    ServiceAbs<?> model;

    @Override
    public void sendBundleToChild(Bundle savedInstanceState) {
        if (savedInstanceState != null)
            try {

                ward = savedInstanceState.getSerializable("ward");
                operation = savedInstanceState.getString("operation");
                myCallBack = (GetDataFromChildFragment) savedInstanceState.getSerializable("myCallBack");
            } catch (Exception ex) {
                ward = null;
                ex.printStackTrace();
            }
    }

    @Override
    public void sendACKInitialData() {
        model = new OtherServiceImpl(operation, MainActivity.mContext);
        model.acceptACKInitial(model, ward);
    }

    @Override
    public boolean getWaitingACK() {

        if (model == null) {
            return true;
        }
        return !model.dataMode;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.custom_list_dialog, container,
                false);
        myFragment = this;

        final Button btnBack = (Button) rootView.findViewById(R.id.custom_dialog_btn);
        final ListView expandList = (ListView) rootView.findViewById(R.id.custom_dialog_list);
        final TextView txtTitle = (TextView) rootView.findViewById(R.id.custom_dialog_title);

        expandList.setDividerHeight(0);
        if (model.listData == null || model.listData.size() == 0) return null;
        String[] list = new String[model.listData.size()];

        switch (operation) {
            case "GetAllCitys":
                // get simple String data
                for (int i = 0, n = model.listData.size(); i < n; i++) {
                    list[i] = ((WardModel) model.listData.get(i)).getCity();
                }
                break;
            case "GetAllDistrictByCity":
                // get simple String data
                for (int i = 0, n = model.listData.size(); i < n; i++) {
                    list[i] = ((WardModel) model.listData.get(i)).getDistrict();
                }
                break;
            case "GetAllStreetByDistrict":
                // get simple String data
                for (int i = 0, n = model.listData.size(); i < n; i++) {
                    list[i] = ((WardModel) model.listData.get(i)).getStreet();
                }
                break;
            case "GetAllCategories":
            case "GetAllServices":
                for (int i = 0, n = model.listData.size(); i < n; i++) {
                    list[i] = ((CategoriesModel) model.listData.get(i)).getName();
                }
                break;
            default:
                break;
        }

        final ArrayList<String> strings = new ArrayList<>();
        strings.addAll(Arrays.asList(list));
        expandList.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, strings) {
            @Override
            public int getCount() {
                return strings.size();
            }

            // important!!!
            @Override
            public int getViewTypeCount() {
                return getCount();
            }

            @Override
            public int getItemViewType(int position) {
                return position;
            }

            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                textView.setBackground(getResources().getDrawable(R.color.clGrey, null));
                textView.setCompoundDrawables(null, null, null, getResources().getDrawable(R.drawable.bottom_edittext_line, null));
                textView.setText(strings.get(position));
                return textView;
            }
        });

        expandList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (operation.equals("GetAllCitys") || operation.equals("GetAllDistrictByCity")  || operation.equals("GetAllStreetByDistrict") ) {
                    WardModel ward = (WardModel) model.listData.get(position);
                    switch (operation) {
                        case "GetAllCitys":
                            // get simple String data
                            ward = new WardModel(ward.getId(), ward.getCity());
                            break;
                        case "GetAllDistrictByCity":
                            // get simple String data
                            ward = new WardModel(ward.getId(), null, ward.getDistrict(), null);
                            break;
                        case "GetAllStreetByDistrict":
                            // get simple String data
                            ward = new WardModel(ward.getId(), null, ward.getStreet());
                            break;
                        default:
                            break;
                    }
                    myCallBack.getAddress(ward);
                }
                else {
                    String tabName;
                    CategoriesModel category = (CategoriesModel) model.listData.get(position);
                    tabName = category.getId() + ":" +category.getName();
                    myCallBack.getTabName(tabName);
                }
                btnBack.performClick();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().remove(myFragment).commit();
            }
        });

        txtTitle.setText("Choose city");
        // Do something else
        return rootView;
    }

}
