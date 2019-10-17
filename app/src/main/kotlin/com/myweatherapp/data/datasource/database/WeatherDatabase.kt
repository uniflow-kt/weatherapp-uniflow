package com.myweatherapp.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.myweatherapp.data.datasource.database.weather.WeatherDAO
import com.myweatherapp.data.datasource.database.weather.WeatherEntity

@Database(entities = [WeatherEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDAO(): WeatherDAO
}