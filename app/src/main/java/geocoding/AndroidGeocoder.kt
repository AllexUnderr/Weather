package geocoding

import android.content.Context
import android.os.Build

class AndroidGeocoder(context: Context) : Geocoder {
    private var geocoder = android.location.Geocoder(context)

    override fun directGeocode(
        locationName: String,
        success: (List<Coordinates>) -> Unit
    ) {
        if (isDeprecatedVersion()) {
            val addresses = geocoder.getFromLocationName(locationName, 1)
            success(
                listOf(
                    Coordinates(
                        requireNotNull(addresses?.first()?.latitude),
                        requireNotNull(addresses?.first()?.longitude)
                    )
                )
            )
        } else {
            geocoder.getFromLocationName(locationName, 1) {
                success(listOf(Coordinates(it.first().latitude, it.first().longitude)))
            }
        }
    }

    override fun reverseGeocode(
        latitude: Double,
        longitude: Double,
        success: (List<Location>) -> Unit
    ) {
        if (isDeprecatedVersion()) {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            success(listOf(Location(requireNotNull(addresses?.first()?.subAdminArea))))
        } else {
            geocoder.getFromLocation(latitude, longitude, 1) {
                success(listOf(Location(it.first().subAdminArea)))
            }
        }
    }

    private fun isDeprecatedVersion() = Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU
}