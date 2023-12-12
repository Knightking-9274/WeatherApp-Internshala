package com.example.weatherapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface FetchAPI {

    @GET("weather")
    Call<Test> getWeather(@Query("q") String cityName,@Query("appid") String apikey);

}
