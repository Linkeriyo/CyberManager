package com.linkeriyo.cybermanger.models;

import com.linkeriyo.cybermanger.utilities.Tags;

import org.json.JSONException;
import org.json.JSONObject;

public class Computer {

    private String pk;
    private String macAddress;
    private String ipAddress;
    private String alias;

    public Computer() {
        pk = "";
        macAddress = "";
        ipAddress = "";
        alias = "";
    }

    public Computer(JSONObject json) throws JSONException {
        pk = json.getString(Tags.PK);
        macAddress = json.getString(Tags.MAC_ADDRESS);
        ipAddress = json.getString(Tags.IP_ADDRESS);
        alias = json.getString(Tags.ALIAS);
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(Tags.PK, pk);
        json.put(Tags.MAC_ADDRESS, macAddress);
        json.put(Tags.IP_ADDRESS, ipAddress);
        json.put(Tags.ALIAS, alias);
        return json;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
