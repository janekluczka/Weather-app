package com.example.api_reader_app.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

private const val BASE_URL = "https://community-open-weather-map.p.rapidapi.com/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface OpenWeatherMapAPIService {
    @Headers(
        "x-rapidapi-key: 4707b6485bmsh025db3a12baefbfp1e9fdfjsn53ad0ce245b4",
        "x-rapidapi-host: community-open-weather-map.p.rapidapi.com"
    )
    @GET("forecast/daily?q=wroclaw%2Cpl&cnt=16&units=metric")
    fun get16DayForecastForWroclaw():
            Call<Forecast>
}

object OpenWeatherMapAPI {
    val retrofitService: OpenWeatherMapAPIService =
        retrofit.create(OpenWeatherMapAPIService::class.java)
}

