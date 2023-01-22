package com.example.weatherapplication.navigation

import androidx.fragment.app.Fragment
import com.example.weatherapplication.history.HistoryRecord

fun Fragment.navigator(): Navigator {
    return requireActivity() as Navigator
}

interface Navigator {
    fun showWeather()

    fun showHistory()

    fun showRecord(record: HistoryRecord)
}