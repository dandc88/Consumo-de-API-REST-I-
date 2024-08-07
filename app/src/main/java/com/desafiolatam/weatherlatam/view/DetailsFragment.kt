package com.desafiolatam.weatherlatam.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.desafiolatam.weatherlatam.R
import com.desafiolatam.weatherlatam.WeatherApplication
import com.desafiolatam.weatherlatam.data.CELSIUS
import com.desafiolatam.weatherlatam.data.ITEM_ID
import com.desafiolatam.weatherlatam.databinding.FragmentDetailsBinding
import com.desafiolatam.weatherlatam.view.viewmodel.WeatherViewModel
import com.desafiolatam.weatherlatam.view.viewmodel.WeatherViewModelFactory

class DetailsFragment : Fragment() {

    private val viewModel: WeatherViewModel by viewModels {
        WeatherViewModelFactory((activity?.application as WeatherApplication).repository)
    }

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private var weatherInfoId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)

        val weatherData = arguments?.getStringArray("WEATHER_DATA")
        weatherData?.let {
            populateWeatherData(it)
        }

        /*savedInstanceState?.getInt(ITEM_ID)?.let { weatherInfoId = it } //Esto se usará más adelante para buscar ka iformacion desde la base de datos
        arguments?.getInt(ITEM_ID)?.let { weatherInfoId = it }
        Log.d("DetailsFragment", "Received ID: $weatherInfoId")

        getWeatherData(weatherInfoId)*/



        navigateToSettings()
        return binding.root
    }

    private fun populateWeatherData(data: Array<String>) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val unit = sharedPref.getString(getString(R.string.settings_temperature_unit), CELSIUS) ?: CELSIUS

        val formattedData = viewModel.getFormattedWeatherData(data, unit)

        binding.cityName.text = formattedData[0]
        binding.currentTemp.text = formattedData[1]
        binding.maximumTemp.text = getString(R.string.max_temp, formattedData[2])
        binding.minimumTemp.text = getString(R.string.min_temp, formattedData[3])
        binding.pressure.text = getString(R.string.pressure, formattedData[4])
        binding.humidity.text = getString(R.string.humidity, formattedData[5])
        binding.windSpeed.text = getString(R.string.wind_speed, formattedData[6])
        binding.sunrise.text = getString(R.string.sunrise, formattedData[7])
        binding.sunset.text = getString(R.string.sunset, formattedData[8])
    }

    /* Se utilizará cuando se tomen los datos desde la base de datos
    private fun getWeatherData(id: Int) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val unit = sharedPref.getString(getString(R.string.settings_temperature_unit), CELSIUS)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.getWeatherDataById(id).collectLatest { weather ->
                    if (weather != null) {
                        binding.currentTemp.text =
                            if (unit == CELSIUS) weather.currentTemp.toString() else weather.currentTemp.toFahrenheit()
                                .toString()
                        binding.maximumTemp.text = getString(R.string.max_temp, weather.maxTemp.toString())
                        binding.minimumTemp.text = getString(R.string.min_temp, weather.minTemp.toString())
                        binding.pressure.text = getString(R.string.pressure, weather.pressure.toString())
                        binding.humidity.text = getString(R.string.humidity, weather.humidity.toString())
                        binding.windSpeed.text = getString(R.string.wind_speed, weather.windSpeed.toString())
                        binding.sunrise.text = getString(R.string.sunrise, weather.sunrise.toShortDateString())
                        binding.sunset.text = getString(R.string.sunset, weather.sunset.toShortDateString())
                        binding.cityName.text = weather.cityName
                    } else {
                        Log.d("DetailsFragment", "No weather data found for ID: $id")
                    }
                }
            }
        }
    }*/

    private fun navigateToSettings() {
        binding.btnSettings.setOnClickListener {
            findNavController().navigate(R.id.action_detailsFragment_to_settingsFragment)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(ITEM_ID, weatherInfoId)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}