package com.myweatherapp.di

import com.myweatherapp.common.file.JsonReader
import com.myweatherapp.common.file.JavaReader
import org.koin.dsl.module


/**
 * Local java json getCurrentWeather
 */
val JUnitMockModule = module(override = true) {
    single<JsonReader> { JavaReader() }
}

val testWeatherApp = offlineWeatherApp + JUnitMockModule