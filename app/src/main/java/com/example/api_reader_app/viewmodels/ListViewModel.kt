package com.example.api_reader_app.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.api_reader_app.database.DailyForecast
import com.example.api_reader_app.database.ForecastDatabaseDao
import com.example.api_reader_app.network.Forecast
import com.example.api_reader_app.network.OpenWeatherMapAPI
import kotlinx.coroutines.*
import java.util.*

@SuppressLint("SimpleDateFormat")
class ListViewModel(
    val database: ForecastDatabaseDao,
    application: Application
    ) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(viewModelJob)

    var daysList = mutableListOf<String>()

    private lateinit var forecast: Forecast

    var numberOfDays = 7

    init {
        Log.i("ListViewModel", "ListViewModel created")
        updateDaysList()
        getData()
    }

    fun updateDaysList() {
        Log.i("ListViewModel", "updateDaysList called")
        val date = Calendar.getInstance()
        val formatter = SimpleDateFormat("dd.MM.yyyy  EEEE")

        daysList.clear()

        for (i in 0..numberOfDays) {
            daysList.add(formatter.format(date.time))
            date.add(Calendar.DATE, 1)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun getData() {
        Log.i("ListViewModel", "getData called")
        uiScope.launch {
            // clear database
            database.clear()
            // call API and get response
            callAPI()
            // add forecast to empty database
            addForecastToDatabase()
        }
    }

    private fun callAPI() {
        Log.i("ListViewModel", "callAPI called")
        val call = OpenWeatherMapAPI.retrofitService.get16DayForecastForWroclaw()
        forecast = call.execute().body()!!
    }

    private fun addForecastToDatabase() {
        Log.i("ListViewModel", "addForecastToDatabase called")

        // loop through all days and add them to database
        for (i in (0..15)) {
            Log.i("ListViewModel", "Creating and inserting new forecast day $i")
            // create new forecast for a day to add
                val newDay = DailyForecast(
                    i.toLong(), // date
                    forecast.list[i].temp.day.toInt(),
                    forecast.list[i].temp.night.toInt()
                )
            // add forecast to database
            database.insert(newDay)
        }
    }
}