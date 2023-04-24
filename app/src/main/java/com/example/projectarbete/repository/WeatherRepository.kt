package com.example.projectarbete.repository

import com.example.projectarbete.api.WeatherApiService
import com.example.projectarbete.data.WeatherResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRepository {
    private val API_KEY = "6c66efae39ef924518354cb720b32e23"

    private fun createWeatherApiService(): WeatherApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(WeatherApiService::class.java)
    }

    suspend fun getWeather(cityName: String): WeatherResponse? {
        val weatherApiService = createWeatherApiService()
        val response = weatherApiService.getWeatherByCity(cityName, API_KEY)
        return if (response.isSuccessful) response.body() else null
    }
}
