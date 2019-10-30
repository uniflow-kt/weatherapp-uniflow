package com.myweatherapp.domain.usecase.weather

import com.myweatherapp.data.repository.weather.WeatherEntityRepository
import com.myweatherapp.domain.model.weather.DailyForecast
import com.myweatherapp.domain.model.weather.DailyForecastId
import io.uniflow.result.SafeResult

class GetWeatherDetail(private val dailyForecastRepository: WeatherEntityRepository) {
    suspend operator fun invoke(id: DailyForecastId): SafeResult<DailyForecast> {
        return dailyForecastRepository.getWeatherDetail(id)
    }
}