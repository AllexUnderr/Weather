package com.example.weatherapplication.history

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapplication.mvvm.SingleLiveEvent

class HistoryViewModel(private val history: History) {
    private val internalRecords: MutableLiveData<List<HistoryRecord>> = MutableLiveData()
    val records: LiveData<List<HistoryRecord>> = internalRecords
    val showRecord = SingleLiveEvent<HistoryRecord>()

    fun init() {
        runOnBackgroundThread {
            val records = history.getRecords()
            runOnMainThread {
                internalRecords.value = records
            }
        }
    }

    fun removeRecord(record: HistoryRecord) {
        runOnBackgroundThread {
            history.removeRecord(record)
            val records = history.getRecords()
            runOnMainThread {
                internalRecords.value = records
            }
        }
    }

    fun onSee(record: HistoryRecord) {
        showRecord.value = record
    }
}

fun runOnBackgroundThread(lambda: () -> Unit) {
    Thread {
        lambda()
    }.start()
}

fun runOnMainThread(lambda: () -> Unit) {
    Handler(Looper.getMainLooper()).post {
        lambda()
    }
}