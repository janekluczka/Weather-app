package com.example.api_reader_app.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://community-open-weather-map.p.rapidapi.com/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

object OpenWeatherMapAPI {

    val retrofitService: OpenWeatherMapAPIService =
        retrofit.create(OpenWeatherMapAPIService::class.java)

}

