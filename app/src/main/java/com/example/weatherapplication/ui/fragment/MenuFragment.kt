package com.example.weatherapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weatherapplication.databinding.FragmentMenuBinding
import com.example.weatherapplication.navigation.navigator

class MenuFragment : Fragment() {
    private lateinit var binding: FragmentMenuBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMenuBinding.inflate(inflater, container, false)

        binding.weatherButton.setOnClickListener {
            navigator().showWeather()
        }

        binding.historyButton.setOnClickListener {
            navigator().showHistory()
        }

        return binding.root
    }
}