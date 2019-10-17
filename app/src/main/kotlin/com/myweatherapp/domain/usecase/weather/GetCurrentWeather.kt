package com.myweatherapp.domain.usecase.weather

import arrow.core.Try
import com.myweatherapp.data.repository.weather.WeatherEntityRepository
import com.myweatherapp.domain.model.weather.DailyForecast

class GetCurrentWeather(private val dailyForecastRepository: WeatherEntityRepository) {
    suspend operator fun invoke(): Try<List<DailyForecast>> {
        return dailyForecastRepository.getWeather()
    }
}