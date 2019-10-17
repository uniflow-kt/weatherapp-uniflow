package com.myweatherapp.unit

import com.myweatherapp.common.mapping.generateId
import com.myweatherapp.common.mapping.generateStringId
import com.myweatherapp.common.mapping.now
import com.myweatherapp.data.datasource.database.weather.WeatherEntity
import com.myweatherapp.domain.model.weather.DailyForecast
import com.myweatherapp.domain.model.weather.Humidity
import com.myweatherapp.domain.model.weather.Temperature
import com.myweatherapp.domain.model.weather.Wind

/**
 * Mock Weather Data
 */
object MockedData {

    const val location = "Default_Location"

    val dailyForecasts: List<DailyForecast> = listOf(
            generateDailyForecast("location1", "1", "5"),
            generateDailyForecast("location2", "10", "15"),
            generateDailyForecast("location3", "3", "8")
    )

    val weatherEntities: List<WeatherEntity> = listOf(
            generateWeatherEntity("location1", "1", "5"),
            generateWeatherEntity("location2", "10", "15"),
            generateWeatherEntity("location3", "3", "8")
    )

}

fun generateWeatherEntity(location: String, tempLow: String, tempHigh: String) =
        WeatherEntity(
                generateStringId(),
                location,
                "day",
                "shortText",
                "fullText",
                "iconUrl",
                "icon",
                tempLow,
                tempHigh,
                0,
                "wind_dir",
                0,
                now()
        )

fun generateDailyForecast(location: String, tempLow: String, tempHigh: String) =
        DailyForecast(
                location,
                now().toString(),
                "shortText",
                "fullText",
                "iconUrl",
                "icon",
                Temperature(tempLow, tempHigh),
                Wind(0, "wind_dir"),
                Humidity(0),
                generateId()
        )