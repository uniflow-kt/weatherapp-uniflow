package com.myweatherapp.unit.ui

import com.myweatherapp.domain.model.weather.DailyForecastId
import com.myweatherapp.domain.usecase.weather.GetWeatherDetail
import com.myweatherapp.ui.view.detail.DetailViewModel
import com.myweatherapp.ui.view.detail.mapToDetailState
import com.myweatherapp.unit.MockedData
import com.myweatherapp.unit.mvvm.ViewModelTest
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verifySequence
import io.uniflow.android.test.MockedViewObserver
import io.uniflow.android.test.mockObservers
import io.uniflow.core.flow.UIState
import io.uniflow.result.SafeResult
import org.junit.Before
import org.junit.Test


class DetailViewModelMockTest : ViewModelTest() {

    lateinit var detailViewModel: DetailViewModel
    lateinit var view: MockedViewObserver

    val getWeatherDetail: GetWeatherDetail = mockk()
    val id = DailyForecastId("ID")

    @Before
    fun before() {
        detailViewModel = DetailViewModel(id, getWeatherDetail)
        view = detailViewModel.mockObservers()
    }

    @Test
    fun testGetLastWeather() {
        val weather = MockedData.dailyForecasts.first()

        coEvery { getWeatherDetail(id) } returns SafeResult.Success(weather)

        detailViewModel.getDetail()

        verifySequence {
            view.states.onChanged(UIState.Empty)
            view.states.onChanged(weather.mapToDetailState())
        }
    }

    @Test
    fun testGeLasttWeatherFailed() {
        val error = Exception("Got error")

        coEvery { getWeatherDetail(id) } returns SafeResult.Failure(error)

        detailViewModel.getDetail()

        verifySequence {
            view.states.onChanged(UIState.Empty)
            view.states.onChanged(UIState.Failed(error = error))
        }
    }
}
