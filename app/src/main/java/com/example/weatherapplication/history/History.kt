package com.example.weatherapplication.history

interface History {
    fun addRecord(record: HistoryRecord)

    fun getRecords(): List<HistoryRecord>

    fun removeRecord(record: HistoryRecord)
}