package com.example.weatherapplication.model.history.file

import android.content.Context
import com.example.weatherapplication.model.history.History
import com.example.weatherapplication.model.history.HistoryRecord
import com.google.gson.Gson
import java.io.File

class HistoryFile(context: Context) : History {
    private val path = context.filesDir
    private var file: File = File("$path/history")
    private val gson = Gson()

    override fun addRecord(record: HistoryRecord) {
        if (isUnique(record))
            file.appendText("${gson.toJson(record)}\n")
    }

    override fun getRecords(): List<HistoryRecord> =
        if (file.exists())
            file.readLines().map { gson.fromJson(it, HistoryRecord::class.java) }
        else
            emptyList()

    override fun removeRecord(record: HistoryRecord) {
        val lines = getRecords()
        file.writeText("")
        lines.forEach { if (it != record) addRecord(it) }
    }

    private fun isUnique(record: HistoryRecord): Boolean = record !in getRecords()
}