package com.myweatherapp.domain.usecase.weather

import com.myweatherapp.data.repository.weather.WeatherEntityRepository
import com.myweatherapp.domain.model.weather.DailyForecast
import io.uniflow.result.SafeResult

class GetWeatherForGivenLocation(private val dailyForecastRepository: WeatherEntityRepository) {
    suspend operator fun invoke(location: String): SafeResult<List<DailyForecast>> {
        return dailyForecastRepository.getWeather(location)
    }
}