package com.example.geekyadarsh.stats;

import android.app.Application;

import com.firebase.client.Firebase;

public class persist extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
    }
}
