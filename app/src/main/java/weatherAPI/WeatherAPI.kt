package weatherAPI

import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Query

interface WeatherAPI {
    @GET("weather")
    fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") unit: String,
        @Query("appid") APIKey: String,
    ): Call<WeatherData>
}
