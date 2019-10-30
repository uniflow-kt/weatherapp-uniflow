package com.myweatherapp.unit.usecase

import com.myweatherapp.data.repository.weather.WeatherEntityRepository
import com.myweatherapp.data.repository.weather.mapToDailyForecast
import com.myweatherapp.domain.model.weather.DailyForecastId
import com.myweatherapp.domain.usecase.weather.GetWeatherDetail
import com.myweatherapp.unit.MockedData
import io.mockk.coEvery
import io.mockk.mockk
import io.uniflow.result.SafeResult
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetWeatherDetailTest {

    val dailyForecastRepository: WeatherEntityRepository = mockk()
    lateinit var getWeatherDetail: GetWeatherDetail
    val id = DailyForecastId("ID")

    @Before
    fun before() {
        getWeatherDetail = GetWeatherDetail(dailyForecastRepository)
    }

    @Test
    fun `successful getWeatherDetail`() = runBlocking {
        val weather = MockedData.weatherEntities.first()

        coEvery { dailyForecastRepository.getWeatherDetail(id) } returns SafeResult.Success(weather.mapToDailyForecast())

        val result = getWeatherDetail(id)

        assertEquals(weather.mapToDailyForecast(), result.get())
    }

    @Test
    fun `unsuccessful getWeatherDetail`() = runBlocking<Unit> {
        val error = Throwable("Got error")

        coEvery { dailyForecastRepository.getWeatherDetail(id) } throws error

        try {
            getWeatherDetail(id)
        } catch (e: Throwable) {
            assertEquals(error, e)
        }
    }

}