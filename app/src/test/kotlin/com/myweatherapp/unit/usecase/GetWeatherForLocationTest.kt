package com.myweatherapp.unit.usecase

import com.myweatherapp.data.repository.weather.WeatherEntityRepository
import com.myweatherapp.data.repository.weather.mapToDailyForecasts
import com.myweatherapp.domain.usecase.weather.GetWeatherForGivenLocation
import com.myweatherapp.unit.MockedData
import io.mockk.coEvery
import io.mockk.mockk
import io.uniflow.result.SafeResult
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetWeatherForLocationTest {

    lateinit var getWeatherForLocation: GetWeatherForGivenLocation
    val dailyForecastRepository: WeatherEntityRepository = mockk()
    val location = "a location"

    @Before
    fun before() {
        getWeatherForLocation = GetWeatherForGivenLocation(dailyForecastRepository)
    }

    @Test
    fun `successful getWeatherForLocation`() = runBlocking {
        coEvery { dailyForecastRepository.getWeather(location) } returns SafeResult.Success(MockedData.weatherEntities.mapToDailyForecasts())

        val result = getWeatherForLocation(location)

        assertEquals(MockedData.weatherEntities.mapToDailyForecasts(), result.get())
    }

    @Test
    fun `unsuccessful getWeatherForLocation`() = runBlocking<Unit> {
        val error = Throwable("Got error")

        coEvery { dailyForecastRepository.getWeather(location) } throws error

        try {
            getWeatherForLocation(location)
        } catch (e: Throwable) {
            assertEquals(error, e)
        }
    }

}