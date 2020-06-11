package com.example.authcoursera.system;

import android.app.Application;

import androidx.room.Room;

import com.example.authcoursera.model.DbLocal;

public class App extends Application {

    public static App instance;

    private DbLocal database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, DbLocal.class, "database")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public DbLocal getDatabase() {
        return database;
    }

}
