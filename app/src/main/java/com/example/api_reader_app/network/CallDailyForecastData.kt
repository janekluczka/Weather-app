package com.example.api_reader_app.network

data class Forecast(
    val list: List<Day>
)

data class Day(
    val temp: Temperature
)

data class Temperature(
    val day: Float,
    val night: Float
)
