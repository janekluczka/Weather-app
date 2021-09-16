package com.example.api_reader_app.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ForecastDao {

    @Insert
    fun insert(day: DailyForecast)

    @Update
    fun update(day: DailyForecast)

    @Query("SELECT * from daily_forecast_table WHERE dayID = :key")
    fun getByID(key: Long): DailyForecast?

    @Query("SELECT * from daily_forecast_table WHERE date = :key")
    fun getByDay(key: Long): DailyForecast?

    @Query("DELETE FROM daily_forecast_table")
    fun clear()

}