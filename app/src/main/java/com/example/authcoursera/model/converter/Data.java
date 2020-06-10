package com.example.authcoursera.model.converter;

import com.google.gson.annotations.SerializedName;

/**
 * Created by marat.taychinov
 */

public class Data<T> {
    @SerializedName("data")
    public T response;
}
