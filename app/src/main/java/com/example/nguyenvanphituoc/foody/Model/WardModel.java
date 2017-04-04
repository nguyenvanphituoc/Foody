package com.example.nguyenvanphituoc.foody.Model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nguyenvanphituoc.foody.DAO.DatabaseHandler;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by PhiTuocPC on 4/3/2017.
 */

public class WardModel implements Serializable {
    private int city_id;
    private String district_name;
    private String name;
    private static final String TABLE_NAME = "foody_ward";
    public DistrictModel districtModel;
    public ArrayList<DistrictModel> districtModels = new ArrayList<>();
    public WardModel(int city_id, String district_name, String name) {
        this.city_id = city_id;
        this.district_name = district_name;
        this.name = name;
    }

    public WardModel(){}

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    static public ArrayList<WardModel> getAllWard(DatabaseHandler databaseHandler) {
        ArrayList<WardModel> lisAll = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase myDataBase = databaseHandler.getMyDataBase();
        Cursor cursor = myDataBase.rawQuery(query, null);//selectQuery,selectedArguments

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String district_name = cursor.getString(1);
                String name = cursor.getString(2);
                WardModel model = new WardModel(id, district_name, name);
                lisAll.add(model);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        myDataBase.close();

        return lisAll;
    }

    static public ArrayList<WardModel> getAllWardByDistrict(DatabaseHandler databaseHandler, DistrictModel districtModel) {
        ArrayList<WardModel> lisAll = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME + " ward where city_id == " + districtModel.getCity_id() +
                " and district_name == '" + districtModel.getName() + "' ";
        SQLiteDatabase myDataBase = databaseHandler.getMyDataBase();
        Cursor cursor = myDataBase.rawQuery(query, null);//selectQuery,selectedArguments

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String district_name = cursor.getString(1);
                String name = cursor.getString(2);
                WardModel model = new WardModel(id, district_name, name);
                lisAll.add(model);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        myDataBase.close();

        return lisAll;
    }

}
