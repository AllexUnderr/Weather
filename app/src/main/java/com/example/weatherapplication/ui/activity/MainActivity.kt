package com.example.weatherapplication.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.weatherapplication.R
import com.example.weatherapplication.databinding.ActivityMainBinding
import com.example.weatherapplication.history.HistoryFragment
import com.example.weatherapplication.history.HistoryRecord
import com.example.weatherapplication.navigation.Navigator
import com.example.weatherapplication.ui.fragment.MenuFragment
import com.example.weatherapplication.weather.WeatherFragment

class MainActivity : AppCompatActivity(), Navigator {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        supportFragmentManager.commit {
            add(R.id.appFragmentContainer, MenuFragment())
        }
    }

    override fun showWeather() {
        launchFragment(WeatherFragment())
    }

    override fun showHistory() {
        launchFragment(HistoryFragment())
    }

    override fun showRecord(record: HistoryRecord) {
        launchFragment(WeatherFragment.newInstance(record))
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            addToBackStack(null)
            replace(binding.appFragmentContainer.id, fragment)
        }
    }
}