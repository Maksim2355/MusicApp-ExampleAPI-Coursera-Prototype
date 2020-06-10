package com.example.authcoursera.system;

import android.app.Application;

import androidx.room.Room;

import com.example.authcoursera.model.DbLocal;

public class App extends Application {
    private static App instance;

    private DbLocal dbLocal;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        dbLocal = Room.databaseBuilder(this, DbLocal.class, "database")
                                        .build();
    }

    public static App getInstance(){
        return instance;
    }

    public DbLocal getDatabase(){
        return dbLocal;
    }

}
