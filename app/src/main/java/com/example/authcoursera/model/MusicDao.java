package com.example.authcoursera.model;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Single;

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

    @Query("SELECT * FROM ALBUM WHERE id = :id")
    Single<AlbumAndSongs> getAlbumsAndSongs(int id);

}
