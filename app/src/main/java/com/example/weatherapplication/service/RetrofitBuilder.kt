package com.example.weatherapplication.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val URL = "https://api.openweathermap.org/"

    const val API_KEY = "dc0b743045342ccf1c13087fef9fe0cb"

    val retrofit: Retrofit = Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(URL)
        .build()
}