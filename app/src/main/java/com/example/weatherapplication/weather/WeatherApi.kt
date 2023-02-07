package com.example.weatherapplication.weather

import com.example.weatherapplication.service.RetrofitBuilder
import com.example.weatherapplication.weather.model.WeatherData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("data/2.5/weather")
    fun getWeatherByCoordinates(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") unit: String = "metric",
        @Query("appid") APIKey: String = RetrofitBuilder.API_KEY,
    ): Single<WeatherData>
}