package com.linkeriyo.cybermanger.models;

import com.linkeriyo.cybermanger.utilities.Tags;

import org.json.JSONException;
import org.json.JSONObject;

public class CyberCafe {

    private String pk;
    private String name;
    private String description;
    private String phono;
    private String address;
    private String city;
    private String province;
    private String email;
    private String zipCode;

    public CyberCafe() {
        pk = "";
        name = "";
        description = "";
        phono = "";
        address = "";
        city = "";
        province = "";
        email = "";
        zipCode = "";
    }

    public CyberCafe(JSONObject json) throws JSONException {
        pk = json.getString(Tags.PK);
        name = json.getString(Tags.NAME);
        description = json.getString(Tags.DESCRIPTION);
        phono = json.getString(Tags.PHONO);
        address = json.getString(Tags.ADDRESS);
        city = json.getString(Tags.CITY);
        province = json.getString(Tags.PROVINCE);
        email = json.getString(Tags.EMAIL);
        zipCode = json.getString(Tags.ZIP_CODE);
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(Tags.PK, pk);
        json.put(Tags.NAME, name);
        json.put(Tags.DESCRIPTION, description);
        json.put(Tags.PHONO, phono);
        json.put(Tags.ADDRESS, address);
        json.put(Tags.CITY, city);
        json.put(Tags.PROVINCE, province);
        json.put(Tags.EMAIL, email);
        json.put(Tags.ZIP_CODE, zipCode);
        return json;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhono() {
        return phono;
    }

    public void setPhono(String phono) {
        this.phono = phono;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
