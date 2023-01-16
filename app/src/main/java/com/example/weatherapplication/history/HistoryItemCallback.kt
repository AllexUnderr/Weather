package com.example.weatherapplication.history

import androidx.recyclerview.widget.DiffUtil
import com.example.weatherapplication.history.HistoryRecord

class HistoryItemCallback : DiffUtil.ItemCallback<HistoryRecord>() {
    override fun areItemsTheSame(oldItem: HistoryRecord, newItem: HistoryRecord) =
        oldItem.locationName == newItem.locationName

    override fun areContentsTheSame(oldItem: HistoryRecord, newItem: HistoryRecord) =
        oldItem == newItem
}