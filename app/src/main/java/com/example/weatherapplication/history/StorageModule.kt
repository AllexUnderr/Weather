package com.example.weatherapplication.history

import android.content.Context
import com.example.weatherapplication.BuildConfig
import com.example.weatherapplication.history.file.HistoryFile
import com.example.weatherapplication.history.room.AppDatabase
import dagger.Module
import dagger.Provides

@Module
class StorageModule {
    private val ROOM_STORAGE = "roomStorage"

    @Provides
    fun getStorage(context: Context): History =
        if (BuildConfig.FLAVOR_storage == ROOM_STORAGE)
            AppDatabase.getInstance(context).getHistoryDao()
        else
            HistoryFile(context)
}