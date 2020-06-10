package com.example.authcoursera;


import com.example.authcoursera.model.Album;
import com.example.authcoursera.model.AlbumAndSongs;
import com.example.authcoursera.model.User;
import com.google.gson.JsonObject;


import java.util.List;

import io.reactivex.Completable;
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
    Single<List<Album>> getAlbum();

    @GET("albums/{id}")
    Single<AlbumAndSongs> getSongsFromAlbums(@Path("id") int id);

}
