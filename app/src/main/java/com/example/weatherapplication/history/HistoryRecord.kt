package com.example.weatherapplication.history

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity

@Parcelize
@Entity(
    primaryKeys = [
        "location_name",
        "latitude",
        "longitude"
    ]
)
data class HistoryRecord(
    @ColumnInfo(name = "location_name") val locationName: String,
    val latitude: Double,
    val longitude: Double,
) : Parcelable