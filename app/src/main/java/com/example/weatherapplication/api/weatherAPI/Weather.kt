package com.example.weatherapplication.api.weatherAPI

import retrofit2.*

class Weather(private val service: WeatherAPI) {
    fun getWeather(lat: Double, lon: Double, success: (WeatherData) -> Unit) {
        val call = service.getWeatherByCoordinates(lat, lon)
        call.enqueue(object : Callback<WeatherData> {
            override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                success(requireNotNull(response.body()))
            }

            override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}