package com.example.api_reader_app.repositories

import com.example.api_reader_app.database.DailyForecast
import com.example.api_reader_app.database.ForecastDao
import com.example.api_reader_app.network.Forecast
import com.example.api_reader_app.network.OpenWeatherApiService

class ForecastRepositoryImpl(
    private val database: ForecastDao
) : ForecastRepository {

    override fun getAndSaveDailyForecast() {
        val forecast = getForecastFromOpenWeatherAPI()
        if (forecast != null) saveForecastToDatabase(forecast)
    }

    override fun getForecastByDay(key: Long): DailyForecast? {
        return database.getByDay(key)
    }

    override fun getForecastByID(key: Long): DailyForecast? {
        return database.getByID(key)
    }

    private fun getForecastFromOpenWeatherAPI(): Forecast? {
        val call = OpenWeatherApiService.retrofitService.get16DayForecastForWroclaw()
        val execute = call.execute()

        return if (execute.isSuccessful) execute.body()!!
        else null
    }

    private fun saveForecastToDatabase(forecast: Forecast) {
        database.clear()

        for (i in forecast.list.indices) {
            database.insert(
                DailyForecast(
                    i.toLong(),
                    forecast.list[i].temp.day.toInt(),
                    forecast.list[i].temp.night.toInt()
                )
            )
        }
    }

}
