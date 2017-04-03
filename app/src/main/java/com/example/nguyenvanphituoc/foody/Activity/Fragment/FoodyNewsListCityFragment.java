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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nguyenvanphituoc.foody.Activity.UIUtils;
import com.example.nguyenvanphituoc.foody.DAO.DatabaseHandler;
import com.example.nguyenvanphituoc.foody.Interface.SendDataFromChildFragment;
import com.example.nguyenvanphituoc.foody.Model.CityModel;
import com.example.nguyenvanphituoc.foody.Model.DistrictModel;
import com.example.nguyenvanphituoc.foody.Model.ServiceModel;
import com.example.nguyenvanphituoc.foody.Model.WardModel;
import com.example.nguyenvanphituoc.foody.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by PhiTuocPC on 4/3/2017.
 */

public class FoodyNewsListCityFragment extends Fragment {
    String tabName;
    Button btnBackStack;
    TextView txtCity;
    Button btnChangeCity;
    LinearLayout myListView;
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
            tabName = savedInstanceState.getString("tabName");
            mCallback = (SendDataFromChildFragment) savedInstanceState.getSerializable("fragment");
        }
        myFragment = this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewContent = inflater.inflate(R.layout.inflate_onbottom_news_city, container, false);
        btnBackStack = (Button) viewContent.findViewById(R.id.city_btn_back);
        txtCity = (TextView) viewContent.findViewById(R.id.city_text_root);
        btnChangeCity = (Button) viewContent.findViewById(R.id.city_btn_root);
        myListView = (LinearLayout) viewContent.findViewById(R.id.city_scroll_list);

        btnBackStack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myFragment.getActivity().onBackPressed();
            }
        });

        int city_id = Integer.parseInt((String) txtCity.getTag());
        DatabaseHandler db = new DatabaseHandler(getContext());
        CityModel city = InitialDataOfCity(db, city_id);

        txtCity.setText(city.getName());
        txtCity.setOnClickListener(textChooseClicked(txtCity.getText().toString()));

        btnChangeCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        for (final DistrictModel district : city.districtModels) {
            final View view = inflater.inflate(R.layout.custom_list_spinner, null, false);
            TextView tv = (TextView) view.findViewById(R.id.city_text_left);
            Button btn = (Button) view.findViewById(R.id.city_btn_right);

            tv.setText(district.getName());
            tv.setTag(district.getCity_id());
            tv.setOnClickListener(textChooseClicked(tv.getText().toString()));

            String countWard = Integer.toString(district.wardModels.size()) + " streets";

            btn.setText(countWard);
            btn.setTag("click");
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ListView expandList = (ListView) view.findViewById(R.id.city_expand_list);

                    String stt = (String) v.getTag();
                    if (stt.equals("click")) {
                        String[] list = new String[district.wardModels.size()];
                        for (int i = 0, n = district.wardModels.size(); i < n; i++)
                            list[i] = district.wardModels.get(i).getName();
                        ArrayList<String> strings = new ArrayList<>();
                        strings.addAll(Arrays.asList(list));
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1
                                , strings);
                        expandList.setAdapter(adapter);
                        expandList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                mCallback.sendTabName(((TextView)view).getText().toString());
                                btnBackStack.performClick();
                            }
                        });
                        v.setTag("clicked");
                    } else if (stt.equals("clicked")) {
                        expandList.setAdapter(null);
                        v.setTag("click");
                    }
                    UIUtils.setListViewHeightBasedOnItems(expandList);
                }
            });
            myListView.addView(view);
        }
        return viewContent;
    }

    public View.OnClickListener textChooseClicked(final String name){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.sendTabName(name);
                btnBackStack.performClick();
            }
        };
    }

    public CityModel InitialDataOfCity(DatabaseHandler db, int city_id) {
        CityModel city = CityModel.getCityById(db, city_id);
        city.districtModels = DistrictModel.getAllDistrictByCity(db, city);

        for (DistrictModel districtModel : city.districtModels) {
            districtModel.cityModel = city;
            districtModel.wardModels = WardModel.getAllWardByDistrict(db, districtModel);
            for (WardModel ward : districtModel.wardModels) {
                ward.districtModel = districtModel;
            }
        }
        return city;
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
    }

}
