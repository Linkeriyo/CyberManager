package com.linkeriyo.cybermanger;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Locale;

@SuppressLint("StaticFieldLeak")
public class CyberManager extends Application {

    private static Context context;
    public static SimpleDateFormat sdf;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
    }

    public static Context getContext() {
        return context;
    }
}
