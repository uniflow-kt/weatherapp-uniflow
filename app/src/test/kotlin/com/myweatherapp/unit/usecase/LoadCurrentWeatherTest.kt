//package com.myweatherapp.unit.usecase
//
//import com.myweatherapp.data.repository.weather.mapToDailyForecasts
//import com.myweatherapp.domain.usecase.weather.GetCurrentWeather
//import com.myweatherapp.domain.usecase.weather.LoadCurrentWeather
//import com.myweatherapp.unit.MockedData
//import io.mockk.coEvery
//import io.mockk.mockk
//import io.uniflow.core.result.success
//import kotlinx.coroutines.runBlocking
//import org.junit.Assert.assertEquals
//import org.junit.Assert.assertNotNull
//import org.junit.Before
//import org.junit.Test
//
//class LoadCurrentWeatherTest {
//
//    lateinit var loadCurrentWeather: LoadCurrentWeather
//    val getCurrentWeather: GetCurrentWeather = mockk(relaxed = true)
//
//    @Before
//    fun before() {
//        loadCurrentWeather = LoadCurrentWeather(getCurrentWeather)
//    }
//
//    @Test
//    fun `successful getCurrentWeather`() = runBlocking {
//        coEvery { getCurrentWeather() } returns success(MockedData.weatherEntities.mapToDailyForecasts())
//        val result = loadCurrentWeather()
//        assertNotNull(result)
//    }
//
//    @Test
//    fun `unsuccessful getCurrentWeather`() = runBlocking {
//        val error = Throwable("Got error")
//        coEvery { getCurrentWeather() } throws error
//
//        try {
//            loadCurrentWeather()
//        } catch (e: Throwable) {
//            assertEquals(error, e)
//        }
//    }
//
//}