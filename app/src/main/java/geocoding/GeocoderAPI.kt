package geocoding

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocoderAPI {
    @GET("geo/1.0/direct")
    fun directGeocode(
        @Query("q") objectName: String,
        @Query("appid") APIKey: String,
    ): Call<List<Coordinates>>

    @GET("geo/1.0/reverse")
    fun reverseGeocode(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") APIKey: String,
    ): Call<List<Location>>
}