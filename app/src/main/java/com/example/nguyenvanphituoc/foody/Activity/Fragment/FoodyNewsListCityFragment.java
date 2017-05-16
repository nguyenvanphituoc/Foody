package com.example.nguyenvanphituoc.foody.Activity.Fragment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nguyenvanphituoc.foody.Activity.MainActivity;
import com.example.nguyenvanphituoc.foody.Activity.UIUtils;
import com.example.nguyenvanphituoc.foody.DAO.DatabaseHandler;
import com.example.nguyenvanphituoc.foody.DAO.ExtraSupport.StaticSupportResources;
import com.example.nguyenvanphituoc.foody.DAO.OtherServiceImpl;
import com.example.nguyenvanphituoc.foody.DAO.ServiceAbs;
import com.example.nguyenvanphituoc.foody.Interface.GetDataFromChildFragment;
import com.example.nguyenvanphituoc.foody.Interface.SendDataToChildFragment;
import com.example.nguyenvanphituoc.foody.Model.CityModel;
import com.example.nguyenvanphituoc.foody.Model.DisplayModel;
import com.example.nguyenvanphituoc.foody.Model.DistrictModel;
import com.example.nguyenvanphituoc.foody.Model.WardModel;
import com.example.nguyenvanphituoc.foody.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by PhiTuocPC on 4/3/2017.
 * nguyễn văn phi tước
 */

public class FoodyNewsListCityFragment extends Fragment implements SendDataToChildFragment {
    //    String tabName;
    ServiceAbs<?> model;
    GetDataFromChildFragment myCallback;
    Button btnBackStack;
    Fragment myFragment;
    private WardModel myWard;

    @Override
    public void sendBundleToChild(Bundle savedInstanceState) {

    }

    @Override
    public void sendACKInitialData() {

    }

    @Override
    public boolean getWaitingACK() {

        if (model == null) {
            return true;
        }
        return !model.dataMode;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        myCallback = null;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
//            tabName = savedInstanceState.getString("tabName");
            myWard = (WardModel) savedInstanceState.get("ward");
            myCallback = (GetDataFromChildFragment) savedInstanceState.getSerializable("fragment");
        }
        myFragment = this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View viewContent = inflater.inflate(R.layout.inflate_onbottom_news_city, container, false);
        btnBackStack = (Button) viewContent.findViewById(R.id.city_btn_back);
        TextView txtCity = (TextView) viewContent.findViewById(R.id.city_text_root);
//        Button btnChangeCity = (Button) viewContent.findViewById(R.id.city_btn_root);
        LinearLayout myListView = (LinearLayout) viewContent.findViewById(R.id.city_scroll_list);

