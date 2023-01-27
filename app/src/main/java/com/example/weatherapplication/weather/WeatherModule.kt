package com.example.weatherapplication.weather

import com.example.weatherapplication.geocoding.Geocoder
import com.example.weatherapplication.history.History
import dagger.Module
import dagger.Provides

@Module
class WeatherModule {
    @Provides
    fun provideWeatherViewModel(geocoder: Geocoder, history: History) = WeatherViewModel(geocoder, history)
}