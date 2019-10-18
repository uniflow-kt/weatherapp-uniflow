package com.myweatherapp.unit.ui

import arrow.core.Failure
import arrow.core.Success
import com.myweatherapp.domain.usecase.weather.GetCurrentWeather
import com.myweatherapp.domain.usecase.weather.GetWeatherForGivenLocation
import com.myweatherapp.ui.view.weather.WeatherListViewModel
import com.myweatherapp.ui.view.weather.uimodel.WeatherListUIEvent
import com.myweatherapp.ui.view.weather.uimodel.mapToWeatherListState
import com.myweatherapp.unit.MockedData.dailyForecasts
import com.myweatherapp.unit.mvvm.ViewModelTest
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verifySequence
import io.uniflow.android.test.MockedViewObserver
import io.uniflow.android.test.mockObservers
import io.uniflow.core.flow.Event
import io.uniflow.core.flow.UIEvent
import io.uniflow.core.flow.UIState
import org.junit.Before
import org.junit.Test

class WeatherHeaderViewModelMockTest : ViewModelTest() {

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
    fun testDisplayList() {
        coEvery { getCurrentWeather() } returns Success(dailyForecasts)

        viewModel.getWeather()

        verifySequence {
            view.states.onChanged(UIState.Empty)
            view.states.onChanged(UIState.Loading)
            view.states.onChanged(dailyForecasts.mapToWeatherListState())
        }
    }

    @Test
    fun testDisplayListFailed() {
        val error = Exception("Got an error")
        coEvery { getCurrentWeather() } returns Failure(error)

        viewModel.getWeather()

        verifySequence {
            view.states.onChanged(UIState.Empty)
            view.states.onChanged(UIState.Loading)
            view.states.onChanged(UIState.Failed(error = error, state = UIState.Loading))
        }
    }

    @Test
    fun testSearchNewLocation() {
        val location = "new location"
        coEvery { getCurrentWeather() } returns Success(dailyForecasts)
        coEvery { getWeatherForLocation(location) } returns Success(dailyForecasts)

        viewModel.getWeather()
        viewModel.loadNewLocation(location)

        verifySequence {
            view.states.onChanged(UIState.Empty)
            view.states.onChanged(UIState.Loading)
            view.states.onChanged(dailyForecasts.mapToWeatherListState())
            view.events.onChanged(Event(WeatherListUIEvent.ProceedLocation(location)))
            view.states.onChanged(dailyForecasts.mapToWeatherListState())
        }
    }

    @Test
    fun testSearchNewLocationWrongState() {
        val location = "new location"
        viewModel.loadNewLocation(location)

        verifySequence {
            view.states.onChanged(UIState.Empty)
            view.events.onChanged(Event(UIEvent.BadOrWrongState(UIState.Empty)))
        }
    }

    @Test
    fun testSearchNewLocationFailed() {
        val location = "new location"
        val error = Exception("Got an error")

        coEvery { getCurrentWeather() } returns Success(dailyForecasts)
        coEvery { getWeatherForLocation(location) } returns Failure(error)

        viewModel.getWeather()
        viewModel.loadNewLocation(location)

        verifySequence {
            view.states.onChanged(UIState.Empty)
            view.states.onChanged(UIState.Loading)
            view.states.onChanged(dailyForecasts.mapToWeatherListState())
            view.events.onChanged(Event(WeatherListUIEvent.ProceedLocation(location)))
            view.events.onChanged(Event(WeatherListUIEvent.ProceedLocationFailed(location, error)))
        }
    }

}