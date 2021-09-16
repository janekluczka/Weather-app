package com.example.api_reader_app.repositories

import com.example.api_reader_app.database.DailyForecast
import com.example.api_reader_app.database.ForecastDao
import com.example.api_reader_app.network.Forecast
import com.example.api_reader_app.network.OpenWeatherMapAPI

class ForecastRepository (
        private val database: ForecastDao
    ) {

    fun getAndSaveDailyForecast() {
        val forecast = getForecastFromOpenWeatherAPI()
        if (forecast != null) {
            saveForecastToDatabase(forecast)
        }
    }

    fun getForecastByDay(key: Long): DailyForecast? {
        return database.getByDay(key)
    }

    fun getForecastByID(key: Long): DailyForecast? {
        return database.getByID(key)
    }

    private fun getForecastFromOpenWeatherAPI(): Forecast? {
        val call = OpenWeatherMapAPI.retrofitService.get16DayForecastForWroclaw()
        val execute = call.execute()

        return if (execute.isSuccessful) {
            execute.body()!!
        } else {
            null
        }
    }

    private fun saveForecastToDatabase(forecast: Forecast) {
        database.clear()

        // loop through all days and add them to database
        for (i in (0..15)) {
            // add forecast to database
            database.insert(
                DailyForecast(
                    i.toLong(),
                    forecast.list[i].temp.day.toInt(),
                    forecast.list[i].temp.night.toInt()
            ))
        }
    }

}
