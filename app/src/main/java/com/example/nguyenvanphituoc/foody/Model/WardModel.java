package com.example.nguyenvanphituoc.foody.Model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nguyenvanphituoc.foody.DAO.DatabaseHandler;

import org.ksoap2.serialization.PropertyInfo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by PhiTuocPC on 4/3/2017.
 * nguyễn văn phi tước
 */

public class WardModel implements Serializable {
    private int id;
    private String city;
    private String district;
    private String street;
    private static final String TABLE_NAME = "foody_ward";
    public DistrictModel districtModel;
    public ArrayList<DistrictModel> districtModels = new ArrayList<>();
    public WardModel(int city_id, String district_name, String name) {
        this.id = city_id;
        this.district = district_name;
        this.street = name;
    }

    public WardModel(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
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

//    <id>31</id>
//    <city>TP Hồ Chí Minh</city>
//    <district>Bình Chánh</district>
//    <street>Đinh Bộ Lĩnh</street>

    public void setProperty(int index, Object value) {
        switch (index) {
            case 0:
                id = Integer.parseInt(value.toString());
                break;
            case 1:
                city = value.toString();
                break;
            case 2:
                district = value.toString();
                break;
            case 3:
                street = value.toString();
                break;
            default:
                break;
        }
    }

    public Object getPropertyInfo(String index) {
        String tmp = index.trim().toLowerCase();
        PropertyInfo pi = new PropertyInfo();

        switch (tmp) {
            case "id":
                pi.setName("id");
                pi.setValue(this.getId());
                pi.setType(Integer.TYPE);
                return pi;
            case "city":
                pi.setName("name");
                pi.setValue(this.getCity());
                pi.setType(this.getCity().getClass());
                return pi;
            case "district":
                pi.setName("image");
                pi.setValue(this.getDistrict());
                pi.setType(this.getDistrict().getClass());
                return pi;
            case "street":
                pi.setName("status");
                pi.setValue(this.getStreet());
                pi.setType(this.getStreet().getClass());
                return pi;
            default:
                return null;
        }
    }

    public void setProperty(String index, Object value) {
        String tmp = index.trim().toLowerCase();
        switch (tmp) {
            case "id":
                id = Integer.parseInt(value.toString());
                break;
            case "city":
                city = value.toString();
                break;
            case "district":
                district = value.toString();
                break;
            case "street":
                street = value.toString();
                break;
            default:
                break;
        }
    }

}
