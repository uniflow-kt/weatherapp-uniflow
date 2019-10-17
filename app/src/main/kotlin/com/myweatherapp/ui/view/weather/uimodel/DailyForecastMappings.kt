package com.myweatherapp.ui.view.weather.uimodel

import com.myweatherapp.domain.model.weather.DailyForecast
import com.myweatherapp.ui.view.weather.adapter.WeatherUIItem


fun List<DailyForecast>.mapToWeatherListState(): WeatherListState {
    return if (isEmpty()) error("weather list should not be empty")
    else {
        val dailyForecast = first()
        val first = dailyForecast.mapToWeatherUIItem()
        val location = dailyForecast.location
        val lastDays = takeLast(size - 1).mapToWeatherUIItems()
        WeatherListState(location, first, lastDays)
    }
}

fun DailyForecast.mapToWeatherUIItem(): WeatherUIItem = WeatherUIItem(
        id.value,
        icon,
        day,
        temperature.toString(),
        shortText,
        colorCode
)

fun List<DailyForecast>.mapToWeatherUIItems(): List<WeatherUIItem> = map { it.mapToWeatherUIItem() }