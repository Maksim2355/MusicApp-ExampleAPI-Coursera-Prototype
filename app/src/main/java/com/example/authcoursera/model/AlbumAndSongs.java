package com.example.authcoursera.model;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;
import androidx.room.Relation;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AlbumAndSongs implements Serializable {

    @SerializedName("id")
    @ColumnInfo(name = "id")
    private int mId;

    @SerializedName("name")
    @ColumnInfo(name = "name")
    private String mName;


    @Ignore
    @Relation(parentColumn = "id", entityColumn = "album_id")
    private List<Song> songs;





    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
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
