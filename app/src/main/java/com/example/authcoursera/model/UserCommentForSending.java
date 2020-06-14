package com.example.authcoursera.model;

import androidx.room.ColumnInfo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserCommentForSending implements Serializable {

    public int getAlbumId() {
        return mAlbumId;
    }

    public void setAlbumId(int mAlbumId) {
        this.mAlbumId = mAlbumId;
    }

    public String getTextComment() {
        return mTextComment;
    }

    public void setTextComment(String mTextComment) {
        this.mTextComment = mTextComment;
    }

    public UserCommentForSending(int albumId, String textComment){
        this.mAlbumId = albumId;
        this.mTextComment = textComment;
    }

    @SerializedName("album_id")
    private int mAlbumId;

    @SerializedName("text")
    private String mTextComment;



}
