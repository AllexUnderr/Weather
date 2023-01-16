package com.example.weatherapplication.ui.activity

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapplication.R
import com.example.weatherapplication.service.RetrofitBuilder
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.weatherapplication.geocoding.Geocoder
import com.example.weatherapplication.weather.Weather
import com.example.weatherapplication.databinding.ActivityWeatherBinding
import com.example.weatherapplication.geocoding.GeocoderProvider
import com.example.weatherapplication.history.History
import com.example.weatherapplication.history.HistoryRecord
import com.example.weatherapplication.history.StorageProvider
import retrofit2.create

class WeatherActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityWeatherBinding
    private lateinit var history: History
    private lateinit var geocoder: Geocoder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater).also { setContentView(it.root) }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        history = StorageProvider().getStorage(this)
        geocoder = GeocoderProvider().getGeocoder(this)

        binding.insertButton.setOnClickListener {
            val locationName = binding.objectNameEditText.text.toString()

            if (locationName.isBlank())
                return@setOnClickListener

            directGeocode(locationName, geocoder)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val record = getRecordFromIntent()
        setRecordView(record)

        map.setOnMapClickListener { marker ->
            val latitude = marker.latitude
            val longitude = marker.longitude

            setCoordinates(latitude, longitude)
            setMarker(marker)

            reverseGeocode(latitude, longitude, geocoder)
        }
    }

    private fun directGeocode(locationName: String, geocoder: Geocoder) {
        geocoder.directGeocode(locationName) geocode@{ coordinates ->
            if (coordinates == null) {
                setViewErrorMessage()
                return@geocode
            }

            val (latitude, longitude) = coordinates
            Weather(RetrofitBuilder.retrofit.create()).getWeather(latitude, longitude) {
                setTemperature(it.main.temperature)
            }
            setCity(locationName)

            setCoordinates(latitude, longitude)
            setMarker(LatLng(latitude, longitude))

            Thread {
                history.addRecord(HistoryRecord(locationName, latitude, longitude))
            }.start()
        }
    }

    private fun reverseGeocode(latitude: Double, longitude: Double, geocoder: Geocoder) {
        geocoder.reverseGeocode(latitude, longitude) geocode@{ location ->
            if (location == null) {
                setViewErrorMessage()
                return@geocode
            }

            val locationName = location.name
            Weather(RetrofitBuilder.retrofit.create()).getWeather(latitude, longitude) {
                setTemperature(it.main.temperature)
            }
            setCity(locationName)

            Thread {
                history.addRecord(HistoryRecord(locationName, latitude, longitude))
            }.start()
        }
    }

    private fun isDeprecated() = Build.VERSION.SDK_INT < 33

    private fun getRecordFromIntent(): HistoryRecord? =
        if (isDeprecated())
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(HistoryRecord::class.simpleName)
        else
            intent.getParcelableExtra(HistoryRecord::class.simpleName, HistoryRecord::class.java)

    private fun setRecordView(record: HistoryRecord?) {
        if (record != null) {
            reverseGeocode(record.latitude, record.longitude, geocoder)
            setCoordinates(record.latitude, record.longitude)
            setMarker(LatLng(record.latitude, record.longitude))
        }
    }

    private fun setViewErrorMessage() {
        binding.objectNameTextView.text = getString(R.string.object_name_error)
        binding.cityTextView.text = getString(R.string.object_name)
    }

    private fun setCoordinates(latitude: Double, longitude: Double) {
        binding.latitudeTextView.text = getString(R.string.latitude_template, latitude)
        binding.longitudeTextView.text = getString(R.string.longitude_template, longitude)
    }

    private fun setMarker(latLng: LatLng) {
        map.clear()
        map.addMarker(MarkerOptions().position(latLng))
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng))
    }

    private fun setTemperature(temperature: Double) {
        binding.objectNameTextView.text = getString(R.string.temperature, temperature.toString())
    }

    private fun setCity(locationName: String) {
        binding.cityTextView.text = locationName
    }
}