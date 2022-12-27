package com.example.weatherapplication.api.geocoding

import com.example.weatherapplication.api.RetrofitBuilder
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocoderAPI {
    @GET("geo/1.0/direct")
    fun directGeocode(
        @Query("q") objectName: String,
        @Query("appid") APIKey: String = RetrofitBuilder.API_KEY,
    ): Call<List<Coordinates>>

    @GET("geo/1.0/reverse")
    fun reverseGeocode(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") APIKey: String = RetrofitBuilder.API_KEY,
    ): Call<List<Location>>
}