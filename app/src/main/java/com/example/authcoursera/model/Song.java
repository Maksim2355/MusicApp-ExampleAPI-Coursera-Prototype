package com.example.authcoursera.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;


@Entity
public class Song implements Serializable {

    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = "id")
    private int mId;

    @SerializedName("name")
    @ColumnInfo(name = "name")
    private String mName;

    @SerializedName("duration")
    @ColumnInfo(name = "duration")
    private String mDuration;

    @ColumnInfo(name = "album_id")
    private transient int mIdAlbum;


    public int getIdAlbum() { return mIdAlbum; }

    public void setIdAlbum(int mIdAlbum) { this.mIdAlbum = mIdAlbum; }

    public int getId() { return mId; }

    public void setId(int id) { mId = id; }

    public String getName() { return mName; }

    public void setName(String name) { mName = name; }

    public String getDuration() { return mDuration; }

    public void setDuration(String duration) { mDuration = duration; }

}
