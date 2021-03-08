package com.myweatherapp.unit.ui

import com.myweatherapp.domain.usecase.weather.LoadCurrentWeather
import com.myweatherapp.ui.view.splash.SplashViewModel
import com.myweatherapp.unit.mvvm.ViewModelTest
import io.mockk.coEvery
import io.mockk.mockk
import io.uniflow.android.test.TestViewObserver
import io.uniflow.android.test.createTestObserver
import io.uniflow.core.flow.data.UIEvent
import io.uniflow.core.flow.data.UIState
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SplashViewModelMockTest : ViewModelTest() {

    lateinit var viewModel: SplashViewModel
    lateinit var view: TestViewObserver
    val loadCurrentWeather: LoadCurrentWeather = mockk()

    @Before
    fun before() {
        viewModel = SplashViewModel(loadCurrentWeather)
        view = viewModel.createTestObserver()
    }

    @Test
    fun testGetLastWeather() = runBlocking {
        coEvery { loadCurrentWeather() } returns Unit

        viewModel.getLastWeather()

        view.verifySequence(
            UIState.Empty,
            UIEvent.Loading,
            UIState.Success
        )
    }

    @Test
    fun testGetLasttWeatherFailed() = runBlocking {
        val error = Exception("boom")
        coEvery { loadCurrentWeather() } throws error

        viewModel.getLastWeather()

        view.verifySequence(
            UIState.Empty,
            UIEvent.Loading,
            UIState.Failed("getLastWeather failed", error = error)
        )
    }
}