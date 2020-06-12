package com.example.authcoursera.model;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Album.class, Song.class, User.class, Comment.class}, version = 9, exportSchema = false)
public abstract class DbLocal extends RoomDatabase {
    public abstract MusicDao getMusicDao();
    public abstract UserDao getUserDao();
}
