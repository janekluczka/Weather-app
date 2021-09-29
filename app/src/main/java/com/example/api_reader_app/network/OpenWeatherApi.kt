package com.example.api_reader_app.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface OpenWeatherApi {

    @Headers(
        "x-rapidapi-key: 4707b6485bmsh025db3a12baefbfp1e9fdfjsn53ad0ce245b4",
        "x-rapidapi-host: community-open-weather-map.p.rapidapi.com"
    )
    @GET("forecast/daily?q=wroclaw%2Cpl&cnt=16&units=metric")
    suspend fun get16DayForecastForWroclaw(): Forecast
    //    fun get16DayForecastForWroclaw(): Call<Forecast>


}