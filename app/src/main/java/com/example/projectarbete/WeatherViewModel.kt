package com.example.projectarbete

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectarbete.api.WeatherApiService
import com.example.projectarbete.data.WeatherResponse
import com.example.projectarbete.repository.WeatherRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val API_KEY = "6c66efae39ef924518354cb720b32e23"

    private val _weatherData = MutableLiveData<WeatherResponse?>()
    val weatherData: LiveData<WeatherResponse?> = _weatherData
    private val error = MutableLiveData<String?>()

    private val weatherRepository = WeatherRepository()

    private val _text = MutableLiveData<String>().apply {
        value = "Weather data not available"
    }
    val text: LiveData<String> = _text

    fun updateWeather(cityName: String) {
        viewModelScope.launch {
            val weather = weatherRepository.getWeather(cityName)
            if (weather != null) {
                _weatherData.value = weather
            } else {
                // Handle the error case when weather is null
            }
        }

    }
}
