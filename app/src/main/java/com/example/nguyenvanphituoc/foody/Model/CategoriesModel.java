package com.example.nguyenvanphituoc.foody.Model;

import org.ksoap2.serialization.PropertyInfo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Admin on 3/19/2017.
 * nguyễn văn phi tước
 */

public class CategoriesModel implements Serializable {
    private int id;
    private String name;
    private byte[] blobImg;
    private String stt;
    public ArrayList<DistrictModel> districtModels = new ArrayList<>();
    private static final String TABLE_NAME = "foody_category";

    public CategoriesModel() {
    }

    public CategoriesModel(String name, byte[] blobImg, String status) {
        this.name = name;
        this.blobImg = blobImg;
        this.stt = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getStt() {
        return stt;
    }

    public void setStt(String stt) {
        this.stt = stt;
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
            case "name":
                pi.setName("name");
                pi.setValue(this.getName());
                pi.setType(this.getName().getClass());
                return pi;
            case "image":
                pi.setName("image");
                pi.setValue(this.getBlobImg());
                pi.setType(Byte.TYPE);
                return pi;
            case "status":
                pi.setName("status");
                pi.setValue(this.getStt());
                pi.setType(this.getStt().getClass());
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
            case "name":
                name = value.toString();
                break;
            case "image":
                blobImg = (byte[]) value;
                break;
            case "status":
                stt = value.toString();
                break;
            default:
                break;
        }
    }
}
