package com.example.drs;

import com.example.drs.Model.Headlines;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {


    @GET("top-headlines")
    Call<Headlines> getHeadlines(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );

    @GET("everything")
    Call<Headlines> getSpecificData(
            @Query("country") String query,
            @Query("apiKey") String apiKey
    );



}
