package com.example.nguyenvanphituoc.foody.Model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nguyenvanphituoc.foody.DAO.DatabaseHandler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PhiTuocPC on 4/3/2017.
 */

public class DistrictModel implements Serializable {
    private int city_id;
    private String name;
    private static final String TABLE_NAME = "foody_district";
    public List<WardModel> wardModels = new ArrayList<>();
    public CityModel  cityModel;
    public DistrictModel(int city_id, String name) {
        this.city_id = city_id;
        this.name = name;
    }

    public DistrictModel(){}

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    static public ArrayList<DistrictModel> getAllDistrict(DatabaseHandler databaseHandler) {
        ArrayList<DistrictModel> lisAll = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase myDataBase = databaseHandler.getMyDataBase();
        Cursor cursor = myDataBase.rawQuery(query, null);//selectQuery,selectedArguments

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                DistrictModel model = new DistrictModel(id, name);
                lisAll.add(model);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        myDataBase.close();
        return lisAll;
    }

    static public ArrayList<DistrictModel> getAllDistrictByCity(DatabaseHandler databaseHandler, CityModel cityModel){
        ArrayList<DistrictModel> lisAll = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME + " district WHERE city_id == " + cityModel.getId();
        SQLiteDatabase myDataBase = databaseHandler.getMyDataBase();
        Cursor cursor = myDataBase.rawQuery(query, null);//selectQuery,selectedArguments

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                DistrictModel model = new DistrictModel(id, name);
                lisAll.add(model);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        myDataBase.close();

        return lisAll;
    }
}
