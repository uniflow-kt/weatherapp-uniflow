package com.myweatherapp.common.mapping

import com.myweatherapp.domain.model.weather.DailyForecastId
import java.util.*

fun generateId() = DailyForecastId()

fun generateStringId() = generateId().value

fun now() = Date()