package com.example.api_reader_app.network

class Forecast(
    val list: List<Day>
)

class Day(
    val temp: Temperature
)

class Temperature(
    val day: Float,
    val night: Float
)
