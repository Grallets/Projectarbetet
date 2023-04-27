package com.example.projectarbete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.projectarbete.api.WeatherApiService
import com.example.projectarbete.data.WeatherDatabase
import com.example.projectarbete.databinding.FragmentSettingsBinding
import com.example.projectarbete.repository.WeatherRepository
import com.example.projectarbete.viewmodel.WeatherViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SettingsFragment : Fragment() {

    private val apiKey = "6c66efae39ef924518354cb720b32e23"
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val weatherDao = WeatherDatabase.getDatabase(requireContext()).weatherDao()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val weatherApiService = retrofit.create(WeatherApiService::class.java)
        val weatherRepository = WeatherRepository(weatherDao, weatherApiService)

        viewModel = ViewModelProvider(requireActivity()).get(WeatherViewModel::class.java)

        binding.buttonUpdateWeather.setOnClickListener {
            val cityName = binding.editTextCity.text.toString()
            if (cityName.isNotEmpty()) {
                viewModel.updateCity(cityName)

            } else {
                Toast.makeText(requireContext(), "Please enter a city name", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
