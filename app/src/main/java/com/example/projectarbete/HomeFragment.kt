package com.example.projectarbete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.projectarbete.api.WeatherApiService
import com.example.projectarbete.data.WeatherDatabase
import com.example.projectarbete.databinding.FragmentHomeBinding
import com.example.projectarbete.repository.WeatherRepository
import com.example.projectarbete.viewmodel.WeatherViewModel
import com.example.projectarbete.viewmodel.WeatherViewModelFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: WeatherViewModel
    private val apiKey = "6c66efae39ef924518354cb720b32e23"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //buttons
        val btnSettings: Button = view.findViewById(R.id.btnSettings)
        val btnMap: Button = view.findViewById(R.id.btnMap)

        val weatherDao = WeatherDatabase.getDatabase(requireContext()).weatherDao()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val weatherApiService = retrofit.create(WeatherApiService::class.java)
        val weatherRepository = WeatherRepository(weatherDao, weatherApiService)

        viewModel = WeatherViewModelFactory(weatherRepository, apiKey, this).provideSharedViewModel()


        initWeatherData()


        viewModel.weatherData.observe(viewLifecycleOwner, Observer { weather ->
            if (weather != null) {
                binding.textCityName.text = weather.cityName
                val tempInt = weather.temperature.toInt()
                binding.textTemperature.text = "$tempInt°C"
                binding.textTempMin.text = "Min Temp: ${weather.tempMin.toInt()}°C"
                binding.textTempMax.text = "Max Temp: ${weather.tempMax.toInt()}°C"
            } else {
                println("error")
            }
        })



        btnSettings.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
        }
        btnMap.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_mapFragment)
        }
    }

    private fun initWeatherData() {
        viewModel.fetchWeatherFromDatabase()

        if (viewModel.weatherData.value == null) {
            val initialCity = "Stockholm"
            viewModel.fetchWeatherFromApi(initialCity)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
