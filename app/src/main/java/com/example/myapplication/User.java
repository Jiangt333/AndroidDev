package com.example.myapplication;

public class User {
    private String login;
    private String name;
    private String realpassword;
    private String phone;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
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