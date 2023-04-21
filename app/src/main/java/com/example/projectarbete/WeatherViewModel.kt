package com.example.projectarbete

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectarbete.api.WeatherApiService
import com.example.projectarbete.data.WeatherResponse
import com.example.projectarbete.WeatherData
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val API_KEY = "YOUR_API_KEY" // Replace with your actual API key

    val weatherData = MutableLiveData<WeatherData>()
    private val error = MutableLiveData<String?>()

    private fun createWeatherApiService(): WeatherApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(WeatherApiService::class.java)
    }

    private val _text = MutableLiveData<String>().apply {
        value = "Weather data not available"
    }
    val text: LiveData<String> = _text

    fun updateWeatherData(city: String) {
        viewModelScope.launch {
            val weatherApiService = createWeatherApiService()
            try {
                val response = weatherApiService.getWeatherByCity(city, API_KEY)
                if (response.isSuccessful) {
                    weatherData.postValue(response.body())
                    error.postValue(null)
                } else {
                    error.postValue("Weather data not available")
                }
            } catch (e: Exception) {
                error.postValue("Weather data not available")
            }
        }
    }
}
