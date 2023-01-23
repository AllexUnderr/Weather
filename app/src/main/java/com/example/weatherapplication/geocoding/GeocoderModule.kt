package com.example.weatherapplication.geocoding

import android.content.Context
import com.example.weatherapplication.BuildConfig
import com.example.weatherapplication.service.RetrofitBuilder
import dagger.Module
import dagger.Provides
import retrofit2.create

@Module
class GeocoderModule {
    private val WEATHER_GEOCODE = "weatherGeocode"

    @Provides
    fun getGeocoder(context: Context): Geocoder =
        if (BuildConfig.FLAVOR_geocode == WEATHER_GEOCODE)
            WeatherGeocoder(RetrofitBuilder.retrofit.create())
        else
            AndroidGeocoder(context)
}