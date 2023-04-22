package com.example.projectarbete.data

data class WeatherResponse(
    val cloud_pct: Int,
    val temp: Int,
    val feels_like: Int,
    val humidity: Int,
    val min_temp: Int,
    val max_temp: Int,
    val wind_speed: Double,
    val wind_degrees: Int,
    val sunrise: Long,
    val sunset: Long
)
