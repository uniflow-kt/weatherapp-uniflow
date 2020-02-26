package com.myweatherapp.ui.view.detail

import com.myweatherapp.domain.model.weather.DailyForecastId
import com.myweatherapp.domain.usecase.weather.GetWeatherDetail
import io.uniflow.androidx.flow.AndroidDataFlow
import io.uniflow.core.flow.data.UIState

class DetailViewModel(private val id: DailyForecastId,
    private val getWeatherDetail: GetWeatherDetail) : AndroidDataFlow(UIState.Empty) {

    fun getDetail() = action {
        val dailyForecast = getWeatherDetail(id)
        setState { dailyForecast.mapToDetailState() }
    }

}