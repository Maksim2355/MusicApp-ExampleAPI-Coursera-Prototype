package com.example.authcoursera.model;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MusicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAlbum(Album album);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAlbums(List<Album> albumsList);

    @Update
    void updateAlbum(Album album);

    @Delete
    void deleteAlbum(Album album);

    @Query("SELECT * FROM Album")
    List<Album> getAlbums();

    @Query("SELECT * FROM Album WHERE id = :id")
    Album getAlbumById(int id);

    @Query("DELETE FROM ALBUM WHERE ID = :albumId")
    void deleteAlbum(int albumId);


    @Transaction
    @Query("SELECT id, name FROM ALBUM WHERE id = :id")
    AlbumAndSongs getAlbumsWithSongs(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addSongs(List<Song> songs);

    @Query("SELECT * FROM Song")
    List<Song> getSongs();

    @Query("SELECT * FROM SONG WHERE id = :id")
    Song getSong(int id);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addComments(List<Comment> comment);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addComment(Comment comment);

    @Query("SELECT * FROM COMMENT")
    List<Comment> getComments();

    @Query("SELECT * FROM COMMENT WHERE ALBUM_ID = :albumId")
    List<Comment> getCommentsByAlbum(int albumId);
}
