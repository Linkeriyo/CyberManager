package com.linkeriyo.cybermanger.models;

import com.linkeriyo.cybermanger.utilities.Tags;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductType {

    private String name;
    private String description;

    public ProductType() {
        name = "";
        description = "";
    }

    public ProductType(JSONObject json) throws JSONException {
        name = json.getString(Tags.NAME);
        description = json.getString(Tags.DESCRIPTION);
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(Tags.NAME, name);
        json.put(Tags.DESCRIPTION, description);
        return json;
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
