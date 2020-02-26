package com.myweatherapp.data.repository.weather

import com.myweatherapp.data.datasource.database.weather.WeatherDAO
import com.myweatherapp.data.datasource.database.weather.WeatherEntity
import com.myweatherapp.data.datasource.database.weather.mapToWeatherEntities
import com.myweatherapp.data.datasource.webservice.weather.WeatherDataSource
import com.myweatherapp.data.datasource.webservice.weather.mapToLocation
import com.myweatherapp.data.repository.weather.DefaultParameters.DEFAULT_LANG
import com.myweatherapp.domain.model.weather.DailyForecast
import com.myweatherapp.domain.model.weather.DailyForecastId

class WeatherEntityPersistentRepositoryImpl(
    private val weatherDatasource: WeatherDataSource,
    private val weatherDAO: WeatherDAO
) : WeatherEntityRepository {

    // Get weather from its value
    override suspend fun getWeatherDetail(id: DailyForecastId): DailyForecast =
        weatherDAO.findWeatherById(id.value).mapToDailyForecast()

    // Get weather from latest or default one if the location is null
    // else get new weather
    override suspend fun getWeather(
        location: String?
    ): List<DailyForecast> {
        return when {
            location != null -> getWeatherForLocation(location)
            else -> getWeatherFromLatestData()
        }.mapToDailyForecasts()
    }

    private suspend fun getWeatherFromLatestData(): List<WeatherEntity> {
        val latest = weatherDAO.findLatestWeather()
        return when {
            latest.isEmpty() -> getWeatherForLocation(DefaultParameters.DEFAULT_LOCATION)
            else -> getLatestWeather(latest.first())
        }
    }

    // Find latest weather
    private suspend fun getLatestWeather(latest: WeatherEntity): List<WeatherEntity> =
        weatherDAO.findAllBy(latest.location, latest.date)

    // Get new weather and save it
    private suspend fun getWeatherForLocation(location: String): List<WeatherEntity> {

        val (lat, lng) = weatherDatasource.geocode(location).await().mapToLocation() ?: error(
            "can't flatMap to location: $location")
        val weather = weatherDatasource.weather(lat, lng, DEFAULT_LANG).await().mapToWeatherEntities(location)
        weatherDAO.saveAll(weather)
        return weather
    }
}