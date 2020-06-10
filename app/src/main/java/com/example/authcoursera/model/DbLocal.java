package com.example.authcoursera.model;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Album.class, Song.class}, version = 1)
public abstract class DbLocal extends RoomDatabase {
    public abstract MusicDao getMusicDao();
}
