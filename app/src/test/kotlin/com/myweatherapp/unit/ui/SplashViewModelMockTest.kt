package com.myweatherapp.unit.ui

import com.myweatherapp.domain.usecase.weather.LoadCurrentWeather
import com.myweatherapp.ui.view.splash.SplashViewModel
import com.myweatherapp.unit.mvvm.ViewModelTest
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verifySequence
import io.uniflow.android.test.MockedViewObserver
import io.uniflow.android.test.mockObservers
import io.uniflow.core.flow.UIState
import io.uniflow.result.SafeResult
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SplashViewModelMockTest : ViewModelTest() {

    lateinit var viewModel: SplashViewModel
    lateinit var view: MockedViewObserver
    val loadCurrentWeather: LoadCurrentWeather = mockk()

    @Before
    fun before() {
        viewModel = SplashViewModel(loadCurrentWeather)
        view = viewModel.mockObservers()
    }

    @Test
    fun testGetLastWeather() = runBlocking {
        coEvery { loadCurrentWeather() } returns SafeResult.Success(Unit)

        viewModel.getLastWeather()

        verifySequence {
            view.states.onChanged(UIState.Empty)
            view.states.onChanged(UIState.Success)
        }
    }

    @Test
    fun testGetLasttWeatherFailed() = runBlocking {
        val error = Exception("Got an error")
        coEvery { loadCurrentWeather() } returns SafeResult.Failure(error)

        viewModel.getLastWeather()

        verifySequence {
            view.states.onChanged(UIState.Empty)
            view.states.onChanged(UIState.Failed(error = error))
        }
    }
}