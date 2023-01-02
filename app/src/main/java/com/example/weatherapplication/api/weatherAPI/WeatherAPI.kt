package com.example.weatherapplication.api.weatherAPI

import com.example.weatherapplication.api.RetrofitBuilder
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Query

interface WeatherAPI {
    @GET("data/2.5/weather")
    fun getWeatherByCoordinates(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") unit: String = "metric",
        @Query("appid") APIKey: String = RetrofitBuilder.API_KEY,
    ): Call<WeatherData>
}