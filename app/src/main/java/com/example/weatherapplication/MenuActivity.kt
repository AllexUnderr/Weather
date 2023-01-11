package com.example.weatherapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherapplication.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {
    private val STORAGE = "storage"

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater).also { setContentView(it.root) }

        binding.weatherButton.setOnClickListener {
            val intent = Intent(this, WeatherActivity::class.java)
            if (binding.storageSwitch.isChecked)
                intent.putExtra(STORAGE, true)
            else
                intent.putExtra(STORAGE, false)
            startActivity(intent)
        }

        binding.historyButton.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            if (binding.storageSwitch.isChecked)
                intent.putExtra(STORAGE, true)
            else
                intent.putExtra(STORAGE, false)
            startActivity(intent)
        }
    }
}