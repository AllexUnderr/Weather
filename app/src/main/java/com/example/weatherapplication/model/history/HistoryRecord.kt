package com.example.weatherapplication.model.history

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class HistoryRecord(val locationName: String, val latitude: Double, val longitude: Double) : Parcelable