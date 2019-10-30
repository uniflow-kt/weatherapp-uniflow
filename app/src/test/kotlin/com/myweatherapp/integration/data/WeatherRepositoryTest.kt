package com.myweatherapp.integration.data

import com.myweatherapp.data.repository.weather.WeatherEntityRepository
import com.myweatherapp.di.testWeatherApp
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject

class WeatherRepositoryTest : AutoCloseKoinTest() {

    val repository by inject<WeatherEntityRepository>()

    val location = "paris"

    @Before
    fun before() {
        startKoin {
            printLogger()
            fileProperties()
            modules(testWeatherApp)
        }
    }

    @Test
    fun testGetWeatherSuccess() = runBlocking {
        val list = repository.getWeather(location)
        assertNotNull(list)
    }

    @Test
    fun testCachedWeather() = runBlocking {
        val l1 = repository.getWeather("paris")
        val l2 = repository.getWeather("toulouse")
        val l3 = repository.getWeather()

        assertEquals(l3, l2)
        assertNotSame(l1, l2)
    }

    @Test
    fun testGetDetail() = runBlocking {
        repository.getWeather(location)
        val list = repository.getWeather(location)
        val first = list.get().first()
        val detail = repository.getWeatherDetail(first.id)
        assertEquals(first, detail.get())
    }
}