package com.example.drs.resources;

import com.example.drs.Model.Count;
import com.example.drs.Model.Vote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface VoteResource
{
    @POST("/rest/vote/load/{candidate}/{party}")
    Call<Boolean> persist(@Path("candidate") String candidate, @Path("party") String party);

    @POST("/rest/vote/count")
    Call<List<Count>> votecount();
}
