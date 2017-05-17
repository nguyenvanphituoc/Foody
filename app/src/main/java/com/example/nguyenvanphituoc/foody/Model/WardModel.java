package com.example.nguyenvanphituoc.foody.Model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nguyenvanphituoc.foody.DAO.DatabaseHandler;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by PhiTuocPC on 4/3/2017.
 * nguyễn văn phi tước
 */

public class WardModel implements Serializable, KvmSerializable {
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

    public WardModel(int city_id, String city) {
        this.id = city_id;
        this.city = city;
    }

    public WardModel(int city_id, String city, String district_name, String name) {
        this.id = city_id;
        this.city = city;
        this.district = district_name;
        this.street = name;
    }

    public WardModel(){}

    @Override
    public Object getProperty(int index) {
        switch (index) {
            case 0:
               return this.id;
            case 1:
                return this.city;
            case 2:
                return this.district;
            case 3:
                return this.street;
            default:
                return null;
        }
    }

    @Override
    public int getPropertyCount() {
        return 4;
    }

    @Override
    public void getPropertyInfo(int index, Hashtable properties, PropertyInfo info) {
        switch (index) {
            case 0:
                info.setName("id");
                info.setValue(this.getId());
                info.setType(Integer.TYPE);
            case 1:
                info.setName("city");
                info.setValue(this.getCity());
                info.setType(String.class);
            case 2:
                info.setName("district");
                info.setValue(this.getDistrict());
                info.setType(String.class);
            case 3:
                info.setName("street");
                info.setValue(this.getStreet());
                info.setType(String.class);
            default:
                break;
        }
    }

    @Override
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
