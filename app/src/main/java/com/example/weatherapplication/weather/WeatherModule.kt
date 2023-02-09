package com.example.weatherapplication.weather

import com.example.weatherapplication.geocoding.Geocoder
import com.example.weatherapplication.history.History
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module
class WeatherModule {
    @Provides
    fun provideWeatherViewModel(geocoder: Geocoder, history: History, weather: Weather) =
        WeatherViewModel(geocoder, history, weather)

    @Provides
    fun provideWeather(retrofit: Retrofit) = Weather(retrofit.create())
}