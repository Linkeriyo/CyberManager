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

    public static JSONObject createSignUpJSON(final String username, final String email, final String password) {
        JSONObject json = new JSONObject();
        try {
            json.put(Tags.USERNAME, username);
            json.put(Tags.EMAIL, email);
            json.put(Tags.PASSWORD, password);
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
        return json;
    }

    public static JSONObject createCheckExtraDataJSON(final String token, final String username) {
        JSONObject json = new JSONObject();
        try {
            json.put(Tags.TOKEN, token);
            json.put(Tags.USERNAME, username);
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
        return json;
    }
}