        viewContent.findViewById(R.id.city_btn_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //backstack
        btnBackStack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myFragment.getActivity().onBackPressed();
            }
        });

        int city_id = Integer.parseInt((String) txtCity.getTag());

        model = new OtherServiceImpl(OtherServiceImpl.OPERATION.GetAllStreetsByCity.toString(), MainActivity.mContext);
        if (StaticSupportResources.ISLOADEDCITY)
            model.getData();
        else
            model.acceptACKInitial(model, city_id);

        try {
            this.sendACKInitialData();
            while (this.getWaitingACK()) {
                Thread.sleep(10);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // find all districts and streets in city
//        CityModel city = InitialDataOfCity(db, city_id);
//        CityModel city = new CityModel();
        if (model.listData == null || model.listData.size() <= 0) return null;
        CityModel city = groupWardByCity( (ArrayList<WardModel>) model.listData);
        // check if is first click of tab => initial by text.Tag so we can change city and put value to the Tag or save to main tab
        if (myWard == null) myWard = new WardModel(city_id, null, null);
        //set tab name is city name
        txtCity.setText(city.getName());
        //set the city is high light
        if (myWard.getDistrict() == null && myWard.getStreet() == null)
            txtCity.setTextColor(getResources().getColor(R.color.clRed, null));
        txtCity.setOnClickListener(textChooseClicked(txtCity.getText().toString(), new WardModel(city_id, null, null)));
        // that features in future
        //create district and add to myListview is main list view
        for (final DistrictModel district : city.districtModels) {
            final View view = inflater.inflate(R.layout.custom_list_spinner, null, false);
            final TextView tv = (TextView) view.findViewById(R.id.city_text_left);
            final Button btn = (Button) view.findViewById(R.id.city_btn_right);
            //if we click district text change name to district is high light
            tv.setText(district.getName());
            if (myWard.getDistrict() != null && myWard.getStreet() == null && tv.getText().equals(myWard.getDistrict()))
                tv.setTextColor(getResources().getColor(R.color.clRed, null));
            //when click send data to main tab
            tv.setOnClickListener(textChooseClicked(tv.getText().toString(), new WardModel(city.getId(), district.getName(), null)));
            //
            String countWard = Integer.toString(district.wardModels.size()) + " streets";
            // create nested list view in myListview when button expand click
            final ListView expandList = (ListView) view.findViewById(R.id.city_expand_list);
            expandList.setDividerHeight(0);
            String[] list = new String[district.wardModels.size()];
            // get simple String data
            for (int i = 0, n = district.wardModels.size(); i < n; i++)
                list[i] = district.wardModels.get(i).getStreet();
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
                    textView.setText(strings.get(position));
                    return textView;
                }
            });
            expandList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    myCallback.getTabName(((TextView) view).getText().toString());
                    WardModel ward = new WardModel(district.getCity_id(), district.getName(), district.wardModels.get(position).getStreet());
                    myCallback.getAddress(ward);
                    btnBackStack.performClick();
                }
            });
            //the first time we not click so set high to ZERO
            ViewGroup.LayoutParams params = expandList.getLayoutParams();
            params.height = 0;
            expandList.setLayoutParams(params);
            //check status of the button to toggle the nested list
            btn.setText(countWard);
            btn.setTag("click");
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String stt = (String) v.getTag();
                    if (stt.equals("click")) {
                        //set high of list == high of all view in list
                        UIUtils.setListViewHeightBasedOnItems(expandList);
                        v.setTag("clicked");
                    } else if (stt.equals("clicked")) {
                        ViewGroup.LayoutParams params = expandList.getLayoutParams();
                        params.height = 0;
                        expandList.setLayoutParams(params);
                        v.setTag("click");
                    }

                }
            });
            //check high light if the street is clicked before
            if (district.getName().equals(myWard.getDistrict()) && myWard.getStreet() != null) {
                final int[] position = new int[1];
                findDataName(strings, myWard.getStreet(), position);
                if (position[0] != -1) {
                    expandList.setSelection(position[0]);
                    View templateV = getViewByPosition(position[0], expandList);
                    TextView txt = (TextView) templateV;
                    txt.setTextColor(Color.RED);
                    btn.performClick();
                }
            }

            myListView.addView(view);
        }
        return viewContent;
    }

    public View.OnClickListener textChooseClicked(final String name, final WardModel ward) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // send data and call back
                myCallback.getTabName(name);
                WardModel tmp = new WardModel(ward.getId(), ward.getDistrict(), ward.getStreet());
                myCallback.getAddress(tmp);
                btnBackStack.performClick();
            }
        };
    }

    // get position of list data
    private void findDataName(ArrayList<String> listData, String Name, int[] o) {
        for (int i = 0, n = listData.size(); i < n; i++) {
            String data = listData.get(i);
            if (data.equals(Name)) {
                o[0] = i;
                return;
            }
        }
        o[0] = -1;
    }

    private CityModel groupWardByCity(List<WardModel> list) {

        CityModel cityModel = new CityModel();
        // initial first element
        cityModel.setId(list.get(0).getId());
        cityModel.setName(list.get(0).getCity());
        DistrictModel sentinelDistrict = new DistrictModel(list.get(0).getId(), list.get(0).getDistrict());
        cityModel.districtModels.add(sentinelDistrict);

        for ( int i = 1, n = list.size(); i < n; i++ ) {

            WardModel wardIndex = list.get(i);
            DistrictModel district = new DistrictModel(wardIndex.getId(), wardIndex.getDistrict());
            if (!sentinelDistrict.getName().equals(district.getName())) {
                sentinelDistrict = district;
                cityModel.districtModels.add(sentinelDistrict);
            }
            sentinelDistrict.wardModels.add(wardIndex);
        }
        return cityModel;
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
}
