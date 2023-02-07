package com.example.weatherapplication.dagger

import android.content.Context
import com.example.weatherapplication.geocoding.GeocoderModule
import com.example.weatherapplication.history.HistoryFragment
import com.example.weatherapplication.history.HistoryModule
import com.example.weatherapplication.history.StorageModule
import com.example.weatherapplication.service.RetrofitBuilder
import com.example.weatherapplication.weather.WeatherFragment
import com.example.weatherapplication.weather.WeatherModule
import com.example.weatherapplication.weather.WeatherViewModel
import dagger.Component
import dagger.Module
import dagger.Provides

@Component(
    modules = [
        AppComponent.Companion.AppModule::class,
        StorageModule::class,
        GeocoderModule::class,
        HistoryModule::class,
        WeatherModule::class,
        RetrofitBuilder::class,
    ]
)
interface AppComponent {
    fun inject(historyFragment: HistoryFragment)
    fun inject(weatherFragment: WeatherFragment)
    fun inject(weatherViewModel: WeatherViewModel)

    companion object {
        @Module
        class AppModule(private val context: Context) {
            @Provides
            fun provideContext(): Context = context
        }
    }
}