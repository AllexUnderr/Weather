package weatherAPI

import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class Weather {
    private val URL = "https://api.openweathermap.org/"
    private val UNIT = "metric"
    private val API_KEY = "dc0b743045342ccf1c13087fef9fe0cb"

    private var retrofit: Retrofit = Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(URL)
        .build()

    private val service = retrofit.create<WeatherAPI>()

    fun getWeather(lat: Double, lon: Double, success: (WeatherData) -> Unit) {
        val call = service.getWeatherByCoordinates(lat, lon, UNIT, API_KEY)
        call.enqueue(object : Callback<WeatherData> {
            override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                success(requireNotNull(response.body()))
            }

            override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun geocoding(objectName: String, success: (List<Geocoding>) -> Unit) {
        val call = service.getWeatherByObject(objectName, API_KEY)
        call.enqueue(object : Callback<List<Geocoding>> {
            override fun onResponse(
                call: Call<List<Geocoding>>,
                response: Response<List<Geocoding>>
            ) {
                success(requireNotNull(response.body()))
            }

            override fun onFailure(call: Call<List<Geocoding>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun getObjectName(lat: Double, lon: Double, success: (List<ObjectName>) -> Unit) {
        val call = service.getObjectName(lat, lon, API_KEY)
        call.enqueue(object : Callback<List<ObjectName>> {
            override fun onResponse(
                call: Call<List<ObjectName>>,
                response: Response<List<ObjectName>>
            ) {
                success(requireNotNull(response.body()))
            }

            override fun onFailure(call: Call<List<ObjectName>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}