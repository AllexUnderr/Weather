package com.example.weatherapplication.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapplication.geocoding.Geocoder
import com.example.weatherapplication.history.History
import com.example.weatherapplication.history.HistoryRecord
import com.example.weatherapplication.service.RetrofitBuilder
import com.example.weatherapplication.weather.model.GeographicalObject
import retrofit2.create

class WeatherViewModel(private val geocoder: Geocoder, private val history: History) {
    private val internalGeographicObject: MutableLiveData<GeographicalObject?> = MutableLiveData()
    val geographicObject: LiveData<GeographicalObject?> = internalGeographicObject

    fun directGeocode(locationName: String) {
        geocoder.directGeocode(locationName) geocode@{ coordinates ->
            if (coordinates == null) {
                internalGeographicObject.value = null
                return@geocode
            }

            val (latitude, longitude) = coordinates
            Weather(RetrofitBuilder.retrofit.create()).getWeather(latitude, longitude) {
                internalGeographicObject.value =
                    GeographicalObject(locationName, latitude, longitude, it.main.temperature)
            }
        }
    }

    fun reverseGeocode(latitude: Double, longitude: Double) {
        geocoder.reverseGeocode(latitude, longitude) geocode@{ location ->
            if (location == null) {
                internalGeographicObject.value = null
                return@geocode
            }

            val locationName = location.name
            Weather(RetrofitBuilder.retrofit.create()).getWeather(latitude, longitude) {
                internalGeographicObject.value =
                    GeographicalObject(locationName, latitude, longitude, it.main.temperature)
            }
        }
    }

    fun saveRecord(record: HistoryRecord) {
        Thread {
            history.addRecord(record)
        }.start()
    }
}