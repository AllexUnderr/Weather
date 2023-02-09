package com.example.weatherapplication.geocoding

import android.content.Context
import android.location.Address
import android.os.Build
import com.example.weatherapplication.geocoding.model.Coordinates
import com.example.weatherapplication.geocoding.model.Location
import io.reactivex.Single

class AndroidGeocoder(context: Context) : Geocoder {
    private var geocoder = android.location.Geocoder(context)

    override fun directGeocode(locationName: String): Single<List<Coordinates>> =
        Single.create {
            if (!it.isDisposed) {
                if (isDeprecated()) {
                    @Suppress("DEPRECATION")
                    geocoder.getFromLocationName(locationName, 1)?.let { addresses ->
                        it.onSuccess(convertAddressesToCoordinates(addresses))
                    }
                } else {
                    geocoder.getFromLocationName(locationName, 1) { addresses ->
                        it.onSuccess(convertAddressesToCoordinates(addresses))
                    }
                }
            }
        }

    override fun reverseGeocode(latitude: Double, longitude: Double): Single<List<Location>> =
        Single.create {
            if (!it.isDisposed) {
                if (isDeprecated()) {
                    @Suppress("DEPRECATION")
                    geocoder.getFromLocation(latitude, longitude, 1)?.let { addresses ->
                        it.onSuccess(convertAddressesToLocations(addresses))
                    }
                } else {
                    geocoder.getFromLocation(latitude, longitude, 1) { addresses ->
                        it.onSuccess(convertAddressesToLocations(addresses))
                    }
                }
            }
        }

    private fun isDeprecated() = Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU

    private fun convertAddressesToCoordinates(addresses: List<Address>): List<Coordinates> =
        addresses.map { Coordinates(it.latitude, it.longitude) }

    private fun convertAddressesToLocations(addresses: List<Address>): List<Location> =
        addresses.map { Location(it.subAdminArea) }
}