package com.myweatherapp.domain.usecase.weather


class LoadCurrentWeather(private val getCurrentWeather: GetCurrentWeather) {
    suspend operator fun invoke() {
        getCurrentWeather()
    }
}