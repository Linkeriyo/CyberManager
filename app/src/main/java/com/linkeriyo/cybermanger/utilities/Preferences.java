package com.linkeriyo.cybermanger.utilities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.linkeriyo.cybermanger.CyberManager;

public class Preferences {

    @SuppressLint("StaticFieldLeak")
    private static final Context context = CyberManager.getContext();

    public static SharedPreferences getSharedPreferences(){
        return context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
    }

    public static void setString(String key, String value){
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key,value);
        editor.apply();
    }

    public static String getString(String key){
        return getSharedPreferences().getString(key, "");
    }

    public static void setInt(String key, int value){
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt(key,value);
        editor.apply();
    }

    public static int getInt(String key){
        return getSharedPreferences().getInt(key,0);
    }

    public static void setBoolean(String key, boolean value){
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(key,value);
        editor.apply();
    }

    public static Boolean getBoolean(String key){
        return getSharedPreferences().getBoolean(key, false);
    }

    public static void setToken(String token){
        setString(Tags.TOKEN, token);
    }

    public static String getToken(){
        return getSharedPreferences().getString(Tags.TOKEN, null);
    }
}
