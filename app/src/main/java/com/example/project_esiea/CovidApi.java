package com.example.project_esiea;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CovidApi {

    @GET("/summary")
    Call<RestCovidResponse> getCovidResponse();

}
