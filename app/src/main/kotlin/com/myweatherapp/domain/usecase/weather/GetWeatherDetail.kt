package com.myweatherapp.domain.usecase.weather

import com.myweatherapp.data.repository.weather.WeatherEntityRepository
import com.myweatherapp.domain.model.weather.DailyForecast
import com.myweatherapp.domain.model.weather.DailyForecastId

class GetWeatherDetail(private val dailyForecastRepository: WeatherEntityRepository) {
    suspend operator fun invoke(id: DailyForecastId): DailyForecast {
        return dailyForecastRepository.getWeatherDetail(id)
    }
}