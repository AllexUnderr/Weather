package com.example.weatherapplication.model.history

import android.content.Context
import com.google.gson.Gson
import java.io.File

class History(context: Context) {
    private val path = context.filesDir
    private var file: File = File("$path/history")
    private val gson = Gson()

    fun addRecord(record: HistoryRecord) {
        file.appendText("${gson.toJson(record)}\n")
    }

    fun getRecords(): List<HistoryRecord> =
        if (file.exists())
            file.readLines().map { gson.fromJson(it, HistoryRecord::class.java) }
        else
            emptyList()

    fun removeRecord(record: HistoryRecord) {
        val lines = getRecords()
        file.writeText("")
        lines.forEach { if (it != record) addRecord(it) }
    }
}