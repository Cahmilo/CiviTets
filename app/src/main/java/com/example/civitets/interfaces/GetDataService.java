package com.example.civitets.interfaces;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {

    @GET("/api/v1/search/query")
    Call<JsonObject> getAllData();

    @GET("/api/v1/search/query")
    Call<JsonObject> getShearch(
            @Query("term") String term,
            @Query("limit") int limit,
            @Query("order_by") String distance,
            @Query("radio") int radio,
            @Query("user_lat") double user_lat,
            @Query("user_lng") double user_lng);
}
