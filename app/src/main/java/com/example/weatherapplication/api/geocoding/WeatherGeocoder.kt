package com.example.weatherapplication.api.geocoding

import retrofit2.*

class WeatherGeocoder(private val service: GeocoderAPI) : Geocoder {
    override fun directGeocode(
        locationName: String,
        success: (Coordinates?) -> Unit
    ) {
        val call = service.directGeocode(locationName)
        call.enqueue(object : Callback<List<Coordinates>> {
            override fun onResponse(
                call: Call<List<Coordinates>>,
                response: Response<List<Coordinates>>
            ) {
                success(response.body()?.firstOrNull())
            }

            override fun onFailure(call: Call<List<Coordinates>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    override fun reverseGeocode(
        latitude: Double,
        longitude: Double,
        success: (Location?) -> Unit
    ) {
        val call = service.reverseGeocode(latitude, longitude)
        call.enqueue(object : Callback<List<Location>> {
            override fun onResponse(
                call: Call<List<Location>>,
                response: Response<List<Location>>
            ) {
                success(response.body()?.firstOrNull())
            }

            override fun onFailure(call: Call<List<Location>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}