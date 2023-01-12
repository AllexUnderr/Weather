package com.example.weatherapplication.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherapplication.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater).also { setContentView(it.root) }

        binding.weatherButton.setOnClickListener {
            val intent = WeatherActivity.createIntent(this, shouldUseFileStorage = binding.storageSwitch.isChecked)
            startActivity(intent)
        }

        binding.historyButton.setOnClickListener {
            val intent = HistoryActivity.createIntent(this, shouldUseFileStorage = binding.storageSwitch.isChecked)
            startActivity(intent)
        }
    }
}