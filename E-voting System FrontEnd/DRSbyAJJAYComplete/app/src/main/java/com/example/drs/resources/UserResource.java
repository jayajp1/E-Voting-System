package com.example.drs.resources;

import com.example.drs.Model.Users;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserResource {

    @POST("/rest/users/authuser/{uid}/{pwd}")
    Call<Users> authenticate(@Path("uid") String uid, @Path("pwd") String pwd);
}
