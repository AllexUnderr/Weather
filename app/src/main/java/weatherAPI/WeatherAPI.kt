package weatherAPI

import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Query

interface WeatherAPI {
    @GET("data/2.5/weather")
    fun getWeatherByCoordinates(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") unit: String,
        @Query("appid") APIKey: String,
    ): Call<WeatherData>

    @GET("geo/1.0/direct")
    fun getWeatherByObject(
        @Query("q") objectName: String,
        @Query("appid") APIKey: String,
    ): Call<List<Geocoding>>

    @GET("geo/1.0/reverse")
    fun getObjectName(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") APIKey: String,
    ): Call<List<ObjectName>>
}