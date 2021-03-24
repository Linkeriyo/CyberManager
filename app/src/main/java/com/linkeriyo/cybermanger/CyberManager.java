package com.linkeriyo.cybermanger;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

@SuppressLint("StaticFieldLeak")
public class CyberManager extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        return context;
    }
}
