package com.example.simpleweatherapp.model

package com.example.simpleweatherapp.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val name: String,
    val main: Main,
    val weather: List<Weather>,
    val wind: Wind,
    val sys: Sys,
    val visibility: Int,
    val dt: Long
)

data class Main(
    val temp: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    val humidity: Int,
    @SerializedName("temp_min") val tempMin: Double,
    @SerializedName("temp_max") val tempMax: Double,
    val pressure: Int
)

data class Weather(val id: Int, val main: String, val description: String, val icon: String)
data class Wind(val speed: Double, val deg: Int)
data class Sys(val country: String, val sunrise: Long, val sunset: Long)
data class ForecastResponse(val city: City, val list: List<ForecastItem>)
data class City(val name: String, val country: String)

data class ForecastItem(
    val dt: Long,
    val main: Main,
    val weather: List<Weather>,
    val wind: Wind,
    @SerializedName("dt_txt") val dtTxt: String
)