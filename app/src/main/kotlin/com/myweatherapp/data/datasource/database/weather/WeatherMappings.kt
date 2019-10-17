package com.myweatherapp.data.datasource.database.weather

import com.myweatherapp.common.mapping.now
import com.myweatherapp.data.datasource.webservice.weather.Forecastday
import com.myweatherapp.data.datasource.webservice.weather.Forecastday_
import com.myweatherapp.data.datasource.webservice.weather.Weather
import com.myweatherapp.domain.model.weather.PREFIX

/**
 * Extract Weather DailyForecast list from Weather
 */
fun Weather.mapToWeatherEntities(location: String, date: java.util.Date= now()): List<WeatherEntity> {
    val txtList: List<Forecastday> = forecast?.txtForecast?.forecastday.orEmpty()
    return forecast?.simpleforecast?.forecastday.orEmpty()
            .map { f: Forecastday_ ->
                f.mapToWeatherEntity(location, date)
            }
            .map { f ->
                f.copy(
                        fullText = txtList.firstOrNull { it.title ?: "" == f.day }?.fcttext ?: ""
                )
            }
            .filter { f -> !f.icon.startsWith(PREFIX) }
}