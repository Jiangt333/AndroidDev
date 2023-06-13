package com.example.myapplication.entity;

import java.io.Serializable;

public class User implements Serializable {

    private int Id;
    private String name;
    // Getters and setters
    private String realpassword;
    private String phone;
    public int getId() {
        return Id;
    }
    public void setId(int id) {
        Id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getRealpassword() {
        return realpassword;
    }
    public void setRealpassword(String realpassword) {
        this.realpassword = realpassword;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

}