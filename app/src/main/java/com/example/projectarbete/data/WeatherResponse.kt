package com.example.projectarbete.data

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("name")
    val name: String?, // Add this line

    @SerializedName("main")
    val main: Main,

    @SerializedName("wind")
    val wind: Wind,

    @SerializedName("clouds")
    val clouds: Clouds,

    @SerializedName("sys")
    val sys: Sys
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

data class Wind(
    @SerializedName("speed")
    val speed: Double,

    @SerializedName("deg")
    val degrees: Int
)

data class Clouds(
    @SerializedName("all")
    val cloudPct: Int
)

data class Sys(
    @SerializedName("country")
    val country: String,

    @SerializedName("sunrise")
    val sunrise: Long,

    @SerializedName("sunset")
    val sunset: Long
)
