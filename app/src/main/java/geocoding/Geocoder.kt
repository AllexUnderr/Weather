package geocoding

interface Geocoder {
    fun directGeocode(locationName: String, success: (List<Coordinates>) -> Unit)

    fun reverseGeocode(latitude: Double, longitude: Double, success: (List<Location>) -> Unit)
}