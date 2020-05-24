package com.example.project_esiea.data;

import com.example.project_esiea.presentation.model.RestCovidResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CovidApi {

    @GET("/summary")
    Call<RestCovidResponse> getCovidResponse();

}
