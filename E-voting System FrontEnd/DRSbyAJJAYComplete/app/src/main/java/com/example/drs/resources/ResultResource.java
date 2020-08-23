package com.example.drs.resources;

import com.example.drs.Model.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ResultResource {

    @POST(value = "/rest/result/update")
    Call<Boolean> Updateresult(@Body Result result);

    @GET(value ="/rest/result/all" )
    Call<List<Result>> getAll();
}
