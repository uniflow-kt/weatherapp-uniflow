package com.myweatherapp.domain.model.weather

import java.util.*

/**
 * Represents our weather forecast for one day
 */
data class DailyForecast(
        val location: String,
        val day: String,
        val shortText: String,
        val fullText: String,
        val iconUrl: String,
        val icon: String,
        val temperature: Temperature,
        val wind: Wind,
        val humidity: Humidity,
        val id: DailyForecastId = DailyForecastId()
) {
    val colorCode: Int by lazy { colorCodeFromTemperatureRange(temperature.high.toInt()) }

    private fun colorCodeFromTemperatureRange(avg: Int): Int {
        return when {
            avg in 0..8 -> 1
            avg in 8..13 -> 2
            avg in 14..20 -> 3
            avg > 21 -> 4
            else -> 0
        }
    }
}

data class Wind(val kph: Int, val dir: String) {
    override fun toString(): String {
        return "$kph KPH $dir"
    }
}

data class Temperature(val low: String, val high: String) {
    override fun toString(): String {
        return "$low°C - $high°C"
    }
}

data class Humidity(val humidity: Int) {
    override fun toString(): String {
        return "$humidity %"
    }
}

inline class DailyForecastId(val value: String = UUID.randomUUID().toString())