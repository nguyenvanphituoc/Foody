package com.example.nguyenvanphituoc.foody.Adapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.LayoutRes;
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
 * Created by Admin on 3/19/2017.
 */

public class PageNewsAdapter extends ArrayAdapter<CategoriesModel> {
    private Context mContext;
    private List<CategoriesModel> modelList;
    public PageNewsAdapter(@NonNull Context context, int resource,
                           @NonNull List<CategoriesModel> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.modelList = objects;
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public View getView(int position, View convertView,@NonNull ViewGroup parent){
        //get the property we are displaying
        CategoriesModel caterogy ;
        ViewHolder viewHolder  ;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            caterogy = modelList.get(position);
            //get the inflater and inflate the XML layout for each item
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_list_inline, null);

            viewHolder.imageView  = (ImageView) convertView.findViewById(R.id.custom_inline_item_tImageView);
            viewHolder.textContent = (TextView) convertView.findViewById(R.id.custom_inline_item_TextView);
            viewHolder.textStatus = (TextView) convertView.findViewById(R.id.custom_inline_item_StatusView);

            Drawable drawable = getDrawableFromAsset(caterogy.getPathImg());
            viewHolder.imageView.setImageDrawable(drawable);
            viewHolder.textContent.setText(caterogy.getName());

            convertView.setBackground(mContext.getResources().getDrawable(R.color.clGrey, null));
        }
        return convertView;
    }

    private Bitmap getBitmapFromAsset(String strName)
    {
        AssetManager assetManager = mContext.getAssets();
        InputStream istr;
        try {
            istr = assetManager.open(strName);
            Bitmap bitmap = BitmapFactory.decodeStream(istr);
            istr.close();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
       return null;
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
