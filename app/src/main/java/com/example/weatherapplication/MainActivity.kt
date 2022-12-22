package com.example.weatherapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.example.weatherapplication.databinding.ActivityMainBinding
import org.osmdroid.config.Configuration.*
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import weatherAPI.Weather

class MainActivity : AppCompatActivity() {
    private val REQUEST_PERMISSIONS_REQUEST_CODE = 1
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        getInstance().load(
            this,
            androidx.preference.PreferenceManager.getDefaultSharedPreferences(this)
        )
        setContentView(view)

        binding.mapView.setTileSource(TileSourceFactory.MAPNIK)

        binding.insertButton.setOnClickListener {
            val latitude = binding.latitudeEditText.text.toString().toDouble()
            val longitude = binding.longitudeEditText.text.toString().toDouble()

            if (latitude in -90.0..90.0 && longitude in -180.0..180.0) {
                Weather().getWeather(latitude, longitude) {
                    binding.temperatureTextView.text =
                        getString(R.string.temperature, it.main.temp.toString())
                }
            } else {
                binding.temperatureTextView.text = getString(R.string.coordinates_error)
            }
        }
    }


    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val permissionsToRequest = ArrayList<String>()
        var i = 0
        while (i < grantResults.size) {
            permissionsToRequest.add(permissions[i])
            i++
        }
        if (permissionsToRequest.size > 0) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                REQUEST_PERMISSIONS_REQUEST_CODE)
        }
    }
}