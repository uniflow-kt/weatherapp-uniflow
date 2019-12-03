package com.myweatherapp.di

import androidx.room.Room
import com.myweatherapp.data.datasource.database.WeatherDatabase
import com.myweatherapp.data.repository.weather.WeatherEntityPersistentRepositoryImpl
import com.myweatherapp.data.repository.weather.WeatherEntityRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val roomDatabaseModule = module {

    // Weather Room Data Repository
    single<WeatherEntityRepository>(override = true) { WeatherEntityPersistentRepositoryImpl(get(), get()) }
    // Room Database
    single {
        Room.databaseBuilder(androidApplication(), WeatherDatabase::class.java, "weather-db")
                .build()
    }
    // Expose WeatherDAO
    single { get<WeatherDatabase>().weatherDAO() }
}
