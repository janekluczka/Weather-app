package com.example.api_reader_app

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException
import java.util.*

@SuppressLint("SimpleDateFormat")
class ListViewModel : ViewModel() {

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
    }

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
}

class Forecast(val list: List<Day>)

class Day(val id: Int, val temp: Temperature)

class Temperature(val day: Float, val night: Float)
