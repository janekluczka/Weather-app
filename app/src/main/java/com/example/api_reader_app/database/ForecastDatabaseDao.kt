package com.example.api_reader_app.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ForecastDatabaseDao {
    @Insert
    fun insert(day: ForecastDay)

    @Update
    fun update(day: ForecastDay)

    @Query("SELECT * from day_forecast_table WHERE dayID = :key")
    fun get(key: Int): ForecastDay?

    @Query("DELETE FROM day_forecast_table")
    fun clear()
}