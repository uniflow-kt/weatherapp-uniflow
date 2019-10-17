package com.myweatherapp.domain.usecase.weather

import arrow.core.Try

class LoadCurrentWeather(private val getCurrentWeather: GetCurrentWeather) {
    suspend operator fun invoke(): Try<Unit> {
        return getCurrentWeather().map { Unit }
    }
}