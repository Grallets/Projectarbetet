package com.example.projectarbete.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "cityName")
    val cityName: String,

    @ColumnInfo(name = "temperature")
    val temperature: Double,

    @ColumnInfo(name = "temp_min")
    val tempMin: Double,

    @ColumnInfo(name = "temp_max")
    val tempMax: Double,
)
