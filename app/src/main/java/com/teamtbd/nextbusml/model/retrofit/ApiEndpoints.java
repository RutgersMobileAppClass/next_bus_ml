package com.teamtbd.nextbusml.model.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiEndpoints {
    // Request method and URL specified in the annotation
    // Callback for the parsed response is the last parameter

    @GET("{bus}")
    Call<List<RetrofitStop>> getStops(@Path("bus") String bus);

}
