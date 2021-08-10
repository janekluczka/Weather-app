package com.example.api_reader_app.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "day_forecast_table")
data class ForecastDay (
    @ColumnInfo(name = "date")
    var date: Long = 0L,

    @ColumnInfo(name = "day_temperature")
    val dayTemperature: Int = -273,

    @ColumnInfo(name = "night_temperature")
    val nightTemperature: Int = -273
) {
    @PrimaryKey(autoGenerate = true)
    var dayID: Long = 0L
}