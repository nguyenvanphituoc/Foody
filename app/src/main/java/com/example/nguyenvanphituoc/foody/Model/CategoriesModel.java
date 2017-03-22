package com.example.nguyenvanphituoc.foody.Model;

/**
 * Created by Admin on 3/19/2017.
 */

public class CategoriesModel {
    private String name;
    private String pathImg;
    private String stt;
    public CategoriesModel(String name, String pathImg, String status) {
        this.name = name;
        this.pathImg = pathImg;
        this.stt = status;
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
}
