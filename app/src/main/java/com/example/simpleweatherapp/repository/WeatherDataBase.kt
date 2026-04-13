package com.example.simpleweatherapp.repository

import android.content.Context
import androidx.room.*
import com.example.simpleweatherapp.model.SavedCity
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {
    @Query("SELECT * FROM saved_cities ORDER BY isDefault DESC, cityName ASC")
    fun getAllCities(): Flow<List<SavedCity>>

    @Query("SELECT * FROM saved_cities WHERE isDefault = 1 LIMIT 1")
    suspend fun getDefaultCity(): SavedCity?

    @Query("SELECT * FROM saved_cities WHERE cityName = :name LIMIT 1")
    suspend fun getCityByName(name: String): SavedCity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: SavedCity): Long

    @Update
    suspend fun updateCity(city: SavedCity)

    @Delete
    suspend fun deleteCity(city: SavedCity)

    @Query("UPDATE saved_cities SET isDefault = 0")
    suspend fun clearDefaultCity()

    @Query("UPDATE saved_cities SET isDefault = 1 WHERE id = :id")
    suspend fun setDefaultCity(id: Int)
}

@Database(entities = [SavedCity::class], version = 1, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao

    companion object {
        @Volatile private var INSTANCE: WeatherDatabase? = null

        fun getInstance(context: Context): WeatherDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDatabase::class.java,
                    "weather_database"
                ).fallbackToDestructiveMigration().build().also { INSTANCE = it }
            }
        }
    }
}