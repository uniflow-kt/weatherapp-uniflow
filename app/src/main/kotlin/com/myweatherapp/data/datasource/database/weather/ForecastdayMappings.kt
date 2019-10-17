package com.myweatherapp.data.datasource.database.weather

import com.myweatherapp.common.mapping.generateStringId
import com.myweatherapp.data.datasource.webservice.weather.Forecastday_
import java.util.*

fun Forecastday_.mapToWeatherEntity(location: String, date: Date): WeatherEntity = WeatherEntity(
        generateStringId(),
        location,
        this.date?.weekday ?: "",
        conditions ?: "",
        "",
        iconUrl ?: "",
        icon ?: "",
        low?.celsius ?: "",
        high!!.celsius!!,
        avewind?.kph ?: 0,
        avewind?.dir ?: "",
        avehumidity ?: 0,
        date
)
