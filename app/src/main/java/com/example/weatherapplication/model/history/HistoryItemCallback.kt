package com.example.weatherapplication.model.history

import androidx.recyclerview.widget.DiffUtil

class HistoryItemCallback : DiffUtil.ItemCallback<HistoryRecord>() {
    override fun areItemsTheSame(oldItem: HistoryRecord, newItem: HistoryRecord) =
        oldItem.locationName == newItem.locationName

    override fun areContentsTheSame(oldItem: HistoryRecord, newItem: HistoryRecord) =
        oldItem == newItem
}