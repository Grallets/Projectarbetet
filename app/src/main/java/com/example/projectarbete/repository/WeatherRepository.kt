package com.example.projectarbete.repository

import androidx.lifecycle.LiveData
import com.example.projectarbete.api.WeatherApiService
import com.example.projectarbete.data.WeatherDao
import com.example.projectarbete.data.WeatherEntity
import com.example.projectarbete.data.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository(private val weatherDao: WeatherDao, private val weatherApiService: WeatherApiService) {

    val weatherData: LiveData<WeatherEntity?> = weatherDao.getWeatherLiveData("Stockholm")

    suspend fun insertWeather(weatherEntity: WeatherEntity) {
        withContext(Dispatchers.IO) {
            weatherDao.insertWeather(weatherEntity)
        }
    }
    suspend fun getLatestWeatherFromDatabase(): WeatherEntity? {
        return weatherDao.getLatestWeather()
    }

    suspend fun deleteCity(cityName: String) {
        withContext(Dispatchers.IO) {
            weatherDao.deleteCity(cityName)
        }
    }


    suspend fun getWeather(cityName: String): WeatherEntity? {
        return withContext(Dispatchers.IO) {
            weatherDao.getWeather(cityName)
        }
    }
    fun getAllWeather(): LiveData<List<WeatherEntity>> {
        return weatherDao.getAllWeather()
    }


    suspend fun getWeatherFromApi(cityName: String, apiKey: String): WeatherResponse? {
        return try {
            val response = weatherApiService.getWeatherByCity(cityName, apiKey)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}
