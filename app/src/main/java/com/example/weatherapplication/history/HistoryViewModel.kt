package com.example.weatherapplication.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapplication.mvvm.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class HistoryViewModel(private val history: History) {
    private val internalRecords: MutableLiveData<List<HistoryRecord>> = MutableLiveData()
    val records: LiveData<List<HistoryRecord>> = internalRecords
    val showRecord = SingleLiveEvent<HistoryRecord>()
    private val disposableList = mutableListOf<Disposable>()

    fun init() {
        disposableList += history.getRecords()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    internalRecords.value = it
                },
                onError = {
                    it.printStackTrace()
                }
            )
    }

    fun removeRecord(record: HistoryRecord) {
        disposableList += history.removeRecord(record)
            .andThen(history.getRecords())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    internalRecords.value = it
                },
                onError = {
                    it.printStackTrace()
                }
            )
    }

    fun onSee(record: HistoryRecord) {
        showRecord.value = record
    }

    fun disposeAll() {
        disposableList.forEach { it.dispose() }
        disposableList.clear()
    }
}