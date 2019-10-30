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
import io.uniflow.android.test.MockedViewObserver
import io.uniflow.android.test.mockObservers
import io.uniflow.core.flow.UIState
import io.uniflow.result.SafeResult.Companion.failure
import io.uniflow.result.SafeResult.Companion.success
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class WeatherListViewModelMockTest : ViewModelTest() {

    lateinit var viewModel: WeatherListViewModel

    lateinit var view: MockedViewObserver
    val getCurrentWeather: GetCurrentWeather = mockk()
    val getWeatherForLocation: GetWeatherForGivenLocation = mockk()

    @Before
    fun before() {
        viewModel = WeatherListViewModel(getCurrentWeather, getWeatherForLocation)
        view = viewModel.mockObservers()
    }

    @Test
    fun testDisplayList() = runBlocking {
        coEvery { getCurrentWeather() } returns success(MockedData.dailyForecasts)

        viewModel.getWeather()
        verifySequence {
            view.states.onChanged(UIState.Empty)
            view.states.onChanged(MockedData.dailyForecasts.mapToWeatherListState())
        }
    }

    @Test
    fun testDisplayListFailed() = runBlocking {
        val error = Exception("Got an error")
        coEvery { getCurrentWeather() } returns failure(error)

        viewModel.getWeather()
        verifySequence {
            view.states.onChanged(UIState.Empty)
            view.states.onChanged(UIState.Failed(error = error))
        }
    }

}