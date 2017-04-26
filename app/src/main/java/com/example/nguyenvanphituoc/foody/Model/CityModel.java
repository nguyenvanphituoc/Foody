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
 * nguyễn văn phi tước
 */

public class CityModel implements Serializable {
    private int id;
    private String name;
    private static final String TABLE_NAME = "foody_city";
    public List<DistrictModel> districtModels = new ArrayList<>();
    public CityModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public CityModel(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    static public ArrayList<CityModel> getAllCity(DatabaseHandler databaseHandler) {
        ArrayList<CityModel> lisAll = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase myDataBase = databaseHandler.getMyDataBase();
        Cursor cursor = myDataBase.rawQuery(query, null);//selectQuery,selectedArguments

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                CityModel model = new CityModel(id, name);
                lisAll.add(model);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        myDataBase.close();

        return lisAll;
    }

    static public CityModel getCityById(DatabaseHandler databaseHandler, int cityId){
        CityModel model = new CityModel() ;
        ArrayList<CityModel> listCity = CityModel.getAllCity(databaseHandler);
        for (CityModel city: listCity) {
            if(city.id == cityId){
                model = city;
                break;
            }
        }
        return model;
    }
}
