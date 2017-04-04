package com.example.nguyenvanphituoc.foody.Model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nguyenvanphituoc.foody.DAO.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PhiTuocPC on 4/4/2017.
 */

public class DisplayModel {
    private int id;
    private String name;
    private String address;
    private double rating;
    private byte[] image;
    private String city_name;
    private String district_name;
    private String ward_name;
    private String service_name;
    private String category_name;
    public WardModel wardModel;
    public CategoriesModel categoriesModel;
    public ServiceModel serviceModel;
    private static final String TABLE_NAME = "foody_places";

    public DisplayModel(int id, String name, double rating, byte[] image, String city_id, String district_name, String ward_name, String service_id, String category_id) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.image = image;
        this.city_name = city_id;
        this.district_name = district_name;
        this.ward_name = ward_name;
        this.service_name = service_id;
        this.category_name = category_id;
        this.address += " " + city_name + " " + district_name + " " + ward_name;
    }

    public DisplayModel() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public String getWard_name() {
        return ward_name;
    }

    public void setWard_name(String ward_name) {
        this.ward_name = ward_name;
    }

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

    static public ArrayList<DisplayModel> getAllPlaces(DatabaseHandler databaseHandler) {
        ArrayList<DisplayModel> lisAll = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase myDataBase = databaseHandler.getMyDataBase();
        Cursor cursor = myDataBase.rawQuery(query, null);//selectQuery,selectedArguments

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                double rate = cursor.getDouble(1);
                String name = cursor.getString(2);
                byte[] img = cursor.getBlob(4);
                String district_name = cursor.getString(5);
                String city_id = cursor.getString(6);
                String ward = cursor.getString(7);
                String category = cursor.getString(8);
                String service = cursor.getString(9);
                DisplayModel model = new DisplayModel(id, name, rate, img, city_id, district_name, ward, category, service);
                lisAll.add(model);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        myDataBase.close();

        return lisAll;
    }

    static public ArrayList<DisplayModel> getAllPlaces(DatabaseHandler databaseHandler, String service, String category) {
        ArrayList<DisplayModel> lisAll = new ArrayList<>();
        String tableGet = TABLE_NAME+".";

        String query = "SELECT * FROM " + TABLE_NAME + " where " + tableGet +"service_name == '" + service +"' and "+
                tableGet+ "category_name == '" +category+ "'";
        SQLiteDatabase myDataBase = databaseHandler.getMyDataBase();
        Cursor cursor = myDataBase.rawQuery(query, null);//selectQuery,selectedArguments

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                double rate = cursor.getDouble(1);
                String name = cursor.getString(2);
                byte[] img = cursor.getBlob(4);
                String district_name = cursor.getString(5);
                String city_id = cursor.getString(6);
                String award = cursor.getString(7);
                String acategory = cursor.getString(8);
                String aservice = cursor.getString(9);
                DisplayModel model = new DisplayModel(id, name, rate, img, city_id, district_name, award, acategory, aservice);
                lisAll.add(model);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        myDataBase.close();

        return lisAll;
    }

}
