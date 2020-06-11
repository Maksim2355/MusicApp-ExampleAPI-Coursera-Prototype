package com.example.authcoursera.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Relation;

import java.io.Serializable;
import java.util.List;

public class AlbumAndSongs implements Serializable {

    private int id;

    @ColumnInfo(name = "name")
    private String mName;


    @Relation(parentColumn = "id", entityColumn = "album_id")
    private List<Song> songs;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }



}
