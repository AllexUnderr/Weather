package com.example.weatherapplication.dagger

import android.app.Application
import com.example.weatherapplication.dagger.AppComponent.Companion.AppModule

class MainApp : Application() {
    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(
                AppModule(applicationContext)
            )
            .build()
    }
}