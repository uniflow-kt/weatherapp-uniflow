package com.myweatherapp.unit.usecase

import arrow.core.Success
import com.myweatherapp.data.repository.weather.WeatherEntityRepository
import com.myweatherapp.data.repository.weather.mapToDailyForecasts
import com.myweatherapp.domain.usecase.weather.GetCurrentWeather
import com.myweatherapp.unit.MockedData
import io.mockk.coEvery
import io.mockk.mockk
import io.uniflow.core.result.get
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetCurrentWeatherTest {

    val dailyForecastRepository: WeatherEntityRepository = mockk()
    lateinit var getCurrentWeather: GetCurrentWeather

    @Before
    fun before() {
        getCurrentWeather = GetCurrentWeather(dailyForecastRepository)
    }

    @Test
    fun `successful getCurrentWeather`() = runBlocking {
        coEvery { dailyForecastRepository.getWeather() } returns Success(MockedData.weatherEntities.mapToDailyForecasts())

        val result = getCurrentWeather()

        assertEquals(MockedData.weatherEntities.mapToDailyForecasts(), result.get())
    }

    @Test
    fun `unsuccessful getCurrentWeather`() = runBlocking<Unit> {
        val error = Throwable("Got error")

        coEvery { dailyForecastRepository.getWeather() } throws error

        try {
            getCurrentWeather()
        } catch (e: Throwable) {
            assertEquals(error, e)
        }
    }
}