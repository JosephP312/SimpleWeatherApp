package com.example.simpleweatherapp.model

package com.example.simpleweatherapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_cities")
data class SavedCity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val cityName: String,
    val country: String,
    val tempCelsius: Double = 0.0,
    val condition: String = "",
    val iconCode: String = "",
    val humidity: Int = 0,
    val windSpeed: Double = 0.0,
    val tempMin: Double = 0.0,
    val tempMax: Double = 0.0,
    val isDefault: Boolean = false,
    val alertsEnabled: Boolean = true,
    val lastUpdated: Long = 0L
)