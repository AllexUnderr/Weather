package com.example.weatherapplication.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weatherapplication.databinding.FragmentHistoryBinding
import com.example.weatherapplication.navigation.navigator
import com.example.weatherapplication.dagger.MainApp
import javax.inject.Inject

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding

    @Inject
    lateinit var viewModel: HistoryViewModel

    private val onDelete: (HistoryRecord) -> Unit = { record ->
        viewModel.removeRecord(record)
    }

    private val onSee: (HistoryRecord) -> Unit = { record ->
        viewModel.onSee(record)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MainApp.appComponent.inject(this)


        val historyAdapter = HistoryRecyclerAdapter(onDelete, onSee)
        binding.historyRecyclerView.adapter = historyAdapter

        viewModel.records.observe(viewLifecycleOwner) {
            historyAdapter.submitList(it)
        }

        viewModel.showRecord.observe(viewLifecycleOwner) {
            navigator().showRecord(it)
        }

        viewModel.init()
    }
}