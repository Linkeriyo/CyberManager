package com.linkeriyo.cybermanger.models;

import com.linkeriyo.cybermanger.utilities.Tags;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    private String pk;
    private String username;
    private String email;
    private String name;
    private String surname;
    private String telephoneNumber;
    private String address;
    private String city;
    private String province;
    private String zipCode;

    public User() {
        pk = "";
        username = "";
        email = "";
        name = "";
        surname = "";
        telephoneNumber = "";
        address = "";
        city = "";
        province = "";
        zipCode = "";
    }

    public User(JSONObject json) throws JSONException {
        pk = json.getString(Tags.PK);
        username = json.getString(Tags.USERNAME);
        email = json.getString(Tags.EMAIL);
        name = json.getString(Tags.NAME);
        surname = json.getString(Tags.SURNAME);
        telephoneNumber = json.getString(Tags.TELEPHONE_NUMBER);
        address = json.getString(Tags.ADDRESS);
        city = json.getString(Tags.CITY);
        province = json.getString(Tags.PROVINCE);
        zipCode = json.getString(Tags.ZIP_CODE);
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(Tags.PK, pk);
        json.put(Tags.USERNAME, username);
        json.put(Tags.NAME, name);
        json.put(Tags.EMAIL, email);
        json.put(Tags.SURNAME, surname);
        json.put(Tags.TELEPHONE_NUMBER, telephoneNumber);
        json.put(Tags.ADDRESS, address);
        json.put(Tags.CITY, city);
        json.put(Tags.PROVINCE, province);
        json.put(Tags.ZIP_CODE, zipCode);
        return json;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
