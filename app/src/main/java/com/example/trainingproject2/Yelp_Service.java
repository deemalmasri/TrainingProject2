package com.example.trainingproject2;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface Yelp_Service {
    String BASE_URL = "https://api.yelp.com/v3/businesses/";
    @Headers({ "Content-Type: application/json;charset=UTF-8"})

    @GET("search?location=Houston&limit=20")
    Call<Bobj> getResturents();

    @GET("search?location=Houston&price=1,2,3&limit=20")
    Call<Bobj> getResturentswhithterm(@Query("term") String term);



}
