package com.example.authcoursera.model;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {

    @Insert
    void loginInAccount(User user);

    @Query("SELECT * FROM USER")
    User getAuthUser();

    @Query("DELETE FROM USER")
    void loginOutAccount();

}
