package com.vitalgroundz.weatherapp;

import com.vitalgroundz.weatherapp.data.Forecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jrperiod on 5/5/16.
 */
public interface OpenWeatherMapService {

    @GET("data/2.5/weather?")
    Call<Forecast> currentForecastByZip(@Query("zip") String zip,
                                        @Query("units") String units,
                                        @Query("apikey") String apiKey);
}
