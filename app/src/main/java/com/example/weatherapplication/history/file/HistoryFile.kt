package com.example.weatherapplication.history.file

import android.content.Context
import com.example.weatherapplication.history.History
import com.example.weatherapplication.history.HistoryRecord
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File

class HistoryFile(context: Context) : History {
    private val file: File = File("${context.filesDir}/history")
    private val gson = Gson()

    override fun addRecord(record: HistoryRecord): Completable =
        getRecords()
            .filter { record !in it }
            .doOnSuccess {
                file.appendText("${gson.toJson(record)}\n")
            }
            .ignoreElement()

    override fun getRecords(): Single<List<HistoryRecord>> =
        Single.fromCallable {
            if (file.exists())
                file.readLines().map { gson.fromJson(it, HistoryRecord::class.java) }
            else
                emptyList()
        }

    override fun removeRecord(record: HistoryRecord): Completable =
        getRecords()
            .doOnSuccess { records ->
                file.writeText("")
                records.forEach { if (it != record) file.appendText("${gson.toJson(it)}\n") }
            }
            .ignoreElement()
}