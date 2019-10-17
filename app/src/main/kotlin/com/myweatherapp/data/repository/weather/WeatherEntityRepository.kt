package com.myweatherapp.data.repository.weather

import arrow.core.Try
import com.myweatherapp.domain.model.weather.DailyForecast
import com.myweatherapp.domain.model.weather.DailyForecastId

/**
 * Weather repository
 */
interface WeatherEntityRepository {
    /**
     * Get weather from given location
     * if location is null, get last weather or default
     */
    suspend fun getWeather(location: String? = null): Try<List<DailyForecast>>

    /**
     * Get weather for given id
     */
    suspend fun getWeatherDetail(id: DailyForecastId): Try<DailyForecast>
}