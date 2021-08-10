package com.example.api_reader_app.list

import android.annotation.SuppressLint
import android.app.Application
import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.api_reader_app.database.ForecastDatabaseDao
import com.example.api_reader_app.database.ForecastDay
import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import okhttp3.*
import java.io.IOException
import java.util.*

@SuppressLint("SimpleDateFormat")
class ListViewModel(
    val database: ForecastDatabaseDao,
    application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()

//    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val client = OkHttpClient()

    var next14Days = mutableListOf<String>()

    lateinit var forecast: Forecast

    init {
        Log.i("ListViewModel", "ListViewModel created")

        val date = Calendar.getInstance()
        val formatter = SimpleDateFormat("dd.MM.yyyy  EEEE")

        for (i in 1..14) {
            next14Days.add(formatter.format(date.time))
            date.add(Calendar.DATE, 1)
        }

        callAPI()
//        addForecastToDatabase()
    }

//    private suspend fun insert(day: ForecastDay) {
//        withContext(Dispatchers.IO) {
//            database.insert(day)
//        }
//    }
//
//    private suspend fun update(day: ForecastDay) {
//        withContext(Dispatchers.IO) {
//            database.update(day)
//        }
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//        viewModelJob.cancel()
//    }
//
//    fun onClear() {
//        uiScope.launch {
//            clear()
//        }
//    }
//
//    suspend fun clear() {
//        withContext(Dispatchers.IO) {
//            database.clear()
//        }
//    }


    fun callAPI() {
        Log.i("ListViewModel", "callAPI called")

        val request = Request.Builder()
            .url("https://community-open-weather-map.p.rapidapi.com/forecast/daily?q=wroclaw%2Cpl&lat=35&lon=139&cnt=14&units=metric")
            .get()
            .addHeader("x-rapidapi-key", "4707b6485bmsh025db3a12baefbfp1e9fdfjsn53ad0ce245b4")
            .addHeader("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                // response body
                val body = response.body()?.string()
                // gson builder
                val gson = GsonBuilder().create()
                // get forecast from response
                forecast = gson.fromJson(body, Forecast::class.java)
            }

            override fun onFailure(call: Call, e: IOException) {
                //Toast.makeText()
            }
        })
    }

//    fun addForecastToDatabase() {
//        Log.i("ListVIewModel", "addForecastToDatabase called")
//
//        uiScope.launch {
//            Log.i("ListVIewModel", "Looping through days")
//            // loop through all days and add them to database
//            for (i in (0..13)) {
//                Log.i("ListVIewModel", "Creating new forecast day")
//                // create new forecast for a day to add
//                val newDay = ForecastDay(
//                    0L,
//                    forecast.list[i].temp.day.toInt(),
//                    forecast.list[i].temp.night.toInt()
//                )
//                Log.i("ListVIewModel", "Adding to database")
//                // add forecast to database
//                insert(newDay)
//            }
//        }
//    }
}

class Forecast(val list: List<Day>)

class Day(val temp: Temperature)

class Temperature(val day: Float, val night: Float)
