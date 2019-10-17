package com.myweatherapp.data.repository.weather

import arrow.core.Success
import arrow.core.Try
import com.myweatherapp.data.datasource.database.weather.WeatherEntity
import com.myweatherapp.data.datasource.database.weather.mapToWeatherEntities
import com.myweatherapp.data.datasource.webservice.weather.WeatherDataSource
import com.myweatherapp.data.datasource.webservice.weather.mapToLocation
import com.myweatherapp.data.repository.weather.DefaultParameters.DEFAULT_LANG
import com.myweatherapp.data.repository.weather.DefaultParameters.DEFAULT_LOCATION
import com.myweatherapp.domain.model.weather.DailyForecast
import com.myweatherapp.domain.model.weather.DailyForecastId
import io.uniflow.core.result.networkCall
import io.uniflow.core.result.onSuccess

/**
 * Weather repository
 * Make use of WeatherDataSource & add some cache
 */
class WeatherEntityRepositoryImpl(private val weatherDatasource: WeatherDataSource) :
        WeatherEntityRepository {

    private val weatherCache = arrayListOf<WeatherEntity>()

    private fun lastLocationFromCache() = weatherCache.firstOrNull()?.location

    override suspend fun getWeatherDetail(id: DailyForecastId): Try<DailyForecast> = Try { weatherCache.first { it.id == id.value } }.map { it.mapToDailyForecast() }

    override suspend fun getWeather(
            location: String?
    ): Try<List<DailyForecast>> {
        // Take cache
        return if (isAlreadyInCache(location)) Success(weatherCache.mapToDailyForecasts())
        else {
            weatherCache.clear()

            val targetLocation: String = location ?: lastLocationFromCache() ?: DEFAULT_LOCATION

            networkCall { weatherDatasource.geocode(targetLocation).await() }
                    .map { it.mapToLocation() ?: error("Can't map to location: $it") }
                    .flatMap { (lat, lng) -> networkCall { weatherDatasource.weather(lat, lng, DEFAULT_LANG).await() } }
                    .map { it.mapToWeatherEntities(targetLocation) }
                    .onSuccess { weatherCache.addAll(it) }
                    .map { it.mapToDailyForecasts() }
        }
    }

    private fun isAlreadyInCache(location: String?) = location == null && weatherCache.isNotEmpty()
}