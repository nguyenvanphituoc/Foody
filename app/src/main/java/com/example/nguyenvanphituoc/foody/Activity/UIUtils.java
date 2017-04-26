package com.example.nguyenvanphituoc.foody.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.nguyenvanphituoc.foody.DAO.ServiceCaller;
import com.example.nguyenvanphituoc.foody.DAO.ServiceIF;
import com.example.nguyenvanphituoc.foody.Interface.GetDataFromService;
import com.example.nguyenvanphituoc.foody.Model.DisplayModel;

import org.jetbrains.annotations.Contract;

import java.util.List;

/**
 * Created by PhiTuocPC on 3/31/2017.
 * nguyễn văn phi tước
 */

public class UIUtils {
    //hanlde global resources
    /**
     * Sets ListView height dynamically based on the height of the items.
     *
     * @param listView to be resized
     * @return true if the listView is successfully resized, false otherwise
     */
    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            return true;
        } else {
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = 0;
            listView.setLayoutParams(params);
            return false;
        }
    }

    @NonNull
    public static byte[] ParseStringtoByte(String s){

        return s.getBytes();
    }

    @Contract("_ -> !null")
    public static String ParseByteToString(byte[]  bytes){

        return new String(bytes);
    }

    public static byte[] getByteFromBase64(String base64){
        return Base64.decode(base64, Base64.DEFAULT);
    }

    @Nullable
    public Bitmap getBitmapFromByte(byte[] blob) {

        try {

            return BitmapFactory.decodeByteArray(blob, 0, blob.length);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }
}
