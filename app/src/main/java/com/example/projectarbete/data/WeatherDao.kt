package com.example.projectarbete.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weatherEntity: WeatherEntity): Long

    @Query("SELECT * FROM weather_table WHERE cityName = :cityName")
    suspend fun getWeather(cityName: String): WeatherEntity?

    @Query("SELECT * FROM weather_table WHERE cityName = :cityName")
    fun getWeatherLiveData(cityName: String): LiveData<WeatherEntity?>

    @Query("SELECT * FROM weather_table ORDER BY id DESC LIMIT 1")
    suspend fun getLatestWeather(): WeatherEntity?

    @Query("SELECT * FROM weather_table")
    fun getAllWeather(): LiveData<List<WeatherEntity>>

    @Query("DELETE FROM weather_table WHERE cityName = :cityName")
    suspend fun deleteCity(cityName: String)





}
