package com.example.nguyenvanphituoc.foody.Adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nguyenvanphituoc.foody.Model.CategoriesModel;
import com.example.nguyenvanphituoc.foody.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by PhiTuocPC on 3/31/2017.
 * nguyễn văn phi tước
 */

public class FoodyNewsListCategoriesAdapter extends ArrayAdapter<CategoriesModel> {
    private Context mContext;
    private List<CategoriesModel> modelList;
    public FoodyNewsListCategoriesAdapter(@NonNull Context context, @NonNull List<CategoriesModel> objects) {
        super(context, 0, objects);
        this.mContext = context;
        this.modelList = objects;
    }

    @Override
    public int getCount() {
        return modelList.size();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        //get the property we are displaying
        CategoriesModel categoriesModel;
        ViewHolder viewHolder;
        if (convertView == null) {
            // error 12 pos
            categoriesModel = modelList.get(position);
            //get the inflater and inflate the XML layout for each item
            LayoutInflater inflater=LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.custom_list_inline, parent, false);
            viewHolder = new ViewHolder(convertView);

            if (categoriesModel.getBlobImg() != null) {
//                Drawable drawable = getDrawableFromAsset(places.getPathImg());
                viewHolder.imageView.setImageBitmap(getBitmapFromByte(categoriesModel.getBlobImg()));
//                viewHolder.imageView.setImageDrawable(getDrawableFromAsset(places.getPathImg()));
            }
            if(categoriesModel.getStt()!= null && !categoriesModel.getStt().equals("")){
                viewHolder.textStatus.setText(categoriesModel.getStt());
                viewHolder.textStatus.setPadding(15,5,15,5);
                viewHolder.textStatus.setBackground(mContext.getResources().getDrawable(R.drawable.simple_corner_clred, null));
                viewHolder.textStatus.setTextColor(mContext.getResources().getColor(R.color.clWhite, null));
            }
            viewHolder.textContent.setText(categoriesModel.getName());
            viewHolder.textContent.setTextColor(mContext.getResources().getColor(R.color.clBlack, null));

            convertView.setBackground(mContext.getResources().getDrawable(R.color.clGrey, null));
        }
        return convertView;
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



    private Bitmap getBitmapFromByte(byte[] blob) {
        try {
            return BitmapFactory.decodeByteArray(blob, 0, blob.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Drawable getDrawableFromAsset(String strName) {
        AssetManager assetManager = mContext.getAssets();
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

    private class ViewHolder {
        ImageView imageView;  TextView textContent; TextView textStatus;
        private ViewHolder(View convertView) {
            this.imageView = (ImageView) convertView.findViewById(R.id.custom_inline_item_tImageView);
            this.textContent = (TextView) convertView.findViewById(R.id.custom_inline_item_TextView);
            this.textStatus = (TextView) convertView.findViewById(R.id.custom_inline_item_StatusView);
        }
    }
}
