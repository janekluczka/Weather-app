package com.example.api_reader_app.repositories

import android.util.Log
import com.example.api_reader_app.database.DailyForecast
import com.example.api_reader_app.database.ForecastDao
import com.example.api_reader_app.network.Forecast
import com.example.api_reader_app.network.OpenWeatherApiService

class ForecastRepositoryImpl(
    private val database: ForecastDao
) : ForecastRepository {

    override fun getAndSaveDailyForecast() {
        Log.i("ForecastRepositoryImpl", "getAndSaveDailyForecast called")

        val forecast = getForecastFromOpenWeatherApi()
        if (forecast != null) saveForecastToDatabase(forecast)
    }

    override fun getForecastByDay(key: Long): DailyForecast? {
        Log.i("ForecastRepositoryImpl", "getForecastByDay($key) called")

        return database.getByDay(key)
    }

    override fun getForecastByID(key: Long): DailyForecast? {
        Log.i("ForecastRepositoryImpl", "getForecastByID($key) called")

        return database.getByID(key)
    }

    private fun getForecastFromOpenWeatherApi(): Forecast? {
        Log.i("ForecastRepositoryImpl", "getForecastFromOpenWeatherApi called")

        val call = OpenWeatherApiService.retrofitService.get16DayForecastForWroclaw()
        val execute = call.execute()

        return if (execute.isSuccessful) {
            Log.i("ForecastRepositoryImpl", "getForecastFromOpenWeatherApi successful")
            execute.body()!!
        } else {
            Log.i("ForecastRepositoryImpl", "getForecastFromOpenWeatherApi failed")
            null
        }
    }

    private fun saveForecastToDatabase(forecast: Forecast) {
        Log.i("ForecastRepositoryImpl", "saveForecastToDatabase called")

        // clear database before adding new forecast
        database.clear()

        // add new forecast to database
        for (i in forecast.list.indices) {
            Log.i(
                "ForecastRepositoryImpl",
                "getForecastFromOpenWeatherApi inserting DailyForecast nr $i"
            )

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
