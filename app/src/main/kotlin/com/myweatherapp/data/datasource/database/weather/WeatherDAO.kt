package com.myweatherapp.data.datasource.database.weather

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import java.util.*

@Dao
interface WeatherDAO {

    @Insert
    fun saveAll(entities: List<WeatherEntity>)

    @Query("SELECT * FROM weather WHERE id = :id")
    fun findWeatherById(id: String): WeatherEntity

    @Query("SELECT * FROM weather WHERE location = :location AND date = :date")
    fun findAllBy(location: String, date: Date): List<WeatherEntity>

    @Query("SELECT * FROM weather ORDER BY date DESC")
    fun findLatestWeather(): List<WeatherEntity>
}