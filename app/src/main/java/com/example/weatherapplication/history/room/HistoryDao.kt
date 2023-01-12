package com.example.weatherapplication.history.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapplication.history.History
import com.example.weatherapplication.history.HistoryRecord

@Dao
interface HistoryDao : History {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun addRecord(record: HistoryRecord)

    @Query("SELECT * FROM HistoryRecord")
    override fun getRecords(): List<HistoryRecord>

    @Delete
    override fun removeRecord(record: HistoryRecord)
}