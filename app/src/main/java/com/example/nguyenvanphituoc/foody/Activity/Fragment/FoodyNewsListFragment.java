package com.example.nguyenvanphituoc.foody.Activity.Fragment;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.example.nguyenvanphituoc.foody.Adapter.FoodyNewsAdapter;
import com.example.nguyenvanphituoc.foody.Model.CategoriesModel;
import com.example.nguyenvanphituoc.foody.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Admin on 3/26/2017.
 */

public class FoodyNewsListFragment extends Fragment {
    String model;
    String tabName;
    ListView myListView;
    Button btnBackStack;
    Fragment myFragment;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            model = savedInstanceState.getString("model");
            tabName = savedInstanceState.getString("tabName");
        }
        myFragment = this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewContent = inflater.inflate(R.layout.tab_ontop_simple_listview, container, false);
        btnBackStack = (Button) viewContent.findViewById(R.id.tabOnTopSimpleListView_Txt);
        myListView = (ListView) viewContent.findViewById(R.id.tabOnTopSimpleListView_List);
        myListView.setBackground(getResources().getDrawable(R.color.grey05, null));
        ArrayList<CategoriesModel> listData = getDataFromModel(findDataName(tabName));
        assert listData!=null;
        ArrayAdapter<CategoriesModel> adapter = new FoodyNewsAdapter(getContext(), 0, listData);
        for(int i = 0, n = adapter.getCount(); i < n; i++){
            View v = null;
            v = getView(i, v, listData);
            myListView.addView(v);
        }
//        myListView.setAdapter(adapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txt = (TextView) view.findViewById(R.id.custom_inline_item_TextView);
                ImageView img = (ImageView) view.findViewById(R.id.custom_inline_item_tImageView);
                for (int i = 0, n = parent.getCount(); i < n; i++) {
                    View v = parent.getChildAt(i);
                    ImageView image = (ImageView) v.findViewById(R.id.custom_inline_item_tImageView);
                    image.setColorFilter(getResources().getColor(R.color.grey05, null), PorterDuff.Mode.SRC_ATOP);
                    TextView text = (TextView) v.findViewById(R.id.custom_inline_item_TextView);
                    text.setTextColor(getResources().getColor(R.color.clBlack, null));
                }
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



    private JSONObject findDataName(String tabName) {
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
                    if (name.toLowerCase().equals(tabName.toLowerCase()))
                        return line;
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

    public View getView(int position, View convertView,ArrayList<CategoriesModel> modelList){
        //get the property we are displaying
        CategoriesModel caterogy ;
        ViewHolder viewHolder  ;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            // error 12 pos
            caterogy = modelList.get(position);
            //get the inflater and inflate the XML layout for each item
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_list_inline, null);

            viewHolder.imageView  = (ImageView) convertView.findViewById(R.id.custom_inline_item_tImageView);
            viewHolder.textContent = (TextView) convertView.findViewById(R.id.custom_inline_item_TextView);
            viewHolder.textStatus = (TextView) convertView.findViewById(R.id.custom_inline_item_StatusView);

            if(getDrawableFromAsset(caterogy.getPathImg()) != null) {
                Drawable drawable = getDrawableFromAsset(caterogy.getPathImg());
                viewHolder.imageView.setImageDrawable(drawable);
                viewHolder.imageView.setColorFilter(getContext().getResources().getColor(R.color.grey05, null), PorterDuff.Mode.SRC_ATOP);
            }
            viewHolder.textContent.setText(caterogy.getName());
            viewHolder.textContent.setTextColor(getContext().getResources().getColor(R.color.clBlack, null));

            convertView.setBackground(getActivity().getResources().getDrawable(R.color.clGrey, null));
        }
        return convertView;
    }

    private Drawable getDrawableFromAsset(String strName)
    {
        AssetManager assetManager = getContext().getAssets();
        InputStream istr;
        try {
            istr = assetManager.open(strName);
            Drawable img = Drawable.createFromStream(istr, null);
            istr.close();
            return img;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class ViewHolder{
        ImageView imageView;
        TextView  textContent;
        TextView  textStatus;
    }
}
