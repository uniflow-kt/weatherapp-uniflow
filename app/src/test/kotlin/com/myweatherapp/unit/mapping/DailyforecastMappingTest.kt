package com.myweatherapp.unit.mapping

import com.myweatherapp.data.repository.weather.mapToDailyForecast
import com.myweatherapp.domain.model.weather.Humidity
import com.myweatherapp.domain.model.weather.Temperature
import com.myweatherapp.domain.model.weather.Wind
import com.myweatherapp.unit.MockedData
import org.junit.Assert.assertEquals
import org.junit.Test

class DailyforecastMappingTest {

    @Test
    fun `WeatherEntity to Dailyforecast`() {
        // take an entity
        val entity = MockedData.weatherEntities.first()
        // flatMap it
        val dailyForecast = entity.mapToDailyForecast()

        assertEquals(entity.id, dailyForecast.id.value)
        assertEquals(entity.location, dailyForecast.location)
        assertEquals(entity.day, dailyForecast.day)
        assertEquals(entity.fullText, dailyForecast.fullText)
        assertEquals(entity.iconUrl, dailyForecast.iconUrl)
        assertEquals(Temperature(entity.temp_low, entity.temp_high), dailyForecast.temperature)
        assertEquals(Wind(entity.wind_kph, entity.wind_dir), dailyForecast.wind)
        assertEquals(Humidity(entity.humidity), dailyForecast.humidity)

    }
}