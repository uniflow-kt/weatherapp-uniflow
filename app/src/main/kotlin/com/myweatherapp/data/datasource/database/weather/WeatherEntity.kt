package com.myweatherapp.data.datasource.database.weather

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "weather")
data class WeatherEntity(
        @PrimaryKey
        val id: String,
        val location: String,
        val day: String,
        val shortText: String,
        val fullText: String,
        val iconUrl: String,
        val icon: String,
        val temp_low: String,
        val temp_high: String,
        val wind_kph: Int,
        val wind_dir: String,
        val humidity: Int,
        val date: Date
)
