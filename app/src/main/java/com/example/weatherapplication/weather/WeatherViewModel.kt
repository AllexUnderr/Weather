package com.example.weatherapplication.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapplication.geocoding.Geocoder
import com.example.weatherapplication.history.History
import com.example.weatherapplication.history.HistoryRecord
import com.example.weatherapplication.weather.model.GeographicalObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class WeatherViewModel(
    private val geocoder: Geocoder,
    private val history: History,
    private val weather: Weather,
) {
    private val internalGeographicObject: MutableLiveData<GeographicalObject?> = MutableLiveData()
    val geographicObject: LiveData<GeographicalObject?> = internalGeographicObject
    private val disposableList: MutableList<Disposable> = mutableListOf()

    fun directGeocode(locationName: String) {
        disposableList += geocoder.directGeocode(locationName)
            .filter { it.isNotEmpty() }
            .map { it.first() }
            .flatMapSingleElement { coords ->
                weather.getWeather(coords.latitude, coords.longitude)
                    .map { coords to it }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { (coords, weather) ->
                    internalGeographicObject.value =
                        GeographicalObject(
                            locationName = locationName,
                            latitude = coords.latitude,
                            longitude = coords.longitude,
                            temperature = weather.main.temperature
                        )
                },
                onComplete = {
                    internalGeographicObject.value = null
                },
                onError = {
                    it.printStackTrace()
                }
            )
    }

    fun reverseGeocode(latitude: Double, longitude: Double) {
        disposableList += geocoder.reverseGeocode(latitude, longitude)
            .filter { it.isNotEmpty() }
            .map { it.first() }
            .flatMapSingleElement { location ->
                weather.getWeather(latitude, longitude)
                    .map { location to it }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { (location, weather) ->
                    internalGeographicObject.value =
                        GeographicalObject(
                            locationName = location.name,
                            latitude = latitude,
                            longitude = longitude,
                            temperature = weather.main.temperature
                        )
                },
                onComplete = {
                    internalGeographicObject.value = null
                },
                onError = {
                    it.printStackTrace()
                }
            )
    }

    fun saveRecord(record: HistoryRecord) {
        disposableList += history.addRecord(record)
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onError = {
                    it.printStackTrace()
                }
            )
    }

    fun disposeAll() {
        disposableList.forEach { it.dispose() }
        disposableList.clear()
    }
}