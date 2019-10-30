package com.myweatherapp.data.repository.weather

import com.myweatherapp.domain.model.weather.DailyForecast
import com.myweatherapp.domain.model.weather.DailyForecastId
import io.uniflow.result.SafeResult

/**
 * Weather repository
 */
interface WeatherEntityRepository {
    /**
     * Get weather from given location
     * if location is null, get last weather or default
     */
    suspend fun getWeather(location: String? = null): SafeResult<List<DailyForecast>>

    /**
     * Get weather for given id
     */
    suspend fun getWeatherDetail(id: DailyForecastId): SafeResult<DailyForecast>
}