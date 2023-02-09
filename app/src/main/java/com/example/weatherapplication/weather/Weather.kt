package com.example.weatherapplication.weather

import com.example.weatherapplication.weather.model.WeatherData
import io.reactivex.Single

class Weather(private val service: WeatherApi) {
    fun getWeather(lat: Double, lon: Double): Single<WeatherData> =
        service.getWeatherByCoordinates(lat, lon)
}