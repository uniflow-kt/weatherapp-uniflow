package com.myweatherapp.domain.usecase.weather

import com.myweatherapp.data.repository.weather.WeatherEntityRepository
import com.myweatherapp.domain.model.weather.DailyForecast

class GetWeatherForGivenLocation(private val dailyForecastRepository: WeatherEntityRepository) {
    suspend operator fun invoke(location: String): List<DailyForecast> {
        return dailyForecastRepository.getWeather(location)
    }
}