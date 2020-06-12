package com.example.authcoursera.model;

import androidx.room.ColumnInfo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserCommentForSending implements Serializable {

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

    @SerializedName("album_id")
    private String mAlbumId;

    @SerializedName("text")
    private String mTextComment;



}
