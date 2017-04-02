package com.example.nguyenvanphituoc.foody.Model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nguyenvanphituoc.foody.DAO.DatabaseHandler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PhiTuocPC on 3/31/2017.
 */

public class ServiceModel implements Serializable {
    private String name;
    private String pathImg;
    private byte[] blobImg;
    private String stt;
    private static final String TABLE_NAME = "foody_service";

    public ServiceModel(String name, String pathImg, String status) {
        this.name = name;
        this.pathImg = pathImg;
        this.stt = status;
    }

    public ServiceModel(String name, byte[] blobImg, String status) {
        this.name = name;
        this.blobImg = blobImg;
        this.stt = status;
    }

    public byte[] getBlobImg() {
        return blobImg;
    }

    public void setBlobImg(byte[] blobImg) {
        this.blobImg = blobImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPathImg() {
        return pathImg;
    }

    public void setPathImg(String pathImg) {
        this.pathImg = pathImg;
    }

    public String getStt() {
        return stt;
    }

    public void setStt(String stt) {
        this.stt = stt;
    }

    public boolean insertService(DatabaseHandler databaseHandler) {
        return false;
    }

    public boolean updateService(DatabaseHandler databaseHandler) {
        return false;
    }

    public boolean deleteService(DatabaseHandler databaseHandler) {
        return false;
    }

    static public ArrayList<ServiceModel> getAllService(DatabaseHandler databaseHandler) {
        ArrayList<ServiceModel> lisAll = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase myDataBase = databaseHandler.getMyDataBase();
        Cursor cursor = myDataBase.rawQuery(query, null);//selectQuery,selectedArguments

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(1);
                byte[] pathImg = cursor.getBlob(2);
                String stt = cursor.getString(3);
                ServiceModel model = new ServiceModel(name, pathImg, stt);
                lisAll.add(model);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        myDataBase.close();

        return lisAll;
    }
}