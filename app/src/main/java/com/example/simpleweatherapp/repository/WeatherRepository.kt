package com.example.simpleweatherapp.repository

import com.example.simpleweatherapp.BuildConfig
import com.example.simpleweatherapp.model.ForecastResponse
import com.example.simpleweatherapp.model.SavedCity
import com.example.simpleweatherapp.model.WeatherResponse
import com.example.simpleweatherapp.network.RetrofitInstance
import kotlinx.coroutines.flow.Flow

class WeatherRepository(private val cityDao: CityDao) {
    private val api = RetrofitInstance.api
    private val apiKey = BuildConfig.WEATHER_API_KEY

    fun getAllSavedCities(): Flow<List<SavedCity>> = cityDao.getAllCities()
    suspend fun getDefaultCity(): SavedCity? = cityDao.getDefaultCity()
    suspend fun saveCity(city: SavedCity): Long = cityDao.insertCity(city)
    suspend fun updateCity(city: SavedCity) = cityDao.updateCity(city)
    suspend fun deleteCity(city: SavedCity) = cityDao.deleteCity(city)
    suspend fun cityExists(name: String): Boolean = cityDao.getCityByName(name) != null

    suspend fun setDefaultCity(id: Int) {
        cityDao.clearDefaultCity()
        cityDao.setDefaultCity(id)
    }

    suspend fun fetchCurrentWeather(city: String): Result<WeatherResponse> = try {
        val r = api.getCurrentWeather(city, apiKey)
        if (r.isSuccessful && r.body() != null) Result.success(r.body()!!)
        else Result.failure(Exception("City not found"))
    } catch (e: Exception) { Result.failure(e) }

    suspend fun fetchCurrentWeatherByCoords(lat: Double, lon: Double): Result<WeatherResponse> = try {
        val r = api.getCurrentWeatherByCoords(lat, lon, apiKey)
        if (r.isSuccessful && r.body() != null) Result.success(r.body()!!)
        else Result.failure(Exception("Location fetch failed"))
    } catch (e: Exception) { Result.failure(e) }

    suspend fun fetchForecast(city: String): Result<ForecastResponse> = try {
        val r = api.getForecast(city, apiKey)
        if (r.isSuccessful && r.body() != null) Result.success(r.body()!!)
        else Result.failure(Exception("Forecast fetch failed"))
    } catch (e: Exception) { Result.failure(e) }

    suspend fun fetchForecastByCoords(lat: Double, lon: Double): Result<ForecastResponse> = try {
        val r = api.getForecastByCoords(lat, lon, apiKey)
        if (r.isSuccessful && r.body() != null) Result.success(r.body()!!)
        else Result.failure(Exception("Forecast by coords failed"))
    } catch (e: Exception) { Result.failure(e) }
}
