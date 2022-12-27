package com.example.weatherapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapplication.api.RetrofitBuilder
import com.example.weatherapplication.databinding.ActivityMainBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.weatherapplication.api.geocoding.AndroidGeocoder
import com.example.weatherapplication.api.geocoding.Geocoder
import com.example.weatherapplication.api.geocoding.WeatherGeocoder
import com.example.weatherapplication.api.weatherAPI.Weather
import retrofit2.create

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.insertButton.setOnClickListener {
            val locationName = binding.objectNameEditText.text.toString()

            if (locationName.isBlank())
                return@setOnClickListener

            if (binding.geocodeSwitch.isChecked)
                directGeocode(locationName, AndroidGeocoder(this))
            else
                directGeocode(locationName, WeatherGeocoder(RetrofitBuilder.retrofit.create()))
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.setOnMapClickListener { marker ->
            val latitude = marker.latitude
            val longitude = marker.longitude

            setCoordinates(latitude, longitude)
            setMarker(marker)

            if (binding.geocodeSwitch.isChecked)
                reverseGeocode(latitude, longitude, AndroidGeocoder(this))
            else
                reverseGeocode(
                    latitude,
                    longitude,
                    WeatherGeocoder(RetrofitBuilder.retrofit.create())
                )
        }
    }

    private fun directGeocode(locationName: String, geocoder: Geocoder) {
        geocoder.directGeocode(locationName) geocode@{ geocoding ->
            if (geocoding == null) {
                binding.objectNameTextView.text = getString(R.string.object_name_error)
                binding.applicationTitleTextView.text = getString(R.string.app_name)
                return@geocode
            }

            val (latitude, longitude) = geocoding
            Weather(RetrofitBuilder.retrofit.create()).getWeather(latitude, longitude) {
                binding.objectNameTextView.text =
                    getString(R.string.temperature, it.main.temperature.toString())
            }
            binding.applicationTitleTextView.text = locationName

            setCoordinates(latitude, longitude)
            setMarker(LatLng(latitude, longitude))
        }
    }

    private fun reverseGeocode(latitude: Double, longitude: Double, geocoder: Geocoder) {
        geocoder.reverseGeocode(latitude, longitude) geocode@{
            if (it == null) {
                binding.objectNameTextView.text = getString(R.string.object_coordinates_error)
                binding.applicationTitleTextView.text = getString(R.string.app_name)
                return@geocode
            }

            binding.applicationTitleTextView.text = it.name
            Weather(RetrofitBuilder.retrofit.create()).getWeather(
                latitude,
                longitude
            ) { weatherData ->
                binding.objectNameTextView.text =
                    getString(R.string.temperature, weatherData.main.temperature.toString())
            }
        }
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
}