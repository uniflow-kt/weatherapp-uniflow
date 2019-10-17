package com.myweatherapp.data.repository.weather

import com.myweatherapp.data.datasource.database.weather.WeatherEntity
import com.myweatherapp.domain.model.weather.*

fun WeatherEntity.mapToDailyForecast() =
        DailyForecast(
                location,
                day,
                shortText,
                fullText,
                iconUrl,
                getWeatherCodeForIcon(icon),
                Temperature(temp_low, temp_high),
                Wind(wind_kph, wind_dir),
                Humidity(humidity),
                DailyForecastId(id)
        )

fun List<WeatherEntity>.mapToDailyForecasts() =
        this.map { it.mapToDailyForecast() }