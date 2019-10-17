package com.myweatherapp.data.repository.weather

import arrow.core.Try
import com.myweatherapp.data.datasource.database.weather.WeatherDAO
import com.myweatherapp.data.datasource.database.weather.WeatherEntity
import com.myweatherapp.data.datasource.database.weather.mapToWeatherEntities
import com.myweatherapp.data.datasource.webservice.weather.WeatherDataSource
import com.myweatherapp.data.datasource.webservice.weather.mapToLocation
import com.myweatherapp.data.repository.weather.DefaultParameters.DEFAULT_LANG
import com.myweatherapp.domain.model.weather.DailyForecast
import com.myweatherapp.domain.model.weather.DailyForecastId
import io.uniflow.core.result.databaseCall
import io.uniflow.core.result.networkCall
import io.uniflow.core.result.onSuccess

class WeatherEntityPersistentRepositoryImpl(
        private val weatherDatasource: WeatherDataSource,
        private val weatherDAO: WeatherDAO
) : WeatherEntityRepository {

    // Get weather from its value
    override suspend fun getWeatherDetail(id: DailyForecastId): Try<DailyForecast> = databaseCall { weatherDAO.findWeatherById(id.value) }.map { it.mapToDailyForecast() }

    // Get weather from latest or default one if the location is null
    // else get new weather
    override suspend fun getWeather(
            location: String?
    ): Try<List<DailyForecast>> {
        return when {
            location != null -> getWeatherForLocation(location)
            else -> getWeatherFromLatestData()
        }.map { it.mapToDailyForecasts() }
    }

    private suspend fun getWeatherFromLatestData(): Try<List<WeatherEntity>> {
        return databaseCall { weatherDAO.findLatestWeather() }
                .flatMap { latest ->
                    when {
                        latest.isEmpty() -> getWeatherForLocation(DefaultParameters.DEFAULT_LOCATION)
                        else -> getLatestWeather(latest.first())
                    }
                }
    }

    // Find latest weather
    private suspend fun getLatestWeather(latest: WeatherEntity): Try<List<WeatherEntity>> = databaseCall { weatherDAO.findAllBy(latest.location, latest.date) }

    // Get new weather and save it
    private suspend fun getWeatherForLocation(location: String): Try<List<WeatherEntity>> {
        return networkCall { weatherDatasource.geocode(location).await() }
                .map { it.mapToLocation() ?: error("can't flatMap to location: $it") }
                .flatMap { (lat, lng) -> networkCall { weatherDatasource.weather(lat, lng, DEFAULT_LANG).await() } }
                .map { it.mapToWeatherEntities(location) }
                .onSuccess { databaseCall { weatherDAO.saveAll(it) } }
    }
}