package com.example.nguyenvanphituoc.foody.Adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nguyenvanphituoc.foody.Model.CategoriesModel;
import com.example.nguyenvanphituoc.foody.Model.DisplayModel;
import com.example.nguyenvanphituoc.foody.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

/**
 * Created by PhiTuocPC on 4/4/2017.
 */

public class FoodyNewsListDisplayAdapter extends ArrayAdapter<DisplayModel> {
    private Context mContext;
    private List<DisplayModel> modelList;
    public FoodyNewsListDisplayAdapter(@NonNull Context context, @NonNull List<DisplayModel> objects) {
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
        DisplayModel model;
        ViewHolder viewHolder;
        if (convertView == null) {
            // error 12 pos
            model = modelList.get(position);
            //get the inflater and inflate the XML layout for each item
            LayoutInflater inflater=LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.custom_list_display, parent, false);
            viewHolder = new ViewHolder(convertView);

            if (model.getImage() != null) {
//                Drawable drawable = getDrawableFromAsset(places.getPathImg());
                Bitmap myBitmap = getBitmapFromByte(model.getImage());
                viewHolder.imgDisplay.setImageBitmap(myBitmap);
//                viewHolder.imageView.setImageDrawable(getDrawableFromAsset(places.getPathImg()));
            }
            viewHolder.textName.setText(model.getName() + "-" + model.getCategory_name());
            viewHolder.textAddress.setText(model.getCity_name() + " " + model.getAddress());
            String rating = String.format(Locale.US, "%.1f", model.getRating());
            viewHolder.textRating.setText(rating);
            if(model.getRating() > 5)
                viewHolder.textRating.setBackground(mContext.getResources().getDrawable(R.drawable.text_circle_green, null));
            else  viewHolder.textRating.setBackground(mContext.getResources().getDrawable(R.drawable.text_circle_red, null));
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
        ImageView imgDisplay;  TextView textRating, textName, textAddress, comment;
        private ViewHolder(View convertView) {
            this.imgDisplay = (ImageView) convertView.findViewById(R.id.custom_list_display_image);
            this.textRating = (TextView) convertView.findViewById(R.id.custom_list_display_text_rating);
            this.textName = (TextView) convertView.findViewById(R.id.custom_list_display_text_name);
            this.textAddress = (TextView) convertView.findViewById(R.id.custom_list_display_text_address);
        }
    }
}