package com.example.weatherapplication.geocoding

import com.example.weatherapplication.geocoding.model.Coordinates
import com.example.weatherapplication.geocoding.model.Location
import io.reactivex.Single

interface Geocoder {
    fun directGeocode(locationName: String): Single<List<Coordinates>>

    fun reverseGeocode(latitude: Double, longitude: Double): Single<List<Location>>
}