package com.example.weatherapplication.model.history.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapplication.model.history.History
import com.example.weatherapplication.model.history.HistoryRecord

@Dao
interface HistoryDao : History {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun addRecord(record: HistoryRecord)

    @Query("SELECT * FROM HistoryRecord")
    override fun getRecords(): List<HistoryRecord>

    @Delete
    override fun removeRecord(record: HistoryRecord)
}