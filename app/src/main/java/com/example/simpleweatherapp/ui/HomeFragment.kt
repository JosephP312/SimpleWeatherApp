package com.example.simpleweatherapp.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.simpleweatherapp.adapter.ForecastAdapter
import com.example.simpleweatherapp.databinding.FragmentHomeBinding
import com.example.simpleweatherapp.util.LocationHelper
import com.example.simpleweatherapp.viewmodel.WeatherViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WeatherViewModel by activityViewModels()
    private lateinit var forecastAdapter: ForecastAdapter
    private lateinit var locationHelper: LocationHelper

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (granted) fetchWeatherByLocation() else loadDefaultCity()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationHelper = LocationHelper(requireContext())
        forecastAdapter = ForecastAdapter()
        binding.rvForecast.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = forecastAdapter
        }
        observeViewModel()
        binding.swipeRefreshLayout.setOnRefreshListener { checkLocationAndLoad() }
        checkLocationAndLoad()
    }

    private fun observeViewModel() {
        viewModel.currentWeather.observe(viewLifecycleOwner) { weather ->
            weather ?: return@observe
            binding.tvCityName.text = "${weather.name}, ${weather.sys.country}"
            binding.tvTemperature.text = "${weather.main.temp.toInt()}°F"
            binding.tvCondition.text = weather.weather.firstOrNull()?.description?.replaceFirstChar { it.uppercase() } ?: ""
            binding.tvHumidity.text = "Humidity: ${weather.main.humidity}%"
            binding.tvWind.text = "Wind: ${weather.wind.speed.toInt()} mph"
            binding.tvFeelsLike.text = "Feels like ${weather.main.feelsLike.toInt()}°F"
            binding.tvHighLow.text = "H:${weather.main.tempMax.toInt()}° L:${weather.main.tempMin.toInt()}°"
            Glide.with(this).load("https://openweathermap.org/img/wn/${weather.weather.firstOrNull()?.icon}@2x.png").into(binding.ivWeatherIcon)
            binding.tvOutfitAdvice.text = viewModel.getOutfitRecommendation(weather.main.temp, weather.weather.firstOrNull()?.description ?: "")
            binding.swipeRefreshLayout.isRefreshing = false
            binding.progressBar.visibility = View.GONE
            binding.contentGroup.visibility = View.VISIBLE
        }
        viewModel.forecast.observe(viewLifecycleOwner) { forecast ->
            forecast ?: return@observe
            forecastAdapter.submitList(forecast.list.filter { it.dtTxt.contains("12:00:00") }.take(5))
        }
        viewModel.weatherLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) { binding.progressBar.visibility = View.VISIBLE; binding.contentGroup.visibility = View.GONE }
        }
        viewModel.weatherError.observe(viewLifecycleOwner) { error ->
            error ?: return@observe
            binding.swipeRefreshLayout.isRefreshing = false
            binding.progressBar.visibility = View.GONE
            binding.tvError.text = error
            binding.tvError.visibility = View.VISIBLE
        }
    }

    private fun checkLocationAndLoad() {
        val fine = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val coarse = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        if (fine || coarse) fetchWeatherByLocation()
        else locationPermissionRequest.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
    }

    private fun fetchWeatherByLocation() {
        locationHelper.getLastLocation { location ->
            if (location != null) {
                viewModel.loadWeatherByCoords(location.latitude, location.longitude)
                viewModel.loadForecastByCoords(location.latitude, location.longitude)
            } else loadDefaultCity()
        }
    }

    private fun loadDefaultCity() {
        viewModel.loadWeather("Tampa")
        viewModel.loadForecast("Tampa")
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
