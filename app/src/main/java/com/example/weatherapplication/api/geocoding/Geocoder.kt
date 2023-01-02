package com.example.weatherapplication.api.geocoding

interface Geocoder {
    fun directGeocode(locationName: String, success: (Coordinates?) -> Unit)

    fun reverseGeocode(latitude: Double, longitude: Double, success: (Location?) -> Unit)
}