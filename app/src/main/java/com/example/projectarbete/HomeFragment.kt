package com.example.projectarbete

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.projectarbete.WeatherViewModel
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

        binding.buttonUpdateWeather.setOnClickListener {
            val cityName = binding.editTextCity.text.toString()
            viewModel.updateWeatherData(cityName)
        }

        viewModel.weatherData.observe(viewLifecycleOwner, Observer { weather ->
            if (weather != null) {
                binding.textTemperature.text = "Temperature: ${weather.temp}"
                binding.textHumidity.text = "Humidity: ${weather.humidity}"
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

