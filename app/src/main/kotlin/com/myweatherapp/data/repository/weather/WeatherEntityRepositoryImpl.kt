package com.myweatherapp.data.repository.weather

import com.myweatherapp.data.datasource.database.weather.WeatherEntity
import com.myweatherapp.data.datasource.database.weather.mapToWeatherEntities
import com.myweatherapp.data.datasource.webservice.weather.WeatherDataSource
import com.myweatherapp.data.datasource.webservice.weather.mapToLocation
import com.myweatherapp.data.repository.weather.DefaultParameters.DEFAULT_LANG
import com.myweatherapp.data.repository.weather.DefaultParameters.DEFAULT_LOCATION
import com.myweatherapp.domain.model.weather.DailyForecast
import com.myweatherapp.domain.model.weather.DailyForecastId

/**
 * Weather repository
 * Make use of WeatherDataSource & add some cache
 */
class WeatherEntityRepositoryImpl(private val weatherDatasource: WeatherDataSource) :
    WeatherEntityRepository {

    private val weatherCache = arrayListOf<WeatherEntity>()

    private fun lastLocationFromCache() = weatherCache.firstOrNull()?.location

    override suspend fun getWeatherDetail(
        id: DailyForecastId): DailyForecast = weatherCache.first { it.id == id.value }.mapToDailyForecast()

    override suspend fun getWeather(
        location: String?
    ): List<DailyForecast> {
        // Take cache
        return if (isAlreadyInCache(location)) weatherCache.mapToDailyForecasts()
        else {
            weatherCache.clear()

            val targetLocation: String = location ?: lastLocationFromCache() ?: DEFAULT_LOCATION

            val (lat, lng) = weatherDatasource.geocode(targetLocation).await().mapToLocation() ?: error(
                "Can't map to location: '$targetLocation'")

            val weather: List<WeatherEntity> = weatherDatasource.weather(lat, lng,
                DEFAULT_LANG).await().mapToWeatherEntities(targetLocation)

            weatherCache.addAll(weather)
            weather.mapToDailyForecasts()
        }
    }

    private fun isAlreadyInCache(location: String?) = location == null && weatherCache.isNotEmpty()
}