package com.example.authcoursera;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.authcoursera.model.converter.DataConverterFactory;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;



public class ApiUtils {

    private static OkHttpClient client;
    private static Retrofit retrofit;
    private static Gson gson;
    private static AcademyApi api;




    private static OkHttpClient getBasicAuthClient(final String email, final String password) {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.authenticator((route, response) -> {
            String credential = Credentials.basic(email, password);
            return response.request().newBuilder().header("Authorization", credential).build();
        });
        builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        client = builder.build();
        return client;
    }

    private static Retrofit getRetrofit(String email, String password) {
        if (gson == null) {
            gson = new Gson();
        }
        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .client(getBasicAuthClient(email, password))
                .addConverterFactory(new DataConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit;
    }

    //Вызываем при авторизации для получения нового клиента
    public static AcademyApi getNewApiService(String email, String password) {
        return getRetrofit(email, password).create(AcademyApi.class);
    }

    //Получаем api с установленным юзером после авторизации или
    public static AcademyApi getApiService() {
        if (api == null) {
            api = getRetrofit("", "").create(AcademyApi.class);
        }
        return api;
    }
}
