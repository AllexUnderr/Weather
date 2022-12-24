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
            val latitude = binding.latitudeEditText.text.toString().toDoubleOrNull()
            val longitude = binding.longitudeEditText.text.toString().toDoubleOrNull()

            if (latitude == null || longitude == null)
                return@setOnClickListener

            if (latitude in -90.0..90.0 && longitude in -180.0..180.0) {
                setTemperature(latitude, longitude)
                map.clear()
                val coordinates = LatLng(latitude, longitude)
                map.addMarker(MarkerOptions().position(coordinates))
                map.moveCamera(CameraUpdateFactory.newLatLng(coordinates))
            } else {
                binding.temperatureTextView.text = getString(R.string.coordinates_error)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.setOnMapClickListener {
            map.clear()
            map.addMarker(MarkerOptions().position(it))
            setTemperature(it.latitude, it.longitude)
            binding.latitudeEditText.setText(it.latitude.toString())
            binding.longitudeEditText.setText(it.longitude.toString())
        }
    }

    private fun setTemperature(latitude: Double, longitude: Double) {
        Weather().getWeather(latitude, longitude) {
            binding.temperatureTextView.text =
                getString(R.string.temperature, it.main.temp.toString())
        }
    }
}