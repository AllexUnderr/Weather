package weatherAPI

import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class Weather {
    private val url = "https://api.openweathermap.org/data/2.5/"
    private val unit = "metric"
    private val apiKey = "dc0b743045342ccf1c13087fef9fe0cb"

    fun getWeather(lat: Double, lon: Double, success: (WeatherData) -> Unit) {
        val retrofit = Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(url)
            .build()

        val service = retrofit.create<WeatherAPI>()
        val call = service.getWeather(lat, lon, unit, apiKey)
        call.enqueue(object : Callback<WeatherData> {
            override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                success(requireNotNull(response.body()))
            }

            override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}