package com.braun1792.comp259finalproject;

/**
 * Created by braun1792 on 2/16/2017.
 */
public class Contact {

    private int id;
    private String name;
    private String phone;
    private String address;
    private String email;

    Contact(){}

    Contact(int i, String n, String p, String e, String a){
        setId(i);
        setName(n);
        setPhone(p);
        setEmail(e);
        setAddress(a);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int i){
        this.id = i;
    }

    public int getId(){
        return id;
    }
}
