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
            viewModel.updateWeatherForCity(cityName)
        }

        viewModel.weatherData.observe(viewLifecycleOwner, Observer { weather ->
            binding.textWeather.text = weather.toString()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
