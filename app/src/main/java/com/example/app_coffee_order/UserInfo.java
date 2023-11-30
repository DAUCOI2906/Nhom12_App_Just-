package com.example.app_coffee_order;

public class UserInfo {
    private String Name;
    private String Phone;
    private String Email;
    private String Password;
    private String Address;

    public UserInfo() {}

    public UserInfo(String name, String phone, String email, String password, String address) {
        Name = name;
        Phone = phone;
        Email = email;
        Password = password;
        Address = address;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
