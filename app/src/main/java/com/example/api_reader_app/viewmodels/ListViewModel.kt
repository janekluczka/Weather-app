package com.example.api_reader_app.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.api_reader_app.database.DailyForecast
import com.example.api_reader_app.database.ForecastDatabaseDao
import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import okhttp3.*
import java.io.IOException
import java.util.*

@SuppressLint("SimpleDateFormat")
class ListViewModel(
    val database: ForecastDatabaseDao,
    application: Application
    ) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(viewModelJob)

    private val client = OkHttpClient()

    var twoWeeksList = mutableListOf<String>()

    lateinit var forecast: Forecast

    init {
        Log.i("ListViewModel", "ListViewModel created")

        val date = Calendar.getInstance()
        val formatter = SimpleDateFormat("dd.MM.yyyy  EEEE")

        for (i in 1..14) {
            twoWeeksList.add(formatter.format(date.time))
            date.add(Calendar.DATE, 1)
        }

        getData()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun getData() {
        uiScope.launch {
            // clear database
            database.clear()
            // call API and get response
            callAPI()
            // add forecast to empty database
            addForecastToDatabase()
        }
    }

    private suspend fun callAPI() {
        withContext(Dispatchers.IO) {
            Log.i("ListViewModel", "callAPI called")

            val request = Request.Builder()
                .url("https://community-open-weather-map.p.rapidapi.com/forecast/daily?q=wroclaw%2Cpl&cnt=14&units=metric")
                .get()
                .addHeader("x-rapidapi-key", "4707b6485bmsh025db3a12baefbfp1e9fdfjsn53ad0ce245b4")
                .addHeader("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                Log.i("ListViewModel", "newCall called")

                val body = response.body()?.string()
                // gson builder
                val gson = GsonBuilder().create()
                // get forecast from response
                Log.i("ListViewModel", "got forecast")
                forecast = gson.fromJson(body, Forecast::class.java)
            }
        }
    }

    private fun addForecastToDatabase() {
        Log.i("ListViewModel", "addForecastToDatabase called")

        // loop through all days and add them to database
        for (i in (0..13)) {
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

class Forecast(val list: List<Day>)

class Day(val temp: Temperature)

class Temperature(val day: Float, val night: Float)
