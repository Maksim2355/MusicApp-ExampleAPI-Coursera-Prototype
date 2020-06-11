package com.example.authcoursera;

import com.example.authcoursera.model.User;
import com.example.authcoursera.model.UserDao;
import com.example.authcoursera.model.converter.DataConverterFactory;
import com.example.authcoursera.system.App;
import com.google.gson.Gson;


import java.lang.reflect.Array;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;



public class ApiUtils {

    public static final List<Class<?>> NETWORK_EXCEPTION = Arrays.asList(
            UnknownHostException.class,
            SocketTimeoutException.class,
            ConnectException.class);

    private static Gson gson;
    private static AcademyApi api;


    private static OkHttpClient getBasicAuthClient(final String email, final String password)
    {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.authenticator((route, response) -> {
            String credential = Credentials.basic(email, password);
            return response.request().newBuilder().header("Authorization", credential).build();
        });
        builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        OkHttpClient client = builder.build();
        return client;
    }

    private static Retrofit getRetrofit(String email, String password) {
        if (gson == null) {
            gson = new Gson();
        }

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .client(getBasicAuthClient(email, password))
                .addConverterFactory(new DataConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    //Вызываем при авторизации для получения нового клиента
    public static AcademyApi getNewApiService(String email, String password) {
        api = getRetrofit(email, password).create(AcademyApi.class);
        return api;
    }

    //Получаем api с установленным юзером после авторизации или
    public static AcademyApi getApiService() {
        if (api == null) {
            api = getRetrofit(getUserDao().getEmail(), getUserDao().getPassword()).create(AcademyApi.class);
        }
        return api;
    }

    private static User getUserDao(){
        return App.getInstance().getDatabase().getUserDao().getAuthUser();
    }
}
