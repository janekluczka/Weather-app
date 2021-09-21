package com.example.api_reader_app.repositories

import com.example.api_reader_app.database.DailyForecast

interface ForecastRepository {

    fun getAndSaveDailyForecast()
    fun getForecastByDay(key: Long): DailyForecast?
    fun getForecastByID(key: Long): DailyForecast?

}