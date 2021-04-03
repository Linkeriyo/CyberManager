package com.linkeriyo.cybermanger.requests;

import com.linkeriyo.cybermanger.utilities.Tags;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONTemplates {

    public static JSONObject createLoginJSON(final String username, final String password) {
        JSONObject json = new JSONObject();
        try {
            json.put(Tags.USERNAME, username);
            json.put(Tags.PASSWORD, password);
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
        return json;
    }

}
