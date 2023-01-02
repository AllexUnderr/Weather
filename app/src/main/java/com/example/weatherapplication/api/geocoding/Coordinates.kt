package com.example.weatherapplication.api.geocoding

import com.google.gson.annotations.SerializedName

data class Coordinates(
    @SerializedName("lat") val latitude: Double,
    @SerializedName("lon") val longitude: Double
)