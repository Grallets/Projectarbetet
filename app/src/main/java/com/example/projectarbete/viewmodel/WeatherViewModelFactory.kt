package com.example.projectarbete.viewmodel

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projectarbete.repository.WeatherRepository

class WeatherViewModelFactory(
    private val weatherRepository: WeatherRepository,
    private val apiKey: String,
    private val fragment: Fragment
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(weatherRepository, apiKey) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    fun provideSharedViewModel(): WeatherViewModel {
        return ViewModelProvider(fragment.requireActivity(), this)[WeatherViewModel::class.java]
    }
}


