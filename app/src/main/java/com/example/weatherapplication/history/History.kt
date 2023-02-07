package com.example.weatherapplication.history

import io.reactivex.Completable
import io.reactivex.Single

interface History {
    fun addRecord(record: HistoryRecord): Completable

    fun getRecords(): Single<List<HistoryRecord>>

    fun removeRecord(record: HistoryRecord): Completable
}