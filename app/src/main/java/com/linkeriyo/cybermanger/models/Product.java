package com.linkeriyo.cybermanger.models;

import com.linkeriyo.cybermanger.utilities.Tags;

import org.json.JSONException;
import org.json.JSONObject;

public class Product {

    private ProductType productType;
    private String name;
    private String description;

    public Product() {
        productType = new ProductType();
        name = "";
        description = "";
    }

    public Product(JSONObject json) throws JSONException {
        productType = (ProductType) json.get(Tags.PRODUCT_TYPE);
        name = json.getString(Tags.NAME);
        description = json.getString(Tags.DESCRIPTION);
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(Tags.PRODUCT_TYPE, productType);
        json.put(Tags.NAME, name);
        json.put(Tags.DESCRIPTION, description);
        return json;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
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
