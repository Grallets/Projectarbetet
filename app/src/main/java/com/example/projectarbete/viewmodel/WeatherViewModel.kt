package com.example.projectarbete.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectarbete.data.WeatherEntity
import com.example.projectarbete.repository.WeatherRepository
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers

class WeatherViewModel(private val weatherRepository: WeatherRepository, private val apiKey: String) : ViewModel() {
    private val _weatherData = MutableLiveData<WeatherEntity?>()
    val weatherData: MutableLiveData<WeatherEntity?> = _weatherData

    fun insertWeather(weatherEntity: WeatherEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.insertWeather(weatherEntity)
        }
    }


    fun getAllWeather(): LiveData<List<WeatherEntity>> {
        return weatherRepository.getAllWeather()
    }

    fun deleteCity(cityName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.deleteCity(cityName)
        }
    }


    fun getWeather(cityName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val fetchedWeather = weatherRepository.getWeather(cityName)
            _weatherData.postValue(fetchedWeather)
        }
    }
    fun updateCity(cityName: String) {
        fetchWeatherFromApi(cityName)
    }

    fun fetchWeatherFromDatabase() {
        viewModelScope.launch {
            weatherData.postValue(weatherRepository.getLatestWeatherFromDatabase())
        }
    }

    fun fetchWeatherFromApi(cityName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val fetchedWeather = weatherRepository.getWeatherFromApi(cityName, apiKey)
            if (fetchedWeather != null) {
                val weatherEntity = WeatherEntity(
                    cityName = fetchedWeather.name ?: "",
                    temperature = fetchedWeather.main.temp,
                    tempMin = fetchedWeather.main.tempMin,
                    tempMax = fetchedWeather.main.tempMax
                )
                insertWeather(weatherEntity)
                _weatherData.postValue(weatherEntity)
            } else {
                // Handle API fetch failure
            }
        }
    }

}
