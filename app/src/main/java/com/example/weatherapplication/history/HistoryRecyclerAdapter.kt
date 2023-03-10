package com.example.weatherapplication.history

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.databinding.RecyclerviewItemBinding

class HistoryRecyclerAdapter(
    private val onDelete: (HistoryRecord) -> Unit,
    private val onSee: (HistoryRecord) -> Unit,
) :
    ListAdapter<HistoryRecord, HistoryRecyclerAdapter.ViewHolder>(HistoryItemCallback()) {
    class ViewHolder(binding: RecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val locationNameTextView: TextView = binding.locationNameTextView
        val latitudeTextView: TextView = binding.historyLatitudeTextView
        val longitudeTextView: TextView = binding.historyLongitudeTextView
        val deleteButton: ImageButton = binding.deleteButton
        val seeButton: ImageButton = binding.viewButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record = getItem(position)
        holder.locationNameTextView.text = record.locationName
        holder.latitudeTextView.text = record.latitude.toString()
        holder.longitudeTextView.text = record.longitude.toString()

        holder.deleteButton.setOnClickListener {
            onDelete(record)
        }

        holder.seeButton.setOnClickListener {
            onSee(record)
        }
    }

    private class HistoryItemCallback : DiffUtil.ItemCallback<HistoryRecord>() {
        override fun areItemsTheSame(oldItem: HistoryRecord, newItem: HistoryRecord) =
            oldItem.locationName == newItem.locationName

        override fun areContentsTheSame(oldItem: HistoryRecord, newItem: HistoryRecord) =
            oldItem == newItem
    }
}