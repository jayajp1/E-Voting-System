package com.example.drs.resources;

import com.example.drs.Model.Admin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AdminResource {

    @POST("/rest/admin/load")
    Call<Integer> persist(@Body Admin admin);

    @POST("/rest/admin/auth/{uid}/{pwd}")
    Call<Admin> AuthenticateAdmin(@Path("uid") String uid, @Path("pwd") String pwd);

}
