package com.example.weatherapplication.api.weatherAPI

import com.google.gson.annotations.SerializedName

data class Main(@SerializedName("temp") val temperature: Double)

data class WeatherData(val main: Main)