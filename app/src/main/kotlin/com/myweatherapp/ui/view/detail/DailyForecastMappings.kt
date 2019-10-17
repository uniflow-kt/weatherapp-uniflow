package com.myweatherapp.ui.view.detail

import com.myweatherapp.domain.model.weather.DailyForecast

fun DailyForecast.mapToDetailState() = DetailState(
        icon,
        day,
        fullText,
        wind.toString(),
        temperature.toString(),
        humidity.toString(),
        colorCode
)