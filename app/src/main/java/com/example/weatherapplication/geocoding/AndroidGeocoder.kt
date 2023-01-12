package com.example.weatherapplication.geocoding

import android.content.Context
import android.location.Address
import android.os.Build
import com.example.weatherapplication.geocoding.model.Coordinates
import com.example.weatherapplication.geocoding.model.Location

class AndroidGeocoder(context: Context) : Geocoder {
    private var geocoder = android.location.Geocoder(context)

    override fun directGeocode(locationName: String, success: (Coordinates?) -> Unit) {
        if (isDeprecatedVersion()) {
            @Suppress("DEPRECATION")
            val addresses = geocoder.getFromLocationName(locationName, 1)
            callDirect(addresses, success)
        } else {
            geocoder.getFromLocationName(locationName, 1) {
                callDirect(it, success)
            }
        }
    }

    override fun reverseGeocode(latitude: Double, longitude: Double, success: (Location?) -> Unit) {
        if (isDeprecatedVersion()) {
            @Suppress("DEPRECATION")
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            callReverse(addresses, success)
        } else {
            geocoder.getFromLocation(latitude, longitude, 1) {
                callReverse(it, success)
            }
        }
    }

    private fun isDeprecatedVersion() = Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU

    private fun callDirect(addresses: List<Address>?, success: (Coordinates?) -> Unit) {
        val address = addresses?.firstOrNull()
        if (address == null)
            success(null)
        else
            success(Coordinates(address.latitude, address.longitude))
    }

    private fun callReverse(addresses: List<Address>?, success: (Location?) -> Unit) {
        val address = addresses?.firstOrNull()
        if (address == null)
            success(null)
        else
            success(Location(address.subAdminArea))
    }
}