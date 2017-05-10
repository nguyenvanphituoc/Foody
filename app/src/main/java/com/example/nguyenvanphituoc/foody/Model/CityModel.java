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
}
