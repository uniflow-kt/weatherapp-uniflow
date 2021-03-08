package com.myweatherapp.unit.ui

import com.myweatherapp.domain.model.weather.DailyForecastId
import com.myweatherapp.domain.usecase.weather.GetWeatherDetail
import com.myweatherapp.ui.view.detail.DetailViewModel
import com.myweatherapp.ui.view.detail.mapToDetailState
import com.myweatherapp.unit.MockedData
import com.myweatherapp.unit.mvvm.ViewModelTest
import io.mockk.coEvery
import io.mockk.mockk
import io.uniflow.android.test.TestViewObserver
import io.uniflow.android.test.createTestObserver
import io.uniflow.core.flow.data.UIState
import org.junit.Before
import org.junit.Test

class DetailViewModelMockTest : ViewModelTest() {

    lateinit var detailViewModel: DetailViewModel
    lateinit var view: TestViewObserver

    val getWeatherDetail: GetWeatherDetail = mockk()
    val id = DailyForecastId("ID")

    @Before
    fun before() {
        detailViewModel = DetailViewModel(id, getWeatherDetail)
        view = detailViewModel.createTestObserver()
    }

    @Test
    fun testGetLastWeather() {
        val weather = MockedData.dailyForecasts.first()

        coEvery { getWeatherDetail(id) } returns weather

        detailViewModel.getDetail()

        view.verifySequence(
            UIState.Empty,
            weather.mapToDetailState()
        )
    }

    @Test
    fun testGeLasttWeatherFailed() {
        val error = Exception("boom")

        coEvery { getWeatherDetail(id) } throws error

        detailViewModel.getDetail()

        view.verifySequence(
            UIState.Empty,
            UIState.Failed("getDetail failed", error = error)
        )
    }
}
