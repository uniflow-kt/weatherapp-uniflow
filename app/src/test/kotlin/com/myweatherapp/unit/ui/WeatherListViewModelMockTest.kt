package com.myweatherapp.unit.ui

import com.myweatherapp.domain.usecase.weather.GetCurrentWeather
import com.myweatherapp.domain.usecase.weather.GetWeatherForGivenLocation
import com.myweatherapp.ui.view.weather.WeatherListViewModel
import com.myweatherapp.ui.view.weather.uimodel.mapToWeatherListState
import com.myweatherapp.unit.MockedData
import com.myweatherapp.unit.mvvm.ViewModelTest
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verifySequence
import io.uniflow.android.test.MockViewObserver
import io.uniflow.android.test.mockObservers
import io.uniflow.core.flow.data.UIState
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class WeatherListViewModelMockTest : ViewModelTest() {

    lateinit var viewModel: WeatherListViewModel

    lateinit var view: MockViewObserver
    val getCurrentWeather: GetCurrentWeather = mockk()
    val getWeatherForLocation: GetWeatherForGivenLocation = mockk()

    @Before
    fun before() {
        viewModel = WeatherListViewModel(getCurrentWeather, getWeatherForLocation)
        view = viewModel.mockObservers()
    }

    @Test
    fun testDisplayList() = runBlocking {
        coEvery { getCurrentWeather() } returns MockedData.dailyForecasts

        viewModel.getWeather()
        verifySequence {
            view.states.onChanged(UIState.Empty)
            view.states.onChanged(MockedData.dailyForecasts.mapToWeatherListState())
        }
    }

    @Test
    fun testDisplayListFailed() = runBlocking {
        val error = Exception("boom")
        coEvery { getCurrentWeather() } throws error

        viewModel.getWeather()
        verifySequence {
            view.states.onChanged(UIState.Empty)
            view.states.onChanged(UIState.Failed("getWeather failed", error = error))
        }
    }
}