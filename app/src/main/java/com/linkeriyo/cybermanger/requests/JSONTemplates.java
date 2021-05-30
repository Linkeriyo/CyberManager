package com.linkeriyo.cybermanger.requests;

import com.linkeriyo.cybermanger.models.CyberCafe;
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

    public static JSONObject createSetExtraDataJSON(final String token, final String username,
                                                    final String name, final String surname,
                                                    final String phono, final String address,
                                                    final String city, final String province,
                                                    final String zipCode
    ) {
        JSONObject json = new JSONObject();
        try {
            json.put(Tags.TOKEN, token);
            json.put(Tags.USERNAME, username);
            json.put(Tags.NAME, name);
            json.put(Tags.SURNAME, surname);
            json.put(Tags.PHONO, phono);
            json.put(Tags.ADDRESS, address);
            json.put(Tags.CITY, city);
            json.put(Tags.PROVINCE, province);
            json.put(Tags.ZIP_CODE, zipCode);
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
        return json;
    }

    public static JSONObject createAddCybercafeToUserJSON(String token, CyberCafe cyberCafe) {
        JSONObject json = new JSONObject();
        try {
            json.put(Tags.TOKEN, token);
            json.put(Tags.BUSINESS, cyberCafe.toJSON());
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
        return json;
    }
}
