package weatherAPI

data class Main(val temp: Double)

data class WeatherData(val main: Main)

data class Geocoding(val name: String, val lat: Double, val lon: Double)

data class ObjectName(val name: String)