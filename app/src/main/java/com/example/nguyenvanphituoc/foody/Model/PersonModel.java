package com.example.nguyenvanphituoc.foody.Model;

import java.util.Date;

/**
 * Created by PhiTuocPC on 4/19/2017.
 * nguyễn văn phi tước
 */

public class PersonModel {
    private int id;
    private String fullName;
    private String email;
    private Boolean gender;
    private Date birthday;
    private String description;
    private String phone;
    private byte[] picture;
    private String user;
    private String pass;

    public PersonModel() {

    }

    public PersonModel(int id, String fullName, String description) {
        this.id = id;
        this.fullName = fullName;
        this.description = description;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
}
