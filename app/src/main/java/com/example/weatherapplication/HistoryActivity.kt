package com.example.weatherapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherapplication.databinding.ActivityHistoryBinding
import com.example.weatherapplication.model.history.HistoryRecyclerAdapter
import com.example.weatherapplication.model.history.History
import com.example.weatherapplication.model.history.HistoryRecord

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var history: History

    private val onDelete: (HistoryRecyclerAdapter, HistoryRecord) -> Unit = { adapter, record ->
        binding.root.post {
            history.removeRecord(record)
            adapter.submitList(history.getRecords())
        }
    }

    private val onSee: (HistoryRecord) -> Unit = { record ->
        val intent = Intent(this, WeatherActivity::class.java)
        intent.putExtra(HistoryRecord::class.simpleName, record)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater).also { setContentView(it.root) }

        history = History(this)
        val records = history.getRecords()
        val historyAdapter = HistoryRecyclerAdapter(onDelete, onSee)
        historyAdapter.submitList(records)
        binding.historyRecyclerView.adapter = historyAdapter
    }
}