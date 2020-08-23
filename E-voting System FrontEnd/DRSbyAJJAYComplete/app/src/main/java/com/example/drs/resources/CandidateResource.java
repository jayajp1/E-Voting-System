package com.example.drs.resources;

import com.example.drs.Model.Candidate;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CandidateResource
{
    @GET("/rest/candidate/all")
    Call<List<Candidate>> GetCandidate();

    @POST("/rest/candidate/load")
    Call<Boolean> persist(@Body Candidate candidate);

    @POST("/rest/candidate/select/{city}")
    Call<List<Candidate>> selectbycity (@Path("city") String city);

}
