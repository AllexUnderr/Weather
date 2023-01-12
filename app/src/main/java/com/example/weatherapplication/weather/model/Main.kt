package com.example.weatherapplication.weather.model

import com.google.gson.annotations.SerializedName

data class Main(@SerializedName("temp") val temperature: Double)