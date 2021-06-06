package com.linkeriyo.cybermanger.models;

import com.linkeriyo.cybermanger.utilities.Tags;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentMethod {

    private String pk;
    private String name;
    private String description;


    public PaymentMethod(JSONObject json) throws JSONException {
        pk = json.getString(Tags.PK);
        name = json.getString(Tags.NAME);
        description = json.getString(Tags.DESCRIPTION);
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(Tags.PK, pk);
        json.put(Tags.NAME, name);
        json.put(Tags.DESCRIPTION, description);
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
}
