package com.example.weatherapplication.geocoding

import android.content.Context
import com.example.weatherapplication.BuildConfig
import com.example.weatherapplication.service.RetrofitBuilder
import retrofit2.create

class GeocoderProvider {
    private val WEATHER_GEOCODE = "weatherGeocode"

    fun getGeocoder(context: Context): Geocoder =
        if (BuildConfig.FLAVOR_geocode == WEATHER_GEOCODE)
            WeatherGeocoder(RetrofitBuilder.retrofit.create())
        else
            AndroidGeocoder(context)
}