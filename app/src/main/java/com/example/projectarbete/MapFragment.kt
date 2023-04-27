package com.example.projectarbete

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.projectarbete.databinding.FragmentMapBinding
import com.example.projectarbete.viewmodel.WeatherViewModel

class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WeatherViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllWeather().observe(viewLifecycleOwner, Observer { weatherList ->
            if (weatherList.isNotEmpty()) {
                val cityNames = weatherList.map { it.cityName }
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, cityNames)
                binding.listViewCities.adapter = adapter
            } else {
                binding.listViewCities.adapter = null
            }
        })

        binding.listViewCities.setOnItemLongClickListener { _, _, position, _ ->
            val cityName = binding.listViewCities.getItemAtPosition(position) as String
            showDeleteCityDialog(cityName)
            true
        }
    }

    private fun showDeleteCityDialog(cityName: String) {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("Delete City")
        alertDialogBuilder.setMessage("Are you sure you want to delete $cityName?")
        alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
            viewModel.deleteCity(cityName)
            Toast.makeText(requireContext(), "$cityName deleted", Toast.LENGTH_SHORT).show()
        }
        alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        alertDialogBuilder.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
