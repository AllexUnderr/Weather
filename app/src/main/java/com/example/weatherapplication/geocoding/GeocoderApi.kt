package com.example.weatherapplication.geocoding

import com.example.weatherapplication.service.RetrofitBuilder
import com.example.weatherapplication.geocoding.model.Coordinates
import com.example.weatherapplication.geocoding.model.Location
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocoderApi {
    @GET("geo/1.0/direct")
    fun directGeocode(
        @Query("q") objectName: String,
        @Query("appid") APIKey: String = RetrofitBuilder.API_KEY,
    ): Single<List<Coordinates>>

    @GET("geo/1.0/reverse")
    fun reverseGeocode(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") APIKey: String = RetrofitBuilder.API_KEY,
    ): Single<List<Location>>
}