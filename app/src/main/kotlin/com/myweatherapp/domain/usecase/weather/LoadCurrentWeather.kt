package com.myweatherapp.domain.usecase.weather

import io.uniflow.result.SafeResult

class LoadCurrentWeather(private val getCurrentWeather: GetCurrentWeather) {
    suspend operator fun invoke(): SafeResult<Unit> {
        return getCurrentWeather().map { Unit }
    }
}