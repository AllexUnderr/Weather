package com.example.weatherapplication.history

import dagger.Module
import dagger.Provides

@Module
class HistoryModule {
    @Provides
    fun provideHistoryViewModel(history: History) = HistoryViewModel(history)
}