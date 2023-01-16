package com.example.weatherapplication.geocoding

import com.example.weatherapplication.geocoding.model.Coordinates
import com.example.weatherapplication.geocoding.model.Location

interface Geocoder {
    fun directGeocode(locationName: String, success: (Coordinates?) -> Unit)

    fun reverseGeocode(latitude: Double, longitude: Double, success: (Location?) -> Unit)
}