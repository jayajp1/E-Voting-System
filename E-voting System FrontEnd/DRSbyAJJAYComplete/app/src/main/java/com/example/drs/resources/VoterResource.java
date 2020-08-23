package com.example.drs.resources;

import com.example.drs.Model.Voter;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface VoterResource {

    @POST("/rest/voter/auth/{uid}/{pwd}")
    Call<Voter> AuthenticateVoter(@Path("uid") String uid, @Path("pwd") String pwd);

    @POST("/rest/voter/auth/{uid}/{pwd}/{adr}")
    Call<Voter> AuthenticateVoterQR(@Path("uid") String uid, @Path("pwd") String pwd,@Path("adr") int adr);

    @POST("/rest/voter/update/{uid}/{pwd}/{mob}")
    Call<Boolean> updatevoter(@Path("uid") String uid, @Path("pwd") String pwd,@Path("mob") String mob);

    @POST("/rest/voter/load")
    Call<Integer> persist(@Body Voter voter);

    @POST("/rest/voter/updatevt/{uid}/{vdone}")
    Call<Boolean> updatevdone(@Path("uid") String uid, @Path("vdone") String vdone);



}
