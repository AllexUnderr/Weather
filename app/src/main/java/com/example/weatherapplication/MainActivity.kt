package com.example.weatherapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.core.app.ActivityCompat
import androidx.preference.PreferenceFragmentCompat
import com.example.weatherapplication.databinding.ActivityMainBinding
import org.osmdroid.config.Configuration.*
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView
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

        binding.MapView.setTileSource(TileSourceFactory.MAPNIK)

        binding.insertButton.setOnClickListener {
            val latitude = binding.latitudeEditText.text.toString().toDouble()
            val longitude = binding.longitudeEditText.text.toString().toDouble()

            if (latitude <= 90.0 && latitude >= -90.0 && longitude <= 180.0 && longitude >= -180) {
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
        binding.MapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.MapView.onPause()
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