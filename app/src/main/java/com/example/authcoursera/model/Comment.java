package com.example.authcoursera.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity
public class Comment implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private int mId;

    @ColumnInfo(name = "album_id")
    @SerializedName("album_id")
    private String mAlbumId;

    @ColumnInfo(name = "text")
    @SerializedName("text")
    private String mTextComment;

    @ColumnInfo(name = "author")
    @SerializedName("author")
    private String mAuthor;

    @ColumnInfo(name = "timestamp")
    @SerializedName("timestamp")
    private String mTimestamp;


    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getAlbumId() {
        return mAlbumId;
    }

    public void setAlbumId(String mAlbumId) {
        this.mAlbumId = mAlbumId;
    }

    public String getTextComment() {
        return mTextComment;
    }

    public void setTextComment(String mTextComment) {
        this.mTextComment = mTextComment;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getTimestamp() {
        return mTimestamp;
    }

    public void setTimestamp(String mTimestamp) {
        this.mTimestamp = mTimestamp;
    }
}
