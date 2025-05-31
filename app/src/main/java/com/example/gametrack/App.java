package com.example.gametrack;

import android.app.Application;
import android.content.Context;

import com.example.gametrack.data.storage.SecurePreferences;

public class App extends Application {
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        SecurePreferences.init(this);
    }

    public static App getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }
}
