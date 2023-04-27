package com.example.projectarbete.data

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("name")
    val name: String?,
    @SerializedName("main")
    val main: Main
)

data class Main(
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("temp_min")
    val tempMin: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("humidity")
    val humidity: Int
)

