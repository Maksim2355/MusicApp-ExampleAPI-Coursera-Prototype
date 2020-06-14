package com.example.authcoursera;


import com.example.authcoursera.model.Album;
import com.example.authcoursera.model.AlbumAndSongs;
import com.example.authcoursera.model.Comment;
import com.example.authcoursera.model.User;
import com.example.authcoursera.model.UserCommentForSending;
import com.google.gson.JsonObject;


import java.util.List;
import java.util.Observable;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface AcademyApi {

    @POST("registration")
    Completable registration(@Body User user);

    @GET("user")
    Single<User> getUserInfo();

    @GET("albums")
    Single<List<Album>> getAlbumsList();


    @GET("albums/{id}")
    Single<AlbumAndSongs> getAlbumById(@Path("id") int id);

    @GET("albums/{id}/comments")
    Single<List<Comment>> getCommentsByAlbum(@Path("id") int id);

    @POST("comments")
    Completable postComment(@Body UserCommentForSending comment);





}
