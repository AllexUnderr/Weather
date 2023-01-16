package com.example.weatherapplication.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherapplication.databinding.ActivityHistoryBinding
import com.example.weatherapplication.history.History
import com.example.weatherapplication.history.HistoryRecyclerAdapter
import com.example.weatherapplication.history.HistoryRecord
import com.example.weatherapplication.history.StorageProvider

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var history: History

    private val onDelete: (HistoryRecyclerAdapter, HistoryRecord) -> Unit = { adapter, record ->
        binding.root.post {
            Thread {
                history.removeRecord(record)
                val records = history.getRecords()
                runOnUiThread {
                    adapter.submitList(records)
                }
            }.start()
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

        history = StorageProvider().getStorage(this)

        Thread {
            val records = history.getRecords()
            val historyAdapter = HistoryRecyclerAdapter(onDelete, onSee)
            runOnUiThread {
                historyAdapter.submitList(records)
                binding.historyRecyclerView.adapter = historyAdapter
            }
        }.start()
    }
}