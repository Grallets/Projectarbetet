package com.example.projectarbete


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import com.google.gson.Gson
import com.example.projectarbete.WeatherResponse


class HomeFragment : Fragment() {

    private val client = OkHttpClient()
    private lateinit var weatherButton: Button
    private lateinit var weatherTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        weatherButton = view.findViewById(R.id.weatherButton)
        weatherTextView = view.findViewById(R.id.weatherTextView)

        weatherButton.setOnClickListener {
            fetchWeatherForCity("Seattle")
        }
    }

    @SuppressLint("SetTextI18n")
    private fun fetchWeatherForCity(city: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val request = Request.Builder()
                .url("https://weather-by-api-ninjas.p.rapidapi.com/v1/weather?city=$city")
                .get()
                .addHeader("X-RapidAPI-Key", "96ef1af237msh410217abbe77cefp1eb8f6jsnda47755cc4bc")
                .addHeader("X-RapidAPI-Host", "weather-by-api-ninjas.p.rapidapi.com")
                .build()

            try {
                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()

                if (response.isSuccessful && responseBody != null) {
                    val gson = Gson()
                    val weatherResponse = gson.fromJson(responseBody, WeatherResponse::class.java)

                    withContext(Dispatchers.Main) {
                        weatherTextView.text = "Temperature: ${weatherResponse.temp}°C\n" +
                                "Feels Like: ${weatherResponse.feels_like}°C\n" +
                                "Humidity: ${weatherResponse.humidity}%\n" +
                                "Wind Speed: ${weatherResponse.wind_speed} m/s\n" +
                                "Wind Degrees: ${weatherResponse.wind_degrees}°\n" +
                                "Cloud Cover: ${weatherResponse.cloud_pct}%"
                    }
                } else {
                    // Handle the error case
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Failed to fetch weather data", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                // Handle exceptions like network errors
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }}