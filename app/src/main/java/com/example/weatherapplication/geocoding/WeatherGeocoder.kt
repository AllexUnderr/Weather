package com.example.weatherapplication.geocoding

import com.example.weatherapplication.geocoding.model.Coordinates
import com.example.weatherapplication.geocoding.model.Location
import io.reactivex.Single

class WeatherGeocoder(private val service: GeocoderApi) : Geocoder {
    override fun directGeocode(locationName: String): Single<List<Coordinates>> =
        service.directGeocode(locationName)

    override fun reverseGeocode(latitude: Double, longitude: Double): Single<List<Location>> =
        service.reverseGeocode(latitude, longitude)
}