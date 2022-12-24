package com.example.weatherapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapplication.databinding.ActivityMainBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import weatherAPI.Weather

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
            val nameObject = binding.objectNameEditText.text.toString()

            if (nameObject.isBlank())
                return@setOnClickListener

            Weather().geocoding(nameObject) { geocoding ->
                if (geocoding.isEmpty()) {
                    binding.objectNameTextView.text = getString(R.string.object_name_error)
                    binding.applicationTitleTextView.text = getString(R.string.app_name)
                } else {
                    val (name, latitude, longitude) = geocoding.first()

                    Weather().getWeather(latitude, longitude) {
                        binding.objectNameTextView.text =
                            getString(R.string.temperature, it.main.temp.toString())
                    }
                    binding.applicationTitleTextView.text = name

                    setCoordinates(latitude, longitude)
                    setMarker(LatLng(latitude, longitude))
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.setOnMapClickListener { marker ->
            val latitude = marker.latitude
            val longitude = marker.longitude

            setCoordinates(latitude, longitude)
            setMarker(marker)

            Weather().getObjectName(latitude, longitude) {
                if (it.isEmpty()) {
                    binding.objectNameTextView.text = getString(R.string.object_coordinates_error)
                    binding.applicationTitleTextView.text = getString(R.string.app_name)
                } else {
                    binding.applicationTitleTextView.text = it.first().name
                    Weather().getWeather(latitude, longitude) { weatherData ->
                        binding.objectNameTextView.text =
                            getString(R.string.temperature, weatherData.main.temp.toString())
                    }
                }
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