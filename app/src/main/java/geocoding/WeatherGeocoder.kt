package geocoding

import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class WeatherGeocoder : Geocoder {
    private val URL = "https://api.openweathermap.org/"
    private val API_KEY = "dc0b743045342ccf1c13087fef9fe0cb"

    private var retrofit: Retrofit = Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(URL)
        .build()

    private val service = retrofit.create<GeocoderAPI>()

    override fun directGeocode(
        locationName: String,
        success: (List<Coordinates>) -> Unit
    ) {
        val call = service.directGeocode(locationName, API_KEY)
        call.enqueue(object : Callback<List<Coordinates>> {
            override fun onResponse(
                call: Call<List<Coordinates>>,
                response: Response<List<Coordinates>>
            ) {
                success(requireNotNull(response.body()))
            }

            override fun onFailure(call: Call<List<Coordinates>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    override fun reverseGeocode(
        latitude: Double,
        longitude: Double,
        success: (List<Location>) -> Unit
    ) {
        val call = service.reverseGeocode(latitude, longitude, API_KEY)
        call.enqueue(object : Callback<List<Location>> {
            override fun onResponse(
                call: Call<List<Location>>,
                response: Response<List<Location>>
            ) {
                success(requireNotNull(response.body()))
            }

            override fun onFailure(call: Call<List<Location>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}