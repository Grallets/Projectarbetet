package com.example.projectarbete

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.projectarbete.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        val initialCity = "Stockholm"
        viewModel.updateWeather(initialCity)

        binding.buttonUpdateWeather.setOnClickListener {
            val cityName = binding.editTextCity.text.toString()
            if (cityName.isNotEmpty()) {
                binding.textCityName.text = cityName
                viewModel.updateWeather(cityName)
            } else {
                Toast.makeText(requireContext(), "Please enter a city name", Toast.LENGTH_SHORT).show()
            }
        }


        viewModel.weatherData.observe(viewLifecycleOwner, Observer { weather ->
            if (weather != null) {
                binding.textCityName.text = weather.name
                val tempInt = weather.main.temp.toInt()
                binding.textTemperature.text = "Temperature: $tempInt°C"
                binding.textHumidity.text = "Humidity: ${weather.main.humidity}%"
                binding.textFeelsLike.text = "Feels Like: ${weather.main.feelsLike.toInt()}°C"
                binding.textTempMin.text = "Min Temperature: ${weather.main.tempMin.toInt()}°C"
                binding.textTempMax.text = "Max Temperature: ${weather.main.tempMax.toInt()}°C"
                binding.textPressure.text = "Pressure: ${weather.main.pressure} hPa"
            } else {
                // Show an error message or a default message if weather data is not available
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

