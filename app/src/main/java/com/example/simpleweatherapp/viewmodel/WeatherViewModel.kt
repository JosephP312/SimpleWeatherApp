package com.example.simpleweatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.simpleweatherapp.model.ForecastResponse
import com.example.simpleweatherapp.model.SavedCity
import com.example.simpleweatherapp.model.WeatherResponse
import com.example.simpleweatherapp.repository.WeatherDatabase
import com.example.simpleweatherapp.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: WeatherRepository

    init {
        val db = WeatherDatabase.getInstance(application)
        repository = WeatherRepository(db.cityDao())
    }

    val savedCities: LiveData<List<SavedCity>> = repository.getAllSavedCities().asLiveData()

    private val _operationStatus = MutableLiveData<String>()
    val operationStatus: LiveData<String> = _operationStatus

    private val _currentWeather = MutableLiveData<WeatherResponse?>()
    val currentWeather: LiveData<WeatherResponse?> = _currentWeather

    private val _weatherLoading = MutableLiveData<Boolean>()
    val weatherLoading: LiveData<Boolean> = _weatherLoading

    private val _weatherError = MutableLiveData<String?>()
    val weatherError: LiveData<String?> = _weatherError

    private val _forecast = MutableLiveData<ForecastResponse?>()
    val forecast: LiveData<ForecastResponse?> = _forecast

    fun addCity(cityName: String) = viewModelScope.launch {
        if (repository.cityExists(cityName)) {
            _operationStatus.value = "City already saved"
            return@launch
        }
        repository.fetchCurrentWeather(cityName).onSuccess { weather ->
            repository.saveCity(SavedCity(
                cityName = weather.name,
                country = weather.sys.country,
                tempCelsius = weather.main.temp,
                condition = weather.weather.firstOrNull()?.description
                    ?.replaceFirstChar { it.uppercase() } ?: "",
                iconCode = weather.weather.firstOrNull()?.icon ?: "",
                humidity = weather.main.humidity,
                windSpeed = weather.wind.speed,
                tempMin = weather.main.tempMin,
                tempMax = weather.main.tempMax,
                lastUpdated = System.currentTimeMillis()
            ))
            _operationStatus.value = "City added"
        }.onFailure {
            _operationStatus.value = "City not found. Check spelling and try again."
        }
    }

    fun deleteCity(city: SavedCity) = viewModelScope.launch {
        repository.deleteCity(city)
        _operationStatus.value = "City removed"
    }

    fun setDefaultCity(id: Int) = viewModelScope.launch {
        repository.setDefaultCity(id)
    }

    fun loadWeather(city: String) = viewModelScope.launch {
        _weatherLoading.value = true
        _weatherError.value = null
        repository.fetchCurrentWeather(city)
            .onSuccess { _currentWeather.value = it }
            .onFailure { _weatherError.value = it.message }
        _weatherLoading.value = false
    }

    fun loadWeatherByCoords(lat: Double, lon: Double) = viewModelScope.launch {
        _weatherLoading.value = true
        _weatherError.value = null
        repository.fetchCurrentWeatherByCoords(lat, lon)
            .onSuccess { _currentWeather.value = it }
            .onFailure { _weatherError.value = it.message }
        _weatherLoading.value = false
    }

    fun loadForecast(city: String) = viewModelScope.launch {
        repository.fetchForecast(city)
            .onSuccess { _forecast.value = it }
            .onFailure { _weatherError.value = it.message }
    }

    fun loadForecastByCoords(lat: Double, lon: Double) = viewModelScope.launch {
        repository.fetchForecastByCoords(lat, lon)
            .onSuccess { _forecast.value = it }
            .onFailure { _weatherError.value = it.message }
    }

    fun getOutfitRecommendation(tempF: Double, condition: String): String {
        val base = when {
            tempF >= 85 -> "It's hot! Wear light clothes, shorts, and sunscreen."
            tempF >= 70 -> "Nice and warm — a t-shirt and light pants will do."
            tempF >= 55 -> "A bit cool. Consider a light jacket or hoodie."
            tempF >= 40 -> "It's cold — wear a warm jacket and layers."
            else        -> "Very cold! Bundle up with a heavy coat, scarf, and gloves."
        }
        val extra = when {
            "rain" in condition.lowercase() || "drizzle" in condition.lowercase() ->
                " Don't forget an umbrella!"
            "snow" in condition.lowercase() ->
                " Snow boots and a waterproof jacket are a must!"
            "storm" in condition.lowercase() ->
                " Stay indoors if possible — severe weather alert!"
            "clear" in condition.lowercase() || "sunny" in condition.lowercase() ->
                " Great day to be outside!"
            else -> ""
        }
        return base + extra
    }
}