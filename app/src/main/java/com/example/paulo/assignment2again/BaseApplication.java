package com.example.paulo.assignment2again;

import android.app.Application;
import io.realm.Realm;
import io.realm.RealmConfiguration;



public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("artists.realm")
                .schemaVersion(2)
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
